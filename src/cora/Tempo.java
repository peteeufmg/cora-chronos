package cora;

import configuracoes.Configurar;
import constantes.Bateria;
import constantes.Diretorio;
import constantes.Parametro;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tempo implements interfaces.ITempo{
    private static final double melhorTempo[][] = new double[Bateria.QUANTIDADE.getValue()][Parametro.NUMERO_MAXIMO_TRECHOS.getValue()];
    private static final Equipe melhorEquipeNoTrecho[][] = new Equipe[Bateria.QUANTIDADE.getValue()][Parametro.NUMERO_MAXIMO_TRECHOS.getValue()];
    
    public static double getMelhorTempo(int bateria, int trecho) {
        return melhorTempo[bateria][trecho];
    }

    public static void setMelhorTempo(double melhorTempo, int bateria, int trecho) {
        Tempo.melhorTempo[bateria][trecho] = melhorTempo;
    }
    
    public static Equipe getMelhorEquipeNoTrecho(int bateria, int trecho) {
        return melhorEquipeNoTrecho[bateria][trecho];
    }

    public static void setMelhorEquipeNoTrecho(Equipe equipe, int bateria, int trecho) {
        Tempo.melhorEquipeNoTrecho[bateria][trecho] = equipe;
    }
    
    @Override
    public void salvar(Equipe equipe, JTable tabela, JLabel label, int bateria, int tentativa){
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();  
        String arq = Diretorio.COMPETICAO.getValue().concat("\\" + equipe.getCodigo() + "_" + (bateria + 1) + "_" + tentativa + ".txt");
        File arquivo = new File(arq); 
        FileWriter fileWriter;
        String linha;
        try {
            arquivo.createNewFile();
            fileWriter = new FileWriter(arquivo);
            try (BufferedWriter bw = new BufferedWriter(fileWriter)) {
                bw.write(label.getText());
                bw.newLine();
                bw.write("-----------------");
                bw.newLine();
                for(int row = 0; row < tabela.getRowCount(); row++){
                    linha = (String) modelo.getValueAt(row, 1);
                    bw.write(linha);
                    bw.newLine();
                }
            }
            fileWriter.close();
            System.out.println("Tempos salvos com sucesso.");
        } catch (IOException ex) {
            Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //----------------- Ler Arquivos TXT -------------------------//
    @Override
    public void arquivos(List<Equipe> listaEquipes) {   
        int codigo;
        int bateria;
        
        try{
            FileFilter filtro = (File file) -> file.getName().endsWith(".txt");  //Filtro para selecionar apenas arquivos txt
            String pontuacao_diretorio = Diretorio.COMPETICAO.getValue();
            File diretorio = new File(pontuacao_diretorio);  
            File[] files = diretorio.listFiles(filtro); 
            
            int NO_INDEX = 9999;
            int index;
            for (File arquivo : files) {
                index = NO_INDEX;
                String nomeArquivo;
                nomeArquivo = (arquivo.getName()).replace(".txt", "");
                String[] partes = nomeArquivo.split("_");
                codigo = Integer.parseInt(partes[0]);
                bateria = Integer.parseInt(partes[1]);
                bateria = bateria - 1;

                for(Equipe equipe : listaEquipes){
                    if(equipe.getCodigo() == codigo){
                        index = listaEquipes.indexOf(equipe);
                    }
                }
                
                if(index == NO_INDEX){
                    Equipe equipe = new Equipe(codigo, "", 0, false, false, 0);
                    resetaTempo(equipe);
                    listaEquipes.add(equipe);
                    index = listaEquipes.indexOf(equipe);
                } 
            
                if(bateria == Bateria.FINAL.getValue()){
                    listaEquipes.get(index).setClassificada(true);
                }
            
                calcularTempo(arquivo, listaEquipes, index, bateria);
            }  
            System.out.println("Tempos de cada equipe calculados.");
        }catch(NullPointerException e){}
    }  
    
    //----------------- Calcula o Tempo de Cada Equipe  -------------------------//
    @Override
    public void calcularTempo(File arquivo, List<Equipe> listaEquipes, int index, int bateria) {
        double tempoTotal;
        double tempoCheckPoint;
        double[] tempoBateria = new double[Parametro.NUMERO_MAXIMO_CHECK_POINTS.getValue()];
        double[] tempoTrecho = new double[Parametro.getTrechos(bateria)];
        Equipe equipe = listaEquipes.get(index);   
        try{
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha = br.readLine();
                tempoTotal = converterTempo(linha);
                
                br.readLine(); //Ler linha de traçoes para ignorá-la
                
                int checkPoints = 0;
                while(br.ready()){
                    linha = br.readLine();
                    tempoCheckPoint = converterTempo(linha);
                    tempoBateria[checkPoints] = tempoCheckPoint;
                    checkPoints++;
                }
            }
            
            if (bateria == Bateria.SPRINT.getValue()) {
                equipe.setTempoSprint(tempoTotal);
            }
            else if(equipe.getTempoTotal(bateria) >= tempoTotal){
                if(tempoTotal != Parametro.PENALIDADE.getValue()){
                    equipe.setCompletou(true, bateria);
                }
                
                double tempo_aux = 0;
                double tempo_equipe = 0;
                int trechos_aux_completos = 0;
                int trechos_equipe_completos = 0;
                for(int trecho = 0; trecho < Parametro.getTrechos(bateria); trecho++){                                              
                    if(tempoBateria[trecho + 1] != Parametro.PENALIDADE.getValue()){
                        trechos_aux_completos++;
                        tempoTrecho[trecho] = tempoBateria[trecho + 1] - tempoBateria[trecho];
                        tempo_aux += tempoTrecho[trecho];
                        tempo_equipe += (equipe.getTempoTrecho(bateria, trecho) == Parametro.PENALIDADE.getValue() ? 0 : equipe.getTempoTrecho(bateria, trecho));

                        if(equipe.getTempoTrecho(bateria, trecho) != Parametro.PENALIDADE.getValue()){
                            trechos_equipe_completos++;
                        }
                        
                        if(Tempo.getMelhorTempo(bateria, trecho) > tempoTrecho[trecho] && bateria != Bateria.FINAL.getValue()){
                            Tempo.setMelhorTempo(tempoTrecho[trecho], bateria, trecho);
                            Tempo.setMelhorEquipeNoTrecho(equipe, bateria, trecho);
                        } 
                        else if (Tempo.getMelhorTempo(bateria, trecho) > tempoTrecho[trecho] && bateria == Bateria.FINAL.getValue() && equipe.isClassificada()) {
                            Tempo.setMelhorTempo(tempoTrecho[trecho], bateria, trecho);
                            Tempo.setMelhorEquipeNoTrecho(equipe, bateria, trecho);
                        }  
                    }
                    else {
                        tempoTrecho[trecho] = Parametro.PENALIDADE.getValue();   
                    }   
                }
                
                boolean melhorDesempenho = trechos_aux_completos > trechos_equipe_completos;
                boolean mesmoDesempenho = trechos_aux_completos == trechos_equipe_completos;

                if(tempoTotal < equipe.getTempoTotal(bateria)){
                    equipe.setTempoTotal(tempoTotal, bateria);
                    for(int trecho = 0; trecho < Parametro.getTrechos(bateria); trecho++){
                        equipe.setTempoTrecho(tempoTrecho[trecho], bateria, trecho);
                    }
                }
                else if((tempo_equipe > tempo_aux && mesmoDesempenho)|| tempo_equipe == 0 || melhorDesempenho){
                    for(int trecho = 0; trecho < Parametro.getTrechos(bateria); trecho++){
                        equipe.setTempoTrecho(tempoTrecho[trecho], bateria, trecho);
                    }
                }
            }
        }catch(IOException ioe){
            System.err.println(ioe);
        }
    }    
       
    
    //----------------- Reseta os Melhores Tempos -------------------------//
    @Override
    public void resetaMelhorTempo() {
        double K = Parametro.PENALIDADE.getValue();
        double trechos;
        int baterias = Bateria.FINAL.getValue();
        for(int bateria_aux = 0; bateria_aux <= baterias; bateria_aux++){
            trechos = Parametro.getTrechos(bateria_aux);
            for(int trecho_aux = 0; trecho_aux < trechos; trecho_aux++){
                Tempo.setMelhorTempo(K, bateria_aux, trecho_aux);
            }
        }

        System.out.println("Melhores tempos resetados");
    }  
    
    //----------------- Reseta Tempo da Equipe -------------------------//
    @Override
    public void resetaTempo(Equipe equipe) {
        double K = Parametro.PENALIDADE.getValue();
        double trechos;
        int baterias = Bateria.FINAL.getValue();
        for(int bateria_aux = 0; bateria_aux <= baterias; bateria_aux++){
            equipe.setTempoTotal(K, bateria_aux);
            trechos = Parametro.getTrechos(bateria_aux);
            for(int trecho_aux = 0; trecho_aux < trechos; trecho_aux++){
                equipe.setTempoTrecho(K, bateria_aux, trecho_aux);
            }
        }

        System.out.println("Tempos da equipe " + equipe.getNome() + " resetado");
    }  
    
        //----------------- Converte o Tempo do TXT em Segundos -------------------------//
    public double converterTempo(String linha){
        String[] split = linha.split(":");
        double min = 0;
        double seg = 0;
        double cent = 0;
        if (!"--".equals(split[0])) {
            min = Double.parseDouble(split[0]);
            seg = Double.parseDouble(split[1]);
            cent = Double.parseDouble(split[2]);
        }
      
        double tempo;
        if(min == 99){
            tempo = Parametro.PENALIDADE.getValue();
        }
        else{
            min = min * 60;
            cent = cent/100;
            tempo = seg + min + cent;
        }
        return tempo;
    }
}

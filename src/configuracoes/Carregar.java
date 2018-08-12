package configuracoes;

import constantes.Bateria;
import constantes.CheckPoint;
import constantes.Diretorio;
import constantes.Parametro;
import constantes.Pista;
import cora.Equipe;
import cora.Tempo;
import frames.ExibicaoUI;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public final class Carregar implements interfaces.ICarregar{
    
     //----------------- Carrega a Tabela com os Tempos de cada Equipe an Tela CoRA -------------------------//
    @Override
    public boolean tabelaTempoEquipe(JTable tabela, JButton [] sensores, JLabel label, JLabel status, ExibicaoUI exibicaoUI, Equipe equipe, int bateria, int tentativa) {
        boolean existe;
        Object[] linha = new Object[2];
        DecimalFormat timeFormatter = new DecimalFormat("00");  
        EstiloFrame estilo = new EstiloFrame();
         
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();  
        model.setNumRows(0);
        String arq = Diretorio.COMPETICAO.getValue().concat("\\" + equipe.getCodigo() + "_" + (bateria + 1) + "_" + tentativa + ".txt");
        File arquivo = new File(arq);
        
        if(!arquivo.exists()){
            existe = false;
            
            label.setText(timeFormatter.format(0) + ":"
                        + timeFormatter.format(0) + ":"
                        + timeFormatter.format(0));
            
            exibicaoUI.getTempoTotal().setText(timeFormatter.format(0) + ":"
                        + timeFormatter.format(0) + "."
                        + timeFormatter.format(0));
            
            status.setText("PENDENTE");
            status.setForeground(new Color(0,0,255));
            
            for(int checkPoint = 0; checkPoint <= Parametro.getTrechos(bateria); checkPoint++){
                if(checkPoint == CheckPoint.LARGADA.getValue()){
                        linha[0] = "Largada";
                }
                else if(checkPoint == Parametro.getTrechos(bateria)){
                        linha[0] = "Chegada";
                        exibicaoUI.getTempo(checkPoint - 1).setText("--:--.--");
                }
                else{
                    linha[0] = checkPoint + "º Check Point";
                    exibicaoUI.getTempo(checkPoint - 1).setText("--:--.--");
                }
                
                linha[1] = "--:--.--";
                model.insertRow(tabela.getRowCount(), linha); 
                estilo.botao(sensores[checkPoint], "/imagens/bandeira_azul.png", 20, 30);
            }
        }
        else{
            existe = true;
            int checkPoint = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String tempo = br.readLine();
                String[] split = tempo.split(":");
        
                double min = Double.parseDouble(split[0]);
                double seg = Double.parseDouble(split[1]);
                double cent = Double.parseDouble(split[2]);
                
                label.setText(timeFormatter.format(min) + ":"
                        + timeFormatter.format(seg) + ":"
                        + timeFormatter.format(cent));
                
                exibicaoUI.getTempoTotal().setText(timeFormatter.format(min) + ":"
                        + timeFormatter.format(seg) + "."
                        + timeFormatter.format(cent));
                             
                if(min == 99){
                    status.setText("NÃO COMPLETOU");
                    status.setForeground(Color.RED);
                }
                else{
                    status.setText("COMPLETOU");
                    status.setForeground(new Color(0,100,0));
                }

                
                br.readLine(); //Ler linha de traçoes para ignorá-la
                
                while(br.ready()){
                    tempo = br.readLine();
                    split = tempo.split(":");
                    min = Double.parseDouble(split[0]);
                    seg = Double.parseDouble(split[1]);
                    cent = Double.parseDouble(split[2]);
                    
                    if(checkPoint == CheckPoint.LARGADA.getValue()){
                        linha[0] = "Largada";
                        linha[1] = timeFormatter.format(0) + ":" + timeFormatter.format(0) + ":" + timeFormatter.format(0);
                    }
                    else{
                        if(checkPoint == Parametro.getTrechos(bateria)){
                            linha[0] = "Chegada";
                        }
                        else{
                            linha[0] = checkPoint + "º Check Point";
                        }
                        
                        if(min != 99){
                            linha[1] = timeFormatter.format(min) + ":" + timeFormatter.format(seg) + ":" + timeFormatter.format(cent);
                            exibicaoUI.getTempo(checkPoint - 1).setText(timeFormatter.format(min) + ":" + timeFormatter.format(seg) + "." + timeFormatter.format(cent));
                        }
                        else{
                            linha[1] = "--:--:--";
                            exibicaoUI.getTempo(checkPoint - 1).setText("--:--.--");
                        }
                    }
                    
                     if(min != 99){
                            estilo.botao(sensores[checkPoint], "/imagens/bandeira_completou.png", 20, 30);
                        }
                        else{
                            estilo.botao(sensores[checkPoint], "/imagens/bandeira_vermelha.png", 20, 30);
                        }

                    model.insertRow(tabela.getRowCount(), linha); 
                    checkPoint++;
                }
                br.close();             
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Carregar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Carregar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        tabela.setModel(model);
        centralizaTabela(tabela);
        
        return existe;
    }
        
    @Override
    public void updateTempoEquipe(JTable tabela, String minutos, String segundos, String centesimos, int bateria, int checkPoint) {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();  
        Object[] linha = new Object[2];
        
        if(Pista.getOrdem()[bateria][checkPoint] == CheckPoint.LARGADA.getValue()){
            linha[0] = "Largada";
        }
        else if(Pista.getOrdem()[bateria][checkPoint] == CheckPoint.CHEGADA.getValue()){
            linha[0] = "Chegada";
        }
        else{
            linha[0] = checkPoint + "º Check Point";
        }
        
        linha[1] = minutos + ":" + segundos + ":" + centesimos;
        model.insertRow(tabela.getRowCount(), linha); 

       
        tabela.setModel(model);
        centralizaTabela(tabela);
    }

    //----------------- Carrega a Tabela com os Tempos de cada Equipe -------------------------//
    @Override
    public void tabelaTempo(JTable tabela, List<Equipe> listaEquipes, int bateria) {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();  
        model.setNumRows(0);
        int trechos = Parametro.getTrechos(bateria);
        Object[] linha = new Object[trechos + 3];

        int row = 0;
        
        if(bateria == Bateria.FINAL.getValue()){
            try{
                for (Equipe equipe : listaEquipes){
                    if(equipe.isClassificada()){
                        linha[0] = equipe.getCodigo();
                        linha[1] = equipe.getNome();
                        linha[trechos + 2] = equipe.getTempoTotal(bateria) == Parametro.PENALIDADE.getValue() ?
                                "---" : round(equipe.getTempoTotal(bateria), 3);
                        for(int trecho_aux = 0; trecho_aux < trechos; trecho_aux++){
                            linha[trecho_aux + 2] = equipe.getTempoTrecho(bateria, trecho_aux) == Parametro.PENALIDADE.getValue() ?
                                "---" : round(equipe.getTempoTrecho(bateria, trecho_aux), 3);
                        }
                        model.insertRow(row, linha);
                        row++;
                    }
                }    
            } catch(NullPointerException e){
                System.out.print(e);
            }    
        }
        else {
            try{
                for (Equipe equipe : listaEquipes){
                    linha[0] = equipe.getCodigo();
                    linha[1] = equipe.getNome();
                    linha[trechos + 2] = equipe.getTempoTotal(bateria) == Parametro.PENALIDADE.getValue() ?
                            "---" : round(equipe.getTempoTotal(bateria), 3);
                    for(int trecho_aux = 0; trecho_aux < trechos; trecho_aux++){
                        linha[trecho_aux + 2] = equipe.getTempoTrecho(bateria, trecho_aux) == Parametro.PENALIDADE.getValue() ?
                            "---" : round(equipe.getTempoTrecho(bateria, trecho_aux), 3);
                    }
                    model.insertRow(row, linha);
                    row++;
                }    
            } catch(NullPointerException e){
                System.out.print(e);
            }    
        }
       
        tabela.setModel(model);
        centralizaTabela(tabela);

        System.out.println("Tabela de tempo da " + (bateria + 1) + "ª bateria preenchida."); 
    }
    
    
    //----------------- Carrega a Tabela com os Melhores Tempos de cada Trecho -------------------------//
    @Override
    public void tabelaMelhorTempoTrecho(JTable tabela, int bateria) {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();  
        model.setNumRows(0);
        int numTrechos = Parametro.getTrechos(bateria);
        Object[] linha = new Object[4];

        int row = 0;
        
            try{
                for (int trecho = 0; trecho < numTrechos; trecho++){
                    linha[0] = trecho + 1 + "º Trecho";
                    if(Tempo.getMelhorEquipeNoTrecho(bateria, trecho) != null){
                        linha[1] = Tempo.getMelhorEquipeNoTrecho(bateria, trecho).getCodigo();
                        linha[2] = Tempo.getMelhorEquipeNoTrecho(bateria, trecho).getNome();
                        linha[3] = Tempo.getMelhorTempo(bateria, trecho) == Parametro.PENALIDADE.getValue() ? "--:--.--" :  round(Tempo.getMelhorTempo(bateria, trecho), 3);
                    }
                    else{
                        linha[1] = "---";
                        linha[2] = "---";
                        linha[3] = "--:--.--";
                    }

                    model.insertRow(row, linha);
                    row++;
                }
            } catch(NullPointerException e){
                System.out.print(e);
            }
       
        tabela.setModel(model);
        centralizaTabela(tabela);

        System.out.println("Tabela de melhores tempos por trecho da " + (bateria + 1) + "ª bateria preenchida."); 
    }
    
    //----------------- Carrega a Tabela com os Pontos de cada Equipe -------------------------//
    @Override
    public void tabelaPontos(JTable tabela, List<Equipe> listaEquipes, int bateria) { 
        int codigo;
        String nome;
        double relatorio;
        double pontuacao;
        boolean calouro;
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();  
        modelo.setNumRows(0);
        int row = 0;
        try{
            for (Equipe equipe : listaEquipes) {
                codigo = equipe.getCodigo();
                nome = equipe.getNome();
                relatorio = round(equipe.getRelatorio(), 3);
                calouro = equipe.isCalouro();
                if(bateria == Bateria.FINAL.getValue() && equipe.isClassificada()){
                    pontuacao = round(equipe.getFinalPontos(), 3);
                    Object[] linha = {codigo, nome, relatorio, calouro, pontuacao};
                    modelo.insertRow(row, linha);
                    row++;
                }
                else if(bateria == Bateria.CLASSIFICATORIA.getValue()) {
                    pontuacao = round(equipe.getClassificatoriaPontos(), 3);
                    Object[] linha = {codigo, nome, relatorio, calouro, pontuacao};
                    modelo.insertRow(row, linha);
                    row++;
                }
            }
        }catch ( NullPointerException ex){
            System.out.print(ex);
        }
        tabela.setModel(modelo);
        //centralizaTabela(tabela);

        String etapa;
        if(bateria == Bateria.FINAL.getValue()){
            etapa = "final";
        } 
        else {
            etapa = "classificatória";
        }
        System.out.println("Tabela de pontos da etapa " + etapa + " preenchida");
    }
    
    public void tabelaEquipes(JTable tabela, List<Equipe> listaEquipes) { 
        int codigo;
        String nome;
        double relatorio;
        boolean calouro;
        boolean classificada;
        double penalidade;
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();  
        modelo.setNumRows(0);
        int row = 0;
        try{
            for (Equipe equipe : listaEquipes) {
                codigo = equipe.getCodigo();
                nome = equipe.getNome();
                relatorio = round(equipe.getRelatorio(), 3);
                calouro = equipe.isCalouro();
                classificada = equipe.isClassificada();
                penalidade = equipe.getPenalidade();
                Object[] linha = {codigo, nome, relatorio, calouro, classificada, penalidade};
                modelo.insertRow(row, linha);
                row++;
            }
        }catch ( NullPointerException ex){
            System.out.print(ex);
        }
        tabela.setModel(modelo);
        //centralizaTabela(tabela);
    }
        
    //----------------- Centralizar Tabela -------------------------//
    private void centralizaTabela(JTable tabela){
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment( JLabel.CENTER );
        tabela.setDefaultRenderer(Object.class, renderer);
        
        TableCellRenderer rendererFromHeader = tabela.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
    }
        
    //----------------- Doble com Duas Casas Decimais -------------------------//
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}

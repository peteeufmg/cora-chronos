package configuracoes;

import constantes.Bateria;
import constantes.Diretorio;
import constantes.Fator;
import constantes.Parametro;
import constantes.Pista;
import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Configurar implements interfaces.IConfigurar{
    private final String dirParametros = Diretorio.CONFIGURACAO.getValue(); 
    
    //----------------- Look and Feel -------------------------//
    @Override
    public void lookandfeel() {
        try {
            UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
        } 
        catch (ParseException | UnsupportedLookAndFeelException e) {
        }
    }

    
    //----------------- Carrega Parâmetros -------------------------//
    @Override
    public void parametros() {
        criarArquivoParametros();
        File arquivo = new File(dirParametros.concat("/config.txt"));
        String parametros[ ] = new String[27];
        
        FileReader fileReader;  
        try {
            fileReader = new FileReader(arquivo);
            try (BufferedReader br = new BufferedReader(fileReader)) {
                String data;
                String[] split;
                int i = 0;
                while(br.ready()){
                    data = br.readLine();
                    split = data.split(": ");
                    parametros[i] = split[1];
                    i++;
                }
                fileReader.close();
                
                Fator.CHECKPOINT_CLASSIFICATORIA.setValue(Integer.parseInt(parametros[0]));
                Fator.TEMPO_CLASSIFICATORIA.setValue(Integer.parseInt(parametros[1]));
                Fator.COMPLETAR_CLASSIFICATORIA.setValue(Integer.parseInt(parametros[2]));
                Fator.RELATORIO_CLASSIFICATORIA.setValue(Integer.parseInt(parametros[3]));
                Fator.CALOURO_CLASSIFICATORIA.setValue(Integer.parseInt(parametros[4]));
                
                Fator.CHECKPOINT_FINAL.setValue(Integer.parseInt(parametros[5]));
                Fator.TEMPO_FINAL.setValue(Integer.parseInt(parametros[6]));
                Fator.COMPLETAR_FINAL.setValue(Integer.parseInt(parametros[7]));
                Fator.RELATORIO_FINAL.setValue(Integer.parseInt(parametros[8]));
                Fator.CALOURO_FINAL.setValue(Integer.parseInt(parametros[9]));
                
                Parametro.setTrechos(0, Integer.parseInt(parametros[10]));
                Parametro.setTrechos(1, Integer.parseInt(parametros[11]));
                Parametro.setTrechos(2, Integer.parseInt(parametros[12]));
                Parametro.setTrechos(3, Integer.parseInt(parametros[13]));
                Parametro.setTrechos(Bateria.SPRINT.getValue(), 1); //Sprint possui um número fixo de trechos = 1
                
                Parametro.NUMERO_MAXIMO_EQUIPES.setValue(Integer.parseInt(parametros[14]));
                Parametro.TENTATIVAS_CLASSIFICATORIA.setValue(Integer.parseInt(parametros[15]));
                Parametro.TENTATIVAS_FINAL.setValue(Integer.parseInt(parametros[16]));
                Parametro.TEMPO_LIMITE.setValue(Integer.parseInt(parametros[17]));
                Parametro.PORTA_COM.setPorta(String.valueOf(parametros[18]));
                Parametro.BAUDRATE.setValue(Integer.parseInt(parametros[19]));
                Parametro.BAUDRATE_INDEX.setValue(Integer.parseInt(parametros[20]));

                Parametro.DIST_SPRINT.setValue(Integer.parseInt(parametros[21]));
                
                int pos = 22;
                String[] ordem_aux;
                int [][] ordem = Pista.getOrdem();
                for(int bateria = 0; bateria < Bateria.FINAL.getValue(); bateria++){
                    ordem_aux = parametros[pos].split("_");
                    for(int ii = 0; ii <= Parametro.getTrechos(bateria); ii++){
                        ordem[bateria][ii] = Integer.parseInt(ordem_aux[ii]);
                    }


                    pos++;

                }
                Pista.setOrdem(ordem);
            }    
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NullPointerException ex) {
            Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Parametros carregados.");
    }

    //----------------- Cria o Arquivo com as Configurações -------------------------//
    @Override
    public void criarArquivoParametros() {
        File arquivo = new File(dirParametros);
        if(!arquivo.exists()){
            arquivo.mkdir();
        }
        
        arquivo = new File(dirParametros.concat("/config.txt"));
        if(!arquivo.exists()){
            try {
                arquivo.createNewFile();
                
                Parametro.setTrechos(Bateria.PRIMEIRA.getValue(), 3);
                Parametro.setTrechos(Bateria.SEGUNDA.getValue(), 5);
                Parametro.setTrechos(Bateria.TERCEIRA.getValue(), 5);
                Parametro.setTrechos(Bateria.FINAL.getValue(), 7);
                
                Pista.reseta();

                salvarParametros();  
                System.out.println("Arquivo de configuração criado.");
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    //----------------- Salva as Configurações -------------------------//
    @Override
    public void salvarParametros() {
        File arquivo = new File(dirParametros.concat("/config.txt"));
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(arquivo);
            try (BufferedWriter bw = new BufferedWriter(fileWriter)) {
                bw.write( "classificatoria_check_point: " + String.valueOf(Fator.CHECKPOINT_CLASSIFICATORIA.getValue()));
                bw.newLine();
                
                bw.write( "classificatoria_tempo: " + String.valueOf(Fator.TEMPO_CLASSIFICATORIA.getValue()));
                bw.newLine();
                
                bw.write( "classificatoria_completar: " + String.valueOf(Fator.COMPLETAR_CLASSIFICATORIA.getValue()));
                bw.newLine();
                
                bw.write( "classificatoria_relatorio: " + String.valueOf(Fator.RELATORIO_CLASSIFICATORIA.getValue()));
                bw.newLine();
                
                bw.write( "classificatoria_calouro: " + String.valueOf(Fator.CALOURO_CLASSIFICATORIA.getValue()));
                bw.newLine();
                
                bw.write( "final_check_point: " + String.valueOf(Fator.CHECKPOINT_FINAL.getValue()));
                bw.newLine();
                
                bw.write( "final_tempo: " + String.valueOf(Fator.TEMPO_FINAL.getValue()));
                bw.newLine();
                
                bw.write( "final_completar: " + String.valueOf(Fator.COMPLETAR_FINAL.getValue()));
                bw.newLine();
                
                bw.write( "final_relatorio: " +String.valueOf(Fator.RELATORIO_FINAL.getValue()));
                bw.newLine();
                
                bw.write( "final_calouro: " + String.valueOf(Fator.CALOURO_FINAL.getValue()));
                bw.newLine();
                
                for(int bateria = 0; bateria < Bateria.FINAL.getValue(); bateria++){
                    bw.write( "trechos_" + (bateria + 1) + "Bateria: " + String.valueOf(Parametro.getTrechos(bateria)));
                    bw.newLine();
                }
                
                bw.write( "trechos_final: " + String.valueOf(Parametro.getTrechos(Bateria.FINAL.getValue())));
                bw.newLine();
                
                bw.write( "número_máximo_equipes: " + Parametro.NUMERO_MAXIMO_EQUIPES.getValue());
                bw.newLine();
                
                bw.write( "número_tentativas_classificatoria: " + Parametro.TENTATIVAS_CLASSIFICATORIA.getValue());
                bw.newLine();
                
                bw.write( "número_tentativas_final: " + Parametro.TENTATIVAS_FINAL.getValue());
                bw.newLine();
                
                bw.write( "tempo_limite: " + Parametro.TEMPO_LIMITE.getValue());
                bw.newLine();
                
                bw.write( "porta_COM: " + Parametro.PORTA_COM.getPorta());
                bw.newLine();
                
                bw.write( "baud_rate: " + Parametro.BAUDRATE.getValue());
                bw.newLine();
                
                bw.write( "baud_rate_index: " + Parametro.BAUDRATE_INDEX.getValue());
                bw.newLine();
                
                bw.write( "distância_sprint: " + String.valueOf(Parametro.DIST_SPRINT.getValue()));
                bw.newLine();
                
                String linha;
                int [][] ordem = Pista.getOrdem();
                for(int bateria = 0; bateria < Bateria.QUANTIDADE.getValue(); bateria++){
                    linha = "";
                    for(int ii = 0; ii < Parametro.getTrechos(bateria); ii++){
                        linha = linha.concat(String.valueOf(ordem[bateria][ii]).concat("_"));
                    }
                    linha = linha.concat(String.valueOf(ordem[bateria][Parametro.getTrechos(bateria)]));
                    if(bateria != Bateria.FINAL.getValue()){
                        bw.write( "O_" + (bateria  + 1) + "Bateria: " + linha);
                    }
                    else{
                        bw.write( "O_Final: " + linha);
                    }

                    bw.newLine();
                }
                Pista.setOrdem(ordem);
            }
            fileWriter.close();
            System.out.println("Parâmetros salvos com sucesso.");
        } catch (IOException | NullPointerException ex) {
            Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
    
}

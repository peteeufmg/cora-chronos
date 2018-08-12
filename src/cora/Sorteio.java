package cora;

import configuracoes.Configurar;
import constantes.Diretorio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 07/2016
 * @author Joao Pedro Antunes Ferreira
 */

public class Sorteio {
     public final String dirCompeticao = Diretorio.COMPETICAO.getValue();
     public int numeroTotalEquipes, numeroTotalEquipesFinal;
     public String arq;
     
    // TESTA O ARQUIVO EQEUIPES.TXT VERIFICANDO SE EXISTE ALGUMA EQUIPE CADASTRADA PARA REALIZAÇÃO DO SORTEIO
        public boolean testaArquivo(){
            File arquivo = new File(dirCompeticao.concat("/listaEquipes/equipes.txt"));
            return (arquivo.length()==0);
     }
        
    // --------------------------FUNÇÕES SORTEIO----------------------------------------               
    // SORTEIA AS EQUIPES BATERIA FINAL
        public List<Integer> sorteiaEquipesFinal() throws FileNotFoundException, IOException {
                         
            List<Integer> listaSorteioFinalistas = new ArrayList();
            List<Integer> listaSorteadaFinalistas = new ArrayList();
            // Lê o arquivo equipes.txt e sorteia as equipes para a bateria classificatória
            FileReader leituraEquipes = new FileReader(dirCompeticao.concat("/listaEquipes/equipes.txt"));
            BufferedReader buff = new BufferedReader(leituraEquipes);  
            
                    while(buff.ready()){
                       boolean okFinalista;
                       String linha = buff.readLine();     
                       String splitLinha[] = linha.split(":");                     
                       int simFinalista = Integer.parseInt(splitLinha[4].substring(1, 2));
                       if(simFinalista==1){
                           okFinalista = true;
                       } else {
                           okFinalista=false;
                       }
                       char indice = (char) linha.codePointAt(0);
                       int indiceEquipe = Character.getNumericValue(indice);
                       if(okFinalista){
                           listaSorteioFinalistas.add(indiceEquipe);
                           numeroTotalEquipesFinal++;
                       }
                    }   
            // Monta lista de equipes a serem sorteadas              
            while(listaSorteioFinalistas.isEmpty()== false) {
                Collections.shuffle(listaSorteioFinalistas);
                listaSorteadaFinalistas.add(listaSorteioFinalistas.get(0));
                listaSorteioFinalistas.remove(0);
            }
            return listaSorteadaFinalistas;   
    }    
    //---------------------------------------------------------------------------------
          
      // TRATA A LISTA RECEBIDA SORTEADA E PREPARA PARA A TABELA A PARTIR DO ARQUIVO
        public String[] escreveTabelaSorteioFinal() throws FileNotFoundException, IOException {
            FileReader leituraEquipes = new FileReader(dirCompeticao.concat("/listaEquipes/equipes.txt"));
            BufferedReader buff = new BufferedReader(leituraEquipes);
                    // PEGA OS INDICES DAS EQUIPES SORTEADAS E COMPARA PRA VERIFICAR QUAIS SÃO AS EQUIPES                           
                    
                        List<Integer> listaSorteadaInt = new ArrayList(sorteiaEquipesFinal());
                        int tamanho = listaSorteadaInt.size();
                        String vetorSorteadoEquipesFinais[] = new String[tamanho];
                        while(buff.ready()){
                            String linha = buff.readLine(); 
                            String splitLinha[] = linha.split(":");
                            boolean simFinalista = linha.endsWith("1");
                            char indice = (char) linha.codePointAt(0);
                            int indiceEquipe = Character.getNumericValue(indice);
                            int indiceLista = listaSorteadaInt.indexOf(indiceEquipe);
                            
                            if(simFinalista){
                                vetorSorteadoEquipesFinais[indiceLista]= splitLinha[1];
                            }
                        }
                    return vetorSorteadoEquipesFinais;
    }
                    
            

        
    // --------------------TRATAMENTO ARQUIVOS BACKUP TXT----------------------------- 
    //CRIA ARQUIVO QUE CONTERÁ AS EQUIPES SORTEADAS E ORDENADAS DA BATERIA CLASSIFICATÓRIA
        public String arquivoBateria(boolean clasFinal) {
        String tipoBateria;
        if(clasFinal){
            tipoBateria="Classificatoria";
        } else {tipoBateria="Final";}
        LocalDateTime agora = LocalDateTime.now();
        String data = agora.toString().replaceAll("[^0-9]", "");
        arq = Diretorio.COMPETICAO.getValue().concat("/listaEquipes/backupBateriasEquipes/bateria"+ tipoBateria+data+".txt");
        
        File arquivoBateria = new File(arq);
        if(!arquivoBateria.exists()){
            try {
                arquivoBateria.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return arq;
    }
         
    // ESCREVE AS EQUIPES SORTEADAS PRA BATERIA EM UM TXT JA CRIADO
        public void escreveTxtBateria(List<String> textoParaSalvar, String arq) throws IOException{
            File arquivo = new File(arq);
            FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(arquivo);
            
            try (BufferedWriter buffw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo), "UTF-8"));) {
                buffw.write("Ordem das Equipes para Competição na Bateria");
                buffw.newLine();
                buffw.newLine();
                for (String textoParaSalvar1 : textoParaSalvar) {
                    buffw.write(textoParaSalvar1);
                    buffw.newLine();
                }
            }
            fileWriter.close();
            System.out.println("Lista de equipes ordenadas para bateria salva com sucesso.");
        } catch (IOException ex) {
            Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
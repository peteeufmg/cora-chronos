package cora;

import configuracoes.Configurar;
import constantes.Diretorio;
import interfaces.IArquivo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arquivo implements IArquivo{
    private final String dirCompeticao = Diretorio.COMPETICAO.getValue();
    //----------------- Carrega Lista Equipes -------------------------//
    @Override
    public List<Equipe> listaEquipes() {
        arquivoEquipes();
        Tempo tempo = new Tempo();
        File arquivo =  new File(dirCompeticao);
        if(!arquivo.exists()){
            arquivo.mkdir();
        }
        arquivo = new File(dirCompeticao.concat("/listaEquipes"));
        if(!arquivo.exists()){
            arquivo.mkdir();
            arquivo = new File(dirCompeticao.concat("/listaEquipes/equipes.txt"));
            try {
                arquivo.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
        arquivo = new File(dirCompeticao.concat("/listaEquipes/equipes.txt"));
        List<Equipe> listaEquipes = new ArrayList<>();
        FileReader fileReader;  

        try {
            fileReader = new FileReader(arquivo);
            try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"));){
                String data;               
                String[] split;
                while(br.ready()){
                    data = br.readLine();
                    split = data.split(" : ");
                    Equipe equipe = new Equipe(Integer.parseInt(split[0].trim()), split[1], Double.parseDouble(split[2]),
                            "1".equals(split[3]), "1".equals(split[4]), Double.parseDouble(split[5]));
                    tempo.resetaTempo(equipe);
                    listaEquipes.add(equipe);
                }
                fileReader.close();
            }    
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Lista de equipes carregada.");
        return listaEquipes;
    }
    
    //----------------- Salva as Configurações -------------------------//
    @Override
    public void salvarListaEquipes(List<Equipe> listaEquipes) {
        File arquivo = new File(dirCompeticao.concat("/listaEquipes/equipes.txt"));
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(arquivo);
            
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo), "UTF-8"));) {
                for(Equipe equipe : listaEquipes){
                    bw.write(equipe.getCodigo() + " : " + equipe.getNome() + " : " + equipe.getRelatorio() 
                            + " : " +  (equipe.isCalouro() ? "1":"0") + " : " +  (equipe.isClassificada()? "1":"0") + " : " + equipe.getPenalidade());
                    bw.newLine();
                }
            }
            fileWriter.close();
            System.out.println("Lista de equipes salva com sucesso.");
        } catch (IOException ex) {
            Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //----------------- Atualiza a Lista para um Certo Número de Competidores -------------------------//
    @Override
    public void updateListaEquipes(List<Equipe> listaEquipes){
        try{
            for (Iterator<Equipe> iterator = listaEquipes.listIterator(); iterator.hasNext(); ) {
                Equipe equipe = iterator.next();
                if (equipe.getCodigo() == 0) {
                    iterator.remove();
                }
            }
            System.out.println("Lista de equipes atualizada.");
        }catch(IllegalStateException e){}
    }
    
    //----------------- Cria o Arquivo com as Equipes -------------------------//
    @Override
    public void arquivoEquipes() {
        File arquivo = new File(dirCompeticao.concat("/listaEquipes/"));
        if(!arquivo.exists()){
            arquivo.mkdir();
        }
        
        arquivo = new File(dirCompeticao.concat("/listaEquipes/equipes.txt"));
        if(!arquivo.exists()){
            try {
                arquivo.createNewFile();
                System.out.println("Lista de equipes criada.");
            } catch (IOException ex) {
                Logger.getLogger(Configurar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
}

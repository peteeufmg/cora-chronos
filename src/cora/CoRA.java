package cora;

import configuracoes.Configurar;
import frames.CoRAUI;
import java.io.IOException;
import java.util.List;

public class CoRA {
    public static void main(String[] args) throws IOException, Exception {         
        Configurar configurar = new Configurar();
        configurar.lookandfeel();
        configurar.parametros();
        

        Arquivo arquivo = new Arquivo();
        List<Equipe> listaEquipes = arquivo.listaEquipes();     
                        
        CoRAUI coraUI = new CoRAUI(listaEquipes);
        coraUI.setVisible(true); 
    } 
}
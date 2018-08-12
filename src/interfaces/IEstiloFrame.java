package interfaces;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;

public interface IEstiloFrame {      
    public void tabelaTempo(JTable tabela, int trechos); 
    
    public void icone(JFrame jFrame, String imagem);
    
    public void icone(JDialog jFrame, String imagem);
    
    public void botao(JButton botao, String imagem);
    
    public void botao(JButton botao, String imagem, int n_d, int ro_d);   
}

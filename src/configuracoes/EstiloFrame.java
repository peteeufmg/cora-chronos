package configuracoes;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class EstiloFrame implements interfaces.IEstiloFrame {
   //----------------- Estilo da Tabela de Tempo  -------------------------//
    @Override
    public void tabelaTempo(JTable tabela, int trechos){ 
        (tabela.getParent()).setSize(0, 0);
        int linhas = 0;
        int colunas = trechos + 3;
        String[] colNomes = new String[colunas];
        colNomes[0] = "Código";
        colNomes[1] = "Equipe";
        colNomes[trechos + 2] = "Total (s)";
        for(int i = 0; i < trechos; i++){
            colNomes[i + 2] = (i + 1) + "° Trecho (s)";
        }  

        Object[][] celulas = new Object[linhas][colNomes.length];

        DefaultTableModel modelo = new DefaultTableModel(celulas, colNomes){
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        tabela.setModel(modelo);
        
        tabela.setAutoCreateRowSorter(true);
        
        tabela.getParent().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                if (tabela.getPreferredSize().width < tabela.getParent().getWidth()) {
                    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    (tabela.getParent()).setPreferredSize(tabela.getPreferredScrollableViewportSize());
                } else {
                    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                }
            }
            
        });
      }
    
        
    //----------------- Seta Ícone da JFrame -------------------------//
    @Override
    public void icone(JFrame jFrame, String imagem) { 
        URL url = this.getClass().getResource(imagem);  
        Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(url);  
        jFrame.setIconImage(imagemTitulo);  
    }  
    
    @Override
    public void icone(JDialog jFrame, String imagem) { 
        URL url = this.getClass().getResource(imagem);  
        Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(url);  
        jFrame.setIconImage(imagemTitulo);  
    }  
    
    //----------------- Carrega os Estilos dos Botões -------------------------//
    @Override
    public void botao(JButton botao, String imagem){
        try {
           Image img = ImageIO.read(getClass().getResource(imagem));
           Image imgro = ImageIO.read(getClass().getResource(imagem));
           img = img.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH );  
           imgro = imgro.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH );  
           botao.setIcon(new ImageIcon(img));
           botao.setRolloverIcon(new ImageIcon(imgro));
        } catch (IOException ex) {}
        botao.setRolloverEnabled(true);
    }
    
    @Override
    public void botao(JButton botao, String imagem, int n_d, int ro_d){
        try {
           Image img = ImageIO.read(getClass().getResource(imagem));
           Image imgro = ImageIO.read(getClass().getResource(imagem));
           img = img.getScaledInstance(n_d, n_d,  java.awt.Image.SCALE_SMOOTH );  
           imgro = imgro.getScaledInstance(ro_d, ro_d,  java.awt.Image.SCALE_SMOOTH );  
           botao.setIcon(new ImageIcon(img));
           botao.setRolloverIcon(new ImageIcon(imgro));
        } catch (IOException ex) {}
        botao.setRolloverEnabled(true);
    }
    
    public void label(JLabel label, String imagem){
        try {
           Image img = ImageIO.read(getClass().getResource(imagem));
           img = img.getScaledInstance(label.getWidth(), label.getHeight() - 20, java.awt.Image.SCALE_SMOOTH );  
           label.setIcon(new ImageIcon(img));
        } catch (IOException ex) {}
    }
}

package interfaces;

import cora.Equipe;
import frames.ExibicaoUI;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;

public interface ICarregar { 
    public void tabelaPontos(JTable tabela, List<Equipe> listaEquipes, int bateria);
                   
    public void tabelaTempo(JTable tabela, List<Equipe> listaEquipes, int bateria);
    
    public void tabelaMelhorTempoTrecho(JTable tabela, int bateria);
    
    public boolean tabelaTempoEquipe(JTable tabela, JButton [] sensores, JLabel label, JLabel status, ExibicaoUI exibicaoUI, Equipe equipe, int bateria, int tentativa);
    
    public void updateTempoEquipe(JTable tabela, String minutos, String segundos, String centesimos, int bateria, int checkPoint);
}

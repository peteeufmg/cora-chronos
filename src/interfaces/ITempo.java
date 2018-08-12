package interfaces;

import cora.Equipe;
import java.io.File;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;

public interface ITempo {
    public void arquivos(List<Equipe> equipe);
    
    public void salvar(Equipe equipe, JTable tabela, JLabel label, int bateria, int tentativa);
    
    public void resetaMelhorTempo();
    
    public void resetaTempo(Equipe equipe);
            
    public void calcularTempo(File arquivo, List<Equipe> equipe, int index, int bateria);
    
}

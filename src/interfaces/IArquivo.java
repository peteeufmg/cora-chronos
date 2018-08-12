package interfaces;

import cora.Equipe;
import java.util.List;

public interface IArquivo {
    public List<Equipe> listaEquipes();
    
    public void arquivoEquipes();
    
    public void salvarListaEquipes(List<Equipe> listaEquipes);
    
    public void updateListaEquipes(List<Equipe> listaEquipes);
    
}

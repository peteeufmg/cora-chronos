package interfaces;

import cora.Equipe;
import java.util.List;

public interface IPontuar {
    public void calcularPontos(List<Equipe> listaEquipes, int index, int bateria);
}

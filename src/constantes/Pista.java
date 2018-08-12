package constantes;

public class Pista {
    static int [][] ordem = new int [Bateria.QUANTIDADE.getValue()][Parametro.NUMERO_MAXIMO_CHECK_POINTS.getValue()];

    public static int[][] getOrdem() {
        return ordem;
    }

    public static void setOrdem(int[][] ordem) {
        Pista.ordem = ordem;
    }
    
    public static void reseta(){
        int trechos;
        for(int bateria = 0; bateria < Bateria.QUANTIDADE.getValue(); bateria++){
            trechos = Parametro.getTrechos(bateria);
            for(int j = 0; j < trechos; j++){
                ordem[bateria][j] = j;
            }
            ordem[bateria][trechos] = CheckPoint.CHEGADA.getValue();
        }
    }
}

package constantes;

public enum Parametro {
    NUMERO_MAXIMO_EQUIPES(25),
    NUMERO_MAXIMO_TRECHOS(9),
    NUMERO_MAXIMO_CHECK_POINTS(10),
    TENTATIVAS_CLASSIFICATORIA(2),
    TENTATIVAS_FINAL(4),
    PENALIDADE(9999999),
    BAUDRATE_INDEX(5),
    BAUDRATE(9600),
    PORTA_COM("COM5"),
    TEMPO_LIMITE(240000),
    DIST_SPRINT(243);
    
    private int parametro; 
    private String porta;
    
    Parametro(int parametro){
        this.parametro = parametro;
    }
    
    Parametro(String porta){
        this.porta = porta;
    }
    
    public int getValue() {  
        return parametro;  
    } 
    
    public String getPorta() {  
        return porta;  
    } 
    
    public void setValue(int parametro) {  
        this.parametro = parametro;  
    } 
        
    public void setPorta(String porta) {  
        this.porta = porta;
    }
    
    private static final int [] TRECHOS = new int[Bateria.QUANTIDADE.getValue()];  
    public static int getTrechos(int bateria) {
        return TRECHOS[bateria];
    }

    public static void setTrechos(int bateria, int TRECHOS) {
        Parametro.TRECHOS[bateria] = TRECHOS;
    }
}

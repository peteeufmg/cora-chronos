package constantes;

public enum Trechos {
    PRIMEIRA_BATERIA(0), SEGUNDA_BATERIA(1), TERCEIRA_BATERIA(2), FINAL(3), SPRINT(4);
    
    private final int trechos;
    Trechos(int trechos){
	this.trechos = trechos;
    }
    public int getValue() {  
        return trechos;  
    }
}

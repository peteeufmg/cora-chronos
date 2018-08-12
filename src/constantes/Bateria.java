package constantes;

public enum Bateria {
    PRIMEIRA(0), SEGUNDA(1), TERCEIRA(2), FINAL(3), SPRINT(4), QUANTIDADE(5), CLASSIFICATORIA(999);
    
    private final int bateria;
    Bateria(int bateria){
	this.bateria = bateria;
    }
    public int getValue() {  
        return bateria;  
    }
}

package constantes;

public enum CheckPoint {
    LARGADA(0), PRIMEIRO(1), SEGUNDO(2), TERCEIRO(3), QUARTO(4), QUINTO(5),  SEXTO(6), SETIMO(7), OITAVO(8), CHEGADA(9);
    
    private final int CP;
    CheckPoint(int CP){
	this.CP = CP;
    }
    
    public int getValue() {  
        return CP;  
    }
    
}

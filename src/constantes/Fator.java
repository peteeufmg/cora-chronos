package constantes;

public enum Fator {
        CHECKPOINT_CLASSIFICATORIA(60),
        TEMPO_CLASSIFICATORIA(20),
        COMPLETAR_CLASSIFICATORIA(10),
        RELATORIO_CLASSIFICATORIA(20),
        CALOURO_CLASSIFICATORIA(10),
        
        CHECKPOINT_FINAL(70),
        TEMPO_FINAL(30),
        COMPLETAR_FINAL(20),
        RELATORIO_FINAL(0),
        CALOURO_FINAL(0);
        
        private int fator;
	Fator(int fator){
		this.fator = fator;
	}
        public int getValue() {  
            return fator;  
        } 
        public void setValue(int fator) {  
            this.fator = fator;  
        } 
}

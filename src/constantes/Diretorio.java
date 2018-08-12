package constantes;

public enum Diretorio {
    CONFIGURACAO(System.getProperty("user.dir").concat("\\config")),
    COMPETICAO(System.getProperty("user.dir").concat("\\competicao")),
    RELATORIO(System.getProperty("user.dir").concat("\\relatório.pdf"));
    
    private String diretorio;
    Diretorio(String diretorio){
        this.diretorio = diretorio;
    }
    
    public String getValue() {  
        return diretorio;  
    }    
    public void setValue(String diretorio) {  
        this.diretorio = diretorio;  
    }    
}

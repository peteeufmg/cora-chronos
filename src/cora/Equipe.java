package cora;

import constantes.Bateria;
import constantes.Parametro;

public final class Equipe implements Comparable<Equipe> {
    private String nome;
    private int codigo; 
    
    private double relatorio;
    private boolean calouro;
    private boolean classificada;
    
    private double classificatoriaPontos;
    private double finalPontos;
    private double penalidade;
    
    private final double[][] tempoTrechos;
    private final double[] tempoTotal;
    private double tempoSprint;
    
    private final boolean [] completou;
    
    public Equipe() {      
        this.nome = "";
        this.codigo = 0;
        this.relatorio = 0;
        this.calouro = false;
        this.classificada = false;
        
        this.classificatoriaPontos = 0;
        this.finalPontos = 0;
        this.penalidade = 0;
        
        this.completou = new  boolean[Bateria.QUANTIDADE.getValue()];
        
        this.tempoTrechos = new double[Bateria.QUANTIDADE.getValue()][Parametro.NUMERO_MAXIMO_TRECHOS.getValue()];
        this.tempoTotal = new double[Bateria.QUANTIDADE.getValue()];
        this.tempoSprint = 0;
    }
    
    public Equipe(int codigo, String nome, double relatorio, boolean calouro, boolean finalista, double penalidade) {      
        this.nome = nome;
        this.codigo = codigo;
        this.relatorio = relatorio;
        this.calouro = calouro;
        this.classificada = finalista;
        this.penalidade = penalidade;
        
        this.classificatoriaPontos = 0;
        this.finalPontos = 0;
        
        this.completou = new  boolean[Bateria.QUANTIDADE.getValue()];
        
        this.tempoTrechos = new double[Bateria.QUANTIDADE.getValue()][Parametro.NUMERO_MAXIMO_TRECHOS.getValue()];
        this.tempoTotal = new double[Bateria.QUANTIDADE.getValue()];
        this.tempoSprint = 0;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;       
    }

    public boolean isClassificada() {
        return classificada;
    }

    public void setClassificada(boolean classificada) {
        this.classificada = classificada;
    }
    
    public double getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(double relatorio) {
        this.relatorio = relatorio;
    }

    public boolean isCalouro() {
        return calouro;
    }

    public void setCalouro(boolean calouro) {
        this.calouro = calouro;
    }
    
    public double getClassificatoriaPontos() {
        return classificatoriaPontos;
    }

    public void setClassificatoriaPontos(double classificatoriaPontos) {
        this.classificatoriaPontos = classificatoriaPontos;
    }

    public double getFinalPontos() {
        return finalPontos;
    }

    public void setFinalPontos(double finalPontos) {
        this.finalPontos = finalPontos;
    }
    
    public double getPenalidade() {
        return penalidade;
    }

    public void setPenalidade(double penalidade) {
        this.penalidade = penalidade;
    }
    
    public boolean getCompletou(int bateria) {
        return completou[bateria];
    }

    public void setCompletou(boolean completou, int bateria) {
        this.completou[bateria] = completou;
    }

    public double getTempoTrecho(int bateria, int trecho) {
        return tempoTrechos[bateria][trecho];
    }

    public void setTempoTrecho(double tempo, int bateria, int trecho) {
        this.tempoTrechos[bateria][trecho] = tempo;
    } 
    
    public double getTempoTotal(int bateria) {
        return tempoTotal[bateria];
    }

    public void setTempoTotal(double tempo, int bateria) {
        this.tempoTotal[bateria] = tempo;
    }

    public double[][] getTempoTrechos() {
        return tempoTrechos;
    }

    public double[] getTempoTotal() {
        return tempoTotal;
    }

    public boolean[] getCompletou() {
        return completou;
    }
    
    public double getTempoSprint() {
        return tempoSprint;
    }
    
    public void setTempoSprint(double tempoSprint) {
        this.tempoSprint = tempoSprint;
    }

    @Override
    public int compareTo(Equipe equipe) {
        if (this.finalPontos < equipe.getFinalPontos()) {
            return 1;
        }
        else if (this.finalPontos > equipe.getFinalPontos()) {
            return -1;
        }
        else{
           if (this.classificatoriaPontos < equipe.getClassificatoriaPontos()) {
                return 1;
            }
            else if (this.classificatoriaPontos > equipe.getClassificatoriaPontos()) {
                return -1;
            } 
        }
        return 0;
    }
}
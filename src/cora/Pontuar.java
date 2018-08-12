package cora;

import constantes.Bateria;
import constantes.Fator;
import constantes.Parametro;
import java.util.Collections;
import java.util.List;

public class Pontuar implements interfaces.IPontuar {
    //----------------- Calcula a Pontuação de Cada Equipe -------------------------//
    @Override
    public void calcularPontos(List<Equipe> listaEquipes, int index, int bateria) {
        int fator_checkpoint,  fator_tempo, fator_completar, fator_relatorio, fator_calouro;
        int trechos;
        double bateriasCompletas = 0, trechosCompletos = 0;
        double tempoTrecho, melhorTempo, pontos[], media = 0.0;
        boolean sprint = (bateria == Bateria.SPRINT.getValue());
        
        Equipe equipe = listaEquipes.get(index);
        
        if (bateria == Bateria.FINAL.getValue() && equipe.isClassificada() && !sprint){               
            fator_checkpoint = Fator.CHECKPOINT_FINAL.getValue();
            fator_tempo = Fator.TEMPO_FINAL.getValue();
            fator_completar = Fator.COMPLETAR_FINAL.getValue();
            fator_relatorio = Fator.RELATORIO_FINAL.getValue();
            fator_calouro = Fator.CALOURO_FINAL.getValue();
            
            if(equipe.getCompletou(Bateria.FINAL.getValue())){
                bateriasCompletas = 1;
            }
            
            trechos = Parametro.getTrechos(Bateria.FINAL.getValue());
            for(int trecho_aux = 0; trecho_aux < trechos; trecho_aux++){
                tempoTrecho = equipe.getTempoTrecho(bateria, trecho_aux);
                melhorTempo = Tempo.getMelhorTempo(bateria, trecho_aux);
                
                if(Parametro.PENALIDADE.getValue() != tempoTrecho){
                        media += (melhorTempo/tempoTrecho);
                        trechosCompletos++;
                }
                
            }
            media /= trechos;
            
            trechosCompletos /= Parametro.getTrechos(Bateria.FINAL.getValue());
            
            double total = pontuacao(equipe, media, bateriasCompletas, trechosCompletos, fator_checkpoint, fator_tempo, fator_completar, fator_relatorio, fator_calouro);
            equipe.setFinalPontos(total);
        }
        else if(bateria == Bateria.CLASSIFICATORIA.getValue() && !sprint){
            fator_checkpoint = Fator.CHECKPOINT_CLASSIFICATORIA.getValue();
            fator_tempo = Fator.TEMPO_CLASSIFICATORIA.getValue();
            fator_completar = Fator.COMPLETAR_CLASSIFICATORIA.getValue();
            fator_relatorio = Fator.RELATORIO_CLASSIFICATORIA.getValue();
            fator_calouro = Fator.CALOURO_CLASSIFICATORIA.getValue();
            
            int numBaterias = Bateria.FINAL.getValue();
            int numTrechos = Parametro.getTrechos(Bateria.PRIMEIRA.getValue()) + Parametro.getTrechos(Bateria.SEGUNDA.getValue()) + Parametro.getTrechos(Bateria.TERCEIRA.getValue());
            pontos = new double[numBaterias];
            for(int bateria_aux = 0; bateria_aux < numBaterias; bateria_aux++){
                trechos = Parametro.getTrechos(bateria_aux);
                for(int trecho_aux = 0; trecho_aux < trechos; trecho_aux++){
                    tempoTrecho = equipe.getTempoTrecho(bateria_aux, trecho_aux);
                    melhorTempo = Tempo.getMelhorTempo(bateria_aux, trecho_aux);
                    if(Parametro.PENALIDADE.getValue() != tempoTrecho){
                        pontos[bateria_aux] += (melhorTempo/tempoTrecho);
                        trechosCompletos++;
                    }
                    else{
                        pontos[bateria_aux] = 0; 
                    }
                }
                
                if(equipe.getCompletou(bateria_aux) == true){
                    bateriasCompletas++;
                }
                pontos[bateria_aux] /= trechos; 
            }
            bateriasCompletas = bateriasCompletas/numBaterias;
            double soma = 0.0;
            for(int i = 0; i < numBaterias; i++){
                soma += pontos[i];
            }
            media =  soma / numBaterias;
            
            trechosCompletos /= numTrechos;
                    
            double total = pontuacao(equipe, media, bateriasCompletas, trechosCompletos, fator_checkpoint, fator_tempo, fator_completar, fator_relatorio, fator_calouro);
            equipe.setClassificatoriaPontos(total);
        }

        listaEquipes.set(index, equipe);
        
        if(bateria == Bateria.FINAL.getValue()){
            System.out.println("Pontos da equipe " + equipe.getNome() + " na etapa final calculados.");
        } 
        else {
            System.out.println("Pontos da equipe " + equipe.getNome() + " na etapa classificatória calculados.");
        }
    }
    
    //----------------- Calcula a Pontuação de Cada Equipe -------------------------//
    private double pontuacao(Equipe equipe, double media,  double desempenhoBaterias, double desempenhoTrechos, int checkpoint, int tempo, int completar, int relatorio, int calouro){
        double total;
        total = (media * tempo);
        total += (desempenhoBaterias * completar);
        total += (desempenhoTrechos * checkpoint);
        
        if(relatorio != 0){
            total += equipe.getRelatorio();
        }
        
        if(equipe.isCalouro()){
            total = total  + calouro;
        }
        
        total -= equipe.getPenalidade();

        if (total < 0) total = 0;
        return total;
    }
    
    public void finalistas(List<Equipe> listaEquipes){       
        for(int index = 0; index < listaEquipes.size(); index++){
            calcularPontos(listaEquipes, index, Bateria.CLASSIFICATORIA.getValue());
            calcularPontos(listaEquipes, index, Bateria.FINAL.getValue());
        }
        Collections.sort(listaEquipes);
    }
}
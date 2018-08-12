/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cora;

import constantes.Parametro;

/**
 *
 * @author Mateus Simões
 */
public class Sprint {
        public double velocidade(String t){
        t = t.replace('.', ':');
        Tempo tempo = new Tempo();
        double tempo_total = tempo.converterTempo(t);
        double dist = Parametro.DIST_SPRINT.getValue()/100;
        double velocidade  = (tempo_total != 0 ? dist / tempo_total : 0);
        
        long factor = (long) Math.pow(10, 2);
        velocidade = velocidade * factor;
        long tmp = Math.round(velocidade);
        velocidade = (double) tmp / factor;
        
        return velocidade;
    } 
}

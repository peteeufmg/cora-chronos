/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import configuracoes.Carregar;
import configuracoes.Configurar;
import configuracoes.EstiloFrame;
import constantes.Bateria;
import constantes.Fator;
import cora.Equipe;
import cora.Pontuar;
import cora.Tempo;
import java.util.Collections;
import java.util.List;

public class PontuacaoUI extends javax.swing.JFrame {

 private List<Equipe> listaEquipes;
    private static MonitorSerialUI monitorSerial = new MonitorSerialUI();
    
    public PontuacaoUI() {
        initComponents();
    }
    
    public PontuacaoUI(List<Equipe> listaEquipes) {     
        initComponents();
        this.listaEquipes = listaEquipes;
        inicializaFrame();
    }
    
    public static MonitorSerialUI getMonitorSerial() {
        return monitorSerial;
    }
    
    private void salvarFatores(){
        Fator.CHECKPOINT_CLASSIFICATORIA.setValue((int) checkPointClassificatoria.getValue());
        Fator.TEMPO_CLASSIFICATORIA.setValue((int) tempoClassificatoria.getValue());
        Fator.COMPLETAR_CLASSIFICATORIA.setValue((int) completarClassificatoria.getValue());
        Fator.RELATORIO_CLASSIFICATORIA.setValue((int) relatorioClassificatoria.getValue());
        Fator.CALOURO_CLASSIFICATORIA.setValue((int) calouroClassificatoria.getValue());
        
        Fator.CHECKPOINT_FINAL.setValue((int) checkPointFinal.getValue());
        Fator.TEMPO_FINAL.setValue((int) tempoFinal.getValue());
        Fator.COMPLETAR_FINAL.setValue((int) completarFinal.getValue());
        Fator.RELATORIO_FINAL.setValue((int) relatorioFinal.getValue());
        Fator.CALOURO_FINAL.setValue((int) calouroFinal.getValue());
        
        recalcularPontos();
    }
        
    private void setarFatores(){
        checkPointClassificatoria.setValue(Fator.CHECKPOINT_CLASSIFICATORIA.getValue());
        tempoClassificatoria.setValue(Fator.TEMPO_CLASSIFICATORIA.getValue());
        completarClassificatoria.setValue(Fator.COMPLETAR_CLASSIFICATORIA.getValue());
        relatorioClassificatoria.setValue(Fator.RELATORIO_CLASSIFICATORIA.getValue());
        calouroClassificatoria.setValue(Fator.CALOURO_CLASSIFICATORIA.getValue());   
        
        checkPointFinal.setValue(Fator.CHECKPOINT_FINAL.getValue());
        tempoFinal.setValue(Fator.TEMPO_FINAL.getValue());
        completarFinal.setValue(Fator.COMPLETAR_FINAL.getValue());
        relatorioFinal.setValue(Fator.RELATORIO_FINAL.getValue());
        calouroFinal.setValue(Fator.CALOURO_FINAL.getValue());   
    }
    
    private void setarValoresTabelaPontos(){
        Collections.sort(listaEquipes);
        Carregar carregar = new Carregar(); 
        carregar.tabelaPontos(tabelaClassificacao, listaEquipes, Bateria.CLASSIFICATORIA.getValue());
        carregar.tabelaPontos(tabelaFinal, listaEquipes, Bateria.FINAL.getValue());
    }
    
    private void recalcularPontos(){              
        Pontuar pontuar = new Pontuar();
        
        for(int index = 0; index < listaEquipes.size(); index++){
            pontuar.calcularPontos(listaEquipes, index, Bateria.CLASSIFICATORIA.getValue());
            pontuar.calcularPontos(listaEquipes, index, Bateria.FINAL.getValue());
        } 
        
        Collections.sort(listaEquipes);

        setarValoresTabelaPontos();
        setarFatores();
        
    }
    
    private void setarValores(){
        Tempo tempo = new Tempo();
        tempo.resetaMelhorTempo();
        tempo.arquivos(listaEquipes);
        
        Pontuar pontuar = new Pontuar();
        
        for(int index = 0; index < listaEquipes.size(); index++){
            pontuar.calcularPontos(listaEquipes, index, Bateria.CLASSIFICATORIA.getValue());
            pontuar.calcularPontos(listaEquipes, index, Bateria.FINAL.getValue());
        } 
        
        Collections.sort(listaEquipes);

        setarValoresTabelaPontos();
        setarFatores();
    }
    
    private void inicializaFrame(){
        EstiloFrame estilo = new EstiloFrame();
        estilo.icone(this, "/imagens/pontuacao.png");
        estilo.label(icone1, "/imagens/CoRA_Logo.png");
        estilo.label(icone2, "/imagens/CoRA_Logo.png");
        
        setarValores();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaClassificacao = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        labelCalouro = new javax.swing.JLabel();
        checkPointClassificatoria = new javax.swing.JSpinner();
        labelRelatorio = new javax.swing.JLabel();
        tempoClassificatoria = new javax.swing.JSpinner();
        labelTempo = new javax.swing.JLabel();
        completarClassificatoria = new javax.swing.JSpinner();
        labelCheckPoint = new javax.swing.JLabel();
        relatorioClassificatoria = new javax.swing.JSpinner();
        labelCompletar = new javax.swing.JLabel();
        calouroClassificatoria = new javax.swing.JSpinner();
        botaoSalvarClassificatoria = new javax.swing.JButton();
        botaoResetarClassificatoria = new javax.swing.JButton();
        icone1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaFinal = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        labelCalouroFinal = new javax.swing.JLabel();
        checkPointFinal = new javax.swing.JSpinner();
        labelRelatorioFinal = new javax.swing.JLabel();
        tempoFinal = new javax.swing.JSpinner();
        labelTempoFinal = new javax.swing.JLabel();
        completarFinal = new javax.swing.JSpinner();
        labelCheckPointFinal = new javax.swing.JLabel();
        relatorioFinal = new javax.swing.JSpinner();
        labelFinal = new javax.swing.JLabel();
        calouroFinal = new javax.swing.JSpinner();
        botaoSalvarFinal = new javax.swing.JButton();
        botaoResetarFinal = new javax.swing.JButton();
        icone2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pontuação");
        setResizable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Classificação"));

        tabelaClassificacao.setAutoCreateRowSorter(true);
        tabelaClassificacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Equipe", "Relatório", "Calouro", "Pontuação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaClassificacao);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fatores"));

        labelCalouro.setText("Check Point");

        checkPointClassificatoria.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        checkPointClassificatoria.setToolTipText("Fator por Completar Trecho");

        labelRelatorio.setText("Tempo");

        tempoClassificatoria.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        tempoClassificatoria.setToolTipText("Fator Tempo");

        labelTempo.setText("Completar");

        completarClassificatoria.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        completarClassificatoria.setToolTipText("Bônus por Completar Pista");

        labelCheckPoint.setText("Relatório");

        relatorioClassificatoria.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        relatorioClassificatoria.setToolTipText("Fator Relatório ");

        labelCompletar.setText("Calouro");

        calouroClassificatoria.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        calouroClassificatoria.setToolTipText("Bônus por ser Calouro");

        botaoSalvarClassificatoria.setText("Salvar");
        botaoSalvarClassificatoria.setToolTipText("Salvar Fatores");
        botaoSalvarClassificatoria.setPreferredSize(new java.awt.Dimension(71, 23));
        botaoSalvarClassificatoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarClassificatoriaActionPerformed(evt);
            }
        });

        botaoResetarClassificatoria.setText("Resetar");
        botaoResetarClassificatoria.setToolTipText("Resetar Fatores");
        botaoResetarClassificatoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoResetarClassificatoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCheckPoint)
                    .addComponent(labelRelatorio)
                    .addComponent(labelCalouro)
                    .addComponent(labelCompletar)
                    .addComponent(labelTempo)
                    .addComponent(botaoSalvarClassificatoria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(completarClassificatoria)
                    .addComponent(relatorioClassificatoria)
                    .addComponent(tempoClassificatoria)
                    .addComponent(checkPointClassificatoria)
                    .addComponent(calouroClassificatoria)
                    .addComponent(botaoResetarClassificatoria))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCalouro)
                    .addComponent(checkPointClassificatoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRelatorio)
                    .addComponent(tempoClassificatoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTempo)
                    .addComponent(completarClassificatoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCheckPoint)
                    .addComponent(relatorioClassificatoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCompletar)
                    .addComponent(calouroClassificatoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(botaoResetarClassificatoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botaoSalvarClassificatoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(icone1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icone1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Classificatória", jPanel1);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Classificação"));

        tabelaFinal.setAutoCreateRowSorter(true);
        tabelaFinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Equipe", "Relatório", "Calouro", "Pontuação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelaFinal);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fatores"));

        labelCalouroFinal.setText("Check Point");

        checkPointFinal.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        checkPointFinal.setToolTipText("Fator por Completar Trecho");

        labelRelatorioFinal.setText("Tempo");

        tempoFinal.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        tempoFinal.setToolTipText("Fator Tempo");

        labelTempoFinal.setText("Completar");

        completarFinal.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        completarFinal.setToolTipText("Bônus por Completar Pista");

        labelCheckPointFinal.setText("Relatório");

        relatorioFinal.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        relatorioFinal.setToolTipText("Fator Relatório ");

        labelFinal.setText("Calouro");

        calouroFinal.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        calouroFinal.setToolTipText("Bônus por ser Calouro");

        botaoSalvarFinal.setText("Salvar");
        botaoSalvarFinal.setToolTipText("Salvar Fatores");
        botaoSalvarFinal.setPreferredSize(new java.awt.Dimension(71, 23));
        botaoSalvarFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarFinalActionPerformed(evt);
            }
        });

        botaoResetarFinal.setText("Resetar");
        botaoResetarFinal.setToolTipText("Resetar Fatores");
        botaoResetarFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoResetarFinalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCheckPointFinal)
                    .addComponent(labelRelatorioFinal)
                    .addComponent(labelCalouroFinal)
                    .addComponent(labelFinal)
                    .addComponent(labelTempoFinal)
                    .addComponent(botaoSalvarFinal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(completarFinal)
                    .addComponent(relatorioFinal)
                    .addComponent(tempoFinal)
                    .addComponent(checkPointFinal)
                    .addComponent(calouroFinal)
                    .addComponent(botaoResetarFinal))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCalouroFinal)
                    .addComponent(checkPointFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRelatorioFinal)
                    .addComponent(tempoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTempoFinal)
                    .addComponent(completarFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCheckPointFinal)
                    .addComponent(relatorioFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFinal)
                    .addComponent(calouroFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(botaoResetarFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botaoSalvarFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(icone2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icone2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Final", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSalvarClassificatoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarClassificatoriaActionPerformed
        salvarFatores();

        Configurar configurar = new Configurar();
        configurar.salvarParametros();
    }//GEN-LAST:event_botaoSalvarClassificatoriaActionPerformed

    private void botaoResetarClassificatoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoResetarClassificatoriaActionPerformed
        setarFatores();
    }//GEN-LAST:event_botaoResetarClassificatoriaActionPerformed

    private void botaoSalvarFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarFinalActionPerformed
        salvarFatores();
        Configurar configuracao = new Configurar();
        configuracao.salvarParametros();
    }//GEN-LAST:event_botaoSalvarFinalActionPerformed

    private void botaoResetarFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoResetarFinalActionPerformed
        setarFatores();
    }//GEN-LAST:event_botaoResetarFinalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PontuacaoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PontuacaoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PontuacaoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PontuacaoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PontuacaoUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoResetarClassificatoria;
    private javax.swing.JButton botaoResetarFinal;
    private javax.swing.JButton botaoSalvarClassificatoria;
    private javax.swing.JButton botaoSalvarFinal;
    private javax.swing.JSpinner calouroClassificatoria;
    private javax.swing.JSpinner calouroFinal;
    private javax.swing.JSpinner checkPointClassificatoria;
    private javax.swing.JSpinner checkPointFinal;
    private javax.swing.JSpinner completarClassificatoria;
    private javax.swing.JSpinner completarFinal;
    private javax.swing.JLabel icone1;
    private javax.swing.JLabel icone2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelCalouro;
    private javax.swing.JLabel labelCalouroFinal;
    private javax.swing.JLabel labelCheckPoint;
    private javax.swing.JLabel labelCheckPointFinal;
    private javax.swing.JLabel labelCompletar;
    private javax.swing.JLabel labelFinal;
    private javax.swing.JLabel labelRelatorio;
    private javax.swing.JLabel labelRelatorioFinal;
    private javax.swing.JLabel labelTempo;
    private javax.swing.JLabel labelTempoFinal;
    private javax.swing.JSpinner relatorioClassificatoria;
    private javax.swing.JSpinner relatorioFinal;
    private javax.swing.JTable tabelaClassificacao;
    private javax.swing.JTable tabelaFinal;
    private javax.swing.JSpinner tempoClassificatoria;
    private javax.swing.JSpinner tempoFinal;
    // End of variables declaration//GEN-END:variables
}

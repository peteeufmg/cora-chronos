package frames;


import constantes.Bateria;
import constantes.Parametro;
import configuracoes.Carregar;
import configuracoes.EstiloFrame;
import cora.Equipe;
import cora.Tempo;
import java.util.Collections;
import java.util.List;

public class TempoUI extends javax.swing.JFrame {
    private List<Equipe> listaEquipes;
    
    public TempoUI() {
        initComponents();
    }
    
    public TempoUI(List<Equipe> listaEquipes) {
        initComponents();
        this.listaEquipes = listaEquipes;
        inicializaFrame();
    }
    
    private void setarValoresTabelaTempo(){
        Carregar carregar = new Carregar(); 
        EstiloFrame estilo = new EstiloFrame();
        int trechos, bateria;
        bateria = Bateria.PRIMEIRA.getValue();
        trechos = Parametro.getTrechos(bateria);
        estilo.tabelaTempo(tabelaTempo1Bateria, trechos);
        carregar.tabelaTempo(tabelaTempo1Bateria, listaEquipes, bateria);
        carregar.tabelaMelhorTempoTrecho(tabelaMelhorTempoBateria1, bateria);
        
        bateria = Bateria.SEGUNDA.getValue();
        trechos = Parametro.getTrechos(bateria);
        estilo.tabelaTempo(tabelaTempo2Bateria, trechos);
        carregar.tabelaTempo(tabelaTempo2Bateria, listaEquipes, bateria);
        carregar.tabelaMelhorTempoTrecho(tabelaMelhorTempoBateria2, bateria);
        
        bateria = Bateria.TERCEIRA.getValue();
        trechos = Parametro.getTrechos(bateria);
        estilo.tabelaTempo(tabelaTempo3Bateria, trechos);
        carregar.tabelaTempo(tabelaTempo3Bateria, listaEquipes, bateria);
        carregar.tabelaMelhorTempoTrecho(tabelaMelhorTempoBateria3, bateria);
        
        bateria = Bateria.FINAL.getValue();
        trechos = Parametro.getTrechos(bateria);
        estilo.tabelaTempo(tabelaTempoFinal, trechos);
        carregar.tabelaTempo(tabelaTempoFinal, listaEquipes, bateria);
        carregar.tabelaMelhorTempoTrecho(tabelaMelhorTempoFinal, bateria);
    }
    
    private void setarValores(){           
        Tempo tempo = new Tempo();
        tempo.resetaMelhorTempo();
        tempo.arquivos(listaEquipes);
        
        Collections.sort(listaEquipes);

        setarValoresTabelaTempo();
    }
    
    private void inicializaFrame(){
        EstiloFrame estilo = new EstiloFrame();
        estilo.icone(this, "/imagens/tempo.png");  
        
        setarValores();
    }
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        guia1Bateria = new javax.swing.JPanel();
        scrollPanetabelaTempo1Bateria = new javax.swing.JScrollPane();
        tabelaTempo1Bateria = new javax.swing.JTable();
        guia2Bateria = new javax.swing.JPanel();
        scrollPanetabelaTempo2Bateria = new javax.swing.JScrollPane();
        tabelaTempo2Bateria = new javax.swing.JTable();
        guia3Bateria = new javax.swing.JPanel();
        scrollPanetabelaTempo3Bateria = new javax.swing.JScrollPane();
        tabelaTempo3Bateria = new javax.swing.JTable();
        guiaTFinal = new javax.swing.JPanel();
        scrollPaneTempoFinal = new javax.swing.JScrollPane();
        tabelaTempoFinal = new javax.swing.JTable();
        guiaMelhoresTempos = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        painelMelhorTempoBateria1 = new javax.swing.JPanel();
        scrollPanelMTempo1 = new javax.swing.JScrollPane();
        tabelaMelhorTempoBateria1 = new javax.swing.JTable();
        painelMelhorTempoBateria2 = new javax.swing.JPanel();
        scrollPanelMTempo2 = new javax.swing.JScrollPane();
        tabelaMelhorTempoBateria2 = new javax.swing.JTable();
        painelMelhorTempoBateria3 = new javax.swing.JPanel();
        scrollPanelMTempo3 = new javax.swing.JScrollPane();
        tabelaMelhorTempoBateria3 = new javax.swing.JTable();
        painelMelhorTempoFinal = new javax.swing.JPanel();
        scrollPanelMTempoFunal = new javax.swing.JScrollPane();
        tabelaMelhorTempoFinal = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tempos");
        setPreferredSize(new java.awt.Dimension(1025, 500));
        setResizable(false);

        tabelaTempo1Bateria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Equipe", "1º Trecho", "2º Trecho", "3º Trecho", "Tempo Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaTempo1Bateria.getTableHeader().setReorderingAllowed(false);
        scrollPanetabelaTempo1Bateria.setViewportView(tabelaTempo1Bateria);

        javax.swing.GroupLayout guia1BateriaLayout = new javax.swing.GroupLayout(guia1Bateria);
        guia1Bateria.setLayout(guia1BateriaLayout);
        guia1BateriaLayout.setHorizontalGroup(
            guia1BateriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guia1BateriaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanetabelaTempo1Bateria, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
                .addContainerGap())
        );
        guia1BateriaLayout.setVerticalGroup(
            guia1BateriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guia1BateriaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanetabelaTempo1Bateria, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("1ª Bateria", guia1Bateria);

        tabelaTempo2Bateria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Equipe", "1º Trecho", "2º Trecho", "3º Trecho", "4º Trecho", "5º Trecho", "Tempo Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaTempo2Bateria.getTableHeader().setReorderingAllowed(false);
        scrollPanetabelaTempo2Bateria.setViewportView(tabelaTempo2Bateria);

        javax.swing.GroupLayout guia2BateriaLayout = new javax.swing.GroupLayout(guia2Bateria);
        guia2Bateria.setLayout(guia2BateriaLayout);
        guia2BateriaLayout.setHorizontalGroup(
            guia2BateriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guia2BateriaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanetabelaTempo2Bateria, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
                .addContainerGap())
        );
        guia2BateriaLayout.setVerticalGroup(
            guia2BateriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guia2BateriaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanetabelaTempo2Bateria, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("2ª Bateria", guia2Bateria);

        tabelaTempo3Bateria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Equipe", "1º Trecho", "2º Trecho", "3º Trecho", "4º Trecho", "5º Trecho", "Tempo Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaTempo3Bateria.getTableHeader().setReorderingAllowed(false);
        scrollPanetabelaTempo3Bateria.setViewportView(tabelaTempo3Bateria);

        javax.swing.GroupLayout guia3BateriaLayout = new javax.swing.GroupLayout(guia3Bateria);
        guia3Bateria.setLayout(guia3BateriaLayout);
        guia3BateriaLayout.setHorizontalGroup(
            guia3BateriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guia3BateriaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanetabelaTempo3Bateria, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
                .addContainerGap())
        );
        guia3BateriaLayout.setVerticalGroup(
            guia3BateriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guia3BateriaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanetabelaTempo3Bateria, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("3ª Bateria", guia3Bateria);

        tabelaTempoFinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Equipe", "1º Trecho", "2º Trecho", "3º Trecho", "4º Trecho", "5º Trecho", "6º Trecho", "7º Trecho", " Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaTempoFinal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaTempoFinal.getTableHeader().setReorderingAllowed(false);
        scrollPaneTempoFinal.setViewportView(tabelaTempoFinal);

        javax.swing.GroupLayout guiaTFinalLayout = new javax.swing.GroupLayout(guiaTFinal);
        guiaTFinal.setLayout(guiaTFinalLayout);
        guiaTFinalLayout.setHorizontalGroup(
            guiaTFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guiaTFinalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneTempoFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
                .addContainerGap())
        );
        guiaTFinalLayout.setVerticalGroup(
            guiaTFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guiaTFinalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneTempoFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Final", guiaTFinal);

        tabelaMelhorTempoBateria1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Trecho", "Código", "Equipe", "Tempo (s)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPanelMTempo1.setViewportView(tabelaMelhorTempoBateria1);

        javax.swing.GroupLayout painelMelhorTempoBateria1Layout = new javax.swing.GroupLayout(painelMelhorTempoBateria1);
        painelMelhorTempoBateria1.setLayout(painelMelhorTempoBateria1Layout);
        painelMelhorTempoBateria1Layout.setHorizontalGroup(
            painelMelhorTempoBateria1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMelhorTempoBateria1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanelMTempo1, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelMelhorTempoBateria1Layout.setVerticalGroup(
            painelMelhorTempoBateria1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMelhorTempoBateria1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanelMTempo1, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("1ª Bateria", painelMelhorTempoBateria1);

        tabelaMelhorTempoBateria2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Trecho", "Código", "Equipe", "Tempo (s)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPanelMTempo2.setViewportView(tabelaMelhorTempoBateria2);

        javax.swing.GroupLayout painelMelhorTempoBateria2Layout = new javax.swing.GroupLayout(painelMelhorTempoBateria2);
        painelMelhorTempoBateria2.setLayout(painelMelhorTempoBateria2Layout);
        painelMelhorTempoBateria2Layout.setHorizontalGroup(
            painelMelhorTempoBateria2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMelhorTempoBateria2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanelMTempo2, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelMelhorTempoBateria2Layout.setVerticalGroup(
            painelMelhorTempoBateria2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMelhorTempoBateria2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanelMTempo2, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("2ª Bateria", painelMelhorTempoBateria2);

        tabelaMelhorTempoBateria3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Trecho", "Código", "Equipe", "Tempo (s)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPanelMTempo3.setViewportView(tabelaMelhorTempoBateria3);

        javax.swing.GroupLayout painelMelhorTempoBateria3Layout = new javax.swing.GroupLayout(painelMelhorTempoBateria3);
        painelMelhorTempoBateria3.setLayout(painelMelhorTempoBateria3Layout);
        painelMelhorTempoBateria3Layout.setHorizontalGroup(
            painelMelhorTempoBateria3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMelhorTempoBateria3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanelMTempo3, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelMelhorTempoBateria3Layout.setVerticalGroup(
            painelMelhorTempoBateria3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMelhorTempoBateria3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanelMTempo3, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("3ª Bateria", painelMelhorTempoBateria3);

        tabelaMelhorTempoFinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Trecho", "Código", "Equipe", "Tempo (s)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPanelMTempoFunal.setViewportView(tabelaMelhorTempoFinal);

        javax.swing.GroupLayout painelMelhorTempoFinalLayout = new javax.swing.GroupLayout(painelMelhorTempoFinal);
        painelMelhorTempoFinal.setLayout(painelMelhorTempoFinalLayout);
        painelMelhorTempoFinalLayout.setHorizontalGroup(
            painelMelhorTempoFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMelhorTempoFinalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanelMTempoFunal, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelMelhorTempoFinalLayout.setVerticalGroup(
            painelMelhorTempoFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMelhorTempoFinalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanelMTempoFunal, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Final", painelMelhorTempoFinal);

        javax.swing.GroupLayout guiaMelhoresTemposLayout = new javax.swing.GroupLayout(guiaMelhoresTempos);
        guiaMelhoresTempos.setLayout(guiaMelhoresTemposLayout);
        guiaMelhoresTemposLayout.setHorizontalGroup(
            guiaMelhoresTemposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guiaMelhoresTemposLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        guiaMelhoresTemposLayout.setVerticalGroup(
            guiaMelhoresTemposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guiaMelhoresTemposLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Melhores Tempos", guiaMelhoresTempos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(TempoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TempoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TempoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TempoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TempoUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel guia1Bateria;
    private javax.swing.JPanel guia2Bateria;
    private javax.swing.JPanel guia3Bateria;
    private javax.swing.JPanel guiaMelhoresTempos;
    private javax.swing.JPanel guiaTFinal;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPanel painelMelhorTempoBateria1;
    private javax.swing.JPanel painelMelhorTempoBateria2;
    private javax.swing.JPanel painelMelhorTempoBateria3;
    private javax.swing.JPanel painelMelhorTempoFinal;
    private javax.swing.JScrollPane scrollPaneTempoFinal;
    private javax.swing.JScrollPane scrollPanelMTempo1;
    private javax.swing.JScrollPane scrollPanelMTempo2;
    private javax.swing.JScrollPane scrollPanelMTempo3;
    private javax.swing.JScrollPane scrollPanelMTempoFunal;
    private javax.swing.JScrollPane scrollPanetabelaTempo1Bateria;
    private javax.swing.JScrollPane scrollPanetabelaTempo2Bateria;
    private javax.swing.JScrollPane scrollPanetabelaTempo3Bateria;
    private javax.swing.JTable tabelaMelhorTempoBateria1;
    private javax.swing.JTable tabelaMelhorTempoBateria2;
    private javax.swing.JTable tabelaMelhorTempoBateria3;
    private javax.swing.JTable tabelaMelhorTempoFinal;
    private javax.swing.JTable tabelaTempo1Bateria;
    private javax.swing.JTable tabelaTempo2Bateria;
    private javax.swing.JTable tabelaTempo3Bateria;
    private javax.swing.JTable tabelaTempoFinal;
    // End of variables declaration//GEN-END:variables
}

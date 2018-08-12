/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import configuracoes.EstiloFrame;
import constantes.Bateria;
import constantes.Parametro;
import cora.Sprint;
import cora.Tempo;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Mateus Simões
 */
public class ExibicaoUI extends javax.swing.JFrame {
    private final JLabel [] tempos;
    private final JLabel [] textoCP;
    
    public ExibicaoUI() {
        initComponents();
        tempos = new JLabel[]{tempoCP1, tempoCP2, tempoCP3, tempoCP4, tempoCP5, tempoCP6, tempoCP7, tempoCP8, tempoCP9};
        textoCP = new JLabel[]{CP1, CP2, CP3, CP4, CP5, CP6, CP7, CP8, CP9};
        iniciarFrame();
        dimensoes();
    }
    
    private void iniciarFrame() {
        EstiloFrame estilo = new EstiloFrame();
        estilo.icone(this, "/imagens/exibicao.png");
    }
    
    private void dimensoes(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(screenSize);  
        double width = screenSize.getWidth(); 
        double height = screenSize.getHeight();
        tempoTotal.setFont(new Font("Tahoma", Font.BOLD, (int) (width / 5)));
        tempoTotal.setSize((int) (width - 400), (int) (height / 3));
        
        for (JLabel tempo : tempos) {
            tempo.setFont(new Font("Tahoma", Font.PLAIN, (int) (width / 50)));//67
            tempo.setPreferredSize(new Dimension((int) (width / 55), 0));
        }
        
        for(JLabel texto : textoCP){
            texto.setFont(new Font("Tahoma", Font.PLAIN, (int) (width / 50)));//67
            texto.setPreferredSize(new Dimension((int) (width / 55), 0));
        }
        
        try {
            Image img = ImageIO.read(getClass().getResource("/imagens/CoRA_Logo.png"));
            img = img.getScaledInstance((int) (width / 3), (int) (height / 6), java.awt.Image.SCALE_SMOOTH );
            ImageIcon imageIcon = new ImageIcon(img);
            labelIconeCoRA.setIcon(imageIcon);
        } catch (IOException ex) {
            Logger.getLogger(InserirEquipeUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void esconder(){
        if(!CoRAUI.isSprint()){
            for (JLabel tempo : tempos) {
                tempo.setText("--:--.--");
            }
        }

    }
    
    public void setTempos(JLabel tempos, int index) {
        this.tempos[index] = tempos;
    }
    
    public JLabel getTempo(int index) {
        return tempos[index];
    }
    
    public JLabel [] getTempos() {
        return this.tempos;
    }
    
    public void setTemposVisivel(boolean estado, int index) {
        tempos[index].setVisible(estado);
    }
    
    public void setTextoCP(JLabel textoCP, int index) {
        this.textoCP[index] = textoCP;
    }
    
    public void setTextoCP(String textoCP, int index) {
        this.textoCP[index].setText(textoCP);
    }
    
    public void setTextoCPVisivel(boolean estado, int index) {
        this.textoCP[index].setVisible(estado);
    }

    public void setLabelBateria(int bateria) {
        String texto = (bateria == Bateria.FINAL.getValue() ? "Final" : (bateria + 1) + "ª Bateria");
        if (bateria == Bateria.SPRINT.getValue()) texto = sprint();
        else this.textoCP[0].setText("1º Trecho");
        this.labelBateria.setText(texto);
    }

    public void setNomeEquipe(String nome) {
        this.nomeEquipe.setText(nome);
    }

    public JLabel getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(JLabel tempoTotal) {
        this.tempoTotal = tempoTotal;
    }
    
    private String sprint(){
        this.textoCP[0].setText("Velocidade");
        this.tempos[0].setText(String.valueOf(velocidade()) + " m/s");
        return  "Sprint";
    }
    
    public double velocidade(){
        String tempo = this.tempoTotal.getText();
        Sprint sprint = new Sprint();
        return sprint.velocidade(tempo);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tempoTotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nomeEquipe = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tempoCP1 = new javax.swing.JLabel();
        CP1 = new javax.swing.JLabel();
        CP2 = new javax.swing.JLabel();
        tempoCP2 = new javax.swing.JLabel();
        tempoCP3 = new javax.swing.JLabel();
        CP3 = new javax.swing.JLabel();
        CP4 = new javax.swing.JLabel();
        tempoCP4 = new javax.swing.JLabel();
        tempoCP5 = new javax.swing.JLabel();
        CP5 = new javax.swing.JLabel();
        tempoCP6 = new javax.swing.JLabel();
        CP6 = new javax.swing.JLabel();
        CP7 = new javax.swing.JLabel();
        tempoCP7 = new javax.swing.JLabel();
        tempoCP8 = new javax.swing.JLabel();
        CP8 = new javax.swing.JLabel();
        tempoCP9 = new javax.swing.JLabel();
        CP9 = new javax.swing.JLabel();
        labelBateria = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labelIconeCoRA = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Competição de Robôs Autônomos");

        tempoTotal.setFont(new java.awt.Font("Tahoma", 1, 350)); // NOI18N
        tempoTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoTotal.setText("00:00.00");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("Equipe");

        nomeEquipe.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        nomeEquipe.setText("NOME DA EQUIPE");

        tempoCP1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tempoCP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoCP1.setText("00:00.00");

        CP1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        CP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CP1.setText("1º Trecho");

        CP2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        CP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CP2.setText("2º Trecho");

        tempoCP2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tempoCP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoCP2.setText("00:00.00");

        tempoCP3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tempoCP3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoCP3.setText("00:00.00");

        CP3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        CP3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CP3.setText("3º Trecho");

        CP4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        CP4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CP4.setText("4º Trecho");

        tempoCP4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tempoCP4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoCP4.setText("00:00.00");

        tempoCP5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tempoCP5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoCP5.setText("00:00.00");

        CP5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        CP5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CP5.setText("5º Trecho");

        tempoCP6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tempoCP6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoCP6.setText("00:00.00");

        CP6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        CP6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CP6.setText("6º Trecho");

        CP7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        CP7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CP7.setText("7º Trecho");

        tempoCP7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tempoCP7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoCP7.setText("00:00.00");

        tempoCP8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tempoCP8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoCP8.setText("00:00.00");

        CP8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        CP8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CP8.setText("8º Trecho");

        tempoCP9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tempoCP9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoCP9.setText("00:00.00");

        CP9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        CP9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CP9.setText("9º Trecho");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tempoCP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CP2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tempoCP2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CP3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tempoCP3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CP4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tempoCP4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CP5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tempoCP5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CP6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tempoCP6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CP7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tempoCP7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CP8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tempoCP8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CP9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tempoCP9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CP9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tempoCP9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CP1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tempoCP1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CP2)
                            .addComponent(CP3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tempoCP2)
                            .addComponent(tempoCP3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CP4)
                            .addComponent(CP5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tempoCP4)
                            .addComponent(tempoCP5)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CP6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tempoCP6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CP7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tempoCP7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CP8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tempoCP8)))
                .addContainerGap())
        );

        labelBateria.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        labelBateria.setText("# Bateria");

        labelIconeCoRA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelIconeCoRA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelIconeCoRA, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelBateria)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nomeEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 1057, Short.MAX_VALUE))
                    .addComponent(tempoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tempoTotal)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nomeEquipe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelBateria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
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
            java.util.logging.Logger.getLogger(ExibicaoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExibicaoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExibicaoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExibicaoUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExibicaoUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CP1;
    private javax.swing.JLabel CP2;
    private javax.swing.JLabel CP3;
    private javax.swing.JLabel CP4;
    private javax.swing.JLabel CP5;
    private javax.swing.JLabel CP6;
    private javax.swing.JLabel CP7;
    private javax.swing.JLabel CP8;
    private javax.swing.JLabel CP9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelBateria;
    private javax.swing.JLabel labelIconeCoRA;
    private javax.swing.JLabel nomeEquipe;
    private javax.swing.JLabel tempoCP1;
    private javax.swing.JLabel tempoCP2;
    private javax.swing.JLabel tempoCP3;
    private javax.swing.JLabel tempoCP4;
    private javax.swing.JLabel tempoCP5;
    private javax.swing.JLabel tempoCP6;
    private javax.swing.JLabel tempoCP7;
    private javax.swing.JLabel tempoCP8;
    private javax.swing.JLabel tempoCP9;
    private javax.swing.JLabel tempoTotal;
    // End of variables declaration//GEN-END:variables
}

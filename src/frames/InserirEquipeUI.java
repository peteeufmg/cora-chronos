/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import configuracoes.Carregar;
import configuracoes.EstiloFrame;
import constantes.Fator;
import constantes.Parametro;
import cora.Arquivo;
import cora.Equipe;
import cora.Tempo;
import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mateus
 */
public class InserirEquipeUI extends javax.swing.JDialog{
    
    List<Equipe> listaEquipes;
    
    public InserirEquipeUI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public InserirEquipeUI(java.awt.Frame parent, boolean modal, List<Equipe> listaEquipes) {
        super(parent, modal);
        initComponents();
        this.listaEquipes = listaEquipes;
        inicializarLista();  
        iniciarFrame();
    }
    
    private void iniciarFrame() {
        EstiloFrame estilo = new EstiloFrame();
        estilo.icone(this, "/imagens/addEquipe.png");
        try {
            Image img = ImageIO.read(getClass().getResource("/imagens/CoRA_Logo.png"));
            img = img.getScaledInstance(labelIcone.getWidth(), labelIcone.getHeight(), java.awt.Image.SCALE_SMOOTH );
            ImageIcon imageIcon = new ImageIcon(img);
            labelIcone.setIcon(imageIcon);
        } catch (IOException ex) {
            Logger.getLogger(InserirEquipeUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getIndex(int codigo){
        int index = 0;
        int aux;
        for (Equipe equipe : listaEquipes) {
            aux = equipe.getCodigo();
            if(codigo == aux){
                index = listaEquipes.indexOf(equipe);
            }
        }
        return index; 
    }
    
    private double notaRelatorio(double relatorio){
        double maior;
        if(Fator.RELATORIO_CLASSIFICATORIA.getValue() >  Fator.RELATORIO_FINAL.getValue()){
            maior = Fator.RELATORIO_CLASSIFICATORIA.getValue();
        }
        else{
            maior = Fator.RELATORIO_FINAL.getValue();
        }
        
        if(relatorio > Fator.RELATORIO_CLASSIFICATORIA.getValue()){
            relatorio = maior;
        }
        return relatorio;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoDeBotaoCalouro = new javax.swing.ButtonGroup();
        painelGeralCadastroEquipe = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        campoNomeEquipe = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        calouroSIM = new javax.swing.JRadioButton();
        calouroNAO = new javax.swing.JRadioButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        notaRelatorio = new javax.swing.JTextField();
        botaoInserirEquipe = new javax.swing.JButton();
        painelListaDeEquipes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaEquipes = new javax.swing.JTable();
        labelIcone = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        botaoSalvar = new javax.swing.JButton();
        botaoResetar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento de Equipes");
        setResizable(false);

        painelGeralCadastroEquipe.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cadastrar Equipe"));
        painelGeralCadastroEquipe.setToolTipText("");

        jLabel1.setText("Nome da Equipe");

        campoNomeEquipe.setToolTipText("Nome da Equipe");

        jLabel6.setText("Possui Calouro?");

        grupoDeBotaoCalouro.add(calouroSIM);
        calouroSIM.setText("SIM");
        calouroSIM.setToolTipText("A Equipe Possui Calouro?");

        grupoDeBotaoCalouro.add(calouroNAO);
        calouroNAO.setSelected(true);
        calouroNAO.setText("NÃO");
        calouroNAO.setToolTipText("A Equipe Possui Calouro?");

        jLabel3.setText("Relatório");

        notaRelatorio.setToolTipText("Pontuação Obtida no Relatório");

        botaoInserirEquipe.setText("Inserir");
        botaoInserirEquipe.setToolTipText("Inserir Nova Equipe");
        botaoInserirEquipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoInserirEquipeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelGeralCadastroEquipeLayout = new javax.swing.GroupLayout(painelGeralCadastroEquipe);
        painelGeralCadastroEquipe.setLayout(painelGeralCadastroEquipeLayout);
        painelGeralCadastroEquipeLayout.setHorizontalGroup(
            painelGeralCadastroEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(painelGeralCadastroEquipeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelGeralCadastroEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelGeralCadastroEquipeLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(campoNomeEquipe))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelGeralCadastroEquipeLayout.createSequentialGroup()
                        .addGroup(painelGeralCadastroEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addGroup(painelGeralCadastroEquipeLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(notaRelatorio)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(painelGeralCadastroEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelGeralCadastroEquipeLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(calouroSIM)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(calouroNAO))
                            .addComponent(jLabel6)))
                    .addGroup(painelGeralCadastroEquipeLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(botaoInserirEquipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelGeralCadastroEquipeLayout.setVerticalGroup(
            painelGeralCadastroEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelGeralCadastroEquipeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelGeralCadastroEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoNomeEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelGeralCadastroEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelGeralCadastroEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calouroSIM)
                    .addComponent(calouroNAO)
                    .addComponent(notaRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoInserirEquipe, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2))
        );

        painelListaDeEquipes.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lista de Equipes"));

        tabelaEquipes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Equipe", "Relatório", "Calouro", "Finalista", "Penalidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaEquipes);

        javax.swing.GroupLayout painelListaDeEquipesLayout = new javax.swing.GroupLayout(painelListaDeEquipes);
        painelListaDeEquipes.setLayout(painelListaDeEquipesLayout);
        painelListaDeEquipesLayout.setHorizontalGroup(
            painelListaDeEquipesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelListaDeEquipesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelListaDeEquipesLayout.setVerticalGroup(
            painelListaDeEquipesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelListaDeEquipesLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lista de Equipes"));

        botaoSalvar.setText("Salvar");
        botaoSalvar.setToolTipText("Salvar Alterações na Lista de Equipes");
        botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarActionPerformed(evt);
            }
        });

        botaoResetar.setText("Resetar");
        botaoResetar.setToolTipText("Resetar Informações na Lista de Equipes");
        botaoResetar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoResetarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoResetar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoSalvar)
                    .addComponent(botaoResetar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelIcone, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                    .addComponent(painelGeralCadastroEquipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(painelListaDeEquipes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(painelGeralCadastroEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelIcone, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(painelListaDeEquipes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoInserirEquipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoInserirEquipeActionPerformed
        String nomeEquipe = campoNomeEquipe.getText();
        int maiorCodigo = 0;
        if(!"".equals(nomeEquipe)){
            boolean existe = false;
            
            for(Equipe equipe: listaEquipes){
                if(nomeEquipe.equals(equipe.getNome())){
                    existe = true;
                }
                if(maiorCodigo < equipe.getCodigo()){
                    maiorCodigo = equipe.getCodigo();
                }
            }
            if(!existe){
                int quantidadeEquipes = listaEquipes.size();
                if(quantidadeEquipes < Parametro.NUMERO_MAXIMO_EQUIPES.getValue()){
                    int codigo = maiorCodigo + 1;
                    boolean calouro = calouroSIM.isSelected();
                    double relatorio = ("".equals(notaRelatorio.getText())) ? 0 : Double.parseDouble(notaRelatorio.getText());
                    
                    relatorio = notaRelatorio(relatorio);
                    
                    Equipe equipe = new Equipe(codigo, nomeEquipe, relatorio, calouro, false, 0);
                    Tempo tempo = new Tempo();
                    tempo.resetaTempo(equipe);
                    listaEquipes.add(equipe);
                    Arquivo arquivo = new Arquivo();
                    arquivo.salvarListaEquipes(listaEquipes);
            
                    System.out.println("Equipe " + nomeEquipe + " Inscrita.");
                    inicializarLista();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Já Existem " + Parametro.NUMERO_MAXIMO_EQUIPES.getValue() + " Equipes Inscritas!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "Equipe " + nomeEquipe + " Já Está Inscrita!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
            }

        }
        else{
            JOptionPane.showMessageDialog(null, "Insira o Nome da Equipe!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE); 
        }

    }//GEN-LAST:event_botaoInserirEquipeActionPerformed

    private void botaoResetarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoResetarActionPerformed
        inicializarLista();  
    }//GEN-LAST:event_botaoResetarActionPerformed

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        Arquivo arquivo = new Arquivo();
        DefaultTableModel modelo = (DefaultTableModel) tabelaEquipes.getModel();
        int index;
        int codigo;
        double relatorio;
        Equipe equipe;
        for(int row = 0; row < tabelaEquipes.getRowCount(); row++){
            codigo = (int) modelo.getValueAt(row, 0);
            index = getIndex(codigo);
            equipe = listaEquipes.get(index);     
            equipe.setNome((String) modelo.getValueAt(row, 1));
            
            relatorio = notaRelatorio((double) modelo.getValueAt(row, 2));
            
            equipe.setRelatorio(relatorio);
            equipe.setCalouro((boolean) modelo.getValueAt(row, 3));
            equipe.setClassificada((boolean) modelo.getValueAt(row, 4));
            equipe.setPenalidade((double) modelo.getValueAt(row, 5));
            
            listaEquipes.set(index, equipe);
        }
        arquivo.salvarListaEquipes(listaEquipes);
        inicializarLista();
    }//GEN-LAST:event_botaoSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(InserirEquipeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InserirEquipeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InserirEquipeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InserirEquipeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                InserirEquipeUI dialog = new InserirEquipeUI(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    private void inicializarLista(){
        Carregar carregar = new Carregar();
        carregar.tabelaEquipes(tabelaEquipes, listaEquipes);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoInserirEquipe;
    private javax.swing.JButton botaoResetar;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JRadioButton calouroNAO;
    private javax.swing.JRadioButton calouroSIM;
    private javax.swing.JTextField campoNomeEquipe;
    private javax.swing.ButtonGroup grupoDeBotaoCalouro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel labelIcone;
    private javax.swing.JTextField notaRelatorio;
    private javax.swing.JPanel painelGeralCadastroEquipe;
    private javax.swing.JPanel painelListaDeEquipes;
    private javax.swing.JTable tabelaEquipes;
    // End of variables declaration//GEN-END:variables
}

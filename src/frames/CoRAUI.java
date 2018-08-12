package frames;

import com.itextpdf.text.DocumentException;
import constantes.Bateria;
import constantes.Parametro;
import cora.Equipe;
import configuracoes.Carregar;
import configuracoes.EstiloFrame;
import constantes.CheckPoint;
import constantes.Diretorio;
import constantes.Pista;
import cora.Pontuar;
import cora.Tempo;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import relatorio.Relatorio;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import serial.Comunicacao;

public class CoRAUI extends javax.swing.JFrame {
    private final Carregar carregar = new Carregar();
    private final EstiloFrame estilo = new EstiloFrame();
    private static Comunicacao comunicacao = new Comunicacao();
    private static MonitorSerialUI monitorSerial = new MonitorSerialUI();
    
    ExibicaoUI exibicaoUI = new ExibicaoUI();
    
    private List<Equipe> listaEquipes;
    private static JButton [] sensores; 
    private static JButton [] statusSensor;
    private static boolean sensorAtivado[] = new boolean[Parametro.NUMERO_MAXIMO_CHECK_POINTS.getValue()];
    private int [][] ordem;
    
    private static boolean conectado;
    private static boolean contar;
    private static boolean sprint;
    private int checkpoint;
    private boolean largou;
    private boolean tentou;
    
    long agora;
    long dif;
    long minutos;
    long segundos;
    long centesimos;

    private static long startTime;
    private long limite = Parametro.TEMPO_LIMITE.getValue();
    private DecimalFormat timeFormatter = new DecimalFormat("00");  
    
    public CoRAUI() {
        initComponents();
    }
    
    public CoRAUI(List<Equipe> listaEquipes) {
        initComponents();
        sensores = new JButton[]{botaoSensor0, botaoSensor1, botaoSensor2, botaoSensor3, botaoSensor4, 
            botaoSensor5, botaoSensor6, botaoSensor7, botaoSensor8, botaoSensor9};
        
        statusSensor = new JButton[]{sensor0, sensor1, sensor2, sensor3, sensor4, 
            sensor5, sensor6, sensor7, sensor8, sensor9};
        
        this.listaEquipes = listaEquipes;
        inicializaFrame();
        conectado = false;
        contar = false;
        largou = false;
        sprint = false;
        botaoContagem.setEnabled(false);
    }

    public static boolean isConectado() {
        return conectado;
    }

    public static void setConectado(boolean conectado) {
        CoRAUI.conectado = conectado;
    }
    
    public static boolean isSprint() {
        return sprint;
    }

    public static void setSprint(boolean sprint) {
        CoRAUI.sprint = sprint;
    }

    public static MonitorSerialUI getMonitorSerial() {
        return monitorSerial;
    }

    public static void setMonitorSerial(MonitorSerialUI monitorSerial) {
        CoRAUI.monitorSerial = monitorSerial;
    }
    
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            agora = System.currentTimeMillis();
            dif = agora - startTime;
            agora = dif;

            minutos = dif / (60 * 1000);
            dif = Math.round(dif % (60 * 1000));
            segundos = dif / 1000;
            dif = Math.round(dif % 1000);
            centesimos = dif / 10;

            labelTempo.setText(timeFormatter.format(minutos) + ":"
                + timeFormatter.format(segundos) + ":"
                + timeFormatter.format(centesimos));
            
            exibicaoUI.getTempoTotal().setText(timeFormatter.format(minutos) + ":"
                + timeFormatter.format(segundos) + "."
                + timeFormatter.format(centesimos));
                    
            if (agora > limite) {
                pararContagem(false);
            }
        }
    });
    
    public static void sensorStatus(boolean[] status) {
        EstiloFrame estilo = new EstiloFrame();
        for(int i = 0; i < status.length; i++){
            if(!sensorAtivado[i]){
                if(status[i]){
                    estilo.botao(statusSensor[i], "/imagens/ledVerde.png", 10, 15);
                }
                else{
                    estilo.botao(statusSensor[i], "/imagens/ledVermelho.png", 10, 15);
                }
            }
        }
    }
    
    public void sensorAtivado(int sensor){
        if(contar){
            if(largou && sensor == ordem[selecionarBateria.getSelectedIndex()][checkpoint]){            
                carregar.updateTempoEquipe(tabelaTempoCheckpoints, timeFormatter.format(minutos), timeFormatter.format(segundos), 
                        timeFormatter.format(centesimos), selecionarBateria.getSelectedIndex(), checkpoint);
                
                exibicaoUI.getTempo(checkpoint - 1).setVisible(true);
                if (!sprint) exibicaoUI.getTempo(checkpoint - 1).setText(timeFormatter.format(minutos) + ":" + timeFormatter.format(segundos) + "." + timeFormatter.format(centesimos)) ;
                passou();
                
                if(sensor == CheckPoint.CHEGADA.getValue()){
                    pararContagem(true);
                }
                
                else{
                    checkpoint++;
                }
            }
            else if(!largou && sensor == CheckPoint.LARGADA.getValue()){
                largou = true;
            
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        startTime = System.currentTimeMillis(); 
                        timer.start();
                    }
                });    
                
                passou();
                
                carregar.updateTempoEquipe(tabelaTempoCheckpoints, timeFormatter.format(0), timeFormatter.format(0), 
                        timeFormatter.format(0), selecionarBateria.getSelectedIndex(), checkpoint);
                statusEquipe.setText("EM PERCURSO");
                statusEquipe.setForeground(Color.BLACK);
                checkpoint++;
            }
        }
    }
    
    private void passou(){
        estilo.botao(sensores[checkpoint], "/imagens/bandeira_completou.png", 20, 30);  
        sensorAtivado[checkpoint] = true;
    }
    
        
    private void iniciarContagem(){
        if(!tentou){
            DefaultTableModel model = (DefaultTableModel) tabelaTempoCheckpoints.getModel();  
            model.setNumRows(0);
            tabelaTempoCheckpoints.setModel(model);
        
            habilitar(false);
        
            contar = true;
            largou = false;
        
            checkpoint = 0;
            ordem = Pista.getOrdem();
        
            botaoContagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botaoVermelho.png")));
        
            statusEquipe.setText("EM ESPERA");
            statusEquipe.setForeground(Color.BLACK);
        
            resetaTimer();
            
            exibicaoUI.esconder();
        }else{
            JOptionPane.showMessageDialog(this, selecionarEquipe.getSelectedItem() + " Já Realizou a Tentativa", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE, null);
        }

    }
    
    private void pararContagem(boolean completou){
        if(largou){
            timer.stop();
        
            Equipe equipe = getEquipeSelecionada();
            int bateria = selecionarBateria.getSelectedIndex();
        
            if(completou){
                equipe.setCompletou(true, bateria);
                statusEquipe.setText("COMPLETOU");
                statusEquipe.setForeground(new Color(0,204,0));
            }
            else{
                statusEquipe.setText("NÃO COMPLETOU");
                statusEquipe.setForeground(Color.RED);
                penalidade();
            }
        
            DefaultTableModel modelo = (DefaultTableModel) tabelaTempoCheckpoints.getModel();  
            labelTempo.setText((String) modelo.getValueAt(tabelaTempoCheckpoints.getRowCount() - 1, 1));
            exibicaoUI.getTempoTotal().setText((String) modelo.getValueAt(tabelaTempoCheckpoints.getRowCount() - 1, 1));
            
            Tempo tempo = new Tempo();
            tempo.salvar(equipe, tabelaTempoCheckpoints, labelTempo, bateria, selecionarTentativa.getSelectedIndex() + 1);
            
            if (!sprint) {
                JOptionPane.showMessageDialog(this, selecionarEquipe.getSelectedItem() +
                    " Terminou a " + selecionarBateria.getSelectedItem() + ".",
                    "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (sprint){
                exibicaoUI.getTempo(0).setText(String.valueOf(exibicaoUI.velocidade()) + " m/s");
                
                String t = String.valueOf(exibicaoUI.getTempoTotal());
                t = t.replace('.', ':');
                double te = Double.parseDouble(t);
                equipe.setTempoSprint(te);
                
                JOptionPane.showMessageDialog(this, selecionarEquipe.getSelectedItem() +
                    " Terminou o Sprint!",
                    "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            }
            tentou = true;   
        }
        
        habilitar(true);
        contar = false;
        botaoContagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botaoVerde.png")));  
            //updateListaTentativas();
        
        resetarSensores();
        botoes();
    }
    
    private void penalidade(){
        int bateria = selecionarBateria.getSelectedIndex();
        while(ordem[bateria][checkpoint] != CheckPoint.CHEGADA.getValue()){
            carregar.updateTempoEquipe(tabelaTempoCheckpoints, timeFormatter.format(99), timeFormatter.format(99), timeFormatter.format(99), bateria, checkpoint);
            sensorAtivado[checkpoint] = false;
            estilo.botao(sensores[checkpoint], "/imagens/bandeira_vermelha.png", 20, 30);  
            checkpoint++;
        }
        estilo.botao(sensores[checkpoint], "/imagens/bandeira_vermelha.png", 20, 30);  
        
        carregar.updateTempoEquipe(tabelaTempoCheckpoints, timeFormatter.format(99), timeFormatter.format(99), timeFormatter.format(99), bateria, checkpoint);    
        
        labelTempo.setText(timeFormatter.format(99) + ":"
                + timeFormatter.format(99) + ":"
                + timeFormatter.format(99));
    }
    
    private void inicializaFrame(){
        updateListaBateria();
        estilo.icone(this, "/imagens/icone.png");
        
        estilo.botao(botaoAddEquipe, "/imagens/addEquipe.png");
        estilo.botao(botaoExibicao, "/imagens/exibicao.png");
        estilo.botao(botaoMonitorSerial, "/imagens/monitorSerial.png");
        estilo.botao(botaoTabelaPontuacao, "/imagens/pontuacao.png");
        estilo.botao(botaoTabelaTempo, "/imagens/tempo.png");
        estilo.botao(botaoPdf, "/imagens/pdf.png");
        estilo.botao(botaoNetwork, "/imagens/network.png");
        estilo.botao(botaoConfiguracoes, "/imagens/configuracao.png");
        estilo.botao(botaoSortear, "/imagens/dados.png");
        
        resetarSensores();
    }
    
    private void botoes(){
        int bateria = selecionarBateria.getSelectedIndex();
        int trechos = Parametro.getTrechos(bateria);
        
        for(int i = 0; i <= trechos; i++){
            sensores[i].setVisible(true);
        }
        for(int i = trechos + 1; i < Parametro.NUMERO_MAXIMO_CHECK_POINTS.getValue(); i++){
              sensores[i].setVisible(false);
        }
    }
    
    private void habilitar(boolean estado){
        selecionarBateria.setEnabled(estado);
        selecionarEquipe.setEnabled(estado);
        selecionarTentativa.setEnabled(estado);
        botaoConectar.setEnabled(estado);
        for(int i = 0; i < barraferramentas.getComponentCount(); i++){
            barraferramentas.getComponentAtIndex(i).setEnabled(estado);
        }
        botaoMonitorSerial.setEnabled(true);
        botaoExibicao.setEnabled(true);
    }
    
    private void resetaTimer(){
        timer.stop();
        labelTempo.setText("00:00:00");
        
        exibicaoUI.getTempoTotal().setText("00:00.00");
        if(!sprint){
            for (JLabel tempo : exibicaoUI.getTempos()) {
                tempo.setText("00:00.00");
            }
        }
    }
    
    private void resetarSensores(){
        for(int i = 0; i < sensorAtivado.length; i++){
            sensorAtivado[i] = false;
        }
        
        for(int i = 0; i < Parametro.NUMERO_MAXIMO_CHECK_POINTS.getValue(); i++){
            estilo.botao(statusSensor[i], "/imagens/ledAmarelo.png", 10, 15);
        }
    }
    
    private void updateListaBateria(){
        int bateria = selecionarBateria.getSelectedIndex();
        
        sprint = (bateria == Bateria.SPRINT.getValue());
        
        selecionarEquipe.removeAllItems();
        if(bateria == Bateria.FINAL.getValue()){
            listaEquipes.stream().filter((equipe) -> (equipe.isClassificada())).forEach((equipe) -> {
                selecionarEquipe.addItem(equipe.getNome());
            });
        }      
        else{
            listaEquipes.stream().forEach((equipe) -> {
                selecionarEquipe.addItem(equipe.getNome());
            });
        }
    }
    
    private void updateListaEquipe(){
        if(listaEquipes.size() > 0){
            int bateria = selecionarBateria.getSelectedIndex();
            int tentativas = (bateria == Bateria.FINAL.getValue() ? Parametro.TENTATIVAS_FINAL.getValue() : Parametro.TENTATIVAS_CLASSIFICATORIA.getValue());       
            if (sprint) tentativas = 1;
            selecionarTentativa.removeAllItems();
            for(int i = 1; i <= tentativas; i++){
                selecionarTentativa.addItem(i);
            }  
        }
    }
    
    private void updateListaTentativas(){
        if(listaEquipes.size() > 0){
            Equipe equipe = getEquipeSelecionada();
            int bateria = selecionarBateria.getSelectedIndex();
            int tentativa = selecionarTentativa.getSelectedIndex() + 1;
        
            tentou = carregar.tabelaTempoEquipe(tabelaTempoCheckpoints, sensores, labelTempo, statusEquipe, exibicaoUI, equipe, bateria, tentativa);
        
            updateExibicao();
        }
    }
    
    private void updateExibicao(){
        int bateria = selecionarBateria.getSelectedIndex();
        int trechos = Parametro.getTrechos(bateria);
        for(int i = 0; i < trechos; i++){       
            exibicaoUI.setTemposVisivel(true, i);
            exibicaoUI.setTextoCPVisivel(true, i);
        }
        
        for(int i = trechos; i < Parametro.NUMERO_MAXIMO_TRECHOS.getValue(); i++){
            exibicaoUI.setTemposVisivel(false, i);
            exibicaoUI.setTextoCPVisivel(false, i);
        }
        
        exibicaoUI.setNomeEquipe((String) selecionarEquipe.getSelectedItem());
        exibicaoUI.setLabelBateria(selecionarBateria.getSelectedIndex()); 
    }
    
    public void conexao(){
        if(!conectado){         
            conectado = comunicacao.initialize(this);
        
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
                }
            };
            thread.start();
            
            if(conectado){
                statusComunicacao.setText("CONECTADO");
                botaoConectar.setText("Desconectar");
                statusComunicacao.setForeground(new Color(0,204,0));
            }
            else{
                statusComunicacao.setText("ERRO");
                botaoConectar.setText("Conectar");
                statusComunicacao.setForeground(Color.RED);
            }    
        }
        
        else{
            comunicacao.close();
            conectado = false;
            statusComunicacao.setText("DESCONECTADO");
            botaoConectar.setText("Conectar");
            statusComunicacao.setForeground(Color.BLACK);
        }
        
        botaoContagem.setEnabled(conectado);
    }
    
    private Equipe getEquipeSelecionada(){
        int index = 0;
        String nome = (String) selecionarEquipe.getSelectedItem();
        String nome_aux;
        for (Equipe equipe : listaEquipes) {
            nome_aux = equipe.getNome();
            if(nome == null ? nome_aux == null : nome.equals(nome_aux)){
                index = listaEquipes.indexOf(equipe);
            }
        }
        return listaEquipes.get(index);
    }
    
    private void botaoPressionado(int botao){
        if(contar){
            if(ordem[selecionarBateria.getSelectedIndex()][botao] == ordem[selecionarBateria.getSelectedIndex()][checkpoint]){
                sensorAtivado(ordem[selecionarBateria.getSelectedIndex()][botao]);
            }
        }
    }
    
    private void salvarRelatorioParcial(){
        PDFDir.setDialogTitle("Salvar como");    
        int userSelection = PDFDir.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String caminho = PDFDir.getSelectedFile().getAbsolutePath();
            int i = caminho.length();
            if(caminho.charAt(i - 4) == '.' && caminho.charAt(i - 3) == 'p' &&  caminho.charAt(i - 2) == 'd' && caminho.charAt(i - 1) == 'f'){
                Diretorio.RELATORIO.setValue(caminho);
            }else{
                Diretorio.RELATORIO.setValue(caminho.concat(".pdf"));
            }

            Tempo tempo = new Tempo();
            tempo.resetaMelhorTempo();
            tempo.arquivos(listaEquipes);
        
            Pontuar pontuar = new Pontuar();
        
            for(int index = 0; index < listaEquipes.size(); index++){
                pontuar.calcularPontos(listaEquipes, index, Bateria.CLASSIFICATORIA.getValue());
                pontuar.calcularPontos(listaEquipes, index, Bateria.FINAL.getValue());
            } 
        
            Collections.sort(listaEquipes);
            Relatorio relatorio = new Relatorio();
            try {
                relatorio.gerarPDFParciais(listaEquipes);
            } catch (DocumentException | IOException ex) {
                Logger.getLogger(CoRAUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void salvarRelatorioFinal(){
        PDFDir.setDialogTitle("Salvar como");    
        int userSelection = PDFDir.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String caminho = PDFDir.getSelectedFile().getAbsolutePath();
            int i = caminho.length();
            if(caminho.charAt(i - 4) == '.' && caminho.charAt(i - 3) == 'p' &&  caminho.charAt(i - 2) == 'd' && caminho.charAt(i - 1) == 'f'){
                Diretorio.RELATORIO.setValue(caminho);
            }else{
                Diretorio.RELATORIO.setValue(caminho.concat(".pdf"));
            }

            Tempo tempo = new Tempo();
            tempo.resetaMelhorTempo();
            tempo.arquivos(listaEquipes);
        
            Pontuar pontuar = new Pontuar();
        
            for(int index = 0; index < listaEquipes.size(); index++){
                pontuar.calcularPontos(listaEquipes, index, Bateria.CLASSIFICATORIA.getValue());
                pontuar.calcularPontos(listaEquipes, index, Bateria.FINAL.getValue());
            } 
        
            Collections.sort(listaEquipes);
            Relatorio relatorio = new Relatorio();
            try {
                relatorio.gerarRelatorio(listaEquipes);
            } catch (DocumentException | IOException ex) {
                Logger.getLogger(CoRAUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PDFDir = new javax.swing.JFileChooser();
        barraferramentas = new javax.swing.JToolBar();
        botaoAddEquipe = new javax.swing.JButton();
        botaoSortear = new javax.swing.JButton();
        botaoExibicao = new javax.swing.JButton();
        botaoTabelaPontuacao = new javax.swing.JButton();
        botaoTabelaTempo = new javax.swing.JButton();
        botaoNetwork = new javax.swing.JButton();
        botaoMonitorSerial = new javax.swing.JButton();
        botaoPdf = new javax.swing.JButton();
        botaoConfiguracoes = new javax.swing.JButton();
        painelGeral = new javax.swing.JPanel();
        painelConfiguraCompeticao = new javax.swing.JPanel();
        labelBateria = new javax.swing.JLabel();
        selecionarBateria = new javax.swing.JComboBox();
        selecionarEquipe = new javax.swing.JComboBox();
        labelEquipe = new javax.swing.JLabel();
        labelTentativa = new javax.swing.JLabel();
        selecionarTentativa = new javax.swing.JComboBox();
        labelStatus = new javax.swing.JLabel();
        statusEquipe = new javax.swing.JLabel();
        botaoContagem = new javax.swing.JButton();
        painelStatusComunicacao = new javax.swing.JPanel();
        statusComunicacao = new javax.swing.JLabel();
        botaoConectar = new javax.swing.JButton();
        labelTempo = new javax.swing.JLabel();
        painelBandeiras = new javax.swing.JPanel();
        botaoSensor0 = new javax.swing.JButton();
        botaoSensor1 = new javax.swing.JButton();
        botaoSensor2 = new javax.swing.JButton();
        botaoSensor3 = new javax.swing.JButton();
        botaoSensor4 = new javax.swing.JButton();
        botaoSensor5 = new javax.swing.JButton();
        botaoSensor6 = new javax.swing.JButton();
        botaoSensor7 = new javax.swing.JButton();
        botaoSensor8 = new javax.swing.JButton();
        botaoSensor9 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaTempoCheckpoints = new javax.swing.JTable();
        barraStatusSensores = new javax.swing.JToolBar();
        sensor0 = new javax.swing.JButton();
        sensor1 = new javax.swing.JButton();
        sensor2 = new javax.swing.JButton();
        sensor3 = new javax.swing.JButton();
        sensor4 = new javax.swing.JButton();
        sensor5 = new javax.swing.JButton();
        sensor6 = new javax.swing.JButton();
        sensor7 = new javax.swing.JButton();
        sensor8 = new javax.swing.JButton();
        sensor9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("CoRA Chronos");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        barraferramentas.setFloatable(false);
        barraferramentas.setRollover(true);
        barraferramentas.setBorderPainted(false);

        botaoAddEquipe.setToolTipText("Gerenciar Equipes");
        botaoAddEquipe.setContentAreaFilled(false);
        botaoAddEquipe.setFocusable(false);
        botaoAddEquipe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoAddEquipe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoAddEquipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAddEquipeActionPerformed(evt);
            }
        });
        barraferramentas.add(botaoAddEquipe);

        botaoSortear.setToolTipText("Sortear Ordem das Equipes");
        botaoSortear.setContentAreaFilled(false);
        botaoSortear.setFocusable(false);
        botaoSortear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoSortear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoSortear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSortearActionPerformed(evt);
            }
        });
        barraferramentas.add(botaoSortear);

        botaoExibicao.setToolTipText("Tela de Exibição");
        botaoExibicao.setContentAreaFilled(false);
        botaoExibicao.setFocusable(false);
        botaoExibicao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoExibicao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoExibicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoExibicaoActionPerformed(evt);
            }
        });
        barraferramentas.add(botaoExibicao);

        botaoTabelaPontuacao.setToolTipText("Exibir Tabela de Pontuação");
        botaoTabelaPontuacao.setContentAreaFilled(false);
        botaoTabelaPontuacao.setFocusable(false);
        botaoTabelaPontuacao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoTabelaPontuacao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoTabelaPontuacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoTabelaPontuacaoActionPerformed(evt);
            }
        });
        barraferramentas.add(botaoTabelaPontuacao);

        botaoTabelaTempo.setToolTipText("Exibir Tabela de Tempos");
        botaoTabelaTempo.setContentAreaFilled(false);
        botaoTabelaTempo.setFocusable(false);
        botaoTabelaTempo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoTabelaTempo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoTabelaTempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoTabelaTempoActionPerformed(evt);
            }
        });
        barraferramentas.add(botaoTabelaTempo);

        botaoNetwork.setToolTipText("Gerenciar Rede");
        botaoNetwork.setContentAreaFilled(false);
        botaoNetwork.setFocusable(false);
        botaoNetwork.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoNetwork.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoNetwork.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNetworkActionPerformed(evt);
            }
        });
        barraferramentas.add(botaoNetwork);

        botaoMonitorSerial.setToolTipText("Monitor Serial");
        botaoMonitorSerial.setContentAreaFilled(false);
        botaoMonitorSerial.setFocusable(false);
        botaoMonitorSerial.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoMonitorSerial.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoMonitorSerial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMonitorSerialActionPerformed(evt);
            }
        });
        barraferramentas.add(botaoMonitorSerial);

        botaoPdf.setToolTipText("Criar Relatório");
        botaoPdf.setContentAreaFilled(false);
        botaoPdf.setFocusable(false);
        botaoPdf.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoPdf.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPdfActionPerformed(evt);
            }
        });
        barraferramentas.add(botaoPdf);

        botaoConfiguracoes.setToolTipText("Configurações");
        botaoConfiguracoes.setContentAreaFilled(false);
        botaoConfiguracoes.setFocusable(false);
        botaoConfiguracoes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoConfiguracoes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoConfiguracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConfiguracoesActionPerformed(evt);
            }
        });
        barraferramentas.add(botaoConfiguracoes);

        painelGeral.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Painel de Controle"));

        painelConfiguraCompeticao.setBorder(javax.swing.BorderFactory.createTitledBorder("CoRA"));

        labelBateria.setText("Passagem");

        selecionarBateria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1ª Bateria", "2ª Bateria", "3ª Bateria", "Final", "Sprint" }));
        selecionarBateria.setToolTipText("Selecionar Bateria");
        selecionarBateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecionarBateriaActionPerformed(evt);
            }
        });

        selecionarEquipe.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Equipe" }));
        selecionarEquipe.setToolTipText("Selecionar Equipe");
        selecionarEquipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecionarEquipeActionPerformed(evt);
            }
        });

        labelEquipe.setText("Equipe");

        labelTentativa.setText("Tentativa");

        selecionarTentativa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1" }));
        selecionarTentativa.setToolTipText("Selecionar Tentativa");
        selecionarTentativa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecionarTentativaActionPerformed(evt);
            }
        });

        labelStatus.setText("Status");

        statusEquipe.setText("STATUS");

        botaoContagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botaoVerde.png"))); // NOI18N
        botaoContagem.setToolTipText("Iniciar / Parar Cronometragem");
        botaoContagem.setContentAreaFilled(false);
        botaoContagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoContagemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelConfiguraCompeticaoLayout = new javax.swing.GroupLayout(painelConfiguraCompeticao);
        painelConfiguraCompeticao.setLayout(painelConfiguraCompeticaoLayout);
        painelConfiguraCompeticaoLayout.setHorizontalGroup(
            painelConfiguraCompeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelConfiguraCompeticaoLayout.createSequentialGroup()
                .addGroup(painelConfiguraCompeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelConfiguraCompeticaoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(painelConfiguraCompeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTentativa)
                            .addComponent(labelStatus)
                            .addGroup(painelConfiguraCompeticaoLayout.createSequentialGroup()
                                .addGroup(painelConfiguraCompeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelEquipe)
                                    .addComponent(labelBateria))
                                .addGap(24, 24, 24)
                                .addGroup(painelConfiguraCompeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(selecionarBateria, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(statusEquipe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(selecionarEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selecionarTentativa, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(painelConfiguraCompeticaoLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(botaoContagem)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelConfiguraCompeticaoLayout.setVerticalGroup(
            painelConfiguraCompeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelConfiguraCompeticaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelConfiguraCompeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBateria)
                    .addComponent(selecionarBateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelConfiguraCompeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selecionarEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEquipe))
                .addGap(18, 18, 18)
                .addGroup(painelConfiguraCompeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTentativa)
                    .addComponent(selecionarTentativa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelConfiguraCompeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusEquipe)
                    .addComponent(labelStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botaoContagem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelStatusComunicacao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Comunicação"));

        statusComunicacao.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        statusComunicacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusComunicacao.setText("DESCONECTADO");

        botaoConectar.setText("Conectar");
        botaoConectar.setToolTipText("Iniciar Conexão Serial");
        botaoConectar.setMaximumSize(new java.awt.Dimension(110, 25));
        botaoConectar.setMinimumSize(new java.awt.Dimension(110, 25));
        botaoConectar.setPreferredSize(new java.awt.Dimension(110, 25));
        botaoConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConectarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelStatusComunicacaoLayout = new javax.swing.GroupLayout(painelStatusComunicacao);
        painelStatusComunicacao.setLayout(painelStatusComunicacaoLayout);
        painelStatusComunicacaoLayout.setHorizontalGroup(
            painelStatusComunicacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusComunicacao, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(painelStatusComunicacaoLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(botaoConectar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        painelStatusComunicacaoLayout.setVerticalGroup(
            painelStatusComunicacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelStatusComunicacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusComunicacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoConectar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        labelTempo.setFont(new java.awt.Font("Tahoma", 0, 130)); // NOI18N
        labelTempo.setText("00:00:00");

        botaoSensor0.setContentAreaFilled(false);
        botaoSensor0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSensor0ActionPerformed(evt);
            }
        });

        botaoSensor1.setContentAreaFilled(false);
        botaoSensor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSensor1ActionPerformed(evt);
            }
        });

        botaoSensor2.setContentAreaFilled(false);
        botaoSensor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSensor2ActionPerformed(evt);
            }
        });

        botaoSensor3.setContentAreaFilled(false);
        botaoSensor3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSensor3ActionPerformed(evt);
            }
        });

        botaoSensor4.setContentAreaFilled(false);
        botaoSensor4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSensor4ActionPerformed(evt);
            }
        });

        botaoSensor5.setContentAreaFilled(false);
        botaoSensor5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSensor5ActionPerformed(evt);
            }
        });

        botaoSensor6.setContentAreaFilled(false);
        botaoSensor6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSensor6ActionPerformed(evt);
            }
        });

        botaoSensor7.setContentAreaFilled(false);
        botaoSensor7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSensor7ActionPerformed(evt);
            }
        });

        botaoSensor8.setContentAreaFilled(false);
        botaoSensor8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSensor8ActionPerformed(evt);
            }
        });

        botaoSensor9.setContentAreaFilled(false);
        botaoSensor9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSensor9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelBandeirasLayout = new javax.swing.GroupLayout(painelBandeiras);
        painelBandeiras.setLayout(painelBandeirasLayout);
        painelBandeirasLayout.setHorizontalGroup(
            painelBandeirasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBandeirasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botaoSensor0)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSensor1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSensor2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSensor3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSensor4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSensor5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSensor6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSensor7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSensor8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSensor9)
                .addContainerGap(77, Short.MAX_VALUE))
        );
        painelBandeirasLayout.setVerticalGroup(
            painelBandeirasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBandeirasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelBandeirasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botaoSensor9)
                    .addComponent(botaoSensor8)
                    .addComponent(botaoSensor7)
                    .addComponent(botaoSensor6)
                    .addGroup(painelBandeirasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(botaoSensor0)
                        .addComponent(botaoSensor1)
                        .addComponent(botaoSensor2)
                        .addComponent(botaoSensor3)
                        .addComponent(botaoSensor4)
                        .addComponent(botaoSensor5)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tabelaTempoCheckpoints.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Check Point", "Tempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelaTempoCheckpoints);

        javax.swing.GroupLayout painelGeralLayout = new javax.swing.GroupLayout(painelGeral);
        painelGeral.setLayout(painelGeralLayout);
        painelGeralLayout.setHorizontalGroup(
            painelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelStatusComunicacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelConfiguraCompeticao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(painelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTempo)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(painelBandeiras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelGeralLayout.setVerticalGroup(
            painelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(painelGeralLayout.createSequentialGroup()
                        .addComponent(labelTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(painelBandeiras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(painelGeralLayout.createSequentialGroup()
                        .addComponent(painelConfiguraCompeticao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painelStatusComunicacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        barraStatusSensores.setFloatable(false);
        barraStatusSensores.setRollover(true);
        barraStatusSensores.setBorderPainted(false);

        sensor0.setToolTipText("Status Sensor 1");
        sensor0.setContentAreaFilled(false);
        sensor0.setFocusable(false);
        sensor0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sensor0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraStatusSensores.add(sensor0);

        sensor1.setToolTipText("Status Sensor 2");
        sensor1.setContentAreaFilled(false);
        sensor1.setFocusable(false);
        sensor1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sensor1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraStatusSensores.add(sensor1);

        sensor2.setToolTipText("Status Sensor 3");
        sensor2.setContentAreaFilled(false);
        sensor2.setFocusable(false);
        sensor2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sensor2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraStatusSensores.add(sensor2);

        sensor3.setToolTipText("Status Sensor 4");
        sensor3.setContentAreaFilled(false);
        sensor3.setFocusable(false);
        sensor3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sensor3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraStatusSensores.add(sensor3);

        sensor4.setToolTipText("Status Sensor 5");
        sensor4.setContentAreaFilled(false);
        sensor4.setFocusable(false);
        sensor4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sensor4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraStatusSensores.add(sensor4);

        sensor5.setToolTipText("Status Sensor 6");
        sensor5.setContentAreaFilled(false);
        sensor5.setFocusable(false);
        sensor5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sensor5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraStatusSensores.add(sensor5);

        sensor6.setToolTipText("Status Sensor 7");
        sensor6.setContentAreaFilled(false);
        sensor6.setFocusable(false);
        sensor6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sensor6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraStatusSensores.add(sensor6);

        sensor7.setToolTipText("Status Sensor 8");
        sensor7.setContentAreaFilled(false);
        sensor7.setFocusable(false);
        sensor7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sensor7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraStatusSensores.add(sensor7);

        sensor8.setToolTipText("Status Sensor 9");
        sensor8.setContentAreaFilled(false);
        sensor8.setFocusable(false);
        sensor8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sensor8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraStatusSensores.add(sensor8);

        sensor9.setToolTipText("Status Sensor 10");
        sensor9.setContentAreaFilled(false);
        sensor9.setFocusable(false);
        sensor9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sensor9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraStatusSensores.add(sensor9);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(barraferramentas, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(barraStatusSensores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(painelGeral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(barraferramentas, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(barraStatusSensores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelGeral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoAddEquipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAddEquipeActionPerformed
        InserirEquipeUI inserirEquipeUI = new InserirEquipeUI(this, true, listaEquipes);
        inserirEquipeUI.setVisible(true);
        inserirEquipeUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                updateListaBateria();
            }
        });
    }//GEN-LAST:event_botaoAddEquipeActionPerformed

    private void botaoMonitorSerialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMonitorSerialActionPerformed
        monitorSerial.setVisible(true);
    }//GEN-LAST:event_botaoMonitorSerialActionPerformed

    private void botaoPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPdfActionPerformed
        Object[] options = {"Classificação Parcial",
                    "Relatório Final",
                    "Cancelar"};
        int n = JOptionPane.showOptionDialog(this,
            "Qual documento deseja criar?",
            "Gerar Relatórios",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[2]);
        
        if(n == 0){
            salvarRelatorioParcial();
        }
        else if(n == 1){
            salvarRelatorioFinal();
        }

    }//GEN-LAST:event_botaoPdfActionPerformed

    private void botaoConfiguracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConfiguracoesActionPerformed
        Object[] options = {"Parâmetros",
                    "Sentido da Pista",
                    "Cancelar"};
        int n = JOptionPane.showOptionDialog(this,
            "O que deseja configurar?",
            "Configurações",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[2]);
        
        if(n == 0){ 
            ConfiguracoesUI configuracoesUI = new ConfiguracoesUI(this, true);
            configuracoesUI.setVisible(true);
            configuracoesUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    updateListaEquipe();
                }
            });
        }
        else if(n == 1){
            PistaUI pistaUI = new PistaUI(this, true);
            pistaUI.setVisible(true);
        }
    }//GEN-LAST:event_botaoConfiguracoesActionPerformed

    private void selecionarBateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecionarBateriaActionPerformed
        updateListaBateria();
    }//GEN-LAST:event_selecionarBateriaActionPerformed

    private void selecionarEquipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecionarEquipeActionPerformed
        updateListaEquipe();
    }//GEN-LAST:event_selecionarEquipeActionPerformed

    private void botaoContagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoContagemActionPerformed
        if(!contar){
            iniciarContagem();
        }
        else{
            pararContagem(false);
        }
    }//GEN-LAST:event_botaoContagemActionPerformed

    private void botaoConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConectarActionPerformed
        conexao();
    }//GEN-LAST:event_botaoConectarActionPerformed

    private void botaoTabelaPontuacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoTabelaPontuacaoActionPerformed
       PontuacaoUI pontuacaoUI = new PontuacaoUI(listaEquipes);
       pontuacaoUI.setVisible(true);
    }//GEN-LAST:event_botaoTabelaPontuacaoActionPerformed

    private void botaoTabelaTempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoTabelaTempoActionPerformed
        TempoUI tempoUI = new TempoUI(listaEquipes);
        tempoUI.setVisible(true);
    }//GEN-LAST:event_botaoTabelaTempoActionPerformed

    private void botaoSensor0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSensor0ActionPerformed
        botaoPressionado(0);
    }//GEN-LAST:event_botaoSensor0ActionPerformed

    private void botaoSensor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSensor1ActionPerformed
        botaoPressionado(1);
    }//GEN-LAST:event_botaoSensor1ActionPerformed

    private void botaoSensor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSensor2ActionPerformed
        botaoPressionado(2);
    }//GEN-LAST:event_botaoSensor2ActionPerformed

    private void botaoSensor3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSensor3ActionPerformed
        botaoPressionado(3);
    }//GEN-LAST:event_botaoSensor3ActionPerformed

    private void botaoSensor4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSensor4ActionPerformed
        botaoPressionado(4);
    }//GEN-LAST:event_botaoSensor4ActionPerformed

    private void botaoSensor5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSensor5ActionPerformed
        botaoPressionado(5);
    }//GEN-LAST:event_botaoSensor5ActionPerformed

    private void botaoSensor6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSensor6ActionPerformed
        botaoPressionado(6);
    }//GEN-LAST:event_botaoSensor6ActionPerformed

    private void botaoSensor7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSensor7ActionPerformed
        botaoPressionado(7);
    }//GEN-LAST:event_botaoSensor7ActionPerformed

    private void botaoSensor8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSensor8ActionPerformed
        botaoPressionado(8);
    }//GEN-LAST:event_botaoSensor8ActionPerformed

    private void botaoSensor9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSensor9ActionPerformed
        botaoPressionado(9);
    }//GEN-LAST:event_botaoSensor9ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int selectedOption = JOptionPane.showConfirmDialog(null, 
               "Deseja Finalizar o CoRA Chronos?", 
               "Finalizar", 
               JOptionPane.YES_NO_OPTION); 
        if (selectedOption == JOptionPane.YES_OPTION) {
            if(conectado) comunicacao.close();
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } 
    }//GEN-LAST:event_formWindowClosing

    private void botaoNetworkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNetworkActionPerformed

    }//GEN-LAST:event_botaoNetworkActionPerformed

    private void selecionarTentativaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecionarTentativaActionPerformed
        updateListaTentativas();  
        botoes();
    }//GEN-LAST:event_selecionarTentativaActionPerformed

    private void botaoExibicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExibicaoActionPerformed
        exibicaoUI.pack();
        exibicaoUI.setVisible(true);
    }//GEN-LAST:event_botaoExibicaoActionPerformed

    private void botaoSortearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSortearActionPerformed
        SortearUI sortearUI; 
        try {
            sortearUI = new SortearUI();
            sortearUI.setSize(exibicaoUI.getSize());
        
        
            //sortearUI.pack();
            sortearUI.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(CoRAUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_botaoSortearActionPerformed

    
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
            java.util.logging.Logger.getLogger(CoRAUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CoRAUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CoRAUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CoRAUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CoRAUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser PDFDir;
    private javax.swing.JToolBar barraStatusSensores;
    private javax.swing.JToolBar barraferramentas;
    private javax.swing.JButton botaoAddEquipe;
    private javax.swing.JButton botaoConectar;
    private javax.swing.JButton botaoConfiguracoes;
    private javax.swing.JButton botaoContagem;
    private javax.swing.JButton botaoExibicao;
    private javax.swing.JButton botaoMonitorSerial;
    private javax.swing.JButton botaoNetwork;
    private javax.swing.JButton botaoPdf;
    private javax.swing.JButton botaoSensor0;
    private javax.swing.JButton botaoSensor1;
    private javax.swing.JButton botaoSensor2;
    private javax.swing.JButton botaoSensor3;
    private javax.swing.JButton botaoSensor4;
    private javax.swing.JButton botaoSensor5;
    private javax.swing.JButton botaoSensor6;
    private javax.swing.JButton botaoSensor7;
    private javax.swing.JButton botaoSensor8;
    private javax.swing.JButton botaoSensor9;
    private javax.swing.JButton botaoSortear;
    private javax.swing.JButton botaoTabelaPontuacao;
    private javax.swing.JButton botaoTabelaTempo;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelBateria;
    private javax.swing.JLabel labelEquipe;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelTempo;
    private javax.swing.JLabel labelTentativa;
    private javax.swing.JPanel painelBandeiras;
    private javax.swing.JPanel painelConfiguraCompeticao;
    private javax.swing.JPanel painelGeral;
    private javax.swing.JPanel painelStatusComunicacao;
    private javax.swing.JComboBox selecionarBateria;
    private javax.swing.JComboBox selecionarEquipe;
    private javax.swing.JComboBox selecionarTentativa;
    private javax.swing.JButton sensor0;
    private javax.swing.JButton sensor1;
    private javax.swing.JButton sensor2;
    private javax.swing.JButton sensor3;
    private javax.swing.JButton sensor4;
    private javax.swing.JButton sensor5;
    private javax.swing.JButton sensor6;
    private javax.swing.JButton sensor7;
    private javax.swing.JButton sensor8;
    private javax.swing.JButton sensor9;
    private javax.swing.JLabel statusComunicacao;
    private javax.swing.JLabel statusEquipe;
    private javax.swing.JTable tabelaTempoCheckpoints;
    // End of variables declaration//GEN-END:variables
}

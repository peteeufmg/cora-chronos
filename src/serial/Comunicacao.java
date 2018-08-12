/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial;

import constantes.Parametro;
import frames.CoRAUI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class Comunicacao implements SerialPortEventListener {
    CoRAUI coRAUI;
    Protocolo protocolo = new Protocolo();
    SerialPort serialPort;
    /** The port we're normally going to use. */
    private static final String PORT_NAMES[] = { 
        "/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyACM0", // Raspberry Pi
	"/dev/ttyUSB0", // Linux
	Parametro.PORTA_COM.getPorta(), // Windows
    };
	
    /**
     * A BufferedReader which will be fed by a InputStreamReader 
    * converting the bytes into characters 
    * making the displayed results codepage independent
    */
    private BufferedReader input;
    /** The output stream to the port */
    private OutputStream output;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static int DATA_RATE = Parametro.BAUDRATE.getValue();
    
    private void parametros(){
        DATA_RATE = Parametro.BAUDRATE.getValue();
        PORT_NAMES[3] = Parametro.PORTA_COM.getPorta(); 
    }
        
    public boolean initialize(CoRAUI coRAUI) {
        parametros();
        this.coRAUI = coRAUI;
        boolean conectou = false;
	CommPortIdentifier portId = null;
	Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

	//First, Find an instance of serial port as set in PORT_NAMES.
	while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
		if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
		}
            }
	}
        
	if (portId == null) {
            JOptionPane.showMessageDialog(null, "A Porta Selecionada Não Foi Encontada", "ERRO", JOptionPane.ERROR_MESSAGE);
            System.out.println("Porta Não Encontrada.");
            return false;
	}

	try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            conectou = true;
            System.out.println("Comunicação iniciada.");
	} catch (PortInUseException | UnsupportedCommOperationException | IOException | TooManyListenersException e) {
            System.err.println(e.toString());
	}
        return conectou;
    }

    /**
    * Termina a conexão serial. 
    */
    public synchronized void close() {
	if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
            System.out.println("Desconectado.");
	}
    }

    /**
    * Método que será executado sempre que houver um evento na conexão serial. 
    * O argumento evento contém as informações sobre um evento na conexão serial, que significa o recebimento de uma mensagem.
    * <p>
    * O método também envia uma mensagem de volta via conexão serial. A mensagem pode ser:
    * tipo A --> O sistema entendeu a mensagem, caso a mensagem recebida seja do tipo P, o método identifica qual sensor foi ativado
    * tipo N --> a mensagem nao foi entendida
    * @param  evento contém as informações da mensagem recebida
    */
    @Override
    public synchronized void serialEvent(SerialPortEvent evento) {
        String resposta;
	if (evento.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
		String mensagem = input.readLine();
		System.out.println(mensagem);
                CoRAUI.getMonitorSerial().monitor.append("mensagem recebida: " + mensagem + "\n");
                if(protocolo.traduzir(mensagem)){
                    if(mensagem.charAt(1) == 'P'){
                        coRAUI.sensorAtivado(Character.getNumericValue(mensagem.charAt(2)));
                    }
                    resposta = "0A002";
                }
                else{
                    resposta = "0N003";
                }
                CoRAUI.getMonitorSerial().monitor.append("mensagem enviada: " + resposta + "\n");
                resposta = resposta.concat("\n");
                output.write(resposta.getBytes());

            } catch (Exception e) {
		System.err.println(e.toString());
            }
	}
	// Ignore all the other eventTypes, but you should consider the other ones.
	}
    /**
    * Retorna as portas COMs utilizados no computador. 
    * O argumento é uma referência para {@link JComboBox} que contém as informações 
    * das portas COMs do sistema, onde um Arduino pode estar conectado. 
    * <p>
    * Não há retorno no método, porém a função identifica as portas ativas e as adicionas
    * à {@link JComboBox} por meio do método addItem. 
    *
    * @param  portaCOM  irá conter as portas disponíveis do sistema
    * @see    JComboBox
       */
    public void getPortas(JComboBox portaCOM){
        portaCOM.removeAllItems();
        Enumeration pList = CommPortIdentifier.getPortIdentifiers();

        while (pList.hasMoreElements()) {
            CommPortIdentifier cpi = (CommPortIdentifier) pList.nextElement();
            if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portaCOM.addItem(cpi.getName());
            } 
        }
    }
}

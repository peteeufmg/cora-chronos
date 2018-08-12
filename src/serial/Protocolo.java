package serial;

import constantes.Parametro;
import frames.CoRAUI;


public class Protocolo {
    private static String [] dicionario = new String[]{"1P002", "1P103", "1P204", "1P305", "1P406", "1P507", "1P608", "1P709", "1P810", "1P911"};
    private final boolean [] statusSensor;
    
    public Protocolo() {
        statusSensor = new boolean[Parametro.NUMERO_MAXIMO_CHECK_POINTS.getValue()];
    }

    public static String[] getDicionario() {
        return dicionario;
    }

    public static void setDicionario(String[] dicionario) {
        Protocolo.dicionario = dicionario;
    }
    
    /**
    * Verifica se a mensagem recebida está de acordo com o protocolo do sistema. 
    * O parâmetro mensagem é do tipo {@link String}, e representa a mensagem enviada pelo arduíno.
    * <p>
    * Este método retorna verdadeiro para um mensagem que esteja de acordo com o protocolo e falso caso contrário. 
    * <p> 
    * protocolo<br>
    * mensagem tipo P --> indica Ativação dos sensores. (objeto Passou). <br>
    * mensagem tipo C --> mensagem de Checagem dos sensores. <br>
    * mensagem tipo N --> mensagem não foi entendida. <br>

    *
    * @param  mensagem  mensgem recebida pelo arduino Mestre.
    * @return      se a imagem foi entendida ou não.
    */
    public boolean traduzir(String mensagem){
        char tipo = mensagem.charAt(1);
        if(mensagem.length() == 5 && tipo == 'P'){
            int sensor = Character.getNumericValue(mensagem.charAt(2));
            int crc_calc = 1 + Character.getNumericValue(mensagem.charAt(0)) + sensor;
            int crc_rec = (Character.getNumericValue(mensagem.charAt(3)) * 10) + Character.getNumericValue(mensagem.charAt(4));          
            return crc_calc == crc_rec;
        }
        
        else if(tipo == 'C'){
            for(int i = 2; i < mensagem.length() - 2; i++){
               statusSensor[i - 2] = (mensagem.charAt(i) == '1');
            }
            CoRAUI.sensorStatus(statusSensor);
            return true;
        }
        else{
            return false;
        } 
    }
    
    /**
    * Verifica se a mensagem recebida faz parte do dicionário do sistema. 
    * O parâmetro mensagem é do tipo {@link String}, e representa a mensagem enviada pelo arduíno quando houve a ativação dos sensores.
    * <p>
    * Este método retorna verdadeiro para um mensagem que faça parte do dicionário dos sistema. 
    * <p> 
    * protocolo<br>
    * 1PSXX <br>
    * P --> indica mensagem de detecção dos sensores <br>
    * XX --> número do sensor ativado <br>
    * S --> resultado do cállculo: S = XX - 2 <br>
    * @param  mensagem  mensgem recebida pelo arduino Mestre.
    * @return      se a imagem foi entendida ou não.
    */
    public boolean conten(String mensagem){
        boolean contem = false;
        for (String palavra : dicionario) {
            if (palavra.equals(mensagem)) {
                contem = true;
            }
        }
        return contem;
    }
}


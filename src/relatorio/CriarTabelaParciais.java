package relatorio;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import constantes.Bateria;
import constantes.Parametro;
import cora.Equipe;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.List;
 
public class CriarTabelaParciais{
    private static boolean estado = false;
    
    public PdfPTable resultadoPontosClassificatoria(List<Equipe> listaEquipes) throws DocumentException {      
        float [] colunas = new float[]{0.5f, 3.5f}; 
        // cria uma tebela com um certo número de colunas
        PdfPTable tabela = new PdfPTable(colunas);
 
        // seta a largura da tabela para 100% da página
        tabela.setWidthPercentage(100);
        
        //
        tabela.setKeepTogether(true);
        
 
        // ---------------- Título da Tabela ----------------
        // fonte
        Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
        // cria a célula do título
        PdfPCell cell = new PdfPCell(new Phrase("ETAPA CLASSIFICATÓRIA", font));
        // seta o span da coluna
        cell.setColspan(colunas.length);
        // seta estilo
        EstiloRelatorio.titulo(cell);
        // add à tabela
        tabela.addCell(cell);
        
        //-----------------Título das Colunas------------------


        tabela.addCell(criaCabecalho("  "));
        //tabela.addCell(criaCabecalho("Código"));
        tabela.addCell(criaCabecalhoEquipe("Equipe"));
        //tabela.addCell(criaCabecalho("Pontuação"));
 
 
         //--------------------- Conteúdo da CriarTabela ---------------
        int colocacao = 1;
        for (Equipe equipe : listaEquipes) {
            tabela.addCell(criaCelula(colocacao + "º    "));
            tabela.addCell(criaCelulaNomeEquipe(converteEncode(equipe.getNome())));
            //tabela.addCell(criaCelula(String.valueOf(round(equipe.getClassificatoriaPontos(), 3))));
            estado = !estado;
            colocacao++;
        }
 
        estado = false;
        return tabela;
    }
    
    public PdfPTable resultadoPontosFinal(List<Equipe> listaEquipes) throws DocumentException {      
        float [] colunas = new float[]{0.5f, 3.5f}; 
        // cria uma tebela com um certo número de colunas
        PdfPTable tabela = new PdfPTable(colunas);
 
        // seta a largura da tabela para 100% da página
        tabela.setWidthPercentage(100);
        
        //
        tabela.setKeepTogether(true);
        
 
        // ---------------- Título da Tabela ----------------
        // fonte
        Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
        // cria a célula do título
        PdfPCell cell = new PdfPCell(new Phrase("ETAPA FINAL", font));
        // seta o span da coluna
        cell.setColspan(colunas.length);
        // seta estilo
        EstiloRelatorio.titulo(cell);
        // add à tabela
        tabela.addCell(cell);
        
        //-----------------Título das Colunas------------------


        tabela.addCell(criaCabecalho("  "));
        //tabela.addCell(criaCabecalho("Código"));
        tabela.addCell(criaCabecalhoEquipe("Equipe"));
        //tabela.addCell(criaCabecalho("Pontuação"));
 
 
         //--------------------- Conteúdo da CriarTabela ---------------
        int colocacao = 1;
        for (Equipe equipe : listaEquipes) {
            tabela.addCell(criaCelula(colocacao + "º    "));
            tabela.addCell(criaCelulaNomeEquipe(converteEncode(equipe.getNome())));
           // tabela.addCell(criaCelula(String.valueOf(equipe.getFinalPontos() == 0 ? "-----": round(equipe.getFinalPontos(), 3) )));
            estado = !estado;
            colocacao++;
        }
 
        estado = false;
        return tabela;
    }
    
     // cria tabela
    public PdfPTable resultadoTempo(List<Equipe> listaEquipes, int bateria) throws DocumentException {
        int trechos = Parametro.getTrechos(bateria);
         float [] colunas =  criaColumn(trechos);
        // cria uma tebela com um certo número de colunas
        PdfPTable tabela = new PdfPTable(colunas);
 
        // seta a largura da tabela para 100% da página
        tabela.setWidthPercentage(100);
        
        //
        tabela.setKeepTogether(true);
        
 
        // ---------------- Título da Tabela ----------------
        // fonte
        Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
        // cria a célula do título
        PdfPCell cell;
        if(bateria != Bateria.FINAL.getValue()){
            cell = new PdfPCell(new Phrase("TEMPO - " + (bateria + 1) + "° BATERIA", font));
        }
        else {
            cell = new PdfPCell(new Phrase("TEMPO - FINAL", font));
        }
        
        // seta o span da coluna
        cell.setColspan(colunas.length);
        // seta estilo
        EstiloRelatorio.titulo(cell);
        // add à tabela
        tabela.addCell(cell);
        
        //-----------------Título das Colunas------------------


        tabela.addCell(criaCabecalho("  "));
        //tabela.addCell(criaCabecalho("Código"));
        tabela.addCell(criaCabecalhoEquipe("Equipe"));
        for(int i = 1; i <= trechos; i++){
            tabela.addCell(criaCabecalho(i + "° Trecho"));
        }
        tabela.addCell(criaCabecalho("Total"));
 
 
         //--------------------- Conteúdo da CriarTabela ---------------
        int colocacao = 1;
        for (Equipe equipe : listaEquipes) {
            if(bateria == Bateria.FINAL.getValue() && equipe.isClassificada()){
                tabela.addCell(criaCelula(colocacao + "º    "));
                tabela.addCell(criaCelulaNomeEquipe(converteEncode(equipe.getNome())));
                for(int trecho_aux = 0; trecho_aux < trechos; trecho_aux++){
                    tabela.addCell(criaCelula(equipe.getTempoTrecho(bateria, trecho_aux) == Parametro.PENALIDADE.getValue() ?
                        "-----" : String.valueOf(round(equipe.getTempoTrecho(bateria, trecho_aux),3))));
                }
                tabela.addCell(criaCelula( equipe.getTempoTotal(bateria) == Parametro.PENALIDADE.getValue() ? 
                    "-----" : String.valueOf(round(equipe.getTempoTotal(bateria), 3))));
                estado = !estado;
                colocacao++;               
            }
            else if(bateria != Bateria.FINAL.getValue()){
                tabela.addCell(criaCelula(colocacao + "º    "));
                tabela.addCell(criaCelulaNomeEquipe(converteEncode(equipe.getNome())));
                for(int trecho_aux = 0; trecho_aux < trechos; trecho_aux++){
                    tabela.addCell(criaCelula(equipe.getTempoTrecho(bateria, trecho_aux) == Parametro.PENALIDADE.getValue() ?
                        "-----" : String.valueOf(round(equipe.getTempoTrecho(bateria, trecho_aux),3))));
                }
                tabela.addCell(criaCelula( equipe.getTempoTotal(bateria) == Parametro.PENALIDADE.getValue() ? 
                    "-----" : String.valueOf(round(equipe.getTempoTotal(bateria), 3))));
                estado = !estado;
                colocacao++;             
            }
        }
 
        estado = false;
        return tabela;
    }
 
    // create cells
    private PdfPCell criaCabecalho(String text){      
        // font
        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.DARK_GRAY);
 
        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));
 
        // set style
        EstiloRelatorio.cabecalho(cell);
        return cell;
    }
    
        // create cells
    private PdfPCell criaCabecalhoEquipe(String text){      
        // font
        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.DARK_GRAY);
 
        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));
 
        // set style
        EstiloRelatorio.cabecalho(cell);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }
 
    // create cells
    private static PdfPCell criaCelula(String text){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
 
        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));
 
        // set style
        EstiloRelatorio.cell(cell, estado);
        return cell;
    }
    
        // create cells
    private static PdfPCell criaCelulaNomeEquipe(String text){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
 
        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));
 
        // set style
        EstiloRelatorio.cell(cell, estado);    
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }
    
    private float[] criaColumn(int trechos){
        int num = trechos  + 3; //+ 4;
        float [] colunas = new float[num];
        colunas[0] = 0.3f;
        //colunas[1] = 0.5f;
        colunas[1] = 1.5f;//1.3f;
        float restante = 10 - colunas[0] - colunas[1];
        float razao = restante/num;
        for(int i = 2; i < num; i++){
            colunas[i] = razao;
        }
        return colunas;
    }
    
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    public static String converteEncode(String palavra) {  
        // Create the encoder and decoder for ISO-8859-1
        Charset charset = Charset.forName("ISO-8859-1");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        try {
            ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(palavra));
            CharBuffer cbuf = decoder.decode(bbuf);
            palavra = cbuf.toString();
        } catch (CharacterCodingException e) {
    }
        return palavra;
    }  
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorio;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import constantes.Bateria;
import constantes.Diretorio;
import cora.Equipe;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.swing.JOptionPane;



/**
 *
 * @author Mateus
 */
public class Relatorio {
    /**
     * Creates a PDF file
     * @param listaEquipe
     * @throws java.io.FileNotFoundException
     * @throws com.itextpdf.text.DocumentException
     * @throws java.io.IOException
     */
    public void gerarRelatorio(List<Equipe> listaEquipe) throws FileNotFoundException, DocumentException, IOException{
        Document documento = null;
        OutputStream outputStream = null;
        try{
            String caminho = Diretorio.RELATORIO.getValue();
            documento = new Document(PageSize.A4.rotate(), 24, 24, 72, 0);
            outputStream = new FileOutputStream(caminho);
            PdfWriter.getInstance(documento, outputStream);
            documento.open();

            Image imagem = Image.getInstance(getClass().getResource("/imagens/CoRA_Logo.png"));
            imagem.setAlignment(Element.ALIGN_CENTER);
            imagem.scaleToFit(300f, 300f);
            documento.add(imagem);
            
            documento.add(new Phrase("\n"));
            Font fonte = new Font(Font.FontFamily.HELVETICA, 40, Font.NORMAL, BaseColor.BLACK);
            Paragraph texto = new Paragraph("Resultados", fonte);
            texto.setAlignment(Element.ALIGN_CENTER);
            documento.add(texto);        
            
            documento.add(new Phrase("\n"));
            
            PdfPTable tabela;
            CriarTabela criarTabela = new CriarTabela();
            tabela = criarTabela.resultadoPontos(listaEquipe);
            documento.add(tabela);
            
            documento.add(new Phrase("\n"));
            
            for(int bateria = 0; bateria < Bateria.QUANTIDADE.getValue(); bateria++){
                tabela = criarTabela.resultadoTempo(listaEquipe, bateria);
                documento.add(tabela);
                documento.add(new Phrase("\n"));
            }

            
            documento.close();
            
            System.out.println("Relatório gerado com sucesso em: " + caminho);
            
            if (JOptionPane.showConfirmDialog(null, "Relatório Criado Com Sucesso! \n Deseja Abri-lo Agora?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + caminho);
            }
        }
        finally {
            if (documento != null) {
                documento.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
    
    
    public void gerarPDFParciais(List<Equipe> listaEquipe) throws FileNotFoundException, DocumentException, IOException{
        Document documento = null;
        OutputStream outputStream = null;
        try{
            String caminho = Diretorio.RELATORIO.getValue();
            documento = new Document(PageSize.A4.rotate(), 24, 24, 72, 0);
            outputStream = new FileOutputStream(caminho);
            PdfWriter.getInstance(documento, outputStream);
            documento.open();

            Image imagem = Image.getInstance(getClass().getResource("/imagens/CoRA_Logo.png"));
            imagem.setAlignment(Element.ALIGN_CENTER);
            imagem.scaleToFit(300f, 300f);
            documento.add(imagem);
            
            documento.add(new Phrase("\n"));
            Font fonte = new Font(Font.FontFamily.HELVETICA, 40, Font.NORMAL, BaseColor.BLACK);
            Paragraph texto = new Paragraph("Resultado Parcial", fonte);
            texto.setAlignment(Element.ALIGN_CENTER);
            documento.add(texto);        
            
            documento.add(new Phrase("\n"));
            
            PdfPTable tabela;
            CriarTabelaParciais criarTabela = new CriarTabelaParciais();
            tabela = criarTabela.resultadoPontosClassificatoria(listaEquipe);
            documento.add(tabela);
            tabela = criarTabela.resultadoPontosFinal(listaEquipe);
            documento.add(tabela);
            
            documento.add(new Phrase("\n"));
            
            for(int bateria = 0; bateria < Bateria.QUANTIDADE.getValue(); bateria++){
                tabela = criarTabela.resultadoTempo(listaEquipe, bateria);
                documento.add(tabela);
                documento.add(new Phrase("\n"));
            }

            
            documento.close();
            
            System.out.println("Relatório gerado com sucesso em: " + caminho);
            
            if (JOptionPane.showConfirmDialog(null, "Relatório Criado Com Sucesso! \n Deseja Abri-lo Agora?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + caminho);
            }
        }
        finally {
            if (documento != null) {
                documento.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}

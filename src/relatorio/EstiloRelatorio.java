package relatorio;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;

public class EstiloRelatorio {
      public static void titulo(PdfPCell cell){
        // alinamhento
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
 
        // acolchoamento
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(7f);
 
        // cor de fundo
        cell.setBackgroundColor(BaseColor.WHITE);
 
        // borda
        cell.setBorder(0);
        cell.setBorderWidthBottom(2f);
 
    }
    public static void cabecalho(PdfPCell cell){
        // alinhamento
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);       
 
        // acolchoamento
        cell.setPaddingLeft(3f);
        cell.setPaddingTop(0f);
 
        // cor de fundo
        cell.setBackgroundColor(BaseColor.WHITE);
 
        // borda
        cell.setBorder(0);
        cell.setBorderWidthBottom(1);
        cell.setBorderColorBottom(BaseColor.GRAY);
        cell.setBorder(Rectangle.NO_BORDER);
 
        // height
        cell.setMinimumHeight(18f);
    }
 
    public static void cell(PdfPCell cell, boolean estado){
        //cor
        cell.setBackgroundColor(estado ? BaseColor.WHITE : BaseColor.LIGHT_GRAY);
        // alinhamento
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        // acolchoamento
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(5f);
 
        // borda
        cell.setBorder(0);
        cell.setBorderWidthBottom(0.5f);
 
        // altura
        cell.setMinimumHeight(18f);
    }
    
}

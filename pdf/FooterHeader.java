//    Pdfprüfbericht - Creates a Pdf-File from XML
//    Copyright (C) 2015  Jan Scholz // jan_scholz@gmx.net
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Affero General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Affero General Public License for more details.
//
//    You should have received a copy of the GNU Affero General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;



public class FooterHeader extends PdfPageEventHelper{


    protected Phrase header;
    protected Phrase footer;
    protected PdfTemplate total;

    public FooterHeader(String Titel, PdfWriter writer) {     
        header = new Phrase(Titel);
        total = writer.getDirectContent().createTemplate(30, 16);      
    }  
    

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
       PdfPTable table = new PdfPTable(3);
        try {
          if (document.getPageNumber() > 1) { 
            table.setWidths(new int[]{24, 24, 2});
            table.setTotalWidth(527);
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(20);
            table.getDefaultCell().setBorder(Rectangle.BOTTOM);
            table.addCell(header);
            table.getDefaultCell().setHorizontalAlignment(
              Element.ALIGN_RIGHT);
            table.addCell(
              String.format("Seite %d von", writer.getPageNumber()));
            PdfPCell cell = new PdfPCell(Image.getInstance(total));   
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);
            table.writeSelectedRows(0, -1,
              34, 803, writer.getDirectContent());
          }
        }
        catch(DocumentException de) {
          throw new ExceptionConverter(de);
        }

    PdfContentByte cb = writer.getDirectContent();             
    if (document.getPageNumber() > 1) {          
        footer = new Phrase(document.getPageNumber()-2);
      ColumnText.showTextAligned(cb,    
        Element.ALIGN_CENTER, footer, 
        (document.right() - document.left() -30)/ 2             
        + document.leftMargin(), document.bottom()+ 10, 0); 
    }
  
}
    
@Override
  public void onCloseDocument(PdfWriter writer, Document document) {
    ColumnText.showTextAligned(total, Element.ALIGN_LEFT,              
      new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);  
  } 
    
}

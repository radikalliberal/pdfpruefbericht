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

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.FileOutputStream;
import java.io.IOException;
public class watermark {
    
    public watermark() {
        try {
            PdfReader Read_PDF_To_Watermark = new PdfReader("D:\\Dropbox\\Studium\\Bachelor Thesis\\Resourcen\\pdfexport\\xmltopdf\\test.pdf");
            int number_of_pages = Read_PDF_To_Watermark.getNumberOfPages();
            PdfStamper stamp = new PdfStamper(
                    Read_PDF_To_Watermark, 
                    new FileOutputStream("D:\\Dropbox\\Studium\\Bachelor Thesis\\Resourcen\\pdfexport\\xmltopdf\\New_PDF_With_Watermark_Image.pdf"));
            int i = 0;
            Image watermark_image = Image.getInstance("D:\\Dropbox\\Studium\\Bachelor Thesis\\Resourcen\\pdfexport\\xmltopdf\\desy.png");
            watermark_image.setAbsolutePosition(50, 150);
            watermark_image.scaleToFit(500, 500);
            PdfContentByte add_watermark;            
            while (i < number_of_pages) {
              i++;
              add_watermark = stamp.getUnderContent(i);
              add_watermark.addImage(watermark_image);
            }
            stamp.close();
        }
        catch (IOException | DocumentException i1) {
            i1.printStackTrace();
        }
    }
}
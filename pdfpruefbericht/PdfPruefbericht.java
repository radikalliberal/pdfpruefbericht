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


package pdfpruefbericht;
import xml.ContentParser;
import xml.StructureParser;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
 
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfStructureElement;
import com.itextpdf.text.pdf.PdfStructureTreeRoot;
import com.itextpdf.text.pdf.PdfWriter;
import pdf.PdfBuilder;

/**
 *
 * @author jan
 */
public class PdfPruefbericht {

   
     public static void main(String[] args)
        throws IOException, DocumentException, SAXException, ParserConfigurationException {
         
        String RESULT = args[1];
        String RESOURCE = args[0];
        ContentParser cp;
        
        try{
            Document document = new Document(PageSize.A4);
            document.setMargins(36, 72, 36, 144);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
            writer.setTagged();
            document.open();
            PdfStructureTreeRoot root = writer.getStructureTreeRoot();
            root.mapRole(new PdfName("Report"), PdfName.ROOT);
            root.mapRole(new PdfName("Test"), PdfName.SECT);
            root.mapRole(new PdfName("Segment"), PdfName.SEPARATION);
            root.mapRole(new PdfName("Comment"), PdfName.CO);
            root.mapRole(new PdfName("img"), PdfName.PI);
            root.mapRole(new PdfName("log"), PdfName.L);
            root.mapRole(new PdfName("Setup"), PdfName.SETTINGS);
            root.mapRole(new PdfName("device"), PdfName.D);
            root.mapRole(new PdfName("parameter"), PdfName.P);

            PdfStructureElement top = new PdfStructureElement(root, new PdfName("Report"));
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            List<PdfStructureElement> elements = new ArrayList<>();
            cp = new ContentParser(document, writer, elements);
            parser.parse(
                new InputSource(new FileInputStream(RESOURCE)),
                new StructureParser(top, elements));
            parser.parse(
                new InputSource(new FileInputStream(RESOURCE)),
                    cp
                );
            PdfBuilder pdf = new PdfBuilder(document,writer,cp.getReport());
            pdf.build();
            document.close();
        }
        catch(IOException|DocumentException|SAXException|ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
        //watermark watermark = new watermark();
    }


    

 
}


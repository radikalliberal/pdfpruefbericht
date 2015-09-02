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

import DataStructure.Parameter;
import DataStructure.Report;
import DataStructure.Segment;
import DataStructure.Setup;
import DataStructure.Test;
import DataStructure.device;
import DataStructure.img;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.processing.FilerException;
import utilities.util;

/**
 *
 * @author jan
 */
public class PdfBuilder {
    
    protected PdfWriter writer;
    protected Document document;
    protected Report report;
    protected ColumnText column;
    protected PdfContentByte canvas;
    protected int figureCount;
    protected Font font,headerfont,titelfont,smallertitelfont,smallerheaderfont;
    protected Paragraph p;
    protected ArrayList<device> devices;

        
    public PdfBuilder(Document d, PdfWriter w, Report r) 
            throws DocumentException, IOException {
        this.writer = w;
        this.document = d;
        this.report = r;
        this.devices = new ArrayList<>();
    }

    public void build() throws DocumentException, IOException {
        initialise();
        TitelPage(); 
        printTests();
        printDeviceTable();
    }

    private void initialise() throws IOException, DocumentException {
        canvas = writer.getDirectContent();
        column = new ColumnText(canvas);
        column.setSimpleColumn(util.m2p(20), util.m2p(20),util.m2p(190),util.m2p(277));
        titelfont = new Font(
                BaseFont.createFont("c:/windows/fonts/times.ttf", 
                                    BaseFont.WINANSI, BaseFont.EMBEDDED), 20);
        smallertitelfont = new Font(
                BaseFont.createFont("c:/windows/fonts/times.ttf", 
                                    BaseFont.WINANSI, BaseFont.EMBEDDED), 15);
        font = new Font(
                BaseFont.createFont("c:/windows/fonts/times.ttf", 
                                    BaseFont.WINANSI, BaseFont.EMBEDDED), 12);
        headerfont = new Font(
                BaseFont.createFont("c:/windows/fonts/times.ttf", 
                                    BaseFont.WINANSI, BaseFont.EMBEDDED), 18,Font.BOLD);
        smallerheaderfont = new Font(
                BaseFont.createFont("c:/windows/fonts/times.ttf", 
                                    BaseFont.WINANSI, BaseFont.EMBEDDED), 15,Font.BOLD);
        //Kopf und Fusszeile
        writer.setPageEvent(new FooterHeader( report.getEUT() + " " + report.getObnr(), writer));
    }
    
    private void TitelPage() throws DocumentException {
        document.newPage();
        column.setSimpleColumn(util.m2p(20), util.m2p(20),util.m2p(190),util.m2p(277));
        p = new Paragraph("\n\n\n\nEMV Prüfbericht für die Baugruppe \n"
                + report.getEUT() +" \n " +report.getObnr(), titelfont);
        p.setAlignment(Element.ALIGN_CENTER);
        column.addElement(p);
        p = new Paragraph("\n\nPrüfer " + report.getPruefer() + "\ngeprüft am "
                + report.getDate(),smallertitelfont);
        p.setAlignment(Element.ALIGN_CENTER);
        column.addElement(p);
        typesetting (document);
    }
    
    private void printSetup(Test test)throws IOException, DocumentException {
        Setup set = test.getSet();
        p = new Paragraph("Versuchsaufbau \n", smallerheaderfont);
        p.setAlignment(Element.ALIGN_JUSTIFIED);
        column.addElement(p); 
        typesetting (document);
        p = new Paragraph(set.getComm(), font);
        p.setAlignment(Element.ALIGN_JUSTIFIED);
        column.addElement(p); 
        typesetting (document);
        printImages(set.images);

    }
    
    private void printTests() throws IOException, DocumentException {
        for(Test test:report) {
            document.newPage();
            column.setSimpleColumn(util.m2p(20), util.m2p(20),util.m2p(190),util.m2p(277));
            p = new Paragraph(test.getName() + "\n\n", headerfont);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p); 
            p = new Paragraph("Luftfeuchtigkeit: " + test.getHumidity() + "%\nTemperatur: " + test.getTemp() + "°C\n\n", font);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p); 
            typesetting (document);
            printSetup(test);
            printSegments(test);
            
        }
    }

    private void printSegments(Test test) throws IOException, DocumentException {
        for(Segment seg:test) {
            p = new Paragraph(seg.getName(), smallerheaderfont);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p);
            typesetting (document);
            printParameters(seg);
            printImages(seg.images);
            printComments(seg);
        }
    }

    private void printComments(Segment seg) throws DocumentException {
        if(seg.getComment() != null) {
            p = new Paragraph(seg.getComment(), font);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p);
            typesetting (document);
        }
    }

    private void printImages(List<img> items) throws IOException, DocumentException {
        for(img im:items) {
            column.addElement(new Phrase(" "));
            File image = new File(im.getPath());
            String mimeType = new MimetypesFileTypeMap().getContentType(image);
            
            try {
                if (!image.exists()){
                    throw new FileNotFoundException("Der Pfad \"" + im.getPath()+ "\" ist ungültig");
                }
                if(!mimeType.substring(0,5).equalsIgnoreCase("image")) { //kein Bild
                    if(!util.getExtension(image).equalsIgnoreCase("png")) //Png-Dateien wurden nicht erkannt ...
                        throw new FilerException("Die Datei "+im.getPath()+
                                            " ist keine Bilddatei und kann nicht dargestellt werden.");
                }

                Image img = Image.getInstance(im.getPath());
                img.setAlignment(Element.ALIGN_CENTER);
                img.scaleToFit(400, 400);
                if((img.getScaledHeight()+100)>(column.getYLine())){
                    document.newPage();
                    column.setSimpleColumn(util.m2p(20), util.m2p(20),util.m2p(190),util.m2p(277));
                }
                column.addElement(img);
                figureCount++;
                p = new Paragraph("Abbildung " + String.valueOf(figureCount) + ". " +im.getLabel(), font);
                p.setAlignment(Element.ALIGN_CENTER);
                column.addElement(p);
                typesetting (document);
            }
            catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private void printDeviceTable() throws DocumentException  {
        for(Test t :report) {
            if(t.getSet().devices != null)
                t.getSet().devices.stream().forEach((d) -> {
                    devices.add(d);
            });
        }
        if(!devices.isEmpty()) {
            document.newPage();
            column.setSimpleColumn(util.m2p(20), util.m2p(20),util.m2p(190),util.m2p(277));
            p = new Paragraph("Geräte\n\n", smallerheaderfont);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p);
            typesetting (document);
            PdfPTable table = new PdfPTable(3);    
            table.addCell("Name");
            table.addCell("Seriennummer");
            table.addCell("letzte Kalibrierung");
            for(device dev :devices){
                table.addCell(dev.getName()); 
                table.addCell(dev.getSerialnumber());
                table.addCell(dev.getLastCalibration());
            }
            column.addElement(table);
            typesetting (document);
        }
    }
    
    private void printParameters(Segment seg) throws DocumentException  {
        if(!seg.parameters.isEmpty()){
            p = new Paragraph("\n\nParameter\n\n", smallerheaderfont);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p);
            typesetting (document);
            PdfPTable table = new PdfPTable(seg.getColumns());  
            for(Parameter par :seg.parameters){
                if(par==null) break;
                table.addCell(par.getAttribute()); 
                for(int i = 0; i < seg.getColumns() - 1; i++)
                table.addCell(par.getValue(i));
            }
            column.addElement(table);
            typesetting (document);
        }
    }

    private void typesetting (Document document) throws DocumentException {
        int status = column.go();
        while (ColumnText.hasMoreText(status)) {
            document.newPage();
            column.setSimpleColumn(util.m2p(20), util.m2p(20),util.m2p(190),util.m2p(277));
            status = column.go();
        }
    }

}

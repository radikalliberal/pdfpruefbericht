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
import DataStructure.Test;
import DataStructure.Setup;
import DataStructure.Norm;
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
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    protected Path jarpath;  
    protected float compression;

        
    public PdfBuilder(Document d, PdfWriter w, Report r, float comp) 
            throws DocumentException, IOException, URISyntaxException {
        writer = w;
        document = d;
        report = r;
        devices = new ArrayList<>();
        jarpath = Paths.get(PdfBuilder.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        compression = comp;
    }

    public void build() throws DocumentException, IOException, URISyntaxException {
        initialise();
        TitelPage(); 
        printNorm();
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
    
    private void printSetup(Norm test)throws IOException, DocumentException, URISyntaxException {
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
    
    private void printNorm() throws IOException, DocumentException, URISyntaxException {
        for(Norm norm:report) {
            document.newPage();
            column.setSimpleColumn(util.m2p(20), util.m2p(20),util.m2p(190),util.m2p(277));
            p = new Paragraph(norm.getName() + "\n\n", headerfont);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p); 
            p = new Paragraph("Luftfeuchtigkeit: " + norm.getHumidity() + "%\nTemperatur: " + norm.getTemp() + "°C\n\n", font);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p); 
            typesetting (document);
            printSetup(norm);
            printTest(norm);
            
        }
    }

    private void printTest(Norm norm) throws IOException, DocumentException, URISyntaxException {
        for(Test test:norm) {
            if(column.getYLine() < 250){
                document.newPage();
                column.setSimpleColumn(util.m2p(20), util.m2p(20),util.m2p(190),util.m2p(277));
            }
            p = new Paragraph(test.getName(), smallerheaderfont);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p);
            typesetting (document);
            printParameters(test);
            printImages(test.images);
            printComments(test);
        }
    }

    private void printComments(Test seg) throws DocumentException {
        if(seg.getComment() != null) {
            p = new Paragraph(seg.getComment(), font);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p);
            typesetting (document);
        }
    }

    private void printImages(List<img> items) throws IOException, DocumentException, URISyntaxException {
        for(img im:items) {
            column.addElement(new Phrase(" "));
            File image = new File(im.getPath());
            
            try {
                if (!image.exists() || image == null){
                    image = new File(jarpath.getParent() +  "\\Data\\nopic.png");
                    System.out.println("Der Pfad \"" + im.getPath()+ "\" ist ungültig");
                }
                String mimeType = new MimetypesFileTypeMap().getContentType(image);
                if(!mimeType.substring(0,5).equalsIgnoreCase("image")) { //kein Bild
                    if(!util.getExtension(image).equalsIgnoreCase("png")) //Png-Dateien wurden nicht erkannt ...
                        throw new FilerException("Die Datei "+image.getAbsolutePath()+
                                            " ist keine Bilddatei und kann nicht dargestellt werden.");
                }
                
                   
                Image img = Image.getInstance(util.resize(image,compression).getAbsolutePath());
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
        for(Norm t :report) {
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
    
    private void printParameters(Test test) throws DocumentException  {
        if(!test.parameters.isEmpty()){
            int tablesize = 23*test.parameters.size()+100;
            float spaceOnPage = column.getYLine();
            if(tablesize > spaceOnPage){
                    document.newPage();
                    column.setSimpleColumn(util.m2p(20), util.m2p(20),util.m2p(190),util.m2p(277));
            }
            p = new Paragraph("\n\nParameter\n\n", smallerheaderfont);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            column.addElement(p);
            typesetting (document);
            PdfPTable table = new PdfPTable(test.getColumns());  
            for(Parameter par :test.parameters){
                if(par==null) break;
                table.addCell(par.getAttribute()); 
                for(int i = 0; i < test.getColumns() - 1; i++)
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

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

package xml;

import DataStructure.Parameter;
import DataStructure.Report;
import DataStructure.Test;
import DataStructure.Setup;
import DataStructure.Norm;
import DataStructure.device;
import DataStructure.img;
import java.io.IOException;
import java.util.List;
 
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfStructureElement;
import com.itextpdf.text.pdf.PdfWriter;
 
public class ContentParser extends DefaultHandler {
 
    /** The StringBuffer that holds the characters. */
    protected StringBuffer buf = new StringBuffer();
 
    /** The document to which content parsed form XML will be added. */
    
    /** The writer to which PDF syntax will be written. */
    
    /** The canvas to which content will be written. */
    
    /** A list with structure elements. */
    protected List<PdfStructureElement> elements;
    /** The current structure element during the parsing process. */
    protected PdfStructureElement current;
    /** The column to which content will be added. */
    
    /** The font used when content elements are created. */
    
    
    
    
    protected Report report;
    
    protected Norm currentNorm;
    
    protected Test currentSegment;
    
    protected Setup currentSetup;
    
    protected boolean isSetup;
    
    protected double temp,humidity;
 
    /**
     * Creates a new ContentParser
     * @param document
     * @param writer
     * @param elements
     * @throws DocumentException
     * @throws IOException
     */
    public ContentParser(Document document, PdfWriter writer, List<PdfStructureElement> elements)
        throws DocumentException, IOException {
    
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        for (int i = start; i < start + length; i++) {
            if (ch[i] == '\n')
                buf.append(' ');
            else
                buf.append(ch[i]);
        }
    }
    

    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if ("Report".equals(qName)){
            report = new Report(attributes.getValue(2), attributes.getValue(3), attributes.getValue(1), attributes.getValue(0));
            return;
        }
        
        switch (qName) {
            case "parameter":
                int columns = 0;
                while(attributes.getValue(columns) != null) columns++;
                currentSegment.setColumns(columns);
                String[] Values = new String[columns];
                for(int i = 1; i < columns ;i++ ) {
                   Values[i-1]=attributes.getValue(i);
                }
                currentSegment.addParameter(new Parameter(attributes.getValue(0),Values));
                break;
            case "device":
                currentSetup.devices.add(new device(attributes.getValue(1),
                                                attributes.getValue(2),
                                                attributes.getValue(0)));
                break;
            case "Setup":
                isSetup = true;
                currentSetup = new Setup();
                currentNorm.addSetup(currentSetup);
                break;
            case "img":
                if(isSetup)
                    currentSetup.images.add(new img(attributes.getValue(0),
                                                attributes.getValue(1)));
                else
                    currentSegment.addImage(new img(attributes.getValue(0),
                                                attributes.getValue(1)));
                break;

            case "Norm":
                temp = attributes.getValue(2).isEmpty() ?
                        0 : Double.valueOf(attributes.getValue(3).replaceAll(",", "."));
                humidity = attributes.getValue(0).isEmpty() ? 
                        0 : Double.valueOf(attributes.getValue(0).replaceAll(",", "."));
                currentNorm = new Norm(attributes.getValue(2),
                                       temp,
                                       humidity);
                report.addNorm(currentNorm);
                break;

            case "Test":
                isSetup = false;
                currentSegment = new Test(
                                             attributes.getValue(1),
                                             attributes.getValue(0));
                currentNorm.addTest(currentSegment);
                break;

            default:
                break;
        }

        //elements.remove(0);
    }

    public Report getReport() {
        return report;
    }
    
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if ("Report".equals(qName)) return;
        
        String s = buf.toString().trim();
        buf = new StringBuffer();
        if (s.length() > 0) {
            if ("Comment".equals(qName)) 
                if(isSetup)
                    currentSetup.addComment(s);
                else
                    currentSegment.addComment(s);    
            if ("log".equals(qName)) currentSegment.setLog(s);
        }
    }
}

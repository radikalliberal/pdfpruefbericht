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


import java.util.List;
 
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfStructureElement;
 
public class StructureParser extends DefaultHandler {
 
    /** The top element in the PDF structure */
    protected PdfStructureElement top;
    /** The list of structure elements */
    protected List<PdfStructureElement> elements;
 
    /** Creates a parser that will parse an XML file into a structure tree.
     * @param top
     * @param elements */
    public StructureParser(PdfStructureElement top, List<PdfStructureElement> elements) {
        this.top = top;
        this.elements = elements;
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if ("Report".equals(qName)) return;
        elements.add(new PdfStructureElement(top, new PdfName(qName)));
    }
}
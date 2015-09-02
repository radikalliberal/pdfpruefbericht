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

package DataStructure;

public class Parameter {
    private final String Attribute;
    private final String[] Values;

    public Parameter(String Attribute, String[] Values) {
        this.Attribute = Attribute;
        this.Values = Values;
    }

    public String getAttribute() {
        return Attribute;
    }

    public String getValue(int i) {
        return Values[i];
    }
    
    
}

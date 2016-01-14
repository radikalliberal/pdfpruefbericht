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

import java.util.Iterator;

@SuppressWarnings (" unchecked ")
public class Report implements Iterable<Norm>{

    private final String  obnr; // Orderbasenummer (Warenwirtschaftssystem)
    private final String  pruefer;
    private final String  EUT; //Equipemnt under Norm
    private final Norm[] norms = new Norm[6];
    private int N = 0;
    private final String date;

    public String getDate() {
        return date;
    }
    
    public Report(String obnr, String pruefer, String EUT, String d) {
        this.obnr = obnr;
        this.pruefer = pruefer;
        this.EUT = EUT;
        this.date = d;
       // this.tests = new Norm[6];
    }

    public String getObnr() {
        return obnr;
    }

    public String getPruefer() {
        return pruefer;
    }

    public String getEUT() {
        return EUT;
    }
    
    public void addNorm(Norm norm) {
        this.norms[N++] = norm;
    }
    
     public void deleteNorm(int index) {
        for(int i = index + 1; i < this.norms.length; i++) {
            this.norms[i - 1] =  this.norms[i];
        }
        this.norms[this.norms.length - 1] = null;
    }
    
     public Norm getNorm(int index){
        return this.norms[index];
    }

    @Override
    public Iterator<Norm> iterator() {
        return new NormIterator();
    }

    private class NormIterator<Norm> implements Iterator<Norm>{

        int i = 0;
        @Override
        public boolean hasNext() { return i<N;}
        @Override
        public Norm next() { return (Norm) norms[i++];}
        @Override
        public void remove(){}

    }
    
}

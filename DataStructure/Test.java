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
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings (" unchecked ")
public class Test implements Iterable<Segment>{
    
    private final String name;
    private final double temperature;
    private final double humidity;
    private List<Segment> segments = new ArrayList();
    private Setup set;

    public double getTemp() {
        return this.temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getName() {
        return name;
    }

    public Test(String n, double t, double h) {
        this.name = n;
        this.temperature = t;
        this.humidity = h;
    }
    
    public void addSegment(Segment seg) {
       segments.add(seg);
    }
    
    public void deleteSegment( int index) {
        segments.remove(index);
    }
    
    public Segment getSegment( int index) {
        return segments.get(index);
    }

    @Override
    public Iterator<Segment> iterator() {
        return new SegmentIterator();
    }

    public void addSetup(Setup currentSetup) {
        this.set = currentSetup;
    }

    public Setup getSet() {
        return set;
    }

    
    private class SegmentIterator implements Iterator<Segment> {

        int i = 0;
        @Override
        public boolean hasNext() { return i<segments.size();}
        @Override
        public Segment next() { return (Segment) segments.get(i++);}
        @Override
        public void remove(){}


    }
    
    
}

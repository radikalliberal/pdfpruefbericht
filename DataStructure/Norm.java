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
public class Norm implements Iterable<Test>{
    
    private final String name;
    private final double temperature;
    private final double humidity;
    private List<Test> tests = new ArrayList();
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

    public Norm(String n, double t, double h) {
        this.name = n;
        this.temperature = t;
        this.humidity = h;
    }
    
    public void addTest(Test seg) {
       tests.add(seg);
    }
    
    public void deleteTest( int index) {
        tests.remove(index);
    }
    
    public Test getTest( int index) {
        return tests.get(index);
    }

    @Override
    public Iterator<Test> iterator() {
        return new TestIterator();
    }

    public void addSetup(Setup currentSetup) {
        this.set = currentSetup;
    }

    public Setup getSet() {
        return set;
    }

    
    private class TestIterator implements Iterator<Test> {

        int i = 0;
        @Override
        public boolean hasNext() { return i<tests.size();}
        @Override
        public Test next() { return (Test) tests.get(i++);}
        @Override
        public void remove(){}


    }
    
    
}

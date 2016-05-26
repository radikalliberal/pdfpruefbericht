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

public class device implements Comparable<device>{   
    private final String name;
    private final String serialnumber;
    private final String lastCalibration;
    private final String hersteller;

    public device(String hersteller, String name, String serialnumber, String lastCalibration) {
        this.hersteller = hersteller;
        this.name = name;
        this.serialnumber = serialnumber;
        this.lastCalibration = lastCalibration;
        
    }
    
    public String getHersteller() {
        return hersteller;
    }

    public String getName() {
        return name;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public String getLastCalibration() {
        return lastCalibration;
    }

    @Override
    public int compareTo(device dev) {
        if(name.equals(dev.getName()))
           return serialnumber.compareTo(dev.getSerialnumber()); 
        else return name.compareTo(dev.getName());        
    }

    public boolean equals(device dev) {
        return compareTo(dev) == 0;
    }
    
    
    
}

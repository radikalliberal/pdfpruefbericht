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

import java.util.ArrayList;
import java.util.List;

public class Setup {

    private String comment;
    public List<img> images;
    public List<device> devices;

    public Setup() {
        this.images = new ArrayList<img>();
        this.devices = new ArrayList<device>();
    }

    public String getComm() {
        return comment;
    }
    
    public void addComment(String comment) {
        this.comment = comment;
    }

//    public img[] getImages() {
//        return images;
//    }

//    public device[] getDevices() {
//        return devices;
//    }
    
//    private void refactorimages() {
//        int i = 0;
//        img temp[] = new img[NumberOfImages*2];
//        for(img s:images) 
//            temp[i++] = s;
//        images = temp;
//    }
//    
//    private void refactordevices() {
//        int i = 0;
//        device temp[] = new device[NumberOfDevices*2];
//        for(device s:devices) 
//            temp[i++] = s;
//        devices = temp;
//    }
    
//    @Override
//    public void addImage(img image) {
//        if(this.images.length == NumberOfImages) 
//            refactorimages();
//        this.images[NumberOfImages++] = image;
//    }
//   
//    public void addDevice(device dev) {
//        boolean existing = false;
//        for(device device : devices) {
//            if(device != null)
//            if(device.equals(dev)) {
//                existing = true;
//            }
//        }
//        if(!existing) {
//            if(devices.length == NumberOfDevices) 
//                refactordevices();
//            devices[NumberOfDevices++] = dev;
//        }
//    }
//
//    @Override
//    public Iterator<img> iterator() {
//           return new imgIterator();
//    }
//
//    private class imgIterator<img> implements Iterator<img>{
//
//        int i = 0;
//        @Override
//        public boolean hasNext() { return i<NumberOfImages;}
//        @Override
//        public img next() { return (img) images[i++];}
//        @Override
//        public void remove(){}
//
//    }
    
}

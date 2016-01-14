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

public class Test {
    
    private final String name;
    private String comment;
    private final String time;
    public List<img> images;
    private String log;
    public List<Parameter> parameters;
    private int columns;

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void addParameter(Parameter para) {
        parameters.add(para);
    }

    public Parameter getParameter(int index) {
        return parameters.get(index);
    }

    public void setLog(String log) {
        this.log = log;
    }


    public Test(String zeit, String Name) {
        this.name = Name;
        this.time = zeit;
        this.images = new ArrayList<>();
        this.parameters = new ArrayList<>();
    }

    public String getLog() {
        return log;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return time;
    }
    
    public void addComment(String com) {
        comment = com;
    }
    
    public void addImage(img image) {
        images.add(image);
    } 

     public img getImage(int index){
        return images.get(index);
    }
   
   
    
}

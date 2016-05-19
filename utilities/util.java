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

package utilities;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Utilities;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class util {
        
    public static float m2p(float value) {
        return Utilities.millimetersToPoints(value);
    }
    
    public static String getExtension(File f) {
        return f.getName().substring(f.getName().length()-3,f.getName().length());
    }
    
    public static File resize(File img, float quality) throws IOException, BadElementException {
        
        BufferedImage bufImage = ImageIO.read(img);
        int img_width = 600;
        int img_height = (int)(bufImage.getHeight()/((double)(bufImage.getWidth()/img_width)));
        int type = bufImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufImage.getType();
        
                
        BufferedImage resizedImage = new BufferedImage(img_height, img_width, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(bufImage, 0, 0, img_height,img_width, null);
	g.dispose();
        
        File compressedImageFile = new File("temp.jpg");
        OutputStream os =new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        writer.write(null, new IIOImage(resizedImage, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
        
        return compressedImageFile;
    }
    
    
}

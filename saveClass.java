/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import static oracle.jrockit.jfr.events.Bits.intValue;
import static paint.FXMLDocumentController.filepath;
import static paint.FXMLDocumentController.isSaved;

/**
 *  Handles save menu item.
 * @author apoe0
 */
public class saveClass {
    /**
     * Updates filepath and saves the file, overwriting the original.
     * @param canvas 
     */
    public void saveFile(Canvas canvas) {
        //save in the same location
        File file1 = filepath;
        //file1.renameTo(filename);
        if (file1 != null) {
            try {
                //make new writable to save, take snapshot
                WritableImage writable = new WritableImage(intValue(canvas.getWidth()),intValue(canvas.getHeight()));
                canvas.snapshot(null, writable);
                RenderedImage render = SwingFXUtils.fromFXImage(writable, null);
                ImageIO.write(render, "png", file1);
                isSaved = true;
            } catch (IOException ex){
                System.out.println("Error!!!!!");
                
            }
       
    }
    }
}

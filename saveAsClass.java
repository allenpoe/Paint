/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import static paint.FXMLDocumentController.isSaved;

/**
 *  Handles save as menu item.
 * @author apoe0
 */
public class saveAsClass {
    /**
     * Checks if the new file extension is the same as the original. If not,
     * inform user.  Then saves the file in the desired location.
     * @param canvas
     * @param timerBtn
     * @param menuItemSave 
     */
    public void saveAs(Canvas canvas,Button timerBtn,MenuItem menuItemSave) {
         //save as button
        FileChooser fileChooser = new FileChooser();
        //takes a screenshot of the canvas
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        File file = fileChooser.showSaveDialog(null);
       //as long as there is a file, save it
        if (file != null) {
            try {
                //update file information
                FXMLDocumentController.newName = file.getName();
                FXMLDocumentController.newExt = FXMLDocumentController.newName.substring(FXMLDocumentController.newName.lastIndexOf(".") + 1, file.getName().length());
                //checks if extensions match
                if (!FXMLDocumentController.newExt.equals(FXMLDocumentController.fileExtension)) {
                    Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
                    warning.setContentText("You are saving the image as a different file type, some losses may occur. Are you sure?");
                    //warning.showAndWait();
                    Optional<ButtonType> result = warning.showAndWait();
                    //if they x out do nothing
                    if (!result.isPresent()) {

                    } //if they hit ok save it
                    else if (result.get() == ButtonType.OK) {
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                        FXMLDocumentController.filepath = file;
                        FXMLDocumentController.isSaved = true;
                        timerBtn.setDisable(false);
                        menuItemSave.setDisable(false);
                    } //if they hit cancel do nothing
                    else if (result.get() == ButtonType.CANCEL) {

                    }
                } else {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                    FXMLDocumentController.filepath = file;
                    FXMLDocumentController.isSaved = true;
                    timerBtn.setDisable(false);
                }
            } catch (IOException ex) {
                Logger.getLogger(
                        PainT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

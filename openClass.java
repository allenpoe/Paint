/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.File;
import java.util.Optional;
import java.util.Stack;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import static paint.FXMLDocumentController.blackboardMode;
import static paint.FXMLDocumentController.fileExtension;
import static paint.FXMLDocumentController.fileName;
import static paint.FXMLDocumentController.filepath;
import static paint.FXMLDocumentController.isSaved;

/**
 *  Handles the open file menu item.
 * @author apoe0
 */
public class openClass {
    /**
     * Prompts user first if file is unsaved. Then opens the filechooser and 
     * writes the selected image on the canvas.  Resizes window size according 
     * to dimensions of image.  Keeps track of file name, location, and extension.
     * @param canvas
     * @param img
     * @param undoStack
     * @param menuItemSave
     * @param menuItemSaveAs
     * @param menuItemClose
     * @param timerBtn
     * @param moveBtn 
     */
    public void openFile(Canvas canvas,Image img,Stack undoStack,MenuItem menuItemSave,
    MenuItem menuItemSaveAs,MenuItem menuItemClose,ToggleButton timerBtn,ToggleButton moveBtn) {
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        final int widthModifier = 500;
        final int heightModifier = 400;
        final FileChooser fileChooser = new FileChooser();
        //check if saved
        if (isSaved) {
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                img = new Image(file.toURI().toString());
                //get image dimensions
                PainT.imageHeight = img.getHeight();
                PainT.imageWidth = img.getWidth();
                //draw image onto canvas
                graphicsContext.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
                filepath = file;
                //resize window
                PainT.window.setHeight(img.getHeight() + heightModifier);
                PainT.window.setWidth(img.getWidth() + widthModifier);
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
                //update file information
                fileName = file.getName();
                fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
                //Open Unit Test
                System.out.println("Opened " + fileExtension + " file");
                //disable unnecessary buttons
                blackboardMode = false;
                menuItemSave.setDisable(false);
                menuItemSaveAs.setDisable(false);
                menuItemClose.setDisable(false);
                moveBtn.setDisable(true);
                timerBtn.setDisable(false);
            }
        }
        else {
            //if not make an alert popup
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setContentText("You have not saved your changes. Are you sure you want to close the file?");
            //warning.showAndWait();
            Optional<ButtonType> result = warning.showAndWait();
            //if they hit the X nothing
            if(!result.isPresent()) {
                
            } 
            //if they hit ok perform normal action
            else if (result.get() == ButtonType.OK) {
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    img = new Image(file.toURI().toString());
                    //get image dimensions
                    PainT.imageHeight = img.getHeight();
                    PainT.imageWidth = img.getWidth();
                    //draw image onto canvas
                    graphicsContext.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
                    filepath = file;
                    //resize window
                    PainT.window.setHeight(img.getHeight() + heightModifier);
                    PainT.window.setWidth(img.getWidth() + widthModifier);
                    //push snap for undo
                    undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
                    //update file information
                    fileName = file.getName();
                    fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
                    //Open Unit Test
                    System.out.println("Opened " + fileExtension + " file");
                    //disable unnecessary buttons
                    blackboardMode = false;
                    isSaved = true;
                    menuItemSave.setDisable(false);
                    menuItemSaveAs.setDisable(false);
                    menuItemClose.setDisable(false);
                    moveBtn.setDisable(true);
                    timerBtn.setDisable(false);
                }
            }
            //if they hit cancel do nothing
            else if(result.get() == ButtonType.CANCEL) {
                
            }
        
        }
    }
    
}

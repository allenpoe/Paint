/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.Optional;
import java.util.Stack;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import static paint.FXMLDocumentController.isSaved;

/**
 *  Handles New menu item
 * @author apoe0
 */
public class newClass {
    /**
     * Checks if file is saved, if not prompt user.  Then resets canvas size
     * and clears it.
     * @param timerBtn
     * @param moveBtn
     * @param undoStack
     * @param menuItemSave
     * @param menuItemSaveAs
     * @param menuItemClose
     * @param canvas 
     */
    public void newFile(Button timerBtn,ToggleButton moveBtn,Stack undoStack,
    MenuItem menuItemSave,MenuItem menuItemSaveAs,MenuItem menuItemClose, Canvas canvas) {
        //check if file is saved first
        if (isSaved) {
            //make sure bb mode is off
            FXMLDocumentController.blackboardMode = false;
            //disable unnecessary buttons
            menuItemSave.setDisable(true);
            timerBtn.setDisable(true);
            moveBtn.setDisable(true);
            menuItemSaveAs.setDisable(false);
            menuItemClose.setDisable(false);
            //get graphicscontext as usual
            final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            //set dimensions of canvas back to normal if resized
            canvas.setHeight(FXMLDocumentController.defaultCanvasHeight);
            canvas.setWidth(FXMLDocumentController.defaultCanvasWidth);
            //clear the canvas.
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
        } else {
            //if not make an alert popup
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setContentText("This action will close your current file. "
                    + "You have not saved your changes. "
                    + "Are you sure you want to close the file?");
            //warning.showAndWait();
            Optional<ButtonType> result = warning.showAndWait();
            //if they hit the X nothing
            if(!result.isPresent()) {
                
            } 
            //if they hit ok perform normal action
            else if (result.get() == ButtonType.OK) {
                //make sure bb mode is off
                FXMLDocumentController.blackboardMode = false;
                //disable unnecessary buttons
                menuItemSave.setDisable(true);
                timerBtn.setDisable(true);
                moveBtn.setDisable(true);
                menuItemSaveAs.setDisable(false);
                menuItemClose.setDisable(false);
                //get graphicscontext as usual
                final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
                //clear the canvas.
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
                //the file is saved, update bool
                isSaved = true;
            }
            //if they hit cancel do nothing
            else if(result.get() == ButtonType.CANCEL) {
                
            }
        }
    }
}

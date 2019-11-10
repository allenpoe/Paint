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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import static paint.FXMLDocumentController.isSaved;
import static paint.FXMLDocumentController.blackboardMode;

/**
 *  Handles blackboard feature.
 * @author apoe0
 */
public class blackboardClass {
    /**
     * Fills canvas with black and inverts the eraser to be black.  Checks if the 
     * file has been saved, if not first prompts the user.
     * @param menuItemSave
     * @param menuItemClose
     * @param menuItemSaveAs
     * @param moveBtn
     * @param timerBtn
     * @param canvas
     * @param undoStack
     * @param cpBorder
     * @param cpFill 
     */
    public void startBlackboard(MenuItem menuItemSave, MenuItem menuItemClose, 
            MenuItem menuItemSaveAs, ToggleButton moveBtn, Button timerBtn, 
            Canvas canvas,Stack undoStack, ColorPicker cpBorder,ColorPicker cpFill) {
        if (isSaved) {
            blackboardMode = true;
            //button disables
            menuItemSave.setDisable(true);
            timerBtn.setDisable(true);
            moveBtn.setDisable(true);
            menuItemSaveAs.setDisable(false);
            menuItemClose.setDisable(false);
            //fill background of canvas with black
            final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            //can undo this change
            undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            //sets default colors for new mode
            cpBorder.setValue(Color.WHITE);
            cpFill.setValue(Color.TRANSPARENT);
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
                blackboardMode = true;
                //button disables
                menuItemSave.setDisable(true);
                timerBtn.setDisable(true);
                moveBtn.setDisable(true);
                menuItemSaveAs.setDisable(false);
                menuItemClose.setDisable(false);
                //fill background of canvas with black
                final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
                canvas.setHeight(FXMLDocumentController.defaultCanvasHeight);
                canvas.setWidth(FXMLDocumentController.defaultCanvasWidth);
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                //can undo this change
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
                //sets default colors for new mode
                cpBorder.setValue(Color.WHITE);
                cpFill.setValue(Color.TRANSPARENT);
                isSaved = true;
            }
            //if they hit cancel do nothing
            else if(result.get() == ButtonType.CANCEL) {
                
            }
        }
    }
}

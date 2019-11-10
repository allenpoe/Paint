/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.Optional;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import static paint.FXMLDocumentController.isSaved;

/**
 *  Handles closing a file through the menu item.
 * @author apoe0
 */
public class closeClass {
    //used for adjusting the window size
    private final int widthModifierC = 500;
    private final int heightModifierC = 400;
    
    /**
     * First checks if the file has been saved. If not, it first prompts the 
     * user if they want to continue. Otherwise, it clears the canvas,
     * and disables unnecessary buttons.
     * @param canvas
     * @param menuItemSave
     * @param menuItemSaveAs
     * @param menuItemClose
     * @param moveBtn
     * @param timerBtn 
     */
    public void closeFile(Canvas canvas, MenuItem menuItemSave,MenuItem menuItemSaveAs,
            MenuItem menuItemClose,ToggleButton moveBtn,Button timerBtn) {
        final int widthModifier = 300;
        final int heightModifier = 200;
        //checks if the file is saved
        if (FXMLDocumentController.isSaved) {
            //if it is clear the canvas
            final GraphicsContext gc = canvas.getGraphicsContext2D();
            final int none = 0;
            //gc.restore();
            gc.clearRect(none, none, canvas.getHeight()+heightModifier, canvas.getWidth()+widthModifier);
            menuItemSave.setDisable(true);
            menuItemSaveAs.setDisable(true);
            menuItemClose.setDisable(true);
            moveBtn.setDisable(true);
        } else {
            //if not make an alert popup
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setContentText("You have not saved your changes. Are you sure you want to close the file?");
            //warning.showAndWait();
            Optional<ButtonType> result = warning.showAndWait();
            //if they hit the X nothing
            if(!result.isPresent()) {
                
            } 
            //if they hit ok clear it
            else if(result.get() == ButtonType.OK) {
                final GraphicsContext gc = canvas.getGraphicsContext2D();
                final int none = 0;
                //gc.restore();
                gc.clearRect(none, none, canvas.getHeight()+heightModifierC, canvas.getWidth()+widthModifierC);
                menuItemSave.setDisable(true);
                menuItemSaveAs.setDisable(true);
                menuItemClose.setDisable(true);
                timerBtn.setDisable(false);
                moveBtn.setDisable(true);
            }
            //if they hit cancel do nothing
            else if(result.get() == ButtonType.CANCEL) {
                
            }
        }
    }
}

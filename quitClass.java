/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import static paint.FXMLDocumentController.isSaved;

/**
 *  Handles when the user exits the program via the menu item.
 * @author apoe0
 */
public class quitClass {
    /**
     * If the file is saved, exit the program. If it isn't saved, prompt the 
     * user first.
     */
    public void checkQuit() {
        //closes entire program
        if (FXMLDocumentController.isSaved) {
               Platform.exit();
        } else {
            //if not saved show alert
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setContentText("You have not saved your changes. Are you sure you want to quit?");
            //warning.showAndWait();
            Optional<ButtonType> result = warning.showAndWait();
            //if they x out do nothing
            if(!result.isPresent()) {
                
            } 
            //if they hit ok end it
            else if(result.get() == ButtonType.OK) {
                Platform.exit();
            }
            //if they hit cancel do nothing
            else if(result.get() == ButtonType.CANCEL) {
                
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;
import static paint.FXMLDocumentController.isSaved;

/**
 *  Handles when the user manually closes the program via the "red X."
 * @author apoe0
 */
public class manualCloseClass {
    /**
     * If the user closes the program manually, check if the file is saved. If
     * not, ask them if they really want to close it.
     */
    public void checkManualClose() {
        PainT.window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (FXMLDocumentController.isSaved) {
                    
                } else {
                    //if not make an alert popup
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setContentText("You have not saved your changes. Are you sure you want to close the file?");
            //warning.showAndWait();
            Optional<ButtonType> result = warning.showAndWait();
            //if they hit the X nothing
            if(!result.isPresent()) {
                event.consume();
            } 
            //if they hit ok clear it
            else if(result.get() == ButtonType.OK) {
                
            }
            //if they hit cancel do nothing
            else if(result.get() == ButtonType.CANCEL) {
                event.consume();
            }
          }
        }
            
              
      });
    }
}

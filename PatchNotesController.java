/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author apoe0
 */
public class PatchNotesController implements Initializable {

    @FXML
    private TextArea textarea;
    @FXML
    private Button close;

    /**
     * Opens a new window that reads from the patch notes text file and shows it
     * in a text area.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        textarea.setFont(Font.font("Comic Sans MS"));
        try {
        Scanner s = new Scanner(new File("C:\\Users\\apoe0\\OneDrive\\Documents\\NetBeansProjects\\PainT\\src\\paint\\patchnotes.txt")).useDelimiter("  ");
        while (s.hasNext()) {
            if (s.hasNextInt()) { // check if next token is an int
                textarea.appendText(s.nextInt() + " "); // display the found integer
            } else {
                textarea.appendText(s.next() + " " + "\n"); // else read the next token
            }
        }
    } catch (FileNotFoundException ex) {
        System.err.println(ex);
    }
 }
    /**
     * Checks if close button is pressed and closes the window.
     * @param event 
     */
    @FXML
    private void handleclose(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
    // do what you have to do
        stage.close();
    }
    }    
    


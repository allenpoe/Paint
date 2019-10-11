/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Platform;
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up text area when it opens
        /*
        textarea.setEditable(false);
        textarea.setWrapText(true);
        textarea.setText("Welcome to the Help Menu!");
        textarea.appendText("\n\nFile contains opening and saving functionality"); 
        textarea.appendText("\nEdit contains free drawing and line drawing");
*/
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

    @FXML
    private void handleclose(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
    // do what you have to do
        stage.close();
    }
    }    
    


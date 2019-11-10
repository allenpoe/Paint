
package paint;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *  Main file of the program.
 * @author apoe0
 */
public class PainT extends Application {
    //variables to be used in controllers
     static public Stage window;
     public int WindowWidth = 1000;
     public int WindowHeight = 900;
     
    static public double imageWidth;
    static public double imageHeight;
    /**
     * Switches path to the controller for scene builder. Shows the stage.
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        //load fxml file for project
        window=stage;
        
        //reroute to controller for project
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        loader.setController("FXMLDocumentController.java");
        //set window title
        stage.setTitle("Paint");
        
        Scene scene = new Scene(root);
        
        stage.sizeToScene();
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

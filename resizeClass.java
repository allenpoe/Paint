/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.Optional;
import java.util.Stack;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import static paint.FXMLDocumentController.isSaved;

/**
 *  Handles the resize menu item.
 * @author apoe0
 */
public class resizeClass {
    /**
     * Shows custom dialog box with height and width inputs. Sets canvas 
     * dimensions to new entered values. Includes unit test.
     * @param canvas
     * @param undoStack 
     */
    public void resizeCanvas(Canvas canvas, Stack undoStack) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int inset1 = 20;
        int inset2 = 150;
        int inset3 = 10;
        //new dialog box with two inputs
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        //setup button for completion
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        //use gridapne to format dialog box
        GridPane gridPane = new GridPane();
        //misc formatting
        gridPane.setHgap(inset3);
        gridPane.setVgap(inset3);
        gridPane.setPadding(new Insets(inset1, inset2, inset3, inset3));
        //textfield for height to be entered
        TextField height = new TextField();
        height.setPromptText("Height");
        //textfield for width to be entered
        TextField width = new TextField();
        width.setPromptText("Width");
        //add fields to the grid
        gridPane.add(height, 0, 0);
        gridPane.add(new Label("Height | Width"), 1, 0);
        gridPane.add(width, 2, 0);
        dialog.getDialogPane().setContent(gridPane);
                //get the results as a pair
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        return new Pair<>(height.getText(), width.getText());
                    }
                return null;
                });
                Optional<Pair<String, String>> result = dialog.showAndWait();
                 result.ifPresent(pair -> {
                    //convert the pair into separate doubles
                    final double newHeight = Integer.parseInt(pair.getKey());
                    final double newWidth = Integer.parseInt(pair.getValue());
                    //set the canvas to its new dimensions
                    canvas.setHeight(newHeight);
                    canvas.setWidth(newWidth);
                    //Resize Unit Test
                    if (canvas.getWidth()==newWidth) {
                        System.out.println("New Width is equal: " + Double.toString(newWidth));
                    }
                    if (canvas.getHeight()==newHeight) {
                        System.out.println("New Height is equal: " + Double.toString(newHeight));
                    }
                    isSaved = false;
                    gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight()); 
                });
    }
}

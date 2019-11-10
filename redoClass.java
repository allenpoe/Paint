/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.Stack;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *  Handles the redo menu item.
 * @author apoe0
 */
public class redoClass {
    /**
     * Loads last item onto canvas when clicked.  Updates undo history stack
     * and save tracker.
     * @param canvas
     * @param redoStack
     * @param undoStack 
     */
    public void redoItem(Canvas canvas, Stack redoStack, Stack undoStack) {
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        //if the history is empty do nothing
        if (redoStack.empty()) {
            
        } else {
            //insert last saved snap onto canvas
            gc.drawImage((Image)redoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight()); 
            //load into undo
            undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            //delete used redo item
            redoStack.pop();
            //update save tracker
            FXMLDocumentController.isSaved = false;
        }
    }
}

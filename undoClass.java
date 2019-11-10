/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import static paint.FXMLDocumentController.isSaved;

/**
 *  Handles the undo menu item.
 * @author apoe0
 */
public class undoClass {
    /**
     * Loads the last element of the undo history stack into the canvas.
     * @param undoStack
     * @param canvas
     * @param redoStack 
     */
    public void undoItem(Stack undoStack, Canvas canvas, Stack redoStack) {
        //if stack is empty do nothing
        if (undoStack.empty()) {
            
        } else {
            final GraphicsContext gc = canvas.getGraphicsContext2D();
            //load last element into canvas
            gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight()); 
            //add used element to redo stack
            redoStack.push(undoStack.lastElement());
            //delete used element
            undoStack.pop();
            //update saved tracker
            isSaved = false;
        }
    }
}

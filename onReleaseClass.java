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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import static paint.FXMLDocumentController.blackboardMode;
import static paint.FXMLDocumentController.numSides;
import static paint.FXMLDocumentController.polyStartX;
import static paint.FXMLDocumentController.polyStartY;
import static paint.FXMLDocumentController.selImg;

/**
 *  Handles when the user releases their mouse on drags.
 * @author apoe0
 */
public class onReleaseClass {
    /**
     * Checks if the user releases the mouse after dragging.  Then it controls
     * which operation needs to be completed based on which toggle button is 
     * active.
     * @param gc
     * @param canvas
     * @param lineBtn
     * @param rectBtn
     * @param line
     * @param rect
     * @param status
     * @param eraserBtn
     * @param circleBtn
     * @param circle
     * @param elps
     * @param txtBoxBtn
     * @param fontsizeBtn
     * @param fontBox
     * @param text
     * @param arc
     * @param polyBtn
     * @param arcBtn
     * @param selectBtn
     * @param copyBtn
     * @param elpsBtn
     * @param selRect
     * @param spinner
     * @param pencilBtn
     * @param cpBorder
     * @param cpFill
     * @param polySides
     * @param moveBtn
     * @param undoStack 
     */
    public void checkRelease(GraphicsContext gc,Canvas canvas,ToggleButton lineBtn,
    ToggleButton rectBtn,Line line,Rectangle rect,Label status,ToggleButton eraserBtn,
    ToggleButton circleBtn,Circle circle,Ellipse elps,ToggleButton txtBoxBtn,Spinner fontsizeBtn,
    ChoiceBox fontBox,TextArea text,Arc arc,ToggleButton polyBtn,ToggleButton arcBtn,
    ToggleButton selectBtn,ToggleButton copyBtn,ToggleButton elpsBtn,
    Rectangle selRect,Spinner spinner,ToggleButton pencilBtn,ColorPicker cpBorder,
    ColorPicker cpFill,Spinner polySides,ToggleButton moveBtn,Stack undoStack) {
        canvas.setOnMouseReleased(e->{
            //-----------------line release-----------------
            if(lineBtn.isSelected()) {
                //end line
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            //-----------------rectangle release------------
            else if(rectBtn.isSelected()) {
                //get newest snap of canvas
                gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight());
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                //checks if it is dragged the other direction
                if(rect.getX() > e.getX()) {
                    rect.setX(e.getX());
                }
                if(rect.getY() > e.getY()) {
                    rect.setY(e.getY());
                }
                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));

            }
            //------------------bb eraser release-----------------
            else if(eraserBtn.isSelected() && blackboardMode) {
                gc.closePath();
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            //-------------------normal eraser release----------------
            else if(eraserBtn.isSelected() && !blackboardMode) {
                gc.closePath();
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            //-------------------circle release-----------------
            else if(circleBtn.isSelected()) {
                //get newest snap of canvas
                gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight());
                circle.setRadius((Math.abs(e.getX() - circle.getCenterX()) + Math.abs(e.getY() - circle.getCenterY())) / 2);
                //checks if it is dragged the other direction
                if(circle.getCenterX() > e.getX()) {
                    circle.setCenterX(e.getX());
                }
                if(circle.getCenterY() > e.getY()) {
                    circle.setCenterY(e.getY());
                }
                gc.fillOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
                gc.strokeOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            //------------------ellipse release----------------
            else if(elpsBtn.isSelected()) {
                //get newest snap of canvas
                gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight());
                elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
                elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));
                //checks if it is dragged the other direction
                if(elps.getCenterX() > e.getX()) {
                    elps.setCenterX(e.getX());
                }
                if(elps.getCenterY() > e.getY()) {
                    elps.setCenterY(e.getY());
                }
                gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            //-----------------pencil release---------------------
            else if (pencilBtn.isSelected()) {
                gc.closePath();
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            //------------------arc release-----------------------
            else if (arcBtn.isSelected()) {
                //get newest snap of canvas
                gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight());
                double angle = 180;
                double width = Math.abs((e.getX() - arc.getCenterX()));
                double height = Math.abs((e.getY() - arc.getCenterY()));
                //checks if it is dragged the other direction
                if (arc.getCenterX() > e.getX()) {
                    arc.setCenterX(e.getX());
                }
                if (arc.getCenterY() > e.getY()) {
                    arc.setCenterY(e.getY());
                }
                gc.strokeArc(e.getX(), e.getY(), width, height, angle, angle, ArcType.OPEN);
               //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            //------------------polygon release--------------------
            else if (polyBtn.isSelected()) {
                numSides = (int) polySides.getValue();
                double radius = ((Math.abs(e.getX() - polyStartX) + Math.abs(e.getY() - polyStartY)) / 2);
                //checks if it is dragged the other direction
                if (polyStartX > e.getX()) {
                    polyStartX = e.getX();
                }
                if (polyStartY > e.getY()) {
                    polyStartY = e.getY();
                }
                //new array for sides
                double[] xSides = new double[numSides];
                double[] ySides = new double[numSides];
                //apply sides to polygon
                for (int i = 0; i<numSides; i++) {
                    xSides[i] = radius*Math.cos(2*i*Math.PI/numSides) + polyStartX;
                    ySides[i] = radius*Math.sin(2*i*Math.PI/numSides) + polyStartY;
                }
                //draw polygon
                gc.strokePolygon(xSides, ySides, numSides);
                gc.fillPolygon(xSides, ySides, numSides);
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            //------------------select release--------------------
            else if (selectBtn.isSelected()) {
                selRect.setWidth(Math.abs(e.getX() - selRect.getX()));
                selRect.setHeight(Math.abs(e.getY() - selRect.getY()));
                //checks if it is dragged the other direction
                if(selRect.getX() > e.getX()) {
                    selRect.setX(e.getX());
                }
                if (selRect.getY() > e.getY()) {
                    selRect.setY(e.getY());
                }
                //get a new snap
                WritableImage writableImage = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
                canvas.snapshot(null, writableImage);
                //fill selImg with new selection
                gc.fillRect(selRect.getX(), selRect.getY(), selRect.getWidth(), selRect.getHeight());
                PixelReader pixelReader = writableImage.getPixelReader();
                selImg = new WritableImage(pixelReader, (int)selRect.getX(), (int)selRect.getY(), (int)selRect.getWidth(), (int)selRect.getHeight());
                //clear selected area
                gc.clearRect(selRect.getX(), selRect.getY(), selRect.getWidth(), selRect.getHeight());
                //enable move button
                moveBtn.setDisable(false);
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            //------------------copy release-----------------------
            else if (copyBtn.isSelected()) {
                selRect.setWidth(Math.abs(e.getX() - selRect.getX()));
                selRect.setHeight(Math.abs(e.getY() - selRect.getY()));
                //checks if it is dragged the other direction
                if(selRect.getX() > e.getX()) {
                    selRect.setX(e.getX());
                }
                if (selRect.getY() > e.getY()) {
                    selRect.setY(e.getY());
                }
                //get a new snap
                WritableImage writableImage = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
                canvas.snapshot(null, writableImage);
                //fill selImg with new copy area
                PixelReader pixelReader = writableImage.getPixelReader();
                selImg = new WritableImage(pixelReader, (int)selRect.getX(), (int)selRect.getY(), (int)selRect.getWidth(), (int)selRect.getHeight());
                //enable move button
                moveBtn.setDisable(false);
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            //---------------------move release-----------------
            else if (moveBtn.isSelected()) {
                gc.drawImage(selImg, e.getX(), e.getY());
            }
            //--------------------text box release--------------
            else if (txtBoxBtn.isSelected()) {
                //push snap for undo
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
        });
    }
}

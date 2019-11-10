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
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import static paint.FXMLDocumentController.blackboardMode;
import static paint.FXMLDocumentController.chosenColor;
import static paint.FXMLDocumentController.fillColor;
import static paint.FXMLDocumentController.isSaved;
import static paint.FXMLDocumentController.penWidth;
import static paint.FXMLDocumentController.polyStartX;
import static paint.FXMLDocumentController.polyStartY;
import static paint.FXMLDocumentController.selImg;
import static paint.FXMLDocumentController.tmpSnap;
import static paint.FXMLDocumentController.numSides;
import static paint.FXMLDocumentController.penWidth;

/**
 *  Handles when the user drags the mouse after pressing on the canvas.
 * @author apoe0
 */
public class onDragClass {
    /**
     * Checks if the canvas is dragged on.  If it is, checks which tool is selected.
     * Controls which operation is completed depending on which button is selected.
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
    public void checkDrag(GraphicsContext gc,Canvas canvas,ToggleButton lineBtn,
    ToggleButton rectBtn,Line line,Rectangle rect,Label status,ToggleButton eraserBtn,
    ToggleButton circleBtn,Circle circle,Ellipse elps,ToggleButton txtBoxBtn,Spinner fontsizeBtn,
    ChoiceBox fontBox,TextArea text,Arc arc,ToggleButton polyBtn,ToggleButton arcBtn,
    ToggleButton selectBtn,ToggleButton copyBtn,ToggleButton elpsBtn,
    Rectangle selRect,Spinner spinner,ToggleButton pencilBtn,ColorPicker cpBorder,
    ColorPicker cpFill,Spinner polySides,ToggleButton moveBtn,Stack undoStack) {
        canvas.setOnMouseDragged(e->{
            //-----------------------normal eraser drag-------------------
            if(eraserBtn.isSelected() && !blackboardMode){
                //set width and color
                penWidth = (int) spinner.getValue();
                gc.setLineWidth(penWidth);
                chosenColor = cpBorder.getValue();
                gc.setStroke(Color.WHITE);
                //line to mouse
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
            //-----------------------eraser with blackboard drag---------------
            else if (eraserBtn.isSelected() && blackboardMode) {
                //set width and color
                penWidth = (int) spinner.getValue();
                gc.setLineWidth(penWidth);
                chosenColor = cpBorder.getValue();
                gc.setStroke(Color.BLACK);
                //line to mouse
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
            //-----------------------pencil drag-------------------------
            else if (pencilBtn.isSelected()) {
                //line to mouse
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
            //--------------------rectangle drag-------------------------
            else if (rectBtn.isSelected()) {
                //take snapshot for shape preview
                gc.drawImage(tmpSnap, 0, 0);
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
            }
            //-------------------circle drag-------------------------------
            else if (circleBtn.isSelected()) {
                //take snapshot for shape preview
                gc.drawImage(tmpSnap, 0, 0);
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
            }
            //---------------------ellipse drag----------------------------
            else if (elpsBtn.isSelected()){
                //take snapshot for shape preview
                gc.drawImage(tmpSnap, 0, 0);
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
            }
            //---------------------arc drag------------------------------
            else if (arcBtn.isSelected()) {
                //take snapshot for shape preview
                gc.drawImage(tmpSnap, 0, 0);
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
                  
            }
            //--------------------line drag------------------------------
            else if (lineBtn.isSelected()) {
                //take snapshot for shape preview
                gc.drawImage(tmpSnap, 0, 0);
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            }
            //----------------------polygon drag-------------------------
            else if (polyBtn.isSelected()) {
                //take snapshot for shape preview
                gc.drawImage(tmpSnap, 0, 0);
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
                //create sides
                for (int i = 0; i<numSides; i++) {
                    xSides[i] = radius*Math.cos(2*i*Math.PI/numSides) + polyStartX;
                    ySides[i] = radius*Math.sin(2*i*Math.PI/numSides) + polyStartY;
                }
                
                gc.strokePolygon(xSides, ySides, numSides);
                gc.fillPolygon(xSides, ySides, numSides);
            }
            //----------------------move drag-----------------------------
            else if (moveBtn.isSelected()) {
                //take snapshot for shape preview
                gc.drawImage(tmpSnap, 0, 0);
                gc.drawImage(selImg, e.getX(), e.getY());
                status.setText("Paste");
            }
            //----------------------text box drag--------------------------
            else if (txtBoxBtn.isSelected()) {
                //take snapshot for shape preview
                gc.drawImage(tmpSnap, 0, 0);
                gc.fillText(text.getText(), e.getX(), e.getY());
                gc.strokeText(text.getText(), e.getX(), e.getY());
            }
        });
    }
     
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import static paint.FXMLDocumentController.blackboardMode;
import static paint.FXMLDocumentController.chosenColor;
import static paint.FXMLDocumentController.fillColor;
import static paint.FXMLDocumentController.isSaved;
import static paint.FXMLDocumentController.penWidth;
import static paint.FXMLDocumentController.tmpSnap;
import static paint.FXMLDocumentController.polyStartX;
import static paint.FXMLDocumentController.polyStartY;

/**
 *
 * @author apoe0
 */
public class onPressClass {
    public void checkPress(GraphicsContext gc,Canvas canvas,ToggleButton lineBtn,
    ToggleButton rectBtn,Line line,Rectangle rect,Label status,ToggleButton eraserBtn,
    ToggleButton circleBtn,Circle circle,Ellipse elps,ToggleButton txtBoxBtn,Spinner fontsizeBtn,
    ChoiceBox fontBox,TextArea text,Arc arc,ToggleButton polyBtn,ToggleButton arcBtn,
    ToggleButton selectBtn,ToggleButton copyBtn,ToggleButton elpsBtn,
    Rectangle selRect,Spinner spinner,ToggleButton pencilBtn,ColorPicker cpBorder,
    ColorPicker cpFill) {
        canvas.setOnMousePressed((MouseEvent e)->{
            tmpSnap = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
            canvas.snapshot(null, tmpSnap);
            //-----------------line press----------------
            if(lineBtn.isSelected()) {
                setupDraw(gc,spinner,cpFill,cpBorder);
                line.setStartX(e.getX());
                line.setStartY(e.getY());
                status.setText("Line");
            }
            //----------------rectangle press------------
            else if(rectBtn.isSelected()) {
                //preliminary writing setup
                setupDraw(gc,spinner,cpFill,cpBorder);
                //start rectangle on mouse
                rect.setX(e.getX());
                rect.setY(e.getY());
                status.setText("Rectangle");
            }
            //----------------blackboard eraser press-------------
            else if(eraserBtn.isSelected() && blackboardMode) {
                isSaved = false;
                penWidth = (int) spinner.getValue();
                gc.setLineWidth(penWidth);
                chosenColor = cpBorder.getValue();
                gc.setStroke(Color.BLACK);
                //start new path to postition
                gc.beginPath();
                gc.moveTo(e.getX(), e.getY());
                gc.stroke();
                status.setText("Eraser");
            }
            //----------------normal eraser press-----------------
            else if(eraserBtn.isSelected() && !blackboardMode) {
                setupDraw(gc,spinner,cpFill,cpBorder);
                gc.setStroke(Color.WHITE);
                //start new path to postition
                gc.beginPath();
                gc.moveTo(e.getX(), e.getY());
                gc.stroke();
                status.setText("Eraser");
            }
            //---------------circle press------------------------
            else if(circleBtn.isSelected()) {
                //writing setup
                setupDraw(gc,spinner,cpFill,cpBorder);
                //set circle center on mouse press
                circle.setCenterX(e.getX());
                circle.setCenterY(e.getY());
                status.setText("Circle");
            }
            //----------------ellipse press---------------------
            else if(elpsBtn.isSelected()) {
                setupDraw(gc,spinner,cpFill,cpBorder);
                //set ellipse center on mouse
                elps.setCenterX(e.getX());
                elps.setCenterY(e.getY());
                status.setText("Ellipse");
            }
            //-----------------pencil press-------------------
            else if (pencilBtn.isSelected()) {
                setupDraw(gc,spinner,cpFill,cpBorder);
                //start new path to postition
                gc.beginPath();
                gc.moveTo(e.getX(), e.getY());
                gc.stroke();
                status.setText("Pencil");
            }
            //-----------------text box press-----------------
            else if(txtBoxBtn.isSelected()) {
                isSaved = false;
                int def = 1;
                gc.setLineWidth(def);
                gc.setStroke(cpBorder.getValue());
                gc.setFill(cpFill.getValue());
                //checking which font is selected
                if (fontBox.getValue().equals("Comic Sans MS")) {
                    //write in comic sans
                    gc.setFont(Font.font("Comic Sans MS", (int) fontsizeBtn.getValue()));
                }
                else if (fontBox.getValue().equals("Times New Roman")) {
                    //write in times new roman
                    gc.setFont(Font.font("Times New Roman", (int) fontsizeBtn.getValue()));
                }
                else {
                    //write in cursive
                    gc.setFont(Font.font("Edwardian Script ITC", (int) fontsizeBtn.getValue()));
                }
                gc.fillText(text.getText(), e.getX(), e.getY());
                gc.strokeText(text.getText(), e.getX(), e.getY());
                //undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
                status.setText("Text");
            }
            //------------------arc press----------------------
            else if(arcBtn.isSelected()) {
                //preliminary writing setup
                setupDraw(gc,spinner,cpFill,cpBorder);
                //start rectangle on mouse
                arc.setCenterX(e.getX());
                arc.setCenterY(e.getY());
                status.setText("Arc");
            }
            //------------------polygon press-------------------
            else if (polyBtn.isSelected()) {
                //preliminary writing setup
                setupDraw(gc,spinner,cpFill,cpBorder);
                //start polygon at press
                polyStartX = e.getX();
                polyStartY = e.getY();
                status.setText("Polygon");
            }
            //-----------------select press---------------------
            else if (selectBtn.isSelected()) {
                //preliminary writing setup
                setupDraw(gc,spinner,cpFill,cpBorder);
                //start selection at press
                selRect.setX(e.getX());
                selRect.setY(e.getY());
                status.setText("Select");
            }
            //-----------------copy press------------------------
            else if (copyBtn.isSelected()) {
                //preliminary writing setup
                setupDraw(gc,spinner,cpFill,cpBorder);
                //start selection at press
                selRect.setX(e.getX());
                selRect.setY(e.getY());
                status.setText("Copy");
            }
        });
    }
    private void setupDraw(GraphicsContext gc,Spinner spinner, ColorPicker cpFill,
        ColorPicker cpBorder) {
        isSaved = false;
        penWidth = (int) spinner.getValue();
        chosenColor = cpBorder.getValue();
        fillColor = cpFill.getValue();
        gc.setStroke(chosenColor);
        gc.setFill(fillColor);
        gc.setLineWidth(penWidth);
    }
}

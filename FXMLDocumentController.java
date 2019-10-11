/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import static oracle.jrockit.jfr.events.Bits.intValue;

/**
 *
 * @author apoe0
 */
public class FXMLDocumentController implements Initializable {
    //Private Variables non FXML related
    private File filepath;
    private String filename;
    private int penWidth = 1;
    private Color chosenColor;
    private Color fillColor;
    private boolean isSaved = true;
    Stack<Shape> undoHistory = new Stack();
    Stack<Shape> redoHistory = new Stack();
    Stack<WritableImage> undoStack = new Stack();
    Stack<WritableImage> redoStack = new Stack();
    ToggleGroup toggleGroup = new ToggleGroup();
    ToggleGroup timerGroup = new ToggleGroup();
    Image img;
    WritableImage tmpSnap;
    WritableImage selImg;
    double polyStartX;
    double polyStartY;
    private int numSides;
    private String fileExtension;
    private String fileName;
    private String newExt;
    private String newName;
    private static final Integer STARTTIME = 15;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    Timeline autosaveTimeline;
    private boolean saveFlag = false;
    
    @FXML
    private MenuItem menuItemNew;
    @FXML
    private MenuItem menuItemOpen;
    @FXML
    private MenuItem menuItemSave;
    @FXML
    private MenuItem menuItemSaveAs;
    @FXML
    private MenuItem menuItemQuit;
    @FXML
    private ImageView imgView;
    @FXML
    private VBox Vbox;
    @FXML
    private ColorPicker colorpicker;
    @FXML
    private Canvas canvas;
    @FXML
    private Spinner<Integer> spinner;
    @FXML
    private MenuItem menuItemNotes;
    @FXML
    private MenuItem menuItemClose;
    @FXML
    private ScrollPane sp;
    @FXML
    private MenuItem menuItemUndo;
    @FXML
    private ToggleButton rectBtn;
    @FXML
    private ColorPicker cpFill;
    @FXML
    private ToggleButton circleBtn;
    @FXML
    private ToggleButton elpsBtn;
    @FXML
    private StackPane stackpane;
    @FXML
    private BorderPane bp;
    @FXML
    private ToggleButton eraserBtn;
    @FXML
    private ToggleButton lineBtn;
    @FXML
    private ToggleButton pencilBtn;
    @FXML
    private ToggleButton dropperBtn;
    @FXML
    private MenuItem redoBtn;
    @FXML
    private MenuItem menuItemTips;
    @FXML
    private MenuItem resizeBtn;
    @FXML
    private ToggleButton txtBoxBtn;
    @FXML
    private TextArea text;
    @FXML
    private ToggleButton arcBtn;
    @FXML
    private ToggleButton selectBtn;
    @FXML
    private ToggleButton polyBtn;
    @FXML
    private Spinner<Integer> polySides;
    @FXML
    private ToggleButton moveBtn;
    @FXML
    private Button timerBtn;

    
    
    //------------------------------NEW-------------------------------------------
    @FXML
    private void handlemenuItemNew(ActionEvent event) {
         final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
         final int zero = 0;
        //access blank picture
        File file = new File("C:\\Users\\apoe0\\OneDrive\\Documents\\NetBeansProjects\\PainT\\src\\paint\\white.jpg");
        Image blank = new Image(file.toURI().toString());
        graphicsContext.drawImage(blank, zero, zero, canvas.getWidth(), canvas.getHeight()); 
         undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
    }
    //-------------------------------SAVE---------------------------------------
    @FXML
    private void handlemenuItemSave(ActionEvent event) {
        //save in the same location
        File file1 = filepath;
        //file1.renameTo(filename);
        if (file1 != null) {
            try {
                //make new writable to save, take snapshot
                WritableImage writable = new WritableImage(intValue(canvas.getWidth()),intValue(canvas.getHeight()));
                canvas.snapshot(null, writable);
                RenderedImage render = SwingFXUtils.fromFXImage(writable, null);
                ImageIO.write(render, "png", file1);
                isSaved = true;
            } catch (IOException ex){
                System.out.println("Error!!!!!");
                
            }
       
    }
    }
    //--------------------------------SAVE AS-------------------------------------
    @FXML
    private void handlemenuItemSaveAs(ActionEvent event) {
         //save as button
        FileChooser fileChooser = new FileChooser();
        //takes a screenshot of the canvas
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        File file = fileChooser.showSaveDialog(null);
       //as long as there is a file, save it
        if (file != null) {
                    try {
                        newName = file.getName();
                        newExt = newName.substring(newName.lastIndexOf(".") + 1, file.getName().length());
                        //System.out.println("file name: " +newName);
                        if (!newExt.equals(fileExtension)) {
                            //System.out.println("newExt: " +newExt);
                            //System.out.println("oldExt: " +fileExtension);
                            Alert warning = new Alert(AlertType.CONFIRMATION);
                            warning.setContentText("You are saving the image as a different file type, some losses may occur. Are you sure?");
                            //warning.showAndWait();
                            Optional<ButtonType> result = warning.showAndWait();
                            //if they x out do nothing
                            if(!result.isPresent()) {
                
                            } 
                            //if they hit ok save it
                            else if(result.get() == ButtonType.OK) {
                                ImageIO.write(SwingFXUtils.fromFXImage(image,null), "png", file);
                                isSaved=true;
                            }
                            //if they hit cancel do nothing
                            else if(result.get() == ButtonType.CANCEL) {
                                
                            }
                        }
                        else {
                            ImageIO.write(SwingFXUtils.fromFXImage(image,null), "png", file);
                            isSaved=true;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(
                            PainT.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }
        
    }
//---------------------------------------------QUIT------------------------------
    @FXML
    private void handlemenuItemQuit(ActionEvent event) {
        //closes entire program
        if (isSaved) {
               Platform.exit();
        } else {
            //if not saved show alert
            Alert warning = new Alert(AlertType.CONFIRMATION);
            warning.setContentText("You have not saved your changes. Are you sure you want to quit?");
            //warning.showAndWait();
            Optional<ButtonType> result = warning.showAndWait();
            //if they x out do nothing
            if(!result.isPresent()) {
                
            } 
            //if they hit ok end it
            else if(result.get() == ButtonType.OK) {
                Platform.exit();
            }
            //if they hit cancel do nothing
            else if(result.get() == ButtonType.CANCEL) {
                
            }
        }
    }
    //--------------------------------------OPEN----------------------------------
    @FXML
    private void handlemenuItemOpen(ActionEvent event) {
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        final int widthModifier = 500;
        final int heightModifier = 400;
        final FileChooser fileChooser = new FileChooser();
        //extensions for file chooser
        //fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".png", ".jpg"),new FileChooser.ExtensionFilter("PNG", "*.png"),new FileChooser.ExtensionFilter("JPEG", "*.jpg"));
                    
        
                    File file = fileChooser.showOpenDialog(null);
                    if (file != null) {
                        img = new Image(file.toURI().toString());
                        //get image dimensions
                        PainT.imageHeight = img.getHeight();
                        PainT.imageWidth = img.getWidth();
                        //resize canvas with image size
                        canvas.setHeight(img.getHeight()+heightModifier);
                        canvas.setWidth(img.getWidth()+widthModifier);
                        //PainT.window.setWidth(PainT.imageWidth+widthModifier);
                        //PainT.window.setHeight(PainT.imageHeight+heightModifier);
                        //Vbox.minHeightProperty().bind(img.heightProperty());
                        //Vbox.minWidthProperty().bind(img.widthProperty());
                        graphicsContext.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight()); 
                        //imgView.setImage(img);
                        //gc.drawImage(img, imgWt, imgHt);
                        filepath = file;
                        //sp.fitToHeightProperty();
                        //sp.fitToWidthProperty();
                        //sp.minWidth(img.getWidth());
                        //sp.minHeight(img.getHeight());
                        //bp.minHeight(img.getHeight());
                        //bp.minWidth(img.getWidth());
                        //stackpane.minHeight(img.getHeight());
                        //stackpane.minWidth(img.getHeight());
                        //if (img.getHeight()>PainT.window.getHeight()) {
                            PainT.window.setHeight(img.getHeight());
                        //}
                        //if (img.getWidth()>PainT.window.getWidth()) {
                            PainT.window.setWidth(img.getWidth());
                            undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
                        //}
                        fileName = file.getName();
                        fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
                        //Open Unit Test
                        System.out.println("Opened " +fileExtension + " file");
                    }
       
    }
  
    //---------------------------------INITIALIZE-------------------------------
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //set up width spinner values
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
        polySides.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 100));
       
        //Keyboard shortcuts for File menu items
        menuItemOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        menuItemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        menuItemClose.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        menuItemQuit.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        //add shapes to toggle group
        //rectBtn.setToggleGroup(toggleGroup);
        circleBtn.setToggleGroup(toggleGroup);
        elpsBtn.setToggleGroup(toggleGroup);
        eraserBtn.setToggleGroup(toggleGroup);
        lineBtn.setToggleGroup(toggleGroup);
        rectBtn.setToggleGroup(toggleGroup);
        dropperBtn.setToggleGroup(toggleGroup);
        pencilBtn.setToggleGroup(toggleGroup);
        txtBoxBtn.setToggleGroup(toggleGroup);
        arcBtn.setToggleGroup(toggleGroup);
        polyBtn.setToggleGroup(toggleGroup);
        selectBtn.setToggleGroup(toggleGroup);
        moveBtn.setToggleGroup(toggleGroup);
        //menuItemLine.setToggleGroup(toggleGroup);
        
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        colorpicker.setValue(Color.BLACK);
        cpFill.setValue(Color.TRANSPARENT);
        colorpicker.getCustomColors().addAll(Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN,Color.GRAY,Color.ORANGE);
        cpFill.getCustomColors().addAll(Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN,Color.GRAY,Color.ORANGE);
        
        text.setFont(Font.font("Comic Sans MS"));
        
        Line line = new Line();
        Rectangle rect = new Rectangle();
        Rectangle selRect = new Rectangle();
        Circle circle = new Circle();
        Ellipse elps = new Ellipse();
        Arc arc = new Arc();
//----------------------------------AUTOSAVE-----------------------------------
      timerBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                //make this a toggle type button, if it is on turn it off, vice versa
                if (saveFlag==true) {
                    saveFlag= false;
                    autosaveTimeline.pause();
                    System.out.println("Autosave paused");
                }
                
                else {
                autosaveTimeline = new Timeline(new KeyFrame(Duration.seconds(15), new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        saveFlag = true;
                        //Autosave Unit Test
                        System.out.println("Autosaved");
                        //save in the same location
                        File file1 = filepath;
                        //file1.renameTo(filename);
                        
                        if (file1 != null) {
                            try {
                            //make new writable to save, take snapshot
                            WritableImage writable = new WritableImage(intValue(canvas.getWidth()),intValue(canvas.getHeight()));
                            canvas.snapshot(null, writable);
                            RenderedImage render = SwingFXUtils.fromFXImage(writable, null);
                            ImageIO.write(render, "png", file1);
                            isSaved = true;
                            } catch (IOException ex){
                                System.out.println("Error!!!!!");
                
                            }
       
                        }
                    }
                }));
                autosaveTimeline.setCycleCount(Timeline.INDEFINITE);
                autosaveTimeline.play();
            }
            }
        });
      
        //--------------------------------CLICKING--------------------------
        canvas.setOnMouseClicked(e->{
            if(dropperBtn.isSelected()) {
            //snapshot the canvas in current state
                WritableImage snap = gc.getCanvas().snapshot(null, null);
                //set c to current mouse pixels color
                Color c = snap.getPixelReader().getColor((int)e.getX(), (int)e.getY());
                //set line CP to c
                colorpicker.setValue(c);
            }
        });
        //--------------------------------PRESSING--------------------------
        canvas.setOnMousePressed((MouseEvent e)->{
            tmpSnap = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
            canvas.snapshot(null, tmpSnap);
            if(lineBtn.isSelected()) {
                isSaved = false;
                penWidth = spinner.getValue();
                gc.setLineWidth(penWidth);
                chosenColor = colorpicker.getValue();
                gc.setStroke(chosenColor);
                line.setStartX(e.getX());
                line.setStartY(e.getY());
            }
            else if(rectBtn.isSelected()) {
                //preliminary writing setup
                isSaved = false;
                penWidth = spinner.getValue();
                chosenColor = colorpicker.getValue();
                fillColor = cpFill.getValue();
                gc.setStroke(chosenColor);
                gc.setFill(fillColor);
                gc.setLineWidth(penWidth);
                //start rectangle on mouse
                rect.setX(e.getX());
                rect.setY(e.getY());
            }
            else if(eraserBtn.isSelected()) {
                isSaved = false;
                penWidth = spinner.getValue();
                chosenColor = colorpicker.getValue();
                fillColor = cpFill.getValue();
                gc.setStroke(chosenColor);
                gc.setFill(fillColor);
                gc.setLineWidth(penWidth);
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }
            else if(circleBtn.isSelected()) {
                //writing setup
                isSaved = false;
                penWidth = spinner.getValue();
                chosenColor = colorpicker.getValue();
                fillColor = cpFill.getValue();
                gc.setStroke(chosenColor);
                gc.setFill(fillColor);
                gc.setLineWidth(penWidth);
                //set circle center on mouse press
                circle.setCenterX(e.getX());
                circle.setCenterY(e.getY());
            }
            else if(elpsBtn.isSelected()) {
                isSaved = false;
                penWidth = spinner.getValue();
                chosenColor = colorpicker.getValue();
                fillColor = cpFill.getValue();
                gc.setStroke(chosenColor);
                gc.setFill(fillColor);
                gc.setLineWidth(penWidth);
                //set ellipse center on mouse
                elps.setCenterX(e.getX());
                elps.setCenterY(e.getY());
            }
            else if (pencilBtn.isSelected()) {
                isSaved = false;
                penWidth = spinner.getValue();
                gc.setLineWidth(penWidth);
                chosenColor = colorpicker.getValue();
                gc.setStroke(chosenColor);
                //start new path to postition
                gc.beginPath();
                gc.moveTo(e.getX(), e.getY());
                gc.stroke();
            }
            else if(txtBoxBtn.isSelected()) {
                isSaved = false;
                gc.setLineWidth(1);
                gc.setStroke(colorpicker.getValue());
                gc.setFill(cpFill.getValue());
                gc.fillText(text.getText(), e.getX(), e.getY());
                gc.strokeText(text.getText(), e.getX(), e.getY());
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            else if(arcBtn.isSelected()) {
                //preliminary writing setup
                isSaved = false;
                penWidth = spinner.getValue();
                chosenColor = colorpicker.getValue();
                fillColor = cpFill.getValue();
                gc.setStroke(chosenColor);
                gc.setFill(fillColor);
                gc.setLineWidth(penWidth);
                //start rectangle on mouse
                arc.setCenterX(e.getX());
                arc.setCenterY(e.getY());
            }
            else if (polyBtn.isSelected()) {
                isSaved = false;
                penWidth = spinner.getValue();
                chosenColor = colorpicker.getValue();
                fillColor = cpFill.getValue();
                gc.setStroke(chosenColor);
                gc.setFill(fillColor);
                gc.setLineWidth(penWidth);
                polyStartX = e.getX();
                polyStartY = e.getY();
            }
            else if (selectBtn.isSelected()) {
                isSaved = false;
                penWidth = spinner.getValue();
                chosenColor = colorpicker.getValue();
                fillColor = cpFill.getValue();
                gc.setStroke(chosenColor);
                gc.setFill(fillColor);
                gc.setLineWidth(penWidth);
                selRect.setX(e.getX());
                selRect.setY(e.getY());
            }
        });
        //--------------------------------DRAGGING-------------------------
        canvas.setOnMouseDragged(e->{
            if(eraserBtn.isSelected()){
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }
            else if (pencilBtn.isSelected()) {
                penWidth = spinner.getValue();
                gc.setLineWidth(penWidth);
                chosenColor = colorpicker.getValue();
                gc.setStroke(chosenColor);
                //line to mouse
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
            else if (rectBtn.isSelected()) {
                gc.drawImage(tmpSnap, 0, 0);
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                if(rect.getX() > e.getX()) {
                    rect.setX(e.getX());
                }
                if(rect.getY() > e.getY()) {
                    rect.setY(e.getY());
                }
                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
            else if (circleBtn.isSelected()) {
                gc.drawImage(tmpSnap, 0, 0);
                circle.setRadius((Math.abs(e.getX() - circle.getCenterX()) + Math.abs(e.getY() - circle.getCenterY())) / 2);
                if(circle.getCenterX() > e.getX()) {
                    circle.setCenterX(e.getX());
                }
                if(circle.getCenterY() > e.getY()) {
                    circle.setCenterY(e.getY());
                }
                gc.fillOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
                gc.strokeOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
            }
            else if (elpsBtn.isSelected()){
                gc.drawImage(tmpSnap, 0, 0);
                elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
                elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));

                if(elps.getCenterX() > e.getX()) {
                    elps.setCenterX(e.getX());
                }
                if(elps.getCenterY() > e.getY()) {
                    elps.setCenterY(e.getY());
                }
                gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
            }
            else if (arcBtn.isSelected()) {
                gc.drawImage(tmpSnap, 0, 0);
                double angle = 180;
                double width = Math.abs((e.getX() - arc.getCenterX()));
                double height = Math.abs((e.getY() - arc.getCenterY()));
                gc.strokeArc(e.getX(), e.getY(), width, height, angle, angle, ArcType.OPEN);
                  
            }
            else if (lineBtn.isSelected()) {
                gc.drawImage(tmpSnap, 0, 0);
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            }
            else if (polyBtn.isSelected()) {
                gc.drawImage(tmpSnap, 0, 0);
                numSides = polySides.getValue();
                double radius = ((Math.abs(e.getX() - polyStartX) + Math.abs(e.getY() - polyStartY)) / 2);
                if (polyStartX > e.getX()) {
                    polyStartX = e.getX();
                }
                if (polyStartY > e.getY()) {
                    polyStartY = e.getY();
                }
                double[] xSides = new double[numSides];
                double[] ySides = new double[numSides];
                
                for (int i = 0; i<numSides; i++) {
                    xSides[i] = radius*Math.cos(2*i*Math.PI/numSides) + polyStartX;
                    ySides[i] = radius*Math.sin(2*i*Math.PI/numSides) + polyStartY;
                }
                
                gc.strokePolygon(xSides, ySides, numSides);
                gc.fillPolygon(xSides, ySides, numSides);
            }
            else if (moveBtn.isSelected()) {
                gc.drawImage(tmpSnap, 0, 0);
                gc.drawImage(selImg, e.getX(), e.getY());
            }
        });
        //--------------------------------RELEASED-------------------------
        canvas.setOnMouseReleased(e->{
            
            if(lineBtn.isSelected()) {
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

                
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            else if(rectBtn.isSelected()) {
                gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight());
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                if(rect.getX() > e.getX()) {
                    rect.setX(e.getX());
                }
                if(rect.getY() > e.getY()) {
                    rect.setY(e.getY());
                }
                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));

            }
            else if(eraserBtn.isSelected()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            else if(circleBtn.isSelected()) {
                gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight());
                circle.setRadius((Math.abs(e.getX() - circle.getCenterX()) + Math.abs(e.getY() - circle.getCenterY())) / 2);
                if(circle.getCenterX() > e.getX()) {
                    circle.setCenterX(e.getX());
                }
                if(circle.getCenterY() > e.getY()) {
                    circle.setCenterY(e.getY());
                }
                gc.fillOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
                gc.strokeOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
                
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            else if(elpsBtn.isSelected()) {
                gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight());
                elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
                elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));

                if(elps.getCenterX() > e.getX()) {
                    elps.setCenterX(e.getX());
                }
                if(elps.getCenterY() > e.getY()) {
                    elps.setCenterY(e.getY());
                }
                gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            else if (pencilBtn.isSelected()) {
                gc.closePath();
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            else if (arcBtn.isSelected()) {
                gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight());
                double angle = 180;
                double width = Math.abs((e.getX() - arc.getCenterX()));
                double height = Math.abs((e.getY() - arc.getCenterY()));
                gc.strokeArc(e.getX(), e.getY(), width, height, angle, angle, ArcType.OPEN);
               
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            else if (polyBtn.isSelected()) {
                numSides = polySides.getValue();
                double radius = ((Math.abs(e.getX() - polyStartX) + Math.abs(e.getY() - polyStartY)) / 2);
                if (polyStartX > e.getX()) {
                    polyStartX = e.getX();
                }
                if (polyStartY > e.getY()) {
                    polyStartY = e.getY();
                }
                double[] xSides = new double[numSides];
                double[] ySides = new double[numSides];
                
                for (int i = 0; i<numSides; i++) {
                    xSides[i] = radius*Math.cos(2*i*Math.PI/numSides) + polyStartX;
                    ySides[i] = radius*Math.sin(2*i*Math.PI/numSides) + polyStartY;
                }
                
                gc.strokePolygon(xSides, ySides, numSides);
                gc.fillPolygon(xSides, ySides, numSides);
                
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            else if (selectBtn.isSelected()) {
                selRect.setWidth(Math.abs(e.getX() - selRect.getX()));
                selRect.setHeight(Math.abs(e.getY() - selRect.getY()));
                
                if(selRect.getX() > e.getX()) {
                    selRect.setX(e.getX());
                }
                if (selRect.getY() > e.getY()) {
                    selRect.setY(e.getY());
                }
                WritableImage writableImage = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
                canvas.snapshot(null, writableImage);
                gc.fillRect(selRect.getX(), selRect.getY(), selRect.getWidth(), selRect.getHeight());
                PixelReader pixelReader = writableImage.getPixelReader();
                selImg = new WritableImage(pixelReader, (int)selRect.getX(), (int)selRect.getY(), (int)selRect.getWidth(), (int)selRect.getHeight());
                gc.clearRect(selRect.getX(), selRect.getY(), selRect.getWidth(), selRect.getHeight());
                
                undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            }
            else if (moveBtn.isSelected()) {
                gc.drawImage(selImg, e.getX(), e.getY());
            }
        });
        
        
        Region target = stackpane;
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        //-------------------------------ZOOM---------------------------
        canvas.setOnScroll(evt -> {
    if (evt.isControlDown()) {
        evt.consume();
        // These numbers need to be hardcoded for standard zoom factor
        final double zoomFactor = evt.getDeltaY() > 0 ? 1.2 : 1 / 1.2;
        Bounds groupBounds = canvas.getLayoutBounds();
        final Bounds viewportBounds = sp.getViewportBounds();
        // calculate pixel offsets from [0, 1] range
        double valX = sp.getHvalue() * (groupBounds.getWidth() - viewportBounds.getWidth());
        double valY = sp.getVvalue() * (groupBounds.getHeight() - viewportBounds.getHeight());
        // convert content coordinates to target coordinates
        Point2D posInZoomTarget = target.parentToLocal(canvas.parentToLocal(new Point2D(evt.getX(), evt.getY())));
        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));
        // do the resizing
        target.setScaleX(zoomFactor * target.getScaleX());
        target.setScaleY(zoomFactor * target.getScaleY());
        // refresh ScrollPane scroll positions & content bounds
        sp.layout();
        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        groupBounds = canvas.getLayoutBounds();
        sp.setHvalue((valX + adjustment.getX()) / (groupBounds.getWidth() - viewportBounds.getWidth()));
        sp.setVvalue((valY + adjustment.getY()) / (groupBounds.getHeight() - viewportBounds.getHeight()));
       }});
    }    
//------------------------------------------TIPS MENU-------------------------------
    @FXML
    private void handlemenuItemNotes(ActionEvent event) {
        final int size = 400;
        //make new window appear and show it
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PatchNotes.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Help Menu");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}
//----------------------------------------CLOSE----------------------------------
    @FXML
    private void handlemenuItemClose(ActionEvent event) {
        final int widthModifier = 300;
        final int heightModifier = 200;
        //checks if the file is saved
        if (isSaved) {
            //if it is clear the canvas
            final GraphicsContext gc = canvas.getGraphicsContext2D();
            final int none = 0;
            gc.restore();
            gc.clearRect(none, none, canvas.getHeight()+heightModifier, canvas.getWidth()+widthModifier);
        } else {
            //if not make an alert popup
            Alert warning = new Alert(AlertType.CONFIRMATION);
            warning.setContentText("You have not saved your changes. Are you sure you want to close the file?");
            //warning.showAndWait();
            Optional<ButtonType> result = warning.showAndWait();
            //if they hit the X nothing
            if(!result.isPresent()) {
                
            } 
            //if they hit ok clear it
            else if(result.get() == ButtonType.OK) {
                final GraphicsContext gc = canvas.getGraphicsContext2D();
                final int none = 0;
                gc.restore();
                gc.clearRect(none, none, canvas.getHeight(), canvas.getWidth());
            }
            //if they hit cancel do nothing
            else if(result.get() == ButtonType.CANCEL) {
                
            }
        }
        
    }

//-------------------------------------UNDO--------------------------
    /**
   * Undoes the last action that is performed on the canvas.
   * This affects any physical changes to the canvas.
   * @param 
   */
    @FXML
    private void handlemenuItemUndo(ActionEvent event) {
        
        if (undoStack.empty()) {
            
        } else {
            final GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage((Image)undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight()); 
            redoStack.push(undoStack.lastElement());
            undoStack.pop();
            isSaved = false;
        }
    }
//---------------------------------REDO-----------------------------------------
   /**
   * Redoes the previous action that is performed on the canvas.
   * This affects any physical changes to the canvas.
   * @param 
   */
    @FXML
    private void handleredoBtn(ActionEvent event) {
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        if (redoStack.empty()) {
            
        } else {
            gc.drawImage((Image)redoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight()); 
            undoStack.push(canvas.snapshot(new SnapshotParameters(), null));
            redoStack.pop();
            isSaved = false;
        }
    }
//---------------------------------TIPS MENU------------------------------------
      /**
   * Opens an alert containing tips on how to use the program.
   * This does not change the canvas in any way, it only displays information.
   * @param 
   */
    @FXML
    private void handlemenuItemTips(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Welcome to the Paint Tips Menu! \nTo undo or redo, click twice.\nTo draw lines, click the starting point and drag.\nExit through the file menu, not the red X!\nYou can save your files as either a .png or a .jpg!");
        alert.show();
    }
//---------------------------------RESIZE---------------------------------------
      /**
   * Opens a dialog popup with two text fields for the user to input numbers.
   * The inputs are then used to resize the image and canvas.
   * Contains a unit test that checks that the user-defined inputs are equal to
   * the new dimensions of the canvas, and also prints them in the console.
   * This action can be undone, and is counted as a change towards smart save.
   * @param 
   */
    @FXML
    private void handleresizeBtn(ActionEvent event) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int inset1 = 20;
        int inset2 = 150;
        int inset3 = 10;
        //new dialog box with two inputs
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
               GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);
                gridPane.setPadding(new Insets(inset1, inset2, inset3, inset3));
                TextField height = new TextField();
                height.setPromptText("Height");
                TextField width = new TextField();
                width.setPromptText("Width");
                gridPane.add(height, 0, 0);
                gridPane.add(new Label("Height | Width"), 1, 0);
                gridPane.add(width, 2, 0);
                dialog.getDialogPane().setContent(gridPane);
                
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        return new Pair<>(height.getText(), width.getText());
                    }
                return null;
                });
                Optional<Pair<String, String>> result = dialog.showAndWait();
                 result.ifPresent(pair -> {
                    //System.out.println("height=" + pair.getKey() + ", width=" + pair.getValue());
                    final double newHeight = Integer.parseInt(pair.getKey());
                    final double newWidth = Integer.parseInt(pair.getValue());
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
                    gc.drawImage(undoStack.lastElement(), 0, 0, canvas.getWidth(), canvas.getHeight()); 
                });
           
    }
} 

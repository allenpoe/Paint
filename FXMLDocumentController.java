/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author apoe0
 */
public class FXMLDocumentController implements Initializable {
    //Variables non FXML related, used in other files
    private static int defaultWidth = 1;
    public static File filepath;
    public static String filename;
    public static int penWidth = defaultWidth;
    public static Color chosenColor;
    public static Color fillColor;
    public static boolean isSaved = true;
    Stack<Shape> undoHistory = new Stack();
    Stack<Shape> redoHistory = new Stack();
    Stack<WritableImage> undoStack = new Stack();
    Stack<WritableImage> redoStack = new Stack();
    ToggleGroup toggleGroup = new ToggleGroup();
    ToggleGroup timerGroup = new ToggleGroup();
    Image img;
    public static WritableImage tmpSnap;
    public static WritableImage selImg;
    public static double polyStartX;
    public static double polyStartY;
    public static int numSides;
    public static String fileExtension;
    public static String fileName;
    public static String newExt;
    public static String newName;
    private static final Integer STARTTIME = 15;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    public static Timeline autosaveTimeline;
    public static boolean saveFlag = false;
    public static boolean saveAs = false;
    public static boolean blackboardMode = false;
    private ObservableList<String> st = FXCollections.observableArrayList("Comic Sans MS","Times New Roman","Edwardian Script ITC");
    public static int defaultCanvasWidth = 1082;
    public static int defaultCanvasHeight = 735;
    
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
    private VBox Vbox;
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
    @FXML
    private Label status;
    @FXML
    private Label autosaveLabel;
    @FXML
    private MenuItem blackboardBtn;
    @FXML
    private ToggleButton copyBtn;
    
    @FXML
    private Spinner<Integer> fontsizeBtn;
    @FXML
    private ChoiceBox<String> fontBox;
    @FXML
    private CheckBox darkCheck;
    @FXML
    private MenuBar menubar;
    @FXML
    private ToolBar toolbar1;
    @FXML
    private ToolBar toolbar2;
    @FXML
    private HBox hbox;
    @FXML
    private Menu menuFile;
    @FXML
    private Menu menuEdit;
    @FXML
    private Menu menuHelp;
    @FXML
    private ColorPicker cpBorder;

    
    
    //------------------------------NEW-------------------------------------------
    @FXML
    private void handlemenuItemNew(ActionEvent event) {
        newClass nc = new newClass();
        nc.newFile(timerBtn, moveBtn, undoStack, menuItemSave, menuItemSaveAs, 
                menuItemClose, canvas);
    }
    //-------------------------------SAVE---------------------------------------
    @FXML
    private void handlemenuItemSave(ActionEvent event) {
        saveClass sc = new saveClass();
        sc.saveFile(canvas);
    }
    //--------------------------------SAVE AS-------------------------------------
    @FXML
    private void handlemenuItemSaveAs(ActionEvent event) {
        saveAsClass saC = new saveAsClass();
        saC.saveAs(canvas, timerBtn, menuItemSave);
    }
//---------------------------------------------QUIT------------------------------
    @FXML
    private void handlemenuItemQuit(ActionEvent event) {
        quitClass qc = new quitClass();
        qc.checkQuit();
    }
    //--------------------------------------OPEN----------------------------------
    @FXML
    private void handlemenuItemOpen(ActionEvent event) {
        openClass open = new openClass();
        open.openFile(canvas, img, undoStack, menuItemSave, menuItemSaveAs, 
               menuItemClose, lineBtn, moveBtn);
    }
  
    //---------------------------------INITIALIZE-------------------------------
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        //instantiate setup class
        setupValues setup = new setupValues();
        //Set disables for these buttons on launch
        setup.initialDisables(menuItemSave, menuItemSaveAs, menuItemClose, 
                moveBtn, timerBtn);
        //Set 4 keyboard shortcuts
        setup.setupShortcuts(menuItemOpen, menuItemSave, menuItemClose, menuItemQuit);
        //Set up integer spinner values
        setup.setupSpinners(spinner, polySides, fontsizeBtn);
        //Add all toggle buttons to a toggle group
        setup.setupToggleGroup(circleBtn, elpsBtn, eraserBtn, lineBtn, rectBtn, 
                dropperBtn, pencilBtn, txtBoxBtn, arcBtn, polyBtn, selectBtn, 
                moveBtn, copyBtn, toggleGroup);
        
        //Get GraphicsContext to be used later
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        //Set default colors for ColorPickers
        cpBorder.setValue(Color.BLACK);
        cpFill.setValue(Color.TRANSPARENT);
        //Add custom named colors to the ColorPickers
        cpBorder.getCustomColors().addAll(Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN,Color.GRAY,Color.ORANGE);
        cpFill.getCustomColors().addAll(Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN,Color.GRAY,Color.ORANGE);
        //set default font
        text.setFont(Font.font("Times New Roman"));
        gc.setFont(Font.font("Times New Roman"));
        fontBox.setValue("Times New Roman");
        fontBox.setItems(st);
        //Shapes to be used later
        Line line = new Line();
        Rectangle rect = new Rectangle();
        Rectangle selRect = new Rectangle();
        Circle circle = new Circle();
        Ellipse elps = new Ellipse();
        Arc arc = new Arc();
//----------------------------------AUTOSAVE-----------------------------------
      autosaveClass ac = new autosaveClass();
      ac.checkAutosave(timerBtn, autosaveLabel, canvas);
  
      //---------------------X button checker------------------------
      /**
       * Checks if user used the X button, opens alert to confirm that they
       * want to close if they didn't save changes
       */
      manualCloseClass mcc = new manualCloseClass();
      mcc.checkManualClose();
      
      //--------------------dark mode--------------------------------
      /**
       * Enables/disables dark mode for the program.
       * uses nearly every design element of the program and changes their styles
       */
      darkClass dc = new darkClass();
      dc.checkDarkMode(darkCheck, menubar, toolbar1, toolbar2, hbox, sp, cpBorder, 
              cpFill, spinner, pencilBtn, lineBtn, rectBtn, circleBtn, elpsBtn, 
              elpsBtn, lineBtn, arcBtn, dropperBtn, eraserBtn, txtBoxBtn, 
              fontsizeBtn, fontBox, text, selectBtn, moveBtn, copyBtn, polyBtn, polySides);
          
      
        //--------------------------------CLICKING--------------------------
        onClickClass click = new onClickClass();
        click.checkClick(canvas, dropperBtn, gc, cpBorder, cpFill, status);
        //--------------------------------PRESSING--------------------------
        onPressClass press = new onPressClass();
        press.checkPress(gc, canvas, lineBtn, rectBtn, line, rect, status, eraserBtn, 
                circleBtn, circle, elps, txtBoxBtn, fontsizeBtn, fontBox, text, arc, 
                polyBtn, arcBtn, selectBtn, copyBtn, elpsBtn, selRect, spinner, 
                pencilBtn, cpBorder, cpFill);
        //--------------------------------DRAGGING---------------------------------------
        onDragClass drag = new onDragClass();
        drag.checkDrag(gc, canvas, lineBtn, rectBtn, line, rect, status, eraserBtn, 
                circleBtn, circle, elps, txtBoxBtn, fontsizeBtn, fontBox, text, arc, 
                polyBtn, arcBtn, selectBtn, copyBtn, elpsBtn, selRect, spinner, 
                pencilBtn, cpBorder, cpFill, polySides, moveBtn, undoStack);
        //--------------------------------RELEASED-------------------------
        onReleaseClass release = new onReleaseClass();
        release.checkRelease(gc, canvas, lineBtn, rectBtn, line, rect, status, 
                eraserBtn, circleBtn, circle, elps, txtBoxBtn, fontsizeBtn, 
                fontBox, text, arc, polyBtn, arcBtn, selectBtn, copyBtn, elpsBtn, 
                selRect, spinner, pencilBtn, cpBorder, cpFill, polySides, moveBtn, 
                undoStack);
        

        //-------------------------------ZOOM---------------------------
        zoomClass zc = new zoomClass();
        zc.zoom(sp, stackpane, canvas);
    }    
//------------------------------------------TIPS MENU-------------------------------
    @FXML
    private void handlemenuItemNotes(ActionEvent event) {
        final int size = 400;
        //make new window appear and show it
        try {
            //FXML loader for patch notes window
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
        closeClass cc = new closeClass();
        cc.closeFile(canvas, menuItemSave, menuItemSaveAs, menuItemClose, moveBtn, timerBtn);
    }

//-------------------------------------UNDO--------------------------
    /**
   * Undoes the last action that is performed on the canvas.
   * This affects any physical changes to the canvas.
   * @param 
   */
    @FXML
    private void handlemenuItemUndo(ActionEvent event) {
        undoClass uc = new undoClass();
        uc.undoItem(undoStack, canvas, redoStack);
    }
//---------------------------------REDO-----------------------------------------
   /**
   * Redoes the previous action that is performed on the canvas.
   * This affects any physical changes to the canvas.
   * @param 
   */
    @FXML
    private void handleredoBtn(ActionEvent event) {
        redoClass rc = new redoClass();
        rc.redoItem(canvas, redoStack, undoStack);
    }
//---------------------------------TIPS MENU------------------------------------
      /**
   * Opens an alert containing tips on how to use the program.
   * This does not change the canvas in any way, it only displays information.
   * @param 
   */
    @FXML
    private void handlemenuItemTips(ActionEvent event) {
        //display tips information as an informational alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Welcome to the Paint Tips Menu! "
        + "\nTo undo or redo, click twice.\nTo draw lines, click the starting "
        + "point and drag.\nYou can save your files as either a .png or a .jpg!"
        + "\nYou can use blackboard mode to have a black background. ");
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
        resizeClass rc = new resizeClass();
        rc.resizeCanvas(canvas, undoStack);
    }
    /**
     * Switches the canvas to blackboard mode when pressed.  This changes the 
     * background to black and inverts the eraser to be black. It also sets the 
     * default colors to white and transparent as opposed to black.
     * @param event 
     */
    @FXML
    private void handleblackboardBtn(ActionEvent event) {
        blackboardClass bc = new blackboardClass();
        bc.startBlackboard(menuItemSave, menuItemClose, menuItemSaveAs, moveBtn, timerBtn, canvas, undoStack, cpBorder, cpFill);
    }
} 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;

/**
 *  Handles if the dark mode check box is ticked.
 * @author apoe0
 */
public class darkClass {
    /**
     * If dark mode is enabled, it sets styles to black for the window and 
     * gray for the buttons.  When it is disabled, go back to default.
     * @param darkCheck
     * @param menubar
     * @param toolbar1
     * @param toolbar2
     * @param hbox
     * @param sp
     * @param colorpicker
     * @param cpFill
     * @param spinner
     * @param pencilBtn
     * @param lineBtn
     * @param rectBtn
     * @param circleBtn
     * @param elpsBtn
     * @param logBtn
     * @param timerBtn
     * @param arcBtn
     * @param dropperBtn
     * @param eraserBtn
     * @param txtBoxBtn
     * @param fontsizeBtn
     * @param fontBox
     * @param text
     * @param selectBtn
     * @param moveBtn
     * @param copyBtn
     * @param polyBtn
     * @param polySides 
     */
    public void checkDarkMode(CheckBox darkCheck, MenuBar menubar,ToolBar toolbar1,ToolBar toolbar2,HBox hbox,
            ScrollPane sp,ColorPicker colorpicker,ColorPicker cpFill,Spinner spinner,ToggleButton pencilBtn,
            ToggleButton lineBtn,ToggleButton rectBtn,ToggleButton circleBtn,ToggleButton elpsBtn,
            ToggleButton logBtn,ToggleButton timerBtn,ToggleButton arcBtn,ToggleButton dropperBtn,
            ToggleButton eraserBtn,ToggleButton txtBoxBtn,Spinner fontsizeBtn,ChoiceBox fontBox,
            TextArea text,ToggleButton selectBtn,ToggleButton moveBtn,ToggleButton copyBtn,
            ToggleButton polyBtn, Spinner polySides) {
        darkCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
          @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
              if(newValue){
                  //DARK THEMES
                menubar.setStyle("-fx-base:black");
                toolbar1.setStyle("-fx-base:black");
                toolbar2.setStyle("-fx-base:black");
                hbox.setStyle("-fx-base:black");
                sp.setStyle("-fx-base:gray");
                colorpicker.setStyle("-fx-base:gray");
                cpFill.setStyle("-fx-base:gray");
                spinner.setStyle("-fx-base:gray");
                pencilBtn.setStyle("-fx-base:gray");
                lineBtn.setStyle("-fx-base:gray");
                rectBtn.setStyle("-fx-base:gray");
                circleBtn.setStyle("-fx-base:gray");
                elpsBtn.setStyle("-fx-base:gray");
                logBtn.setStyle("-fx-base:gray");
                timerBtn.setStyle("-fx-base:gray");
                dropperBtn.setStyle("-fx-base:gray");
                eraserBtn.setStyle("-fx-base:gray");
                txtBoxBtn.setStyle("-fx-base:gray");
                fontBox.setStyle("-fx-base:gray");
                fontsizeBtn.setStyle("-fx-base:gray");
                text.setStyle("-fx-base:gray");
                arcBtn.setStyle("-fx-base:gray");
                selectBtn.setStyle("-fx-base:gray");
                moveBtn.setStyle("-fx-base:gray");
                copyBtn.setStyle("-fx-base:gray");
                polyBtn.setStyle("-fx-base:gray");
                polySides.setStyle("-fx-base:gray");
              } else {
                  //NORMAL STYLES
                menubar.setStyle(null);
                toolbar1.setStyle(null);
                toolbar2.setStyle(null);
                sp.setStyle(null);
                hbox.setStyle(null);
                colorpicker.setStyle(null);
                cpFill.setStyle(null);
                spinner.setStyle(null);
                pencilBtn.setStyle(null);
                lineBtn.setStyle(null);
                rectBtn.setStyle(null);
                circleBtn.setStyle(null);
                elpsBtn.setStyle(null);
                logBtn.setStyle(null);
                timerBtn.setStyle(null);
                dropperBtn.setStyle(null);
                eraserBtn.setStyle(null);
                txtBoxBtn.setStyle(null);
                fontBox.setStyle(null);
                fontsizeBtn.setStyle(null);
                text.setStyle(null);
                arcBtn.setStyle(null);
                selectBtn.setStyle(null);
                moveBtn.setStyle(null);
                copyBtn.setStyle(null);
                polyBtn.setStyle(null);
                polySides.setStyle(null);
              }
            }
      });
    }
}

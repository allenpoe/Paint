/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 *  Sets up necessary components of the program including shortcuts, spinners,
 *  button disables, and togglegroups.
 * @author apoe0
 */
public class setupValues {
    /**
     * Assign keyboard shortcuts to menu items.
     * @param menuItemOpen
     * @param menuItemSave
     * @param menuItemClose
     * @param menuItemQuit 
     */
    public void setupShortcuts(MenuItem menuItemOpen,MenuItem menuItemSave,
        MenuItem menuItemClose,MenuItem menuItemQuit) {
        //assigns shortcuts
        menuItemOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        menuItemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        menuItemClose.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        menuItemQuit.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
    }
    /**
     * Sets default values and limits for the spinners.
     * @param spinner
     * @param polySides
     * @param fontsizeBtn 
     */
    public void setupSpinners(Spinner spinner,Spinner polySides,Spinner fontsizeBtn) {
        //set spinner values
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20));
        polySides.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 100));
        fontsizeBtn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(15, 70));
    }
    /**
     * Adds all togglebuttons to the same togglegroup.
     * @param circleBtn
     * @param elpsBtn
     * @param eraserBtn
     * @param lineBtn
     * @param rectBtn
     * @param dropperBtn
     * @param pencilBtn
     * @param txtBoxBtn
     * @param arcBtn
     * @param polyBtn
     * @param selectBtn
     * @param moveBtn
     * @param copyBtn
     * @param toggleGroup 
     */
    public void setupToggleGroup(ToggleButton circleBtn,ToggleButton elpsBtn,
        ToggleButton eraserBtn,ToggleButton lineBtn,ToggleButton rectBtn,
        ToggleButton dropperBtn,ToggleButton pencilBtn,ToggleButton txtBoxBtn,
        ToggleButton arcBtn,ToggleButton polyBtn,ToggleButton selectBtn,
        ToggleButton moveBtn,ToggleButton copyBtn,ToggleGroup toggleGroup) {
        //add all togglebuttons to a togglegroup
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
        copyBtn.setToggleGroup(toggleGroup);
    }
    /**
     * Sets button disables on unnecessary buttons on launch.
     * @param menuItemSave
     * @param menuItemSaveAs
     * @param menuItemClose
     * @param moveBtn
     * @param timerBtn 
     */
    public void initialDisables(MenuItem menuItemSave,MenuItem menuItemSaveAs,
        MenuItem menuItemClose, ToggleButton moveBtn,Button timerBtn) {
        //disables buttons that can't be used on launch
        menuItemSave.setDisable(true);
        menuItemSaveAs.setDisable(true);
        menuItemClose.setDisable(true);
        moveBtn.setDisable(true);
        timerBtn.setDisable(true);
    }
}

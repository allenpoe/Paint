/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

/**
 *  Handles all clicking operations on canvas.
 * @author apoe0
 */
public class onClickClass {
    /**
     * Checks if canvas is clicked.  When it is, check which tool is selected.
     * In this case, there is only the dropper.  When left clicking, read and 
     * put color into primary colorpicker. When right clicking, read and put 
     * color into secondary colorpicker.
     * @param canvas
     * @param dropperBtn
     * @param gc
     * @param colorpicker
     * @param cpFill
     * @param status 
     */
    public void checkClick(Canvas canvas, ToggleButton dropperBtn, 
            GraphicsContext gc, ColorPicker colorpicker, ColorPicker cpFill, 
            Label status) {
        canvas.setOnMouseClicked(e->{
            if(dropperBtn.isSelected()) {
            //snapshot the canvas in current state
                WritableImage snap = gc.getCanvas().snapshot(null, null);
                //set c to current mouse pixels color
                Color c = snap.getPixelReader().getColor((int)e.getX(), (int)e.getY());
                //set stroke on left click
                if (e.getButton() == MouseButton.PRIMARY) {
                    colorpicker.setValue(c);
                //set fill on right click
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    cpFill.setValue(c);
                }
                //set status label
                status.setText("Dropper");
            }
        });
    }
}

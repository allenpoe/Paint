/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import static oracle.jrockit.jfr.events.Bits.intValue;
import static paint.FXMLDocumentController.filepath;
import static paint.FXMLDocumentController.isSaved;
import static paint.FXMLDocumentController.saveFlag;

/**
 * Handles autosaving.
 * @author apoe0
 */
public class autosaveClass {
    /**
     * Checks if the autosave button has been clicked.  If so, it starts a 
     * 15 second timer and saves when it is up.  The timer resets. When clicked 
     * again the timer is canceled.
     * @param timerBtn
     * @param autosaveLabel
     * @param canvas 
     */
    public void checkAutosave(Button timerBtn,Label autosaveLabel,Canvas canvas) {
        timerBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                //make this a toggle type button, if it is on turn it off, vice versa
                if (saveFlag==true) {
                    saveFlag= false;
                    FXMLDocumentController.autosaveTimeline.pause();
                    System.out.println("Autosave paused");
                    autosaveLabel.setText("Autosave off");
                }
                
                else {
                    //set status label at the bottom of the screen
                    autosaveLabel.setText("Autosave on");
                    FXMLDocumentController.autosaveTimeline = new Timeline(new KeyFrame(Duration.seconds(15), new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            FXMLDocumentController.saveFlag = true;
                            //Autosave Unit Test
                            System.out.println("Autosaved");
                            //save in the same location
                            File file1 = FXMLDocumentController.filepath;
                            //file1.renameTo(filename);

                            if (file1 != null) {
                                try {
                                    //make new writable to save, take snapshot
                                    WritableImage writable = new WritableImage(intValue(canvas.getWidth()), intValue(canvas.getHeight()));
                                    canvas.snapshot(null, writable);
                                    RenderedImage render = SwingFXUtils.fromFXImage(writable, null);
                                    ImageIO.write(render, "png", file1);
                                    isSaved = true;
                                } catch (IOException ex) {
                                    System.out.println("Error!!!!!");

                                }

                            }

                        }
                    }));
                FXMLDocumentController.autosaveTimeline.setCycleCount(Timeline.INDEFINITE);
                FXMLDocumentController.autosaveTimeline.play();
            }
            }
        });
    }
}

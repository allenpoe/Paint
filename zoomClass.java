/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 *  Handles when the user zooms into the canvas.
 * @author apoe0
 */
public class zoomClass {
    /**
     * When the user holds control and scrolls in or out, zoom in or out.
     * @param sp
     * @param stackpane
     * @param canvas 
     */
    public void zoom(ScrollPane sp, StackPane stackpane, Canvas canvas) {
        //initial setup for zooming
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
}

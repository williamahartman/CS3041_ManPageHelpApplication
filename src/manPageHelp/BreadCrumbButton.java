package manPageHelp;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * This class has a single static method to build Buttons that are styled like a breadcrumb widget.
 *
 * Code adapted from http://ustesis.wordpress.com/2013/11/04/implementing-breadcrumbs-in-javafx/
 *
 * @author William Hartman, "Uwe's Blog", Andy Till
 */
public class BreadCrumbButton {

    /**
     * Build a Button with that is styled as a breadcrumb widget.
     *
     * @param breadCrumbText The text that will be placed on the button.
     * @return The breadcrumb styled button.
     */
    public static Button breadCrumbBuilder(String breadCrumbText) {
        Button breadCrumbButton = new Button(breadCrumbText);
        breadCrumbButton.setPrefHeight(30);
        breadCrumbButton.setMinHeight(30);
        breadCrumbButton.setMaxHeight(30);
        breadCrumbButton.setStyle("-fx-padding: 5 15 5 15;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 0;" +
                "-fx-background-color: #005578;" +
                "-fx-background-insets: 0 0 0 0;");

        // build the following shape
        //   --------
        //  \         \
        //  /         /
        //   --------

        double ARROW_WIDTH = 5;
        double ARROW_HEIGHT = 30;

        Path path = new Path();
        // begin in the upper left corner
        MoveTo e1 = new MoveTo(0, 0);
        // draw a horizontal line that defines the width of the shape
        HLineTo e2 = new HLineTo();
        // bind the width of the shape to the width of the button
        e2.xProperty().bind(breadCrumbButton.widthProperty().subtract(ARROW_WIDTH));
        // draw upper part of right arrow
        LineTo e3 = new LineTo();
        // the x endpoint of this line depends on the x property of line e2
        e3.xProperty().bind(e2.xProperty().add(ARROW_WIDTH));
        e3.setY(ARROW_HEIGHT / 2.0);
        // draw lower part of right arrow
        LineTo e4 = new LineTo();
        // the x endpoint of this line depends on the x property of line e2
        e4.xProperty().bind(e2.xProperty());
        e4.setY(ARROW_HEIGHT);
        // draw lower horizontal line
        HLineTo e5 = new HLineTo(0);
        // draw lower part of left arrow
        LineTo e6 = new LineTo(ARROW_WIDTH, ARROW_HEIGHT / 2.0);
        // close path
        ClosePath e7 = new ClosePath();
        path.getElements().addAll(e1, e2, e3, e4, e5, e6, e7);
        // this is a dummy color to fill the shape, it won't be visible
        path.setFill(Color.BLACK);
        // set path as button shape
        breadCrumbButton.setClip(path);

        return breadCrumbButton;
    }
}



package model;

import controller.DrawController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class IfNode extends MyNode {
    ImageView Node_if;

    public IfNode(int factoryID, String defaultText, AnchorPane drawingArea, DrawController drawController) {
        super(factoryID, defaultText, drawingArea, drawController);
    }
}

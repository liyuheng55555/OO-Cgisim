package model;

import controller.DrawController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class StartNode extends MyNode {
    ImageView startNode;

    public StartNode(int factoryID, String defaultText, AnchorPane drawingArea, DrawController drawController) {
        super(factoryID, defaultText, drawingArea, drawController);
    }
}

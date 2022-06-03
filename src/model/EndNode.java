package model;

import controller.DrawController;
import javafx.scene.layout.AnchorPane;

public class EndNode extends MyNode {

    public EndNode(int factoryID, String defaultText, AnchorPane drawingArea, DrawController drawController) {
        super(factoryID, defaultText, drawingArea, drawController);
    }
}

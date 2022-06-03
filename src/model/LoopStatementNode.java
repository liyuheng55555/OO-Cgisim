package model;

import controller.DrawController;
import javafx.scene.layout.AnchorPane;

public class LoopStatementNode extends MyNode {

    public LoopStatementNode(int factoryID, String defaultText, AnchorPane drawingArea, DrawController drawController) {
        super(factoryID, defaultText, drawingArea, drawController);
    }
}

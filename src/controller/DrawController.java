package controller;

import javafx.scene.layout.AnchorPane;
import model.MyNode;

import java.util.HashMap;

public class DrawController {
    private final HashMap<Integer, MyNode> shapeMap = new HashMap<>();
    private int startNodeID;
    private AnchorPane drawingArea;
    private PropertyController propertyController;

    public DrawController(AnchorPane drawingArea, PropertyController propertyController) {
        this.drawingArea = drawingArea;
        this.propertyController = propertyController;
        startNodeID = -1;
    }

    public int getStartNodeID() {
        return startNodeID;
    }

    public void setStartNodeID(int startNodeID) {
        this.startNodeID = startNodeID;
    }

    public HashMap<Integer, MyNode> getShapeMap() {
        return shapeMap;
    }

    public AnchorPane getDrawingArea() {
        return drawingArea;
    }

    public void setDrawingArea(AnchorPane drawingArea) {
        this.drawingArea = drawingArea;
    }

    public PropertyController getPropertyController() {
        return propertyController;
    }

    public void setPropertyController(PropertyController propertyController) {
        this.propertyController = propertyController;
    }
}

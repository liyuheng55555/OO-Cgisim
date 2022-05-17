package model;

import javafx.beans.property.BooleanProperty;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class MyLine extends Line {
    //  factoryID of the line
    protected int factoryID;
    // drawing area of the shape
    protected AnchorPane drawingArea;
    // draw controller of the shape
    protected DrawController drawController;
    // start point of the line
    protected double startX;
    protected double startY;
    // end point of the line
    protected double endX;
    protected double endY;
    // todo
    protected double lastX;
    protected double lastY;
    // component of the line
    protected Polygon triangle;
    protected Text text;
    protected Line line;
    protected Circle circle;
    // 连接信息
    protected MyShape headLinkShape;
    protected MyShape tailLinkShape;
    // status
    protected boolean isOnTheLine = false;
    protected boolean isSelected;
    protected BooleanProperty booleanProperty;
    protected ArrayList<Circle> middlePoints;
}

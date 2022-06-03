package model;

import controller.DrawController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MyNode {
    private int factoryID;
    private Text text;
    private final AnchorPane drawingArea;
    private final DrawController drawController;

    public MyNode(int factoryID, String defaultText, AnchorPane drawingArea, DrawController drawController) {
        this.factoryID = factoryID;
        this.text = new Text(defaultText);
        this.drawingArea = drawingArea;
        this.drawController = drawController;
    }

    public MyNode(AnchorPane drawingArea, DrawController drawController){
        this.drawingArea = drawingArea;
        this.drawController = drawController;
    }

    public ImageView imageView;
    public String name;
}

package model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MyNode {
    private int factoryID;

    public ImageView imageView; // image for the line

    public MyNode(){
    }

    public MyNode(int factoryID, int x, int y) {
        this.factoryID = factoryID;
    }

    public int getFactoryID() {
        return factoryID;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void draw(AnchorPane drawingArea){
    }

    public void remove(AnchorPane drawingArea){
    }

    public void putInTable(MyNode[][] nodeTable) {
    }

    public void removeFromTable(MyNode[][] nodeTable) {
    }
}

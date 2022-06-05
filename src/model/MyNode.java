package model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MyNode {
    private int factoryID;

    public ImageView imageView;
    //public String name;

    public MyNode(){
    }

    public MyNode(int factoryID, int x, int y) {
        this.factoryID = factoryID;
    }

    public int getFactoryID() {
        return factoryID;
    }

    public void setFactoryID(int factoryID) {
        this.factoryID = factoryID;
    }
}

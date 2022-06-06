package model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

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

    public void draw(AnchorPane drawingArea, double x, double y) {
    }

    public void remove(AnchorPane drawingArea){
    }

    public boolean putInTable(MyNode[][] nodeTable) {
        if(nodeTable[(int)(this.getImageView().getY()/viewH)][(int)(this.getImageView().getX()/viewW)] != null) {
            System.out.println("error in StatementNode putInTable");
            return false;
        }
        nodeTable[(int)(this.getImageView().getY()/viewH)][(int)(this.getImageView().getX()/viewW)] = this;
        return true;
    }

    public void removeFromTable(MyNode[][] nodeTable) {
        nodeTable [(int)(this.getImageView().getY()/viewH)][(int)(this.getImageView().getX()/viewW)] = null;
    }
}

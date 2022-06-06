package model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class MyNode {
    private int factoryID;

    public ImageView imageView; // image for the line

    public String name;

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

    // connectTo[1] = 100 意思是本节点的上方连接着100号节点
    public int[] connectTo = new int[]{-1, -1, -1, -1, -1};

    // connectPlace[1] = 2 意思是本节点的上方连接着另一个节点的下方
    public int[] connectPlace = new int []{-1, -1, -1, -1, -1};

    public void putInTable(MyNode[][] nodeTable) {
        if(nodeTable[(int)(this.getImageView().getY()/viewH)][(int)(this.getImageView().getX()/viewW)] != null) {
            System.out.println("error in StatementNode putInTable");
            return;
        }
        nodeTable[(int)(this.getImageView().getY()/viewH)][(int)(this.getImageView().getX()/viewW)] = this;
    }

    public void removeFromTable(MyNode[][] nodeTable) {
        nodeTable [(int)(this.getImageView().getY()/viewH)][(int)(this.getImageView().getX()/viewW)] = null;
    }
}

package model;

import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class MyNode {
    private int factoryID;
    private double xIndex;
    private double yIndex;
    // connectTo[1] = 100 意思是本节点的上方连接着100号节点
    public int[] connectTo = new int[]{-1, -1, -1, -1, -1};
    // connectPlace[1] = 2 意思是本节点的上方连接着另一个节点的下方
    public int[] connectPlace = new int []{-1, -1, -1, -1, -1};

    public MyNode(int factoryID, double x, double y) {
        this.factoryID = factoryID;
        this.xIndex = x;
        this.yIndex = y;
    }

    public ImageView getImageView() {
        return null;
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

    public int getFactoryID() {
        return factoryID;
    }

    public void setFactoryID(int factoryID) {
        this.factoryID = factoryID;
    }

    public double getxIndex() {
        return xIndex;
    }

    public void setxIndex(double xIndex) {
        this.xIndex = xIndex;
    }

    public double getyIndex() {
        return yIndex;
    }

    public void setyIndex(double yIndex) {
        this.yIndex = yIndex;
    }

}

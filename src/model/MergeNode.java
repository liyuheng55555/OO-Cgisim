package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class MergeNode extends MyNode{
    private int mergeTrueID;
    private int mergeFalseID;
    private int mergeNxtID;
    private ImageView merge;

    public MergeNode(int factoryID, int x, int y) {
        super(factoryID,x,y);
        this.mergeTrueID = -1;
        this.mergeFalseID = -1;
        this.mergeNxtID = -1;
        try{
            this.merge = new ImageView(new Image("resources/img/draw_node_merge.png"));
            this.merge.setId("merge");
            this.merge.setX(x);
            this.merge.setY(y + viewH * 2);
            this.merge.setFitHeight(viewH);
            this.merge.setFitWidth(viewW);
            this.merge.setId("merge");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error in loading IfNode image");
        }
    }

    public int getMergeTrueID() {
        return mergeTrueID;
    }

    public void setMergeTrueID(int mergeTrueID) {
        this.mergeTrueID = mergeTrueID;
    }

    public int getMergeFalseID() {
        return mergeFalseID;
    }

    public void setMergeFalseID(int mergeFalseID) {
        this.mergeFalseID = mergeFalseID;
    }

    public int getMergeNxtID() {
        return mergeNxtID;
    }

    public void setMergeNxtID(int mergeNxtID) {
        this.mergeNxtID = mergeNxtID;
    }

    @Override
    public ImageView getImageView()  {
        return this.merge;
    }

    public void draw(AnchorPane drawingArea){
        drawingArea.getChildren().add(this.merge);
    }

    public void remove(AnchorPane drawingArea){
        drawingArea.getChildren().remove(this.merge);
    }

    public void putInTable(MyNode[][] nodeTable) {
        if(nodeTable[(int)(this.merge.getX()/viewW)] [(int)(this.merge.getY()/viewH)] != null) {
            System.out.println("error in IfNode putInTable");
            return;
        }
        nodeTable[(int)(this.merge.getX()/viewW)] [(int)(this.merge.getY()/viewH)] = this;
    }

    public void removeFromTable(MyNode[][] nodeTable) {
        nodeTable[(int)(this.merge.getX()/viewW)] [(int)(this.merge.getY()/viewH)] = null;
    }
}

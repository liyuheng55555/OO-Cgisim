package model;

import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class MergeNode extends MyNode{
    private int mergeTrueID;
    private int mergeTruePlace;
    private int mergeFalseID;
    private int mergeFalsePlace;
    private int mergeNxtID;
    private int mergeNxtPlace;
    private ImageView merge;

    public MergeNode(int factoryID, int x, int y) {
        super(factoryID,x,y);
        this.mergeTrueID = -1;
        this.mergeTruePlace = -1;
        this.mergeFalseID = -1;
        this.mergeFalsePlace = -1;
        this.mergeNxtID = -1;
        this.mergeNxtPlace = -1;
        try{
            this.merge = new ImageView(new Image("sources/img/draw_node_merge.png"));
            this.merge.setId("merge");
            this.merge.setX(x);
            this.merge.setY(y);
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
        connectTo[1] = mergeTrueID;
    }

    public int getMergeFalseID() {
        return mergeFalseID;
    }

    public void setMergeFalseID(int mergeFalseID) {
        this.mergeFalseID = mergeFalseID;
        connectTo[4] = mergeFalseID;
    }

    public int getMergeNxtID() {
        return mergeNxtID;
    }

    public void setMergeNxtID(int mergeNxtID) {
        this.mergeNxtID = mergeNxtID;
        connectTo[2] = mergeNxtID;
    }

    public int getMergeTruePlace() {
        return mergeTruePlace;
    }

    public void setMergeTruePlace(int mergeTruePlace) {
        this.mergeTruePlace = mergeTruePlace;
        connectPlace[1] = mergeTruePlace;
    }

    public int getMergeFalsePlace() {
        return mergeFalsePlace;
    }

    public void setMergeFalsePlace(int mergeFalsePlace) {
        this.mergeFalsePlace = mergeFalsePlace;
        connectPlace[4] = mergeFalsePlace;
    }

    public int getMergeNxtPlace() {
        return mergeNxtPlace;
    }

    public void setMergeNxtPlace(int mergeNxtPlace) {
        this.mergeNxtPlace = mergeNxtPlace;
        connectPlace[2] = mergeNxtPlace;
    }

    @Override @JSONField(serialize=false)
    public ImageView getImageView()  {
        return this.merge;
    }

    @Override
    public void draw(AnchorPane drawingArea){
        drawingArea.getChildren().add(this.merge);
    }

    @Override
    public void draw(AnchorPane drawingArea, double x, double y) {
        super.setxIndex(x);
        super.setyIndex(y);
        this.merge.setX(x);
        this.merge.setY(y);
        drawingArea.getChildren().add(this.merge);
    }

    @Override
    public void remove(AnchorPane drawingArea){
        drawingArea.getChildren().remove(this.merge);
    }

}

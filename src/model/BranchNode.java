package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static model.Constant.viewH;
import static model.Constant.viewW;


public class BranchNode extends MyNode {
    private Text text;
    private int branchPreID;
    private int branchTrueID;
    private int branchFalseID;
    private int mergeTrueID;
    private int mergeFalseID;
    private int mergeNxtID;
    private ImageView branch;
    private ImageView merge;

    public BranchNode(int factoryID, int x, int y) {
        super(factoryID,x,y);
        this.branchPreID = -1;
        this.branchTrueID = -1;
        this.branchFalseID = -1;
        this.mergeTrueID = -1;
        this.mergeFalseID = -1;
        this.mergeNxtID = -1;
        this.text = new Text("input your code!");
        this.text.setX(x + viewW / 2);
        this.text.setY(y + viewH / 2);
        try{
            this.branch = new ImageView(new Image("resources/img/draw_node_if.png"));
            this.merge = new ImageView(new Image("resources/img/draw_node_merge.png"));
            this.branch.setId("branch");
            this.merge.setId("merge");
            this.branch.setX(x);
            this.branch.setY(y);
            this.branch.setFitHeight(viewH);
            this.branch.setFitWidth(viewW);
            this.merge.setX(x);
            this.merge.setY(y + viewH * 2);
            this.merge.setFitHeight(viewH);
            this.merge.setFitWidth(viewW);
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error in loading IfNode image");
        }
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public int getBranchPreID() {
        return branchPreID;
    }

    public void setBranchPreID(int branchPreID) {
        this.branchPreID = branchPreID;
    }

    public int getBranchTrueID() {
        return branchTrueID;
    }

    public void setBranchTrueID(int branchTrueID) {
        this.branchTrueID = branchTrueID;
    }

    public int getBranchFalseID() {
        return branchFalseID;
    }

    public void setBranchFalseID(int branchFalseID) {
        this.branchFalseID = branchFalseID;
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

    public ImageView getBranch() {
        return branch;
    }

    public ImageView getMerge() {
        return merge;
    }

    public void setBranch(ImageView branch) {
        this.branch = branch;
    }

    public void setMerge(ImageView merge) {
        this.merge = merge;
    }

    public void draw(AnchorPane drawingArea){
        drawingArea.getChildren().add(this.branch);
        drawingArea.getChildren().add(this.merge);
        drawingArea.getChildren().add(this.text);
    }

    public void remove(AnchorPane drawingArea){
        drawingArea.getChildren().remove(this.branch);
        drawingArea.getChildren().remove(this.merge);
        drawingArea.getChildren().remove(this.text);
    }

    public void putInTable(ImageView[][] viewTable) {
        if(viewTable[(int)(this.branch.getX()/viewW)] [(int)(this.branch.getY()/viewH)] != null
                || viewTable[(int)(this.merge.getX()/viewW)] [(int)(this.merge.getY()/viewH)] != null) {
            System.out.println("error in IfNode putInTable");
            return;
        }
        viewTable[(int)(this.branch.getX()/viewW)] [(int)(this.branch.getY()/viewH)] = this.branch;
        viewTable[(int)(this.merge.getX()/viewW)] [(int)(this.merge.getY()/viewH)] = this.merge;
    }

    public void removeFromTable(ImageView[][] viewTable) {
        viewTable[(int)(this.branch.getX()/viewW)] [(int)(this.branch.getY()/viewH)] = null;
        viewTable[(int)(this.merge.getX()/viewW)] [(int)(this.merge.getY()/viewH)] = null;
    }
}

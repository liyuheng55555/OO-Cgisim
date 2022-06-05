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
    private int branchPrePlace;
    private int branchTrueID;
    private int branchTruePlace;
    private int branchFalseID;
    private int branchFalsePlace;
    private ImageView branch;

    public BranchNode(int factoryID, int x, int y) {
        super(factoryID,x,y);
        this.branchPreID = -1;
        this.branchPrePlace = -1;
        this.branchTrueID = -1;
        this.branchTruePlace = -1;
        this.branchFalseID = -1;
        this.branchFalsePlace = -1;
        this.text = new Text("branch code!");
        this.text.setX(x);
        this.text.setY(y + viewH / 2);
        this.text.setFont(Constant.font);
        try{
            this.branch = new ImageView(new Image("resources/img/draw_node_if.png"));
            this.branch.setId("branch");
            this.branch.setX(x);
            this.branch.setY(y);
            this.branch.setFitHeight(viewH);
            this.branch.setFitWidth(viewW);
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

    public int getBranchPrePlace() {
        return branchPrePlace;
    }

    public void setBranchPrePlace(int branchPrePlace) {
        this.branchPrePlace = branchPrePlace;
    }

    public int getBranchTruePlace() {
        return branchTruePlace;
    }

    public void setBranchTruePlace(int branchTruePlace) {
        this.branchTruePlace = branchTruePlace;
    }

    public int getBranchFalsePlace() {
        return branchFalsePlace;
    }

    public void setBranchFalsePlace(int branchFalsePlace) {
        this.branchFalsePlace = branchFalsePlace;
    }

    @Override
    public ImageView getImageView() {
        return this.branch;
    }

    @Override
    public void draw(AnchorPane drawingArea){
        drawingArea.getChildren().add(this.branch);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void remove(AnchorPane drawingArea){
        drawingArea.getChildren().remove(this.branch);
        drawingArea.getChildren().remove(this.text);
    }

    @Override
    public void putInTable(MyNode[][] nodeTable) {
        if(nodeTable[(int)(this.branch.getX()/viewW)] [(int)(this.branch.getY()/viewH)] != null) {
            System.out.println("error in IfNode putInTable");
            return;
        }
        nodeTable[(int)(this.branch.getX()/viewW)] [(int)(this.branch.getY()/viewH)] = this;
    }

    @Override
    public void removeFromTable(MyNode[][] nodeTable) {
        nodeTable[(int)(this.branch.getX()/viewW)] [(int)(this.branch.getY()/viewH)] = null;
    }
}

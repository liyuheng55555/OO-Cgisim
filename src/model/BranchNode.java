package model;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static model.Constant.*;


public class BranchNode extends MyNode {
    private Text text;
    private int branchPreID;
    private int branchPrePlace;
    private int branchTrueID;
    private int branchTruePlace;
    private int branchFalseID;
    private int branchFalsePlace;
    private String branchText; // branchText is the text of the branch node
    private ImageView branch;


    public BranchNode(int factoryID, int x, int y) {
        super(factoryID,x,y);
        this.branchPreID = -1;
        this.branchPrePlace = -1;
        this.branchTrueID = -1;
        this.branchTruePlace = -1;
        this.branchFalseID = -1;
        this.branchFalsePlace = -1;
        this.branchText = "branch code!";
        this.text = new Text("branch code!");
        this.text.setX(x + branchTextRelativeX);
        this.text.setY(y + branchTextRelativeY);
        this.text.setFont(Constant.font);
        try{
            this.branch = new ImageView(new Image("sources/img/draw_node_if.png"));
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

    @JSONField(serialize=false)
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
        connectTo[1] = branchPreID;
    }

    public int getBranchTrueID() {
        return branchTrueID;
    }

    public void setBranchTrueID(int branchTrueID) {
        this.branchTrueID = branchTrueID;
        connectTo[2] = branchTrueID;
    }

    public int getBranchFalseID() {
        return branchFalseID;
    }

    public void setBranchFalseID(int branchFalseID) {
        this.branchFalseID = branchFalseID;
        connectTo[4] = branchFalseID;
    }

    public int getBranchPrePlace() {
        return branchPrePlace;
    }

    public void setBranchPrePlace(int branchPrePlace) {
        this.branchPrePlace = branchPrePlace;
        connectPlace[1] = branchPrePlace;
    }

    public int getBranchTruePlace() {
        return branchTruePlace;
    }

    public void setBranchTruePlace(int branchTruePlace) {
        this.branchTruePlace = branchTruePlace;
        connectPlace[2] = branchTruePlace;
    }

    public int getBranchFalsePlace() {
        return branchFalsePlace;
    }

    public void setBranchFalsePlace(int branchFalsePlace) {
        this.branchFalsePlace = branchFalsePlace;
        connectPlace[4] = branchFalsePlace;
    }

    public String getBranchText() {
        return branchText;
    }

    public void setBranchText(String branchText) {
        this.branchText = branchText;
    }

    @Override @JSONField(serialize=false)
    public ImageView getImageView() {
        return this.branch;
    }

    @Override
    public void draw(AnchorPane drawingArea){
        drawingArea.getChildren().add(this.branch);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void draw(AnchorPane drawingArea, double x, double y) {
        super.setxIndex(x);
        super.setyIndex(y);
        this.branch.setX(x);
        this.branch.setY(y);
        this.text.setX(x + branchTextRelativeX);
        this.text.setY(y + branchTextRelativeY);
        drawingArea.getChildren().add(this.branch);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void remove(AnchorPane drawingArea){
        drawingArea.getChildren().remove(this.branch);
        drawingArea.getChildren().remove(this.text);
    }

    @JSONCreator
    public BranchNode(@JSONField(name="factoryID") int factoryID,
                      @JSONField(name = "connectPlace") int[] connectPlace,
                      @JSONField(name = "connectTo") int[] connectTo,
                      @JSONField(name = "xIndex") double xIndex,
                      @JSONField(name = "yIndex") double yIndex,
                      @JSONField(name = "branchPrePlace") int branchPrePlace,
                      @JSONField(name = "branchTrueID") int branchTrueID,
                      @JSONField(name = "branchTruePlace") int branchTruePlace,
                      @JSONField(name = "branchFalseID") int branchFalseID,
                      @JSONField(name = "branchFalsePlace") int branchFalsePlace,
                      @JSONField(name = "branchText") String branchText) {
        super(factoryID,connectPlace,connectTo,xIndex,yIndex);
        this.branchPrePlace = branchPrePlace;
        this.branchTrueID = branchTrueID;
        this.branchTruePlace = branchTruePlace;
        this.branchFalseID = branchFalseID;
        this.branchFalsePlace = branchFalsePlace;
        this.branchText = branchText;
        this.text = new Text(branchText);
        this.text.setX(xIndex + branchTextRelativeX);
        this.text.setY(yIndex + branchTextRelativeY);
        this.text.setFont(Constant.font);
        try {
            this.branch = new ImageView(new Image("sources/img/draw_node_if.png"));
            this.branch.setX(xIndex);
            this.branch.setY(yIndex);
            this.branch.setFitWidth(viewW);
            this.branch.setFitHeight(viewH);
            this.branch.setId("branch");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in loading IfNode image");
        }
    }
}

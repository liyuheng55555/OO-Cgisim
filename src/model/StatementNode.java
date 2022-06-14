package model;


import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static model.Constant.*;

public class StatementNode extends MyNode {
    private Text text;
    private int preID;
    private int prePlace;
    private int nxtID;
    private int nxtPlace;
    private String statementText; // statementText is the text of the statement node
    private ImageView statement;

    public StatementNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.preID = -1;
        this.prePlace = -1;
        this.nxtID = -1;
        this.nxtPlace = -1;
        this.statementText = "statement code!";
        this.text = new Text("statement code!");
        this.text.setX(x + statementTextRelativeX);
        this.text.setY(y + statementTextRelativeY);
        this.text.setFont(Constant.font);
        try{
            this.statement = new ImageView(new Image("sources/img/draw_node_statement.png"));
            this.statement.setX(x);
            this.statement.setY(y);
            this.statement.setFitHeight(viewH);
            this.statement.setFitWidth(viewW);
            this.statement.setId("statement");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error in loading StatementNode image");
        }
    }

    @JSONCreator
    public StatementNode(@JSONField(name="factoryID") int factoryID,
                     @JSONField(name = "connectPlace") int[] connectPlace,
                     @JSONField(name = "connectTo") int[] connectTo,
                     @JSONField(name = "xIndex") double xIndex,
                     @JSONField(name = "yIndex") double yIndex,
                     @JSONField(name = "preID") int preID,
                     @JSONField(name = "prePlace") int prePlace,
                     @JSONField(name = "nxtID") int nxtID,
                     @JSONField(name = "nxtPlace") int nxtPlace,
                     @JSONField(name = "statementText") String statementText) {
        super(factoryID,connectPlace,connectTo,xIndex,yIndex);
        this.preID = preID;
        this.prePlace = prePlace;
        this.nxtID = nxtID;
        this.nxtPlace = nxtPlace;
        this.statementText = statementText;
        this.text = new Text(statementText);
        this.text.setX(xIndex + statementTextRelativeX);
        this.text.setY(yIndex + statementTextRelativeY);
        this.text.setFont(Constant.font);
        try {
            this.statement = new ImageView(new Image("sources/img/draw_node_statement.png"));
            this.statement.setX(xIndex);
            this.statement.setY(yIndex);
            this.statement.setFitWidth(viewW);
            this.statement.setFitHeight(viewH);
            this.statement.setId("statement");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in loading StatementNode image");
        }
    }

    @JSONField(serialize=false)
    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public int getPreID() {
        return preID;
    }

    public void setPreID(int preID) {
        this.preID = preID;
        connectTo[1] = preID;
    }

    public int getNxtID() {
        return nxtID;
    }

    public void setNxtID(int nxtID) {
        this.nxtID = nxtID;
        connectTo[2] = nxtID;
    }

    public int getPrePlace() {
        return prePlace;
    }

    public void setPrePlace(int prePlace) {
        this.prePlace = prePlace;
        connectPlace[1] = prePlace;
    }

    public int getNxtPlace() {
        return nxtPlace;
    }

    public void setNxtPlace(int nxtPlace) {
        this.nxtPlace = nxtPlace;
        connectPlace[2] = nxtPlace;
    }

    public String getStatementText() {
        return statementText;
    }

    public void setStatementText(String statementText) {
        this.statementText = statementText;
    }

    @Override @JSONField(serialize=false)
    public ImageView getImageView() {
        return statement;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.statement);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void draw(AnchorPane drawingArea, double x, double y) {
        super.setxIndex(x);
        super.setyIndex(y);
        this.statement.setX(x);
        this.statement.setY(y);
        this.text.setX(x + statementTextRelativeX);
        this.text.setY(y + statementTextRelativeY);
        drawingArea.getChildren().add(this.statement);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.statement);
        drawingArea.getChildren().remove(this.text);
    }

}

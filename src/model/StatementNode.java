package model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class StatementNode extends MyNode {
    private Text text;
    private int preID;
    private int prePlace;
    private int nxtID;
    private int nxtPlace;
    private ImageView statement;

    public StatementNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.preID = -1;
        this.prePlace = -1;
        this.nxtID = -1;
        this.nxtPlace = -1;
        this.text = new Text("statement code!");
        this.text.setX(x);
        this.text.setY(y + viewH / 2);
        this.text.setFont(Constant.font);
        try{
            this.statement = new ImageView(new Image("resources/img/draw_node_statement.png"));
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
    }

    public int getNxtID() {
        return nxtID;
    }

    public void setNxtID(int nxtID) {
        this.nxtID = nxtID;
    }

    public int getPrePlace() {
        return prePlace;
    }

    public void setPrePlace(int prePlace) {
        this.prePlace = prePlace;
    }

    public int getNxtPlace() {
        return nxtPlace;
    }

    public void setNxtPlace(int nxtPlace) {
        this.nxtPlace = nxtPlace;
    }

    @Override
    public ImageView getImageView() {
        return statement;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.statement);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.statement);
        drawingArea.getChildren().remove(this.text);
    }

    public void putInTable(ImageView[][] viewTable) {
        if(viewTable[(int)(this.statement.getX()/viewW)] [(int)(this.statement.getY()/viewH)] != null) {
            System.out.println("error in StatementNode putInTable");
            return;
        }
        viewTable[(int)(this.statement.getX()/viewW)] [(int)(this.statement.getY()/viewH)] = this.statement;
    }

    public void removeFromTable(ImageView[][] viewTable) {
        viewTable[(int)(this.statement.getX()/viewW)] [(int)(this.statement.getY()/viewH)] = null;
    }
}

package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class EndNode extends MyNode {
    private int preID;
    private int prePlace;
    private ImageView end;

    public EndNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.preID = -1;
        this.prePlace = -1;
        try{
            this.end = new ImageView(new Image("resources/img/draw_node_end.png"));
            this.end.setX(x);
            this.end.setY(y);
            this.end.setFitWidth(viewW);
            this.end.setFitHeight(viewH);
            this.end.setId("end");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error in loading EndNode image");
        }
    }

    public int getPreID() {
        return preID;
    }

    public void setPreID(int preID) {
        this.preID = preID;
    }

    public int getPrePlace() {
        return prePlace;
    }

    public void setPrePlace(int prePlace) {
        this.prePlace = prePlace;
    }

    @Override
    public ImageView getImageView() {
        return this.end;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.end);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.end);
    }

    @Override
    public void putInTable(MyNode[][] nodeTable) {
        if(nodeTable[(int)(this.end.getX()/viewW)] [(int)(this.end.getY()/viewH)] != null) {
            System.out.println("error in EndNode putInTable");
            return;
        }
        nodeTable[(int)(this.end.getX()/viewW)] [(int)(this.end.getY()/viewH)] = this;
    }

    @Override
    public void removeFromTable(MyNode[][] nodeTable) {
        nodeTable[(int)(this.end.getX()/viewW)] [(int)(this.end.getY()/viewH)] = null;
    }
}

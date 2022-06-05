package model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class StartNode extends MyNode {
    private int nxtID;
    private ImageView start;

    public StartNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.nxtID = -1;
        try{
            this.start = new ImageView(new Image("resources/img/draw_node_start.png"));
            this.start.setX(x);
            this.start.setY(y);
            this.start.setFitHeight(viewH);
            this.start.setFitWidth(viewW);
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error in loading StartNode image");
        }
    }

    public int getNxtID() {
        return nxtID;
    }

    public void setNxtID(int nxtID) {
        this.nxtID = nxtID;
    }

    @Override
    public ImageView getImageView() {
        return start;
    }

    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.start);
    }

    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.start);
    }

    public void putInTable(MyNode[][] nodeTable) {
        if(nodeTable[(int)(this.start.getX()/viewW)] [(int)(this.start.getY()/viewH)] != null) {
            System.out.println("error in StartNode putInTable");
            return;
        }
        nodeTable[(int)(this.start.getX()/viewW)] [(int)(this.start.getY()/viewH)] = this;
    }

    public void removeFromTable(MyNode[][] nodeTable) {
        nodeTable[(int)(this.start.getX()/viewW)] [(int)(this.start.getY()/viewH)] = null;
    }
}

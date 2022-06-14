package model;


import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class StartNode extends MyNode {
    private int nxtID;
    private int nxtPlace;
    private ImageView start;

    public StartNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.nxtID = -1;
        this.nxtPlace = -1;
        try{
            this.start = new ImageView(new Image("sources/img/draw_node_start.png"));
            this.start.setX(x);
            this.start.setY(y);
            this.start.setFitHeight(viewH);
            this.start.setFitWidth(viewW);
            this.start.setId("start");
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
        connectTo[2] = nxtID;
    }

    public int getNxtPlace() {
        return nxtPlace;
    }

    public void setNxtPlace(int nxtPlace) {
        this.nxtPlace = nxtPlace;
        connectPlace[2] = nxtPlace;
    }

    @Override @JSONField(serialize=false)
    public ImageView getImageView() {
        return start;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.start);
    }

    @Override
    public void draw(AnchorPane drawingArea, double x, double y) {
        super.setxIndex(x);
        super.setyIndex(y);
        this.start.setX(x);
        this.start.setY(y);
        drawingArea.getChildren().add(this.start);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.start);
    }

    @JSONCreator
    public StartNode(@JSONField(name="factoryID") int factoryID,
                     @JSONField(name = "connectPlace") int[] connectPlace,
                     @JSONField(name = "connectTo") int[] connectTo,
                     @JSONField(name = "xIndex") double xIndex,
                     @JSONField(name = "yIndex") double yIndex,
                     @JSONField(name = "nxtID") int nxtID,
                     @JSONField(name = "nxtPlace") int nxtPlace) {
        super(factoryID,connectPlace,connectTo,xIndex,yIndex);
        this.nxtID = nxtID;
        this.nxtPlace = nxtPlace;
        try {
            this.start = new ImageView(new Image("sources/img/draw_node_start.png"));
            this.start.setX(xIndex);
            this.start.setY(yIndex);
            this.start.setFitWidth(viewW);
            this.start.setFitHeight(viewH);
            this.start.setId("start");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in loading StartNode image");
        }
    }
}

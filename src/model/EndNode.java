package model;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class EndNode extends MyNode {
//    private int preID;
//    private int prePlace;
    private ImageView end;

    public EndNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
//        this.preID = -1;
//        this.prePlace = -1;
        try{
            this.end = new ImageView(new Image("sources/img/draw_node_end.png"));
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
//        return preID;
        return this.getConnectTo()[1];
    }

    public void setPreID(int preID) {
//        this.preID = preID;
        this.getConnectTo()[1] = preID;
    }

    public int getPrePlace() {
//        return prePlace;
        return this.getConnectPlace()[1];
    }

    public void setPrePlace(int prePlace) {
//        this.prePlace = prePlace;
        this.getConnectPlace()[1] = prePlace;
    }

    @Override @JSONField(serialize=false)
    public ImageView getImageView() {
        return this.end;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.end);
    }

    @Override
    public void draw(AnchorPane drawingArea, double x, double y) {
        super.setxIndex(x);
        super.setyIndex(y);
        this.end.setX(x);
        this.end.setY(y);
        drawingArea.getChildren().add(this.end);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.end);
    }

    @JSONCreator
    public EndNode(@JSONField(name="factoryID") int factoryID,
                   @JSONField(name = "connectPlace") int[] connectPlace,
                   @JSONField(name = "connectTo") int[] connectTo,
                   @JSONField(name = "xIndex") double xIndex,
                   @JSONField(name = "yIndex") double yIndex
//                   @JSONField(name = "preID") int preID,
//                   @JSONField(name = "prePlace") int prePlace
    ) {
        super(factoryID,connectPlace,connectTo,xIndex,yIndex);
//        this.preID = preID;
//        this.prePlace = prePlace;
        try {
            this.end = new ImageView(new Image("sources/img/draw_node_end.png"));
            this.end.setX(xIndex);
            this.end.setY(yIndex);
            this.end.setFitWidth(viewW);
            this.end.setFitHeight(viewH);
            this.end.setId("end");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in loading EndNode image");
        }
    }

}

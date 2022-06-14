package model;

import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class LoopEndNode extends MyNode {
    private int loop_endPreID;
    private int loop_endPrePlace;
    private int loop_endNxtID;
    private int loop_endNxtPlace;
    private ImageView loop_end;

    public LoopEndNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.loop_endPreID = -1;
        this.loop_endPrePlace = -1;
        this.loop_endNxtID = -1;
        this.loop_endNxtPlace = -1;
        try{
            this.loop_end = new ImageView(new Image("sources/img/draw_node_loop_end.png"));
            this.loop_end.setX(x);
            this.loop_end.setY(y);
            this.loop_end.setFitHeight(viewH);
            this.loop_end.setFitWidth(viewW);
            this.loop_end.setId("loop_end");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error in loading LoopEndNode image");
        }
    }

    public int getLoop_endPreID() {
        return loop_endPreID;
    }

    public int getLoop_endNxtID() {
        return loop_endNxtID;
    }

    public void setLoop_endPreID(int loop_endPreID) {
        this.loop_endPreID = loop_endPreID;
        connectTo[1] = loop_endPreID;
    }

    public void setLoop_endNxtID(int loop_endNxtID) {
        this.loop_endNxtID = loop_endNxtID;
        connectTo[2] = loop_endNxtID;
    }

    public int getLoop_endPrePlace() {
        return loop_endPrePlace;
    }

    public void setLoop_endPrePlace(int loop_endPrePlace) {
        this.loop_endPrePlace = loop_endPrePlace;
        connectPlace[1] = loop_endPrePlace;
    }

    public int getLoop_endNxtPlace() {
        return loop_endNxtPlace;
    }

    public void setLoop_endNxtPlace(int loop_endNxtPlace) {
        this.loop_endNxtPlace = loop_endNxtPlace;
        connectPlace[2] = loop_endNxtPlace;
    }

    @Override @JSONField(serialize=false)
    public ImageView getImageView() {
        return this.loop_end;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.loop_end);
    }

    @Override
    public void draw(AnchorPane drawingArea, double x, double y) {
        super.setxIndex(x);
        super.setyIndex(y);
        this.loop_end.setX(x);
        this.loop_end.setY(y);
        drawingArea.getChildren().add(this.loop_end);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.loop_end);
    }

}

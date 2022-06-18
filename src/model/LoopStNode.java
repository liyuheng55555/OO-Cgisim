package model;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static model.Constant.*;

public class LoopStNode extends MyNode {

//    private int loop_stPreID;
//    private int loop_stPrePlace;
//    private int loop_stNxtID;
//    private int loop_stNxtPlace;
    private ImageView loop_st;

    public LoopStNode(int factoryID, int x, int y) {
        super(factoryID,x,y);
//        this.loop_stPreID = -1;
//        this.loop_stPrePlace = -1;
//        this.loop_stNxtID = -1;
//        this.loop_stNxtPlace = -1;
        try{
            this.loop_st = new ImageView(new Image("sources/img/draw_node_loop_st.png"));
            this.loop_st.setX(x);
            this.loop_st.setY(y);
            this.loop_st.setFitHeight(viewH);
            this.loop_st.setFitWidth(viewW);
            this.loop_st.setId("loop_st");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error in loading IfNode image");
        }
    }

    @JSONCreator
    public LoopStNode(@JSONField(name="factoryID") int factoryID,
                       @JSONField(name = "connectPlace") int[] connectPlace,
                       @JSONField(name = "connectTo") int[] connectTo,
                       @JSONField(name = "xIndex") double xIndex,
                       @JSONField(name = "yIndex") double yIndex,
//                       @JSONField(name = "loop_stPreID") int loop_stPreID,
//                       @JSONField(name = "loop_stPrePlace") int loop_stPrePlace,
//                       @JSONField(name = "loop_stNxtID") int loop_stNxtID,
//                       @JSONField(name = "loop_stNxtPlace") int loop_stNxtPlace,
                      @JSONField(name = "loop_stText") String loop_stText) {
        super(factoryID,connectPlace,connectTo,xIndex,yIndex);
//        this.loop_stPreID = loop_stPreID;
//        this.loop_stPrePlace = loop_stPrePlace;
//        this.loop_stNxtID = loop_stNxtID;
//        this.loop_stNxtPlace = loop_stNxtPlace;
//        recoverConnect(connectTo, connectPlace);
        try {
            this.loop_st = new ImageView(new Image("sources/img/draw_node_loop_st.png"));
            this.loop_st.setX(xIndex);
            this.loop_st.setY(yIndex);
            this.loop_st.setFitWidth(viewW);
            this.loop_st.setFitHeight(viewH);
            this.loop_st.setId("loop_st");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in loading IfNode image");
        }
    }

    public int getLoop_stPreID() {
//        return loop_stPreID;
        return connectTo[1];
    }

    public void setLoop_stPreID(int loop_stPreID) {
//        this.loop_stPreID = loop_stPreID;
        connectTo[1] = loop_stPreID;
    }

    public int getLoop_stNxtID() {
        return connectTo[2];
    }

    public void setLoop_stNxtID(int loop_stNxtID) {
//        this.loop_stNxtID = loop_stNxtID;
        connectTo[2] = loop_stNxtID;
    }

    public int getLoop_stPrePlace() {
//        return loop_stPrePlace;
        return connectPlace[1];
    }

    public void setLoop_stPrePlace(int loop_stPrePlace) {
//        this.loop_stPrePlace = loop_stPrePlace;
        connectPlace[1] = loop_stPrePlace;
    }

    public int getLoop_stNxtPlace() {
//        return loop_stNxtPlace;
        return connectPlace[2];
    }

    public void setLoop_stNxtPlace(int loop_stNxtPlace) {
//        this.loop_stNxtPlace = loop_stNxtPlace;
        connectPlace[2] = loop_stNxtPlace;
    }

    @Override @JSONField(serialize=false)
    public ImageView getImageView() {
        return loop_st;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.loop_st);
    }

    @Override
    public void draw(AnchorPane drawingArea, double x, double y) {
        super.setxIndex(x);
        super.setyIndex(y);
        this.loop_st.setX(x);
        this.loop_st.setY(y);
        drawingArea.getChildren().add(this.loop_st);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.loop_st);
    }

}

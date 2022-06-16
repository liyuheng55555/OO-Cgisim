package model;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static model.Constant.*;

public class LoopEndNode extends MyNode {
    private Text text;
//    private int loop_endPreID;
//    private int loop_endPrePlace;
//    private int loop_endNxtID;
//    private int loop_endNxtPlace;
    private String loop_endText; // loop_endText is the text of the loop end node
    private ImageView loop_end;

    public LoopEndNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
//        this.loop_endPreID = -1;
//        this.loop_endPrePlace = -1;
//        this.loop_endNxtID = -1;
//        this.loop_endNxtPlace = -1;
        this.loop_endText = "loop end code!";
        this.text = new Text("loop end code!");
        this.text.setX(x + LoopTextRelativeX);
        this.text.setY(y + LoopTextRelativeY);
        this.text.setFont(Constant.font);
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

    @JSONCreator
    public LoopEndNode(@JSONField(name="factoryID") int factoryID,
                     @JSONField(name = "connectPlace") int[] connectPlace,
                     @JSONField(name = "connectTo") int[] connectTo,
                     @JSONField(name = "xIndex") double xIndex,
                     @JSONField(name = "yIndex") double yIndex,
//                     @JSONField(name = "loop_endPreID") int loop_endPreID,
//                     @JSONField(name = "loop_endPrePlace") int loop_endPrePlace,
//                     @JSONField(name = "loop_endNxtID") int loop_endNxtID,
//                     @JSONField(name = "loop_endNxtPlace") int loop_endNxtPlace,
                       @JSONField(name = "loop_endText") String loop_endText) {
        super(factoryID,connectPlace,connectTo,xIndex,yIndex);
//        this.loop_endPreID = loop_endPreID;
//        this.loop_endPrePlace = loop_endPrePlace;
//        this.loop_endNxtID = loop_endNxtID;
//        this.loop_endNxtPlace = loop_endNxtPlace;

        try {
            this.loop_end = new ImageView(new Image("sources/img/draw_node_loop_end.png"));
            this.loop_end.setX(xIndex);
            this.loop_end.setY(yIndex);
            this.loop_end.setFitWidth(viewW);
            this.loop_end.setFitHeight(viewH);
            this.loop_end.setId("loop_end");
            this.loop_endText = loop_endText;
            this.text = new Text(loop_endText);
            this.text.setX(xIndex + LoopTextRelativeX);
            this.text.setY(yIndex + LoopTextRelativeY);
            this.text.setFont(Constant.font);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in loading LoopEndNode image");
        }
    }

    @JSONField(serialize=false)
    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public int getLoop_endPreID() {
//        return loop_endPreID;
        return connectTo[1];
    }

    public int getLoop_endNxtID() {
//        return loop_endNxtID;
        return connectTo[2];
    }

    public void setLoop_endPreID(int loop_endPreID) {
//        this.loop_endPreID = loop_endPreID;
        connectTo[1] = loop_endPreID;
    }

    public void setLoop_endNxtID(int loop_endNxtID) {
//        this.loop_endNxtID = loop_endNxtID;
        connectTo[2] = loop_endNxtID;
    }

    public int getLoop_endPrePlace() {
//        return loop_endPrePlace;
        return connectPlace[1];
    }

    public void setLoop_endPrePlace(int loop_endPrePlace) {
//        this.loop_endPrePlace = loop_endPrePlace;
        connectPlace[1] = loop_endPrePlace;
    }

    public int getLoop_endNxtPlace() {
//        return loop_endNxtPlace;
        return connectPlace[2];
    }

    public void setLoop_endNxtPlace(int loop_endNxtPlace) {
//        this.loop_endNxtPlace = loop_endNxtPlace;
        connectPlace[2] = loop_endNxtPlace;
    }

    public String getLoop_endText() {
        return loop_endText;
    }

    public void setLoop_endText(String loop_stText) {
        this.loop_endText = loop_stText;
    }

    @Override @JSONField(serialize=false)
    public ImageView getImageView() {
        return this.loop_end;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.loop_end);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void draw(AnchorPane drawingArea, double x, double y) {
        super.setxIndex(x);
        super.setyIndex(y);
        this.loop_end.setX(x);
        this.loop_end.setY(y);
        this.text.setX(x + LoopTextRelativeX);
        this.text.setY(y + LoopTextRelativeY);
        drawingArea.getChildren().add(this.loop_end);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.loop_end);
        drawingArea.getChildren().remove(this.text);
    }

}

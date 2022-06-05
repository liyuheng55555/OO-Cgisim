package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class LoopEndNode extends MyNode {
    private int loop_endPreID;
    private int loop_endNxtID;
    private ImageView loop_end;

    public LoopEndNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.loop_endPreID = -1;
        this.loop_endNxtID = -1;
        try{
            this.loop_end = new ImageView(new Image("resources/img/draw_node_loop_end.png"));
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
    }

    public void setLoop_endNxtID(int loop_endNxtID) {
        this.loop_endNxtID = loop_endNxtID;
    }

    public ImageView getEnd() {
        return loop_end;
    }

    public void setEnd(ImageView loop_end) {
        this.loop_end = loop_end;
    }

    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.loop_end);
    }

    @Override
    public ImageView getImageView() {
        return this.loop_end;
    }

    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.loop_end);
    }

    public void putInTable(MyNode[][] nodeTable) {
        if(nodeTable[(int)(this.loop_end.getX()/viewW)] [(int)(this.loop_end.getY()/viewH)] != null) {
            System.out.println("error in EndNode putInTable");
            return;
        }
        nodeTable[(int)(this.loop_end.getX()/viewW)] [(int)(this.loop_end.getY()/viewH)] = this;
    }

    public void removeFromTable(MyNode[][] nodeTable) {
        nodeTable[(int)(this.loop_end.getX()/viewW)] [(int)(this.loop_end.getY()/viewH)] = null;
    }
}

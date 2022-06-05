package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class LoopNode extends MyNode {
    private Text text;
    private int loop_stPreID;
    private int loop_stNxtID;
    private int loop_endPreID;
    private int loop_endNxtID;
    private ImageView loop_st;
    private ImageView loop_end;

    public LoopNode(int factoryID, int x, int y) {
        super(factoryID,x,y);
        this.loop_stPreID = -1;
        this.loop_stNxtID = -1;
        this.loop_endPreID = -1;
        this.loop_endNxtID = -1;
        this.text = new Text("input your code!");
        this.text.setX(x + viewW / 2);
        this.text.setY(y + viewH / 2);
        try{
            this.loop_st = new ImageView(new Image("resources/img/draw_node_loop_st.png"));
            this.loop_end = new ImageView(new Image("resources/img/draw_node_loop_end.png"));
            this.loop_st.setX(x);
            this.loop_st.setY(y);
            this.loop_end.setX(x);
            this.loop_end.setY(y + viewH * 2);
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error in loading IfNode image");
        }
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public int getLoop_stPreID() {
        return loop_stPreID;
    }

    public void setLoop_stPreID(int loop_stPreID) {
        this.loop_stPreID = loop_stPreID;
    }

    public int getLoop_stNxtID() {
        return loop_stNxtID;
    }

    public void setLoop_stNxtID(int loop_stNxtID) {
        this.loop_stNxtID = loop_stNxtID;
    }

    public int getLoop_endPreID() {
        return loop_endPreID;
    }

    public void setLoop_endPreID(int loop_endPreID) {
        this.loop_endPreID = loop_endPreID;
    }

    public int getLoop_endNxtID() {
        return loop_endNxtID;
    }

    public void setLoop_endNxtID(int loop_endNxtID) {
        this.loop_endNxtID = loop_endNxtID;
    }

    public ImageView getLoop_st() {
        return loop_st;
    }

    public void setLoop_st(ImageView loop_st) {
        this.loop_st = loop_st;
    }

    public ImageView getLoop_end() {
        return loop_end;
    }

    public void setLoop_end(ImageView loop_end) {
        this.loop_end = loop_end;
    }
}

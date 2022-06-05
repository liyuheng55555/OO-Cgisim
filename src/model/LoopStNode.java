package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class LoopStNode extends MyNode {
    private Text text;
    private int loop_stPreID;
    private int loop_stPrePlace;
    private int loop_stNxtID;
    private int loop_stNxtPlace;
    private ImageView loop_st;

    public LoopStNode(int factoryID, int x, int y) {
        super(factoryID,x,y);
        this.loop_stPreID = -1;
        this.loop_stPrePlace = -1;
        this.loop_stNxtID = -1;
        this.loop_stNxtPlace = -1;
        this.text = new Text("input your code!");
        this.text.setX(x + viewW / 2);
        this.text.setY(y + viewH / 2);
        try{
            this.loop_st = new ImageView(new Image("resources/img/draw_node_loop_st.png"));
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

    public int getLoop_stPrePlace() {
        return loop_stPrePlace;
    }

    public void setLoop_stPrePlace(int loop_stPrePlace) {
        this.loop_stPrePlace = loop_stPrePlace;
    }

    public int getLoop_stNxtPlace() {
        return loop_stNxtPlace;
    }

    public void setLoop_stNxtPlace(int loop_stNxtPlace) {
        this.loop_stNxtPlace = loop_stNxtPlace;
    }

    @Override
    public ImageView getImageView() {
        return loop_st;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.loop_st);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.loop_st);
        drawingArea.getChildren().remove(this.text);
    }

    @Override
    public void putInTable(MyNode[][] nodeTable) {
        if(nodeTable[(int)(this.loop_st.getX()/viewW)] [(int)(this.loop_st.getY()/viewH)] != null) {
            System.out.println("error in LoopNode putInTable");
            return;
        }
        nodeTable[(int)(this.loop_st.getX()/viewW)] [(int)(this.loop_st.getY()/viewH)] = this;
    }

    @Override
    public void removeFromTable(MyNode[][] nodeTable) {
        nodeTable[(int)(this.loop_st.getX()/viewW)] [(int)(this.loop_st.getY()/viewH)] = null;
    }
}

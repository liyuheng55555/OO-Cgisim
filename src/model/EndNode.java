package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EndNode extends MyNode {
    private int preID;
    private ImageView end;

    public EndNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.preID = -1;
        try{
            this.end = new ImageView(new Image("resources/img/draw_node_end.png"));
            this.end.setX(x);
            this.end.setY(y);
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

    public ImageView getEnd() {
        return end;
    }

    public void setEnd(ImageView end) {
        this.end = end;
    }
}

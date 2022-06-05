package model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    public ImageView getStart() {
        return start;
    }

    public void setStart(ImageView start) {
        this.start = start;
    }
}

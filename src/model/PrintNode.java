package model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static model.Constant.*;


public class PrintNode extends MyNode {
    private Text text;
    private int preID;
    private int prePlace;
    private int nxtID;
    private int nxtPlace;
    private ImageView print;

    public PrintNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.preID = -1;
        this.prePlace = -1;
        this.nxtID = -1;
        this.nxtPlace = -1;
        this.text = new Text("print code!");
        this.text.setX(x + textRelativeX);
        this.text.setY(y + textRelativeY);
        this.text.setFont(Constant.font);
        try{
            this.print = new ImageView(new Image("resources/img/draw_node_print.png"));
            this.print.setX(x);
            this.print.setY(y);
            this.print.setFitHeight(viewH);
            this.print.setFitWidth(viewW);
            this.print.setId("print");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("error in loading Print image");
        }
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public int getPreID() {
        return preID;
    }

    public void setPreID(int preID) {
        this.preID = preID;
    }

    public int getNxtID() {
        return nxtID;
    }

    public void setNxtID(int nxtID) {
        this.nxtID = nxtID;
    }

    public int getPrePlace() {
        return prePlace;
    }

    public void setPrePlace(int prePlace) {
        this.prePlace = prePlace;
    }

    public int getNxtPlace() {
        return nxtPlace;
    }

    public void setNxtPlace(int nxtPlace) {
        this.nxtPlace = nxtPlace;
    }

    @Override
    public ImageView getImageView() {
        return print;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.print);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void draw(AnchorPane drawingArea, double x, double y) {
        this.print.setX(x);
        this.print.setY(y);
        this.text.setX(x + textRelativeX);
        this.text.setY(y + textRelativeY);
        drawingArea.getChildren().add(this.print);
        drawingArea.getChildren().add(this.text);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.print);
        drawingArea.getChildren().remove(this.text);
    }

}

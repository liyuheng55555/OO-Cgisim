package model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static model.Constant.viewH;
import static model.Constant.viewW;


public class PrintNode extends MyNode {
    private Text text;
    private int preID;
    private int nxtID;
    private ImageView print;

    public PrintNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.preID = -1;
        this.nxtID = -1;
        this.text = new Text("input your code!");
        this.text.setX(x + viewW / 2);
        this.text.setY(y + viewH / 2);
        try{
            this.print = new ImageView(new Image("resources/img/draw_node_print.png"));
            this.print.setX(x);
            this.print.setY(y);
            this.print.setFitHeight(viewH);
            this.print.setFitWidth(viewW);
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

    public ImageView getPrint() {
        return print;
    }



    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.print);
        drawingArea.getChildren().add(this.text);
    }

    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.print);
        drawingArea.getChildren().remove(this.text);
    }

    public void putInTable(ImageView[][] viewTable) {
        if(viewTable[(int)(this.print.getX()/viewW)] [(int)(this.print.getY()/viewH)] != null) {
            System.out.println("error in StatementNode putInTable");
            return;
        }
        viewTable[(int)(this.print.getX()/viewW)] [(int)(this.print.getY()/viewH)] = this.print;
    }

    public void removeFromTable(ImageView[][] viewTable) {
        viewTable[(int)(this.print.getX()/viewW)] [(int)(this.print.getY()/viewH)] = null;
    }
}

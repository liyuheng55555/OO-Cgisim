package model;


import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
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
    private String printText; // printText is the text of the print node
    private ImageView print;

    public PrintNode(int factoryID,int x,int y) {
        super(factoryID,x,y);
        this.preID = -1;
        this.prePlace = -1;
        this.nxtID = -1;
        this.nxtPlace = -1;
        this.printText = "print code!";
        this.text = new Text("print code!");
        this.text.setX(x + textRelativeX);
        this.text.setY(y + textRelativeY);
        this.text.setFont(Constant.font);
        try{
            this.print = new ImageView(new Image("sources/img/draw_node_print.png"));
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

    @JSONCreator
    public PrintNode(@JSONField(name="factoryID") int factoryID,
                         @JSONField(name = "connectPlace") int[] connectPlace,
                         @JSONField(name = "connectTo") int[] connectTo,
                         @JSONField(name = "xIndex") double xIndex,
                         @JSONField(name = "yIndex") double yIndex,
                         @JSONField(name = "preID") int preID,
                         @JSONField(name = "prePlace") int prePlace,
                         @JSONField(name = "nxtID") int nxtID,
                         @JSONField(name = "nxtPlace") int nxtPlace,
                         @JSONField(name = "printText") String printText) {
        super(factoryID,connectPlace,connectTo,xIndex,yIndex);
        this.preID = preID;
        this.prePlace = prePlace;
        this.nxtID = nxtID;
        this.nxtPlace = nxtPlace;
        this.printText = printText;
        this.text = new Text(printText);
        this.text.setX(xIndex);
        this.text.setY(yIndex + viewH / 2);
        this.text.setFont(Constant.font);
        try {
            this.print = new ImageView(new Image("sources/img/draw_node_print.png"));
            this.print.setX(xIndex);
            this.print.setY(yIndex);
            this.print.setFitWidth(viewW);
            this.print.setFitHeight(viewH);
            this.print.setId("print");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in loading Print image");
        }
    }

    @JSONField(serialize=false)
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
        connectTo[1] = preID;
    }

    public int getNxtID() {
        return nxtID;
    }

    public void setNxtID(int nxtID) {
        this.nxtID = nxtID;
        connectTo[2] = nxtID;
    }

    public int getPrePlace() {
        return prePlace;
    }

    public void setPrePlace(int prePlace) {
        this.prePlace = prePlace;
        connectPlace[1] = prePlace;
    }

    public int getNxtPlace() {
        return nxtPlace;
    }

    public void setNxtPlace(int nxtPlace) {
        this.nxtPlace = nxtPlace;
        connectPlace[2] = nxtPlace;
    }

    public String getPrintText() {
        return printText;
    }

    public void setPrintText(String printText) {
        this.printText = printText;
    }

    @Override @JSONField(serialize=false)
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
        super.setxIndex(x);
        super.setyIndex(y);
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

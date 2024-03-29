package model;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class DownLeftLine extends MyNode {
    private ImageView line;
    private String lineType;

    public DownLeftLine(int x,int y) {
        super(0,x,y);
        this.lineType = "line_down_left";
        try{
            this.line = new ImageView(new Image("sources/img/draw_line_down_left.png"));
            this.line.setX(x);
            this.line.setY(y);
            this.line.setFitHeight(viewH);
            this.line.setFitWidth(viewW);
            this.line.setId("line_down_left");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error in loading LineNode image");
        }
    }

    @JSONCreator
    public DownLeftLine(@JSONField(name="factoryID") int factoryID,
                        @JSONField(name = "connectPlace") int[] connectPlace,
                        @JSONField(name = "connectTo") int[] connectTo,
                        @JSONField(name = "xIndex") double xIndex,
                        @JSONField(name = "yIndex") double yIndex,
                        @JSONField(name = "lineType") String lineType) {
        super(factoryID,connectPlace,connectTo,xIndex,yIndex);
        this.lineType = lineType;
        try {
            this.line = new ImageView(new Image("sources/img/draw_line_down_left.png"));
            this.line.setX(xIndex);
            this.line.setY(yIndex);
            this.line.setFitWidth(viewW);
            this.line.setFitHeight(viewH);
            this.line.setId("line_down_left");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in loading LineNode image");
        }
    }

    @Override @JSONField(serialize=false)
    public ImageView getImageView() {
        return line;
    }

    @Override
    public void draw(AnchorPane drawingArea) {
        drawingArea.getChildren().add(this.line);
    }

    @Override
    public void remove(AnchorPane drawingArea) {
        drawingArea.getChildren().remove(this.line);
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }
}

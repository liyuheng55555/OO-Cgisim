package model;

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


}

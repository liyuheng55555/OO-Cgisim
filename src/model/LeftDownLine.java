package model;

import com.alibaba.fastjson.annotation.JSONField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static model.Constant.viewH;
import static model.Constant.viewW;

public class LeftDownLine extends MyNode {
    private ImageView line;

    public LeftDownLine(int x,int y) {
        super(0,x,y);
        try{
            this.line = new ImageView(new Image("sources/img/draw_line_left_down.png"));
            this.line.setX(x);
            this.line.setY(y);
            this.line.setFitHeight(viewH);
            this.line.setFitWidth(viewW);
            this.line.setId("line_left_down");
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

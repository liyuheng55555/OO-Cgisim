package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Constant;

import java.sql.SQLOutput;

/**
 * 在table的一些位置上，绘制某种特定的图案，而且能擦掉
 */
public class ShowAnything {
    ImageView imageView;
//    final int tableH;
//    final int tableW;
//    final double
    AnchorPane drawingArea;
    int relativeX;
    int relativeY;

    private int drawCount = 0;  // 目前绘制此类图形的数量

    private final ImageView[][] table = new ImageView[Constant.tableH][Constant.tableW];
    private final ImageView[][] wrong_table = new ImageView[Constant.tableH][Constant.tableW];
    /**
     * 构造出一个新的ShowAnything对象
     * @param imageView     特定图案，应当已经设置好宽高
     * @param drawingArea   绘制区
     * @param relativeX     图案相对于给出的坐标的横向偏移
     * @param relativeY     图案相对于给出的坐标的纵向偏移
     */
    public ShowAnything(ImageView imageView, AnchorPane drawingArea, int relativeX, int relativeY) {
        this.imageView = imageView;
        this.drawingArea = drawingArea;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }


    /**
     * 生成一个this.imageView的副本；
     * ImageView的构造函数没有复制功能，这是不得已而为之
     * @return this.imageView的副本
     */
    private ImageView copyImageView() {
        ImageView imageV = new ImageView(imageView.getImage());
        imageV.setId(imageView.getId());
        imageV.setFitHeight(imageView.getFitHeight());
        imageV.setFitWidth(imageView.getFitWidth());
        return imageV;
    }

    /**
     * 在table的[y][x]上画一个图案
     * @param x     横向索引
     * @param y     纵向索引
     * @throws Exception x或y错误
     */
    public void draw(int x, int y) throws Exception {
        if (x<0 || x>= Constant.tableW || y<0 || y>=Constant.tableH)
            throw new Exception("x或y错误");
        if (table[y][x]!=null) {
            drawingArea.getChildren().remove(table[y][x]);
            drawingArea.getChildren().add(table[y][x]);
            return;
        }

        table[y][x] = copyImageView();
        table[y][x].setX(x*Constant.viewW+relativeX);
        table[y][x].setY(y*Constant.viewH+relativeY);
        drawCount++;
        drawingArea.getChildren().add(table[y][x]);
    }
    public void draw_wrong(int x, int y) throws Exception {
        if (x<0 || x>= Constant.tableW || y<0 || y>=Constant.tableH)
            throw new Exception("x或y错误");
        if (wrong_table[y][x]!=null) {
            return;
        }
        ImageView imageView = new ImageView();
        Image image = new Image("/sources/img/wrong.png");
        imageView.setImage(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        wrong_table[y][x]=imageView;
        wrong_table[y][x].setX(x*Constant.viewW+relativeX);
        System.out.println("debug_wrong_pic");
        System.out.println(x*Constant.viewW+relativeX);
        wrong_table[y][x].setY(y*Constant.viewH+relativeY);
        System.out.println(y*Constant.viewH+relativeY);
        drawCount++;
        drawingArea.getChildren().add(wrong_table[y][x]);
    }
    public void clear_wrong(){
        for (int i=0; i<Constant.tableH; i++) {
            drawingArea.getChildren().removeAll(wrong_table[i]);
        }
    }
    /**
     *  擦除table[y][x]
     * @param x     横向索引
     * @param y     纵向索引
     */
    public void erase(int x, int y) throws Exception {
        if (x<0 || x>= Constant.tableW || y<0 || y>=Constant.tableH)
            throw new Exception("x或y错误");
        if (table[y][x]!=null) {
            drawCount--;
            drawingArea.getChildren().remove(table[y][x]);
            table[y][x] = null;
        }
    }

    /**
     * 清除所有此类图案
     */
    public void clear() {
        for (int i=0; i<Constant.tableH; i++) {
            drawingArea.getChildren().removeAll(table[i]);
        }
        drawCount = 0;
    }

    public boolean hasDraw() {
        System.out.println("drawCount:"+drawCount);
        return drawCount>0;
    }


}

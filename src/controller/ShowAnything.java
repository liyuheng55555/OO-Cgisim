package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Constant;

import java.sql.SQLOutput;

/**
 * ��table��һЩλ���ϣ�����ĳ���ض���ͼ���������ܲ���
 */
public class ShowAnything {
    ImageView imageView;
//    final int tableH;
//    final int tableW;
//    final double
    AnchorPane drawingArea;
    int relativeX;
    int relativeY;

    private int drawCount = 0;  // Ŀǰ���ƴ���ͼ�ε�����

    private final ImageView[][] table = new ImageView[Constant.tableH][Constant.tableW];
    private final ImageView[][] wrong_table = new ImageView[Constant.tableH][Constant.tableW];
    /**
     * �����һ���µ�ShowAnything����
     * @param imageView     �ض�ͼ����Ӧ���Ѿ����úÿ��
     * @param drawingArea   ������
     * @param relativeX     ͼ������ڸ���������ĺ���ƫ��
     * @param relativeY     ͼ������ڸ��������������ƫ��
     */
    public ShowAnything(ImageView imageView, AnchorPane drawingArea, int relativeX, int relativeY) {
        this.imageView = imageView;
        this.drawingArea = drawingArea;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }


    /**
     * ����һ��this.imageView�ĸ�����
     * ImageView�Ĺ��캯��û�и��ƹ��ܣ����ǲ����Ѷ�Ϊ֮
     * @return this.imageView�ĸ���
     */
    private ImageView copyImageView() {
        ImageView imageV = new ImageView(imageView.getImage());
        imageV.setId(imageView.getId());
        imageV.setFitHeight(imageView.getFitHeight());
        imageV.setFitWidth(imageView.getFitWidth());
        return imageV;
    }

    /**
     * ��table��[y][x]�ϻ�һ��ͼ��
     * @param x     ��������
     * @param y     ��������
     * @throws Exception x��y����
     */
    public void draw(int x, int y) throws Exception {
        if (x<0 || x>= Constant.tableW || y<0 || y>=Constant.tableH)
            throw new Exception("x��y����");
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
            throw new Exception("x��y����");
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
     *  ����table[y][x]
     * @param x     ��������
     * @param y     ��������
     */
    public void erase(int x, int y) throws Exception {
        if (x<0 || x>= Constant.tableW || y<0 || y>=Constant.tableH)
            throw new Exception("x��y����");
        if (table[y][x]!=null) {
            drawCount--;
            drawingArea.getChildren().remove(table[y][x]);
            table[y][x] = null;
        }
    }

    /**
     * ������д���ͼ��
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

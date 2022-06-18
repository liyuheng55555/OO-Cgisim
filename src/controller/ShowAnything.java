package controller;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Constant;

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

    private final ImageView[][] table = new ImageView[Constant.tableH][Constant.tableW];

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
    ImageView copyImageView() {
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
        if (table[y][x]!=null)
            erase(x,y);
        table[y][x] = copyImageView();
        table[y][x].setX(x*Constant.viewW+relativeX);
        table[y][x].setY(y*Constant.viewH+relativeY);
        drawingArea.getChildren().add(table[y][x]);
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
    }


}

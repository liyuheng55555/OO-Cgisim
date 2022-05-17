package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * �༭����
 */
public class Editer {
    private Point[] circles;
    private double x;
    private double y;
    private double width;
    private double height;
    private Line[] lines;
    private int OnClick = 0;

    /**
     * ��ȡͼ�α༭����װ�μ�--Բ
     * @return  Point[]
     */
    public Point[] getCircles() {
        return this.circles;
    }

    /**
     * ������
     * @param x ���Ͻ�x����
     * @param y ���Ͻ�y����
     * @param height    ��
     * @param width    ��
     */
    public Editer(double x, double y, double height, double width) {
        this.x = x;
        this.y = y;
        this.height = height + 10;
        this.width = width + 10;
        initLine(x, y); //��ʼ����
        initCircle(x, y);   //��ʼ��Բ
        disapperCircle();   //��ʼ��ʱ����Բ
        disapperLine();    //��ʼ��ʱ������
    }

    /**
     * get the editer's width
     * @return  width attribute
     */
    public double getWidth() {
        return width;
    }

    /**
     * set the editer's width
     * @param width width attribute
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * get the editer's height
     * @return  height attribute
     */
    public double getHeight() {
        return height;
    }

    /**
     * set the editer's height
     * @param height height attribute
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * get the editer onClick attribute
     * @return  onClick attribute
     */
    public int getOnClick() {
        return OnClick;
    }

    /**
     * set the editer onClick attribute
     * @param onClick onClick attribute
     */
    public void setOnClick(int onClick) {
        OnClick = onClick;
    }


    /**
     * ��ʼ��Բ
     * @param x Բ��x����
     * @param y ���Ͻ�y����
     */
    private void initCircle(double x, double y) {
        circles = new Point[9];
        for (int i = 0; i < 9; i++) {
            circles[i] = new Point(x, y, 5);
            circles[i].setFill(Color.BLUE);
        }
    }

    /**
     * ��ʼ����
     * @param x ���Ͻ�x����
     * @param y ���Ͻ�y����
     */
    private void initLine(double x, double y) {
        lines = new Line[8];
        lines[0] = new Line(x, y, x + width, y);
        lines[1] = new Line(x + width, y, x + 2 * width, y);
        lines[2] = new Line(x, y, x, y + height);
        lines[3] = new Line(x, y, x, y + 2 * height);
        lines[4] = new Line(x, y + 2 * height, x + width, y + 2 * height);
        lines[5] = new Line(x + width, y + 2 * height, x + 2 * width, y + 2 * height);
        lines[6] = new Line(x + 2 * width, y, x + 2 * width, y + height);
        lines[7] = new Line(x + 2 * width, y + height, x + 2 * width, y + 2 * height);
    }

    /**
     * ����ͼ���ƶ���������
     * @param x ���Ͻ�x����
     * @param y ���Ͻ�y����
     */
    private void lineMove(double x, double y) {
        x = x - width;
        y = y - height;
        double pos[][] = { { x, y, x + width, y }, { x, y, x + 2 * width, y }, { x, y, x, y + height },
                { x, y, x, y + 2 * height }, { x, y + 2 * height, x + width, y + 2 * height },
                { x + width, y + 2 * height, x + 2 * width, y + 2 * height },
                { x + 2 * width, y, x + 2 * width, y + height },
                { x + 2 * width, y + height, x + 2 * width, y + 2 * height } };
        for (int i = 0; i < 8; i++) {
            lines[i].setStartX(pos[i][0]);
            lines[i].setStartY(pos[i][1]);
            lines[i].setEndX(pos[i][2]);
            lines[i].setEndY(pos[i][3]);
        }
    }

    /**
     * ��MyShape�ĳ�Ա����ͼ����pane�����ͼ��
     * @param pane MyShape�ĳ�Ա����ͼ����pane
     */
    public void addEditer(Pane pane) {
        pane.getChildren().addAll(circles);
        pane.getChildren().addAll(lines);
    }

    public void delEditer(Pane pane) {
        pane.getChildren().removeAll(circles);
        pane.getChildren().removeAll(lines);
    }

    /**
     * չʾ�༭���Բ����
     * @param x ���Ͻ�x����
     * @param y ���Ͻ�y����
     */
    public void show(double x, double y) {
        lineMove(x, y);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                circles[3 * i + j].setCenterX(x + width * (i - 1));
                circles[3 * i + j].setCenterY(y + height * (j - 1));
                circles[3 * i + j].setRadius(5);
                if (i == 1 && j == 1) {
                    circles[3 * i + j].setRadius(0);    // ����Բ����ʾ
                }
            }
        }

    }

    /**
     * ����Բ��ʧ
     */
    public void disapperCircle() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                circles[3 * i + j].setRadius(0);
            }
        }
    }

    /**
     * ����������ʧ
     */
    public void disapperLine() {
        for (int i = 0; i < 8; i++) {
            lines[i].setStartX(0);
            lines[i].setStartY(0);
            lines[i].setEndX(0);
            lines[i].setEndY(0);
        }
    }

    /**
     * ���ñ༭����ʧ
     */
    public void disapper() {
        disapperCircle();
        disapperLine();
    }
}

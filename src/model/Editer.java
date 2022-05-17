package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * 编辑框类
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
     * 获取图形编辑框上装饰件--圆
     * @return  Point[]
     */
    public Point[] getCircles() {
        return this.circles;
    }

    /**
     * 构造器
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param height    高
     * @param width    宽
     */
    public Editer(double x, double y, double height, double width) {
        this.x = x;
        this.y = y;
        this.height = height + 10;
        this.width = width + 10;
        initLine(x, y); //初始化线
        initCircle(x, y);   //初始化圆
        disapperCircle();   //初始化时隐藏圆
        disapperLine();    //初始化时隐藏线
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
     * 初始化圆
     * @param x 圆的x坐标
     * @param y 左上角y坐标
     */
    private void initCircle(double x, double y) {
        circles = new Point[9];
        for (int i = 0; i < 9; i++) {
            circles[i] = new Point(x, y, 5);
            circles[i].setFill(Color.BLUE);
        }
    }

    /**
     * 初始化线
     * @param x 左上角x坐标
     * @param y 左上角y坐标
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
     * 随着图形移动而更新线
     * @param x 左上角x坐标
     * @param y 左上角y坐标
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
     * 向MyShape的成员变量图形组pane中添加图形
     * @param pane MyShape的成员变量图形组pane
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
     * 展示编辑框的圆点组
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     */
    public void show(double x, double y) {
        lineMove(x, y);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                circles[3 * i + j].setCenterX(x + width * (i - 1));
                circles[3 * i + j].setCenterY(y + height * (j - 1));
                circles[3 * i + j].setRadius(5);
                if (i == 1 && j == 1) {
                    circles[3 * i + j].setRadius(0);    // 中心圆不显示
                }
            }
        }

    }

    /**
     * 设置圆消失
     */
    public void disapperCircle() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                circles[3 * i + j].setRadius(0);
            }
        }
    }

    /**
     * 设置线条消失
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
     * 设置编辑框消失
     */
    public void disapper() {
        disapperCircle();
        disapperLine();
    }
}

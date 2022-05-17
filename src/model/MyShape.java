package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;


/**
 * MyShape类是所有自定义图形的父类
 * MyShape定义的所有图形的辖域范围为长方形，
 * 成员变量包含了图形的基本属性，如位置、大小等
 */
public abstract class MyShape {
    // factoryID of the shape [main key]
    protected int factoryID;
    // drawing area of the shape
    protected AnchorPane drawingArea;
    // draw controller of the shape
    protected DrawController drawController;
    // position of the shape
    protected double x;
    protected double y;
    // 左下角点的坐标
    protected double leftX;
    protected double leftY;
    // 右上角点的坐标
    protected double rightX;
    protected double rightY;
    // 图形的宽度和高度
    protected double width;
    protected double height;
    // 监听变量，可以监听图形的改变
    protected BooleanProperty booleanProperty;
    // 图形在drawingArea的状态，true为选中，false为未选中
    private boolean isSelected = false;
    // 图形成员
    protected Shape shape;
    private Editer editer;
    protected Text text;
    protected DrawPoints drawPoints;
    // 图形变换形状的最小变化值map
    private final static int[][] RESIZ_DRECTION = { { 0, -1, -1 }, { 1, -1, 0 }, { 2, -1, 1 }, { 3, 0, -1 },
            { 4, 0, 0 }, { 5, 0, 1 }, { 6, 1, -1 }, { 7, 1, 0 }, { 8, 1, 1 } };
    // 鼠标形状map
    private final static Cursor[] hand = { Cursor.NW_RESIZE, Cursor.W_RESIZE, Cursor.SW_RESIZE, Cursor.N_RESIZE,
            Cursor.MOVE, Cursor.S_RESIZE, Cursor.NE_RESIZE, Cursor.E_RESIZE, Cursor.SE_RESIZE };
    // 图形组
    protected Group pane;

    /**
     * 构造方法
     * @param x 图形的x坐标
     * @param y 图形的y坐标
     * @param width 图形的宽度
     * @param height 图形的高度
     */
    public MyShape(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        leftX = x - width;
        leftY = y - height;
        rightX = x + width;
        rightY = y + height;
    }

    /**
     * init the shape
     * @param shape 图形
     */
    public void setMyShape(Shape shape) {
        this.shape = shape;
        this.editer = new Editer(this.x, this.x, height, width);
        this.booleanProperty = new SimpleBooleanProperty(false);
        this.text = new Text();
        this.text.setX(x);
        this.text.setY(y);
        this.drawPoints = new DrawPoints();
        createDrawPoints();
        shape.setFill(Color.WHITE);     // 初始化颜色
        shape.setStroke(Color.BLACK);   //设置边框颜色
        pane = new Group();             // 初始化组
        pane.setCursor(Cursor.CLOSED_HAND); // 初始化鼠标形状
        pane.getChildren().add(shape);    // 将图形添加到组中
        addListener();                   // 添加监听
    }

//todo: 连接点的形式如何设置？

//    /**
//     * add connection info to the shape
//     * @param info 连接信息
//     */
//    public void addConnectionInfo(ConnectionInfo info) {
//        connectionInfos.add(info);
//    }
//
//    public void delConnectionInfo(MyLine line) {
//        ConnectionInfo delInfo = null;
//        for (ConnectionInfo info : connectionInfos) {
//            if (info.getLine() == line) {
//                System.out.println("delOK");
//                delInfo = info;
//            }
//        }
//        if (delInfo != null)
//            connectionInfos.remove(delInfo);
//    }

    /**
     * 添加监听
     */
    public void addListener() {
        setOnDrag();
        setOnRealse();
        resizeCursorListener();
        resizeListener();
        moveHandListener();
        changeListener();
    }

    /**
     * 设置鼠标拖动监听
     * 拖动时编辑框展示且仅展示边框线条
     */
    public void setOnDrag() {
        shape.setOnMouseDragged(e -> {
            Move(e.getX(), e.getY());
            editer.show(x, y);
            editer.disapperCircle();
            isSelected = false;
        });
    }

    /**
     * 图形跟随鼠标拖拽位置移动
     * @param x 鼠标拖拽后的x坐标
     * @param y 鼠标拖拽后的y坐标
     */
    public void Move(double x, double y) {
        double posX = x - this.getShape().getParent().getLayoutX(); // 当前图像相对于DrawingPane的x坐标
        double posY = y - this.getShape().getParent().getLayoutY(); // 当前图像相对于DrawingPane的y坐标
        booleanProperty.setValue(true);
        updateLocation(posX, posY); // 更新图形在DrawingPane的位置
        lineMove();                // 已于图像连接的线条移动
    }

    /**
     * 图形位置更新
     * @param x 移动后的x坐标
     * @param y 移动后的y坐标
     */
    public void updateLocation(double x, double y) {
        this.x = x;
        this.y = y;
        this.rightX = x + width;
        this.rightY = y + height;
        this.leftX = x - width;
        this.leftY = y - height;
        createDrawPoints();
        this.getEditer().setHeight(height + 10);
        this.getEditer().setWidth(width + 10);
        if (isSelected)
            editer.show(x, y);
        update();
    }

    /**
     * 默认的图形-线条连接点 默认为上下左右四个点
     * 可以自定义,通过子类重写该方法来改变连接点位置
     */
    public void createDrawPoints() {
        double leftMidX = this.x - width;
        double leftMidY = this.y;
        double upMidX = this.x;
        double upMidY = this.y - height;
        double rightMidX = this.x + width;
        double rightMidY = this.y;
        double downMidX = this.x;
        double downMidY = this.y + height;
        drawPoints.updateLocation(leftMidX, leftMidY, upMidX, upMidY, rightMidX, rightMidY, downMidX, downMidY);
    }

    /**
     * 当图形位置发生改变的时候，更新图形中的文字显示位置
     */
    public void update() {
        int len = text.getText().length() * 5;
        text.setX(x - len);
        text.setY(y);
    }

    /**
     * 已于shape连接的line在shape位置更新后需要跟随shape移动
     */
    public void lineMove() {
        for (ConnectionInfo info : connectionInfos) {   // 遍历所有具有连接的line
            if (info.getConnectionPart().equals("end")) {   // 如果连接的是结束点
                double centerX, centerY;
                centerX = drawPoints.getCircles()[info.getLocation()].getCenterX();
                centerY = drawPoints.getCircles()[info.getLocation()].getCenterY();
                info.getLine().endMove(centerX, centerY);
            } else if (info.getConnectionPart().equals("start")) {   // 如果连接的是开始点
                double centerX, centerY;
                centerX = drawPoints.getCircles()[info.getLocation()].getCenterX();
                centerY = drawPoints.getCircles()[info.getLocation()].getCenterY();
                info.getLine().startMove(centerX, centerY);
            }
        }
    }

    /**
     * 设置鼠标放开监听
     * 当鼠标放开时，设置编辑器的出现与否
     */
    public void setOnRealse() {
        shape.setOnMouseReleased(e -> {
            this.setToTop();    // 重绘图形 将当前图形置顶
            this.booleanProperty.setValue(false);
            if (!isSelected) {
                drawController.clearAllOnEdit();
                    isSelected = true;
                editer.show(x, y);
            } else {
                isSelected = false;
                editer.disapperAll();
            }
        });
    }

    /**
     * 重绘图形
     */
    public void setToTop() {
        drawingArea.getChildren().remove(shape);
        editer.delEditer(drawingArea);
        drawPoints.delPoint(drawingArea);
        drawingArea.getChildren().remove(text);
        this.getPane(drawingArea, drawController);
    }

    /**
     * 在drawingArea中绘制MyShape pane中的内容，同时设置图形的编辑器drawController
     */
    public void getPane(AnchorPane drawingArea, DrawController drawController) {
        editer.addEditer(drawingArea);
        drawingArea.getChildren().add(shape);
        drawingArea.getChildren().addAll(drawPoints.getCircles());
        drawPoints.setAllVisiable(false);
        drawingArea.getChildren().add(text);
        this.drawController = drawController;
        this.drawingArea = drawingArea;
    }

    /**
     * TODO
     */
    private void resizeCursorListener() {
        Point[] circles = editer.getCircles();
        for (int i = 0; i < circles.length; i++) {
            circles[i].setPosid(i);
            circles[i].setLeftX(RESIZ_DRECTION[i][0]);
            circles[i].setDirectionX(RESIZ_DRECTION[i][1]);
            circles[i].setDirectionY(RESIZ_DRECTION[i][2]);

            // 设置鼠标形状
            circles[i].setCursor(hand[i]);
        }
    }

    /**
     * 放缩图形
     */
    private void resizeListener() {
        Circle[] circles = editer.getCircles();
        for (int i = 0; i < circles.length; i++) {
            circles[i].setOnMouseDragged(e -> {             // 向每个editer的Point设置鼠标拖动事件
                Point point = ((Point) (e.getSource()));    // 获取原始拖拽点对象，它尚未移动
                double radius = point.getRadius();          // 设置原始拖拽点的半径
                double ox = point.getCenterX();             // 设置原始拖拽点的中心点x坐标
                double oy = point.getCenterY();             // 设置原始拖拽点的中心点y坐标
                double dx = (e.getX() - ox);                // 设置位移的x坐标大小
                double dy = (e.getY() - oy);                // 设置位移的y坐标大小
                int posId = point.getPosid();               // 获取原始拖拽点的id
                dx = dx * ((Point) (e.getSource())).getDirectionX();    // 设置位移的x坐标大小，单位为当前方向的最小位移
                dy = dy * ((Point) (e.getSource())).getDirectionY();    // 设置位移的y坐标大小，单位为最小位移
                dx = dx / 5;                                            // 设置位移的x坐标幅度
                dy = dy / 5;                                             // 设置位移的y坐标幅度

                if (radius * radius <= ((dx * dx) + dy * dy)) {
                    resizeShape(posId, dx, dy);
                    editer.show(this.x, this.y);
                }
            });

            circles[i].setOnMouseReleased(e -> {           // 向每个editer的Point设置鼠标释放事件
                this.booleanProperty.setValue(false);
            });
        }
    }

    protected void resizeShape(int posId, double dx, double dy) {
        if (width + dx >= 0 && height + dy >= 0) {
            // ??????ε?任
            this.width = width + dx;
            this.height = height + dy;
            if (posId <= 1) {
                this.x = rightX - width;
                this.y = rightY - height;
            } else {
                if (posId == 2) {
                    this.x = rightX - width;
                    this.y = leftY + height;
                } else {
                    if (posId == 3) {
                        this.x = rightX - width;
                        this.y = rightY - height;
                    } else {
                        if (posId == 6) {
                            this.x = leftX + width;
                            this.y = rightY - height;
                        } else {
                            this.x = leftX + width;
                            this.y = leftY + height;
                        }
                    }
                }
            }
            // 更新信息
            updateLocation(this.x, this.y);
            getEditer().setHeight(height + 10);
            getEditer().setWidth(width + 10);
            // 设置属性
            this.setX(x);
            this.setY(y);
            this.setWidth(width);
            this.setHeight(height);
            updateLocation(this.x, this.y);
            // 通知改变
            booleanProperty.setValue(true);
        }
    }

    /**
     * 设置shape的相关鼠标样式
     */
    private void moveHandListener() {
        this.getShape().setCursor(Cursor.MOVE);
    }


    /**
     * 设置change监听器,当booleanProperty发生改变的时候响应
     */
    public void changeListener() {
        booleanProperty.addListener(e -> {
            if (!booleanProperty.getValue()) {
                drawController.getPropertyController().setWorkShape(this);
                drawController.getPropertyController().update();
                drawController.saveChange();
            }
        });
    }

    public int getFactoryID() {
        return factoryID;
    }

    public void setFactoryID(int factoryID) {
        this.factoryID = factoryID;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLeftX() {
        return leftX;
    }

    public void setLeftX(double leftX) {
        this.leftX = leftX;
    }

    public double getLeftY() {
        return leftY;
    }

    public void setLeftY(double leftY) {
        this.leftY = leftY;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Shape getShape() {
        return this.shape;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setISelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public BooleanProperty getBooleanProperty() {
        return booleanProperty;
    }

    public void setBooleanProperty(BooleanProperty booleanProperty) {
        this.booleanProperty = booleanProperty;
    }

    public DrawPoints getDrawPoints() {
        return this.drawPoints;
    }

    public Editer getEditer() {
        return editer;
    }

    public void setEditer(Editer editer) {
        this.editer = editer;
    }

    /**
     * 重写equals方法，判断两个图形是否相同
     * @return true：相同，false：不同
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyShape) {
            return ((MyShape) obj).getFactoryID() == this.factoryID;
        }
        return false;
    }
}

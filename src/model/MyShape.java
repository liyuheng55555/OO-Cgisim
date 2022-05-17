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
 * MyShape���������Զ���ͼ�εĸ���
 * MyShape���������ͼ�ε�Ͻ��ΧΪ�����Σ�
 * ��Ա����������ͼ�εĻ������ԣ���λ�á���С��
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
    // ���½ǵ������
    protected double leftX;
    protected double leftY;
    // ���Ͻǵ������
    protected double rightX;
    protected double rightY;
    // ͼ�εĿ�Ⱥ͸߶�
    protected double width;
    protected double height;
    // �������������Լ���ͼ�εĸı�
    protected BooleanProperty booleanProperty;
    // ͼ����drawingArea��״̬��trueΪѡ�У�falseΪδѡ��
    private boolean isSelected = false;
    // ͼ�γ�Ա
    protected Shape shape;
    private Editer editer;
    protected Text text;
    protected DrawPoints drawPoints;
    // ͼ�α任��״����С�仯ֵmap
    private final static int[][] RESIZ_DRECTION = { { 0, -1, -1 }, { 1, -1, 0 }, { 2, -1, 1 }, { 3, 0, -1 },
            { 4, 0, 0 }, { 5, 0, 1 }, { 6, 1, -1 }, { 7, 1, 0 }, { 8, 1, 1 } };
    // �����״map
    private final static Cursor[] hand = { Cursor.NW_RESIZE, Cursor.W_RESIZE, Cursor.SW_RESIZE, Cursor.N_RESIZE,
            Cursor.MOVE, Cursor.S_RESIZE, Cursor.NE_RESIZE, Cursor.E_RESIZE, Cursor.SE_RESIZE };
    // ͼ����
    protected Group pane;

    /**
     * ���췽��
     * @param x ͼ�ε�x����
     * @param y ͼ�ε�y����
     * @param width ͼ�εĿ��
     * @param height ͼ�εĸ߶�
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
     * @param shape ͼ��
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
        shape.setFill(Color.WHITE);     // ��ʼ����ɫ
        shape.setStroke(Color.BLACK);   //���ñ߿���ɫ
        pane = new Group();             // ��ʼ����
        pane.setCursor(Cursor.CLOSED_HAND); // ��ʼ�������״
        pane.getChildren().add(shape);    // ��ͼ����ӵ�����
        addListener();                   // ��Ӽ���
    }

//todo: ���ӵ����ʽ������ã�

//    /**
//     * add connection info to the shape
//     * @param info ������Ϣ
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
     * ��Ӽ���
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
     * ��������϶�����
     * �϶�ʱ�༭��չʾ�ҽ�չʾ�߿�����
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
     * ͼ�θ��������קλ���ƶ�
     * @param x �����ק���x����
     * @param y �����ק���y����
     */
    public void Move(double x, double y) {
        double posX = x - this.getShape().getParent().getLayoutX(); // ��ǰͼ�������DrawingPane��x����
        double posY = y - this.getShape().getParent().getLayoutY(); // ��ǰͼ�������DrawingPane��y����
        booleanProperty.setValue(true);
        updateLocation(posX, posY); // ����ͼ����DrawingPane��λ��
        lineMove();                // ����ͼ�����ӵ������ƶ�
    }

    /**
     * ͼ��λ�ø���
     * @param x �ƶ����x����
     * @param y �ƶ����y����
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
     * Ĭ�ϵ�ͼ��-�������ӵ� Ĭ��Ϊ���������ĸ���
     * �����Զ���,ͨ��������д�÷������ı����ӵ�λ��
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
     * ��ͼ��λ�÷����ı��ʱ�򣬸���ͼ���е�������ʾλ��
     */
    public void update() {
        int len = text.getText().length() * 5;
        text.setX(x - len);
        text.setY(y);
    }

    /**
     * ����shape���ӵ�line��shapeλ�ø��º���Ҫ����shape�ƶ�
     */
    public void lineMove() {
        for (ConnectionInfo info : connectionInfos) {   // �������о������ӵ�line
            if (info.getConnectionPart().equals("end")) {   // ������ӵ��ǽ�����
                double centerX, centerY;
                centerX = drawPoints.getCircles()[info.getLocation()].getCenterX();
                centerY = drawPoints.getCircles()[info.getLocation()].getCenterY();
                info.getLine().endMove(centerX, centerY);
            } else if (info.getConnectionPart().equals("start")) {   // ������ӵ��ǿ�ʼ��
                double centerX, centerY;
                centerX = drawPoints.getCircles()[info.getLocation()].getCenterX();
                centerY = drawPoints.getCircles()[info.getLocation()].getCenterY();
                info.getLine().startMove(centerX, centerY);
            }
        }
    }

    /**
     * �������ſ�����
     * �����ſ�ʱ�����ñ༭���ĳ������
     */
    public void setOnRealse() {
        shape.setOnMouseReleased(e -> {
            this.setToTop();    // �ػ�ͼ�� ����ǰͼ���ö�
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
     * �ػ�ͼ��
     */
    public void setToTop() {
        drawingArea.getChildren().remove(shape);
        editer.delEditer(drawingArea);
        drawPoints.delPoint(drawingArea);
        drawingArea.getChildren().remove(text);
        this.getPane(drawingArea, drawController);
    }

    /**
     * ��drawingArea�л���MyShape pane�е����ݣ�ͬʱ����ͼ�εı༭��drawController
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

            // ���������״
            circles[i].setCursor(hand[i]);
        }
    }

    /**
     * ����ͼ��
     */
    private void resizeListener() {
        Circle[] circles = editer.getCircles();
        for (int i = 0; i < circles.length; i++) {
            circles[i].setOnMouseDragged(e -> {             // ��ÿ��editer��Point��������϶��¼�
                Point point = ((Point) (e.getSource()));    // ��ȡԭʼ��ק���������δ�ƶ�
                double radius = point.getRadius();          // ����ԭʼ��ק��İ뾶
                double ox = point.getCenterX();             // ����ԭʼ��ק������ĵ�x����
                double oy = point.getCenterY();             // ����ԭʼ��ק������ĵ�y����
                double dx = (e.getX() - ox);                // ����λ�Ƶ�x�����С
                double dy = (e.getY() - oy);                // ����λ�Ƶ�y�����С
                int posId = point.getPosid();               // ��ȡԭʼ��ק���id
                dx = dx * ((Point) (e.getSource())).getDirectionX();    // ����λ�Ƶ�x�����С����λΪ��ǰ�������Сλ��
                dy = dy * ((Point) (e.getSource())).getDirectionY();    // ����λ�Ƶ�y�����С����λΪ��Сλ��
                dx = dx / 5;                                            // ����λ�Ƶ�x�������
                dy = dy / 5;                                             // ����λ�Ƶ�y�������

                if (radius * radius <= ((dx * dx) + dy * dy)) {
                    resizeShape(posId, dx, dy);
                    editer.show(this.x, this.y);
                }
            });

            circles[i].setOnMouseReleased(e -> {           // ��ÿ��editer��Point��������ͷ��¼�
                this.booleanProperty.setValue(false);
            });
        }
    }

    protected void resizeShape(int posId, double dx, double dy) {
        if (width + dx >= 0 && height + dy >= 0) {
            // ??????��?��
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
            // ������Ϣ
            updateLocation(this.x, this.y);
            getEditer().setHeight(height + 10);
            getEditer().setWidth(width + 10);
            // ��������
            this.setX(x);
            this.setY(y);
            this.setWidth(width);
            this.setHeight(height);
            updateLocation(this.x, this.y);
            // ֪ͨ�ı�
            booleanProperty.setValue(true);
        }
    }

    /**
     * ����shape����������ʽ
     */
    private void moveHandListener() {
        this.getShape().setCursor(Cursor.MOVE);
    }


    /**
     * ����change������,��booleanProperty�����ı��ʱ����Ӧ
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
     * ��дequals�������ж�����ͼ���Ƿ���ͬ
     * @return true����ͬ��false����ͬ
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyShape) {
            return ((MyShape) obj).getFactoryID() == this.factoryID;
        }
        return false;
    }
}

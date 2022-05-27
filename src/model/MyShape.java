package model;

import java.text.DecimalFormat;
import java.util.ArrayList;

import controller.DrawController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class MyShape {
    // id��
    protected int factoryID;
    // ���ڵ�ͼ�κ����ڵĹ���
    protected AnchorPane drawingArea;
    protected DrawController drawController;
    // ��������
    protected double x;
    protected double y;
    // ���Ͻ�����
    protected double leftX;
    protected double leftY;
    // �������� �ұߵ�����
    protected double rightX;
    protected double rightY;
    // �������� ���Ϳ�
    protected double width;
    protected double height;

    // �ı�������������Լ���ͼ�εĸı�
    protected BooleanProperty booleanProperty;
    // ״̬��Ա
    private boolean isDrag = false;
    private boolean isZoom = false;
    private boolean isSelected = false;
    // ͼ�γ�Ա
    protected Shape shape;
    private Editer editer;
    protected Text text;
    protected DrawPoints drawPoints;
    // ���ӵ���Ϣ�б�
    ArrayList<ConnectionInfo> connectionInfos = new ArrayList<>();
    // ��С�ı仯ֵ
    private final static int[][] RESIZ_DRECTION = { { 0, -1, -1 }, { 1, -1, 0 }, { 2, -1, 1 }, { 3, 0, -1 },
            { 4, 0, 0 }, { 5, 0, 1 }, { 6, 1, -1 }, { 7, 1, 0 }, { 8, 1, 1 } };
    private final static Cursor[] hand = { Cursor.NW_RESIZE, Cursor.W_RESIZE, Cursor.SW_RESIZE, Cursor.N_RESIZE,
            Cursor.MOVE, Cursor.S_RESIZE, Cursor.NE_RESIZE, Cursor.E_RESIZE, Cursor.SE_RESIZE };
    private Group pane;

    // --getter and setter
    public Shape getShape() {
        return this.shape;
    }

    public void setISelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
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

    public void setMyShape(Shape shape) {
        this.shape = shape;
        this.editer = new Editer(this.x, this.x, height, width);
        this.booleanProperty = new SimpleBooleanProperty(false);
        this.text = new Text();
        this.text.setX(x);
        this.text.setY(y);
        this.drawPoints = new DrawPoints();
        createDrawPoints();
        shape.setFill(Color.WHITE);
        shape.setStroke(Color.BLACK);
        pane = new Group();
        pane.setCursor(Cursor.CLOSED_HAND);
        pane.getChildren().add(shape);
        addListener();

    }

    public Editer getEditer() {
        return editer;
    }

    public void setEditer(Editer editer) {
        this.editer = editer;
    }

    public int getFactoryID() {
        return factoryID;
    }

    @Override
    public boolean equals(Object obj) {
        return ((MyShape) obj).getFactoryID() == this.factoryID;
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

    // ���������Ϣ
    public void addConnectionInfo(ConnectionInfo info) {
        connectionInfos.add(info);
    }
    // ɾ��������Ϣ

    public void delConnectionInfo(MyLine line) {
        ConnectionInfo delInfo = null;
        for (ConnectionInfo info : connectionInfos) {
            if (info.getLine() == line) {
                System.out.println("delOK");
                delInfo = info;
            }
        }
        if (delInfo != null)
            connectionInfos.remove(delInfo);
    }

    // -----------constructor------------------------
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

    // ɾ��ͼ��
    public void delet() {
        drawingArea.getChildren().remove(shape);
        editer.delEditer(drawingArea);
        drawPoints.delPoint(drawingArea);
        drawingArea.getChildren().remove(text);
        // drawController.saveChange();
    }

    public void getPane(AnchorPane drawingArea, DrawController drawController) {
        editer.addEditer(drawingArea);
        drawingArea.getChildren().add(shape);
        drawingArea.getChildren().addAll(drawPoints.getCircles());
        drawPoints.setAllVisiable(false);

        drawingArea.getChildren().add(text);
        this.drawController = drawController;
        this.drawingArea = drawingArea;
    }

    public void setToTop() {
        drawingArea.getChildren().remove(shape);
        editer.delEditer(drawingArea);
        drawPoints.delPoint(drawingArea);
        drawingArea.getChildren().remove(text);
        getPane(drawingArea, drawController);
    }

    public void createDrawPoints() {
        double leftMidX = this.x - width;
        double leftMidY = this.y;
        double upMidX = this.x;
        double upMidY = this.y - height;
        double rightMidX = this.x + width;
        double rightMidY = this.y;
        double downMidX = this.x;
        double downMidY = this.y + height;
        drawPoints.updataLocation(leftMidX, leftMidY, upMidX, upMidY, rightMidX, rightMidY, downMidX, downMidY);
    }

    /*
     * ---listener --editer listener --cursor listener --resize listener --move
     * listener
     *
     */
    public void addListener() {
        setOnDrag();
        setOnRealse();
        resizeCursorListener();
        resizeListener();
        moveHandListener();
        changeListener();
    }

    public void changeListener() {
        booleanProperty.addListener(e -> {
            if (booleanProperty.getValue() == false) {
                // �������ı��ʱ����Ӧ
                drawController.getPropertyController().setWorkShape(this);
                drawController.getPropertyController().update();
                drawController.saveChange();
            }
        });
    }

    public void setOnDrag() {
        shape.setOnMouseDragged(e -> {
            Move(e.getX(), e.getY());
            editer.show(x, y);
            editer.disapperCircle();
            isSelected = false;
        });
    }

    public void setOnRealse() {
        shape.setOnMouseReleased(e -> {
            this.setToTop();
            this.booleanProperty.setValue(false);
            if (isSelected == false) {
                drawController.clearAllOnEdit();
                isSelected = true;
                editer.show(x, y);
            } else {
                isSelected = false;
                editer.disapper();
            }
        });
    }

    public void updateLocation(double x, double y) {
        this.x = x;
        this.y = y;
        this.rightX = x + width;
        this.rightY = y + height;
        this.leftX = x - width;
        this.leftY = y - height;
        createDrawPoints();
        getEditer().setHeight(height + 10);
        getEditer().setWidth(width + 10);
        if (isSelected)
            editer.show(x, y);
        update();

    }

    public void lineMove() {
        for (ConnectionInfo info : connectionInfos) {
            if (info.getConnectionPart().equals("end")) {
                double centerX, centerY;
                centerX = drawPoints.getCircles()[info.getLocation()].getCenterX();
                centerY = drawPoints.getCircles()[info.getLocation()].getCenterY();
                info.getLine().endMove(centerX, centerY);
            } else if (info.getConnectionPart().equals("start")) {
                double centerX, centerY;
                centerX = drawPoints.getCircles()[info.getLocation()].getCenterX();
                centerY = drawPoints.getCircles()[info.getLocation()].getCenterY();
                info.getLine().startMove(centerX, centerY);
            }
        }
    }

    public void Move(double x, double y) {
        double posX = x - this.getShape().getParent().getLayoutX();
        double posY = y - this.getShape().getParent().getLayoutY();
        booleanProperty.setValue(true);
        updateLocation(posX, posY);
        lineMove();
    }

    /*
     * Cursor listener for shape cursor will be changed to moveHand for mouse on
     * editer(which is nine small circle) will be changed to RESIZEhand
     *
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

    private void moveHandListener() {
        getShape().setCursor(Cursor.MOVE);
    }

    // Resize listener
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
     * ����ͼ��
     */
    private void resizeListener() {
        Circle[] circles = editer.getCircles();
        for (int i = 0; i < circles.length; i++) {
            circles[i].setOnMouseDragged(e -> {
                Point point = ((Point) (e.getSource()));
                double radius = point.getRadius();
                double ox = point.getCenterX();
                double oy = point.getCenterY();
                double dx = (e.getX() - ox);
                double dy = (e.getY() - oy);
                int posId = point.getPosid();
                dx = dx * ((Point) (e.getSource())).getDirectionX();
                dy = dy * ((Point) (e.getSource())).getDirectionY();
                dx = dx / 5;
                dy = dy / 5;

                int leftX = point.getLeftX();
                int leftY = point.getLeftY();

                if (radius * radius <= ((dx * dx) + dy * dy)) {
                    resizeShape(posId, dx, dy);
                    editer.show(this.x, this.y);
                }
            });

            circles[i].setOnMouseReleased(e -> {
                this.booleanProperty.setValue(false);
                ;
            });
        }
    }

    /**
     * �����ַ����ı��ʱ�򣬸���ͼ���е�����
     */
    public void update() {
        int len = text.getText().length() * 5;
        text.setX(x - len);
        text.setY(y);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.000");
        String tostring = getClass().getSimpleName() + "< " + factoryID + " >" + "(" + df.format(this.x) + ","
                + df.format(this.y) + "," + df.format(this.width) + ","
                + df.format(this.height) + ")" + "[ " + text.getText() + " ]" + "{" + getCSS() + "}" + "  ;\n";
        return tostring;
    }

    public String toString(int factoryID) {
        DecimalFormat df = new DecimalFormat("#.000");
        String tostring = getClass().getSimpleName() + "< " + factoryID + " >" + "(" + df.format(this.x) + ","
                + df.format(this.y) + "," + df.format(this.width) + ","
                + df.format(this.height) + ")" + "[ " + text.getText() + " ]" + "{ " + " }" + "  ;\n";
        return tostring;
    }

    /**
     * ��ȡͼ�ε�css
     *
     * @return
     */
    public String getCSS() {
        String css = "";
        for (int i = 0; i < connectionInfos.size(); i++) {
            css = css + " cnt:" + connectionInfos.get(i).toString();
        }
        return css;
    }

    /**
     * ͨ���ַ�������ͼ�ε�css
     *
     * @param css
     */
    public void setCSS(String css) {

        if (css == null)
            return;
        css = css.trim();
        String[] items = css.split(" ");
        for (int i = 0; i < items.length; i++) {
            String[] temp = items[i].split(":");
            String kind = temp[0].trim();
            System.out.println(kind);
            String info = temp[1].trim();
            switch (kind) {
                case "cnt":
                    addConnectionInfo(new ConnectionInfo(info, drawController));
                    break;

                default:
                    break;
            }
        }
    }

    public void setFactoryID(int factoryID) {
        this.factoryID = factoryID;
    }
}

package model;

import javafx.scene.shape.Rectangle;

public class StatementNode extends MyRectangle {
    private Rectangle rectangle;
    int preID;
    int nxtID;

    public StatementNode(double x, double y, int id) {
        this(x, y, 100, 50, id);
        this.factoryID = id;
    }

    public StatementNode(double x, double y, double width, double height, int id) {
        super(x, y, width, height, id);
        rectangle = new Rectangle();
        setMyShape(rectangle);
        setShape();
    }

    public void setShape() {
        rectangle.setWidth(2 * width);
        rectangle.setHeight(2 * height);
        rectangle.setX(x - width);
        rectangle.setY(y - height);
        rectangle.setStyle("-fx-arc-height: 50;-fx-arc-width: 50;");
    }

    public int getPreID() {
        return preID;
    }

    public void setPreID(int preID) {
        this.preID = preID;
    }

    public int getNxtID() {
        return nxtID;
    }

    public void setNxtID(int nxtID) {
        this.nxtID = nxtID;
    }

    @Override
    public void createDrawPoints() {
        double leftMidX = 0;
        double leftMidY = 0;
        double upMidX = this.x;
        double upMidY = this.y - height;
        double rightMidX = this.x + width;
        double rightMidY = 0;
        double downMidX = this.x;
        double downMidY = this.y + width/2;
        drawPoints.updataLocation(leftMidX, leftMidY, upMidX, upMidY, rightMidX, rightMidY, downMidX, downMidY);
    }
}

package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class EndNode extends MyShape {
    private Ellipse circle;
    int preID;
    public EndNode(double x, double y, int id) {
        this(x, y, 50.0);
        this.factoryID = id;
    }

    public EndNode(double x, double y, double radius) {
        super(x, y, radius, radius);
        this.x = x;
        this.y = y;
        leftX = x - radius;
        leftY = y - radius;
        this.circle = new Ellipse(this.x, this.y, radius, radius);
        this.circle.setFill(Color.WHITE);
        this.circle.setStroke(Color.BLACK);
        super.setMyShape(this.circle);
    }

    public int getPreID() {
        return preID;
    }

    public void setPreID(int preID) {
        this.preID = preID;
    }

    public void setX(double x) {
        this.x = x;
        setShape();
    }

    public void setY(double y) {
        this.y = y;
        setShape();
    }

    public void setWidth(double width) {
        this.width = width;
        setShape();
    }

    public void setHeight(double height) {
        this.height = height;
        setShape();
    }

    public void Move(double x, double y) {
        super.Move(x, y);
        setShape();
    }

    public void setShape() {
        circle.setRadiusX(width);
        circle.setRadiusY(width);
        circle.setCenterX(this.x);
        circle.setCenterY(this.y);
    }

    @Override
    public void createDrawPoints() {
        drawPoints.updataLocation(this.x, this.y, 0, 0, 0, 0, 0,0 );
    }
}

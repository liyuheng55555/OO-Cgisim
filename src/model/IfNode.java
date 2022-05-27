package model;

import javafx.scene.shape.Polygon;

public class IfNode extends MyPolygon {
    int preID;
    int trueID;
    int falseID;

    public IfNode(double x, double y, int id) {
        super(x, y, 100,50);
        this.factoryID=id;
        polygon = new Polygon();
        setMyShape(polygon);
        setShape();
    }

    /**
     * ÅÐ¶ÏÓï¾ä£¬¸üÐÂ×ø±ê
     */
    @Override
    public void setShape() {
        double upLeftX = this.x;
        double upLeftY = this.y - height;
        double upRightX = this.x + width;
        double upRightY = this.y;
        double downLeftX = this.x - width;
        double downLeftY = this.y;
        double downRightX = this.x;
        double downRightY = this.y + height;
        Double[] list = { upLeftX, upLeftY, upRightX, upRightY, downRightX, downRightY, downLeftX, downLeftY };
        polygon.getPoints().setAll(list);
    }

    public int getPreID() {
        return preID;
    }

    public void setPreID(int preID) {
        this.preID = preID;
    }

    public int getTrueID() {
        return trueID;
    }

    public void setTrueID(int trueID) {
        this.trueID = trueID;
    }

    public int getFalseID() {
        return falseID;
    }

    public void setFalseID(int falseID) {
        this.falseID = falseID;
    }

    @Override
    public void createDrawPoints() {
        double leftMidX = this.x - width;
        double leftMidY = this.y;
        double upMidX = this.x;
        double upMidY = this.y - height;
        double rightMidX = this.x + width;
        double rightMidY = this.y;
        double downMidX = 0;
        double downMidY = 0;
        drawPoints.updataLocation(leftMidX, leftMidY, upMidX, upMidY, rightMidX, rightMidY, downMidX, downMidY);
    }
}

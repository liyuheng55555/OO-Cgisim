package controller;

import javafx.scene.image.ImageView;
import model.MyNode;

public class ShapeFactory {
    private int countShapeID = 0;
    private DrawController drawController;

    public ShapeFactory(DrawController drawController) {
        this.drawController = drawController;
    }

//    public ImageView produce(String selectShape, double x, double y) {
//
//    }

    public void createShape(String shapeType, int x, int y) {
//        MyNode node = null;
//        if (shapeType.equals("Rectangle")) {
//            node = new Rectangle(countShapeID++, x, y, drawController);
//        } else if (shapeType.equals("Circle")) {
//            shape = new Circle(countShapeID++, x, y, drawController);
//        } else if (shapeType.equals("Line")) {
//            shape = new Line(countShapeID++, x, y, drawController);
//        }
//        return shape;
    }
}

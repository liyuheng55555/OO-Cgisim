package controller;

import javafx.scene.control.Alert;
import model.BrokenLine;
import model.StartNode;
import model.IfNode;
import model.DoubleBrokenLine;
import model.InputRectangle;
import model.EndNode;
import model.MyLine;
import model.MyRectangle;
import model.MyShape;
import model.StatementNode;

public class ShapeFactory {
    private int countShapeID = 0;
    private DrawController drawController;

    public ShapeFactory(DrawController drawController) {
        this.drawController = drawController;
    }

    public MyRectangle newMyRectangle(double x, double y) {
        countShapeID++;
        return new MyRectangle(x, y, countShapeID);
    }

    public StatementNode newRoundRectangle(double x, double y) {
        countShapeID++;
        return new StatementNode(x, y, countShapeID);
    }

    public IfNode newDecision(double x, double y) {
        countShapeID++;
        return new IfNode(x, y, countShapeID);
    }

    public InputRectangle newInputRectangle(double x, double y) {
        countShapeID++;
        return new InputRectangle(x, y, countShapeID);
    }

    public EndNode newMyCircle(double x, double y) {
        countShapeID++;
        return new EndNode(x, y, countShapeID);
    }

    public StartNode newStartNode(double x, double y) {
        if(drawController.getStartID() == -1){
            countShapeID++;
            drawController.setStartID(countShapeID);
            return new StartNode(x, y, countShapeID);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("you can only use one start node");
            alert.show();
            return null;
        }
    }

    public MyLine newMyLine(double sx, double sy, double ex, double ey) {
        countShapeID++;
        return new MyLine(sx, sy, ex, ey, countShapeID);
    }

    public MyLine newBrokenLine(double sx, double sy, double ex, double ey) {
        countShapeID++;
        return new BrokenLine(sx, sy, ex, ey, countShapeID);
    }

    public MyLine newDoubleBrokenLine(double sx, double sy, double ex, double ey, double ax) {
        countShapeID++;
        return new DoubleBrokenLine(sx, sy, ex, ey, ax, countShapeID);
    }

    public DrawController getDrawController() {
        return drawController;
    }

    public void setDrawController(DrawController drawController) {
        this.drawController = drawController;
    }

    public void produce(String kind, double x, double y, double width, double height, String text, int id) {
        if (kind == null)
            return;
        kind = kind.replaceAll("Image", "");
        if (kind.indexOf("Line") != -1) {
            MyLine line = produceMyLine(kind, x, y, width, height);
            line.getText().setText(text);
            line.setFactoryID(id);
            line.setShape();
        } else {
            MyShape shape = productMyShape(kind, x, y, width, height, text);
            if(shape != null){
                shape.getText().setText(text);
                shape.setFactoryID(id);
                shape.update();
            }
        }
    }

    public void produce(String kind, double x, double y, double ex, double ey, double aX, String text, int id) {
        if (kind == null)
            return;
        MyLine shape = produceMyLine(kind, x, y, ex, ey, aX);
        drawController.regristeLine(shape);
    }

    public void produce(String kind, double x, double y, double width, double height, String text) {
        if (kind == null)
            return;
        kind = kind.replaceAll("Image", "");
        if (kind.indexOf("Line") != -1) {
            MyLine line = produceMyLine(kind, x, y, width, height);
        } else {
            MyShape shape = productMyShape(kind, x, y, width, height, text);
            if(shape != null){
                shape.getText().setText(text);
                shape.update();
            }
        }
    }

    public int getCountShapeID() {
        return countShapeID;
    }

    public void setCountShapeID(int countShapeID) {
        this.countShapeID = countShapeID;
    }

    public void produce(String kind, double x, double y) {
        kind = kind.replaceAll("Image", "");
        if (kind.indexOf("Line") != -1) {
            produceMyLine(kind, x, y);
        } else {
            productMyShape(kind, x, y);
        }
    }

    public MyShape productMyShape(String kind) {
        MyShape shape = productMyShape(kind, 300, 300);
        return shape;
    }

    public MyShape productMyShape(String kind, double x, double y, double width, double height, String text) {
        MyShape shape = null;
        switch (kind) {
            case "CurvedRectangle":
                shape = newStartNode(x, y);
                break;
            case "Decision":
                shape = newDecision(x, y);
                break;
            case "InputRectangle":
                shape = newInputRectangle(x, y);
                break;
            case "MyCircle":
                shape = newMyCircle(x, y);
                break;
            case "RoundRectangle":
                shape = newRoundRectangle(x, y);
                break;
            case "MyRectangle":
                shape = newMyRectangle(x, y);
                break;
            default:
                break;
        }
        if(shape!=null) {
            shape.setWidth(width);
            shape.setHeight(height);
            shape.getText().setText(text);
            drawController.regriste(shape);
        }
        return shape;
    }

    public MyShape productMyShape(String kind, double x, double y) {
        MyShape shape = null;
        switch (kind) {
            case "CurvedRectangle":
                shape = newStartNode(x, y);
                break;
            case "Decision":
                shape = newDecision(x, y);
                break;
            case "InputRectangle":
                shape = newInputRectangle(x, y);
                break;
            case "Circular":
                shape = newMyCircle(x, y);
                break;
            case "RoundRectangle":
                shape = newRoundRectangle(x, y);
                break;
            case "Rectangle":
                shape = newMyRectangle(x, y);
                break;
            default:
                break;
        }
        if(shape != null) {
            System.out.print(shape);
            drawController.regriste(shape);
        }
        return shape;
    }

    public MyLine produceMyLine(String kind, double x, double y) {
        MyLine shape = null;
        switch (kind) {
            case "MyLine":
                shape = newMyLine(x, y, x, y + 100);
                break;
            case "BrokenLine":
                shape = newBrokenLine(x, y, x + 100, y + 100);
                break;
            case "DoubleBrokenLine":
                shape = newDoubleBrokenLine(x, y, x, y + 100, x - 100);
                break;
            default:
                break;
        }
        drawController.regristeLine(shape);
        return shape;
    }

    public MyLine produceMyLine(String kind, double x, double y, double ex, double ey, double aX) {
        MyLine shape = null;
        shape = newDoubleBrokenLine(x, y, ex, ey, aX);
        return shape;
    }

    public MyLine produceMyLine(String kind, double x, double y, double ex, double ey) {
        MyLine shape = null;
        System.out.println(kind);
        switch (kind) {
            case "MyLine":
                shape = newMyLine(x, y, ex, ey);
                break;
            case "BrokenLine":
                shape = newBrokenLine(x, y, ex, ey);
                break;
            default:
                break;
        }
        drawController.regristeLine(shape);
        return shape;
    }
}

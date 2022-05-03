import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Editor {
    private Editor(){}
    static private GraphicsContext graphicsContext;
    static private double lastX,lastY;   // 用于拖动效果
    static private Node lastSelect;    // 最后一次点选的结果，用于拖动效果
    static private Node lastBorderSelect;   // 最后一次边缘点选的结果，用于连线
    static private void canvasClear(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.rgb(255,255,255));
        graphicsContext.fillRect(0,0,10000,10000);
    }
    static public void reDraw(GraphicsContext graphicsContext) {
        canvasClear(graphicsContext);
        Node.drawAll(graphicsContext);
    }
    static public void canvasSetting(Canvas canvas) {
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        graphicsContext = canvas.getGraphicsContext2D();
        // 鼠标按下事件
        canvas.setOnMousePressed(event -> {
            double x = event.getX();
            double y = event.getY();
            lastX = x;
            lastY = y;
            Coordinate cord = new Coordinate(x,y);
            Node node = Node.find(cord);
            if (node!=null)
                lastSelect = node;
            else
                lastSelect = null;
//            reDraw(canvas.getGraphicsContext2D());
        });
        // 鼠标拖拽事件
        canvas.setOnMouseDragged(event -> {
            double x = event.getX();
            double y = event.getY();
//            Circle circle = Circle.get(x,y);
            if (lastSelect!=null) {
                lastSelect.x += x-lastX;
                lastSelect.y += y-lastY;
                lastX = x;
                lastY = y;
            }
//            else if (lastBorderSelect!=null) {
//                Arrow.addTmp(lastBorderSelect, x, y);
//                Circle circle = Circle.borderGet(x,y);
//                if (circle!=null)
//                    circle.borderTouch2();
//                else
//                    Circle.clearBorderTouch2();
//            }

            reDraw(canvas.getGraphicsContext2D());
        });
//        // 鼠标释放事件
//        canvas.setOnMouseReleased(event -> {
//            double x = event.getX();
//            double y = event.getY();
//            if (lastBorderSelect!=null) {
//                Circle circle = Circle.borderGet(x,y);
//                if (circle!=null) {
//                    Arrow.add(new Arrow(lastBorderSelect,circle));
//                    Arrow.removeTmpArrow();
//                }
//                else
//                    Arrow.removeTmpArrow();
//                Circle.clearBorderTouch();
//                lastBorderSelect = null;
//            }
//            reDraw();
//        });
        // 鼠标移动事件，移动时只会触发touch系列效果
        canvas.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            Coordinate cord = new Coordinate(x,y);
            Node node = Node.find(cord);
            System.out.println(event.getButton().name());
            if (event.getButton().name().equals("PRIMARY")) {  // 左键点击，选中或创建
                if (node!=null) {      // 圆内部点击
                    node.select();
                    lastSelect = node;
                }
            }
            else if (event.getButton().name().equals("SECONDARY")) {   // 右键点击，删除
                if (node!=null)
                    Node.remove(node);
            }
            reDraw(canvas.getGraphicsContext2D());
        });
        canvas.setOnMouseMoved(event -> {
            double x = event.getX();
            double y = event.getY();
            Coordinate cord = new Coordinate(x,y);
//            Circle circle = Circle.get(x,y);
            Node node = Node.find(cord);
            if (node!=null) {
                node.touch();
                Circle.clearBorderTouching();
                System.out.println("touch");
            }
            else {
                Node.clearTouching();
//                circle = Circle.borderGet(x,y);
                Circle circle = Circle.borderGet(cord);
                if (circle!=null) {
                    circle.borderTouch();
                    System.out.println("border");
                }
                else
                    Circle.clearBorderTouching();
            }


            reDraw(canvas.getGraphicsContext2D());
        });
    }
    static public void button0Response(ActionEvent event) {
        Node.add(new Circle(100,200,30));
        Editor.reDraw(graphicsContext);
    }
}

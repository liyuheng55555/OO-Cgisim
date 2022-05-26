


//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//
//class Coordinate {
//    double x,y;
//    public Coordinate(double x, double y) {
//        this.x = x;
//        this.y = y;
//    }
//}
//
//abstract class Node {
//    // ----------------绘图和运行共用的属性和方法----------------- //
//    private static int id_cnt = 0;
//    int id;
//    Node() {
//        id = id_cnt++;
//    }
//    Node next;
//    Node prev;
//    public void setNext(Node next) {
//        this.next = next;
//    }
//    public void setPrev(Node prev) {
//        this.prev = prev;
//    }
//
//    private static LinkedList<Node> nodeList = new LinkedList<>();
//    static public void add(Node node) {
//        nodeList.add(node);
//    }
//    static public void remove(Node node) {
//        nodeList.remove(node);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof Node))
//            return false;
//        return ((Node) obj).id == this.id;
//    }
//
//    // ----------------绘图使用的属性和方法----------------- //
//    // -------静态属性和方法------- //
//    static Node selecting = null;
//    static Node touching = null;
//    static public void clearTouching() {
//        if (touching!=null)
//            touching.touched = false;
//        touching = null;
//    }
//    // -------成员属性和方法------- //
//    double x,y;
//    boolean selected = false;
//    boolean touched = false;
//    public void select() {
//        if (selected==true) {  // 取消选择
//            selected = false;
//            selecting = null;
//            return;
//        }
//        // 选择
//        if (selecting!=null)
//            selecting.selected = false;
//        selecting = null;
//        selected = !selected;
//        if (selected)
//            selecting = this;
//    }
//    public void touch() {
//        touched = true;
//        touching = this;
//    }
//    abstract public void draw(GraphicsContext graphicsContext);
//    abstract public boolean contains(Coordinate cord);
//
//    static Node find(Coordinate cord) {
//        for (Node node: nodeList) {
//            if (node.contains(cord)) {
////                nodeList.remove()
//                return node;
//            }
//        }
//        return null;
//    }
//    static void drawAll(GraphicsContext graphicsContext) {
//        for (Node node: nodeList)
//            node.draw(graphicsContext);
//    }
//
//    // ----------------运行使用的属性和方法----------------- //
//    String expression;
//    static Node start = null;
//    static Node now = null;
//}
//
//class Circle extends Node {
//    // ----------------绘图新增或重写的属性和方法----------------- //
//    // ---------新增属性--------- //
//    double r;
//    boolean borderTouched;  // 是否为鼠标悬浮于边缘的状态
//    static Circle borderTouching;
//    public Circle(double x, double y, double r) {
//        this.x = x;
//        this.y = y;
//        this.r = r;
//        selected = false;
//    }
//    static ArrayList<Circle> circles = new ArrayList<>();
//    // ---------重写方法--------- //
//    @Override
//    public boolean contains(Coordinate cord) {
//        return Math.sqrt(Math.pow(this.x-cord.x,2) + Math.pow(this.y-cord.y,2)) < r-5;
//    }
//    @Override
//    public void draw(GraphicsContext graphicsContext) {
//        if (borderTouched)
//            graphicsContext.setLineWidth(5);
//        else
//            graphicsContext.setLineWidth(2);
//        graphicsContext.strokeOval(x-r,y-r,r*2,r*2);
//        if (selected) {
//            graphicsContext.setFill(Color.rgb(0,0,255,0.8));
//            graphicsContext.fillOval(x-r,y-r,r*2,r*2);
//        }
//        else if (touched) {
////            System.out.println("ok");
//            graphicsContext.setFill(Color.rgb(0,0,255,0.4));
//            graphicsContext.fillOval(x-r,y-r,r*2,r*2);
//        }
//    }
//    // ---------新增方法--------- //
//    public boolean edges(Coordinate cord) {
//        double r1 = Math.sqrt(Math.pow(this.x-cord.x,2) + Math.pow(this.y-cord.y,2));
//        return Math.abs(r1-r)<5;
//    }
//    // 鼠标悬浮于边缘
//    public void borderTouch() {
//        this.borderTouched = true;
//        borderTouching = this;
//    }
//    // 鼠标不再悬浮于边缘
//    static public void clearBorderTouching() {
//        if (borderTouching!=null)
//            borderTouching.borderTouched = false;
//        borderTouching = null;
//    }
//    //
//    static Circle borderGet(Coordinate cord) {
//        for (Circle circle: circles) {
//            if (circle.edges(cord))
//                return circle;
//        }
//        return null;
//    }
//
//    // ----------------运行新增或重写的属性和方法----------------- //
//
//
//}








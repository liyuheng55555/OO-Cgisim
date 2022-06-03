package controller;

import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.MyNode;

enum Status {
    normal, dragging, connecting, selected
}


public class RootLayoutController implements Initializable {

    @FXML
    private AnchorPane drawingArea;
    @FXML
    private AnchorPane keyBoardPane;
    @FXML
    private VBox Nodes;
    @FXML
    private ImageView choose_start;
    @FXML
    private ImageView choose_end;
    @FXML
    private ImageView choose_statement;
    @FXML
    private ImageView choose_if;
    @FXML
    private ImageView choose_loop;
    @FXML
    private ImageView choose_print;
    @FXML
    private TextField messageBox;
    //    @FXML
    private ImageView shadow;
    private ImageView imageView;

    static final int viewH = 100;
    static final int viewW = 150;

    static final int connectorSize = 5;

    Status status = Status.normal;


    /*
    inConnector记录每种节点的输入点，outConnector记录输出点
    1：上方    2：下方    3：左侧    4：右侧
     */
    static Map<String, List<Integer>> inConnector = new HashMap<>();
    static Map<String, List<Integer>> outConnector = new HashMap<>();
    static {
        inConnector.put("start", Collections.emptyList());
        inConnector.put("end", Collections.singletonList(1));
        inConnector.put("if", Collections.singletonList(1));
        inConnector.put("loop", Collections.singletonList(1));
        inConnector.put("statement", Collections.singletonList(1));
        inConnector.put("print", Collections.singletonList(1));
        inConnector.put("merge", Arrays.asList(1,4));

        outConnector.put("start", Collections.singletonList(2));
        outConnector.put("end", Collections.emptyList());
        outConnector.put("if", Arrays.asList(2,4));
        outConnector.put("loop", Collections.singletonList(2));
        outConnector.put("statement", Collections.singletonList(2));
        outConnector.put("print", Collections.singletonList(2));
        outConnector.put("merge", Collections.singletonList(2));
    }
    // 各个点相对于基点的偏移量，[3][0]表示左侧点的纵坐标，[3][1]表示横坐标
    static double[][] connectorPos = new double[5][2];
    static {
        connectorPos[1][0] = (double) viewH / 10;
        connectorPos[1][1] = (double) viewW / 2;
        connectorPos[2][0] = (double) viewH / 10 * 9;
        connectorPos[2][1] = (double) viewW / 2;
        connectorPos[3][0] = (double) viewH / 2;
        connectorPos[3][1] = (double) viewW / 10;
        connectorPos[4][0] = (double) viewH / 2;
        connectorPos[4][1] = (double) viewW / 10 * 9;
    }

    int touchConnector(MyNode node, double x, double y) {
        String name = node.name;
        double baseX = node.imageView.getX();
        double baseY = node.imageView.getY();
        for (int i : inConnector.get(name)) {
            double ny = baseY + connectorPos[i][0];
            double nx = baseX + connectorPos[i][1];
            if (distance(x,y,nx,ny)<connectorSize)
                return i;
        }
        for (int i : outConnector.get(name)) {
            double ny = baseY + connectorPos[i][0];
            double nx = baseX + connectorPos[i][1];
            if (distance(x,y,nx,ny)<connectorSize)
                return i;
        }
        return -1;
    }

    /**
     * 尝试绘制连线，x、y均为viewTable中的索引
     * @param sx 起始x
     * @param sy 起始y
     * @param sd 起始输出点
     * @param ex 结束x
     * @param ey 结束y
     * @param ed 最终输入点
     */
    void drawLine(int sx, int sy, int sd, int ex, int ey, int ed) {
//        if
    }




    private DrawController drawController;
    private ShapeFactory shapeFactory;
    private PropertyController propertyController;
    private String selectShape = null;
    private MyNode selection = null;
    private double relativeX = -1;
    private double relativeY = -1;

    final int tableH = 20;
    final int tableW = 10;
    MyNode[][] viewTable = new MyNode[tableH][tableW];
    ArrayList<Point2D> connectorList = new ArrayList<>();

    /**
     * 向viewTable中加入一个ImageView
     * @param node 待加入的imageview
     */
    void putInTable(MyNode node) {
        ImageView image = node.imageView;
        int x = (int) image.getX();
        int y = (int) image.getY();
        int xi = x / viewW;
        int yi = y / viewH;
        if (viewTable[xi][yi]!=null) {
            drawingArea.getChildren().remove(viewTable[xi][yi].imageView);
        }
        viewTable[xi][yi] = node;
    }

    /**
     * 将viewTable转换为Dijkstra所需的费用矩阵（有向图）
     * 每个位置都是一个图上的节点，向上的边费用为无穷大，其它边费用均为1
     * @return  费用矩阵cost
     */
    int[][] tableToCost() {
        int[][] cost = new int[200][200];
        for (int i=0; i<tableH; i++) {
            for (int j=0; j<tableW; j++) {
                int s = i*tableW+j;
                for (int k=0; k<tableH; k++) {
                    for (int l=0; l<tableW; l++) {
                        int e = k*tableW+l;
                        if (i<=k  &&  (i==k && Math.abs(j-l)==1 || j==l && Math.abs(i-k)==1))
                            cost[s][e] = 1;
                        else
                            cost[s][e] = 999999;
                    }
                }
            }
        }
        return cost;
    }

//    // 将image的输入输出链接点记录到connectorList之中
//    void putInConnectorList(ImageView image) {
//        int x = (int) image.getX();
//        int y = (int) image.getY();
//        for (int c : )
//    }

    void removeFromTable(double x, double y) {
        int xi = (int) (x / viewW);
        int yi = (int) (y / viewH);
        viewTable[xi][yi] = null;
    }

//    ArrayList<Integer> tableFindFree() {
//        for (int h=0; h<tableH; h++) {
//            for (int w=0; w<tableW; w++) {
//                if (viewTable[h][w]==null) {
//                    ArrayList<Integer> arr = new ArrayList<>();
//                    arr.add(w,h);
//                    return arr;
//                }
//            }
//        }
//        return null;
//    }



    MyNode produceNode(String selectShape) {
        String shape = selectShape.split("_")[2];
        Image image = null;
        try {
            image = new Image("resources/img/draw_node_"+shape+".png");
        } catch (Exception e) {
            System.out.println("!!!!!!!! 打开文件失败：resources/img/draw_node_"+shape+".png !!!!!!!!!");
            return null;
        }
        MyNode my = new MyNode(drawingArea, drawController);
        my.imageView = new ImageView();
        my.imageView.setImage(image);
        my.imageView.setFitHeight(viewH);
        my.imageView.setFitWidth(viewW);
        my.name = shape;
        return my;
    }

    double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }



//    ImageView produceView(String selectShape) {
//        String shape = selectShape.split("_")[2];
//        ImageView view = new ImageView();
//        view.setFitHeight(viewH);
//        view.setFitWidth (viewW);
//        Image image = null;
//        try {
//            image = new Image("resources/img/draw_node_"+shape+".png");
//        } catch (Exception e) {
//            System.out.println("!!!!!!!! 打开文件失败：resources/img/draw_node_"+shape+".png !!!!!!!!!");
//        }
//        if (image!=null)
//            view.setImage(image);
//        return view;
//    }

    boolean isDragging = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        propertyController = new PropertyController(messageBox);
        drawController = new DrawController(drawingArea, propertyController);
        shapeFactory = new ShapeFactory(drawController);

        shadow = new ImageView();
        shadow.setFitHeight(viewH);
        shadow.setFitWidth (viewW);
        shadow.setImage(new Image("resources/img/shadow.png"));
        shadow.setX(-1000);
        shadow.setY(-1000);

        ImageView connector = new ImageView();
        connector.setFitWidth(2*connectorSize);
        connector.setFitHeight(2*connectorSize);
        connector.setImage(new Image("resources/img/co.png"));
        connector.setX(-1000);
        connector.setY(-1000);

        drawingArea.getChildren().addAll(shadow, connector);

        // 点击，创建图形
//        drawingArea.;

//        drawingArea.setOnMouseClicked(event -> {
        drawingArea.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (status == Status.normal) {

            }
            if (status == Status.dragging) {
                status = Status.normal;
                return;
            }
            keyBoardPane.requestFocus();
            if (event.getButton().name().equals("PRIMARY")) {
                if (event.getClickCount() == 1 && selectShape != null) {
                    double x, y;
                    x = event.getX();
                    y = event.getY();
                    MyNode node = produceNode(selectShape);
                    ImageView view = node.imageView;
                    int xx = (int) x;
                    int yy = (int) y;
                    yy -= yy%viewH;
                    xx -= xx%viewW;
                    view.setX(xx);
                    view.setY(yy);
                    putInTable(node);
                    drawingArea.getChildren().add(view);
                    System.out.println(drawingArea.getChildren().size());
                }
                if (event.getClickCount() == 1) {
                    drawController.getPropertyController().update();
                }
            }
            else if (event.getButton().name().equals("SECONDARY")) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                MyNode node = viewTable[x/viewW][y/viewH];
                if (node!=null) {
                    drawingArea.getChildren().remove(node.imageView);
                    viewTable[x/viewW][y/viewH] = null;
                }
            }
        });

        drawingArea.setOnMouseMoved(event -> {
            if (status == Status.normal) {
                boolean found = false;
                int x = (int) event.getX();
                int y = (int) event.getY();
                MyNode node = viewTable[x/viewW][y/viewH];
                if (node!=null) {
                    int c = touchConnector(node, event.getX(), event.getY());
                    System.out.println(c);
                    if (c!=-1) {
                        connector.setX(node.imageView.getX()+connectorPos[c][1]-connectorSize);
                        connector.setY(node.imageView.getY()+connectorPos[c][0]-connectorSize);
                        // 这样刷一下能把connector的显示拉到最上面
                        drawingArea.getChildren().remove(connector);
                        drawingArea.getChildren().add(connector);
                        found = true;
                    }
                }
                else
                    System.out.println("null");
                if (!found) {
                    connector.setX(-1000);
                    connector.setY(-1000);
                }
            }
            else {
                connector.setX(-1000);
                connector.setY(-1000);
            }
        });

        // ---------------------- 拖动响应，分为按下鼠标、拖动、松开鼠标，三个阶段 ---------------------------
        drawingArea.addEventFilter(MouseDragEvent.MOUSE_PRESSED, event -> {
//        drawingArea.setOnMouseDragEntered(event -> {
            selection = null;
            System.out.println("MOUSE_PRESSED");
            loop:
            for (int i=0; i<tableH; i++) {
                for (int j=0; j<tableW; j++) {
                    if (viewTable[i][j]!=null && viewTable[i][j].imageView.contains(event.getX(), event.getY())) {
                        selection = viewTable[i][j];
//                        selectShape = null;
                        relativeX = event.getX()-viewTable[i][j].imageView.getX();
                        relativeY = event.getY()-viewTable[i][j].imageView.getY();
                        removeFromTable(event.getX(), event.getY());
                        break loop;
                    }
                }
            }
        });



        drawingArea.addEventFilter(MouseDragEvent.MOUSE_DRAGGED, event -> {
//        drawingArea.setOnMouseDragged(event -> {
//            isDragging = true;
            status = Status.dragging;
            if (event.isPrimaryButtonDown() && selection!=null) {
                int xx = (int) event.getX();
                int yy = (int) event.getY();
                yy -= yy%viewH;
                xx -= xx%viewW;
//                System.out.println(x+" "+y);
                shadow.setX(xx);
                shadow.setY(yy);

                double x = event.getX();
                double y = event.getY();
                selection.imageView.setX(x-relativeX);
                selection.imageView.setY(y-relativeY);

//                drawingArea
            }
        });

        drawingArea.addEventFilter(MouseDragEvent.MOUSE_RELEASED, event -> {
//            status = Status.normal;
            if (selection!=null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                y -= y%viewH;
                x -= x%viewW;
                System.out.println(x+" "+y);
//                viewTable[x/viewW][y/viewH] = selection;
                selection.imageView.setX(x);
                selection.imageView.setY(y);
                putInTable(selection);
                selection = null;
                shadow.setX(-1000);
                shadow.setY(-1000);
            }

        });
        // -------------------------------------------- 拖动响应结束 ---------------------------------------------


        // 在左侧栏选择想要绘制的Node
        Nodes.setOnMouseClicked(event -> {
            if (event.getTarget().getClass() == ImageView.class) {
                ImageView nowImage = (ImageView) event.getTarget();
                selectShape = nowImage.getId();
                System.out.println("now: "+ selectShape);
                if(selectShape != null){    // 如果之前有选择过Node，则清除之前选择的Node
                    if (!nowImage.getId().contains("start")){
                        choose_start.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/choose_node_start.png"))));
                        choose_start.setId("choose_node_start");
                    }
                    if (!nowImage.getId().contains("end")){
                        choose_end.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/choose_node_end.png"))));
                        choose_end.setId("choose_node_end");
                    }
                    if (!nowImage.getId().contains("statement")){
                        choose_statement.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/choose_node_statement.png"))));
                        choose_statement.setId("choose_node_statement");
                    }
                    if (!nowImage.getId().contains("if")){
                        choose_if.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/choose_node_if.png"))));
                        choose_if.setId("choose_node_if");
                    }
                    if (!nowImage.getId().contains("loop")){
                        choose_loop.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/choose_node_loop.png"))));
                        choose_loop.setId("choose_node_loop");
                    }
                    if (!nowImage.getId().contains("print")){
                        choose_print.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/choose_node_print.png"))));
                        choose_print.setId("choose_node_print");
                    }
                }
                if (nowImage.getId().contains("choose")) {
                    nowImage.setId(nowImage.getId().replace("choose", "shadow"));
                }
                else if (nowImage.getId().contains("shadow")) {
                    nowImage.setId(nowImage.getId().replace("shadow", "choose"));
                    selectShape = null;
                }
                nowImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/" + nowImage.getId() + ".png"))));
                if(selectShape != null){
                    System.out.println("current status: " + selectShape.split("_")[2]);
                }else{
                    System.out.println("current status: null");
                }
            }
        });
    }

    public void menuNew(){
        System.out.println("New");
    }
    public void menuSave(){
        System.out.println("Save");
    }
    public void menuOpen(){
        System.out.println("Open");
    }
    public void menuExport(){
        System.out.println("Export");
    }
    public void run(){
        System.out.println("run");
    }
    public void stop(){
        System.out.println("stop");
    }
    public void debug(){
        System.out.println("debug");
    }
    public void stepRun(){
        System.out.println("stepRun");
    }
    public void reset(){
        System.out.println("reset");
    }

}


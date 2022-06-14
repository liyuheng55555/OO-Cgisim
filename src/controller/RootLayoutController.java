package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.*;

import static model.Constant.tableH;
import static model.Constant.tableW;
import static model.Constant.viewH;
import static model.Constant.viewW;
import static model.Constant.connectorSize;
import static model.TableVar.varList;

import model.Constant.ClickStatus;
import model.Constant.Status;

public class RootLayoutController implements Initializable {
    //创建数据源
    final ObservableList<TableVar> data = FXCollections.observableArrayList(
            new TableVar("a", "int", "1"),
            new TableVar("b", "float", "2.3"),
            new TableVar("c", "bool", "true")
    );
    @FXML
    private TextArea outText;
    @FXML
    private ImageView stopButton;
    @FXML
    private ImageView pauseButton;
    @FXML
    private ImageView stepButton;
    @FXML
    private ImageView runButton;
    @FXML
    private TableView<TableVar> tableView;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private TableColumn<TableVar, String> VarName;
    @FXML
    private TableColumn<TableVar, String> VarType;
    @FXML
    private TableColumn<TableVar, String> VarValue;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtValue;
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
    private ImageView choose_branch;
    @FXML
    public ImageView choose_merge;
    @FXML
    public ImageView choose_loop_start;
    @FXML
    public ImageView choose_loop_end;
    @FXML
    private ImageView choose_print;
    @FXML
    private TextField messageBox;
//    @FXML
//    private Button compileButton;
//    @FXML
//    private Button stepRunButton;

    Status status = Status.normal;
    ClickStatus clickStatus = ClickStatus.choosingStart;
    NodeFactory nodeFactory;

    ShowAnything showSelection;
    ShowAnything showRunPosition;

    /**
     * 设置所有ShowAnything对象；
     * 因为initialize函数已经太长了，所以单独写一个函数
     */
    void showAnythingInitialize() {
        // 选中位置
        Image selectPng = new Image("sources/img/select.png");
        ImageView selectView = new ImageView(selectPng);
        selectView.setFitHeight(viewH);
        selectView.setFitWidth(viewW);
        showSelection = new ShowAnything(selectView, drawingArea, 0, 0);
        // 运行位置
        Image runPosPng = new Image("sources/img/select.png");
        ImageView runView = new ImageView(runPosPng);
        runView.setFitHeight(viewH);
        runView.setFitWidth(viewW);
        showRunPosition = new ShowAnything(runView, drawingArea, 0, 0);
        // 结构错误、语法错误等

    }

    /*
    inConnector记录每种节点的输入点，outConnector记录输出点
    1：上方    2：下方    3：左侧    4：右侧
     */
    static Map<String, List<Integer>> inConnector = new HashMap<>();
    static Map<String, List<Integer>> outConnector = new HashMap<>();
    static {
        inConnector.put("start", Collections.emptyList());
        inConnector.put("end", Collections.singletonList(1));
        inConnector.put("branch", Collections.singletonList(1));
        inConnector.put("merge", Arrays.asList(1,4));
        inConnector.put("loop_st", Collections.singletonList(1));
        inConnector.put("loop_end", Collections.singletonList(1));
        inConnector.put("statement", Collections.singletonList(1));
        inConnector.put("print", Collections.singletonList(1));

        outConnector.put("start", Collections.singletonList(2));
        outConnector.put("end", Collections.emptyList());
        outConnector.put("branch", Arrays.asList(2,4));
        outConnector.put("merge", Collections.singletonList(2));
        outConnector.put("loop_st", Collections.singletonList(2));
        outConnector.put("statement", Collections.singletonList(2));
        outConnector.put("print", Collections.singletonList(2));
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

    public RootLayoutController() throws Exception {
    }

    /**
     * 寻找最近的可连接点
     * 序号 1：上方    2：下方    3：左侧    4：右侧
     * @param node 待处理node
     * @param x    鼠标横坐标
     * @param y    鼠标纵坐标
     * @return 可连接点的序号，-1表示没有可连接点
     *
     */
    int touchConnector(MyNode node, double x, double y,  Map<String, List<Integer>> connector) {
        String name = node.getImageView().getId();
        ImageView view = node.getImageView();
        double baseX = view.getX();
        double baseY = view.getY();
        if (connector.get(name)!=null) {
            for (int i : connector.get(name)) {
                double ny = baseY + connectorPos[i][0];
                double nx = baseX + connectorPos[i][1];
                if (distance(x, y, nx, ny) < 4 * connectorSize) {
                    return i;
                }
            }
        }
        return -1;
    }

    private PropertyController propertyController;
    private String selectNode = null;
    private MyNode selection = null;
    private double relativeX = -1;
    private double relativeY = -1;

    MyNode[][] nodeTable = new MyNode[tableH][tableW];
    private final HashMap<Integer, MyNode> nodeMap = new HashMap<>();
    int startConnectionID;
    int startConnectionPlace;
    int endConnectionID;
    int endConnectionPlace;

    public HashMap<Integer, MyNode> getNodeMap() {
        return nodeMap;
    }

    /**
     * 将viewTable转换为Dijkstra所需的费用矩阵（有向图）
     * 每个位置都是一个图上的节点，向上的边费用为无穷大，其它边费用均为1
     * 如果一个位置已经被占用，则和它相连的边费用都是无穷大
     * @return  费用矩阵cost
     */
    int[][] tableToCost() {
        int[][] cost = new int[200][200];
        int nb = 0;
        for (int i=0; i<tableH; i++) {
            for (int j = 0; j < tableW; j++) {
                int s = i * tableW + j;
                for (int k = 0; k < tableH; k++) {
                    for (int l = 0; l < tableW; l++) {
                        int e = k * tableW + l;
                        if (i <= k &&
                                (i == k && Math.abs(j - l) == 1 || j == l && Math.abs(i - k) == 1) &&
                                nodeTable[i][j] == null && nodeTable[k][l] == null
                        ) {
                            cost[s][e] = 1;
//                            System.out.printf("%d %d  %d %d\n",i,j,k,l);
                            nb++;
                        } else {
                            cost[s][e] = 999999;
                        }
                    }
                }
            }
        }
        return cost;
    }

    /**
     * 从ShapeMap中获取线连接的信息, 并使用showPath显示路径
     * 重绘所有路径
     */
    public void updateConnection(){
        int[][] connectInfo = new int[6][nodeMap.size()];
        int count = 0;
        for(MyNode node: nodeMap.values()){
            for (int c=1; c<=4; c++) {
                int id = node.connectTo[c];
                System.out.println(id);
                if (id!=-1 && checkInOrOut(node, c)==1) {  // 有连接，而且是输出点
                    connectInfo[0][count] = (int)(node.getImageView().getX()/viewW);
                    connectInfo[1][count] = (int)(node.getImageView().getY()/viewH);
                    connectInfo[2][count] = c;
                    MyNode toNode = nodeMap.get(id);
                    connectInfo[3][count] = (int)(toNode.getImageView().getX()/viewW);
                    connectInfo[4][count] = (int)(toNode.getImageView().getY()/viewH);
                    connectInfo[5][count] = node.connectPlace[c];
                    count++;
                }
            }
        }

        for(int i = 0; i < count; i++){
            System.out.println("connect" + connectInfo[1][i] + " " + connectInfo[2][i] + " " + connectInfo[4][i] + " " + connectInfo[5][i]);
            try{
                showPath(getPath(connectInfo[0][i], connectInfo[1][i], connectInfo[2][i], connectInfo[3][i], connectInfo[4][i], connectInfo[5][i]));
            }catch(Exception e){
                System.out.println("find path error");
                e.printStackTrace();
            }
        }
    }

    /**
     * 擦除所有与node相连的线，不影响前驱后继
     * @param node
     */
    public void eraseAllPath(MyNode node) {
        int x = (int) (node.getImageView().getX()/viewW);
        int y = (int) (node.getImageView().getY()/viewH);
        for (int c=1; c<=4; c++) {
            if (node.connectTo[c]!=-1) {
                switch (c) {
                    case 1: erasePath(x,y-1); break;
                    case 2: erasePath(x,y+1); break;
                    case 3: erasePath(x-1,y); break;
                    case 4: erasePath(x+1,y); break;
                }
            }
        }
    }

    /**
     * 尝试根据node中记录的前驱后继关系，在界面上绘制所有关于node的连线；
     * 如果一个连线无法绘制，就删除对应的前驱后继关系；
     * @param node  待处理的node
     */
    public void tryCreatePath(MyNode node) throws Exception {
        for (int c=1; c<=4; c++) {
            int id = node.connectTo[c];
            if (id!=-1) {
                int inOut = checkInOrOut(node, c);
                int sx = 0, sy = 0, sc = 0, ex = 0, ey = 0, ec = 0;
                MyNode toNode = nodeMap.get(id);
                if (inOut==1) { // 输出点
                    sx = (int)(node.getImageView().getX()/viewW);
                    sy = (int)(node.getImageView().getY()/viewH);
                    sc = c;
                    ex = (int)(toNode.getImageView().getX()/viewW);
                    ey = (int)(toNode.getImageView().getY()/viewH);
                    ec = node.connectPlace[c];
                }
                else if (inOut==0) { // 输入点
                    sx = (int)(toNode.getImageView().getX()/viewW);
                    sy = (int)(toNode.getImageView().getY()/viewH);
                    sc = node.connectPlace[c];
                    ex = (int)(node.getImageView().getX()/viewW);
                    ey = (int)(node.getImageView().getY()/viewH);
                    ec = c;
                }
                else
                    bad();  // 在不应该有连接存在的地方，却有连接存在
                ArrayList<ArrayList<Integer>> path = getPath(sx, sy, sc, ex, ey, ec);
                if (path==null) { // 绘制失败，清除前驱后继
                    int toC = node.connectPlace[c];
                    toNode.connectTo[toC] = -1;
                    toNode.connectPlace[toC] = -1;
                    node.connectTo[c] = -1;
                    node.connectPlace[c] = -1;
                    System.out.printf("连线(%d,%d,%d,%d,%d,%d)绘制失败，前驱后继关系已清除\n",sx, sy, sc, ex, ey, ec);
                }
                else {
                    showPath(path);

                }
            }
        }
    }

    /**
     * 检查对node而言方位c是输入点还是输出点
     * @param node  待检查node
     * @param c     方位
     * @return      1代表输出点，
     *              0代表输入点，
     *              -1代表既不是输出也不是输入
     */
    int checkInOrOut(MyNode node, int c) {
        String name = node.getImageView().getId();
        List<Integer> connectors = inConnector.get(name);
        if (connectors!=null && connectors.contains(c))
            return 0;
        connectors = outConnector.get(name);
        if (connectors!=null && connectors.contains(c))
            return 1;
        return -1;
    }

    void bad() throws Exception {
        throw new Exception("不应该出现这种情况");
    }

    /**
     * 获取路径；
     * 首先调用tableToCost，将viewTable转换成费用矩阵；
     * 然后调用Algorithm.dijkstra求出最短路径；
     * 然后通过前驱数组，生成路径；
     * 如果路径cost之和超过10000，就认为无法抵达，返回null；
     * start和end这两个位置都需要有node存在，否则可能出问题；
     * @param sx startX
     * @param sy startY
     * @param sc startConnector 取值1 2 3 4
     * @param ex endX
     * @param ey endY
     * @param ec endConnector
     * @return [[sx,sy], [x1,y1], ..., [ex,ey]]
     */
    ArrayList<ArrayList<Integer>> getPath(int sx, int sy, int sc, int ex, int ey, int ec) throws Exception {
        int[][] cost = tableToCost();
        // 下面四个是调整后的坐标
        int sx_=sx, sy_=sy, ex_=ex, ey_=ey;
        switch (sc) {
            case 1: bad();
            case 2: sy_++; break;
            case 3: bad();
            case 4: sx_++; break;
            default: bad();
        }
        switch (ec) {
            case 1: ey_--; break;
            case 2: bad();
            case 3: bad();
            case 4: ex_++; break;
            default: bad();
        }
        int s = sx_+sy_*tableW;
        int e = ex_+ey_*tableW;
        int[] path = Algorithm.dijkstra(cost, tableH*tableW, s, e);
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        int costSum = 0;
        for (int i=e; ; i=path[i]) {
            ArrayList<Integer> now = new ArrayList<>();
            now.add(i%tableW);
            now.add(i/tableW);
            result.add(0, now);
            if (i==s) {
                break;
            }
            costSum += cost[path[i]][i];
        }
        if (costSum>10000) {
            System.out.println(costSum);
            return null;
        }
        // 添加首末端点
        ArrayList<Integer> start = new ArrayList<>(), end = new ArrayList<>();
        start.add(sx);
        start.add(sy);
        end.add(ex);
        end.add(ey);
        result.add(0, start);
        result.add(end);
        return result;
    }


    /**
     * 获得路径之后，调用此函数在界面上绘制连线
     * 起点终点的位置不画线
     * @param path
     */
    void showPath(ArrayList<ArrayList<Integer>> path) {
        if (path==null) {
            System.out.println("找不到路径");
            return;
        }
        for (int i=1; i<path.size()-1; i++) {
            int preX=path.get(i-1).get(0);
            int preY=path.get(i-1).get(1);
            int x = path.get(i).get(0);
            int y = path.get(i).get(1);
            int nextX = path.get(i+1).get(0);
            int nextY = path.get(i+1).get(1);
            if (nextY==preY+2) { // 竖线
                nodeTable[y][x] = new VerticalLine(x*viewW, y*viewH);
            }
            else if (nextY==preY+1) { // 弯折线
                if (nextX==preX+1) {
                    if (x==preX) { // ⤵
                        nodeTable[y][x] = new DownRightLine(x*viewW, y*viewH);
                    }
                    else { // ⤷
                        nodeTable[y][x] = new RightDownLine(x*viewW, y*viewH);
                    }
                }
                else {
                    if (x==preX) {// ⤶
                        nodeTable[y][x] = new DownLeftLine(x*viewW, y*viewH);
                    }
                    else { // 左转下，没找到这个符号
                        nodeTable[y][x] = new LeftDownLine(x*viewW, y*viewH);
                    }
                }
            }
            else if (nextY==preY) { // 横线
                nodeTable[y][x] = new HorizonLine(x*viewW, y*viewH);
            }
            nodeTable[y][x].draw(drawingArea);
        }
    }

    /**
     * 递归地擦除所有与(sx,sy)相连的线
     * @param sx
     * @param sy
     */
    void erasePath(int sx, int sy) {
        if (sx<0 || sy<0 || sx>=tableW || sy>=tableH) {
            return;
        }
        MyNode node = nodeTable[sy][sx];
        if (node==null) {
            return;
        }
        String[] ss = node.getImageView().getId().split("_", 2);
        if (!ss[0].equals("line")) {
            return;
        }
        nodeTable[sy][sx].remove(drawingArea);
        nodeTable[sy][sx] = null;
        if (ss[1].equals("vertical") || ss[1].equals("down_left") || ss[1].equals("down_right")) {
            erasePath(sx, sy-1);  // 向上擦除
        }
        if (ss[1].equals("vertical") || ss[1].equals("right_down") || ss[1].equals("left_down")) {
            erasePath(sx, sy+1);  // 向下擦除
        }
        if (ss[1].equals("horizon") || ss[1].equals("down_left") || ss[1].equals("right_down")) {
            erasePath(sx-1, sy);
        }
        if (ss[1].equals("horizon") || ss[1].equals("left_down")  || ss[1].equals("down_right")) {
            erasePath(sx+1, sy);
        }
    }

    double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showAnythingInitialize();
        propertyController = new PropertyController(messageBox);
        nodeFactory = new NodeFactory();

        ImageView shadow;
        shadow = new ImageView();
        shadow.setFitHeight(viewH);
        shadow.setFitWidth (viewW);
        shadow.setImage(new Image("sources/img/shadow.png"));
        shadow.setX(-1000);
        shadow.setY(-1000);

        ImageView connector = new ImageView();
        connector.setFitWidth(2*connectorSize);
        connector.setFitHeight(2*connectorSize);
        connector.setImage(new Image("sources/img/co.png"));
        connector.setX(-1000);
        connector.setY(-1000);

        drawingArea.getChildren().addAll(shadow, connector);

        drawingArea.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (status == Status.normal && selectNode==null) {
                MyNode node = nodeTable[(int) event.getY()/viewH][(int) event.getX()/viewW];
                if (node!=null) {
                    if(clickStatus == ClickStatus.choosingStart) {
                        int found = touchConnector(node, event.getX(), event.getY(), outConnector);
                        if(found != -1) {
                            clickStatus = ClickStatus.choosingEnd;
                            startConnectionID = node.getFactoryID();
                            startConnectionPlace = found;
                            System.out.println("已选择了连线开始点，请选择连线结束点");
                        }
                    }else if(clickStatus == ClickStatus.choosingEnd) {
                        int found = touchConnector(node, event.getX(), event.getY(), inConnector);
                        clickStatus = ClickStatus.choosingStart;
                        if(found != -1) {
                            endConnectionID = node.getFactoryID();
                            endConnectionPlace = found;
                            MyNode start = nodeMap.get(startConnectionID);
                            MyNode end = nodeMap.get(endConnectionID);
                            if(start instanceof StartNode) {
                                ((StartNode) start).setNxtID(endConnectionID);
                                ((StartNode) start).setNxtPlace(endConnectionPlace);
                            }else if(start instanceof BranchNode) {
                                if(startConnectionPlace == 2) {
                                    ((BranchNode) start).setBranchTrueID(endConnectionID);
                                    ((BranchNode) start).setBranchTruePlace(endConnectionPlace);
                                }else if(startConnectionPlace == 4) {
                                    ((BranchNode) start).setBranchFalseID(endConnectionID);
                                    ((BranchNode) start).setBranchFalsePlace(endConnectionPlace);
                                }
                            }else if(start instanceof MergeNode) {
                                ((MergeNode) start).setMergeNxtID(endConnectionID);
                                ((MergeNode) start).setMergeNxtPlace(endConnectionPlace);
                            }else if(start instanceof LoopStNode) {
                                ((LoopStNode) start).setLoop_stNxtID(endConnectionID);
                                ((LoopStNode) start).setLoop_stNxtPlace(endConnectionPlace);
                            }else if(start instanceof LoopEndNode) {
                                ((LoopEndNode) start).setLoop_endNxtID(endConnectionID);
                                ((LoopEndNode) start).setLoop_endNxtPlace(endConnectionPlace);
                            }else if(start instanceof StatementNode) {
                                ((StatementNode) start).setNxtID(endConnectionID);
                                ((StatementNode) start).setNxtPlace(endConnectionPlace);
                            }else if(start instanceof PrintNode) {
                                ((PrintNode) start).setNxtID(endConnectionID);
                                ((PrintNode) start).setNxtPlace(endConnectionPlace);
                            }
                            if(end instanceof EndNode) {
                                ((EndNode) end).setPreID(startConnectionID);
                                ((EndNode) end).setPrePlace(startConnectionPlace);
                            }else if(end instanceof BranchNode) {
                                ((BranchNode) end).setBranchPreID(startConnectionID);
                                ((BranchNode) end).setBranchPrePlace(startConnectionPlace);
                            }else if(end instanceof MergeNode) {
                                if(endConnectionPlace == 1) {
                                   ((MergeNode) end).setMergeTrueID(startConnectionID);
                                   ((MergeNode) end).setMergeTruePlace(startConnectionPlace);
                                }else if(endConnectionPlace == 4) {
                                    ((MergeNode) end).setMergeFalseID(startConnectionID);
                                    ((MergeNode) end).setMergeFalsePlace(startConnectionPlace);
                                }
                            }else if(end instanceof LoopStNode) {
                                ((LoopStNode) end).setLoop_stPreID(startConnectionID);
                                ((LoopStNode) end).setLoop_stPrePlace(startConnectionPlace);
                            }else if(end instanceof LoopEndNode) {
                                ((LoopEndNode) end).setLoop_endPreID(startConnectionID);
                                ((LoopEndNode) end).setLoop_endPrePlace(startConnectionPlace);
                            }else if(end instanceof StatementNode) {
                                ((StatementNode) end).setPreID(startConnectionID);
                                ((StatementNode) end).setPrePlace(startConnectionPlace);
                            }else if(end instanceof PrintNode) {
                                ((PrintNode) end).setPreID(startConnectionID);
                                ((PrintNode) end).setPrePlace(startConnectionPlace);
                            }
                            System.out.println("已选择了连线结束点, 连线完成");
                            try {
                                tryCreatePath(end);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            startConnectionID = -1;
                            System.out.println("连线结束点选择失败，请重新选择连线开始点");
                        }
                    }
                }
            }
            if (status == Status.dragging) {
                status = Status.normal;
                return;
            }
            keyBoardPane.requestFocus();
            if (event.getButton().name().equals("PRIMARY")) {
                if (event.getClickCount() == 1 && selectNode != null) {
                    MyNode node = nodeFactory.produceNode(selectNode, (int)(event.getX()-event.getX()%viewW), (int)(event.getY()-event.getY()%viewH));
                    if(node.putInTable(nodeTable)){
                        nodeMap.put(node.getFactoryID(), node);
                        node.draw(drawingArea);
                    }
                }
                if (event.getClickCount() == 1) {
                    int y = (int)(event.getY()-event.getY()%viewH)/viewH;
                    int x = (int)(event.getX()-event.getX()%viewW)/viewW;
                    MyNode node = nodeTable[y][x];
                    if (node != null) {
                        propertyController.update(node);
                        showSelection.clear();
                        try {
                            showSelection.draw(x,y);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            else if (event.getButton().name().equals("SECONDARY")) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                MyNode node = nodeTable[y/viewH][x/viewW];
                if (node!=null) {
                    eraseAllPath(node);
                    node.removeFromTable(nodeTable);
                    nodeMap.remove(node.getFactoryID());
                    node.remove(drawingArea);
                }
            }
//            updateConnection();
        });

        drawingArea.setOnMouseMoved(event -> {
            if (status == Status.normal) {
                boolean found = false;
                int x = (int) event.getX();
                int y = (int) event.getY();
                MyNode node = nodeTable[y/viewH][x/viewW];
                if (node!=null) {
                    int c1 = touchConnector(node, event.getX(), event.getY(), inConnector);
                    int c2 = touchConnector(node, event.getX(), event.getY(), outConnector);
                    int c;
                    if(c1 == -1){
                        if(c2 == -1){
                            found = false;
                            c = -1;
                        }else{
                            c = c2;
                        }
                    }else {
                        c = c1;
                    }
                    if (c!=-1) {
                        connector.setX(node.getImageView().getX()+connectorPos[c][1]-connectorSize);
                        connector.setY(node.getImageView().getY()+connectorPos[c][0]-connectorSize);
                        // 这样刷一下能把connector的显示拉到最上面
                        drawingArea.getChildren().remove(connector);
                        drawingArea.getChildren().add(connector);
                        found = true;
                    }
                }
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
            System.out.println("MOUSE_PRESSED");
            int y = (int)(event.getY()/viewH);
            int x = (int)(event.getX()/viewW);
            selection = nodeTable[y][x];
            if(selection != null && !selection.getImageView().getId().contains("line")) {
                eraseAllPath(selection);
                showSelection.clear();
                System.out.println("Selection is: " + selection.getClass().getName());
                selection.remove(drawingArea);
                selection.draw(drawingArea);
                relativeX = event.getX() -selection.getImageView().getX();
                relativeY = event.getY() - selection.getImageView().getY();
                selection.removeFromTable(nodeTable);
            }
        });



        drawingArea.addEventFilter(MouseDragEvent.MOUSE_DRAGGED, event -> {
            status = Status.dragging;
            if (event.isPrimaryButtonDown() && selection!=null && !selection.getImageView().getId().contains("line")) {
                shadow.setX((int) event.getX() - (int) event.getX()%viewW);
                shadow.setY((int) event.getY() - (int) event.getY()%viewH);
                selection.remove(drawingArea);
                selection.draw(drawingArea, event.getX()-relativeX, event.getY()-relativeY);
            }
        });

        drawingArea.addEventFilter(MouseDragEvent.MOUSE_RELEASED, event -> {
            if (selection!=null && !selection.getImageView().getId().contains("line")) {
                selection.remove(drawingArea);
                selection.draw(drawingArea, (int) event.getX() - (int) event.getX()%viewW, (int) event.getY() - (int) event.getY()%viewH);
                selection.putInTable(nodeTable);
                try {
                    tryCreatePath(selection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                selectNode = nowImage.getId();
                System.out.println("now: "+ selectNode);
                if(selectNode != null){    // 如果之前有选择过Node，则清除之前选择的Node
                    if (!nowImage.getId().contains("start")){
                        choose_start.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../sources/img/choose_node_start.png"))));
                        choose_start.setId("choose_node_start");
                    }
                    if (!nowImage.getId().contains("end")){
                        choose_end.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../sources/img/choose_node_end.png"))));
                        choose_end.setId("choose_node_end");
                    }
                    if (!nowImage.getId().contains("statement")){
                        choose_statement.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../sources/img/choose_node_statement.png"))));
                        choose_statement.setId("choose_node_statement");
                    }
                    if (!nowImage.getId().contains("branch")){
                        choose_branch.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../sources/img/choose_node_branch.png"))));
                        choose_branch.setId("choose_node_branch");
                    }
                    if(!nowImage.getId().contains("merge")){
                        choose_merge.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../sources/img/choose_node_merge.png"))));
                        choose_merge.setId("choose_node_merge");
                    }
                    if (!nowImage.getId().contains("loop_start")){
                        choose_loop_start.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../sources/img/choose_node_loopStart.png"))));
                        choose_loop_start.setId("choose_node_loopStart");
                    }
                    if (!nowImage.getId().contains("loop_end")){
                        choose_loop_end.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../sources/img/choose_node_loopEnd.png"))));
                        choose_loop_end.setId("choose_node_loopEnd");
                    }
                    if (!nowImage.getId().contains("print")){
                        choose_print.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../sources/img/choose_node_print.png"))));
                        choose_print.setId("choose_node_print");
                    }
                }
                if (nowImage.getId().contains("choose")) {
                    nowImage.setId(nowImage.getId().replace("choose", "shadow"));
                }
                else if (nowImage.getId().contains("shadow")) {
                    nowImage.setId(nowImage.getId().replace("shadow", "choose"));
                    selectNode = null;
                }
                System.out.println("debug: "+ nowImage.getId());
                nowImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../sources/img/" + nowImage.getId() + ".png"))));
                if(selectNode != null){
                    System.out.println("current status: " + selectNode.split("_")[2]);
                }else{
                    System.out.println("current status: null");
                }
            }
        });
//        装载data
        VarName.setCellValueFactory(new PropertyValueFactory<>("varName"));
        VarType.setCellValueFactory(new PropertyValueFactory<>("varType"));
        VarValue.setCellValueFactory(new PropertyValueFactory<>("varValue"));
        tableView.setEditable(true);
        VarName.setCellFactory(TextFieldTableCell.forTableColumn());
        VarName.setOnEditCommit(event->{
            TableView<TableVar> tempTable = event.getTableView();
            TableVar tem_Table_var = (TableVar) tempTable.getItems().get(event.getTablePosition().getRow());
            tem_Table_var.setVarName(event.getNewValue());
        });
        VarType.setCellFactory(TextFieldTableCell.forTableColumn());
        VarType.setOnEditCommit(event->{
            TableView<TableVar> tempTable = event.getTableView();
            TableVar tem_Table_var = (TableVar) tempTable.getItems().get(event.getTablePosition().getRow());
            tem_Table_var.setVarType(event.getNewValue());
        });
        VarValue.setOnEditCommit(event->{
            TableView<TableVar> tempTable = event.getTableView();
            TableVar tem_Table_var = (TableVar) tempTable.getItems().get(event.getTablePosition().getRow());
            tem_Table_var.setVarValue(event.getNewValue());
        });
        VarValue.setCellFactory(TextFieldTableCell.forTableColumn());
        tableView.setItems(data);
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        add.setOnAction(e->{

            if(txtName.getText().isEmpty()||txtType.getText().isEmpty()
                    ||txtValue.getText().isEmpty()){
                alert.setContentText("表格未填满");
                alert.show();
                return;
            }
            try {
                data.add(new TableVar(txtName.getText(), txtType.getText(), txtValue.getText()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        delete.setOnAction(e->{
            if (data.size() == 0) {
                alert.setContentText("表格为空!");
                alert.show();
                return;
            }
            //获取光标所在行号
            int moveIndex = tableView.getSelectionModel().getFocusedIndex();
            System.out.println(moveIndex);
            //删除对应行
            TableVar tableVar = data.get(moveIndex);
            alert.setContentText(tableVar.getVarName());
            alert.show();
            data.remove(moveIndex);
        });
//        停止，暂停，单步，运行四个按钮点击事件测试
        stopButton.setOnMouseClicked(e->{
            alert.setContentText("stop_success");
            alert.show();
        });
        stepButton.setOnMouseClicked(e->{
            alert.setContentText("step_success");
            alert.show();
        });
        runButton.setOnMouseClicked(e->{
            alert.setContentText("run_success");
            alert.show();
            outText.setText("测试输出");
        });
        pauseButton.setOnMouseClicked(e->{
            alert.setContentText("pause_success");
            alert.show();
            outText.appendText("测试追加输出");
        });
    }
    private int getStartID() {
        for (MyNode node : nodeMap.values()) {
            if (node instanceof StartNode)
                return node.getFactoryID();
        }
        return -1;
    }

    public void menuNew(){
        System.out.println("New");
//        Thread thread = new Thread();
//        while(true);
    }

    /**
     * 将nodeMap和nodeTable和varList中的节点信息转换为String。
     * 打开一个文件选择窗口，在本地新建一个文件保存这些信息。
     * 使用Fastjson库
     */
    public void menuJsonExport(){
        System.out.println("menuJsonExport");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存文件");
        fileChooser.setInitialFileName("new.json");
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                String nodeMapJson = JSON.toJSONString(nodeMap, SerializerFeature.IgnoreErrorGetter);
                String nodeTableJson = JSON.toJSONString(nodeTable, SerializerFeature.IgnoreErrorGetter);
                String varListJson = JSON.toJSONString(varList, SerializerFeature.IgnoreErrorGetter);
                fileWriter.write(nodeMapJson+"\n"+ nodeTableJson+"\n"+ varListJson);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void menuJsonImport(){
        System.out.println("menuJsonImport");
    }

    public void menuCodeExport(){
        System.out.println("menuCodeExport");
    }

    public void menuCodeImport(){
        System.out.println("menuCodeImport");
    }

    public void menuImageSave(){
        System.out.println("menuImageSave");
    }

    public void run(){
        System.out.println("run");
        try {
            Run.setup(getStartID(), nodeMap, data);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }


    public void stepRun(){
        System.out.println("stepRun");
        int next = -2;
        try {
            next = Run.stepRun();
        } catch (Exception e) {
//            e.printStackTrace();
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.show();
            return;
        }
        // stepRun success, update run position
        MyNode node = nodeMap.get(next);
        int x = (int) (node.getImageView().getX()/viewW);
        int y = (int) (node.getImageView().getY()/viewH);
        showRunPosition.clear();
        try {
            showRunPosition.draw(x,y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // update data table
        Map<String, Object> varMap = Run.varMap;
        for (TableVar tVar : data) {
            String name = tVar.getVarName();
            tVar.setVarValue(varMap.get(name).toString());
        }
        tableView.refresh();
    }
    public void reset(){
        System.out.println("reset");
        Run.reset();
    }

    public void commit(){
        propertyController.sendMessage();
    }
}


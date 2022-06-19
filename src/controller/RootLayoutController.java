package controller;

import java.io.*;
import java.net.URL;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.stage.Stage;
import model.*;

import static model.Constant.tableH;
import static model.Constant.tableW;
import static model.Constant.viewH;
import static model.Constant.viewW;
import static model.Constant.connectorSize;

import model.Constant.ClickStatus;
import model.Constant.Status;

import javax.imageio.ImageIO;

public class RootLayoutController implements Initializable {
    //创建数据源
    final ObservableList<TableVar> data = FXCollections.observableArrayList(
    );
    private List<TableVar> dataBackup = new ArrayList<>();
    private void saveData() {
//        dataBackup = new ArrayList<>();
        dataBackup.clear();
        for (TableVar var : data) {
            dataBackup.add(new TableVar(var));
        }
    }
    private void recoverData() {
        data.clear();
        for (TableVar var : dataBackup) {
            data.add(new TableVar(var));
        }
    }
    @FXML
    private Button clear;
    @FXML
    private TextArea outText;
    @FXML
    private ImageView buildButton;
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

    Status status = Status.normal;
    ClickStatus clickStatus = ClickStatus.choosingStart;
    NodeFactory nodeFactory;

    ShowAnything showSelection;
    ShowAnything showRunPosition;
    ShowAnything showWrong;
    /**
     * 设置所有ShowAnything对象；
     * 因为initialize函数已经太长了，所以单独写一个函数
     */
    void showAnythingInit() {
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
        showWrong = new ShowAnything(null, drawingArea, 0, 0);
        // 结构错误、语法错误等

    }

    void buttonInit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //    停止，暂停，单步，运行四个按钮点击事件测试
        buildButton.setOnMouseClicked(e-> {
            try {
                build();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        stopButton.setOnMouseClicked(e->{
            reset();
        });
        stepButton.setOnMouseClicked(e->{
//            alert.setContentText("step_success");
//            alert.show();
            try {
                stepRun();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        runButton.setOnMouseClicked(e->{
//            alert.setContentText("run_success");
//            alert.show();
//            outText.setText("测试输出");
//            run();
            try {
                continiousRun();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        pauseButton.setOnMouseClicked(e->{
            pause();
        });
    }

    /*
    inConnector记录每种节点的输入点，outConnector记录输出点
    1：上方    2：下方    3：左侧    4：右侧
     */
    public static Map<String, List<Integer>> inConnector = new HashMap<>();
    public static Map<String, List<Integer>> outConnector = new HashMap<>();
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
        outConnector.put("loop_end", Collections.singletonList(2));
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
    private HashMap<Integer, MyNode> nodeMap = new HashMap<>();
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
                int id = node.getConnectTo()[c];
                System.out.println(id);
                if (id!=-1 && checkInOrOut(node, c)==1) {  // 有连接，而且是输出点
                    connectInfo[0][count] = (int)(node.getImageView().getX()/viewW);
                    connectInfo[1][count] = (int)(node.getImageView().getY()/viewH);
                    connectInfo[2][count] = c;
                    MyNode toNode = nodeMap.get(id);
                    connectInfo[3][count] = (int)(toNode.getImageView().getX()/viewW);
                    connectInfo[4][count] = (int)(toNode.getImageView().getY()/viewH);
                    connectInfo[5][count] = node.getConnectPlace()[c];
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
     * 删除node0与node1之间的所有关系
     */
    public void deleteConnection(MyNode node0, MyNode node1) {
        for(int i=1; i<=4; i++) {
            if (node0.getConnectTo()[i]==node1.getFactoryID()) {
                node0.getConnectTo()[i] = -1;
                node0.getConnectPlace()[i] = -1;
            }
            if (node1.getConnectTo()[i]==node0.getFactoryID()) {
                node1.getConnectTo()[i] = -1;
                node1.getConnectPlace()[i] = -1;
            }
        }
    }

    /**
     * 删除一个节点，实现步骤为： <br/>
     * （1）用{@link #eraseAllPath}把它的连线擦掉 <br/>
     * （2）把它的前驱后继关系取消 <br/>
     * （3）修改drawingArea把它自身擦掉 <br/>
     * （4）从nodeTable中注销 <br/>
     * （5）从nodeMap中注销 <br/>
     * （6）把选框清除 <br/>
     *
     *
     * @param node  待删除节点
     */
    public void deleteNode(MyNode node) {
        // 擦掉连线
        eraseAllPath(node);
        // 删关系
        for (int i=1; i<=4; i++) {
            int id = node.getConnectTo()[i];
            MyNode node1 = nodeMap.get(id);
            if (node1!=null)
                deleteConnection(node, node1);
        }
        // 擦自身
        node.remove(drawingArea);
        // 从nodeTable注销
        int x = (int) (node.getImageView().getX() / viewW);
        int y = (int) (node.getImageView().getY() / viewH);
        nodeTable[y][x] = null;
        // 从nodeMap注销
        nodeMap.remove(node.getFactoryID());
        // 清除选框
        showSelection.clear();
    }

    /**
     * 擦除所有与node相连的线，不影响前驱后继
     * @param node
     */
    public void eraseAllPath(MyNode node) {
        int x = (int) (node.getImageView().getX()/viewW);
        int y = (int) (node.getImageView().getY()/viewH);
        for (int c=1; c<=4; c++) {
            if (node.getConnectTo()[c]!=-1) {
                System.out.println("eraseAllPath:"+c);
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
            int id = node.getConnectTo()[c];
            if (id!=-1) {
                int inOut = checkInOrOut(node, c);
                int sx = 0, sy = 0, sc = 0, ex = 0, ey = 0, ec = 0;
                MyNode toNode = nodeMap.get(id);
//                sx = (int)(node.getImageView().getX()/viewW);
//                sy = (int)(node.getImageView().getY()/viewH);
//                sc = c;
//                ex = (int)(toNode.getImageView().getX()/viewW);
//                ey = (int)(toNode.getImageView().getY()/viewH);
//                ec = node.connectPlace[c];
                ArrayList<ArrayList<Integer>> path = null;
//                if (sy<ey)
//                    path = getPath(sx, sy, sc, ex, ey, ec);
//                else
//                    path = getPath(ex, ey, ec, sx, sy, sc);
                if (inOut==1) { // 输出点
                    sx = (int)(node.getImageView().getX()/viewW);
                    sy = (int)(node.getImageView().getY()/viewH);
                    sc = c;
                    ex = (int)(toNode.getImageView().getX()/viewW);
                    ey = (int)(toNode.getImageView().getY()/viewH);
                    ec = node.getConnectPlace()[c];
                }
                else if (inOut==0) { // 输入点
                    sx = (int)(toNode.getImageView().getX()/viewW);
                    sy = (int)(toNode.getImageView().getY()/viewH);
                    sc = node.getConnectPlace()[c];
                    ex = (int)(node.getImageView().getX()/viewW);
                    ey = (int)(node.getImageView().getY()/viewH);
                    ec = c;
                }
                else
                    bad();  // 在不应该有连接存在的地方，却有连接存在
                path = getPath(sx, sy, sc, ex, ey, ec);
                if (path==null) { // 绘制失败，清除前驱后继
                    int toC = node.getConnectPlace()[c];
                    toNode.getConnectTo()[toC] = -1;
                    toNode.getConnectPlace()[toC] = -1;
                    node.getConnectTo()[c] = -1;
                    node.getConnectPlace()[c] = -1;
                    System.out.printf("连线(%d,%d,%d,%d,%d,%d)绘制失败，前驱后继关系已清除\n",sx, sy, sc, ex, ey, ec);
                }
                else {
                    showPath(path);

                }
            }
        }
    }



    private void tryConnectNeighborHelp(int x0, int y0, int x1, int y1) throws Exception {
        if (x0<0 || x0>= Constant.tableW || y0<0 || y0>=Constant.tableH)
            return;
        if (x1<0 || x1>= Constant.tableW || y1<0 || y1>=Constant.tableH)
            return;
        if (!(x0==x1 && Math.abs(y0-y1)==1))
            throw new Exception("不相邻");
        MyNode node0 = nodeTable[y0][x0];
        MyNode node1 = nodeTable[y1][x1];
        if (node0==null || node1==null)
            return;
        String name0 = node0.getImageView().getId();
        String name1 = node1.getImageView().getId();
        List<Integer> inC0 = inConnector.get(name0);
        List<Integer> outC0 = outConnector.get(name0);
        List<Integer> inC1 = inConnector.get(name1);
        List<Integer> outC1 = outConnector.get(name1);
        if (outC0!=null && inC1!=null && y0+1==y1 && outC0.contains(2) && inC1.contains(1)) { // 0->1
            node0.getConnectTo()[2] = node1.getFactoryID();
            node0.getConnectPlace()[2] = 1;
            node1.getConnectTo()[1] = node0.getFactoryID();
            node1.getConnectPlace()[1] = 2;
            System.out.println("good!");
        }
        if (outC1!=null && inC0!=null && y1+1==y0 && inC0.contains(1) && outC1.contains(2)) { // 0<-1
            node0.getConnectTo()[1] = node1.getFactoryID();
            node0.getConnectPlace()[1] = 2;
            node1.getConnectTo()[2] = node0.getFactoryID();
            node1.getConnectPlace()[2] = 1;
            System.out.println("very good!");
        }
    }

    /**
     * 查看一个MyNode是否和上下左右的邻居存在直接相连<br/>
     * 如果直接相连，就修改前驱后继关系
//     * @param x  待检查的MyNode
     */
    public void tryConnectNeighbor(MyNode node) {
        int y = (int)(node.getImageView().getY()/viewH);
        int x = (int)(node.getImageView().getX()/viewW);
//        try {
//            tryConnectNeighborHelp(x,y,x+1,y);
//        } catch (Exception ignored) {ignored.printStackTrace();}
//        try {
//            tryConnectNeighborHelp(x,y,x-1,y);
//        } catch (Exception ignored) {ignored.printStackTrace();}
        try {
            tryConnectNeighborHelp(x,y,x,y+1);
        } catch (Exception e) {e.printStackTrace();}
        try {
            tryConnectNeighborHelp(x,y,x,y-1);
        } catch (Exception e) {e.printStackTrace();}


//        if (x<0 || x>= Constant.tableW || y<0 || y>=Constant.tableH)
//            throw new Exception("x或y错误");
//        if (nodeTable[y][x]==null)
//            return;
//        MyNode node = nodeTable[y][x];
//        List<Integer> inC = inConnector.get(node.getImageView().getId());
//        List<Integer> outC = outConnector.get(node.getImageView().getId());
//        for
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
//        nodeTable[sy][sx].remove(drawingArea);
        drawingArea.getChildren().remove(node.getImageView());
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
        Check.outText = outText;
        buildButton.setAccessibleText("ok");
        showAnythingInit();
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
            showWrong.clear();
            if ((status == Status.normal || status == Status.prepareToDrag) && selectNode==null) {
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
            if (status == Status.prepareToDrag)
                status = Status.normal;
            keyBoardPane.requestFocus();
            if (event.getButton().name().equals("PRIMARY")) {
                if (event.getClickCount() == 1 && selectNode != null) {
                    int x = (int)(event.getX()-event.getX()%viewW);
                    int y = (int)(event.getY()-event.getY()%viewH);
                    MyNode node = nodeFactory.produceNode(selectNode, x, y);
                    if(node.putInTable(nodeTable)){
                        nodeMap.put(node.getFactoryID(), node);
                        node.draw(drawingArea);
                        tryConnectNeighbor(node);
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
                    deleteNode(node);
//                    eraseAllPath(node);
//                    node.removeFromTable(nodeTable);
//                    nodeMap.remove(node.getFactoryID());
//                    node.remove(drawingArea);
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
                        c = c2;
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
            if (!event.getButton().name().equals("PRIMARY"))
                return;
            System.out.println("MOUSE_PRESSED");
            status = Status.prepareToDrag;
            int y = (int)(event.getY()/viewH);
            int x = (int)(event.getX()/viewW);
            selection = nodeTable[y][x];
            if (selection!=null) {
                relativeX = event.getX() - selection.getImageView().getX();
                relativeY = event.getY() - selection.getImageView().getY();
            }
        });



        drawingArea.addEventFilter(MouseDragEvent.MOUSE_DRAGGED, event -> {
            if (!event.getButton().name().equals("PRIMARY"))
                return;
            if (selection==null)
                return;
            if (status == Status.prepareToDrag) {
                if (distance(relativeX, relativeY,
                        event.getX() -selection.getImageView().getX(),
                        event.getY() - selection.getImageView().getY())>Constant.dragThreshold) {
                    status = Status.dragging;
                    if(selection != null && !selection.getImageView().getId().contains("line")) {
                        eraseAllPath(selection);
                        System.out.println("Selection is: " + selection.getClass().getName());
                        selection.remove(drawingArea);
                        selection.draw(drawingArea);
                        selection.removeFromTable(nodeTable);
                    }
                    showSelection.clear();
                }

                else return;
            }

//                status = Status.dragging;
//            if (showSelection.hasDraw())
//                showSelection.clear();
//            status = Status.dragging;
            if (event.isPrimaryButtonDown() && selection!=null && !selection.getImageView().getId().contains("line")) {
                shadow.setX((int) event.getX() - (int) event.getX()%viewW);
                shadow.setY((int) event.getY() - (int) event.getY()%viewH);
                selection.remove(drawingArea);
                selection.draw(drawingArea, event.getX()-relativeX, event.getY()-relativeY);
            }
        });

        drawingArea.addEventFilter(MouseDragEvent.MOUSE_RELEASED, event -> {
            if (!event.getButton().name().equals("PRIMARY"))
                return;
            if (status!=Status.dragging)
                return;
            if (selection!=null && !selection.getImageView().getId().contains("line")) {
                int x = (int) event.getX() / viewW;
                int y = (int) event.getY() / viewH;
                if (nodeTable[y][x] != null && nodeTable[y][x].getFactoryID() == selection.getFactoryID()) // 没移动
                    return;
                if (nodeTable[y][x] != null) {
                    deleteNode(nodeTable[y][x]);
                }

                selection.remove(drawingArea);
                selection.draw(drawingArea, (int) event.getX() - (int) event.getX()%viewW, (int) event.getY() - (int) event.getY()%viewH);
                selection.putInTable(nodeTable);
                try {
                    tryCreatePath(selection);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tryConnectNeighbor(selection);
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
        buttonInit();
        clear.setOnAction(e->{
            outText.setText("");
            showWrong.clear();
        });


    }

    private void makePng() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开文件");
        File defaultDir = new File("example");
        if (!defaultDir.exists())
            if (defaultDir.mkdir())
                System.out.println("创建了example文件夹");
        fileChooser.setInitialDirectory(defaultDir);
        File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
//        File file = new File("example/e2.c");
        CParse.Main.setFile(file);
        Stage anotherStage = new Stage();
        CParse.Main mm = new CParse.Main();
        try {
            mm.start(anotherStage);
        } catch (Exception e) {
            e.printStackTrace();
            outText.appendText("生成失败\n");
            return;
        }
        outText.appendText("生成成功\n");
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
        nodeMap.clear();
        data.clear();
        outText.setText("");
        for(int i = 0; i < tableH; i++){
            for(int j = 0; j < tableW; j++){
                nodeTable[i][j] = null;
            }
        }
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
        File defaultDir = new File("save");
        if (!defaultDir.exists())
            if (defaultDir.mkdir())
                System.out.println("创建了save文件夹");
        fileChooser.setInitialDirectory(defaultDir);
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                for(int i = 0; i < tableH; i++){
                    for(int j = 0; j < tableW; j++){
                        String nodeJson = JSON.toJSONString(nodeTable[i][j], SerializerFeature.IgnoreErrorGetter, SerializerFeature.WriteMapNullValue);
                        fileWriter.write(nodeJson+"$");
                    }
                }
                fileWriter.write("@");
                saveData();
                String varListJson = JSON.toJSONString(dataBackup, SerializerFeature.IgnoreErrorGetter);
                fileWriter.write(varListJson);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 将nodeMap和nodeTable和varList从JSON String反序列化为java对象。
     * 打开一个文件选择窗口，选择一个本地文件作为JSON的输入文本。
     * 使用Fastjson库
     */
    public void menuJsonImport(){
        System.out.println("menuJsonImport");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开文件");
        File defaultDir = new File("save");
        if (!defaultDir.exists())
            if (defaultDir.mkdir())
                System.out.println("创建了save文件夹");
        fileChooser.setInitialDirectory(defaultDir);
        File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
        if (file != null) {
            for(int i = 0; i < tableH; i++){
                for(int j = 0; j < tableW; j++) {
                    if (nodeTable[i][j] != null) {
                        nodeTable[i][j].remove(drawingArea);
                    }
                    nodeTable[i][j] = null;
                }
            }
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferReader = new BufferedReader(fileReader);
                String json = bufferReader.readLine();
                String[] jsonArgs = json.split("@");
                String[] nodeTableJson = jsonArgs[0].split("\\$");
                String varListJson = jsonArgs[1];
                nodeMap.clear();
                for(int i = 0; i < tableH; i++) {
                    for (int j = 0; j < tableW; j++) {
                        String nodeJson = nodeTableJson[i * tableW + j];
                        MyNode node = null;
                        String nodeJsonWithout$ = nodeJson.replace("$", "");
                        if(nodeJson.contains("branchPreID")){
                            node = JSON.parseObject(nodeJsonWithout$, BranchNode.class);
                            System.out.println("recreate BranchNode in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("printText")) {
                            node = JSON.parseObject(nodeJsonWithout$, PrintNode.class);
                            System.out.println("recreate PrintNode in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("statementText")) {
                            node = JSON.parseObject(nodeJsonWithout$, StatementNode.class);
                            System.out.println("recreate StatementNode in nodeTable");
                            System.out.println(nodeJson);
                            System.out.println("[debug] recreate StatementNode " + Arrays.toString(node.getConnectPlace()));
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("mergeTrueID")){
                            node = JSON.parseObject(nodeJsonWithout$, MergeNode.class);
                            System.out.println("recreate MergeNode in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("loop_endPrePlace")){
                            node = JSON.parseObject(nodeJsonWithout$, LoopEndNode.class);
                            System.out.println("recreate LoopEndNode in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("loop_stPreID")) {
                            node = JSON.parseObject(nodeJsonWithout$, LoopStNode.class);
                            System.out.println("recreate LoopStNode in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("nxtPlace")){
                            node = JSON.parseObject(nodeJsonWithout$, StartNode.class);
                            System.out.println("recreate start node in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("prePlace")){
                            node = JSON.parseObject(nodeJsonWithout$, EndNode.class);
                            System.out.println("recreate end node in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("line_down_right")){
                            node = JSON.parseObject(nodeJsonWithout$, DownRightLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }else if(nodeJson.contains("line_horizon")){
                            node = JSON.parseObject(nodeJsonWithout$, HorizonLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }else if(nodeJson.contains("line_left_down")){
                            node = JSON.parseObject(nodeJsonWithout$, LeftDownLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }else if(nodeJson.contains("line_right_down")){
                            node = JSON.parseObject(nodeJsonWithout$, RightDownLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }else if(nodeJson.contains("line_vertical")){
                            node = JSON.parseObject(nodeJsonWithout$, VerticalLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }else if(nodeJson.contains("line_down_left")){
                            node = JSON.parseObject(nodeJsonWithout$, DownLeftLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }
                        if(node!=null){
                            nodeTable[i][j] = node;
                            nodeTable[i][j].draw(drawingArea);
                        }
                    }
                }
                dataBackup = JSON.parseObject(varListJson, new TypeReference<ArrayList<TableVar>>() {});
                recoverData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 遍历nodeMap获得整个图所表示的代码的字符串，用于保存到文件
     * 打开一个文件选择器, 可以选择保存到哪个文件，将上述字符串写入文件
     */
    public void menuCodeExport(){
        System.out.println("menuCodeExport");
        try {
            build();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        StringBuilder code = new StringBuilder();
        normalNxtCodeExport(nodeMap.get(getStartID()), code, 1);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择保存文件");
        fileChooser.setInitialFileName("code.cpp");
        File defaultDir = new File("code");
        if (!defaultDir.exists())
            if (defaultDir.mkdir())
                System.out.println("创建了save文件夹");
        fileChooser.setInitialDirectory(defaultDir);
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        if(file != null){
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(code.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 遍历nodeMap获得整个图所表示的代码的字符串，用于保存到文件
     * @param cur 当前节点
     * @param code 代码字符串
     */
    private void normalNxtCodeExport(MyNode cur, StringBuilder code, Integer indent) {
        boolean err = false;
        while (!(cur instanceof EndNode)) {
            if (cur instanceof StartNode) {
                code.append("#include <bits/stdc++.h>\n\n").append("int main(){\n");
                cur = nodeMap.get(((StartNode) cur).getNxtID());
            } else if (cur instanceof PrintNode) {
                for(int i = 0; i < indent; i++){
                    code.append("\t");
                }
                code.append("cout << ").append(((PrintNode) cur).getText().getText()).append(";\n");
                cur = nodeMap.get(((PrintNode) cur).getNxtID());
            } else if (cur instanceof StatementNode) {
                for(int i = 0; i < indent; i++){
                    code.append("\t");
                }
                code.append(((StatementNode) cur).getText().getText()).append(";\n");
                cur = nodeMap.get(((StatementNode) cur).getNxtID());
            } else if(cur instanceof BranchNode){
                cur = branchCodeExport((BranchNode) cur, code, indent+1);
                cur = nodeMap.get(((MergeNode) cur).getMergeNxtID());
            }else if(cur instanceof LoopStNode){
                cur = loopCodeExport((LoopStNode)cur, code, indent+1);
                assert cur != null;
                cur = nodeMap.get(((LoopEndNode) cur).getLoop_endNxtID());  //  debug
            }else {
                System.out.println("error in normalNxtCodeExport");
                err = true;
                break;
            }
        }
        if(!err){
            code.append("}");
        }
    }

    private MergeNode branchCodeExport(BranchNode branchNode, StringBuilder code, Integer indent) {
        for(int i = 0; i < indent - 1; i++){
            code.append("\t");
        }
        code.append("if(").append(branchNode.getText().getText()).append("){\n");
        MyNode cur = nodeMap.get(branchNode.getBranchTrueID());
        while (!(cur instanceof MergeNode)){
            if(cur instanceof PrintNode) {
                for (int i = 0; i < indent; i++) {
                    code.append("\t");
                }
                code.append("cout << ").append(((PrintNode) cur).getText().getText()).append(";\n");
                cur = nodeMap.get(((PrintNode) cur).getNxtID());
            }else if(cur instanceof StatementNode){
                for (int i = 0; i < indent; i++) {
                    code.append("\t");
                }
                code.append(((StatementNode) cur).getText().getText()).append(";\n");
                cur = nodeMap.get(((StatementNode) cur).getNxtID());
            }else if(cur instanceof BranchNode){
                cur = branchCodeExport((BranchNode) cur, code, indent + 1);
                cur = nodeMap.get(((MergeNode) cur).getMergeNxtID());
            }else if(cur instanceof LoopStNode) {
                cur = loopCodeExport((LoopStNode) cur, code, indent + 1);
                assert cur != null;
                cur = nodeMap.get(((LoopEndNode) cur).getLoop_endNxtID());
            }else {
                System.out.println(cur.getClass().getName());
                System.out.println("error in branchCodeExport");
                break;
            }
        }
        for(int i = 0; i < indent - 1; i++){
            code.append("\t");
        }
        code.append("}else{\n");
        cur = nodeMap.get(branchNode.getBranchFalseID());
        while (!(cur instanceof MergeNode)) {
            if (cur instanceof PrintNode) {
                for (int i = 0; i < indent; i++) {
                    code.append("\t");
                }
                code.append("cout << ").append(((PrintNode) cur).getText().getText()).append(";\n");
                cur = nodeMap.get(((PrintNode) cur).getNxtID());
            } else if (cur instanceof StatementNode) {
                for (int i = 0; i < indent; i++) {
                    code.append("\t");
                }
                code.append(((StatementNode) cur).getText().getText()).append(";\n");
                cur = nodeMap.get(((StatementNode) cur).getNxtID());
            } else if (cur instanceof BranchNode) {
                cur = branchCodeExport((BranchNode) cur, code, indent + 1);
                cur = nodeMap.get(((MergeNode) cur).getMergeNxtID());
            } else if (cur instanceof LoopStNode) {
                cur = loopCodeExport((LoopStNode) cur, code, indent + 1);
                assert cur != null;
                cur = nodeMap.get(((LoopEndNode) cur).getLoop_endNxtID());
            } else {
                System.out.println(cur.getClass().getName());
                System.out.println("error in branchCodeExport");
                break;
            }
        }
        for(int i = 0; i < indent - 1; i++){
            code.append("\t");
        }
        code.append("}\n");
        assert cur instanceof MergeNode;
        return (MergeNode) cur;
    }

    private LoopEndNode loopCodeExport(LoopStNode loopStNode, StringBuilder code, Integer indent) {
        for(int i = 0; i < indent-1; i++){
            code.append("\t");
        }
        code.append("do{\n");
        MyNode cur = nodeMap.get(loopStNode.getLoop_stNxtID());
        while(!(cur instanceof LoopEndNode)){
            if(cur instanceof PrintNode) {
                for(int i = 0; i < indent-1; i++){
                    code.append("\t");
                }
                code.append("cout << ").append(((PrintNode) cur).getText().getText()).append(";\n");
                cur = nodeMap.get(((PrintNode) cur).getNxtID());
            }else if(cur instanceof StatementNode) {
                for(int i = 0; i < indent-1; i++){
                    code.append("\t");
                }
                code.append(((StatementNode) cur).getText().getText()).append(";\n");
                cur = nodeMap.get(((StatementNode) cur).getNxtID());
            }else if(cur instanceof BranchNode) {
                cur = branchCodeExport((BranchNode) cur, code, indent+1);
            }else if(cur instanceof LoopStNode) {
                cur = loopCodeExport((LoopStNode) cur, code, indent+1);
                cur = nodeMap.get(((LoopEndNode) cur).getLoop_endNxtID());
            }else{
                System.out.println("error in loopCodeExport");
                break;
            }
        }
        for(int i = 0; i < indent - 1; i++){
            code.append("\t");
        }
        if(cur instanceof LoopEndNode){
            code.append("}while(").append(((LoopEndNode)cur).getText().getText()).append(");\n");
            return (LoopEndNode)cur;
        }
        return null;
    }

    public void menuCodeImport(){
        System.out.println("menuCodeImport");
        makePng();
    }

    /**
     * 打开文件选择器并选择文件
     * 将drawingArea的图形保存为图片，并保存为png格式
     */
    public void menuImageSave(){
        System.out.println("menuImageSave");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存图片");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png", "*.png"));
        File file = fileChooser.showSaveDialog(drawingArea.getScene().getWindow());
        if(file != null){
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(drawingArea.snapshot(null, null), null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void menuOpenFromTemplate(){
        System.out.println("menuOpenFromTemplate");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("从模板创建文件");
        File defaultDir = new File("Templates");
        if (!defaultDir.exists())
            if (defaultDir.mkdir())
                System.out.println("创建了Templates文件夹");
        fileChooser.setInitialDirectory(defaultDir);
        File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
        if(file != null) {
            for(int i = 0; i < tableH; i++){
                for(int j = 0; j < tableW; j++) {
                    if (nodeTable[i][j] != null) {
                        nodeTable[i][j].remove(drawingArea);
                    }
                    nodeTable[i][j] = null;
                }
            }
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferReader = new BufferedReader(fileReader);
                String json = bufferReader.readLine();
                String[] jsonArgs = json.split("@");
                String[] nodeTableJson = jsonArgs[0].split("\\$");
                String varListJson = jsonArgs[1];
                nodeMap.clear();
                for(int i = 0; i < tableH; i++) {
                    for (int j = 0; j < tableW; j++) {
                        String nodeJson = nodeTableJson[i * tableW + j];
                        MyNode node = null;
                        String nodeJsonWithout$ = nodeJson.replace("$", "");
                        if(nodeJson.contains("branchPreID")){
                            node = JSON.parseObject(nodeJsonWithout$, BranchNode.class);
                            System.out.println("recreate BranchNode in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("printText")) {
                            node = JSON.parseObject(nodeJsonWithout$, PrintNode.class);
                            System.out.println("recreate PrintNode in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("statementText")) {
                            node = JSON.parseObject(nodeJsonWithout$, StatementNode.class);
                            System.out.println("recreate StatementNode in nodeTable");
                            System.out.println(nodeJson);
                            System.out.println("[debug] recreate StatementNode " + Arrays.toString(node.getConnectPlace()));
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("mergeTrueID")){
                            node = JSON.parseObject(nodeJsonWithout$, MergeNode.class);
                            System.out.println("recreate MergeNode in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("loop_endPrePlace")){
                            node = JSON.parseObject(nodeJsonWithout$, LoopEndNode.class);
                            System.out.println("recreate LoopEndNode in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("loop_stPreID")) {
                            node = JSON.parseObject(nodeJsonWithout$, LoopStNode.class);
                            System.out.println("recreate LoopStNode in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("nxtPlace")){
                            node = JSON.parseObject(nodeJsonWithout$, StartNode.class);
                            System.out.println("recreate start node in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("prePlace")){
                            node = JSON.parseObject(nodeJsonWithout$, EndNode.class);
                            System.out.println("recreate end node in nodeTable");
                            nodeMap.put(node.getFactoryID(),node);
                        }else if(nodeJson.contains("line_down_right")){
                            node = JSON.parseObject(nodeJsonWithout$, DownRightLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }else if(nodeJson.contains("line_horizon")){
                            node = JSON.parseObject(nodeJsonWithout$, HorizonLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }else if(nodeJson.contains("line_left_down")){
                            node = JSON.parseObject(nodeJsonWithout$, LeftDownLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }else if(nodeJson.contains("line_right_down")){
                            node = JSON.parseObject(nodeJsonWithout$, RightDownLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }else if(nodeJson.contains("line_vertical")){
                            node = JSON.parseObject(nodeJsonWithout$, VerticalLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }else if(nodeJson.contains("line_down_left")){
                            node = JSON.parseObject(nodeJsonWithout$, DownLeftLine.class);
                            System.out.println("recreate DownRightLine in nodeTable");
                        }
                        if(node!=null){
                            nodeTable[i][j] = node;
                            nodeTable[i][j].draw(drawingArea);
                        }
                    }
                }
                dataBackup = JSON.parseObject(varListJson, new TypeReference<ArrayList<TableVar>>() {});
                recoverData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void build() throws Exception {
        Test.printMap(nodeMap);

        System.out.println("build");
        if (Run.isRunning()) {
            outText.appendText("运行已停止");
            reset();
        }
        saveData();
        try {
            Run.setup(
                    getStartID(),
                    nodeMap,
                    data,
                    outText,
                    inform,
                    lock
            );
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.show();
            outText.appendText("构建失败："+e.getMessage()+"\n");
            return;
        }
//        Check check = new Check();
        boolean startNode = Check.checkStartNode(nodeMap);
        if(!startNode){
            outText.appendText("无起始节点\n");
        }
        List<List<Integer>> list1 = Check.checkStartNodeNumber(nodeMap);
        List<List<Integer>> list2 = Check.checkConnectionError(nodeMap);
        List<List<Integer>> errList = Check.checkSyntaxError(nodeTable);
//        showWrong.draw_wrong(0, 1);
//        showWrong.draw_wrong(0, 2);
//        showWrong.draw_wrong(1, 0);
//        showWrong.draw_wrong(2, 0);
        List<List<Integer>> listAll = new ArrayList<>();
        listAll.addAll(list1);
        listAll.addAll(list2);
        //listAll.addAll(errList);
        for (List<Integer> value : listAll) {
            int x = value.get(0);
            int y = value.get(1);
            showWrong.draw_wrong(x, y);
            //outText.appendText(value.get(0)+" "+value.get(1)+"\n");
        }
        // lay_pic(10, 10);
//        outText.appendText("构建成功\n");

        //errList.addAll(Check.checkNodeMapError(nodeMap));
        if (errList.isEmpty())
            outText.appendText("构建成功\n");
        else
            outText.appendText("构建失败，编辑区有"+listAll.size() +"个错误\n");
    }
//    public void lay_pic(int x, int y){
//        ImageView imageView = new ImageView();
//        Image image = new Image("/sources/img/wrong.png");
//        imageView.setImage(image);
//        imageView.setFitWidth(50);
//        imageView.setFitHeight(50);
//        imageView.setX(x);
//        imageView.setY(y);
//        drawingArea.getChildren().add(imageView);
//    }


    /*
    -----------------------------------  多线程 --------------------------------------------
     */
    private final Object lock = new Object(); // 继续运行的时候使用lock.notify()通知运算线程，让它继续运行
    private void continue_() {
        Run.pauseSig = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    private void pause() {
        Run.pauseSig = true;
        try {
            Thread.sleep(100);
        } catch (Exception ignored) {}
        int now = Run.getNow();
//        Exception exception = Run.getException();
        updateEveryThing(now, null);
    }

    /**
     * inform的作用是，让运算线程通知主线程运算已经告一段落
     */
    private final SimpleIntegerProperty inform = new SimpleIntegerProperty(0);
    private void setInform() {
        inform.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int now = Run.getNow();
                Exception exception = Run.exception;
                updateEveryThing(now, exception);
            }
        });
    }

    /**
     * 运算出了结果，调用此函数以更新图形界面；<br/>
     * 不管是单步运行还是连续运行，都用这个<br/>
     * @param now   当前运行位置的id
     * @param exception  如果不为null，说明运行中出现了异常，导致运行终止，于是应该显示这个异常信息
     */
    private void updateEveryThing(int now, Exception exception) {
        // stepRun success, update run position
        if (now!=-1) {
            MyNode node = nodeMap.get(now);
            int x = (int) (node.getImageView().getX()/viewW);
            int y = (int) (node.getImageView().getY()/viewH);
            System.out.println(x+" "+y);
            showRunPosition.clear();
            try {
                showRunPosition.draw(x,y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // update data table
        Map<String, Object> varMap = Run.varMap;
        for (TableVar tVar : data) {
            String name = tVar.getVarName();
            tVar.setVarValue(varMap.get(name).toString());
        }
        tableView.refresh();
        if (exception!=null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    private void continiousRun() throws Exception {
        if (Run.isRunning()) {
            continue_();
            return;
        }
        build();
        System.out.println("cRun");
        int next;
        Run run = new Run();
        run.start();
    }

    public void run() throws Exception {
        continiousRun();
//        build();
//        System.out.println("run");
//        int next;
//        try {
//            next = Run.continuousRun();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Alert alert =new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText(e.getMessage());
//            alert.show();
//            return;
//        }
//        // stepRun success, update run position
//        MyNode node = nodeMap.get(next);
//        int x = (int) (node.getImageView().getX()/viewW);
//        int y = (int) (node.getImageView().getY()/viewH);
//        showRunPosition.clear();
//        try {
//            showRunPosition.draw(x,y);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // update data table
//        Map<String, Object> varMap = Run.varMap;
//        for (TableVar tVar : data) {
//            String name = tVar.getVarName();
//            tVar.setVarValue(varMap.get(name).toString());
//        }
//        tableView.refresh();
    }

    public void test(){
        for(MyNode node : nodeMap.values()){
            System.out.println(node.getClass().getName());
        }
    }

    public void stepRun() throws Exception {
        if (!Run.isRunning())
            build();
        System.out.println("stepRun");
        int next = -2;
        try {
            next = Run.stepRun();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.show();
            return;
        }
        // stepRun success, update run position
        updateEveryThing(next, null);
    }

    public void reset() {
        showRunPosition.clear();
        Run.reset();
        recoverData();
    }

    public void commit(){
        propertyController.sendMessage();
    }
}


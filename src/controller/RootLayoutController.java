package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;



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


    private DrawController drawController;
    private ShapeFactory shapeFactory;
    private PropertyController propertyController;
    private String selectShape = null;
    private ImageView selection = null;
    private double relativeX = -1;
    private double relativeY = -1;

    ImageView[][] viewTable = new ImageView[20][10];

    /**
     * 向viewTable中加入一个ImageView
     * @param image 待加入的imageview
     */
    void putInTable(ImageView image) {
        int x = (int) image.getX();
        int y = (int) image.getY();
        int xi = x / viewW;
        int yi = y / viewH;
        if (viewTable[xi][yi]!=null) {
            drawingArea.getChildren().remove(viewTable[xi][yi]);
        }
        viewTable[xi][yi] = image;
    }

    void removeFromTable(double x, double y) {
        int xi = (int) (x / viewW);
        int yi = (int) (y / viewH);
        viewTable[xi][yi] = null;
//        if (viewTable[xi][yi]!=null) {
//            drawingArea.getChildren().remove(viewTable[xi][yi]);
//        }
    }

//    ArrayList<Integer> tableFindFree() {
//        for (int h=0; h<20; h++) {
//            for (int w=0; w<10; w++) {
//                if (viewTable[h][w]==null) {
//                    ArrayList<Integer> arr = new ArrayList<>();
//                    arr.add(w,h);
//                    return arr;
//                }
//            }
//        }
//        return null;
//    }

    final int viewH = 100;
    final int viewW = 150;

    ImageView produceView(String selectShape) {
        String shape = selectShape.split("_")[2];
        ImageView view = new ImageView();
        view.setFitHeight(viewH);
        view.setFitWidth (viewW);
        switch (shape) {
            case "end": view.setImage(new Image("resources/img/draw_node_end.png")); break;
            case "if" : view.setImage(new Image("resources/img/draw_node_if.png")); break;
        }
        return view;
    }

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

//        imageView = new ImageView();
//        imageView.setFitHeight(viewH);
//        imageView.setFitWidth (viewW);
//        imageView.setImage(new Image("resources/img/choose_node_end.png"));
//        imageView.setX(100);

        drawingArea.getChildren().addAll(shadow);

        // 点击，创建图形
        drawingArea.setOnMouseClicked(event -> {
            keyBoardPane.requestFocus();
            if (event.getClickCount() == 1 && selectShape != null) {
                double x, y;
                x = event.getX();
                y = event.getY();
//                drawingArea
//                imageView.setImage();
                ImageView view = produceView(selectShape);
                int xx = (int) x;
                int yy = (int) y;
                yy -= yy%viewH;
                xx -= xx%viewW;
                view.setX(xx);
                view.setY(yy);
                putInTable(view);
                drawingArea.getChildren().add(view);
//                selectShape = null;
            }
            if (event.getClickCount() == 1) {
                drawController.getPropertyController().update();
            }
        });

        // ---------------------- 拖动响应，分为按下鼠标、拖动、松开鼠标，三个阶段 ---------------------------
        drawingArea.addEventFilter(MouseDragEvent.MOUSE_PRESSED, event -> {
            selection = null;
            loop:
            for (int i=0; i<20; i++) {
                for (int j=0; j<10; j++) {
                    if (viewTable[i][j]!=null && viewTable[i][j].contains(event.getX(), event.getY())) {
                        selection = viewTable[i][j];
                        relativeX = event.getX()-viewTable[i][j].getX();
                        relativeY = event.getY()-viewTable[i][j].getY();
                        removeFromTable(event.getX(), event.getY());
                        break loop;
                    }
                }
            }

        });

        drawingArea.addEventFilter(MouseDragEvent.MOUSE_DRAGGED, event -> {
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
                selection.setX(x-relativeX);
                selection.setY(y-relativeY);

            }
        });

        drawingArea.addEventFilter(MouseDragEvent.MOUSE_RELEASED, event -> {
            if (selection!=null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                y -= y%viewH;
                x -= x%viewW;
                System.out.println(x+" "+y);
//                viewTable[x/viewW][y/viewH] = selection;
                selection.setX(x);
                selection.setY(y);
                putInTable(selection);
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


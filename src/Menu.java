//package com.example.javafx;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Menu{
    // Menu(){}
    //顶部布局
    public Button createButton(String name, String color) {
        Button button = new Button(name);
        button.setMinSize(60, 30);
        button.setMaxSize(60, 30);
        String setColor;
        setColor = "-fx-background-color:"+color+";";
        button.setStyle("-fx-background-insets: 0.0;"+
//                        "-fx-font-color:#f0f8ff;"+
                        "-fx-text-fill:#f0f8ff;"+
                        setColor
                // "-fx-background-color: transparent;"
        );
//        System.out.println("准备打开图片");
//        FileInputStream f =new FileInputStream(imgPath);
//        Image img = new Image(f);
//        System.out.println("打开图片成功");
//        BackgroundImage backgroundImage = new BackgroundImage(img, null, null, null, null);
//        button.setBackground(new Background(backgroundImage));
        return button;
    }

    public HBox createHBox(){
        HBox hBox = new HBox();
        String _message_bg="#4f4f4f";
        Button saveButton = createButton("保存",_message_bg);
        Button openButton = createButton("打开",_message_bg);
        Button aboutButton = createButton("关于", _message_bg);
        Button runButton = createButton("运行", "#006400");
        //鼠标点击事件待加
        // button.setOnMouseClicked(new MouseEvent1());
//        button.setText("Click me if you dare!");设置按钮文本
        Button pauseButton = createButton("暂停", "#844200");
        Button debugButton = createButton("DeBug", "#2f4f4f");
        Button nextStepButton = createButton("下一步", "#004B97");
        Button stopButton = createButton("停止", "#8b0000");
        hBox.setStyle("-fx-padding: 0 5px 0 5px; -fx-background-color: " + _message_bg + ";");
        hBox.getChildren().addAll(saveButton, openButton, aboutButton, runButton, pauseButton
                , debugButton, nextStepButton, stopButton);
        return hBox;
    }
    public TableView createTable(){
        TableView<Object> tableView = new TableView<>();
        tableView.setMaxWidth(240);
        tableView.setStyle("-fx-padding: 0 5px 0 5px;-fx-background-color:#4f4f4f;");
        TableColumn<Object, Object> Var  = new TableColumn<>("变量");
        TableColumn<Object, Object> VarType  = new TableColumn<>("变量类型");
        TableColumn<Object, Object> VarValue  = new TableColumn<>("变量值");
        tableView.getColumns().addAll(Var, VarType, VarValue);
//        TextArea input = new TextArea("输入框");
//        //input.setStyle("-fx-background-color:4f4f4f;-fx-text-fill:#f0f8ff;");
//        input.setPrefRowCount(5);
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        String _message_bg="#4f4f4f";
//        vBox.setStyle("-fx-padding: 0 5px 0 5px; -fx-background-color: " + _message_bg + ";");
//        vBox.setMinWidth(240);
//        vBox.setMaxWidth(240);
//        vBox.getChildren().add(tableView);
        //vBox.getChildren().add(input);
        return tableView;
    }
    public VBox leftVBox(){
        VBox vBox = new VBox();
        vBox.setMinWidth(240);
        vBox.setMaxWidth(240);
        String _message_bg="#4f4f4f";
        vBox.setStyle("-fx-padding: 0 5px 0 5px; -fx-background-color: " + _message_bg + ";");
        return vBox;
    }
    public GridPane centerPane(){
        GridPane gridPane = new GridPane();
        String _message_bg="#272727";
        gridPane.setStyle("-fx-padding: 0 5px 0 5px; -fx-background-color: " + _message_bg + ";");
        return gridPane;
    }
    public Scene createScene() throws FileNotFoundException {
        HBox hBox = createHBox();
        //VBox vBox = createVBox();
//将界面分割成上中下，中间部分又分成左中右，一共五部分，通过<top><left><center><right><bottom>来设置内容。
        BorderPane borderPane = new BorderPane();
        //顶层放置按钮
        borderPane.setTop(hBox);
        //右侧放置表格
        borderPane.setRight(createTable());
        //左侧放置各种节点
        borderPane.setLeft(leftVBox());
        //中间放拖拽布局
        borderPane.setCenter(centerPane());
        //底部放输入框
        TextField input = new TextField("输入框");
        //TextArea input = new TextArea("输入框");
        input.setStyle("-fx-background-color:#3c3c3c; -fx-text-fill:#f0f8ff;");
        input.setMinHeight(30);
        //input.setPrefRowCount(5);
        borderPane.setBottom(input);
        return new Scene(borderPane);
    }
}

//package com.example.javafx;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

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
        //       button.setOnMouseClicked(new MouseEvent1());
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
    public VBox createTable(){
        final ObservableList<Var> data = FXCollections.observableArrayList();
        //创建数据源
        try {
            data.add(new Var("a", "int", "1"));
            data.add(new Var("b", "double", "2.3"));
            data.add(new Var("c", "char", "c"));
            data.add(new Var("d", "", "abc"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // 等你补充
        }

        //创建表格
        TableView<Var> tableView = new TableView<>();
        tableView.setMaxWidth(300);
        tableView.setStyle("-fx-padding: 0 5px 0 5px;-fx-background-color:#4f4f4f;");
        TableColumn<Var, String> VarName  = new TableColumn<>("变量");
        TableColumn<Var, String> VarType  = new TableColumn<>("变量类型");
        TableColumn<Var, String> VarValue  = new TableColumn<>("变量值");
        VarName.setCellValueFactory(new PropertyValueFactory<>("name"));
        VarType.setCellValueFactory(new PropertyValueFactory<>("type"));
        VarValue.setCellValueFactory(new PropertyValueFactory<>("valStr"));
        tableView.setEditable(true);
        VarName.setCellFactory(TextFieldTableCell.forTableColumn());
        VarType.setCellFactory(TextFieldTableCell.forTableColumn());
        VarValue.setCellFactory(TextFieldTableCell.forTableColumn());
        tableView.getColumns().addAll(VarName, VarType, VarValue);
        tableView.setItems(data);
        Button btnAdd = new Button("添加");
        Button btnDel = new Button("删除");
        TextField txtName = new TextField();
        TextField txtType = new TextField();
        TextField txtValue = new TextField();
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        btnAdd.setOnAction(e->{
            if(txtName.getText().isEmpty()||txtType.getText().isEmpty()
                    ||txtValue.getText().isEmpty()){
                alert.setContentText("表格未填满");
                alert.show();
                return;
            }
            try {
                data.add(new Var(txtName.getText(), txtType.getText(), txtValue.getText()));
            } catch (Exception excp) {
                System.out.println(excp.getMessage());
                // 等你补充
            }

        });
        btnDel.setOnAction(e->{
            if (data.size() == 0) {
                alert.setContentText("表格为空!");
                alert.show();
                return;
            }
            //获取光标所在行号
            int moveIndex = tableView.getSelectionModel().getFocusedIndex();
            System.out.println(moveIndex);
            //删除对应行
            data.remove(moveIndex);
            Var.remove();  // 等你补充，填入被删除的变量名
        });
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        HBox hBox1 = new HBox();
        hBox.getChildren().addAll(txtName, txtType, txtValue);
        hBox1.getChildren().addAll(btnAdd, btnDel);
        hBox.setMaxWidth(300);
        vBox.getChildren().addAll(tableView, hBox, hBox1);
        return vBox;
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
    public HBox bottom_HBox(){
        TextField textField = new TextField();
        textField.setMinSize(700, 30);
        textField.setMaxWidth(1400);
        Button up_button = new Button("提交");
        up_button.setMinWidth(60);
        up_button.setMinHeight(30);
        //点击传送数据
        MouseEvent1 mouseEvent1 = new MouseEvent1(textField);
        up_button.setOnMouseClicked(mouseEvent1);
        HBox hBox= new HBox();
        hBox.getChildren().addAll(up_button, textField);
        return hBox;
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
//        TextField input = new TextField("输入框");
//
//        //TextArea input = new TextArea("输入框");
//        input.setStyle("-fx-background-color:#3c3c3c; -fx-text-fill:#f0f8ff;");
//        input.setMinHeight(30);
        //input.setPrefRowCount(5);
        borderPane.setBottom(bottom_HBox());
        return new Scene(borderPane);
    }
}
package com.example.javafx;
//窗口stage
//场景 scence
//布局 stackPane
//控件 Button
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Stack;



public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }
    public void start(Stage primaryStage) throws Exception{
//        //实例化按钮
//        Button button = new Button("这是button上的文字");
//        //鼠标点击
//        button.setOnMouseClicked(new MouseEvent1());
//        //创建布局控件
//        StackPane stackPane = new StackPane();
//        //将button加到布局中
//        stackPane.getChildren().add(button);
//        //创建场景 宽400， 高400
        Menu menu = new Menu();
        Scene scene = menu.createScene();
        //将场景添加到窗口
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}

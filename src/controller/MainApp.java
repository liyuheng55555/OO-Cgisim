package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cigsim");
        initRootLayout();
    }

    /**
     * ����RootLayout.fxml
     */
    public void initRootLayout() {
        try {
            // ��RootLayout.fxml���ص�rootLayout��Ա������
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/controller/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            // ��rootLayout��ʼ��һ��scene���ŵ�stage��չʾ
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

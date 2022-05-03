import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
//        Menu.createScene(primaryStage);
        Menu menu = new Menu();
        Scene scene = menu.createScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cgisim");
//        primaryStage.getIcons().add(new Image("chrome.png"));
        primaryStage.setResizable(true);
//        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();

    }

}

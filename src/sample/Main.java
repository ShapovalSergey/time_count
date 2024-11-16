package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Main extends Application {
    private static int end_time = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main_app.fxml"));
        primaryStage.setTitle("Рабочее время");
        primaryStage.setScene(new Scene(root, 300, 160));
        primaryStage.setResizable(false);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        primaryStage.setX(width-300);
        primaryStage.setY(height-231);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}

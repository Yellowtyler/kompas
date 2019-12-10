package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{



        Parent root = FXMLLoader.load(getClass().getResource("View/menu.fxml"));
        primaryStage.setTitle("Главное меню");
        primaryStage.setScene(new Scene(root, 420, 310));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {

        launch(args);
    }
}

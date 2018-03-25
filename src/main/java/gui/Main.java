package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../koburin.fxml"));
        primaryStage.setTitle("Koburin solver.Solver     小ぶり");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.setMinWidth(280);
        primaryStage.setMinHeight(320);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
    /*solver.Application app = new solver.Application();
        app.run();*/
package xyz.scarey.parkingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Parking Enforcement Application");
        Scene scene = new Scene(new StackPane());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        scene.setRoot(loader.load());

        stage.setScene(scene);
        stage.show();
    }
}

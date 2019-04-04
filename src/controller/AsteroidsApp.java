package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import model.GameObject;

public class AsteroidsApp extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Startscherm");
        Pane mainPane = FXMLLoader.load(AsteroidsApp.class.getResource("../view/sample.fxml"));

         stage.setScene(new Scene(mainPane));

       /*
        */
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Asteroid extends GameObject {

        public Asteroid() {
            super(new Polyline(
                    35, 9, 45, (Math.random() * 10) + 5,
                    53, 15, 53, (Math.random() * 7) + 15,
                    (Math.random() * 20) + 35, 31, 35, 31,
                    27, 25, (Math.random() * 10) + 15, 15, 35, 9));
        }

    }
}

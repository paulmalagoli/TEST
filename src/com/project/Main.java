package com.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static String mode;
    public static Stage stage = new Stage();
    @Override
    public void start(Stage stage) throws Exception{
        stage =this.stage;
        Parent root = FXMLLoader.load(getClass().getResource("view/homePage.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Domi’Nations");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
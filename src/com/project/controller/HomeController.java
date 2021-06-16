package com.project.controller;

import com.project.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeController {
    @FXML Button classicModeButton;
    @FXML Button theMiddleKingdomModeButton;
    @FXML Button harmonyModeButton;

    @FXML
    public void initialize() {
        classicModeButton.setOnAction(event -> {
            Main.mode = "Classic";
            try {
                next();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        theMiddleKingdomModeButton.setOnAction(event -> {
            Main.mode = "The Middle Kingdom";
            try {
                next();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        harmonyModeButton.setOnAction(event -> {
            Main.mode = "Harmony";
            try {
                next();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void next() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/editPlayerPage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
        Main.stage.centerOnScreen();
    }
}
package com.project.controller;

import com.project.Main;
import com.project.model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.IOException;

public class EditPlayerController {
    boolean finish = false;
    @FXML Label message;
    @FXML TextField text;
    @FXML Button valid;
    @FXML VBox startVBox;
    GridPane grid = new GridPane();

    @FXML
    public void initialize() {
        startVBox.setSpacing(10);
        message.setText("How many players? 2, 3, or 4 : ");
        text.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                inputNumberOfPlayers();
            }
        });
        valid.setOnAction(event -> inputNumberOfPlayers());
    }

    @FXML
    public void inputNumberOfPlayers() {
        try {
            int NumberInput = Integer.parseInt(text.getText().replaceAll("[\\n\\t]", "").trim());
            text.clear();
            if (NumberInput >= 2 && NumberInput <= 4) {
                Player.numberOfPlayers = NumberInput;

                int numCols = 2 ;
                int numRows = Player.numberOfPlayers ;

                for (int i = 0 ; i < numCols ; i++) {
                    ColumnConstraints colConstraints = new ColumnConstraints();
                    colConstraints.setHgrow(Priority.SOMETIMES);
                    grid.getColumnConstraints().add(colConstraints);
                }

                for (int i = 0 ; i < numRows ; i++) {
                    RowConstraints rowConstraints = new RowConstraints();
                    rowConstraints.setVgrow(Priority.SOMETIMES);
                    grid.getRowConstraints().add(rowConstraints);
                }
                grid.setPadding(new Insets(0, 200, 0, 200));
                grid.setStyle("-fx-background-color: #ffd458; -fx-border-color: #ff5900");
                startVBox.getChildren().add(grid);

                for (int i = 0 ; i < numRows ; i++) {
                    Label label = new Label("Player" + " " + (i + 1));
                    label.setFont(Font.font(16));
                    label.getStyleClass().add("textLabelClass");
                    grid.add(label, 0, i);
                }

                InitializePlayerName();

            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            AlertWindow.AlertDisplay("Please enter a number between 2 and 4");
            text.requestFocus();
        }
    }

    public void InitializePlayerName() {
        Player.createPlayerTab();
        message.setText("Player " + (Player.count + 1) + " enter your name or \"IA\" to insert an IA player : ");
        text.requestFocus();
        text.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                inputPlayerName();
            }
        });
        valid.setOnAction(event -> inputPlayerName());
    }

    public void inputPlayerName() {
        boolean next = false;
        String nameToInsert = text.getText().replaceAll("[\\n\\t]", "").trim();
        text.clear();
        if (nameToInsert.length() >= 1 && nameToInsert.length() <= 10) {
            boolean available = Player.availableName(nameToInsert);
            if (available) {
                if (!nameToInsert.equals("IA")) {
                    Player.playerTab[Player.count].setPlayerName(nameToInsert);
                    Player.playerTab[Player.count].setType("human");
                } else {
                    Player.iaCounter++;
                    nameToInsert = "IA" + Player.iaCounter;
                    Player.playerTab[Player.count].setPlayerName(nameToInsert);
                    Player.playerTab[Player.count].setType("IA");
                }
                Label label = new Label(nameToInsert);
                label.getStyleClass().add("textLabelClass");
                label.setFont(Font.font(16));
                grid.add(label, 1, Player.count);
                if (Player.count >= (Player.numberOfPlayers - 1)) {
                    next = true;
                }
                Player.count++;
                if (Player.count > (Player.numberOfPlayers - 1)) {
                    message.setText("Go next");
                } else {
                    message.setText("Player " + (Player.count + 1) + " enter your name or \"IA\" to insert an IA player : ");
                }
            } else {
                AlertWindow.AlertDisplay("Sorry, but this name is not available");
            }
        } else {
            AlertWindow.AlertDisplay("Please enter a name between 1 and 10 characters");
        }
        if (next) {
            Button nextButton = new Button("Next");
            nextButton.getStyleClass().add("buttonClass");
            nextButton.prefWidth(80);
            nextButton.prefHeight(40);
            nextButton.setFont(Font.font(20));
            nextButton.setOnAction(event -> {
                try {
                    goNext();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            text.setOnKeyPressed(null);
            valid.setOnAction(null);
            startVBox.getChildren().add(nextButton);
            nextButton.requestFocus();
        } else {
            text.requestFocus();
        }
    }

    @FXML
    public void goNext() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/editCastlePage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
        Main.stage.centerOnScreen();
    }
}
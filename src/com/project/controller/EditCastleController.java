package com.project.controller;

import com.project.Main;
import com.project.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditCastleController {
    @FXML Label instruction;
    @FXML Button startButton;

    @FXML Button pinkButton;
    @FXML Button yellowButton;
    @FXML Button greenButton;
    @FXML Button blueButton;

    @FXML ImageView pinkCastle;
    @FXML ImageView yellowCastle;
    @FXML ImageView greenCastle;
    @FXML ImageView blueCastle;

    @FXML Label pinkName;
    @FXML Label yellowName;
    @FXML Label greenName;
    @FXML Label blueName;

    public static Stage stage = new Stage();
    List<String> colorList;

    @FXML
    public void initialize() {
        instruction.setText("Select your castle");
        colorList = new ArrayList<>();
        colorList.add("pink");
        colorList.add("yellow");
        colorList.add("limegreen");
        colorList.add("royalblue");

        Image pinkImage = new Image("com/project/public/images/CastleImg/pinkCastleImg.png");
        pinkCastle.setImage(pinkImage);
        Image yellowImage = new Image("com/project/public/images/CastleImg/yellowCastleImg.png");
        yellowCastle.setImage(yellowImage);
        Image greenImage = new Image("com/project/public/images/CastleImg/limegreenCastleImg.png");
        greenCastle.setImage(greenImage);
        Image blueImage = new Image("com/project/public/images/CastleImg/royalblueCastleImg.png");
        blueCastle.setImage(blueImage);


        pinkButton.setOnAction(event -> pinkClick());
        yellowButton.setOnAction(event -> yellowClick());
        greenButton.setOnAction(event -> greenClick());
        blueButton.setOnAction(event -> blueClick());
        startButton.setOnAction(event -> {
            try {
                starter(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        startButton.setVisible(false);

        pinkName.setText("");
        yellowName.setText("");
        greenName.setText("");
        blueName.setText("");

        Player.count = 0;
        selectColor();
    }

    public void selectColor() {
        if (Player.count < (Player.numberOfPlayers)) {
            String playerName = Player.playerTab[Player.count].getPlayerName();
            instruction.setText(playerName + ", select your castle");

            if (Player.playerTab[Player.count].getType() == "IA") {
                pinkButton.setOnAction(null);
                yellowButton.setOnAction(null);
                greenButton.setOnAction(null);
                blueButton.setOnAction(null);
                String color = IA.iaSelectColor(colorList);
                final Timeline timeline = new Timeline();
                Duration time = Duration.millis(1000);
                switch (color) {
                    case "pink":
                        timeline.getKeyFrames().add(
                                new KeyFrame(time,
                                        event -> {
                                            pinkName.setText(playerName);
                                            Player.playerTab[Player.count].setPlayerColor(color);
                                            pinkButton.setVisible(false);
                                            colorList.remove(color);
                                            Player.count++;
                                        }
                                )
                        );
                        break;
                    case "yellow":
                        timeline.getKeyFrames().add(
                                new KeyFrame(time,
                                        event -> {
                                            yellowName.setText(playerName);
                                            Player.playerTab[Player.count].setPlayerColor(color);
                                            yellowButton.setVisible(false);
                                            colorList.remove(color);
                                            Player.count++;
                                        }
                                )
                        );
                        break;
                    case "limegreen":
                        timeline.getKeyFrames().add(
                                new KeyFrame(time,
                                        event -> {
                                            greenName.setText(playerName);
                                            Player.playerTab[Player.count].setPlayerColor(color);
                                            greenButton.setVisible(false);
                                            colorList.remove(color);
                                            Player.count++;
                                        }
                                )
                        );
                        break;
                    case "royalblue":
                        timeline.getKeyFrames().add(
                                new KeyFrame(time,
                                        event -> {
                                            blueName.setText(playerName);
                                            Player.playerTab[Player.count].setPlayerColor(color);
                                            blueButton.setVisible(false);
                                            colorList.remove(color);
                                            Player.count++;
                                        }
                                )
                        );
                        break;
                }
                timeline.play();
                timeline.setOnFinished(event -> reactivateButton());
            }
        } else {
            instruction.setText("Start the game!");
            pinkButton.setVisible(false);
            yellowButton.setVisible(false);
            greenButton.setVisible(false);
            blueButton.setVisible(false);
            startButton.setVisible(true);
        }
    }

    public void pinkClick() {
        String color = "pink";
        pinkName.setText(Player.playerTab[Player.count].getPlayerName());
        Player.playerTab[Player.count].setPlayerColor(color);
        pinkButton.setVisible(false);
        colorList.remove(color);
        Player.count++;
        selectColor();
    }
    public void yellowClick() {
        String color = "yellow";
        yellowName.setText(Player.playerTab[Player.count].getPlayerName());
        Player.playerTab[Player.count].setPlayerColor(color);
        yellowButton.setVisible(false);
        colorList.remove(color);
        Player.count++;
        selectColor();
    }
    public void greenClick() {
        String color = "limegreen";
        greenName.setText(Player.playerTab[Player.count].getPlayerName());
        Player.playerTab[Player.count].setPlayerColor(color);
        greenButton.setVisible(false);
        colorList.remove(color);
        Player.count++;
        selectColor();
    }
    public void blueClick() {
        String color = "royalblue";
        blueName.setText(Player.playerTab[Player.count].getPlayerName());
        Player.playerTab[Player.count].setPlayerColor(color);
        blueButton.setVisible(false);
        colorList.remove(color);
        Player.count++;
        selectColor();
    }
    public void reactivateButton() {
        pinkButton.setOnAction(event -> pinkClick());
        yellowButton.setOnAction(event -> yellowClick());
        greenButton.setOnAction(event -> greenClick());
        blueButton.setOnAction(event -> blueClick());
        selectColor();
    }

    @FXML
    public void starter(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/game.fxml"));
        Parent root = fxmlLoader.load();
        GameController c = fxmlLoader.getController();
        Scene scene = new Scene(root);
        scene.setOnMouseMoved((event) -> c.mouseMoved(event));
        scene.setOnMouseDragged((event)->c.mouseMoved(event));
        Main.stage.setScene( scene );
        Main.stage.centerOnScreen();

    }
}
package com.project.controller;

import com.project.Main;
import com.project.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    @FXML Pane gameBoard;

    @FXML GridPane gridBoard1;
    @FXML GridPane gridBoard2;
    @FXML GridPane gridBoard3;
    @FXML GridPane gridBoard4;

    @FXML Pane throwPane;

    @FXML Label modeLabel;
    @FXML Label messageLabel;
    @FXML Label turnCounterLabel;
    @FXML Label player1NameLabel;
    @FXML Label player2NameLabel;
    @FXML Label player3NameLabel;
    @FXML Label player4NameLabel;

    private Rectangle trace;
    private List<Pane[][]> cellTabList = new ArrayList<>();
    public static List<GridPane> gridBoardList = new ArrayList<>();
    private List<Label> playerNameLabelList = new ArrayList<>();


    private boolean movingPiece = false;
    private boolean isFirstTurn;
    private boolean isLastTurn;
    private String phase;
    private int rotate = 0;
    private double dominoWidth = 80;
    private double dominoHeight = 40;
    private double firstColumnDominoLayoutX = 90;
    private double firstColumnDominoLayoutY = 160;
    private double kingOffsetLayoutX = 28;
    private double kingOffsetLayoutY = -12;

    private double iaActionDuration = 1000;
    private final InnerShadow shadow = new InnerShadow();

    private Pane currCell;
    private King currKing;
    private Domino currDomino;
    private int currPlayerIndex;
    private int totalDomino;

    private Domino selectedDomino;
    private Domino iaSelectedDomino;

    @FXML
    public void initialize() throws FileNotFoundException {
        modeLabel.setText(" - " + Main.mode);

        Castle.createCastleTab();
        King.createKingTab();
        Board.createBoardList();
        Domino.createDominoList();

        gridBoardList.add(gridBoard1);
        gridBoardList.add(gridBoard2);
        if (Player.numberOfPlayers >= 3) {
            gridBoardList.add(gridBoard3);
            gridBoard3.setVisible(true);
            if (Player.numberOfPlayers >= 4) {
                gridBoardList.add(gridBoard4);
                gridBoard4.setVisible(true);
            }
        }

        playerNameLabelList.add(player1NameLabel);
        playerNameLabelList.add(player2NameLabel);
        if (Player.numberOfPlayers >= 3) {
            playerNameLabelList.add(player3NameLabel);
            player3NameLabel.setVisible(true);
            if (Player.numberOfPlayers >= 4) {
                playerNameLabelList.add(player4NameLabel);
                player4NameLabel.setVisible(true);
            }
        }

        for (int i = 0; i < Player.numberOfPlayers; i++) {
            playerNameLabelList.get(i).setText(Player.playerTab[i].getPlayerName());
            //playerNameLabelList.get(i).setStyle("-fx-text-fill:" + Player.playerTab[i].getPlayerColor());
            GridPane gridBoard = gridBoardList.get(i);
            gridBoard.setVisible(true);
            Pane[][] cellTab = new Pane[9][9];
            cellTabList.add(cellTab);
            for (int j = 0; j < cellTab.length; j++) {
                for (int k = 0; k < cellTab[j].length; k++) {
                    for (Node node : gridBoard.getChildren()) {
                        Integer rowIndex = gridBoard.getRowIndex(node);
                        Integer columnIndex = gridBoard.getColumnIndex(node);
                        if (rowIndex == null) {
                            rowIndex = 0;
                        }
                        if (columnIndex == null) {
                            columnIndex = 0;
                        }
                        if (rowIndex == j && columnIndex == k) {

                            cellTab[j][k] = (Pane) node;
                            cellTab[j][k].setStyle("-fx-background-color:" + Board.boardList.get(i).getBoardColor());
                        }
                    }
                }
            }
        }

        for (int i = 0; i < Castle.castleTab.length; i++) {
            Castle castle = Castle.castleTab[i];
            Board.boardList.get(i).insertCastle();
            castle.setFitWidth(40);
            castle.setFitHeight(40);
            Image image = new Image("com/project/public/images/CastleImg/" + castle.getCastleColor() + "CastleImg.png");
            castle.setImage(image);
            cellTabList.get(i)[4][4].getChildren().add(castle);
            castle.setLayoutX(0);
            castle.setLayoutY(0);
        }

        for (int i = 0; i < King.numberOfKing; i++) {
            King king = King.kingTab[i];
            king.setWidth(25);
            king.setHeight(30);
            Image image = new Image("com/project/public/images/KingImg/" + king.getKingColor() + "KingImg.png");
            ImagePattern imagePattern = new ImagePattern(image);
            king.setFill(imagePattern);
            gameBoard.getChildren().add(king);
            king.setLayoutX(20);
            king.setLayoutY(200);
        }

        Domino.cutDominoList();
        for (int i = 0; i < Domino.dominoList.size(); i++) {
            Domino domino = Domino.dominoList.get(i);
            domino.setWidth(80);
            domino.setHeight(40);
            domino.setArcWidth(5);
            domino.setArcHeight(5);
            domino.setStroke(Color.valueOf("black"));
            Image image = new Image("com/project/public/images/DominoImg/" + domino.getDominoNumber() + ".png");
            ImagePattern imagePattern = new ImagePattern(image);
            domino.setFill(imagePattern);
            gameBoard.getChildren().add(domino);
            domino.setLayoutX(350);
            domino.setLayoutY(60);
        }
        Player.count = 0;
        totalDomino = Domino.dominoList.size();
        isFirstTurn = true;
        isLastTurn = false;
        King.shuffleKingTab();
        Pick.createPickList();
        Pick.sortPickList();
        placeCurrentPick();
    }

    public void placeCurrentPick() {
        final Timeline timeline = new Timeline();
        for (int i = 0; i < Pick.pickList.size(); i++) {
            Domino domino = Pick.pickList.get(i);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(1000),
                            new KeyValue(domino.layoutXProperty(), firstColumnDominoLayoutX),
                            new KeyValue(domino.layoutYProperty(), firstColumnDominoLayoutY + i*2*dominoHeight)
                    ));
            timeline.setCycleCount(1);
        }
        timeline.play();
        timeline.setOnFinished(evt -> {
            if (isFirstTurn) {
                Turn1();
            }
        });
        if (!isFirstTurn) {
            final Timeline timeline1 = new Timeline();
            for (int i = 0; i < King.numberOfKing; i++) {
                King king = King.kingTab[i];
                timeline1.getKeyFrames().add(
                        new KeyFrame(Duration.millis(1000),
                                new KeyValue(king.layoutXProperty(), king.getLayoutX()-2*dominoWidth)
                        ));
                timeline1.setCycleCount(1);
            }
            timeline1.play();
            if (isLastTurn) {
                timeline1.setOnFinished(evt -> turn());
            }
        }

    }
    public void placeNextPick() {
        final Timeline timeline = new Timeline();
        for (int i = 0; i < Pick.pickList.size(); i++) {
            Domino domino = Pick.pickList.get(i);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(1000),
                            new KeyValue(domino.layoutXProperty(), firstColumnDominoLayoutX + 2*dominoWidth),
                            new KeyValue(domino.layoutYProperty(), firstColumnDominoLayoutY + i*2*dominoHeight)
                    ));
        }
        timeline.play();
        timeline.setOnFinished(evt -> turn());
    }
    public void Turn1() {
        turnCounterLabel.setText("Turn " + (totalDomino/King.numberOfKing - Domino.dominoList.size()/King.numberOfKing) + "/" + (totalDomino/King.numberOfKing));
        if (Player.count < King.numberOfKing) {
            currKing = King.kingTab[Player.count];
            currKing.toFront();
            selectionPhase();
        } else {
            turnLooper();
        }
    }

    public void turnLooper() {
        Player.count = 0;
        Pick.currentPickedMap.get("King").addAll(Pick.nextPickedMap.get("King"));
        Pick.nextPickedMap.get("King").clear();

        Pick.currentPickedMap.get("Domino").addAll(Pick.nextPickedMap.get("Domino"));
        Pick.nextPickedMap.get("Domino").clear();

        Pick.currentPickedMap.get("Index").addAll(Pick.nextPickedMap.get("Index"));
        Pick.nextPickedMap.get("Index").clear();
        if (!isLastTurn) {
            if (Domino.dominoList.size() >= King.numberOfKing) {
                if (!isFirstTurn) {
                    placeCurrentPick();
                }
                turnCounterLabel.setText("Turn " + (totalDomino / King.numberOfKing - Domino.dominoList.size() / King.numberOfKing) + "/" + (totalDomino / King.numberOfKing));
                Pick.createPickList();
                Pick.sortPickList();
                placeNextPick();
            } else {
                turnCounterLabel.setText("Turn " + (totalDomino / King.numberOfKing - Domino.dominoList.size() / King.numberOfKing) + "/" + (totalDomino / King.numberOfKing));
                isLastTurn = true;
                placeCurrentPick();
            }
        } else {
            end();
        }
    }

    public void turn() {
        isFirstTurn = false;
        if (Player.count < King.numberOfKing) {
            placementPhase();
        } else {
            turnLooper();
        }
    }

    public void selectionPhase() {
        if (!isLastTurn) {
            phase = "selection";
            messageLabel.setText(currKing.getKingPlayerName() + ", select a domino");
            currKing.setStroke(Color.valueOf("red"));

            for (int i = 0; i < Player.playerTab.length; i++) {
                if (Player.playerTab[i].getPlayerName() == currKing.getKingPlayerName()) {
                    currPlayerIndex = i;
                }
            }
            Board currentBoard =  Board.boardList.get(currPlayerIndex);
            currentBoard.editAvailableRangeList();
            if (currKing.getKingPlayerType() == "human") {
                currKing.setOnMousePressed(this::startMovingKing);
            } else {
                int[] iaLocationTab;
                iaLocationTab = IA.iaSelectLocation(currentBoard);
                currKing.setIaLocationTab(iaLocationTab);
                iaSelectedDomino = Pick.pickList.get(iaLocationTab[0]);
                int rotation = iaLocationTab[1];
                int row = iaLocationTab[2];
                int column = iaLocationTab[3];
                if (rotation != -1) {
                    Board.boardList.get(currPlayerIndex).insertDomino(iaSelectedDomino, row, column, rotation);
                }
                final Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(iaActionDuration),
                                new KeyValue(currKing.layoutXProperty(), iaSelectedDomino.getLayoutX() + kingOffsetLayoutX),
                                new KeyValue(currKing.layoutYProperty(), iaSelectedDomino.getLayoutY() + kingOffsetLayoutY)
                        )
                );
                timeline.play();
                timeline.setOnFinished(event -> {
                    Pick.nextPickedMap.get("King").add(currKing);
                    Pick.nextPickedMap.get("Domino").add(iaSelectedDomino);
                    Pick.nextPickedMap.get("Index").add(Pick.pickList.indexOf(iaSelectedDomino));

                    Player.count++;
                    currKing.setStroke(null);

                    if (isFirstTurn) {
                        Turn1();
                    } else {
                        turn();
                    }
                });
            }
        } else {
            Player.count++;
            turn();
        }
    }

    public void placementPhase() {
        phase = "placement";


        int index = 0;
        int min = (int) Pick.currentPickedMap.get("Index").get(0);
        for (int i = 0; i < Pick.currentPickedMap.get("Index").size(); i++){
            if ((int) Pick.currentPickedMap.get("Index").get(i) < min) {
                min = (int) Pick.currentPickedMap.get("Index").get(i);
                index = i;
            }
        }

        currKing = (King) Pick.currentPickedMap.get("King").get(index);
        King.kingTab[min] = currKing;
        currDomino = (Domino) Pick.currentPickedMap.get("Domino").get(index);
        Pick.currentPickedMap.get("King").remove(index);
        Pick.currentPickedMap.get("Domino").remove(index);
        Pick.currentPickedMap.get("Index").remove(index);

        Player currentPlayer = new Player();
        for (int i = 0; i < Player.playerTab.length; i++) {
            if (Player.playerTab[i].getPlayerName() == currKing.getKingPlayerName()) {
                currentPlayer = Player.playerTab[i];
                currPlayerIndex = i;
            }
        }
        Board currentBoard =  Board.boardList.get(currPlayerIndex);
        currentBoard.editAvailableRangeList();

        messageLabel.setText(currentPlayer.getPlayerName() + ", place your domino");
        gridBoardList.get(currPlayerIndex).setStyle("-fx-border-color: red");
        currDomino.setStroke(Color.valueOf("red"));
        if (currKing.getKingPlayerType() == "human") {
            currDomino.setOnMousePressed(this::clickDefiner);
        } else {
            int[] iaLocationTab = currKing.getIaLocationTab();
            Pane selectedCell = null;
            if (iaLocationTab[1] != -1) {
                int rotation = iaLocationTab[1];
                int row = iaLocationTab[2];
                int column = iaLocationTab[3];
                for (int i = 0; i < cellTabList.get(currPlayerIndex).length; i++) {
                    for (int j = 0; j < cellTabList.get(currPlayerIndex)[i].length; j++) {
                        Pane cell = cellTabList.get(currPlayerIndex)[i][j];
                        if (row == i && column == j) {
                            selectedCell = cell;
                        }
                    }
                }
                currDomino.setRotate(currDomino.getRotate()+90*rotation);
                Point2D cellToScene = selectedCell.getParent().localToScene(selectedCell.getLayoutX(),selectedCell.getLayoutY());
                Point2D cellToPlan = gameBoard.sceneToLocal(cellToScene);
                final Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(iaActionDuration),
                                new KeyValue(currDomino.layoutXProperty(), cellToPlan.getX()),
                                new KeyValue(currDomino.layoutYProperty(), cellToPlan.getY())
                        )
                );
                timeline.play();
                Pane finalSelectedCell = selectedCell;
                Pane finalSelectedCell1 = selectedCell;
                timeline.setOnFinished(event -> {
                    finalSelectedCell.getChildren().add(currDomino);
                    currDomino.setLayoutX(0);
                    currDomino.setLayoutY(0);
                    switch (rotation) {
                        case 0:
                            currDomino.setX(0);
                            currDomino.setY(0);
                            break;
                        case 1:
                            currDomino.setX(-currDomino.getWidth()/4);
                            currDomino.setY(currDomino.getHeight()/2);
                            break;
                        case 2:
                            currDomino.setX(-currDomino.getWidth()/2);
                            currDomino.setY(0);
                            break;
                        case 3:
                            currDomino.setX(-currDomino.getWidth()/4);
                            currDomino.setY(-currDomino.getHeight()/2);
                            break;
                    }
                    currDomino.setStroke(Color.valueOf("black"));
                    finalSelectedCell1.toFront();
                    gridBoardList.get(currPlayerIndex).setStyle("-fx-border-color: black");
                    selectionPhase();
                });
            } else {
                selectedCell = throwPane;
                final Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(iaActionDuration),
                                new KeyValue(currDomino.layoutXProperty(), selectedCell.getLayoutX()),
                                new KeyValue(currDomino.layoutYProperty(), selectedCell.getLayoutY())
                        )
                );
                timeline.play();
                timeline.setOnFinished(event -> {
                    gameBoard.getChildren().remove(currDomino);
                    gridBoardList.get(currPlayerIndex).setStyle("-fx-border-color: black");
                    selectionPhase();
                });
            }
        }
    }

    public void end() {
        gameBoard.getChildren().clear();

        VBox scoreBoard = new VBox();
        gameBoard.getChildren().add(scoreBoard);
        scoreBoard.setLayoutX(50);
        scoreBoard.setLayoutY(30);
        scoreBoard.prefWidth(450);
        scoreBoard.prefHeight(650);
        scoreBoard.setAlignment(Pos.TOP_CENTER);

        Label endGameLabel = new Label("Game is finished");
        scoreBoard.getChildren().add(endGameLabel);
        endGameLabel.getStyleClass().add("textLabelClass");
        endGameLabel.setFont(Font.font(20));
        endGameLabel.setPadding(new Insets(0, 0, 20, 0));

        List<Score> scoreList = Score.createScoreList(Player.playerTab);
        for (int i = 0; i < Board.boardList.size(); i++) {
            scoreList.get(i).scoreCalculation(Board.boardList.get(i));
        }
        List<Label> scoreLabelList = Score.printLeaderBoard(Board.boardList, scoreList);

        Insets insets = new Insets(10, 0, 10, 0);
        for (int i = 0; i < scoreLabelList.size(); i++) {
            Label scoreLabel = scoreLabelList.get(i);
            scoreLabel.getStyleClass().add("textLabelClass");
            scoreLabel.setFont(Font.font(16));
            scoreLabel.setPadding(insets);
            scoreBoard.getChildren().add(scoreLabel);
        }
    }

    public void startMovingKing(MouseEvent evt) {
        currKing.setOnMousePressed(null);
        if (!movingPiece) {
            if (evt.getButton().equals(MouseButton.PRIMARY)) {
                currKing.setOnMouseDragged(this::moveKing);
                currKing.setOnMouseReleased(this::finishMovingKing);

                currKing.setOpacity(0.4d);

                trace = new Rectangle(currKing.getWidth(), currKing.getHeight(), Color.valueOf("F4EEDB"));
                gameBoard.getChildren().add(trace);
                trace.setLayoutX(currKing.getLayoutX());
                trace.setLayoutY(currKing.getLayoutY());
                currKing.toFront();

                movingPiece = true;
            } else {
                currKing.setOnMousePressed(this::startMovingKing);
            }
        }
    }

    @FXML
    public void moveKing(MouseEvent evt) {
        Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
        Point2D mousePoint_p = currKing.localToParent(mousePoint);

        currKing.relocate(mousePoint_p.getX() - currKing.getWidth() / 2, mousePoint_p.getY() - currKing.getHeight() / 2);
    }

    @FXML
    public void finishMovingKing(MouseEvent evt) {
        if (evt.getButton().equals(MouseButton.PRIMARY)) {
            currKing.setOnMouseDragged(null);
            currKing.setOnMouseReleased(null);

            Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
            Point2D mousePointScene = currKing.localToScene(mousePoint);

            Domino domino = selectDomino(mousePointScene.getX(), mousePointScene.getY());

            boolean check = false;
            if (domino != null) {
                if (!Pick.nextPickedMap.get("Domino").contains(domino)) {
                    Pick.nextPickedMap.get("King").add(currKing);
                    Pick.nextPickedMap.get("Domino").add(domino);
                    Pick.nextPickedMap.get("Index").add(Pick.pickList.indexOf(domino));

                    currKing.setLayoutX(domino.getLayoutX() + kingOffsetLayoutX);
                    currKing.setLayoutY(domino.getLayoutY() + kingOffsetLayoutY);
                    currKing.setOpacity(1.0d);

                    check = true;
                    Player.count ++;
                }

            }
            if (!check) {
                final Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(200),
                                new KeyValue(currKing.layoutXProperty(), trace.getLayoutX()),
                                new KeyValue(currKing.layoutYProperty(), trace.getLayoutY()),
                                new KeyValue(currKing.opacityProperty(), 1.0d)
                        )
                );
                timeline.play();
                timeline.setOnFinished(event -> currKing.setOnMousePressed(this::startMovingKing));
            }
            gameBoard.getChildren().remove(trace);
            movingPiece = false;
            if (check) {
                currKing.setStroke(null);
                if (isFirstTurn) {
                    Turn1();
                } else {
                    turn();
                }
            }
        }
    }


    @FXML
    public void clickDefiner(MouseEvent evt) {
        currDomino.setOnMousePressed(null);
        if (!movingPiece) {
            if (evt.getButton().equals(MouseButton.PRIMARY)) {
                currDomino.setOnMouseDragged(this::movePiece);
                currDomino.setOnMouseReleased(this::finishMovingPiece);
                startMovingPiece(evt);
            } else if (evt.getButton().equals(MouseButton.SECONDARY)) {
                currDomino.setOnMouseReleased(this::finishMovingPiece);
                rotatePiece();
            } else {
                currDomino.setOnMousePressed(this::clickDefiner);
            }
        }
    }

    public void rotatePiece() {
        currDomino.setOnMousePressed(this::clickDefiner);
        currDomino.setRotate(currDomino.getRotate()+90);
        if (currDomino.getRotate() == 360) {
            currDomino.setRotate(0);
        }
        rotate = (int) currDomino.getRotate()/90;
        currDomino.setOpacity(1.0d);
    }

    @FXML
    public void startMovingPiece(MouseEvent evt) {
        if (evt.getButton().equals(MouseButton.PRIMARY)) {
            currDomino.setOpacity(0.4d);

            trace = new Rectangle();
            gameBoard.getChildren().add(trace);
            trace.setLayoutX(currDomino.getLayoutX());
            trace.setLayoutY(currDomino.getLayoutY());
            trace.setHeight(currDomino.getHeight());
            trace.setWidth(currDomino.getWidth());
            trace.setStyle("-fx-fill: F4EEDB;");
            trace.toBack();

            movingPiece = true;
        }
    }

    @FXML
    public void movePiece(MouseEvent evt) {
        Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
        Point2D mousePoint_p = currDomino.localToParent(mousePoint);

        switch (rotate) {
            case 0:
                currDomino.relocate(mousePoint_p.getX() - currDomino.getWidth() / 4, mousePoint_p.getY() - currDomino.getHeight() / 2);
                break;
            case 1:
                currDomino.relocate(mousePoint_p.getX() - currDomino.getWidth() / 2, mousePoint_p.getY());
                break;
            case 2:
                currDomino.relocate(mousePoint_p.getX() - (currDomino.getWidth() * 3 / 4), mousePoint_p.getY() - currDomino.getHeight() / 2);
                break;
            case 3:
                currDomino.relocate(mousePoint_p.getX() - currDomino.getWidth() / 2, mousePoint_p.getY() - currDomino.getHeight());
                break;
        }
    }

    @FXML
    public void finishMovingPiece(MouseEvent evt) {
        if (evt.getButton().equals(MouseButton.PRIMARY)) {
            currDomino.setOnMouseDragged(null);
            currDomino.setOnMouseReleased(null);

            Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
            Point2D mousePointScene = currDomino.localToScene(mousePoint);

            Pane pane = pickCell(mousePointScene.getX(), mousePointScene.getY());

            boolean check = false;
            if (pane != null) {
                boolean checkBoard = false;
                int row = 0;
                int column = 0;
                for (int i = 0; i < cellTabList.get(currPlayerIndex).length; i++) {
                    for (int j = 0; j < cellTabList.get(currPlayerIndex)[i].length; j++) {
                        Pane cell = cellTabList.get(currPlayerIndex)[i][j];
                        if (pane == cell) {
                            checkBoard = true;
                            row = i;
                            column = j;
                        }
                    }
                }
                if (checkBoard) {
                    switch (Board.boardList.get(currPlayerIndex).checkInsert(currDomino, row, column, rotate)) {
                        case 0:
                            AlertWindow.AlertDisplay("This location is out of range.");
                            break;
                        case 1:
                            AlertWindow.AlertDisplay("This location is not valid, please check the connections.");
                            break;
                        case 2:
                            pane.getChildren().add(currDomino);
                            currDomino.setLayoutX(0);
                            currDomino.setLayoutY(0);
                            switch (rotate) {
                                case 0:
                                    currDomino.setX(0);
                                    currDomino.setY(0);
                                    break;
                                case 1:
                                    currDomino.setX(-currDomino.getWidth()/4);
                                    currDomino.setY(currDomino.getHeight()/2);
                                    break;
                                case 2:
                                    currDomino.setX(-currDomino.getWidth()/2);
                                    currDomino.setY(0);
                                    break;
                                case 3:
                                    currDomino.setX(-currDomino.getWidth()/4);
                                    currDomino.setY(-currDomino.getHeight()/2);
                                    break;
                            }
                            currDomino.setOpacity(1.0d);
                            currDomino.setStroke(Color.valueOf("black"));
                            pane.toFront();
                            check = true;
                            Board.boardList.get(currPlayerIndex).insertDomino(currDomino, row, column, rotate);
                            break;
                    }
                } else if (pane == throwPane){
                    gameBoard.getChildren().remove(currDomino);
                    check = true;
                } else {
                    AlertWindow.AlertDisplay("This is not your board.");
                }
            }
            rotate = 0;
            if (!check) {
                currDomino.setRotate(trace.getRotate());
                final Timeline timeline = new Timeline();
                timeline.setCycleCount(1);
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(200),
                                new KeyValue(currDomino.layoutXProperty(), trace.getLayoutX()),
                                new KeyValue(currDomino.layoutYProperty(), trace.getLayoutY()),
                                new KeyValue(currDomino.opacityProperty(), 1.0d)
                        )
                );
                timeline.play();
                timeline.setOnFinished(event -> currDomino.setOnMousePressed(this::clickDefiner));
            }
            gameBoard.getChildren().remove(trace);
            movingPiece = false;
            if (check) {
                pane.setEffect(null);
                gridBoardList.get(currPlayerIndex).setStyle("-fx-border-color: black");
                selectionPhase();
            }
        }
    }

    public void mouseMoved(MouseEvent evt) {
        if (phase == "selection") {
            if( currCell != null ) {
                currCell.setEffect( null );
            }

            Domino domino = selectDomino(evt);
            if( domino == null ) {

                if( selectedDomino != null ) {
                    selectedDomino.setEffect( null );
                }

                selectedDomino = null;
                return;
            }

            if( domino != selectedDomino) {

                if( selectedDomino != null ) {
                    selectedDomino.setEffect( null );
                }

                selectedDomino = domino;

                if( selectedDomino != null ) {
                    selectedDomino.setEffect( shadow );
                }
            }
        } else {
            if( selectedDomino != null ) {
                selectedDomino.setEffect( null );
            }

            Pane pickedCell = pickCell(evt);
            if( pickedCell == null ) {

                if( currCell != null ) {
                    currCell.setEffect( null );
                }

                currCell = null;
                return;
            }

            if( pickedCell != currCell) {

                if( currCell != null ) {
                    currCell.setEffect( null );
                }

                currCell = pickedCell;

                if( currCell != null ) {
                    currCell.setEffect( shadow );
                }
            }
        }
    }
    private Domino selectDomino(MouseEvent evt) {
        return selectDomino(evt.getSceneX(), evt.getSceneY());
    }
    private Domino selectDomino(double sceneX, double sceneY) {
        Domino seletedDomino = null;

        for (Domino domino : Pick.pickList) {
            Point2D mousePoint = new Point2D(sceneX, sceneY);
            Point2D mpLocal = domino.sceneToLocal(mousePoint);
            if (domino.contains(mpLocal)) {
                seletedDomino = domino;
                break;
            }
        }
        return seletedDomino;
    }

    private Pane pickCell(MouseEvent evt) {
        return pickCell(evt.getSceneX(), evt.getSceneY());
    }
    private Pane pickCell(double sceneX, double sceneY) {
        Pane pickedCell = null;
        boolean inTab = false;
        for (Pane[][] cellTab : cellTabList) {
            for (Pane[] row : cellTab) {
                for (Pane cell : row) {
                    Point2D mousePoint = new Point2D(sceneX, sceneY);
                    Point2D mpLocal = cell.sceneToLocal(mousePoint);
                    if (cell.contains(mpLocal)) {
                        pickedCell = cell;
                        inTab = true;
                        break;
                    }
                }
            }
        }
        if (!inTab) {
            Point2D mousePoint = new Point2D(sceneX, sceneY);
            Point2D mpLocal = throwPane.sceneToLocal(mousePoint);
            if (throwPane.contains(mpLocal)) {
                pickedCell = throwPane;
            }
        }
        return pickedCell;
    }
}

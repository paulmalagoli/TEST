package com.project.model;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class King extends Rectangle {
    String kingPlayerName;
    String kingColor;
    String kingPlayerType;
    int kingNumber;
    int[] iaLocationTab = new int[4];
    public static int numberOfKing;
    public static King[] kingTab;

    public void setKingPlayerName(String kingPlayerName) {
        this.kingPlayerName = kingPlayerName;
    }
    public void setKingColor(String kingColor) {
        this.kingColor = kingColor;
    }
    public void setKingPlayerType(String kingPlayerType) {
        this.kingPlayerType = kingPlayerType;
    }
    public void setKingNumber(int kingNumber) {
        this.kingNumber = kingNumber;
    }
    public void setIaLocationTab(int[] iaLocationTab) {
        this.iaLocationTab = iaLocationTab;
    }

    public int[] getIaLocationTab() {
        return iaLocationTab;
    }
    public String getKingPlayerName() {
        return this.kingPlayerName;
    }
    public String getKingColor() {
        return this.kingColor;
    }
    public String getKingPlayerType() {
        return kingPlayerType;
    }



    public static void createKingTab() {
        if (Player.numberOfPlayers == 2) {
            numberOfKing = 4;
            kingTab = new King[numberOfKing];
            for (int i = 0; i < 2; i++) {
                kingTab[i] = new King();
                kingTab[i].setKingPlayerName(Player.playerTab[i].getPlayerName());
                kingTab[i].setKingColor(Player.playerTab[i].getPlayerColor());
                kingTab[i].setKingPlayerType(Player.playerTab[i].getType());
                kingTab[i].setKingNumber(i);
            }
            for (int i = 2; i < 4; i++) {
                kingTab[i] = new King();
                kingTab[i].setKingPlayerName(Player.playerTab[i-2].getPlayerName());
                kingTab[i].setKingColor(Player.playerTab[i-2].getPlayerColor());
                kingTab[i].setKingPlayerType(Player.playerTab[i-2].getType());
                kingTab[i].setKingNumber(i);
            }
        } else {
            numberOfKing = Player.numberOfPlayers;
            kingTab = new King[numberOfKing];
            for (int i = 0; i < Player.playerTab.length; i++) {
                kingTab[i] = new King();
                kingTab[i].setKingPlayerName(Player.playerTab[i].getPlayerName());
                kingTab[i].setKingColor(Player.playerTab[i].getPlayerColor());
                kingTab[i].setKingPlayerType(Player.playerTab[i].getType());
                kingTab[i].setKingNumber(i);
            }
        }
    }


    public static void shuffleKingTab() {
        Random random = new Random();
        for (int i=0; i<kingTab.length; i++) {
            int randomPosition = random.nextInt(kingTab.length);
            King temp = kingTab[i];
            kingTab[i] = kingTab[randomPosition];
            kingTab[randomPosition] = temp;
        }
    }
}

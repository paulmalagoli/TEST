package com.project.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    String playerName;
    String playerColor;
    String type;
    public static int numberOfPlayers;
    public static Player[] playerTab;
    public static int count = 0;
    public static int iaCounter = 0;
    List<int[]> iaLocationTabList = new ArrayList<>();
    int[] iaLocationTab = new int[4];

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getPlayerName() {
        return this.playerName;
    }
    public String getPlayerColor() {
        return this.playerColor;
    }
    public String getType() {
        return type;
    }


    public static void createPlayerTab() {
        playerTab = new Player[numberOfPlayers];
        for (int i = 0; i < playerTab.length; i++) {
            playerTab[i] = new Player();
        }
    }

    public static boolean availableName(String nameToInsert) {
        boolean available = true;
        for (Player player : Player.playerTab) {
            if (nameToInsert.equals(player.getPlayerName())) {
                available = false;
            }
        }
        return available;
    }
}

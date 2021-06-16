package com.project.model;

import javafx.scene.image.ImageView;

public class Castle extends ImageView {
    String castlePlayerName;
    String castleColor;
    public static Castle[] castleTab;

    public void setCastlePlayerName(String kingPlayerName) {
        this.castlePlayerName = kingPlayerName;
    }
    public void setCastleColor(String castleColor) {
        this.castleColor = castleColor;
    }

    public String getCastleColor() {
        return this.castleColor;
    }

    public static void createCastleTab() {
        castleTab = new Castle[Player.numberOfPlayers];
        for (int i = 0; i < Player.numberOfPlayers; i++) {
            castleTab[i] = new Castle();
            castleTab[i].setCastlePlayerName(Player.playerTab[i].getPlayerName());
            castleTab[i].setCastleColor(Player.playerTab[i].getPlayerColor());
        }
    }
}

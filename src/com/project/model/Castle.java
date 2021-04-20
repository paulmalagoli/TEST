package com.project.model;

public class Castle {
    String castlePlayerName;

    public void setCastlePlayerName(String kingPlayerName) {
        this.castlePlayerName = kingPlayerName;
    }

    public String getCastlePlayerName() {
        return this.castlePlayerName;
    }

    public static Castle[] createCastleTab(Player[] playerTab) {
        Castle[] castleTab = new Castle[Player.numberOfPlayers];
        for (int i = 0; i < castleTab.length; i++) {
            castleTab[i] = new Castle();
            castleTab[i].setCastlePlayerName(playerTab[i].getPlayerName());
        }
        return castleTab;
    }
}

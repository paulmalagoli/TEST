package com.project.model;

import java.util.Random;

public class King {
    String kingPlayerName;
    public static int numberOfKing;

    public void setKingPlayerName(String kingPlayerName) {
        this.kingPlayerName = kingPlayerName;
    }

    public String getKingPlayerName() {
        return this.kingPlayerName;
    }


    public static King[] createKingTab(Player[] playerTab) {
        if (Player.numberOfPlayers == 2) {
            numberOfKing = 4;
            King[] kingTab = new King[numberOfKing];
            for (int i = 0; i < kingTab.length; i++) {
                kingTab[i] = new King();
            }
            for (int i = 0; i < 2; i++) {
                kingTab[i].setKingPlayerName(playerTab[i].getPlayerName());
            }
            for (int i = 2; i < 4; i++) {
                kingTab[i].setKingPlayerName(playerTab[i-2].getPlayerName());
            }
            return kingTab;
        } else {
            numberOfKing = Player.numberOfPlayers;
            King[] kingTab = new King[numberOfKing];
            for (int i = 0; i < kingTab.length; i++) {
                kingTab[i] = new King();
            }
            for (int i = 0; i < playerTab.length; i++) {
                kingTab[i].setKingPlayerName(playerTab[i].getPlayerName());
            }
            return kingTab;
        }
    }


        public static void shuffleKingTab(King[] kingTab) {
            Random random = new Random();
            for (int i=0; i<kingTab.length; i++) {
                int randomPosition = random.nextInt(kingTab.length);
                King temp = kingTab[i];
                kingTab[i] = kingTab[randomPosition];
                kingTab[randomPosition] = temp;
            }
        }
}

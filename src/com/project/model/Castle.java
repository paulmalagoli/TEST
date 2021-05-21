package com.project.model;

import java.util.List;
import java.util.Scanner;

public class Castle {
    String castlePlayerName;
    String castleColor;

    public void setCastlePlayerName(String kingPlayerName) {
        this.castlePlayerName = kingPlayerName;
    }
    public void setCastleColor(String castleColor) {
        this.castleColor = castleColor;
    }

    public String getCastlePlayerName() {
        return this.castlePlayerName;
    }
    public String getCastleColor() {
        return this.castleColor;
    }

    public static Castle[] createCastleTab(Player[] playerTab) {
        Castle[] castleTab = new Castle[Player.numberOfPlayers];
        for (int i = 0; i < castleTab.length; i++) {
            castleTab[i] = new Castle();
            castleTab[i].setCastlePlayerName(playerTab[i].getPlayerName());
        }
        return castleTab;
    }

    public static void selectCastle(Player[] playerTab, Castle[] castleTab, List<Board> boardList) {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        String[] colorTab = {"Pink", "Yellow", "Green", "Blue"};
        String color;
        Castle castle = new Castle();
        while (count < playerTab.length) {
            try {
                castle = castleTab[count];
                System.out.println();
                System.out.print(playerTab[count].getPlayerName() + ", select a castle, enter 1 for pink, 2 for yellow, 3 for green or 4 for blue : ");
                int numberInput;
                if (playerTab[count].getType() != "IA") {
                    numberInput = Integer.parseInt(sc.nextLine());
                } else {
                    numberInput = IA.iaSelectCastle (colorTab);
                }
                if (numberInput >= 1 && numberInput <= 4 && colorTab[numberInput-1] != null) {
                    color = colorTab[numberInput-1];
                    castle.setCastleColor(color);
                    playerTab[count].setPlayerColor(color);
                    Board board = boardList.get(count);
                    board.insertCastle();
                    colorTab[numberInput-1] = null;
                    count++;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.print("Please enter a valid number");
            }
        }
    }
}

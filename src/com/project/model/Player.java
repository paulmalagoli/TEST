package com.project.model;

import java.util.Scanner;

public class Player {
    String playerName;
    String playerColor;
    String type;
    public static int numberOfPlayers;

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

    public static void inputNumberOfPlayers() {
        Scanner sc= new Scanner(System.in);
        try {
            System.out.print("How many players? 2, 3, or 4 : ");
            int NumberInput = Integer.parseInt(sc.nextLine());
            if (NumberInput >= 2 && NumberInput <= 4) {
                numberOfPlayers = NumberInput;
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number between 2 and 4");
            inputNumberOfPlayers();
        }
    }

    public static Player[] createPlayerTab() {
        Scanner sc= new Scanner(System.in);
        Player[] playerTab = new Player[numberOfPlayers];
        for (int i = 0; i < playerTab.length; i++) {
            playerTab[i] = new Player();
        }

        String nameToInsert;
        int count = 0;
        int iaCounter = 0;
        while (count < numberOfPlayers) {
            System.out.print("Player " + (count + 1) + " enter your name or \"IA\" to insert an IA player : ");
            nameToInsert = sc.nextLine().replaceAll("[\\n\\t]", "").trim();
            if (nameToInsert.length() >= 1 && nameToInsert.length() <= 10) {
                boolean available = true;
                for (Player player : playerTab) {
                    if (nameToInsert.equals(player.getPlayerName())) {
                        available = false;
                    }
                }
                if (available) {
                    if (!nameToInsert.equals("IA")) {
                        playerTab[count].setPlayerName(nameToInsert);
                        playerTab[count].setType("human");
                    } else {
                        iaCounter++;
                        playerTab[count].setPlayerName("IA" + iaCounter);
                        playerTab[count].setType("IA");
                    }
                    count++;
                } else {
                    System.out.println("Sorry, but this name is not available");
                }
            } else {
                System.out.println("Please enter a name between 1 and 10 characters");
            }
        }
        return playerTab;
    }

    public static void printPlayerTab(Player[] playerTab) {
        for (Player player : playerTab) {
            System.out.println("Hello " + player.getPlayerName());
        }
    }
}

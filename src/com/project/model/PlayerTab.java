package com.project.model;

import java.util.Scanner;

public class PlayerTab {
    public static int numberOfPlayers;


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
        while (count < numberOfPlayers) {
            System.out.println("Player " + (count + 1) + " enter your name :");
            nameToInsert = sc.nextLine().replaceAll("[\\n\\t]", "").trim();
            if (nameToInsert.length() >= 1 && nameToInsert.length() <= 10) {
                playerTab[count].setPlayerName(nameToInsert);
                count++;
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

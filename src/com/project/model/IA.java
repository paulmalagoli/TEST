package com.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IA {

    public static int iaSelectCastle (String[] colorTab) {
        List<Integer> availableChoicesList = new ArrayList<>();
        for (int i = 0; i < colorTab.length; i++)
        {
            if (colorTab[i] != null) {
                availableChoicesList.add(i);
            }
        }
        int castleNumber;
        Random random = new Random();
        int randomPosition = random.nextInt(availableChoicesList.size());
        castleNumber = availableChoicesList.get(randomPosition) + 1;
        System.out.println(ConsoleColors.RED + castleNumber + ConsoleColors.RESET);
        return castleNumber;
    }

    public static int iaSelectDomino (List<Domino> pickList) {
        List<Integer> availableChoicesList = new ArrayList<>();
        for (int i = 0; i < pickList.size(); i++)
        {
            Domino pickedDomino = pickList.get(i);
            if (!Turn.nextPickedMap.get("Domino").contains(pickedDomino)) {
                availableChoicesList.add(i);
            }
        }
        int dominoNumber;
        Random random = new Random();
        int randomPosition = random.nextInt(availableChoicesList.size());
        dominoNumber = availableChoicesList.get(randomPosition) + 1;
        System.out.println(ConsoleColors.RED + dominoNumber + ConsoleColors.RESET);
        return dominoNumber;
    }

    public static int[] iaPlaceDomino (Board board, Domino currentDomino) {
        Object[][][] boardTable = board.getBoardTable();
        List<int[]> availableChoicesList = new ArrayList<>();
        for (int i = 0; i < boardTable.length; i++) {
            for (int j = 0; j < boardTable[i].length; j++) {
                if (board.checkRange(String.valueOf(i) + String.valueOf(j))) {
                    if (board.checkRange(String.valueOf(i) + String.valueOf(j + 1)) && board.checkConnection(currentDomino, i, j, i, j + 1)) {
                        availableChoicesList.add(new int[]{1, i, j});
                    }
                    if (board.checkRange(String.valueOf(i + 1) + String.valueOf(j)) && board.checkConnection(currentDomino, i, j, i + 1, j)) {
                        availableChoicesList.add(new int[]{2, i, j});
                    }
                    if (board.checkRange(String.valueOf(i) + String.valueOf(j - 1)) && board.checkConnection(currentDomino, i, j, i, j - 1)) {
                        availableChoicesList.add(new int[]{3, i, j});
                    }
                    if (board.checkRange(String.valueOf(i - 1) + String.valueOf(j)) && board.checkConnection(currentDomino, i, j, i - 1, j)) {
                        availableChoicesList.add(new int[]{4, i, j});
                    }
                }
            }
        }
        int[] locationTab;
        if (!availableChoicesList.isEmpty()) {
            Random random = new Random();
            int randomPosition = random.nextInt(availableChoicesList.size());
            locationTab = availableChoicesList.get(randomPosition);
        } else {
            locationTab = new int[]{5, 0, 0};
        }
        System.out.println(ConsoleColors.RED + locationTab[0] + ConsoleColors.RESET);
        return locationTab;
    }
}
package com.project.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IA {

    public static String iaSelectColor (List<String> colorList) {
        Random random = new Random();
        int randomPosition = random.nextInt(colorList.size());
        return colorList.get(randomPosition);
    }

    public static int[] iaSelectLocation (Board board) {
        Object[][][] boardTable = board.getBoardTable();
        List<int[]> checkInsertChoicesList = new ArrayList<>();
        for (int k = 0; k < Pick.pickList.size(); k++) {
            Domino pickedDomino = Pick.pickList.get(k);
            if (!Pick.nextPickedMap.get("Domino").contains(pickedDomino)) {
                for (int i = 0; i < boardTable.length; i++) {
                    for (int j = 0; j < boardTable[i].length; j++) {
                        for (int rotate = 0; rotate < 4; rotate++) {
                            if (board.checkInsert(pickedDomino, i, j, rotate) == 2) {
                                checkInsertChoicesList.add(new int[]{k, rotate, i, j});

                            }
                        }
                    }
                }
            }
        }
        List<int[]> availableChoicesList = new ArrayList<>();
        List<int[]> choicesList = new ArrayList<>();
        if (!checkInsertChoicesList.isEmpty()) {
            if (Player.numberOfPlayers == 2) {
                int firstIndex = Pick.pickList.size()-1;
                for (int i = 0; i < checkInsertChoicesList.size(); i++) {
                    int dominoIndex = checkInsertChoicesList.get(i)[0];
                    if (dominoIndex < firstIndex) {
                        firstIndex = dominoIndex;
                    }
                }
                for (int i = 0; i < checkInsertChoicesList.size(); i++) {
                    int dominoIndex = checkInsertChoicesList.get(i)[0];
                    if (dominoIndex == firstIndex) {
                        availableChoicesList.add(checkInsertChoicesList.get(i));
                    }
                }
            } else {
                availableChoicesList = checkInsertChoicesList;
            }

            int maxScore = 0;
            for (int k = 0; k < availableChoicesList.size(); k++) {
                Object[][][] copyBoardTable = new Object[boardTable.length][boardTable[0].length][boardTable[0][0].length];
                for (int i = 0; i < copyBoardTable.length; i++) {
                    for (int j = 0; j < copyBoardTable[i].length; j++) {
                        copyBoardTable[i][j] = Arrays.copyOf(boardTable[i][j], boardTable[i][j].length);
                    }
                }
                Board copyBoard = new Board();
                copyBoard.setBoardTable(copyBoardTable);
                copyBoard.insertDomino(Pick.pickList.get(availableChoicesList.get(k)[0]), availableChoicesList.get(k)[2], availableChoicesList.get(k)[3], availableChoicesList.get(k)[1]);
                Score score = new Score();
                score.scoreCalculation(copyBoard);
                if (score.getPlayerScore() > maxScore) {
                    maxScore = score.getPlayerScore();
                    choicesList.clear();
                    choicesList.add(availableChoicesList.get(k));
                } else if(score.getPlayerScore() == maxScore) {
                    choicesList.add(availableChoicesList.get(k));
                }
            }

            Random random = new Random();
            int randomPosition = random.nextInt(choicesList.size());
            return choicesList.get(randomPosition);
        } else {
            Random random = new Random();
            int randomPosition = random.nextInt(Pick.pickList.size());
            while (Pick.nextPickedMap.get("Domino").contains(Pick.pickList.get(randomPosition))) {
                randomPosition = random.nextInt(Pick.pickList.size());
            }
            return new int[] {randomPosition, -1, -1, -1};
        }
    }
}
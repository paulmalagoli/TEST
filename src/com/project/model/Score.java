package com.project.model;

import com.project.Main;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Score {
    String scorePlayerName;
    int playerScore = 0;
    int scopeScore = 0;
    int crownScore = 0;
    int bonus = 0;
    String bonusString = "";
    boolean equality = false;
    HashMap<String, List<Integer>> domainMap = new HashMap<>() {{
        put("Scope", new ArrayList<>());
        put("Crown", new ArrayList<>());
    }};

    public void setScorePlayerName(String scorePlayerName) {
        this.scorePlayerName = scorePlayerName;
    }

    public void setEquality(boolean equality) {
        this.equality = equality;
    }


    public String getBonusString() {
        return bonusString;
    }

    public String getScorePlayerName() {
        return this.scorePlayerName;
    }
    public int getPlayerScore() {
        return this.playerScore;
    }
    public int getScopeScore() {
        return this.scopeScore;
    }
    public int getCrownScore() {
        return this.crownScore;
    }
    public boolean getEquality() {
        return equality;
    }

    public static List<Score> createScoreList(Player[] playerTab) {
        List<Score> scoreList = new ArrayList<>();
        for (int i = 0; i < playerTab.length; i++) {
            scoreList.add(new Score());
            scoreList.get(i).setScorePlayerName(playerTab[i].getPlayerName());
        }
        return scoreList;
    }

    public void editDomainMap(Board board) {
        Object[][][] boardTable = board.getBoardTable();
        List<String> checkList = new ArrayList<>();
        for (int i = 0; i < boardTable.length; i++) {
            for (int j = 0; j < boardTable[i].length; j++) {
                if (boardTable[i][j][0] != null && boardTable[i][j][0] != "0") {
                    checkList.add(String.valueOf(i) + String.valueOf(j));
                }
            }
        }

        for (int i = 0; i < boardTable.length; i++) {
            for (int j = 0; j < boardTable[i].length; j++) {
                if (checkList.contains(String.valueOf(i) + String.valueOf(j))) {
                    this.domainMap.get("Scope").add(0);
                    this.domainMap.get("Crown").add(0);
                    domainDefiner(boardTable, checkList, this.domainMap.get("Scope").size()-1, i, j);
                }
            }
        }
    }

    public void domainDefiner(Object[][][] boardTable, List<String> checkList, int index, int row, int column) {
        this.domainMap.get("Scope").set(index, this.domainMap.get("Scope").get(index) + 1);
        this.domainMap.get("Crown").set(index, this.domainMap.get("Crown").get(index) + (int) boardTable[row][column][1]);
        checkList.remove(String.valueOf(row) + String.valueOf(column));
        if (checkList.contains(String.valueOf(row+1) + String.valueOf(column))) {
            if (boardTable[row][column][0] == boardTable[row+1][column][0]) {
                domainDefiner(boardTable, checkList, index, row+1, column);
            }
        }
        if (checkList.contains(String.valueOf(row-1) + String.valueOf(column))) {
            if (boardTable[row][column][0] == boardTable[row-1][column][0]) {
                domainDefiner(boardTable, checkList, index, row-1, column);
            }
        }
        if (checkList.contains(String.valueOf(row) + String.valueOf(column+1))) {
            if (boardTable[row][column][0] == boardTable[row][column+1][0]) {
                domainDefiner(boardTable, checkList, index, row, column+1);
            }
        }
        if (checkList.contains(String.valueOf(row) + String.valueOf(column-1))) {
            if (boardTable[row][column][0] == boardTable[row][column-1][0]) {
                domainDefiner(boardTable, checkList, index, row, column-1);
            }
        }
    }

    public static boolean harmony(Board board) {
        Object[][][] boardTable = board.getBoardTable();
        int row = 0;
        int column = 0;
        boolean pursue = true;
        int count = 0;
        for (int i = 0; i < boardTable.length; i++) {
            for (int j = 0; j < boardTable[i].length; j++) {
                if (boardTable[i][j][0] != null) {
                    row = i;
                    column = j;
                    pursue = false;
                    break;
                }
                if (!pursue) {
                    break;
                }
            }
        }
        int endRow = row + 4;
        int endColumn = column + 4;
        if (endRow < boardTable.length && endColumn < boardTable[0].length) {
            for (int i = row; i <= endRow; i++) {
                for (int j = column; j <= endColumn; j++) {
                    if (boardTable[i][j][0] != null) {
                        count++;
                    }
                }
            }
        }
        if (count == 25) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean theMiddleKingdom(Board board) {
        Object[][][] boardTable = board.getBoardTable();

        int firstRow = 4;
        int firstColumn = 4;
        int lastRow = 4;
        int lastColumn = 4;
        boolean empty;
        for (int i = 0; i < boardTable.length; i++)
        {
            empty = true;
            for (int j = 0; j < boardTable[i].length; j++)
            {
                if (boardTable[i][j][0] != null) {
                    if (j<firstColumn) {
                        firstColumn = j;
                    }
                    if (j>lastColumn) {
                        lastColumn = j;
                    }
                    empty = false;
                }
            }
            if (!empty) {
                if (i<firstRow) {
                    firstRow = i;
                }
                if (i>lastRow) {
                    lastRow = i;
                }
            }
        }
        if ((lastRow - firstRow) % 2 == 0 && (lastColumn - firstColumn) % 2 == 0) {
            int middleRow = firstRow + (lastRow - firstRow) / 2;
            int middleColumn = firstColumn + (lastColumn - firstColumn) / 2;
            if (boardTable[middleRow][middleColumn][0] == "0") {
                return true;
            }
        }
        return false;
    }

    public void playerScoreCalculation() {
        for (int i = 0; i < this.domainMap.get("Crown").size(); i++) {
            this.playerScore += this.domainMap.get("Scope").get(i) * this.domainMap.get("Crown").get(i);
        }
        this.playerScore += this.bonus;
    }

    public void scopeScoreCalculation() {
        if (domainMap.get("Scope").isEmpty()) {
            this.scopeScore = 0;
        } else {
            this.scopeScore = Collections.max(this.domainMap.get("Scope"));
        }
    }

    public void crownScoreCalculation() {
        for (int i = 0; i < this.domainMap.get("Crown").size(); i++) {
            this.crownScore += this.domainMap.get("Crown").get(i);
        }
    }

    public void scoreCalculation(Board board) {
        if (Main.mode == "Harmony") {
            if (harmony(board)) {
                this.bonus = 5;
                this.bonusString = "\n   +5 bonus points harmony";
            }
        } else if (Main.mode == "The Middle Kingdom") {
            if (theMiddleKingdom(board)) {
                this.bonus = 10;
                this.bonusString = "\n   +10 bonus points The Middle Kingdom";
            }
        } else {
            this.bonus = 0;
            this.bonusString = "";
        }
        editDomainMap(board);
        playerScoreCalculation();
        scopeScoreCalculation();
        crownScoreCalculation();
    }

    public static List<Label> printLeaderBoard(List<Board> boardList, List<Score> scoreList) {
        Collections.sort(scoreList, new RankComparator());
        List<Label> labelList = new ArrayList<>();
        int rank = 0;
        for (Score score : scoreList) {
            Label label = new  Label();
            if (!score.getEquality()){
                rank++;
            }
            label.setText(rank + ": " + score.getScorePlayerName()
                    + "\n   with " + score.getPlayerScore()
                    + " points\n   most extensive domain of " + score.getScopeScore()
                    + " squares\n   " + score.getCrownScore() + " crowns in total" + score.getBonusString());
            for (Board board : boardList) {
                if (board.getBoardPlayerName() == score.getScorePlayerName()){
                    labelList.add(label);
                }
            }
        }
        return labelList;
    }
}

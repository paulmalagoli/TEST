package com.project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Score {
    String scorePlayerName;
    int playerScore = 0;
    int scopeScore = 0;
    int crownScore = 0;
    boolean equality = false;
    HashMap<String, List<Integer>> domainMap = new HashMap<String, List<Integer>>() {{
        put("Scope", new ArrayList<Integer>());
        put("Crown", new ArrayList<Integer>());
    }};

    public void setScorePlayerName(String scorePlayerName) {
        this.scorePlayerName = scorePlayerName;
    }
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
    public void setScopeScore(int scopeScore) {
        this.scopeScore = scopeScore;
    }
    public void setCrownScore(int crownScore) {
        this.crownScore = crownScore;
    }
    public void setEquality(boolean equality) {
        this.equality = equality;
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

    public void playerScoreCalculation() {
        for (int i = 0; i < this.domainMap.get("Crown").size(); i++) {
            this.playerScore += this.domainMap.get("Scope").get(i) * this.domainMap.get("Crown").get(i);
        }
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

    public static void scoreCalculation(List<Board> boardList, List<Score> scoreList) {
        for (int i = 0; i < boardList.size(); i++) {
            scoreList.get(i).editDomainMap(boardList.get(i));
            scoreList.get(i).playerScoreCalculation();
            scoreList.get(i).scopeScoreCalculation();
            scoreList.get(i).crownScoreCalculation();
        }
    }

    public static void printLeaderBoard(List<Board> boardList, List<Score> scoreList) {
        Collections.sort(scoreList, new RankComparator());
        int rank = 0;
        for (Score score : scoreList) {
            if (!score.getEquality()){
                rank++;
            }
            System.out.println(rank + ": " + score.getScorePlayerName()
                    + ", with " + score.getPlayerScore()
                    + " points, most extensive domain of " + score.getScopeScore()
                    + " squares, and " + score.getCrownScore() + " crowns in total");
            for (Board board : boardList) {
                if (board.getBoardPlayerName() == score.getScorePlayerName()){
                    board.printBoard();
                }
            }
        }
    }
}

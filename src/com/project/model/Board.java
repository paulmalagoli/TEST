package com.project.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    Object[][][] boardTable = new Object[9][9][2];
    List<String> availableRangeList = new ArrayList<>();
    String boardPlayerName;


    public void setBoardPlayerName(String playerName) {
        this.boardPlayerName = playerName;
    }
    public String getBoardPlayerName() {
        return this.boardPlayerName;
    }

    public Object[][][] getBoardTable() {
        return this.boardTable;
    }

    public static List<Board> createBoardList(Player[] playerTab) {
        List<Board> boardList = new ArrayList<>();
        for (int i = 0; i < playerTab.length; i++) {
            boardList.add(new Board());
            boardList.get(i).setBoardPlayerName(playerTab[i].getPlayerName());
        }
        return boardList;
    }

    public void insertCastle() {
        this.boardTable[4][4][0] = "0";
        this.boardTable[4][4][1] = 0;
    }

    public void insertDomino(Domino domino, int row1, int column1, int row2, int column2) {
        this.boardTable[row1][column1][0] = domino.getType1();
        this.boardTable[row1][column1][1] = domino.getNbCrown1();
        this.boardTable[row2][column2][0] = domino.getType2();
        this.boardTable[row2][column2][1] = domino.getNbCrown2();
    }

    public void printBoard() {
        for (int i = 0; i < this.boardTable.length; i++) {
            for (int j = 0; j < this.boardTable[i].length; j++) {
                if (this.boardTable[i][j][0] != null && this.boardTable[i][j][0] != "0") {
                    System.out.format("%-2s ", (String) this.boardTable[i][j][0] + (int) this.boardTable[i][j][1]);
                } else if (this.boardTable[i][j][0] == "0"){
                    System.out.format("%-2s ", ConsoleColors.RED + (String) this.boardTable[i][j][0] + (int) this.boardTable[i][j][1] + ConsoleColors.RESET);
                } else {
                    System.out.format("%2s ", String.valueOf(i) + String.valueOf(j));
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void editAvailableRangeList() {
        availableRangeList.clear();
        int firstRow = 4;
        int firstColumn = 4;
        int lastRow = 4;
        int lastColumn = 4;
        boolean empty;
        for (int i = 0; i < this.boardTable.length; i++)
        {
            empty = true;
            for (int j = 0; j < this.boardTable[i].length; j++)
            {
                if (this.boardTable[i][j][0] != null) {
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
        int rowRange = 4 - (lastRow - firstRow);
        int minRow = firstRow - rowRange;
        int maxRow = lastRow + rowRange;

        int columnRange = 4 - (lastColumn - firstColumn);
        int minColumn = firstColumn - columnRange;
        int maxColumn = lastColumn + columnRange;

        for (int i = 0; i < this.boardTable.length; i++)
        {
            if (i >= minRow && i <= maxRow) {
                for (int j = 0; j < this.boardTable[i].length; j++)
                {
                    if (j >= minColumn && j <= maxColumn && this.boardTable[i][j][0] == null) {
                        availableRangeList.add(String.valueOf (i) + String.valueOf (j));
                    }
                }
            }
        }
        availableRangeList.remove("44");
    }

    public boolean checkRange(String location) {
        return this.availableRangeList.contains(location);
    }

    public boolean checkConnection(Domino domino, int row1, int column1, int row2, int column2) {
        boolean connection = false;
        if (row1 >= 0 && row1 <= 8 && column1 >= 0 && column1 <= 8 && row2 >= 0 && row2 <= 8 && column2 >= 0 && column2 <= 8) {
            if (row1 + 1 >= 0 && row1 + 1 <= 8) {
                if (domino.getType1() == this.boardTable[row1 + 1][column1][0] || this.boardTable[row1 + 1][column1][0] == this.boardTable[4][4][0]) {
                    connection = true;
                }
            }
            if (row1 - 1 >= 0 && row1 - 1 <= 8) {
                if (domino.getType1() == this.boardTable[row1 - 1][column1][0] || this.boardTable[row1 - 1][column1][0] == this.boardTable[4][4][0]) {
                    connection = true;
                }
            }
            if (column1 + 1 >= 0 && column1 + 1 <= 8) {
                if (domino.getType1() == this.boardTable[row1][column1 + 1][0] || this.boardTable[row1][column1 + 1][0] == this.boardTable[4][4][0]) {
                    connection = true;
                }
            }
            if (column1 - 1 >= 0 && column1 - 1 <= 8) {
                if (domino.getType1() == this.boardTable[row1][column1 - 1][0] || this.boardTable[row1][column1 - 1][0] == this.boardTable[4][4][0]) {
                    connection = true;
                }
            }

            if (row2 + 1 >= 0 && row2 + 1 <= 8) {
                if (domino.getType2() == this.boardTable[row2 + 1][column2][0] || this.boardTable[row2 + 1][column2][0] == this.boardTable[4][4][0]) {
                    connection = true;
                }
            }
            if (row2 - 1 >= 0 && row2 - 1 <= 8) {
                if (domino.getType2() == this.boardTable[row2 - 1][column2][0] || this.boardTable[row2 - 1][column2][0] == this.boardTable[4][4][0]) {
                    connection = true;
                }
            }
            if (column2 + 1 >= 0 && column2 + 1 <= 8) {
                if (domino.getType2() == this.boardTable[row2][column2 + 1][0] || this.boardTable[row2][column2 + 1][0] == this.boardTable[4][4][0]) {
                    connection = true;
                }
            }
            if (column2 - 1 >= 0 && column2 - 1 <= 8) {
                if (domino.getType2() == this.boardTable[row2][column2 - 1][0] || this.boardTable[row2][column2 - 1][0] == this.boardTable[4][4][0]) {
                    connection = true;
                }
            }
        }
        return connection;
    }
}

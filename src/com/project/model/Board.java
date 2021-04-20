package com.project.model;

public class Board {
    Object[][] boardTable = new Object[5][5];

    public void createBoard() {

    }

    public void printBoard() {
        for (Object[] x : boardTable)
        {
            for (Object y : x)
            {
                System.out.format("%-4s", y);
            }
            System.out.println();
        }
    }
}

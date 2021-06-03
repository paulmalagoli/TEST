package com.project;

import java.io.IOException;

import java.util.List;

import com.project.model.*;

public class Main {


    public static void main(String[] args) {
        // write your code here
        try {
            List<Domino> dominoList = Domino.createDominoList();
            Player.inputNumberOfPlayers();
            Player[] playerTab = Player.createPlayerTab();
            Player.printPlayerTab(playerTab);

            Domino.cutDominoList(dominoList);
            King[] kingTab = King.createKingTab(playerTab);
            Castle[] castleTab = Castle.createCastleTab(playerTab);

            List<Board> boardList = Board.createBoardList(playerTab);
            Castle.selectCastle(playerTab, castleTab, boardList);

            King.shuffleKingTab(kingTab);
            Turn.firstTurn(playerTab, kingTab, dominoList, boardList);

            Turn.turnLooper(playerTab, kingTab, dominoList, boardList);

            List<Score> scoreList = Score.createScoreList(playerTab);
            Score.scoreCalculation(boardList, scoreList);
            System.out.println();
            Score.printLeaderBoard(boardList, scoreList);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
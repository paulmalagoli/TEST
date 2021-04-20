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

            King.shuffleKingTab(kingTab);

            List<Domino> pickList = Pick.createPickList(dominoList);
            Pick.sortPickList(pickList);
            System.out.println();
            for (Domino domino : pickList) {
                System.out.print(domino.getDominoNumber() + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

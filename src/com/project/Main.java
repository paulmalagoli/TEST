package com.project;

import java.io.IOException;

import java.util.List;

import com.project.model.Domino;
import com.project.model.DominoList;
import com.project.model.PlayerTab;

import static com.project.model.PlayerTab.inputNumberOfPlayers;


public class Main {


    public static void main(String[] args) {
        // write your code here
        try {
            List<Domino> dominoList = DominoList.createDominoList();
            inputNumberOfPlayers();
            PlayerTab.printPlayerTab(PlayerTab.createPlayerTab());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
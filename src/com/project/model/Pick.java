package com.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pick {

    public static List<Domino> createPickList(List<Domino> dominoList) {
        if (dominoList.size() >= King.numberOfKing) {
            List<Domino> pickList = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < King.numberOfKing; i++) {
                int nb = random.nextInt(dominoList.size());
                pickList.add(i, dominoList.get(nb));
                dominoList.remove(nb);
            }
            return pickList;
        } else {
            return null;
        }
    }

    public static void sortPickList(List<Domino> pickList) {
        for (int i = 0; i < pickList.size()-1; i++) {
            int indexMin = i;
            for (int j = i; j < pickList.size(); j++) {
                if (pickList.get(j).getDominoNumber() < pickList.get(indexMin).getDominoNumber()) {
                    indexMin = j;
                }
            }
            Domino swap = pickList.get(i);
            pickList.set(i, pickList.get(indexMin));
            pickList.set(indexMin, swap);
        }
    }
}
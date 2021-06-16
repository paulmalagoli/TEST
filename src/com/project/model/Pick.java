package com.project.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Pick {
    public static HashMap<String, List<Object>> currentPickedMap = new HashMap<>() {{
        put("King", new ArrayList<>());
        put("Domino", new ArrayList<>());
        put("Index", new ArrayList<>());
    }};
    public static HashMap<String, List<Object>> nextPickedMap = new HashMap<>() {{
        put("King", new ArrayList<>());
        put("Domino", new ArrayList<>());
        put("Index", new ArrayList<>());
    }};
    public static List<Domino> pickList;

    public static void createPickList() {
        pickList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < King.numberOfKing; i++) {
            int nb = random.nextInt(Domino.dominoList.size());
            pickList.add(Domino.dominoList.get(nb));
            Domino.dominoList.remove(nb);
        }
    }

    public static void sortPickList() {
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
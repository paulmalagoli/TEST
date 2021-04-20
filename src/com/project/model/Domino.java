package com.project.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Domino {
    int dominoNumber;
    int nbCrown1;
    String type1;
    int nbCrown2;
    String type2;


    public void setNbCrown1(int nbCrown1) {
        this.nbCrown1 = nbCrown1;
    }
    public void setType1(String type1) {
        this.type1 = type1;
    }
    public void setNbCrown2(int nbCrown2) {
        this.nbCrown2 = nbCrown2;
    }
    public void setType2(String type2) {
        this.type2 = type2;
    }
    public void setDominoNumber(int NumeroDomino) {
        this.dominoNumber = NumeroDomino;
    }


    public int getNbCrown1() {
        return this.nbCrown1;
    }
    public String getType1() {
        switch(this.type1) {
            case "Champs":
                return "C";
            case "Foret":
                return "F";
            case "Mer":
                return "M";
            case "Prairie":
                return "P";
            case "Mine":
                return "I";
            case "Montagne":
                return "O";
            default:
                return null;
        }
    }
    public int getNbCrown2() {
        return this.nbCrown2;
    }
    public String getType2() {
        switch(this.type2) {
            case "Champs":
                return "C";
            case "Foret":
                return "F";
            case "Mer":
                return "M";
            case "Prairie":
                return "P";
            case "Mine":
                return "I";
            case "Montagne":
                return "O";
            default:
                return null;
        }
    }
    public int getDominoNumber() {
        return this.dominoNumber;
    }


    public static List<Domino> createDominoList() throws FileNotFoundException {

        File getCSVFiles = new File("src/com/project/public/dominos.csv");

        long lines = 0;
        try (LineNumberReader lnr = new LineNumberReader(new FileReader(getCSVFiles))) {
            while (lnr.readLine() != null) {
                lines = lnr.getLineNumber();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(getCSVFiles);
        sc.useDelimiter(",");
        sc.nextLine();
        List<Domino> dominoList = new ArrayList<>();
        for (int i = 0; i < lines - 1; i++) {
            dominoList.add(new Domino());
        }

        int count = 0;
        while (sc.hasNextLine())
        {
            Domino domino = dominoList.get(count);
            String line = sc.nextLine();
            Scanner sc2 = new Scanner(line);
            sc2.useDelimiter(",");
            domino.setNbCrown1(Integer.parseInt(sc2.next()));
            domino.setType1(sc2.next());
            domino.setNbCrown2(Integer.parseInt(sc2.next()));
            domino.setType2(sc2.next());
            domino.setDominoNumber(Integer.parseInt(sc2.next()));
            sc2.close();
            count++;
        }
        sc.close();

        return dominoList;

    }

    public static void cutDominoList(List<Domino> dominoList) {
        Random random = new Random();
        if (Player.numberOfPlayers == 2) {
            for (int i = 0; i < 24; i++) {
                int nb = random.nextInt(48-i);
                dominoList.remove(nb);
            }
        } else if (Player.numberOfPlayers == 3) {
            for (int i = 0; i < 12; i++) {
                int nb = random.nextInt(48-i);
                dominoList.remove(nb);
            }
        } else {
        }
    }
}

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

    public Domino(int dominoNumber, int nbCrown1, String type1, int nbCrown2, String type2) {
        this.dominoNumber = dominoNumber;
        this.nbCrown1 = nbCrown1;
        this.type1 = type1;
        this.nbCrown2 = nbCrown2;
        this.type2 = type2;
    }

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
                return ConsoleColors.YELLOW + "C" + ConsoleColors.RESET;
            case "Foret":
                return ConsoleColors.GREEN + "F" + ConsoleColors.RESET;
            case "Mer":
                return ConsoleColors.BLUE + "M" + ConsoleColors.RESET;
            case "Prairie":
                return ConsoleColors.PURPLE + "P" + ConsoleColors.RESET;
            case "Mine":
                return ConsoleColors.CYAN + "I" + ConsoleColors.RESET;
            case "Montagne":
                return ConsoleColors.WHITE + "T" + ConsoleColors.RESET;
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
                return ConsoleColors.YELLOW + "C" + ConsoleColors.RESET;
            case "Foret":
                return ConsoleColors.GREEN + "F" + ConsoleColors.RESET;
            case "Mer":
                return ConsoleColors.BLUE + "M" + ConsoleColors.RESET;
            case "Prairie":
                return ConsoleColors.PURPLE + "P" + ConsoleColors.RESET;
            case "Mine":
                return ConsoleColors.CYAN + "I" + ConsoleColors.RESET;
            case "Montagne":
                return ConsoleColors.WHITE + "T" + ConsoleColors.RESET;
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

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Scanner sc2 = new Scanner(line);
            sc2.useDelimiter(",");
            int nbCrown1 = Integer.parseInt(sc2.next());
            String type1 = sc2.next();
            int nbCrown2 = Integer.parseInt(sc2.next());
            String type2 = sc2.next();
            int dominoNumber = Integer.parseInt(sc2.next());
            sc2.close();
            dominoList.add(new Domino(dominoNumber, nbCrown1, type1, nbCrown2, type2));
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
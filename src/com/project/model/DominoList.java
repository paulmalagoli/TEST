package com.project.model;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DominoList {
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
            domino.setNbcrown1(Integer.parseInt(sc2.next()));
            domino.setType1(sc2.next());
            domino.setNbcrown2(Integer.parseInt(sc2.next()));
            domino.setType2(sc2.next());
            domino.setDominoNumber(Integer.parseInt(sc2.next()));
            sc2.close();
            count++;
        }
        sc.close();

        return dominoList;

    }
}
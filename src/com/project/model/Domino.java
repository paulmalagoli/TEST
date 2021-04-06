package com.project.model;

public class Domino {
    int dominoNumber;
    int nbcrown1;
    String type1;
    int nbcrown2;
    String type2;


    public void setNbcrown1(int nbcrown1) {
        this.nbcrown1 = nbcrown1;
    }
    public void setType1(String type1) {
        this.type1 = type1;
    }
    public void setNbcrown2(int nbcrown2) {
        this.nbcrown2 = nbcrown2;
    }
    public void setType2(String type2) {
        this.type2 = type2;
    }
    public void setDominoNumber(int NumeroDomino) {
        this.dominoNumber = NumeroDomino;
    }


    public int getNbcrown1() {
        return this.nbcrown1;
    }
    public String getType1() {
        return this.type1;
    }
    public int getNbcrown2() {
        return this.nbcrown2;
    }
    public String getType2() {
        return this.type2;
    }
    public int getDominoNumber() {
        return this.dominoNumber;
    }
}

package kd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.*;

//this class represents the Squares that make up each individual domino (2 per domino). Each Square has a terrain type, and a number of crowns (0, 1, 2 or 3)
public class Square extends Rectangle {

	private int crowns;

	private String name;
	private Color theColor;
	private boolean scoredYet = false;
	public enum TerrainType {
		FARM, MINE, WATER, SWAMP, PLAIN, FOREST, CASTLE, BLANK, OUTOFBOUNDS;
	}

	public Square(TerrainType terrainType) {

		switch (terrainType) {
		case FARM:
			name = "F";
			theColor = Color.YELLOW;
			break;
		case MINE:
			name = "M";
			theColor = Color.BLACK;
			break;
		case WATER:
			name = "W";
			theColor = Color.BLUE;
			break;
		case SWAMP:
			name = "S";
			theColor = Color.GRAY;
			break;
		case PLAIN:
			name = "P";
			theColor = Color.GREEN;
			break;
		case FOREST:
			name = "T";
			theColor = new Color(0x004d00);
			
			break;
		case CASTLE:
			name = "C";
			theColor = Color.PINK;
			break;
		case BLANK:
			 name = "B";
			theColor = Color.WHITE;
			 break;
		case OUTOFBOUNDS:
			name = "X";
			break;

		}
	}
	
	public Color getColor() {
		return theColor;
	}

	public String getName() {
		return name;
	}

	public void setCrowns(int crowns) {
		this.crowns = crowns;
	}

	public int getCrowns() {
		return crowns;
	}

	public String getCrownsString() {
		return Integer.toString(crowns);
	}
	public boolean isScoredYet() {
		return scoredYet;
	}
	public void setScoredYet(boolean scoredYet) {
		this.scoredYet = scoredYet;
	}
	
	

}

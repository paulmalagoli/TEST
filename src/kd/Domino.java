package kd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/*
class that represents one domino in a game of KingDomino. One domino is made up of two Square objects, a left one and a right one, a number 
used to order them when setting out a new turn, and a boolean to see if the domino is still in the supply, or if it's been played.
*/
public class Domino extends JPanel {

	private Square leftSquare;
	private Square pivotSquare;
	private int number;
	private boolean played;
	private boolean chosen;

	public Domino() {
		played = false;
		chosen = false;
	}
	

	public void setPlayed() {
		played = true;
	}

	public boolean getPlayed() {
		return played;
	}

	public void setChosen() {
		chosen = true;
	}

	public boolean isChosen() {
		return chosen;
	}
	public void printADomino() {
		System.out.println(leftSquare.getName() + "" + leftSquare.getCrowns() + " "
				+ pivotSquare.getName() + "" + pivotSquare.getCrowns() + " " + number);
	}

	public void setLeft(Square leftSquare) {
		this.leftSquare = leftSquare;
	}

	public void setRight(Square pivotSquare) {
		this.pivotSquare = pivotSquare;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Square getPivot() {
		return pivotSquare;
	}
	public Square getLeft(){
		return leftSquare;
	}

	public void setLeftCrowns(int num) {
		leftSquare.setCrowns(num);
	}

	public void setRightCrowns(int num) {
		pivotSquare.setCrowns(num);
	}

	public int getLeftCrowns() {
		return leftSquare.getCrowns();
	}

	public int getRightCrowns() {
		return pivotSquare.getCrowns();
	}

	public int getNumber() {
		return number;
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setFont(new Font("TimesRoman", Font.BOLD, 20));
		leftSquare.setBounds(0, 0, 50, 50);
		g2.setColor(leftSquare.getColor());
		g2.fill(leftSquare);
		g2.setColor(complementaryColor(g2.getColor()));
		g2.drawString(leftSquare.getCrownsString(), 5, 50);
		g.setColor(Color.black);
		g.drawRect(0, 0, 50, 50);

		pivotSquare.setBounds(50, 0, 50, 50);
		g2.setColor(pivotSquare.getColor());
		g2.fill(pivotSquare);
		g2.setColor(complementaryColor(g2.getColor()));
		g2.drawString(pivotSquare.getCrownsString(), 55, 50);
		g.setColor(Color.black);
		g.drawRect(50, 0, 50, 50);

	}

	private static Color complementaryColor(Color color) {
		double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		return y >= 128 ? Color.black : Color.white;
	}

}

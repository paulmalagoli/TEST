package kd;

import kd.Square.TerrainType;

//this class represents the supply of dominoes to be drawn from for each turn.
public class DominoSupply {

	public DominoSupply() {
		setUpSupply();
	}

	// variable representing the supply of unplayed dominos. When new dominoes are drawn from this array for a new turn, those dominoes
	//will no longer be drawable.
	private Domino[] unplayedDominoes = new Domino[48];

	// sets up the initial supply of dominos that will be used to set up each turn
	public void setUpSupply() {

		for (int i = 0; i < unplayedDominoes.length; i++) {

			unplayedDominoes[i] = new Domino();
			if (i == 0 || i == 1 || i == 12 || i == 13 || i == 14 || i == 15 || i == 18 || i == 19 || i == 20 || i == 21
					|| i == 22 || i == 35 || i == 37 || i == 40 || i == 42 || i == 47) {
				unplayedDominoes[i].setLeft(new Square(TerrainType.FARM));
			}
			if (i == 0 || i == 1 || i == 23 || i == 24 || i == 25 || i == 26 || i == 29 || i == 30 || i == 39
					|| i == 44) {
				unplayedDominoes[i].setRight(new Square(TerrainType.FARM));
			}
			if (i == 2 || i == 3 || i == 4 || i == 5 || i == 16 || i == 17 || i == 23 || i == 24 || i == 25 || i == 26
					|| i == 27 || i == 28) {
				unplayedDominoes[i].setLeft(new Square(TerrainType.FOREST));
			}
			if (i == 2 || i == 3 || i == 4 || i == 5 || i == 12 || i == 18 || i == 31 || i == 32 || i == 33
					|| i == 34) {
				unplayedDominoes[i].setRight(new Square(TerrainType.FOREST));
			}
			if (i == 6 || i == 7 || i == 8 || i == 29 || i == 30 || i == 31 || i == 32 || i == 33 || i == 34 || i == 36
					|| i == 41) {
				unplayedDominoes[i].setLeft(new Square(TerrainType.WATER));
			}
			if (i == 6 || i == 7 || i == 8 || i == 13 || i == 16 || i == 19 || i == 27) {
				unplayedDominoes[i].setRight(new Square(TerrainType.WATER));
			}
			if (i == 9 || i == 10 || i == 38 || i == 43) {
				unplayedDominoes[i].setLeft(new Square(TerrainType.PLAIN));
			}
			if (i == 9 || i == 10 || i == 14 || i == 17 || i == 20 || i == 28 || i == 35 || i == 36 || i == 40
					|| i == 41) {
				unplayedDominoes[i].setRight(new Square(TerrainType.PLAIN));
			}
			if (i == 11 || i == 45 || i == 46) {
				unplayedDominoes[i].setLeft(new Square(TerrainType.SWAMP));
			}
			if (i == 11 || i == 15 || i == 21 || i == 37 || i == 38 || i == 42 || i == 43) {
				unplayedDominoes[i].setRight(new Square(TerrainType.SWAMP));
			}
			if (i == 39 || i == 44) {
				unplayedDominoes[i].setLeft(new Square(TerrainType.MINE));
			}
			if (i == 22 || i == 45 || i == 46 || i == 47) {
				unplayedDominoes[i].setRight(new Square(TerrainType.MINE));
			}
			if (i >= 0 && i <= 17) {
				unplayedDominoes[i].setLeftCrowns(0);
				unplayedDominoes[i].setRightCrowns(0);
			}
			if ((i >= 18 && i <= 34) || i == 39) {
				unplayedDominoes[i].setLeftCrowns(1);
				unplayedDominoes[i].setRightCrowns(0);

			}
			if (i >= 35 && i <= 38) {
				unplayedDominoes[i].setLeftCrowns(0);
				unplayedDominoes[i].setRightCrowns(1);

			}
			if ((i >= 40 && i <= 43) || i == 45 || i == 46) {
				unplayedDominoes[i].setLeftCrowns(0);
				unplayedDominoes[i].setRightCrowns(2);
			}
			if (i == 44) {
				unplayedDominoes[i].setLeftCrowns(2);
				unplayedDominoes[i].setRightCrowns(0);
			}
			if (i == 47) {

				unplayedDominoes[i].setLeftCrowns(0);
				unplayedDominoes[i].setRightCrowns(3);
			}

			unplayedDominoes[i].setNumber(i + 1);

		}

	}

	// getter method for the unplayedDominoes variable
	public Domino[] getUnplayed() {
		return unplayedDominoes;
	}

	//this method is used in the TurnCanvas class, simply to make the code a little more tidy.
	public int length() {
		return unplayedDominoes.length;
	}

	// checks if there are still dominoes left in the supply, i.e. the
	// unplayedDominoes variable
	public boolean isEmpty() {
		for (int i = 0; i < unplayedDominoes.length; i++) {
			if (unplayedDominoes[i].getPlayed() == false) {
				return false;
			}
		}
		return true;
	}

}

package kd;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import kd.Square.TerrainType;
//This class represents a Player's "PlayArea" in a game of KingDomino, i.e. the place where they will be placing the Dominos they have chosen.
public class PlayArea {
	private List<Domino> dominos;
	private Square[][] playerBoard = new Square[10][10];
	private int totalBoardScore = 0;
	Square[][] testBoard = new Square[10][10];

	//Starting play area is a 10x10 grid of blank Squares, with a castle Square in the center. 
	public PlayArea() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if ((i == 4) && (j == 4)) {
					playerBoard[j][i] = new Square(TerrainType.CASTLE);
				} else {
					playerBoard[j][i] = new Square(TerrainType.BLANK);
				}
			}
		}
	}

	//This method is used for testing. It gives a Square with a random terrain type, to be used
	//by another method to generate random, complete PlayAreas that can be used to test scoring.
	public Square giveRandDomino() {
		Random rand = new Random();
		Random c = new Random();
		Square s;
		int n = rand.nextInt(31) + 1;
		int ca = rand.nextInt(3);
		if (n <= 5) {
			s = new Square(TerrainType.FARM);
		} else if (n > 5 && n <= 10) {
			s = new Square(TerrainType.MINE);
		} else if (n > 10 && n <= 15) {
			s = new Square(TerrainType.WATER);
		} else if (n > 15 && n <= 20) {
			s = new Square(TerrainType.SWAMP);
		} else if (n > 20 && n <= 25) {
			s = new Square(TerrainType.PLAIN);
		} else if (n > 25 && n <= 30) {
			s = new Square(TerrainType.FOREST);
		} else {
			s = new Square(TerrainType.BLANK);
		}
		s.setCrowns(ca);

		return s;

	}

	//This method is used for testing. It generated a random PlayArea, as if a full game of KingDomino had just been played. 
	//It is used to test scoring.
	public void populateRandomBoard() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				if (j < 2 || j > 6 || i < 2 || i > 6) {
					playerBoard[j][i] = new Square(TerrainType.BLANK);
				} else if ((i == 4) && (j == 4)) {
					playerBoard[j][i] = new Square(TerrainType.CASTLE);
				} else if (i < 4 && i > 1) {

					playerBoard[j][i] = giveRandDomino();

				} else if (i > 4 && i < 7) {
					playerBoard[j][i] = giveRandDomino();

				}

			}
		}
		playerBoard[2][4] = giveRandDomino();
		playerBoard[3][4] = giveRandDomino();
		playerBoard[5][4] = giveRandDomino();
		playerBoard[6][4] = giveRandDomino();

	}


	public boolean isntOccupied(int x, int y) {
		if (playerBoard[x][y].getName() != "B") {

			return false;
		} else {

			return true;
		}
	}

	public void setBoardBounds() {
		int minX = 9;
		int maxX = 0;
		int minY = 9;
		int maxY = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (playerBoard[j][i].getName() != "B" && playerBoard[j][i].getName() != "X") {
					if (j < minX) {
						minX = j;
					}
					if (j > maxX) {
						maxX = j;
					}
					if (i < minY) {
						minY = i;
					}
					if (i > maxY) {
						maxY = i;
					}
				}

			}
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (j > minX + 4) {
					playerBoard[j][i] = new Square(TerrainType.OUTOFBOUNDS);
				}
				if (j < maxX - 4) {
					playerBoard[j][i] = new Square(TerrainType.OUTOFBOUNDS);
				}
				if (i > minY + 4) {
					playerBoard[j][i] = new Square(TerrainType.OUTOFBOUNDS);
				}
				if (i < maxY - 4) {
					playerBoard[j][i] = new Square(TerrainType.OUTOFBOUNDS);
				}
			}
		}
	}

	List<Square> theZone = new ArrayList<Square>();
	private Domino chosenDomino;

	public void findAZone(int x, int y) {

		if (!(playerBoard[x][y].isScoredYet())) {
			if (playerBoard[x][y].getName() == "X" || playerBoard[x][y].getName() == "B") {

			} else {
				theZone.add(playerBoard[x][y]);
				playerBoard[x][y].setScoredYet(true);
				if (playerBoard[x + 1][y].getName() == playerBoard[x][y].getName() && (x + 1 != 10)
						&& !playerBoard[x + 1][y].isScoredYet()) {

					findAZone(x + 1, y);

				}

				if ((playerBoard[x - 1][y].getName() == playerBoard[x][y].getName()) && (x - 1 != -1)
						&& !playerBoard[x - 1][y].isScoredYet()) {
					findAZone(x - 1, y);

				}

				if (playerBoard[x][y + 1].getName() == playerBoard[x][y].getName() && (y + 1 != 10)
						&& !playerBoard[x][y + 1].isScoredYet()) {
					findAZone(x, y + 1);

				}

				if (playerBoard[x][y - 1].getName() == playerBoard[x][y].getName() && (y - 1 != -1)
						&& !playerBoard[x][y - 1].isScoredYet()) {
					findAZone(x, y - 1);

				}

			}

		}

	}

	public void scoreAZone(int x, int y) {

		findAZone(x, y);
		int numCrowns = 0;
		for (int i = 0; i < theZone.size(); i++) {
			numCrowns += theZone.get(i).getCrowns();
		}
		totalBoardScore += (theZone.size() * numCrowns);
		theZone = new ArrayList<Square>();
		// System.out.print(" " + numCrowns + " ");
		// System.out.print(theZone.size());

		// System.out.print("Scored");

	}

	public void scoreTheBoard() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				scoreAZone(j, i);
			}
		}

		System.out.print(totalBoardScore);
	}

	public void printBoard() {
		System.out.println("");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (playerBoard[j][i].getName() == "B")
					System.out.print(playerBoard[j][i].getName() + "" + j + "" + i + " ");
				else if (playerBoard[j][i].getName() == "X")
					System.out.print(playerBoard[j][i].getName() + "   ");
				else
					System.out.print(playerBoard[j][i].getName() + "" + playerBoard[j][i].getCrowns() + "  ");

			}
			System.out.println("");

		}
		System.out.println("");
	}

	public Square[][] getPlayerBoard() {
		return playerBoard;
	}

	public void addDomino(Domino domino) {
		dominos.add(domino);
	}

	public void setChosenDomino(Domino chosenDomino) {
		this.chosenDomino = chosenDomino;
	}
}

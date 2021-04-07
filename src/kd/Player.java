package kd;

import java.util.Scanner;

public class Player {
	private PlayArea playArea = new PlayArea();
	Scanner reader = new Scanner(System.in);

	public PlayArea getPlayArea() {
		return playArea;
	}

	// This method lets the current player specify at which XY coordinate they
	// want their domino
	// that they have just chosen to be placed. It returns a DominoPlacement
	// object.
	public DominoPlacement specifyPlacement(Domino toBePlaced) {
		int x = 0, y = 0;
		String heading;
		boolean validXYChoice = false;
		DominoPlacement thePlacement;

		// Keep asking for a coordinate until a valid one is chosen.
		while (!validXYChoice) {
			x = InputGatherer.gatherInt("Specify an X coord");
			y = InputGatherer.gatherInt("Specify a Y coord");
			if (isntOccupied(x, y)) {
				System.out.println("You picked a valid XY location");
				validXYChoice = true;
			} else {
				System.out.println("This spot is already occupied, try again");
			}
		}
		System.out.print("Specify the heading");
	
			heading = InputGatherer.gatherString("Enter N, S, E, or W. Enter U if the domino is unplaceable.");
			
		
		thePlacement = new DominoPlacement(x, y, toBePlaced, heading);
		return thePlacement;

	}

	// Do one turn, i.e. have players specify where they want the Domino to be
	// placed, check if it is a legal placement,
	// and modify the Players PlayArea to reflect that choice. These are all
	// done using seperate methods.
	public void doTurn(Domino chosenDomino) {
		boolean validChoice;
		DominoPlacement currentPlacement;
		playArea.setBoardBounds();
		playArea.printBoard();

		chosenDomino.printADomino();
		validChoice = false;
		while (!validChoice) {

			currentPlacement = specifyPlacement(chosenDomino);

			if (currentPlacement.heading.equals("U")) {
				System.out.print("Too bad");
				validChoice = true;

			} else {
				if (isLegalPlacement(currentPlacement)) {

					makePlacement(currentPlacement);
					validChoice = true;

				} else {
					System.out.println("This is not a legal placement.");

				}
			}

		}
	}

	// this method places the chosen Domino "toBePlaced" at the specified XY
	// location, facing the direction of the heading. It does not
	// check if it is a legal placement.
	public void makePlacement(DominoPlacement thePlacement) {
		int x = thePlacement.x;
		int y = thePlacement.y;
		Domino toBePlaced = thePlacement.toBePlaced;
		String heading = thePlacement.heading;
		switch (heading) {

		case ("S"):

			playArea.getPlayerBoard()[x][y] = toBePlaced.getPivot();
			playArea.getPlayerBoard()[x][y - 1] = toBePlaced.getLeft();

			break;
		case ("N"):
			playArea.getPlayerBoard()[x][y] = toBePlaced.getPivot();
			playArea.getPlayerBoard()[x][y + 1] = toBePlaced.getLeft();

			break;
		case ("E"):

			playArea.getPlayerBoard()[x][y] = toBePlaced.getPivot();
			playArea.getPlayerBoard()[x - 1][y] = toBePlaced.getLeft();

			break;
		case ("W"):

			playArea.getPlayerBoard()[x][y] = toBePlaced.getPivot();
			playArea.getPlayerBoard()[x + 1][y] = toBePlaced.getLeft();

			break;
		}
	}

	// This method checks if a certain location on in the player's area is
	// already occupied by a Domino. Used when the player is placing a Domino,
	// to
	// make sure that the one they are placing doesn't overlap with an already
	// laid Domino.
	public boolean isntOccupied(int x, int y) {
		if (playArea.getPlayerBoard()[x][y].getName() != "B") {

			return false;
		} else {

			return true;
		}
	}

	// In a game of KingDomino, a Domino being laid has to have have at least
	// one
	// of its terrain types matching the terrain type of a Domino
	// adjacent to where it's being laid, unless is being laid next to the
	// Castle terrain type, denoted by
	// "C", which is a wild card terrain type. This method checks for adjacent
	// matching terrain.
	public boolean hasMatchingAdjacentTerrain(DominoPlacement thePlacement) {
		int x = thePlacement.x;
		int y = thePlacement.y;
		Domino toBePlaced = thePlacement.toBePlaced;
		String heading = thePlacement.heading;
		// for the specified heading, check for matching terrain all squares
		// adjacent to the pivot square not including the non pivot, and all
		// squares adjacent to the non
		// pivot, not including the pivot.
		switch (heading) {
		case "S":
			for (int i = -1; i <= 1; i += 2) {
				if (checkAdjascentTerrain(x + i, y, toBePlaced) || checkAdjascentTerrain(x, y + 1, toBePlaced)) {
					return true;
				} else if (checkAdjascentTerrain(x + i, y - 1, toBePlaced)
						|| checkAdjascentTerrain(x, y - 2, toBePlaced)) {
					return true;
				}
			}
			System.out.println("The terrain types don't match, try again.");
			break;
		case "N":
			for (int i = -1; i <= 1; i += 2) {
				if (checkAdjascentTerrain(x + i, y, toBePlaced) || checkAdjascentTerrain(x, y - 1, toBePlaced)) {
					return true;
				} else if (checkAdjascentTerrain(x + i, y + 1, toBePlaced)
						|| checkAdjascentTerrain(x, y + 2, toBePlaced)) {
					return true;
				}
			}
			System.out.println("The terrain types don't match, try again.");
			break;
		case "E":
			for (int i = -1; i <= 1; i += 2) {
				if (checkAdjascentTerrain(x, y + i, toBePlaced) || checkAdjascentTerrain(x + 1, y, toBePlaced)) {
					return true;
				} else if (checkAdjascentTerrain(x - 1, y + i, toBePlaced)
						|| checkAdjascentTerrain(x - 2, y, toBePlaced)) {
					return true;
				}
			}
			System.out.println("The terrain types don't match, try again.");
			break;
		case "W":
			for (int i = -1; i <= 1; i += 2) {
				if (checkAdjascentTerrain(x, y + i, toBePlaced) || checkAdjascentTerrain(x - 1, y, toBePlaced)) {
					return true;
				} else if (checkAdjascentTerrain(x + 1, y + i, toBePlaced)
						|| checkAdjascentTerrain(x + 2, y, toBePlaced)) {
					return true;
				}
			}
			System.out.println("The terrain types don't match, try again.");
			break;
		}
		return false;
	}

	// This method uses the hasMatchingAdjacentTerrain method to check if the
	// tile being placed has adjacent matching terrain, and if the tile isn't
	// overlapping with an already placed tile
	public boolean isLegalPlacement(DominoPlacement thePlacement) {
		int x = thePlacement.x;
		int y = thePlacement.y;
		String heading = thePlacement.heading;
		switch (heading) {
		case "S":
			if (hasMatchingAdjacentTerrain(thePlacement) && isntOccupied(x, y - 1))
				return true;
			else {

				return false;
			}
		case "N":
			if (hasMatchingAdjacentTerrain(thePlacement) && isntOccupied(x, y + 1))
				return true;
			else
				return false;
		case "E":
			if (hasMatchingAdjacentTerrain(thePlacement) && isntOccupied(x - 1, y))
				return true;
			else
				return false;
		case "W":
			if (hasMatchingAdjacentTerrain(thePlacement) && isntOccupied(x + 1, y))
				return true;
			else
				return false;

		}
		return false;

	}

	// This method is used by hasAdjacentMatching terrain. It checks a specific
	// Square on a Player's board,
	// and compares it to a Domino toBePlaced, checking if that Domino shares
	// any terrain types with that Square.
	public boolean checkAdjascentTerrain(int x, int y, Domino toBePlaced) {
		if (playArea.getPlayerBoard()[x][y].getName() == toBePlaced.getPivot().getName()
				|| playArea.getPlayerBoard()[x][y].getName() == toBePlaced.getLeft().getName()
				|| playArea.getPlayerBoard()[x][y].getName() == "C") {
			return true;
		} else
			return false;
	}

}

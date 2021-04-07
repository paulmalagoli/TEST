package kd;

//A class representing a placement in a turn of kingdomino. It hold the domino to be placed, the xy coordinate that it is being placed at, and the heading of the domino.
//It does not check if the move is legal.
public class DominoPlacement {
	int x;
	int y;
	Domino toBePlaced;
	String heading;
	public DominoPlacement(int x, int y, Domino toBePlaced, String heading){
		this.x = x;
		this.y = y;
		this.toBePlaced = toBePlaced;
		this.heading = heading;
	}
}

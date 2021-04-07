package kd;

import java.util.Random;

//This class represents a single Turn in KingDomino
public class Turn {
	private DominoSupply theSupply = new DominoSupply();
	private Domino[] toBeChosenFrom;
	
	public void setTurnSize(int x){
		toBeChosenFrom = new Domino[x];
	}
	
	public Domino[] getToBeChosenFrom(){
		return toBeChosenFrom;
	}
	
	//In kingdomino, each domino has a numerical value. Higher value dominoes are
	//generally better than lower ones, i.e. they have more point scoring "crowns". When setting out the dominos to be chosen from for
	//the current turn, they are set out in numerical order.
	public void setToBeChosenFrom(){

		for (int i = 0; i < toBeChosenFrom.length; i++){
			toBeChosenFrom[i] = giveRandomDomino();
		}
		 for (int i = 0; i < toBeChosenFrom.length - 1; i++)
	        {
	            int index = i;
	            for (int j = i + 1; j < toBeChosenFrom.length; j++)
	                if (toBeChosenFrom[j].getNumber() < toBeChosenFrom[index].getNumber()) 
	                    index = j;
	      
	            Domino smallerDom = toBeChosenFrom[index];  
	            toBeChosenFrom[index] = toBeChosenFrom[i];
	            toBeChosenFrom[i] = smallerDom;
	        }
		
	}
	
	//Gives a random domino that will be part of the current Turn.
	public Domino giveRandomDomino() {
		Domino theTurn = new Domino();
		boolean foundflag = true;
		while (foundflag) {
			int rnd = new Random().nextInt(theSupply.length() - 1);
			if (theSupply.getUnplayed()[rnd].getPlayed() == false) {
				theTurn = theSupply.getUnplayed()[rnd];
				theSupply.getUnplayed()[rnd].setPlayed();
				foundflag = false;
			}
		}
		return theTurn;
	}
}

package ultimateTicTacToe;

import javafx.scene.layout.GridPane;

public class OuterBoard {

	Game gameInstance;
	InnerBoard[][] gameSpace;
	int nextPlay;
	int turns = 0;
	
	OuterBoard(GridPane node, int squareSpace, Game instance) {
		gameInstance = instance;
		gameSpace = new InnerBoard[3][3];
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				gameSpace[row][col] = new InnerBoard(3 * row + col, node, squareSpace, this); // int div 3 of num = row, mod 3 of num = col
			}
		}
		nextPlay = -1;
	}
	
	public void gridWon(char winVal, int gamePos) {
		
		checkWinner();
	}
	
	public void checkWinner() {
		
	}
	
	public int getNextPlay() {
		return nextPlay;
	}
	
	public int getTurns() {
		return turns;
	}
	
	public void setNextLabel() {
		
		if(nextPlay == -1) {
			gameInstance.setNext("Next Position: Any          ");
			return;
		}
		
		String ident = "";
		String buffer = "";
		switch(nextPlay / 3) {
		case 0:
			ident += "Top ";
			buffer += "   ";
			break;
		case 1:
			ident += "Middle ";
			break;
		case 2:
			ident += "Bottom ";
			break;
		}
		
		switch(nextPlay % 3) {
		case 0:
			ident += "Left  ";
			break;
		case 1:
			ident += "Center";
			break;
		case 2:
			ident += "Right ";
			break;
		}
		
		gameInstance.setNext("Next Square: " + ident + buffer);
		
	}
	
	public void setTurnLabel() {
		if(turns % 2 == 0)
			gameInstance.setTurn("Current Turn: X");
		else
			gameInstance.setTurn("Current Turn: O");
	}
	
	public void setNextPlay(int playedPos) {
		int nextRow = (playedPos / 3);
		int nextCol = playedPos % 3;
		
		if(gameSpace[nextRow][nextCol].getWon())
			nextPlay = -1;
		else
			nextPlay = playedPos;
		
		turns++;
		setNextLabel();
		setTurnLabel();
	}
}

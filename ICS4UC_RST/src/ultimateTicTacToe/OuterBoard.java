package ultimateTicTacToe;

import javafx.scene.layout.GridPane;

public class OuterBoard {

	InnerBoard[][] gameSpace;
	int nextPlay;
	int turns = 0;
	
	OuterBoard(GridPane node) {
		gameSpace = new InnerBoard[3][3];
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				gameSpace[row][col] = new InnerBoard(3 * row + col, node, this); // int div 3 of num = row, mod 3 of num = col
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
	
	public void setNextPlay(int playedPos) {
		int nextRow = (playedPos / 3);
		int nextCol = playedPos % 3;
		
		if(gameSpace[nextRow][nextCol].getWon())
			nextPlay = -1;
		else
			nextPlay = playedPos;
		
		turns++;
	}
}

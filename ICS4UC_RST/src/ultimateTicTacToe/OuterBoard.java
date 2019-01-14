package ultimateTicTacToe;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class OuterBoard {

	Main gameInstance;
	InnerBoard[][] gameSpace;
	boolean gameOver = false;
	int nextPlay;
	int turns = 0;
	char curTurn = 'X';
	
	OuterBoard(GridPane node, int squareSpace, Main instance) {
		gameInstance = instance;
		gameSpace = new InnerBoard[3][3];
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				gameSpace[row][col] = new InnerBoard(3 * row + col, node, squareSpace, this); // int div 3 of num = row, mod 3 of num = col
			}
		}
		nextPlay = -1;
		setNextHighlight(0, 0);
	}
	
	public void gridWon(char winVal, int gamePos) {
		checkWinner();
	}
	
	public void reset() {
		
	}
	
	public void checkWinner() {
		//Row check
		for(int row = 0; row < 3; row++) {
			if(gameSpace[row][0].getWonVal() == gameSpace[row][1].getWonVal() && gameSpace[row][0].getWonVal() == gameSpace[row][2].getWonVal() && gameSpace[row][0].getWonVal() != ' ')
				gameOver = true;
		}
		
		//Col check
		for(int col = 0; col < 3; col++) {
			if(gameSpace[0][col].getWonVal() == gameSpace[1][col].getWonVal() && gameSpace[0][col].getWonVal() == gameSpace[2][col].getWonVal() && gameSpace[0][col].getWonVal() != ' ')
				gameOver = true;
		}
		
		//Diag check
		if(gameSpace[0][0].getWonVal() == gameSpace[1][1].getWonVal() && gameSpace[0][0].getWonVal() == gameSpace[2][2].getWonVal() || 
				gameSpace[0][2].getWonVal() == gameSpace[1][1].getWonVal() && gameSpace[0][2].getWonVal() == gameSpace[2][0].getWonVal())
			if(gameSpace[1][1].getWonVal() != ' ') {
				gameOver = true;
		}
		
		if(gameOver) {
			gameInstance.gameOver();
		}
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
	
	public boolean getGameState() {
		return gameOver;
	}
	
	public void setTurnLabel() {
		Color nextCol;
		if(turns % 2 == 0) {
			curTurn = 'X';
			nextCol = Color.RED;
		}
		else {
			curTurn = 'O';
			nextCol = Color.BLUE;
		}
		
		gameInstance.setTurn(Character.toString(curTurn), nextCol);
	}
	
	public void setNextHighlight(int nextRow, int nextCol) {
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++)
				gameSpace[i][j].setHighLight(' ');
		}
		
		if(nextPlay == -1) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++)
					gameSpace[i][j].setHighLight(curTurn);
			}
		}
		else
			gameSpace[nextRow][nextCol].setHighLight(curTurn);
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
		setNextHighlight(nextRow, nextCol);
	}
}

package ultimateTicTacToe;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* OuterBoard.java
* An object for the Ultimate Tic-Tac-Toe Game.
*/

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class OuterBoard {

	//Main board states and vals
	int nextPlay;
	int turns = 0;
	char gameWinner = ' ';
	char curTurn = 'X';
	boolean gameOver = false;
	
	//Main instance and InnerBoard grid
	Main gameInstance;
	InnerBoard[][] gameSpace;
	
	/**
	 * Creates a new outer board.
	 * @param node
	 *			The GridPane to place all graphics of smaller boards inside.
	 * @param squareSpace
	 *			The spacing between square in the InnerBoards.
	 * @param instance
	 *			An instance of the main class to be used in methods later.
	 */
	OuterBoard(GridPane node, int squareSpace, Main instance) {
		//Globalizes and initializes variables
		gameInstance = instance;
		gameSpace = new InnerBoard[3][3];
		
		//Creates a grid of InnerBoard objects for the gameSpace grid
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				gameSpace[row][col] = new InnerBoard(3 * row + col, node, squareSpace, this); // int div 3 of num = row, mod 3 of num = col
			}
		}
		
		//Sets next play and highlight to anywhere
		nextPlay = -1;
		setNextHighlight(0, 0);
	}

	/**
	 * InnerBoard notifying that it has been won, checks overall game for winner.
	 */
	public void gridWon() {
		checkWinner();
	}
	
	/**
	 * Resets game by resetting inner board grid and game states.
	 */
	public void reset() {
		//Resets game states and vals to default
		turns = 0;
		gameOver = false;
		curTurn = 'X';
		nextPlay = -1;
		
		//Resets current turn and next play position text
		setTurnLabel();
		setNextLabel();
		
		//Resets all InnerBoard objects in grid
		for(InnerBoard[] row : gameSpace) {
			for(InnerBoard board : row)
				board.reset();
		}
		
		//Resets next play highlight
		setNextHighlight(0, 0);
	}
	
	public void checkWinner() {
		//Row check
		for(int row = 0; row < 3; row++) {
			if(gameSpace[row][0].getWonVal() == gameSpace[row][1].getWonVal() && gameSpace[row][0].getWonVal() == gameSpace[row][2].getWonVal()) {
				if(gameSpace[row][0].getWonVal() != ' ' && gameSpace[row][0].getWonVal() != 'T') {
					gameOver = true;
					gameWinner = gameSpace[row][0].getWonVal();
				}
			}
		}
		
		//Col check
		for(int col = 0; col < 3; col++) {
			if(gameSpace[0][col].getWonVal() == gameSpace[1][col].getWonVal() && gameSpace[0][col].getWonVal() == gameSpace[2][col].getWonVal()) {
				if(gameSpace[0][col].getWonVal() != ' ' && gameSpace[0][col].getWonVal() != 'T') {
					gameOver = true;
					gameWinner = gameSpace[0][col].getWonVal();
				}
			}
		}
		
		//Diag check
		if(gameSpace[0][0].getWonVal() == gameSpace[1][1].getWonVal() && gameSpace[0][0].getWonVal() == gameSpace[2][2].getWonVal() || 
				gameSpace[0][2].getWonVal() == gameSpace[1][1].getWonVal() && gameSpace[0][2].getWonVal() == gameSpace[2][0].getWonVal())
			if(gameSpace[1][1].getWonVal() != ' ' && gameSpace[1][1].getWonVal() != 'T') {
				gameOver = true;
				gameWinner = gameSpace[1][1].getWonVal();
		}
		
		//Tie Check
		boolean allPlayed = true;
		for(InnerBoard[] row : gameSpace) {
			for(InnerBoard board : row) {
				if(!board.getWon())
					allPlayed = false;
			}
		}
		if(allPlayed && !gameOver) {
			gameOver = true;
			gameWinner = 'T';
		}
		
		if(gameOver) {
			gameInstance.gameOver();
		}
	}
	
	/**
	 * Returns the next InnerBoard position to be played on.
	 * @return
	 *			The next InnerBoard position to be played on. Counts L to R, T to B (0-8). -1 = anywhere.
	 */
	public int getNextPlay() {
		return nextPlay;
	}
	
	/**
	 * Gets the current turn number.
	 * @return
	 *			The current turn number.
	 */
	public int getTurns() {
		return turns;
	}
	
	/**
	 * Sets the text indicating what the next play position is.
	 */
	public void setNextLabel() {
		
		//Sets text if next play is anywhere
		if(nextPlay == -1) {
			gameInstance.setNext("Next Position: Any          ");
			return;
		}
		
		//Identifier for next row, buffer to keep text aligned with smaller words
		String ident = "";
		String buffer = "";
		
		//Determines next row, changes row identifier and buffer accordingly
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
		
		//Identifies next column, adds column identifier to ident string
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
		
		//Sets next position text using ident and buffer
		gameInstance.setNext("Next Square: " + ident + buffer);
	}
	
	/**
	 * Returns the current board won state.
	 * @return
	 *			True if game is over, false if game is still playing.
	 */
	public boolean getGameState() {
		return gameOver;
	}
	
	/**
	 * Sets text for who's turn it is.
	 */
	public void setTurnLabel() {
		//The next player's colour
		Color nextCol;
		
		//If next player is 'X'
		if(turns % 2 == 0) {
			curTurn = 'X';
			nextCol = Color.RED;
		}
		//Otherwise it is 'O'
		else {
			curTurn = 'O';
			nextCol = Color.BLUE;
		}
		
		//Sets the text for the next player using the determined char and colour
		gameInstance.setTurn(Character.toString(curTurn), nextCol);
	}

	/**
	 * Sets the InnerBoard highlights to show the players where to play next
	 * @param nextRow
	 *			The next row to be played in.
	 * @param nextCol
	 *			The next col to be played in.
	 */
	public void setNextHighlight(int nextRow, int nextCol) {
		
		//Sets all highlights to blank to start
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++)
				gameSpace[i][j].setHighLight(' ');
		}
		
		//If next play is anywhere, sets all highlights (except won squares) to next player's colour
		if(nextPlay == -1) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++)
					if(!gameSpace[i][j].getWon())
						gameSpace[i][j].setHighLight(curTurn);
			}
		}
		//Otherwise sets next InnerBoard to be played in to next player's colour
		else
			gameSpace[nextRow][nextCol].setHighLight(curTurn);
	}
	
	/**
	 * Returns the winner of the game.
	 * @return
	 *			The char of the player who won the game.
	 */
	public char getWinner() {
		return gameWinner;
	}
	
	/**
	 * Sets the next board to be played in based on the current game state and the last square position played.
	 * @param playedPos
	 *			The square position of the last play.
	 */
	public void setNextPlay(int playedPos) {
		//Gets the next row and col to be played in
		int nextRow = (playedPos / 3);
		int nextCol = playedPos % 3;
		
		//If space is already won, next play is anywhere
		if(gameSpace[nextRow][nextCol].getWon())
			nextPlay = -1;
		else
			nextPlay = playedPos;
		
		//Increases turns, updates next play labels and InnerBoard highlights
		turns++;
		setNextLabel();
		setTurnLabel();
		setNextHighlight(nextRow, nextCol);
	}
}

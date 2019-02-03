package ultimateTicTacToe;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* InnerBoard.java
* An object for the Ultimate Tic-Tac-Toe Game.
*/

import javafx.event.Event;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InnerBoard {

	//Inner board state objects
	int superPos;
	char wonVal = ' ';
	boolean won;
	Square[][] grid;
	
	//Container / gfx objects and constants
	Pane innerPane;
	OuterBoard container;
	GridPane innerNode;
	Rectangle highLight;
	static int SQUARE_SPACE = 10;
	static double HL_PADDING = 3.0;
	static double HL_OPACITY = 1;
	
	/**
	 * Creates a new inner tic-tac-toe board.
	 * @param superPos
	 *			The inner board's position in the main board. Counted Left to Right, Top to Bottom (0-9)
	 * @param node
	 *			The main board GridPane this inner board is contained in.
	 * @param squareSpace
	 *			The spacing between squares in this board.
	 * @param gameSpace
	 *			The main board object this inner board is a part of.
	 */
	InnerBoard(int superPos, GridPane node, int squareSpace, OuterBoard gameSpace) {
		
		//Initializes board values
		this.superPos = superPos;
		container = gameSpace;
		SQUARE_SPACE = squareSpace;
		grid = new Square[3][3];
		innerInit();
		
		//Initializes the square grid and adds them to the GridPane
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				grid[row][col] = new Square(3 * row + col);
				grid[row][col].setOnAction(e -> playSquare(e));
				innerNode.add(grid[row][col], col, row);
			}
		}
		
		//Adds the GridPane and highlight to a pane, adds this pane to the main board
		innerPane = new Pane();
		innerPane.getChildren().addAll(highLight, innerNode);
		node.add(innerPane, (superPos % 3), (superPos / 3));
	}
	
	/**
	 * Initializes this board's GridPane and rectangle highlight objects.
	 */
	private void innerInit() {
		innerNode = new GridPane();
		innerNode.setVgap(SQUARE_SPACE);
		innerNode.setHgap(SQUARE_SPACE);
		highLight = new Rectangle(-HL_PADDING, -HL_PADDING, 157 + (2 * HL_PADDING), 157 + (2 * HL_PADDING));
		highLight.setOpacity(0.0);
	}
	
	/**
	 * Attempts to play a square when it is clicked on.
	 * @param e
	 *			The event of a square being clicked on.
	 */
	private void playSquare(Event e) {
		//Gets the square that was clicked
		Square temp = (Square)e.getSource();
		
		//Only will play square if this grid is set for next play and square is blank
		if(won || (superPos != container.getNextPlay() && container.getNextPlay() != -1) || temp.getValue() != ' ' || container.getGameState()) {
			return;
		}
		
		//Plays X or O depending on who's turn it is
		if(container.getTurns() % 2 == 0)
			temp.playSquare('X');
		else
			temp.playSquare('O');
		
		//Checks if this board is won and then sets next play based on what square position was clicked
		checkWinner();
		container.setNextPlay(temp.getPos());
	}

	/**
	 * Sets the inner board graphic for when a square is won or tied.
	 */
	private void wonGraphic() {
		
		//If this board is won by 'X'
		if(wonVal == 'X') {
			//Sets the squares in a red X pattern
			for(Square[] row: grid) {
				for(Square square: row)
					square.setRed();
			}
			grid[0][1].setBlank();
			grid[1][0].setBlank();
			grid[2][1].setBlank();
			grid[1][2].setBlank();
		}
		
		//If this board is won by 'O'
		if(wonVal == 'O') {
			//Sets the squares in a blue O pattern
			for(Square[] row: grid) {
				for(Square square: row)
					square.setBlue();
			}
			grid[1][1].setBlank();
		}
		
		//If this board is tied
		if(wonVal == 'T') {
			//Sets the squares in a purple T pattern
			for(Square[] row: grid) {
				for(Square square: row)
					square.setPurple();
			}
			grid[1][0].setBlank();
			grid[2][0].setBlank();
			grid[2][2].setBlank();
			grid[1][2].setBlank();
		}
	}
	
	/**
	 * Sets the highlight behind this inner grid to indicate to player where to play.
	 * @param turn
	 *			The person who can currently play in this grid (determines highlight colour).
	 */
	public void setHighLight(char turn) {
		//Sets the highlight to be seen
		highLight.setOpacity(HL_OPACITY);
		
		//Sets colour based on who can play in this brid (clear if not currently playable)
		switch(turn) {
		case 'X':
			highLight.setFill(Color.RED);
			break;
		case 'O':
			highLight.setFill(Color.BLUE);
			break;
		default:
			highLight.setOpacity(0.0);
			break;
		}
	}
	
	/**
	 * Checks if this board is won or tied with the current square values.
	 */
	private void checkWinner() {
	
		//Checks every row
		for(int row = 0; row < 3; row++) {
			//Checks if a player has all of a row filled
			if(grid[row][0].getValue() == grid[row][1].getValue() && grid[row][0].getValue() == grid[row][2].getValue() && grid[row][0].getValue() != ' ') {
				//Wins the square for the player that filled the row
				won = true;
				wonVal = grid[row][0].getValue();
				container.gridWon();
				wonGraphic();
			}
		}
		//Checks every column
		for(int col = 0; col < 3; col++) {
			//Checks if a player has all of a col filled
			if(grid[0][col].getValue() == grid[1][col].getValue() && grid[0][col].getValue() == grid[2][col].getValue() && grid[0][col].getValue() != ' ') {
				//Wins square for the player that filled the col
				won = true;
				wonVal = grid[0][col].getValue();
				container.gridWon();
				wonGraphic();
			}
		}
		
		//Checks the two diagonals
		if(grid[0][0].getValue() == grid[1][1].getValue() && grid[0][0].getValue() == grid[2][2].getValue() || 
				grid[0][2].getValue() == grid[1][1].getValue() && grid[0][2].getValue() == grid[2][0].getValue()) {
			if(grid[1][1].getValue() != ' ') {
				//Wins the square for the player that filled the diagonal
				won = true;
				wonVal = grid[1][1].getValue();
				container.gridWon();
				wonGraphic();
			}
		}
		
		//Check for a tie
		//Checks if all squares have been played 
		boolean allPlayed = true;
		for(Square[] row : grid) {
			for(Square square : row)
				if(square.getValue() == ' ')
					allPlayed = false;
		}
		//If all squares played and no winner
		if(allPlayed && !won) {
			//Wins the square for player 'T' (Tie)
			won = true;
			wonVal = 'T';
			container.gridWon();
			wonGraphic();
		}
			
	}
	
	/**
	 * Resets this board to have blank squares and states.
	 */
	public void reset() {
		//Sets won state and value to default
		won = false;
		wonVal = ' ';
		
		//Resets every square in grid
		for(Square[] row : grid) {
			for(Square square : row)
				square.reset();
		}
	}

	/**
	 * Gets the boolean of this inner grid's won status.
	 * @return
	 *			True if won, False if not won.
	 */
	public boolean getWon() {
		return won;
	}
	
	/**
	 * Gets the won value of this inner grid.
	 * @return
	 *			The char value of this grid's winner.
	 */
	public char getWonVal() {
		return wonVal;
	}
}

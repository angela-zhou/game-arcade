package ultimateTicTacToe;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class InnerBoard {

	char wonVal = ' ';
	boolean won;
	Square[][] grid;
	int superPos;
	OuterBoard container;
	GridPane innerNode;
	static int SQUARE_SPACE = 10;
	
	InnerBoard(int superPos, GridPane node, int squareSpace, OuterBoard gameSpace) {
		this.superPos = superPos;
		container = gameSpace;
		SQUARE_SPACE = squareSpace;
		grid = new Square[3][3];
		innerInit();
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				grid[row][col] = new Square(3 * row + col);
				grid[row][col].setOnAction(e -> playSquare(e));
				innerNode.add(grid[row][col], col, row);
			}
		}
		node.add(innerNode, (superPos % 3), (superPos / 3));
	}
	
	private void innerInit() {
		innerNode = new GridPane();
		innerNode.setVgap(SQUARE_SPACE);
		innerNode.setHgap(SQUARE_SPACE);
//		innerNode.setPadding(new Insets(SQUARE_SPACE, SQUARE_SPACE, SQUARE_SPACE, SQUARE_SPACE));
	}
	
	private void playSquare(Event e) {
		Square temp = (Square)e.getSource();
		
		//Only will play square if grid is okay to be played in and square is blank
		if(won || (superPos != container.getNextPlay() && container.getNextPlay() != -1) || temp.getValue() != ' ') {
			return;
		}
		
		if(container.getTurns() % 2 == 0)
			temp.playSquare('X');
		else
			temp.playSquare('O');
		
		container.setNextPlay(temp.getPos());
		checkWinner();
	}
	
	private void checkWinner() {
		//Row check
		for(int row = 0; row < 3; row++) {
			if(grid[row][0].getValue() == grid[row][1].getValue() && grid[row][0].getValue() == grid[row][2].getValue() && grid[row][0].getValue() != ' ') {
				won = true;
				wonVal = grid[row][0].getValue();
				container.gridWon(wonVal, superPos);
			}
		}
		//Col check
		for(int col = 0; col < 3; col++) {
			if(grid[0][col].getValue() == grid[1][col].getValue() && grid[0][col].getValue() == grid[2][col].getValue() && grid[0][col].getValue() != ' ') {
				won = true;
				wonVal = grid[0][col].getValue();
				container.gridWon(wonVal, superPos);
			}
				
		}
		
		//Diag check
		if(grid[0][0].getValue() == grid[1][1].getValue() && grid[0][0].getValue() == grid[2][2].getValue() || 
				grid[0][2].getValue() == grid[1][1].getValue() && grid[0][2].getValue() == grid[2][0].getValue()) {
			if(grid[1][1].getValue() != ' ') {
				won = true;
				wonVal = grid[1][1].getValue();
				container.gridWon(wonVal, superPos);
			}
		}
	}
	
	public boolean getWon() {
		return won;
	}
}

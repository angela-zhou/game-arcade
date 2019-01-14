package ultimateTicTacToe;

import javafx.event.Event;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InnerBoard {

	Pane innerPane;
	char wonVal = ' ';
	boolean won;
	Square[][] grid;
	int superPos;
	OuterBoard container;
	GridPane innerNode;
	Rectangle highLight;
	static int SQUARE_SPACE = 10;
	static double HL_PADDING = 3.0;
	static double HL_OPACITY = 1;
	
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
		innerPane = new Pane();
		innerPane.getChildren().addAll(highLight, innerNode);
		node.add(innerPane, (superPos % 3), (superPos / 3));
	}
	
	private void innerInit() {
		innerNode = new GridPane();
		innerNode.setVgap(SQUARE_SPACE);
		innerNode.setHgap(SQUARE_SPACE);
		highLight = new Rectangle(-HL_PADDING, -HL_PADDING, 157 + (2 * HL_PADDING), 157 + (2 * HL_PADDING));
		highLight.setOpacity(0.0);
	}
	
	private void playSquare(Event e) {
		Square temp = (Square)e.getSource();
		
		//Only will play square if grid is okay to be played in and square is blank
		if(won || (superPos != container.getNextPlay() && container.getNextPlay() != -1) || temp.getValue() != ' ' || container.getGameState()) {
			return;
		}
		
		if(container.getTurns() % 2 == 0)
			temp.playSquare('X');
		else
			temp.playSquare('O');
		
		container.setNextPlay(temp.getPos());
		
		checkWinner();
	}
	
	private void wonGraphic() {
		if(wonVal == 'X') {
			for(Square[] row: grid) {
				for(Square square: row)
					square.setRed();
			}
			grid[0][1].setBlank();
			grid[1][0].setBlank();
			grid[2][1].setBlank();
			grid[1][2].setBlank();
		}
		if(wonVal == 'O') {
			for(Square[] row: grid) {
				for(Square square: row)
					square.setBlue();
			}
			grid[1][1].setBlank();
		}
	}
	
	public void setHighLight(char turn) {
		highLight.setOpacity(HL_OPACITY);
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
	
	private void checkWinner() {
		//Row check
		for(int row = 0; row < 3; row++) {
			if(grid[row][0].getValue() == grid[row][1].getValue() && grid[row][0].getValue() == grid[row][2].getValue() && grid[row][0].getValue() != ' ') {
				won = true;
				wonVal = grid[row][0].getValue();
				container.gridWon(wonVal, superPos);
				wonGraphic();
			}
		}
		//Col check
		for(int col = 0; col < 3; col++) {
			if(grid[0][col].getValue() == grid[1][col].getValue() && grid[0][col].getValue() == grid[2][col].getValue() && grid[0][col].getValue() != ' ') {
				won = true;
				wonVal = grid[0][col].getValue();
				container.gridWon(wonVal, superPos);
				wonGraphic();
			}
		}
		
		//Diag check
		if(grid[0][0].getValue() == grid[1][1].getValue() && grid[0][0].getValue() == grid[2][2].getValue() || 
				grid[0][2].getValue() == grid[1][1].getValue() && grid[0][2].getValue() == grid[2][0].getValue()) {
			if(grid[1][1].getValue() != ' ') {
				won = true;
				wonVal = grid[1][1].getValue();
				container.gridWon(wonVal, superPos);
				wonGraphic();
			}
		}
	}
	
	public void reset() {
		won = false;
		wonVal = ' ';
		for(Square[] row : grid) {
			for(Square square : row)
				square.reset();
		}
	}
	
	public boolean getWon() {
		return won;
	}
	
	public char getWonVal() {
		return wonVal;
	}
}

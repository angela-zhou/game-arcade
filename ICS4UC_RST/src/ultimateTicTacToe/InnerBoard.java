package ultimateTicTacToe;

public class InnerBoard {

	boolean won;
	Square[][] grid;
	
	InnerBoard() {
		won = false;
		grid = new Square[3][3];
	}
	
}

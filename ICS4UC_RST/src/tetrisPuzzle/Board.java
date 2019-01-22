package tetrisPuzzle;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * Board.java 
 */
public class Board extends GridPane {

	//dimensions of the play board
	public static int NUM_COLUMNS;
	public static int NUM_ROWS;

	public static final double GAP = 2;

	public static boolean FIRST_TURN = true;

	private PlaceHolder[][] squares;

	public Board() {
		super();
		setAlignment(Pos.CENTER);
		setHgap(GAP);
		setVgap(GAP);

		//may make this feature user-defined
		NUM_COLUMNS = 8;
		NUM_ROWS = 8;
	}
		
//		// create the grid of squares
//		squares = new PlaceHolder[NUM_ROWS][NUM_COLUMNS];
//
//		for (int row = 0; row < NUM_ROWS; row++) {
//			for (int col = 0; col < NUM_COLUMNS; col++) {
//				PlaceHolder blockHolder = new PlaceHolder(row, col, this);
//
//				blockHolder.setOnMouseDragEntered(event -> {
//					TetrisBlock blockInPlay = (TetrisBlock) event.getGestureSource();
//					if (FIRST_TURN || validPlacement(blockInPlay, blockHolder)) blockHolder.setEffect(new DropShadow());
//
//				});
//
//				blockHolder.setOnMouseDragReleased(event -> {
//					TetrisBlock  blockInPlay = (TetrisBlock ) event.getGestureSource();
//					if (FIRST_TURN || validPlacement(blockInPlay, blockHolder)) {
//
//						// place on the board
//
//						// calculate score
//
//						// after first tile laid, all other tiles must follow valid placement rules
//						FIRST_TURN = false;
//
//					}
//					blockInPlay.setCursor(null);
//				});
//
//				blockHolder.setOnMouseDragExited(event -> blockHolder.setEffect(null));
//
//				squares[row][col] = blockHolder;
//
//				add(squares[row][col], col, row);
//
//			}
//		}
//	}

	private boolean validPlacement(TetrisBlock blockToBePlaced, PlaceHolder blockHolder) {
		boolean isValid = false;
		
		
		return isValid;
	}
}


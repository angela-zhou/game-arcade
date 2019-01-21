package tetrisPuzzle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * SquareBlock.java
 */
public class SquareBlock extends TetrisBlock {
	
	public SquareBlock() {
		super();
	}

	@Override
	public void draw() {
		/**
		 * Initialize squares
		 */
		// Initialize array
		Rectangle[] sq = new Rectangle[NUM_SQUARES];
		// set coordinates and side lengths
		// Each square has its own x and y
		sq[0] = new Rectangle(   0,    0, SIZE, SIZE);
		sq[1] = new Rectangle(   0, SIZE, SIZE, SIZE);
		sq[2] = new Rectangle(SIZE,    0, SIZE, SIZE);
		sq[3] = new Rectangle(SIZE, SIZE, SIZE, SIZE);
		
		/**
		 * Draw squares
		 */
		// Fill Squares
		for (int i = 0; i < sq.length; i++) {
			sq[i].setFill(colour);
		}
		// Set Stroke
		for (int i = 0; i < sq.length; i++) {
			sq[i].setStroke(Color.BLACK);
		}
		
		/**
		 * Add to root
		 */
		setLayoutX(x);
		setLayoutY(y);
		getChildren().addAll(sq);
	}

	@Override
	public String toString() {
		return "O Block";
	}
}

package tetrisPuzzle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * LineBlock.java
 */
public class LineBlock extends TetrisBlock implements Orientable {

	public LineBlock(int angle) {
		super();
		setOrientation(angle);
	}

	@Override
	public void draw() {
		/**
		 * Initialize squares
		 */
		// Initialize array
		Rectangle[] sq = new Rectangle[NUM_SQUARES];

		/**
		 * Draw squares according to orientation
		 */
		// set coordinates and side lengths
		switch (orientation) {
		case 0:
			for (int i = 0; i < sq.length; i++) {
				sq[i] = new Rectangle(SIZE * i, 0, SIZE, SIZE);
			}
			break;
		default: // case 90
			for (int i = 0; i < sq.length; i++) {
				sq[i] = new Rectangle(0, SIZE * i, SIZE, SIZE);
			}
			break;
		}
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
		return "I Block";
	}

	@Override
	public void setOrientation(int angle) {
		orientation = angle;
	}
}
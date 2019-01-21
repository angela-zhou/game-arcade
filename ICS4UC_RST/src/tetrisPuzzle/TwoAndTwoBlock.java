package tetrisPuzzle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * TwoAndTwoBlock.java
 */
public class TwoAndTwoBlock extends OffsetBlock implements Orientable {

	public TwoAndTwoBlock(int shift, int angle) {
		super(shift);
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
		switch (orientation) {
		case 0:
			switch (offset) {
			case 1:
				sq[0] = new Rectangle(SIZE * 1,    0, SIZE, SIZE);
				sq[1] = new Rectangle(SIZE * 2,    0, SIZE, SIZE);
				sq[2] = new Rectangle(SIZE * 0, SIZE, SIZE, SIZE);
				sq[3] = new Rectangle(SIZE * 1, SIZE, SIZE, SIZE);
				break;
			default: // case -1
				sq[0] = new Rectangle(SIZE * 0,    0, SIZE, SIZE);
				sq[1] = new Rectangle(SIZE * 1,    0, SIZE, SIZE);
				sq[2] = new Rectangle(SIZE * 1, SIZE, SIZE, SIZE);
				sq[3] = new Rectangle(SIZE * 2, SIZE, SIZE, SIZE);
				break;
			}
			break;
		case 90:
			switch (offset) {
			case 1:
				sq[0] = new Rectangle(   0, SIZE * 0, SIZE, SIZE);
				sq[1] = new Rectangle(   0, SIZE * 1, SIZE, SIZE);
				sq[2] = new Rectangle(SIZE, SIZE * 1, SIZE, SIZE);
				sq[3] = new Rectangle(SIZE, SIZE * 2, SIZE, SIZE);
				break;
			default: // case -1
				sq[0] = new Rectangle(SIZE, SIZE * 0, SIZE, SIZE);
				sq[1] = new Rectangle(SIZE, SIZE * 1, SIZE, SIZE);
				sq[2] = new Rectangle(   0, SIZE * 1, SIZE, SIZE);
				sq[3] = new Rectangle(   0, SIZE * 2, SIZE, SIZE);
				break;
			}
			break;
		default: // if offset == 1 && orientation == 0
			sq[0] = new Rectangle(SIZE * 1,    0, SIZE, SIZE);
			sq[1] = new Rectangle(SIZE * 2,    0, SIZE, SIZE);
			sq[2] = new Rectangle(SIZE * 0, SIZE, SIZE, SIZE);
			sq[3] = new Rectangle(SIZE * 1, SIZE, SIZE, SIZE);
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
		String block;
		
		if (offset == -1) {
			block = "Z Block";
		} else { // if offset == 1
			block = "S Block";
		}
		return block;
	}

	@Override
	public void setOrientation(int angle) {
		orientation = angle;
	}

}


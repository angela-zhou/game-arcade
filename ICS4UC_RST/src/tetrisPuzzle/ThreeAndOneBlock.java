package tetrisPuzzle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * ThreeAndOneBlock.java
 */
public class ThreeAndOneBlock extends OffsetBlock implements Orientable {
	final private int OFFSET_LEN = SIZE * 2;

	public ThreeAndOneBlock(int shift, int angle) {
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
		 * Draw squares according to orientation (must set x and y of 3 squares first)
		 */
		switch (orientation) {
		case 0:
			for (int i = 0; i < sq.length - 1; i++) {
				sq[i] = new Rectangle(SIZE * i, 0, SIZE, SIZE);
			}
			// draw last square depending on offset
			switch (offset) {
			case 1:
				sq[3] = new Rectangle(0, SIZE, SIZE, SIZE);
				break;
			case 2:
				sq[3] = new Rectangle(SIZE, SIZE, SIZE, SIZE);
				break;
			default: // case 3
				sq[3] = new Rectangle(OFFSET_LEN, SIZE, SIZE, SIZE);
				break;
			}
			break;
		case 90:
			for (int i = 0; i < sq.length - 1; i++) {
				sq[i] = new Rectangle(0, SIZE * i, SIZE, SIZE);
			}
			// shift square depending on offset
			switch (offset) {
			case 1:
				sq[3] = new Rectangle(SIZE, OFFSET_LEN, SIZE, SIZE);
				break;
			case 2:
				sq[3] = new Rectangle(SIZE, SIZE, SIZE, SIZE);
				break;
			default: // case 3
				sq[3] = new Rectangle(SIZE, 0, SIZE, SIZE);
				break;
			}
			break;
		case 180:
			for (int i = 0; i < sq.length - 1; i++) {
				sq[i] = new Rectangle(SIZE * i, SIZE, SIZE, SIZE);
			}
			// shift square depending on offset
			switch (offset) {
			case 1:
				sq[3] = new Rectangle(OFFSET_LEN, 0, SIZE, SIZE);
				break;
			case 2:
				sq[3] = new Rectangle(SIZE, 0, SIZE, SIZE);
				break;
			default: // case 3
				sq[3] = new Rectangle(0, 0, SIZE, SIZE);
				break;
			}
			break;
		case 270:
			for (int i = 0; i < sq.length - 1; i++) {
				sq[i] = new Rectangle(SIZE, SIZE * i, SIZE, SIZE);
			}
			// shift square depending on offset
			switch (offset) {
			case 1:
				sq[3] = new Rectangle(0, 0, SIZE, SIZE);
				break;
			case 2:
				sq[3] = new Rectangle(0, SIZE, SIZE, SIZE);
				break;
			default: // case 3
				sq[3] = new Rectangle(0, OFFSET_LEN, SIZE, SIZE);
				break;
			}
			break;
		default: // if offset == 1 && orientation == 0
			sq[0] = new Rectangle(SIZE * 0, 0, SIZE, SIZE);
			sq[1] = new Rectangle(SIZE * 1, 0, SIZE, SIZE);
			sq[2] = new Rectangle(SIZE * 2, 0, SIZE, SIZE);
			sq[3] = new Rectangle(SIZE * 0, SIZE, SIZE, SIZE);
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
		
		if (offset == 1) {
			block = "L Block";
		} else if (offset == 2) {
			block = "T Block";
		} else { // if offset == 3
			block = "J Block";
		}
		return block;
	}

	@Override
	public void setOrientation(int angle) {
		orientation = angle;
	}
}
package tetrisPuzzle;

import javafx.scene.Parent;
import javafx.scene.paint.Color;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * TetrisBlock.java
 */
public abstract class TetrisBlock extends Parent {
	public final static int SIZE        = 25;
	public final static int NUM_SQUARES = 4;
	protected int orientation;
	protected int x;
	protected int y;
	protected Color colour;
	
	public TetrisBlock() {
		setLocation(0, 0);
		setColour();
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setColour() {
		this.colour = Color.rgb(randomColour(), randomColour(), randomColour());
	}
	
	public int randomColour() {
		// generates a random int to pick colour
	    int range = 255 - 1 + 1;
	    return (int) (Math.random() * range) + 1;
	}
	
	public abstract void draw();
	
	public abstract String toString();
	
}


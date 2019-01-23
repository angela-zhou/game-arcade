package spaceInvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Shooter extends ImageView {

	/**
	 * Constants and Variables
	 */
	// invader, ship, or bullet?
	final String TYPE;
	// speed at which the shooters will move
	final int    SPEED          = 2;
	final int    BULLET_SPEED   = 6;
	// determine whether or not to hide the bullet
	boolean      isDead = false;

	/**
	 * Constructor
	 */
	Shooter(int x, int y, String type, Image image) {
		super(image);
		this.TYPE = type;
		setX(x);
		setY(y);
	}
	
	/**
	 * Movement methods
	 */
	void moveLeft() {
		setX(getX() - SPEED);
	}

	void moveRight() {
		setX(getX() + SPEED);
	}
	
	void moveUp() {
		setY(getY() - BULLET_SPEED);
	}

	void moveDown() {
		setY(getY() + BULLET_SPEED);
	}

}
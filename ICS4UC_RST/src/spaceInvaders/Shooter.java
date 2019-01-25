package spaceInvaders;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * Shooter.java 
 * Inspired by https://youtu.be/FVo1fm52hz0
 */
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
	// original coordinate location
	int x;
	int y;

	/**
	 * Constructor
	 */
	Shooter(int x, int y, String type, Image image) {
		super(image);
		this.TYPE = type;
		this.x    = x;
		this.y    = y;
		setX(this.x);
		setY(this.y);
	}
	
	void reset() {
		isDead = false;
		setX(this.x);
		setY(this.y);
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
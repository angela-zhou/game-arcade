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

public abstract class Shooter extends ImageView {

	/**
	 * Constants and Variables
	 */
	// offset bullets
	public static final int OFFSET = 20;
	// invader or ship?
	final String TYPE;
	// speed at which the shooters will move
	final int    SPEED  = 2;
	// determine whether or not to hide the bullet
	//boolean      isDead = false;

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
	 * Movement Methods
	 */
	void moveLeft() {
		setX(getX() - SPEED);
	}

	void moveRight() {
		setX(getX() + SPEED);
	}
	
	/**
	 * Shooting Method
	 */
	public Bullet shoot(String shooter) {
		// bullet needs to come out of the middle of the shooter
		int x = (int) (getX() + OFFSET);
		int y = (int) (getY());
		Bullet bullet = new Bullet(x, y, shooter);
		return bullet;
	}

	/**
	 * Abstract Methods
	 */
	public abstract void reset();
}
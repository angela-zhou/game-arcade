package spaceInvaders;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * Alien.java
 */
import javafx.scene.image.Image;

public class Alien extends Shooter {
	
	// if I had more time, I would make the aliens move
	// and include a barrier for the ship to be safe
	boolean moveLeft;
	boolean moveRight;
	boolean moveUp;
	boolean moveDown;

	Alien(int x, int y, String type, Image image) {
		super(x, y, type, image);
	}
	
	void moveUp() {
		setY(getY() - SPEED);
	}

	void moveDown() {
		setY(getY() + SPEED);
	}

}

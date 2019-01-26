package spaceInvaders;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle {
	
	// speed constant
	final int BULLET_SPEED = 6;
	// invader bullet or ship bullet?
	final String TYPE;
	//movement var
	boolean moveUp;
	boolean moveDown;
	//determine whether or not to hide the bullet
	boolean isDead = false;
	
	Bullet(int x, int y, String type) {
		super(5, 20, Color.WHITE);
		this.TYPE = type;
		setTranslateX(x);
		setTranslateY(y);
		
	}
	
	void moveUp() {
		setTranslateY(getTranslateY() - BULLET_SPEED);
	}

	void moveDown() {
		setTranslateY(getTranslateY() + BULLET_SPEED);
	}
}

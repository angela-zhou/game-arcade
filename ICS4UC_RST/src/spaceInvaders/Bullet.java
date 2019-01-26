package spaceInvaders;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle {
	
	final int BULLET_SPEED = 10;
	// invader bullet or ship bullet
	final String TYPE;
	boolean moveUp;
	boolean moveDown;
	
	Bullet(int x, int y, String type) {
		super(5, 20, Color.WHITE);
		this.TYPE = type;
		setTranslateX(x);
		setTranslateY(y);
		
	}
	
	void moveUp() {
		setY(getTranslateY() - BULLET_SPEED);
	}

	void moveDown() {
		setY(getTranslateY() + BULLET_SPEED);
	}
}

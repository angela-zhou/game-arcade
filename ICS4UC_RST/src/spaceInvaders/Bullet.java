package spaceInvaders;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle {
	
	final int BULLET_SPEED   = 6;
	
	Bullet(int x, int y) {
		super(5, 20, Color.WHITE);
		setTranslateX(x);
		setTranslateY(y);
		
	}
	
	void moveUp() {
		setY(getY() - BULLET_SPEED);
	}

	void moveDown() {
		setY(getY() + BULLET_SPEED);
	}
}


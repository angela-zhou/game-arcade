package spaceInvaders;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shooter extends Rectangle {
	final String TYPE;

	// size constants of shooter
	final static int PLAYER_SIZE   = 40;
	final static int ENEMY_SIZE    = 30;
	final static int BULLET_HEIGHT = 20;
	final static int BULLET_WIDTH  = 5;

	// string constants of shooter
	final static String PLAYER_STRING = "Player";
	final static String ENEMY_STRING  = "Enemy";
	final static String BULLET_STRING = " Bullet";

	// colour constants of shooter
	final static Color PLAYER_COLOUR = Color.LIME;
	final static Color ENEMY_COLOUR  = Color.WHITE;
	final static Color BULLET_COLOUR = Color.WHITE;

	// speed constants
	final static int BULLET_SPEED = 6;
	final static int PLAYER_SPEED = 2;

	boolean isDead = false;

	Shooter(int x, int y, int w, int h, String type, Color colour) {
		super(w, h, colour);
		this.TYPE = type;
		setTranslateX(x);
		setTranslateY(y);
	}
	
	/**
	 * Movement methods
	 */
	void moveLeft() {
		setTranslateX(getTranslateX() - PLAYER_SPEED);
	}

	void moveRight() {
		setTranslateX(getTranslateX() + PLAYER_SPEED);
	}

	void moveUp() {
		setTranslateY(getTranslateY() - BULLET_SPEED);
	}

	void moveDown() {
		setTranslateY(getTranslateY() + BULLET_SPEED);
	}

}

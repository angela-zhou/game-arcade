package spaceInvaders;

import javafx.scene.image.Image;

public class Ship extends Shooter {
	
	boolean moveLeft;
	boolean moveRight;

	Ship(int x, int y, String type, Image image) {
		super(x, y, type, image);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}

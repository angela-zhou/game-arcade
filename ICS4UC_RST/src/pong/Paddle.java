package pong;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Paddle {

	double x, y;
	double speed;
	Rectangle paddle;
	boolean moveU, moveD;
	
	Paddle(Group gameGroup, double speed, double x, double y, double width, double height) {
		paddle = new Rectangle(x, y, width, height);
		gameGroup.getChildren().add(paddle);
		
		this.speed = speed;
		this.x = x;
		this.y = y;
		
		moveU = false;
		moveD = false;
	}
	
	public void move() {
		if(moveU && !moveD)
			y -= speed;
		if(moveD && !moveU)
			y += speed;
		paddle.setY(y);
	}
	
	public void setUp(boolean state) {
		moveU = state;
	}
	
	public void setDown(boolean state) {
		moveD = state;
	}
}

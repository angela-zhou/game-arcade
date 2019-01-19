package pong;

import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle{

	double SIDE_GAP;
	double SCREEN_WIDTH, SCREEN_HEIGHT;
	double speed;
	int playerNum;
	Rectangle paddle;
	boolean moveU, moveD;
	static final double HEIGHT_CONSTANT = 39, WIDTH_CONSTANT = 16;
	
	Paddle(double speed, int playerNum, double pWidth, double pHeight, double gap, double sWidth, double sHeight) {
		
		this.SCREEN_HEIGHT = sHeight;
		this.SCREEN_WIDTH = sWidth;
		this.SIDE_GAP = gap;
		this.playerNum = playerNum;
		this.setWidth(pWidth);
		this.setHeight(pHeight);
		
		this.setY(sHeight / 2 - pHeight);
		if(playerNum == 1)
			this.setX(SIDE_GAP);
		else
			this.setX(sWidth - (SIDE_GAP + pWidth));
		
		this.speed = speed;
		
		moveU = false;
		moveD = false;
	}
	
	public void move() {
		if(moveU && !moveD)
			this.setY(this.getY() - speed);
		if(moveD && !moveU)
			this.setY(this.getY() + speed);
		
		if(this.getY() < 0)
			this.setY(0);
		if(this.getY() + this.getHeight() > SCREEN_HEIGHT)
			this.setY(SCREEN_HEIGHT - this.getHeight());
	}
	
	public void setUp(boolean state) {
		moveU = state;
	}
	
	public void setDown(boolean state) {
		moveD = state;
	}
	
	public void updateScreen(double width, double height) {
		SCREEN_WIDTH = width - WIDTH_CONSTANT;
		SCREEN_HEIGHT = height - HEIGHT_CONSTANT;
		if(playerNum != 1)
			this.setX(SCREEN_WIDTH - SIDE_GAP - this.getWidth());
	}
}

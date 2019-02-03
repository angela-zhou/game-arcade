package pong;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* Paddle.java
* Object class for the Pong Game.
*/

import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle{

	//Paddle states and values
	int score;
	double SIDE_GAP;
	double SCREEN_WIDTH, SCREEN_HEIGHT;
	double moveSpeed, speed, decel, width, height;
	int playerNum;
	Rectangle paddle;
	boolean moveU, moveD;
	static final double HEIGHT_CONSTANT = 39, WIDTH_CONSTANT = 16;
	
	/**
	 * Creates a new paddle object.
	 * @param speed
	 *			The movement speed of the paddle.
	 * @param playerNum
	 *			Indicates player 1 or player 2.
	 * @param pWidth
	 *			The width of the paddle.
	 * @param pHeight
	 *			The height of the paddle.
	 * @param gap
	 *			The gap of this paddle from the side of the screen.
	 * @param sWidth
	 *			The width of the screen.
	 * @param sHeight
	 *			The height of the screen.
	 */
	Paddle(double speed, int playerNum, double pWidth, double pHeight, double gap, double sWidth, double sHeight) {
		
		//Globalizes paddle values and sets initial values
		score = 0;
		this.SCREEN_HEIGHT = sHeight;
		this.SCREEN_WIDTH = sWidth;
		this.SIDE_GAP = gap;
		this.playerNum = playerNum;
		this.width = pWidth;
		this.height = pHeight;
		this.moveSpeed = speed;
		this.decel = 0;
		moveU = false;
		moveD = false;
	}
	
	/**
	 * Resets the paddle to starting size and position.
	 */
	public void reset() {
		//Resets paddle size
		this.setWidth(width);
		this.setHeight(height);
		
		//Resets paddle x and y position
		this.setY(SCREEN_HEIGHT / 2 - height);
		if(playerNum == 1)
			this.setX(SIDE_GAP);
		else
			this.setX(SCREEN_WIDTH - (SIDE_GAP + width));
	}

	/**
	 * Moves the paddle.
	 */
	public void move() {
		//Sets speed based on movement states
		if(moveU && !moveD)
			speed = -moveSpeed;
		else if(moveD && !moveU)
			speed = moveSpeed;
		//Decelerates if there is no movement occurring
		else
			speed *= decel;
		
		//Updates y position using new speed
		this.setY(this.getY() + speed);
		
		//Keeps paddle within the screen bounds
		if(this.getY() < 0)
			this.setY(0);
		if(this.getY() + this.getHeight() > SCREEN_HEIGHT)
			this.setY(SCREEN_HEIGHT - this.getHeight());
	}

	/**
	 * Adds one point to this paddle's score.
	 */
	public void score() {
		score++;
	}
	
	/**
	 * Sets the size of this paddle.
	 * @param width
	 *			The new width for the paddle.
	 * @param height
	 *			The new height for the paddle.
	 */
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets the speed of this paddle.
	 * @param speed
	 *			The new speed for the paddle.
	 */
	public void setSpeed(double speed) {
		moveSpeed = speed;
	}
	
	/**
	 * Sets the deceleration of this paddle.
	 * @param decel
	 *			The new decel for this paddle.
	 */
	public void setDecel(double decel) {
		//Changes from settings value (%) to decel coefficient (decimal)
		try {
			this.decel = (100.0 - decel) / 100.0;
		} catch (Exception e) {
			this.decel = 0;
		}
	}
	
	/**
	 * Sets the state of the move up boolean.
	 * @param state
	 *			The new state of upward movement.
	 */
	public void setUp(boolean state) {
		moveU = state;
	}
	
	/**
	 * Sets the state of the move down boolean.
	 * @param state
	 *			The new state of downward movement.
	 */
	public void setDown(boolean state) {
		moveD = state;
	}
	
	/**
	 * Gets this paddle's score.
	 * @return
	 *			This paddle's score.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Resets this paddle's score to 0.
	 */
	public void resetScore()
	{
		score = 0;
	}
	
	/**
	 * Updates the screen boundary for this paddle.
	 * @param width
	 *			The new screen width.
	 * @param height
	 *			The new screen height.
	 */
	public void updateScreen(double width, double height) {
		//Updates passed screen size (screen is always off by this amount?)
		SCREEN_WIDTH = width - WIDTH_CONSTANT;
		SCREEN_HEIGHT = height - HEIGHT_CONSTANT;
		
		//Updates x pos for player 2 and y pos for both
		if(playerNum != 1)
			this.setX(SCREEN_WIDTH - SIDE_GAP - this.getWidth());
		this.setY(SCREEN_HEIGHT / 2 - this.height);
	}
}

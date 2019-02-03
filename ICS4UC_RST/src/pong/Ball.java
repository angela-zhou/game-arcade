package pong;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* Ball.java
* Object class for the Pong Game.
*/

import java.util.Random;
import javafx.scene.shape.Circle;

public class Ball extends Circle{
	
	//Ball states and values
	static final double HEIGHT_CONSTANT = 39, WIDTH_CONSTANT = 16, HIT_DELAY = 10;
	double SCREEN_WIDTH, SCREEN_HEIGHT;
	double minSpeed, maxSpeed, speed, xSpeed, ySpeed, ballSize;
	Paddle p1, p2;
	long lastHit;
	
	/**
	 * Creates a new ball object.
	 * @param minSpeed
	 *			Ball minimum speed.
	 * @param maxSpeed
	 *			Ball maximum speed.
	 * @param ballSize
	 *			Ball size.
	 * @param sWidth
	 *			Screen width.
	 * @param sHeight
	 *			Screen height.3
	 */
	Ball(double minSpeed, double maxSpeed, double ballSize, double sWidth, double sHeight) {
		
		//Globalizes values
		this.minSpeed = minSpeed;
		this.maxSpeed = maxSpeed;
		this.SCREEN_WIDTH = sWidth - WIDTH_CONSTANT;
		this.SCREEN_HEIGHT = sHeight - HEIGHT_CONSTANT;
		this.ballSize = ballSize;
		
		//Gets instance of paddles
		p1 = Main.getInstance().p1;
		p2 = Main.getInstance().p2;
		
		//Resets game
		reset();
		}
	
	/**
	 * Moves the ball.
	 */
	public void move() {
		//Updates ball position by adding x and y speed to x and y center pos
		this.setCenterX(this.getCenterX() + xSpeed);
		this.setCenterY(this.getCenterY() + ySpeed);
		
		//Top/bottom boundary detection
		if(this.getCenterY() - this.getRadius() <= 0 || this.getCenterY() + this.getRadius() >= SCREEN_HEIGHT)
			ySpeed *= -1;
		
		//Paddle detection
		if(this.getBoundsInLocal().intersects(p1.getBoundsInLocal()))
			paddleHit(p1);
		if(this.getBoundsInLocal().intersects(p2.getBoundsInLocal()))
			paddleHit(p2);
		
		//Score detection
		if(this.getBoundsInLocal().getMaxX() <= p1.getBoundsInLocal().getMinX())
			paddleScore(p2);
		if(this.getBoundsInLocal().getMinX() >= p2.getBoundsInLocal().getMaxX())
			paddleScore(p1);
		
	}

	/**
	 * Code run if a paddle is hit.
	 * @param paddle
	 *			The paddle that was hit.
	 */
	private void paddleHit(Paddle paddle) {
		
		//Double hit on paddle collision fix (adds 10ms hit delay)
		if(System.currentTimeMillis() - lastHit < HIT_DELAY)
			return;
		lastHit = System.currentTimeMillis();
		
		//Hitting side of paddle fix
		if(this.getCenterX() < p1.getX() + paddle.getWidth() && (this.getCenterY() < p1.getY() || this.getCenterY() > p1.getY() + p1.getHeight())) {
			ySpeed *= -1;
			return;
		}
		if(this.getCenterX() > p2.getX() && (this.getCenterY() < p2.getY() || this.getCenterY() > p2.getY() + p2.getHeight())) {
			ySpeed *= -1;
			return;
		}	
		
		//Proportional rebound mechanics 
		//d = distance from center  
		//r = ratio of d / paddle height    
		//a = angle of return (from perpendicular to paddle)
		double d = (paddle.getY() + paddle.getHeight() / 2) - this.getCenterY();
		double r = Math.abs(d / paddle.getHeight());
		double a = 0;
		
		//Top portion of paddle
		if(d >= 0) {
			if(xSpeed >= 0)
				a = Math.PI + ((Math.PI / 2) * r);
			else
				a = 2 * Math.PI - ((Math.PI / 2) * r);
		}
		//Bottom portion of paddle
		else {
			if(xSpeed >= 0)
				a = Math.PI - ((Math.PI / 2) * r);
			else
				a = (Math.PI / 2) * r;
		}
		//New x and y speed using angle and ball speed
		xSpeed = Math.cos(a) * speed;
		ySpeed = Math.sin(a) * speed;
	}
	
	/**
	 * Scores a paddle and checks if someone won.
	 * @param paddle
	 *			The paddle that scored.
	 */
	private void paddleScore(Paddle paddle) {
		//Adds a point to the paddle that scored
		paddle.score();
		
		//If paddle has max score, game over
		if(paddle.getScore() >= Main.getInstance().MAX_SCORE)
			Main.getInstance().gameOver(paddle);
		//Otherwise get ready for next point
		else
			reset();
	}
	
	/**
	 * Resets game for new point.
	 */
	public void reset() {
		//Resets paddle positions
		p1.reset();
		p2.reset();
		
		//Sets ball to middle of the screen
		this.setCenterX(SCREEN_WIDTH / 2);
		this.setCenterY(SCREEN_HEIGHT / 2);
		this.setRadius(ballSize);

		//Gets new random speed (sets initial trajectory to sideways)
		speed = rand(minSpeed, maxSpeed);
		xSpeed = speed / 2;
		//Flips ball x speed (left or right) half the time
		if(rand(0, 1) > 0.5)
			xSpeed *= -1;
		ySpeed = 0;
		
		//Updates scores text and pauses game
		Main.getInstance().updateGfx();
		Main.getInstance().pause(true);
	}
	
	/**
	 * Returns a random double between two values.
	 * @param min
	 *			The minimum value.
	 * @param max
	 *			The maximum value.
	 * @return
	 *			A double between the min and max value
	 */
	private double rand(double min, double max) {
		Random rand = new Random();
		return (rand.nextDouble() * (max - min)) + min;
	}

	/**
	 * Updates screen size boundaries.
	 * @param width
	 *			New screen width.
	 * @param height
	 *			New screen height.
	 */
	public void updateScreen(double width, double height) {
		//Fixes screen size values (always off by the same amount?)
		SCREEN_WIDTH = width - WIDTH_CONSTANT;
		SCREEN_HEIGHT = height - HEIGHT_CONSTANT;
		
		//Resets ball to center of screen
		this.setCenterX(SCREEN_WIDTH / 2);
		this.setCenterY(SCREEN_HEIGHT / 2);
	}

	/**
	 * Sets ball size.
	 * @param size
	 *			The new size of the ball;
	 */
	public void setSize(double size) {
		ballSize = size;
		reset();
	}
	
	/**
	 * Sets ball min speed.
	 * @param speed
	 *			The ball min speed.
	 */
	public void setMinSpeed(double speed) {
		minSpeed = speed;
		reset();
	}
	
	/**
	 * Sets ball max speed.
	 * @param speed
	 *			The ball max speed.
	 */
	public void setMaxSpeed(double speed) {
		maxSpeed = speed;
		reset();
	}
}

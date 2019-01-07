package asteroids;

/**
* @author Matthew Stoltz
* Date: June. 2018
* Course: ICS3U
* Ship.java
* Object class for ship in "Stoltz's Asteroids" game.
*/

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Ship {

	final double[] origXPts = {22, 0, 6, 0}, origYPts = {8, 16, 8, 0};
	double x, y, angle, accel, decel, turnSpeed, speed, maxSpeed, shotsPerSecond;
	boolean turnL, turnR, inAccel, shooting;
	double[] xPts, yPts;
	long lastShot;
	
	/**
	 * Method to initialize object
	 * @param pShip
	 *			Polygon object of ship that is being displayed on screen.
	 * @param maxSpeed
	 *			Ship's maximum speed.
	 * @param accel
	 *			Ship's acceleration speed.
	 * @param decel
	 *			Ship's deceleration speed.
	 * @param turnSpeed
	 *			Ship's turn speed.
	 * @param shotsPerSecond
	 *			Ship's shooting speed (measured in shots / s)
	 * @param sWidth
	 *			Screen's current width.
	 * @param sHeight
	 *			Screen's current height.
	 */
	Ship(Polygon pShip, double maxSpeed, double accel, double decel, double turnSpeed, double shotsPerSecond, int sWidth, int sHeight) {
		//Initializes object variables
		this.maxSpeed = maxSpeed;
		this.turnSpeed = turnSpeed;
		this.shotsPerSecond = shotsPerSecond;
		this.accel = accel;
		this.decel = decel;
		angle = 0;
		turnL = false;
		turnR = false;
		inAccel = false;
		xPts = new double[4];
		yPts = new double[4];
		lastShot = System.currentTimeMillis();
		speed = 0;
		
		//Sets ship starting position
		x = sWidth / 2;
		y = sHeight / 2;
		move(pShip, sWidth, sHeight);
		pShip.getPoints().setAll(getPos());

	}
	
	/**
	 * Moves ship based on current speed.
	 * @param pShip
	 *			Polygon of ship showed on screen.
	 * @param sWidth
	 *			Current screen width (for boundaries).
	 * @param sHeight
	 *			Current screen height (for boundaries).
	 */
	public void move(Polygon pShip, int sWidth, int sHeight) {
	
		//Turns ship a certain amount of radians based on ship's turnSpeed
		//Booleans for turning controlled by user input and setter methods below
		if(turnL)
			angle -= turnSpeed;
		if(turnR)
			angle += turnSpeed;
		
		//Keep angle between 0 and 2pi
		if(angle > 2 * Math.PI)
			angle -= 2 * Math.PI;
		if(angle < 0)
			angle += 2 * Math.PI;
		
		//Raises or lowers ships speed by using accel and decel speeds
		//Booleans controlled by user input and setter methods below
		if(inAccel)
			speed += accel;
		else
			speed *= decel;
		
		//Keeps ship speed under or at max speed
		if(speed > maxSpeed)
			speed = maxSpeed;
		
		//Uses trig equations to move ship's center using angle and speed
		//Equations derived from cosX = adj/hyp and sinX = opp/hyp
			x += speed * Math.cos(angle);
			y += speed * Math.sin(angle);
		
		//Creates new points for ship based off of x, y, and angle
		//All equation derived on paper, uses center point, point distance, and angle
		//For P1 (front point)
		xPts[0] = x + 11 * Math.cos(angle);
		yPts[0] = (y + 11 * Math.sin(angle));
		
		//For P2 (right point)
		xPts[1] = x + 13.6 * Math.cos(angle + 2.51);
		yPts[1] = (y + 13.6 * Math.sin(angle + 2.51));
		
		//For P3 (back point)
		xPts[2] = x + 5 * Math.cos(angle + Math.PI);
		yPts[2] = (y + 5 * Math.sin(angle + Math.PI));
		
		//For P4 (left point)
		xPts[3] = x + 13.6 * Math.cos(angle - 2.51);
		yPts[3] = (y + 13.6 * Math.sin(angle - 2.51));
		
		//Keeps ship on screen by moving center 10 pixels withing boundary
		if(x > sWidth - 10)
			x = sWidth - 10;
		if(x < 10)
			x = 10;
		if(y > sHeight - 10)
			y = sHeight - 10;
		if(y < 10)
			y = 10;
		
		//Updates points on polygon
		pShip.getPoints().setAll(getPos());
	}
	
	/**
	 * Creates a new shot using ships direction and speed
	 * @param shotGroup
	 *			The group of circle objects displayed as shots on screen.
	 * @param shots
	 *			The array of current on screen shot objects.
	 * @param cShots
	 *			The circle array of current on screen shot objects.
	 * @param numShots
	 *			The current number of shots.
	 * @param shotSize
	 *			The desired size of shot.
	 * @param shotLife
	 *			How many seconds the shot will stay on screen.
	 */
	public void shoot(Group shotGroup, Shot[] shots, Circle[] cShots, int numShots, double shotSize, double shotLife) {
		
		//Creates a new shot and adds it in the shot array at the first available index
		shots[numShots] = new Shot(shotLife, speed, angle, xPts[0], yPts[0]);
		
		//Creates a new circle based on the shot just created and adds it to the circle array
		cShots[numShots] = new Circle(shots[numShots].getX(), shots[numShots].getY(), shotSize);
		
		//Adds circle to the group to make it visible on screen
		shotGroup.getChildren().add(cShots[numShots]);
		
		//Updates time of last shot (for shot delay)
		lastShot = System.currentTimeMillis();
		
	}
	
	/**
	 * Puts ship back in the middle of screen and resets variables
	 * @param pShip
	 *			Polygon of ship showed on screen.
	 * @param sWidth
	 *			Current screen width.
	 * @param sHeight
	 *			Current screen height.
	 */
	public void reset(Polygon pShip, int sWidth, int sHeight) {
		//Sets x and y to middle of current screen size
		x = sWidth / 2;
		y = sHeight / 2;
		
		//Resets speed and all ship state booleans
		speed = 0;
		turnL = false;
		turnR = false;
		inAccel = false;
		shooting = false;
		
		//Updates polygon of ship
		pShip.getPoints().setAll(getPos());
	}
	
	/**
	 * Gets a observable list of ship's polygon points
	 * @return
	 *			An observable double list of ship points.
	 */
	public Double[] getPos() {
		//Observable list to be returned
		Double[] pos = new Double[xPts.length * 2];
		
		//For each point adds the x and y value to the observable list
		for(int i = 0; i < xPts.length; i++) {
			pos[i * 2] = xPts[i];
			pos[(i * 2) + 1] = yPts[i];
		}
		
		//Returns the list
		return pos;
	}
	
	/**
	 * Sets state of ship acceleration.
	 * @param acceling
	 *			True or False for if ship is accelerating or not.
	 */
	public void setAccel(boolean acceling) {
		//Sets object variable to value of parameter
		this.inAccel = acceling;
	}
	
	/**
	 * Sets state of ship turning left.
	 * @param turningL
	 *			True or False for if ship is turning left or not.
	 */
	public void setTurnL(boolean turningL) {
		//Sets object variable to value of parameter
		this.turnL = turningL;
	}
	
	/**
	 * Sets stae of ship turning right.
	 * @param turningR
	 *			True or False for if ship is turning right or not.
	 */
	public void setTurnR(boolean turningR) {
		//Sets object variable to value of parameter
		this.turnR = turningR;
	}
	
	/**
	 * Returns center x value.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns center y value.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Uses current time, last shot time, and shot delay to determine if ship was delayed long enough to shoot again.
	 * @return
	 *			Boolean True or False stating whether or not the ship can shoot.
	 */
	public boolean canShoot() {
		//Tests if the current time is past the last shot time + the shot delay (1000 milliseconds / shotsPerSecond)
		if(System.currentTimeMillis() >= lastShot + (1000 / shotsPerSecond))
			//True if enough time has passed
			return true;
		else
			//False if not enough time has passed
			return false;
	}
 	
}

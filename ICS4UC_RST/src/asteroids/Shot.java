package asteroids;

/**
* @author Matthew Stoltz
* Date: June. 2018
* Course: ICS3U
* Game.java
* Object class for shots in "Stoltz's Asteroids" game.
*/

import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class Shot {

	double x, y, xVel, yVel, speed = 5, life;
	long createTime = System.currentTimeMillis();
	
	/**
	 * Method to initialize object.
	 * @param shotLife
	 *			How long the shot will stay on screen.
	 * @param shipSpeed
	 *			The speed of the ship shooting the shot.
	 * @param angle
	 *			The angle of the ship shooting the shot.
	 * @param x
	 *			Starting center x of shot (ship's front point x value)
	 * @param y
	 *				 Starting center y of shot (ship's front point y value)
	 */
	Shot(double shotLife, double shipSpeed, double angle, double x, double y) {
		
		//Sets life of shot in milliseconds
		life = shotLife * 1000;
		
		//Sets shot starting position
		this.x = x;
		this.y = y;
		
		//Sets trajectory for new shot (compounds shot speed and ship speed so shot is moving "speed" relative to ship)
		//Same trig equation as used for ship movement
		xVel = (speed + shipSpeed) * Math.cos(angle);
		yVel = (speed + shipSpeed) * Math.sin(angle);
		
	}
	
	/**
	 * Moves shot and updates circle object.
	 * @param cShots
	 *			Array holding list of shot circle objects.
	 * @param index
	 *			Index of shot being moved.
	 * @param sWidth
	 *			Current screen width (for boundaries).
	 * @param sHeight
	 *			Current screen height (for boundaries).
	 */
	public void move(Circle[] cShots, int index, int sWidth, int sHeight) {
		
		//Moves shots x and y value using predefined velocities
		x += xVel;
		y += yVel;
		
		//Moves shot back onto screen if it has exceeded boundaries
		if(x < 0)
			x = sWidth;
		if(x > sWidth)
			x = 0;
		if(y < 0)
			y = sHeight;
		if(y > sHeight)
			y = 0;
		
		//Updates circle object to new x and y of shot
		cShots[index].setCenterX(x);
		cShots[index].setCenterY(y);
		
	}
	
	/**
	 * Checks if shot has been on screen longer than predefined shot life.
	 * @return
	 *			True if shot has been around too long, false if it hasn't.
	 */
	public boolean pastTime() {
		//Tests if current time is past creation time + life time
		if(System.currentTimeMillis() > createTime + life)
			//True if it is
			return true;
		//False if it isn't
		return false;
	}
	
	/**
	 * Removes shot and corresponding circle from screen and arrays.
	 * @param shotGroup
	 *			The group holding all shots shown on screen.
	 * @param shots
	 *			The array holding all shot objects.
	 * @param cShots
	 *			The array holding all circle shot objects.
	 * @param numShots
	 *			The current number of shots shown on screen.
	 * @param index
	 *			The index of the shot being removed.
	 */
	public void remove(Group shotGroup, Shot[] shots, Circle[] cShots, int numShots, int index) {
		//Removes shot and given index from the screen
		shotGroup.getChildren().remove(index);
		
		//Removes shot at given index from shot and circle shot arrays
		shots[index] = null;
		cShots[index] = null;
		
		//Moves all shots to the right of removed shot left in array
		for(int i = index; i < numShots; i++) {
			shots[i] = shots[i + 1];
			cShots[i] = cShots[i + 1];
		}
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
	 * Returns life of shot.
	 */
	public double getLife() {
		return life;
	}	
}

package asteroids;

/**
* @author Matthew Stoltz
* Date: June. 2018
* Course: ICS3U
* Game.java
* Object class for asteroids in "Stoltz's Asteroids" game.
*/

import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class Asteroid {

	double x, y;
	double[] startValues = new double[2];
	double angle, xVel, yVel;
	int size;
	double[] speedList = {0, 3, 2, 1};
	double[] sizeList = {0, 10, 25, 50};
	int safeZone = 130;
	
	/**
	 * Method to initialize object.
	 * @param astGroup
	 *			Group holding asteroids shown on screen.
	 * @param cAsts
	 *			Group holding asteroid circle object.
	 * @param index
	 *			Index where new asteroid will be added.
	 * @param x
	 *			Starting x value of new asteroid (-1 for random).
	 * @param y
	 *			Starting y value of new asteroid (-1 for random).
	 * @param size
	 *			Size (1 - 3) of new asteroid.
	 * @param angle
	 *			Starting movement angle of new asteroid (-1 for random).
	 * @param sWidth
	 *			Current screen width (for boundaries).
	 * @param sHeight
	 *			Current screen height (for boundaries).
	 */
	Asteroid(Group astGroup, Circle[] cAsts, int index, double x, double y, int size, double angle, int sWidth, int sHeight) {
		
		//Sets object variables for new created asteroid
		//Values with -1 for value are assigned a random number or call a method to set a start value
		this.size = size;
		
		if(x == -1 && y == -1) {
			getStart(sWidth, sHeight);
			this.x = startValues[0];
			this.y = startValues[1];
		}
		else {
			this.x = x;
			this.y = y;
		}
			
		if(angle == -1)
			this.angle = Math.random() * (2 * Math.PI);
		else
			this.angle = angle;
		
		//Sets velocities of asteroid using basic trig (same as ship movement)
		//Gets speed of asteroid by using hard coded list and asteroid size
		xVel = speedList[size] * Math.cos(this.angle);
		yVel = speedList[size] * Math.sin(this.angle);
		
		//Creates new circle for asteroid and adds it to circle array
		cAsts[index] = new Circle(this.x, this.y, sizeList[size]);
		
		//Adds circle to astGroup so it can be seen on screen
		astGroup.getChildren().add(cAsts[index]);
		
	}
	
	/**
	 * Moves asteroid at given index.
	 * @param cAsts
	 *			Circle array holding all asteroid circle objects.
	 * @param index
	 *			Array index of asteroid being moved.
	 * @param sWidth
	 *			Current screen width (for boundaries).
	 * @param sHeight
	 *			Current screen height (for boundaries).
	 */
	public void move(Circle[] cAsts, int index, int sWidth, int sHeight) {
		
		//Moves asteroid using predefined velocities
		x += xVel;
		y += yVel;
		
		//Wraps asteroid by moving it to opposite side of screen when it disappears off a side
		if(x < (0 - sizeList[size]))
			x = sWidth + sizeList[size];
			
		if(x > (sWidth + sizeList[size]))
			x = 0 - sizeList[size];
		
		if(y < (0 - sizeList[size]))
			y = sHeight + sizeList[size];
		
		if(y > (sHeight + sizeList[size]))
			y = 0 - sizeList[size];
		
		//Updates circle objects corresponding to asteroid
		cAsts[index].setCenterX(x);
		cAsts[index].setCenterY(y);
	}
	
	/**
	 * Does necessary processes when asteroid is hit by shot.
	 * @param astGroup
	 *			Group holding asteroids shown on screen.
	 * @param asts
	 *			Array holding asteroid objects.
	 * @param cAsts
	 *			Array holding asteroid circle objects.
	 * @param numAsts
	 *			The current number of asteroids on screen.
	 * @param index
	 *			The index of the asteroid that was hit.
	 * @param sWidth
	 *			The current screen width.
	 * @param sHeight
	 *			The current screen height.
	 * @return
	 *			The new number of asteroids on screen.
	 */
	public int hit(Group astGroup, Asteroid[] asts, Circle[] cAsts, int numAsts, int index, int sWidth, int sHeight) {
		
		//Removes hit asteroid from being seen on screen
		astGroup.getChildren().remove(index);
		
		//Runs this if asteroid is of size 1
		if(size == 1) {
			//Removes asteroid and corresponding circle from arrays
			asts[index] = null;
			cAsts[index] = null;
			
			//Shifts all asteroid to right of removed asteroid left one in array
			for(int i = index; i < numAsts; i++) {
				asts[i] = asts[i + 1];
				cAsts[i] = cAsts[i + 1];
			}
			
			//Returns new number of asteroids
			return numAsts - 1;
		}
		
		//Runs this if asteroid is bigger than size 1
		else {
			//Creates 2 new asteroids that broke off (each new asteroid is 1 radian difference in angle to right or left)
			asts[numAsts] = new Asteroid(astGroup, cAsts, numAsts, x, y, size - 1, angle - 1, sWidth, sHeight);
			asts[numAsts + 1] = new Asteroid(astGroup, cAsts, numAsts + 1, x, y, size - 1, angle + 1, sWidth, sHeight);
			
			//Removes original asteroid and corresponding circle from arrays
			asts[index] = null;
			cAsts[index] = null;
			
			//Shifts all asteroid to right of removed asteroid left one in array
			for(int i = index; i < numAsts + 2; i++) {
				asts[i] = asts[i + 1];
				cAsts[i] = cAsts[i + 1];
			}
			
			//Returns new number of asteroids
			return numAsts + 1;
		}
	}
	
	/**
	 * Moves any asteroids in ship's starting position to a new random point.
	 * @param asts
	 *			Array holding asteroid objects.
	 * @param cAsts
	 *			Array holding asteroid circle objects.
	 * @param numAsts
	 *			The current number of asteroids on screen.
	 * @param sWidth
	 *			The current screen width (for boundaries).
	 * @param sHeight
	 *			The current screen height (for boundaries).
	 */
	public void reset(Asteroid[] asts, Circle[] cAsts, int numAsts, int sWidth, int sHeight) {
		
		//Checks for every asteroid
		for(int i = 0; i < numAsts; i++) {
			//Checks if asteroid center value is inside radius created by safeZone and screen center
			if(Math.pow(asts[i].getX() - sWidth / 2  , 2) + Math.pow(asts[i].getY() - sHeight / 2, 2) <= Math.pow(safeZone, 2)) {
				//If asteroid is within this value, resets asteroids x and y value to new start values
				getStart(sWidth, sHeight);
				asts[i].setX(startValues[0]);
				asts[i].setY(startValues[1]);
			}
		}
	}
	
	/**
	 * Creates new start x and y values by changing object array "startValues".
	 * @param sWidth
	 *			Screen current width (for boundaries).
	 * @param sHeight
	 *			Screen current height (for boundaries).
	 */
	private void getStart(int sWidth, int sHeight) {
		
		//Assigns random values to start width and height using screen width and height as maximums
		startValues[0] = Math.random() * sWidth;
		startValues[1] = Math.random() * sHeight;
		
		//Reassigns start values (by recalling this method) if start position is inside safeZone circle
		if(Math.pow(startValues[0] - sWidth / 2  , 2) + Math.pow(startValues[1] - sHeight / 2, 2) <= Math.pow(safeZone, 2))
			getStart(sWidth, sHeight);
	}
	
	/**
	 * Sets asteroid's x value.
	 */
	public void setX(double val) {
		this.x = val;
	}
	
	/**
	 * Sets asteroid's y value.
	 */
	public void setY(double val) {
		this.y = val;
	}
	
	/**
	 * Returns asteroid's x value.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns asteroid's y value.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns asteroid's size value.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Returns asteroid's radius value.
	 */
	public double getRad() {
		return sizeList[size];
	}
}

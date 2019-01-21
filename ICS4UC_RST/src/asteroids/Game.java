package asteroids;

/**
* @author Matthew Stoltz
* Date: June. 2018
* Course: ICS3U
* Game.java
* Class that runs and controls the "Stoltz's Asteroid" game.
*/

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application{

	//Scene / Stage Variables
	Stage myStage;
	Scene game, gameOver;
	static int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
	Group gameGroup, gOverGroup;
	boolean levelStart = false;
	Label lScore, lLives, lLevel, lPaused, lGOver, lFinalScore, lContinue, lLevelComplete, lNextLevel, lLevelStart;
	
	//Game Variables
	int level = 100, score = 0, lives = 1;
	GameTimer timer;
	boolean timerRunning = false;
	
	//Animation Variables
	boolean hitAnim = false, levelAnim = false;
	int hitAnimCount = 0, levelAnimCount = 0;
	
	//Ship variables
	Ship ship;
	Polygon pShip;
	static final double SHIP_MAX_SPEED = 4, SHIP_ACCEL = 0.2, SHIP_DECEL = 0.95, SHIP_TURN_SPEED = 0.1, SHOTS_PER_SECOND = 100, SHIP_RAD = 4;
	
	//Shot variables
	Group shotGroup;
	boolean shooting = false;
	int numShots = 0;
	Shot[] shots = new Shot[31];
	Circle[] cShots = new Circle[31]; 
	static final double SHOT_SIZE = 3, SHOT_LIFE = 3;
	
	//Asteroid variables
	Group astGroup;
	int numAsts = 0;
	Asteroid[] asts = new Asteroid[1];
	Circle[] cAsts = new Circle[1];
	
	/**
	 * Code run when program launches to initialize all needed variables and objects.
	 */
	@Override
	public void start(Stage myStage) throws Exception {
		
		//Globalizes stage variable
		this.myStage = myStage;
		
		//Initializes timer for game
		timer = new GameTimer();
		
		//Creates new polygon and ship (ship is object, polygon is shown on screen)
		pShip = new Polygon();
		ship = new Ship(pShip, SHIP_MAX_SPEED, SHIP_ACCEL, SHIP_DECEL, SHIP_TURN_SPEED, SHOTS_PER_SECOND, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Creates labels for all scenes
		labelInit();
		
		//Creates groups for each needed set of data (name is self explanatory)
		astGroup = new Group();
		shotGroup = new Group();
		gameGroup = new Group(shotGroup, astGroup, lScore, lLives, lLevel, pShip);
		gOverGroup = new Group(lGOver, lFinalScore, lContinue);
		
		//Creates scenes and adds event handlers
		game = new Scene(gameGroup, SCREEN_WIDTH, SCREEN_HEIGHT);
		game.addEventHandler(KeyEvent.ANY, new GameKeyEvent());
		
		gameOver = new Scene(gOverGroup, SCREEN_WIDTH, SCREEN_HEIGHT);
		gameOver.addEventHandler(KeyEvent.KEY_PRESSED, e -> ResetGameEvent());
		
		//Initializes first level
		levelInit();
		
		//Stage setup
		myStage.setTitle("Stoltz's Asteroids");
		myStage.setScene(game);
		myStage.show();
	}
	
	/**
	 * Anonymous inner class that controls the application through the use of an animation timer.
	 * @author Stoltz
	 */
	class GameTimer extends AnimationTimer {
		
		/**
		 * Code that is run every frame to control game movement, collision detection, updates, etc
		 */
		@Override
		public void handle(long arg0) {
			
			//Updates scene dimensions
			SCREEN_WIDTH = (int)game.getWidth();
			SCREEN_HEIGHT = (int)game.getHeight();
			
			//Skips regular function if currently in an animation, goes to animation method
			if(hitAnim)
				shipHit();
			if(levelAnim)
				levelComplete();
			
			//Updates labels
			labelUpdate();
			
			//Checks for level being completed (if all asteroids are removed from array)
			if(asts[0] == null)
				levelComplete();
			
			//Moves Ship
			ship.move(pShip, SCREEN_WIDTH, SCREEN_HEIGHT);
			
			//Checks for player shooting
			if(shooting && ship.canShoot()) {
				//Adds new shot and increases shot count
				ship.shoot(shotGroup, shots, cShots, numShots, SHOT_SIZE, SHOT_LIFE);
				numShots++;
			}
			
			//Moves Shots
			for(int i = 0; i < numShots; i++)
				shots[i].move(cShots, i, SCREEN_WIDTH, SCREEN_HEIGHT);
			
			//Checks if shot has been on screen for too long
			if(numShots > 0 && shots[0].pastTime()) {
				//Removes shot and decreases shot count
				shots[0].remove(shotGroup, shots, cShots, numShots, 0);
				numShots--;
			}
			
			//Moves asteroids
			for(int i = 0; i < numAsts; i++)
				asts[i].move(cAsts, i, SCREEN_WIDTH, SCREEN_HEIGHT);
			
			//Checks for asteroid-shot collision by testing every shot against every asteroid and seeing if their bounds intersect
			for(int i = 0; i < numShots; i++) {
				for(int j = 0; j < numAsts; j++) {
					if(cShots[i].getBoundsInLocal().intersects(cAsts[j].getBoundsInLocal())) {
						//Runs this if shot[i] and asteroid[j] have been found to intersect
						//Adds score
						score += 10;
						
						//Removes shot that hit asteroid and decreases shot count
						shots[i].remove(shotGroup, shots, cShots, numShots, i);
						numShots--;
						
						//Sends hit signal to asteroid[j]
						numAsts = asts[j].hit(astGroup, asts, cAsts, numAsts, j, SCREEN_WIDTH, SCREEN_HEIGHT);
						
						//Asteroid no longer exists, quit detection for this asteroid (bug fix)
						break;
					}
				}
			}	
			
			//Checks for player-asteroid collision, a circle is used to approximate the size of the ship and a rearranged circle formula is used to
			//check if it overlaps any asteroid circle. Formula for test is: (r1 + r2) ^ 2 > (x2 - x1) ^ 2 - (y2 - y1) ^ 2
			for(int i = 0; i < numAsts; i++) {
				if(Math.pow(asts[i].getRad() + SHIP_RAD, 2) > Math.pow(ship.getX() - asts[i].getX(), 2) + Math.pow(ship.getY() - asts[i].getY(), 2))
					//If ship is hit, runs needed animation
					shipHit();
			}
		}
	}
	
	/**
	 * Provides animation and performs necessary actions when ship collides with asteroid
	 */
	public void shipHit() {
		
		//Sets flag for program to know it's in animation, stops user input recognition and skips other code in handle() method
		hitAnim = true;
		
		//Put all animation code inside while loop so it can be exited at any time
		while(hitAnim) {
			
			//First six grames of animation happen here
			if(hitAnimCount < 6) {
				//Sets ship colour to red
				pShip.setFill(Color.RED);
				
				//Pauses for .4 seconds
				animationDelay(400);
				
				//Adds one to animation counter
				hitAnimCount++;
				
				//Breaks loop and method to return to handle and update screen
				break;
			}
			//Next frame happens here
			else if(hitAnimCount < 7) {
				//Resets ship position to center of screen
				ship.reset(pShip, SCREEN_WIDTH, SCREEN_HEIGHT);
				
				//Adds one to animation counter
				hitAnimCount++;
				
				//Breaks loop and method to return to handle and update screen
				break;
			}
			//Last frame of animation happens below
			
			//Removes one life
			lives--;
			//Tests if user is out of lives
			if(lives < 1) {
				//Updates stage size and changes scene to gameOver
				myStage.setWidth(SCREEN_WIDTH);
				myStage.setHeight(SCREEN_HEIGHT);
				myStage.setScene(gameOver);
				myStage.setWidth(game.getWidth());
				myStage.setHeight(game.getHeight());
			}
			
			//Changes ship back to black
			pShip.setFill(Color.BLACK);
			
			//Resets animation counter and sets animation flag (boolean) to false
			hitAnimCount = 0;
			hitAnim = false;
			
			//Stops timer so user can resume play themselves
			timer.stop();
			timerRunning = false;
				
			//Resets position of any asteroids that are in the user's "safe" spawn zone
			if(numAsts > 0)
				asts[0].reset(asts, cAsts, numAsts, SCREEN_WIDTH, SCREEN_HEIGHT);
			
			break;
		}
	}
	
	/**
	 * Provides animation for level complete text showing up on screen.
	 */
	public void levelComplete() {
		
		//Sets flag for program to know it's in animation, stops user input recognition and skips other code in handle() method
		levelAnim = true;
		
		//Put all animation code inside while loop so it can be exited at any time
		while(levelAnim) {
			
			//First ten frames of animation happens here
			if(levelAnimCount < 10) {
				//Adds level complete text on first frame
				if(levelAnimCount == 0)
					gameGroup.getChildren().add(lLevelComplete);
				
				//Pauses for .2 seconds
				animationDelay(200);
				
				//Adds one to animation counter
				levelAnimCount++;
				
				//Breaks loop and method to return to handle and update screen
				break;
			}
			
			//Next ten frames of animation happen here
			if(levelAnimCount < 20) {
				//Switches to next level text on first frame of this animation
				if(levelAnimCount == 10) {
					gameGroup.getChildren().remove(lLevelComplete);
					gameGroup.getChildren().add(lNextLevel);
				}
				
				//Pauses for .2 seconds
				animationDelay(200);
				
				//Adds one to animation counter
				levelAnimCount++;
				
				//Breaks loop and method to return to handle and update screen
				break;
			}
			
			//Last frame is run here
			//Removes text from screen
			gameGroup.getChildren().remove(lNextLevel);
			//Initializes next level
			levelInit();
			//Resets animation counter and sets animation flag (boolean) to false
			levelAnimCount = 0;
			levelAnim = false;
			break;
		}
	}
	
	/**
	 * Pauses thread for an amount of time to give delay for animations.
	 * @param delay
	 *			The amount of delay(milis) the program will pause.
	 */
	private void animationDelay(int delay) {
		//Sleeps thread with a mandatory catch for the exception
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializes a new level by resetting positions and creating new objects.
	 */
	public void levelInit() {
		
		//Stops game
		timer.stop();
		timerRunning = false;
		
		//Moves to next level and updates level label
		level++;
		lLevel.setText("Level: " + level);
		
		//Resets ship to center of screen
		ship.reset(pShip, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Removes all shots from screen
		for(int i = 0; i < numShots; i++) {
			shots[0].remove(shotGroup, shots, cShots, numShots, 0);
		}
		numShots = 0;
		
		//Sets new sizes for asteroids arrays and variables
		asts = new Asteroid[level * 4 + 2];
		cAsts = new Circle[level * 4 + 2];
		numAsts = level;
		
		//Adds new asteroids for current level
		for(int i = 0; i < level; i++)
			asts[i] = new Asteroid(astGroup, cAsts, i, -1, -1, 3, -1, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Adds text for start instructions on screen and changes boolean so program knows it's there
		levelStart = true;
		gameGroup.getChildren().add(lLevelStart);
	}
		
	
	/**
	 * Initialize labels by giving them their starting text, size, position, and colour.
	 */
	public void labelInit() {
		
		//Creates and positions all text ever seen on screen
		
		//Label for user score
		lScore = new Label("" + score);
		lScore.setFont(new Font(30));
		lScore.setTextFill(Color.RED);
		lScore.setTranslateX(SCREEN_WIDTH / 2);
		lScore.setTranslateY(0);
		
		//Label for lives
		lLives = new Label("Lives: " + lives);
		lLives.setFont(new Font(20));
		lLives.setTextFill(Color.RED);
		lLives.setTranslateX(SCREEN_WIDTH - 100);
		lLives.setTranslateY(0);
		
		//Label for level counter
		lLevel = new Label("Level: " + level);
		lLevel.setFont(new Font(20));
		lLevel.setTextFill(Color.RED);
		lLevel.setTranslateX(20);
		lLevel.setTranslateY(0);
		
		//Label for showing game is paused
		lPaused = new Label("PAUSED");
		lPaused.setFont(new Font(50));
		lPaused.setTextFill(Color.RED);
		lPaused.setTranslateX(SCREEN_WIDTH - 210);
		lPaused.setTranslateY(SCREEN_HEIGHT - 65);
		
		//Label for "GAME OVER"
		lGOver = new Label("GAME OVER");
		lGOver.setFont(new Font("Verdana", 100));
		lGOver.setTranslateX(SCREEN_WIDTH / 2 - 300);
		lGOver.setTranslateY(SCREEN_HEIGHT / 2 - 150);
		
		//Label for user final score
		lFinalScore = new Label("Final Score: " + score);
		lFinalScore.setFont(new Font("Verdana", 25));
		lFinalScore.setTranslateX(SCREEN_WIDTH / 2 - 75);
		lFinalScore.setTranslateY(SCREEN_HEIGHT / 2 - 30);
		
		//Label for Replay message
		lContinue = new Label("Press any key to play again!");
		lContinue.setFont(new Font("Verdana", 40));
		lContinue.setTranslateX(SCREEN_WIDTH / 2 - 280);
		lContinue.setTranslateY(SCREEN_HEIGHT / 2 + 120);
		
		//Label for level complete
		lLevelComplete = new Label("Level Complete!");
		lLevelComplete.setFont(new Font("Verdana", 80));
		lLevelComplete.setTextFill(Color.RED);
		lLevelComplete.setTranslateX(SCREEN_WIDTH / 2 - 300);
		lLevelComplete.setTranslateY(SCREEN_HEIGHT / 2 - 80);
		
		//Label for showing next level #
		lNextLevel = new Label("Level " + (level + 1) + " Begin!");
		lNextLevel.setFont(new Font("Verdana", 80));
		lNextLevel.setTextFill(Color.RED);
		lNextLevel.setTranslateX(SCREEN_WIDTH / 2 - 270);
		lNextLevel.setTranslateY(SCREEN_HEIGHT / 2 - 80);
		
		//Label for game start / resume instructions
		lLevelStart = new Label("Press ENTER to start and stop gameplay");
		lLevelStart.setFont(new Font("Verdana", 30));
		lLevelStart.setTextFill(Color.RED);
		lLevelStart.setTranslateX(SCREEN_WIDTH / 2 - 300);
		lLevelStart.setTranslateY(SCREEN_HEIGHT / 2 + 175);
		
	}
	
	/**
	 * Updates labels by setting text with possibly updated variables and reposition text based on possibly updated screen dimension
	 */
	public void labelUpdate() {
		
		//Updates label values (level updated when level changes -> levelInit() )
		lScore.setText("" + score);
		lLives.setText("Lives: " + lives);
		lFinalScore.setText("Final Score: " + score);
		lNextLevel.setText("Level " + (level + 1) + " Begin!");
		
		//Updates position of labels with possible new scene resize
		lScore.setTranslateX(SCREEN_WIDTH / 2);
		lScore.setTranslateY(0);
		
		lLives.setTranslateX(SCREEN_WIDTH - 100);
		lLives.setTranslateY(0);
		
		lLevel.setTranslateX(20);
		lLevel.setTranslateY(0);
		
		lPaused.setTranslateX(SCREEN_WIDTH - 210);
		lPaused.setTranslateY(SCREEN_HEIGHT - 65);
		
		lGOver.setTranslateX(SCREEN_WIDTH / 2 - 300);
		lGOver.setTranslateY(SCREEN_HEIGHT / 2 - 150);
		
		lFinalScore.setTranslateX(SCREEN_WIDTH / 2 - 75);
		lFinalScore.setTranslateY(SCREEN_HEIGHT / 2 - 30);
		
		lContinue.setTranslateX(SCREEN_WIDTH / 2 - 280);
		lContinue.setTranslateY(SCREEN_HEIGHT / 2 + 120);
		
		lLevelComplete.setTranslateX(SCREEN_WIDTH / 2 - 300);
		lLevelComplete.setTranslateY(SCREEN_HEIGHT / 2 - 80);
		
		lNextLevel.setTranslateX(SCREEN_WIDTH / 2 - 270);
		lNextLevel.setTranslateY(SCREEN_HEIGHT / 2 - 80);
		
		lLevelStart.setTranslateX(SCREEN_WIDTH / 2 - 300);
		lLevelStart.setTranslateY(SCREEN_HEIGHT / 2 + 175);
	}
	
	/**
	 * @author Stoltz
	 * Anonymous inner class controls user inputs. Added to a listener on the "game" scene.
	 */
	class GameKeyEvent implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent ev) {
			//Gets the input type and stores them in temporary variables
			KeyCode code = ev.getCode();
			EventType<KeyEvent> type = ev.getEventType();
			
			//Runs this block if the user pressed a key and game is not currently in any animation
			if(type == KeyEvent.KEY_PRESSED && !hitAnim && !levelAnim) {
				
				//Pressing up accelerates the ship
				if(code == KeyCode.UP) {
					ship.setAccel(true);
				}
				
				//Pressing left turns the ship left
				if(code == KeyCode.LEFT) {
					ship.setTurnL(true);
					ship.setTurnR(false);
				}
				
				//Pressing right turns the ship right
				if(code == KeyCode.RIGHT) {
					ship.setTurnR(true);
					ship.setTurnL(false);
				}
				
				//Pressing space changes shooting boolean true, attempts to shoot each frame in handle
				if(code == KeyCode.SPACE) {
					shooting = true;
				}
				
				//Pauses and Unpauses game
				if(code == KeyCode.ENTER) {
					//If running, pauses and adds "paused" text in bottom right
					if(timerRunning) {
						timerRunning = false;
						gameGroup.getChildren().add(lPaused);
						timer.stop();
					}
					//If not running, unpauses and removes "paused" text + "level start" text
					else {
						if(levelStart)
							gameGroup.getChildren().remove(lLevelStart);
						timerRunning = true;
						gameGroup.getChildren().remove(lPaused);
						timer.start();
					}
				}
			}
			
			//Runs this block if key has been released
			if(type == KeyEvent.KEY_RELEASED) {
				
				//Releasing up stops acceleration
				if(code == KeyCode.UP)
					ship.setAccel(false);
				
				//Stops left turn movement
				if(code == KeyCode.LEFT)
					ship.setTurnL(false);
				
				//Stops right turn movement
				if(code == KeyCode.RIGHT)
					ship.setTurnR(false);
				
				//Stops shooting by changing shooting boolean to false
				if(code == KeyCode.SPACE)
					shooting = false;
				
			}
		}
	}
	
	/**
	 * Resets game by setting variables and objects to their start position.
	 */
	public void ResetGameEvent() {
		lives = 3;
		score = 0;
		level = 0;
		
		gameGroup.getChildren().remove(shotGroup);
		shotGroup = new Group();
		gameGroup.getChildren().add(0, shotGroup);
		numShots = 0;
		
		gameGroup.getChildren().remove(astGroup);
		astGroup = new Group();
		gameGroup.getChildren().add(1, astGroup);
		
		ship.reset(pShip, SCREEN_WIDTH, SCREEN_HEIGHT);
		levelInit();
		myStage.setScene(game);
	}
	
	/**
	 * @param args command-line args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

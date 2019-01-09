package pacMan;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * @author Angela Zhou
 * Date: June 2018 (Finished June 22, 2018)
 * Course: ICS3U
 * Teacher: Mrs. Spindler
 * MazeController.java
 */
public class MazeController {
	/**
	 * Initialization
	 */
	// controlling the animation
	GameTimer timer;

	// speed constants
	final static int PACMAN_SPEED = 2;
	final static int GHOST_SPEED  = 2;
	
	// the amount of pixels that the pacMan will move
	double pacmanXChange = 0;
	double pacmanYChange = 0;
			
	// the amount of pixels the ghost will move
	double ghostXChange = 0;
	double ghostYChange = 0;

	// direction constants
	public static final int RIGHT = 0;
	public static final int UP    = 1;
	public static final int LEFT  = 2;
	public static final int DOWN  = 3;

	public static final int CLOCKWISE        = 0;
	public static final int COUNTERCLOCKWISE = 1;
	
	// put all the direction variables in an array
	int [] ghostDirection = {DOWN, LEFT, RIGHT, UP};
	
	// font constant
	final int FONT_SIZE = 20;
	
	// user score
	int userScore = 0;
	
	// load sounds
	AudioClip beginning;
	AudioClip chomp;
	AudioClip death;
	AudioClip winNoise;
	
	@FXML
	Text txtUserScore;
	
	// welcome screen variables
	@FXML
	Text welcome;  // READY?
	
	@FXML
	Text start;    // Press the spacebar to start and P to pause
	
	
	// win/lose screen variables 
	@FXML
	Text gameOver; // GAME OVER!
	
	@FXML
	Text win;      // YOU WIN
	
	@FXML
	Text reset;    // Press R to reset 
	
	// pause screen variables
	@FXML
	Text pause;    // PAUSED
	
	@FXML
	Text continuePlaying;  // Press the spacebar to continue
	
	// put everything in the root
	@FXML
	Pane root;

	@FXML
	// initialize the rectangles
	Rectangle r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22,
		r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46;

	// put all the rectangles in an array
	Rectangle [] maze;

	@FXML
	// initialize variables for the pacMan
	ImageView pacMan;

	@FXML
	// initialize variables for the ghosts
	ImageView ghostBlue, ghostPink, ghostRed, ghostYellow;

	// put all the ghosts in an array
	ImageView [] ghosts;
	
	// initialize the food
	@FXML
	Circle       		f1, f2, f3, f4, f5, f6, f7, f8, f9, f10,
				f11, f12, f13, f14, f15, f16, f17, f18, f19, f20,
				f21, f22, f23, f24, f25, f26, f27, f28, f29, f30, 
				f31, f32, f33, f34, f35, f36, f37, f38, f39, f40, 
				f41, f42, f43, f44, f45, f46, f47, f48, f49, f50, 
				f51, f52, f53, f54, f55, f56, f57, f58, f59, f60,
				f61, f62, f63, f64, f65, f66, f67, f68, f69, f70, 
				f71, f72, f73, f74, f75, f76, f77, f78, f79, f80,
				f81, f82, f83, f84, f85, f86, f87, f88, f89, f90,
				f91, f92, f93, f94, f95, f96, f97, f98, f99, f100,
		f101, f102, f103, f104, f105, f106, f107, f108, f109, f110,
		f111, f112, f113, f114, f115, f116, f117, f118, f119, f120,
		f121, f122, f123, f124, f125, f126, f127, f128, f129, f130,
		f131, f132, f133, f134, f135, f136, f137, f138, f139, f140,
		f141, f142, f143, f144, f145, f146, f147, f148, f149, f150,
		f151, f152, f153, f154, f155, f156, f157, f158, f159, f160,
		f161, f162, f163, f164, f165, f166, f167, f168, f169, f170,
		f171, f172, f173, f174, f175, f176, f177, f178, f179, f180,
		f181, f182, f183, f184, f185, f186, f187, f188, f189, f190;
	
	static // put all the food into an array
	Circle [] food;

	@FXML
	void initialize() {
		
		// maze barriers
		Rectangle [] tmpMaze = {r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, 
				r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46};

		maze = tmpMaze;
		
		// ghosts
		ImageView [] tmpGhosts = {ghostBlue, ghostPink, ghostRed, ghostYellow};
		
		ghosts = tmpGhosts;
		
		// food
		Circle [] tmpFood = {f1, f2, f3, f4, f5, f6, f7, f8, f9, f10,
					f11, f12, f13, f14, f15, f16, f17, f18, f19, f20,
					f21, f22, f23, f24, f25, f26, f27, f28, f29, f30, 
					f31, f32, f33, f34, f35, f36, f37, f38, f39, f40, 
					f41, f42, f43, f44, f45, f46, f47, f48, f49, f50, 
					f51, f52, f53, f54, f55, f56, f57, f58, f59, f60,
					f61, f62, f63, f64, f65, f66, f67, f68, f69, f70, 
					f71, f72, f73, f74, f75, f76, f77, f78, f79, f80,
					f81, f82, f83, f84, f85, f86, f87, f88, f89, f90,
					f91, f92, f93, f94, f95, f96, f97, f98, f99, f100,
			f101, f102, f103, f104, f105, f106, f107, f108, f109, f110,
			f111, f112, f113, f114, f115, f116, f117, f118, f119, f120,
			f121, f122, f123, f124, f125, f126, f127, f128, f129, f130,
			f131, f132, f133, f134, f135, f136, f137, f138, f139, f140,
			f141, f142, f143, f144, f145, f146, f147, f148, f149, f150,
			f151, f152, f153, f154, f155, f156, f157, f158, f159, f160,
			f161, f162, f163, f164, f165, f166, f167, f168, f169, f170,
			f171, f172, f173, f174, f175, f176, f177, f178, f179, f180,
			f181, f182, f183, f184, f185, f186, f187, f188, f189, f190};
		
		food = tmpFood;
		
		// animation timer to update and render graphics
		timer = new GameTimer();

		// set images in their place
		pacMan.setImage(new Image(getClass().getResource("/PacManImagesMusic/Pacman.png").toString()));
		ghostBlue.setImage(new Image(getClass().getResource("/PacManImagesMusic/GhostBlue.png").toString()));
		ghostPink.setImage(new Image(getClass().getResource("/PacManImagesMusic/GhostPink.png").toString()));
		ghostRed.setImage(new Image(getClass().getResource("/PacManImagesMusic/GhostRed.png").toString()));
		ghostYellow.setImage(new Image(getClass().getResource("/PacManImagesMusic/GhostYellow.png").toString()));
		
		// set up sounds
		beginning = new AudioClip(getClass().getResource("/PacManImagesMusic/pacman_beginning.wav").toString());
		chomp     = new AudioClip(getClass().getResource("/PacManImagesMusic/pacman_chomp.wav").toString());
		death     = new AudioClip(getClass().getResource("/PacManImagesMusic/pacman_death.wav").toString());
		winNoise  = new AudioClip(getClass().getResource("/PacManImagesMusic/pacman_intermission.wav").toString());
		
		beginning.play();
	}

	public static int randomNumber(int a, int b) {
		// random number generator
		int highNum = Math.max(a, b);
		int lowNum = Math.min(a, b);
		int range = highNum - lowNum + 1;
		return (int) (Math.random() * range) + lowNum;
	}
	
	public void newGame() {
		// set back to original positions as set in scene builder
		// reset pacMan
		pacMan.setX(0);
		pacMan.setY(0);
		
		// reset ghosts
		ghostBlue.setX(0);
		ghostBlue.setY(0);
		
		ghostPink.setX(0);
		ghostPink.setY(0);
		
		ghostRed.setX(0);
		ghostRed.setY(0);
		
		ghostYellow.setX(0);
		ghostYellow.setY(0);
		
		// reset food
		for (int i = 0; i < food.length; i++) {
			// make all food visible
			food[i].setVisible(true);
		}
		
		// reset score
		userScore = 0;
		txtUserScore.setText("Score: " + userScore);
		
		// get rid of game over
		gameOver.setVisible(false);
		reset.setVisible(false);
		win.setVisible(false);
		
		// start game again
		timer.start();
	}

	public static void ghostMove(ImageView ghost, int direction) {
		if (direction == UP)    ghost.setY(ghost.getY() - GHOST_SPEED);
		if (direction == DOWN)  ghost.setY(ghost.getY() + GHOST_SPEED);
		if (direction == LEFT)  ghost.setX(ghost.getX() - GHOST_SPEED);
		if (direction == RIGHT) ghost.setX(ghost.getX() + GHOST_SPEED);
	}

	public static int rotateCounterClockwise(int currentDirection) {
		// turn counter clockwise by adding 1
		currentDirection += 1;

		// returns the direction, if direction is too big it will reset to zero
		return currentDirection % 4;
	}

	public static int rotateClockwise(int currentDirection) {
		// turn clockwise by subtracting 1
		currentDirection -= 1;

		// add four to deal with negative direction and modulus
		currentDirection += 4;

		return currentDirection % 4;
	}

	/**
	 * Event handling
	 */

	@FXML
	public void handleKeyPressed(KeyEvent event) {
		KeyCode code = event.getCode();
		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
			// left key press
			if (code == KeyCode.LEFT || code == KeyCode.KP_LEFT) {
				pacMan.setRotate(180);
				pacmanXChange = -PACMAN_SPEED;
				pacmanYChange = 0;
			// right key press
			} else if (code == KeyCode.RIGHT || code == KeyCode.KP_RIGHT) {
				pacMan.setRotate(0);
				pacmanXChange = PACMAN_SPEED;
				pacmanYChange = 0;
			// up key press
			} else if (code == KeyCode.UP || code == KeyCode.KP_UP) {
				pacMan.setRotate(270);
				pacmanXChange = 0;
				pacmanYChange = -PACMAN_SPEED;
			// down key press
			} else if (code == KeyCode.DOWN || code == KeyCode.KP_DOWN) {
				pacMan.setRotate(90);
				pacmanXChange = 0;
				pacmanYChange = PACMAN_SPEED;
			} else if (code == KeyCode.SPACE) {
				
				if (!win.isVisible() && !gameOver.isVisible()) {
				// stop playing beginning music
				beginning.stop();
				
				// start game
				timer.start();
				
				// stop displaying welcome
				welcome.setVisible(false);
				
				// stop displaying instructions
				start.setVisible(false);
				
				// stop displaying pause
				pause.setVisible(false);
				
				// stop displaying restart
				continuePlaying.setVisible(false);
				
				// stop displaying win
				win.setVisible(false);
				
				}
				
			} else if (code == KeyCode.P) {
				// pause the game
				timer.stop();
				
				if (!welcome.isVisible() && !win.isVisible() && !gameOver.isVisible()) {
					// display pause
					pause.setVisible(true);
				
					// display restart
					continuePlaying.setVisible(true);
				}
			} else if (code == KeyCode.R) {
				
				if (!welcome.isVisible()) {
					// start a new game
					newGame();
				}
				
			}
		}
	}

	@FXML
	public void handleKeyReleased(KeyEvent event) {
		// stop moving the pacMan when user releases the arrow key
		KeyCode code = event.getCode();
		if (event.getEventType() == KeyEvent.KEY_RELEASED) {
			if (code == KeyCode.LEFT || code == KeyCode.KP_LEFT || code == KeyCode.RIGHT || code == KeyCode.KP_RIGHT){
				pacmanXChange = 0;
			} else if (code == KeyCode.UP || code == KeyCode.KP_UP || code == KeyCode.DOWN || code == KeyCode.KP_DOWN) {
				pacmanYChange = 0;
			}
		}
	}

	/**
	 * Game Timer
	 */

	class GameTimer extends AnimationTimer {
		
		long lastUpdate = 0;

		private boolean objectAbove(Bounds figure, Bounds barrier, int speed) {

			// find distance between top of barrier and bottom of figure
			double distance = figure.getMaxY() - barrier.getMinY();

			// return true if distance is between 0 and speed
			return distance >= 0 && distance <= speed;
		}

		private boolean objectBelow(Bounds figure, Bounds barrier, int speed) {

			// find distance between bottom of barrier and top of figure
			double distance = barrier.getMaxY() - figure.getMinY();

			// return true if distance is between 0 and speed
			return distance >= 0 && distance <= speed;
		}

		private boolean objectLeft(Bounds figure, Bounds barrier, int speed) {

			// find distance between left of barrier and right of figure
			double distance = figure.getMaxX() - barrier.getMinX();

			// return true if distance is between 0 and speed
			return distance >= 0 && distance <= speed;
		}

		private boolean objectRight(Bounds figure, Bounds barrier, int speed) {

			// find distance between right of barrier and left of figure
			double distance = barrier.getMaxX() - figure.getMinX();

			// return true if distance is between 0 and speed
			return distance >= 0 && distance <= speed;
		}
		
		/**
		 * Main Game Loop
		 */
		@Override
		public void handle(long now) {
			
			if (now - lastUpdate >= 1_000_000_000) {
				// do something every one second
				lastUpdate = now;
			}

			// move ghosts in their initial direction
			ghostMove(ghostBlue,   ghostDirection[0]);
			ghostMove(ghostPink,   ghostDirection[1]);
			ghostMove(ghostRed,    ghostDirection[2]);
			ghostMove(ghostYellow, ghostDirection[3]);

			/**
			 * Ghost/Barrier Collision detection
			 */
			
			for (int i = 0; i < ghosts.length; i++) {
				// set bounds for ghosts
				Bounds ghost = ghosts[i].getBoundsInParent();
				
				for (int j = 0; j < maze.length; j++) {

					// set bounds for the barrier
					Bounds barrier = maze[j].getBoundsInParent();

					if (ghost.intersects(barrier)) {
						// must check for all four sides to reduce jumpy movement
						// if ghost hits a barrier above
						if (objectAbove(ghost, barrier, GHOST_SPEED) && ! (objectBelow(ghost, barrier, GHOST_SPEED) || objectLeft(ghost, barrier, GHOST_SPEED) || objectRight(ghost, barrier, GHOST_SPEED))) {

							// then we reset the ghost back up to his previous position by subtracting speed from Y
							ghosts[i].setY(ghosts[i].getY() - GHOST_SPEED);

							// and we rotate the ghost to face another direction
							ghostDirection[i] = (randomNumber(CLOCKWISE, COUNTERCLOCKWISE) == CLOCKWISE) ? rotateClockwise(DOWN) : rotateCounterClockwise(DOWN);

						} 
						// if ghost hits a barrier below
						if (objectBelow(ghost, barrier, GHOST_SPEED) && ! (objectAbove(ghost, barrier, GHOST_SPEED) || objectLeft(ghost, barrier, GHOST_SPEED) || objectRight(ghost, barrier, GHOST_SPEED))) {

							// then we reset the ghost back down to his previous position by adding speed to X
							ghosts[i].setY(ghosts[i].getY() + GHOST_SPEED);

							// and we rotate the ghost to face another direction
							ghostDirection[i]  = (randomNumber(CLOCKWISE, COUNTERCLOCKWISE) == CLOCKWISE) ? rotateClockwise(UP) : rotateCounterClockwise(UP);
						} 
						// if ghost hits a barrier from the left
						if (objectLeft(ghost, barrier, GHOST_SPEED) && ! (objectAbove(ghost, barrier, GHOST_SPEED) || objectBelow(ghost, barrier, GHOST_SPEED) || objectRight(ghost, barrier, GHOST_SPEED))) {

							// then we reset the ghost back to the left to his previous position by subtracting speed from X
							ghosts[i].setX(ghosts[i].getX() - GHOST_SPEED);

							// and we rotate the ghost to face another direction
							ghostDirection[i]  = (randomNumber(CLOCKWISE, COUNTERCLOCKWISE) == CLOCKWISE) ? rotateClockwise(RIGHT) : rotateCounterClockwise(RIGHT);
						} 
						// if ghost hits a barrier from the right
						if (objectRight(ghost, barrier, GHOST_SPEED) && ! (objectAbove(ghost, barrier, GHOST_SPEED) || objectBelow(ghost, barrier, GHOST_SPEED) || objectLeft(ghost, barrier, GHOST_SPEED))) {

							// then we reset the ghost back right to his previous position by adding speed to Y
							ghosts[i].setX(ghosts[i].getX() + GHOST_SPEED);

							// and we rotate the ghost to face another direction
							ghostDirection[i]  = (randomNumber(CLOCKWISE, COUNTERCLOCKWISE) == CLOCKWISE) ? rotateClockwise(LEFT) : rotateCounterClockwise(LEFT);
						}

						// reset the ghost to the updated coordinates
						ghost = ghosts[i].getBoundsInParent();
					}

				}
			}		

			/**
			 *  Pac-Man/Barrier Collision detection
			 */
			// set bounds for pacman
			Bounds pacBox = pacMan.getBoundsInParent();

			// loop to iterate through all the maze barriers
			for (int i = 0; i < maze.length; i++) {

				// set bounds for the barrier
				Bounds barrier = maze[i].getBoundsInParent();

				// check to see if pacBox is intersecting with the barrier
				// no need to reset pacMan position if there is no intersection
				if (pacBox.intersects(barrier)) {
					// must check for all four sides to reduce jumpy movement
					// if pacMan hits a barrier above
					if (objectAbove(pacBox, barrier, PACMAN_SPEED) && ! (objectBelow(pacBox, barrier, PACMAN_SPEED) || objectLeft(pacBox, barrier, PACMAN_SPEED) || objectRight(pacBox, barrier, PACMAN_SPEED))) {
						// then we reset pacMan back up to his previous position by subtracting speed from Y
						pacMan.setY(pacMan.getY() - PACMAN_SPEED);
						// stop pacMan movement
						pacmanYChange = 0;
					} 
					// if pacMan hits a barrier below
					if (objectBelow(pacBox, barrier, PACMAN_SPEED) && ! (objectAbove(pacBox, barrier, PACMAN_SPEED) || objectLeft(pacBox, barrier, PACMAN_SPEED) || objectRight(pacBox, barrier, PACMAN_SPEED))) {
						// then we reset pacMan back down to his previous position by adding speed to X
						pacMan.setY(pacMan.getY() + PACMAN_SPEED);
						// stop pacMan movement
						pacmanYChange = 0;
					} 
					// if pacMan hits a barrier from the left
					if (objectLeft(pacBox, barrier, PACMAN_SPEED) && ! (objectAbove(pacBox, barrier, PACMAN_SPEED) || objectBelow(pacBox, barrier, PACMAN_SPEED) || objectRight(pacBox, barrier, PACMAN_SPEED))) {
						// then we reset pacMan back to the left to his previous position by subtracting speed from X
						pacMan.setX(pacMan.getX() - PACMAN_SPEED);
						// stop pacMan movement
						pacmanXChange = 0;
					} 
					// if pacMan hits a barrier from the right
					if (objectRight(pacBox, barrier, PACMAN_SPEED) && ! (objectAbove(pacBox, barrier, PACMAN_SPEED) || objectBelow(pacBox, barrier, PACMAN_SPEED) || objectLeft(pacBox, barrier, PACMAN_SPEED))) {
						// then we reset pacMan back right to his previous position by adding speed to Y
						pacMan.setX(pacMan.getX() + PACMAN_SPEED);
						// stop pacMan movement
						pacmanXChange = 0;
					}

					// reset the pacBox to the updated coordinates
					pacBox = pacMan.getBoundsInParent();
				}
			}
		
			/**
			 * Food/PacMan collision detection
			 */
			
			// loop to iterate through all the food
			for (int i = 0; i < food.length; i++) {

				// set bounds for food
				Bounds foodBox = food[i].getBoundsInParent();

				// when pacMan intersects with visible food
				if (pacBox.intersects(foodBox) && food[i].isVisible()) {
					
					// play chomping noise
					chomp.play();
					
					// food disappears
					food[i].setVisible(false);
					
					// user score increases by 1
					userScore += 1;
					
					// score display updates
					txtUserScore.setText("Score: " + userScore);
				}
			}

			/**
			 *  Pac-Man/Ghost Collision detection
			 */
			// set bounds for pacman
			//pacBox = pacMan.getBoundsInParent();
			
			for (int i = 0; i < ghosts.length; i++) {
				Bounds ghost = ghosts[i].getBoundsInParent();
				
				// once pacMan and ghost have made contact
				if (pacBox.intersects(ghost)) {
					
					// play death music
					death.play();
					
					// pause the game
					timer.stop();
					
					// make game over visible
					gameOver.setVisible(true);
					
					// make reset visible
					reset.setVisible(true);
				}
			}
			
			/**
			 * Repositioning
			 */
			// reposition pacMan for the next frame
			pacMan.setX(pacMan.getX() + pacmanXChange);
			pacMan.setY(pacMan.getY() + pacmanYChange);
			
			
			/**
			 * Win Screen
			 */
			// if all the food is eaten
			if (userScore == food.length) {
				
				// play win music
				winNoise.play();
				
				// stop the gameplay
				timer.stop();
				
				// display win message
				win.setVisible(true);
				
				// display playAgain
				reset.setVisible(true);
			}
		}
	}
}


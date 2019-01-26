package spaceInvaders;

import java.util.ArrayList;
/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * SpaceGame.java 
 * Inspired by https://youtu.be/FVo1fm52hz0
 */
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpaceGame extends Application {
	/**
	 * Variables and Constants
	 */	
	// timer to control the animations
	GameTimer timer;

	// screen constants
	public static final int SCREEN_WIDTH  = 400;
	public static final int SCREEN_HEIGHT = 600;
	// gap b/w invaders
	public static final int GAP           = 50;

	// gameplay constant
	public final int    NUM_INVADERS  = 5;
	public final double PERCENT       = 0.1;

	// moving booleans
	boolean moveLeft;
	boolean moveRight;

	// game over var
	boolean gameOver;
	int deadInvaders;

	// image variables
	Image shipImage    = new Image(getClass().getResource("images/Ship.png").toString());
	Image invaderImage = new Image(getClass().getResource("images/Invader.png").toString());

	// Sprites
	// invader array
	Shooter [][] invaders = new Alien[NUM_INVADERS][NUM_INVADERS];
	// bullet array
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	// main player
	private Ship player = new Ship(SCREEN_WIDTH / 2 - Shooter.OFFSET, SCREEN_HEIGHT - 60, "Ship", shipImage);

	// root layout
	private Pane root = new Pane();

	/**
	 * Variables for switching stages and button actions
	 */
	Stage myStage;
	Stage secondStage;

	static SpaceGame instance;

	SpaceOverController gameOverControl;

	Scene scnMenu, scnMain, scnOver;

	FXMLLoader loadOver;

	/**
	 * Start Method
	 */
	@Override
	public void start(Stage myStage) throws Exception {

		instance = this;
		this.myStage = myStage;
		secondStage = new Stage();

		mainGame();

		scnMenu = new Scene(FXMLLoader.load(getClass().getResource("SpaceMenu.fxml")));
		loadOver = new FXMLLoader(getClass().getResource("SpaceOver.fxml"));

		Parent parOver = loadOver.load();
		scnOver = new Scene(parOver);
		gameOverControl = loadOver.getController();
		myStage.setScene(scnMenu);
		myStage.setTitle("Space Invaders");
		myStage.show();
	}

	/**
	 * Main Game Method
	 */
	private void mainGame() {
		// set screen size
		root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		// set screen colour
		root.setStyle("-fx-background-color: #000000;");

		// add player to the root
		root.getChildren().add(player);

		Scene scene = new Scene(root);

		// starts movement and shooting
		scene.setOnKeyPressed(event-> {
			switch (event.getCode()) {
			case LEFT:
				moveLeft  = true;
				break;
			case RIGHT:
				moveRight = true;
				break;
			case SPACE:
				Bullet newBullet = player.shoot();
				root.getChildren().add(newBullet);
				newBullet.moveUp();
				bullets.add(newBullet);
				break;
			default:
				break;
			}
		});

		// stops movement
		scene.setOnKeyReleased(event-> {
			switch (event.getCode()) {
			case LEFT:
				moveLeft  = false;
				break;
			case RIGHT:
				moveRight = false;
				break;
			default:
				break;
			}
		});

		// create timer to control the animation
		timer = new GameTimer();

		// start the timer
		timer.start();

		// set up the invaders
		runInvaders();

		scnMain = scene;
	}

	/**
	 * Initializes all the invaders
	 */
	private void runInvaders() {
		// row the number of new lines
		for (int row = 0; row < invaders.length; row++) {
			// col the number of invaders per line
			for (int col = 0; col < invaders[row].length; col++) {
				invaders[row][col] = new Alien(SCREEN_WIDTH / 5 + col * GAP, GAP + row * GAP, "Invader", invaderImage);
				root.getChildren().add(invaders[row][col]);
			}
		}
	}

//	/**
//	 * Event handling
//	 */
//	public void handleKeyPressed(KeyEvent event) {
//		KeyCode code = event.getCode();
//		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
//			switch (code) {
//			case LEFT:
//				moveLeft  = true;
//				break;
//			case RIGHT:
//				moveRight = true;
//				break;
//			case SPACE:
//				Bullet newBullet = player.shoot();
//				root.getChildren().add(newBullet);
//				newBullet.moveUp();
//				bullets.add(newBullet);
//				break;
//			default:
//				break;
//			}
//		}
//	}
//
//	public void handleKeyReleased(KeyEvent event) {
//		KeyCode code = event.getCode();
//		switch (code) {
//		case LEFT:
//			moveLeft  = false;
//			break;
//		case RIGHT:
//			moveRight = false;
//			break;
//		default:
//			break;
//		}
//	}


	/**
	 * Game Timer
	 */
	class GameTimer extends AnimationTimer {

		long lastUpdate = 0;

		/**
		 * Main Game Loop
		 */
		@Override
		public void handle(long now) {

			/**
			 * Method to control events that happen every second
			 */
			// invaders will shoot about every second
			if (now - lastUpdate >= 1_000_000_000) {
				for (int row = 0; row < invaders.length; row++) {
					for (int col = 0; col < invaders[row].length; col++) {
						// invader has a certain percent chance of shooting
						if (Math.random() < PERCENT) {
							Bullet newBullet = invaders[row][col].shoot();
							root.getChildren().add(newBullet);
							newBullet.moveDown();
							bullets.add(newBullet);
						}
					}
				}	
				lastUpdate = now;
			}

			/**
			 * Handling Movement
			 */
			if (moveLeft) {
				player.moveLeft();
			} else if (moveRight) {
				player.moveRight();
			} 

			/**
			 * Ship/Screen Collision detection
			 */
			// do not allow ship to exit screen
			Bounds ship = player.getBoundsInParent();

			// find distance b/w left and right of screen
			double distRight = SCREEN_WIDTH - ship.getMaxX();
			double distLeft  = ship.getMinX();

			// if ship hits the edge of the screen from the right
			if (distRight >= 0 && distRight <= player.SPEED) {
				// then we reset the ship back left to its previous position 
				// by subtracting speed from X
				player.setX(player.getX() - player.SPEED);
			}

			// if ship hits the edge of the screen from the left
			if (distLeft >= 0 && distLeft <= player.SPEED) {
				// then we reset the ship back right to its previous position 
				// by adding speed to X
				player.setX(player.getX() + player.SPEED);
			}

			/**
			 * Bullet/Ship Collision Detection
			 */
			for (int i = 0; i < bullets.size(); i++) {
				if (bullets.get(i).getBoundsInParent(). intersects(player.getBoundsInParent())) {
					// player disappears
					player.setVisible(false);
					// bullet disappears
					bullets.get(i).setVisible(false);
				}
			}

			/**
			 * Bullet/Aliens Collision Detection
			 */
			for (int i = 0; i < bullets.size(); i++) {
				for (int row = 0; row < invaders.length; row++) {
					for (int col = 0; col < invaders[row].length; col++) {
						if (bullets.get(i).getBoundsInParent().intersects(invaders[row][col].getBoundsInParent())) {
							// alien disappears
							invaders[row][col].setVisible(false);
							deadInvaders++;
							// bullet disappears
							bullets.get(i).setVisible(false);
						}
					}
				}
			}
		}
	}

	/**
	 * Button control method
	 */
	static public SpaceGame getInstance() {
		return instance;
	}

	public void playGame() {
		myStage.setScene(scnMain);
		myStage.show();
	}

	public void mainMenu() {
		myStage.setScene(scnMenu);
	}

	public void hideSecond() {
		secondStage.hide();
	}

	public void gameOver(String winner) {
		gameOverControl.setWinnerText(winner);
		gameOverControl.setWinnerImage(winner);
		secondStage.setScene(scnOver);
		secondStage.setTitle("Game Over");
		secondStage.show();
		myStage.hide();
	}

	/**
	 * Main Method
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
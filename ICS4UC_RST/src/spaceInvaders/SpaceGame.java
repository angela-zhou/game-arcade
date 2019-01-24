package spaceInvaders;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * SpaceGame.java 
 * Inspired by https://youtu.be/FVo1fm52hz0
 */
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import simpleIO.Console;

public class SpaceGame extends Application {
	/**
	 * Initialization
	 */	
	// screen constants
	public static final int SCREEN_WIDTH  = 400;
	public static final int SCREEN_HEIGHT = 600;
	// gap b/w invaders
	public static final int GAP           = 50;

	// time var
	public final double MILISECONDS   = 0.016;
	public final int    TIME_PERIOD   = 2;
	private double      time          = 0;

	// gameplay constant
	public final int    NUM_INVADERS  = 5;
	public final double PERCENT       = 0.25;
	public final int    OFFSET        = 20;

	// boolean variables
	boolean moveLeft;
	boolean moveRight;

	// invader array
	Shooter [][] invaders = new Shooter[NUM_INVADERS][NUM_INVADERS];

	// image variables
	Image shipImage    = new Image(getClass().getResource("images/Ship.png").toString());
	Image invaderImage = new Image(getClass().getResource("images/Invader.png").toString());
	Image bulletImage  = new Image(getClass().getResource("images/Bullet.png").toString());

	// root layout
	private Pane root = new Pane();

	// main player
	private Shooter player = new Shooter(SCREEN_WIDTH / 2 - OFFSET, SCREEN_HEIGHT - 60, "Ship", shipImage);

	/**
	 * Initializes all the invaders
	 */
	private void runInvaders() {

		//		for (int i = 0; i < NUM_INVADERS; i++) {
		//			Shooter invader = new Shooter(SCREEN_WIDTH / 5 + i*50, 150, "Invader", invaderImage);
		//			root.getChildren().add(invader);
		//		}

		// row the number of new lines
		for (int row = 0; row < invaders.length; row++) {
			// col the number of invaders per line
			for (int col = 0; col < invaders[row].length; col++) {
				Shooter invader = new Shooter(SCREEN_WIDTH / 5 + col*50, 50 + row * 50, "Invader", invaderImage);
				root.getChildren().add(invader);
			}
		}
	}

	/**
	 * Initializes the bullets
	 */
	private void shoot(Shooter shooter) {
		// bullet needs to come out of the middle of the ship
		int x = (int) (shooter.getX() + OFFSET);
		int y = (int) (shooter.getY());
		Shooter bullet = new Shooter(x, y, shooter.TYPE + " Bullet", bulletImage);
		root.getChildren().add(bullet);
	}

	/**
	 * Returns list of all shooters
	 */
	private List<Shooter> shooters() {
		return root.getChildren().stream().map(n -> (Shooter) n).collect(Collectors.toList());
	}

	/**
	 * Start Method
	 */
	@Override
	public void start(Stage myStage) throws Exception {
		Scene scene = new Scene(initialize());

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
				shoot(player);
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

		myStage.setScene(scene);
		myStage.setTitle("Space Invaders");
		myStage.show();
	}


	/**
	 * Set up the game
	 */
	private Parent initialize() {

		// set screen size
		root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		// set screen colour
		root.setStyle("-fx-background-color: #000000;");

		// add player to the root
		root.getChildren().add(player);

		// create timer to control the animation
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				// main game loop
				update();
			}
		};

		// start the timer
		timer.start();

		// set up the invaders
		runInvaders();

		return root;
	}


	/**
	 * Main Game loop
	 */
	private void update() {
		// increase time when screen updates
		time += MILISECONDS;

		// smoother movement if the update() method handles movement
		// compared to the key pressed method
		if (moveLeft) {
			player.moveLeft();
		} else if (moveRight) {
			player.moveRight();
		} 

		/**
		 * Bullet Collision Detection
		 */
		shooters().forEach(item -> {
			switch (item.TYPE) {

			case "Invader Bullet":
				// invader bullets move down
				item.moveDown();

				// collision detection with bullet and player
				if (item.getBoundsInParent(). intersects(player.getBoundsInParent())) {
					// player disappears
					player.isDead = true;
					// bullet disappears
					item.isDead = true;
				}

				break;

			case "Ship Bullet":
				// player bullets move up
				item.moveUp();

				// collision detection with bullet and invader
				shooters().stream().filter(e-> e.TYPE.equals("Invader")).forEach(invader -> {
					if (item.getBoundsInParent().intersects(invader.getBoundsInParent())) {
						// invader disappears
						invader.isDead = true;
						// bullet diappears
						item.isDead = true;
					}
				});
				break;
			case "Invader":
				// if the time period is up
				if (time > TIME_PERIOD) {
					// invader has a certain percent chance of shooting
					if (Math.random() < PERCENT) {
						shoot(item);
					}
				}
				break;

			case "Ship":
				// do not allow ship to exit screen
				Bounds ship = item.getBoundsInParent();

				// find distance b/w left and right of screen
				double distRight = SCREEN_WIDTH - ship.getMaxX();
				double distLeft  = ship.getMinX();

				// if ship hits the edge of the screen from the right
				if (distRight >= 0 && distRight <= item.SPEED) {
					// then we reset the ship back left to its previous position 
					// by subtracting speed from X
					item.setX(item.getX() - item.SPEED);
				}

				// if ship hits the edge of the screen from the left
				if (distLeft >= 0 && distLeft <= item.SPEED) {
					// then we reset the ship back right to its previous position 
					// by adding speed to X
					item.setX(item.getX() + item.SPEED);
				}

			}
		});

		// removes dead invaders or players
		root.getChildren().removeIf(dead -> {
			Shooter shooter = (Shooter) dead;
			return shooter.isDead;
		});

		// reset time
		if (time > TIME_PERIOD) {
			time = 0;
		}
	}

	/**
	 * Main Method
	 */
	public static void main(String[] args) {
		launch(args);
	}

}

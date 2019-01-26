package spaceInvaders;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * SpaceGame.java 
 * Inspired by https://youtu.be/FVo1fm52hz0
 */
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
	public final double PERCENT       = 0.045;

	// game over var
	boolean gameOver;
	int deadInvaders;
	
	// score var
	int losses;
	int wins;
	Text txtScore;

	// string var
	String shipString    = "Ship";
	String invaderString = "Invaders";

	// image variables
	Image shipImage    = new Image(getClass().getResource("images/Ship.png").toString());
	Image invaderImage = new Image(getClass().getResource("images/Invader.png").toString());

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
		
		// rectangle for decorative purposes
		Rectangle homeBase = new Rectangle(350, 3, Color.LIME);
		homeBase.setX(25);
		homeBase.setY(SCREEN_HEIGHT - 20);
		root.getChildren().add(homeBase);
		
		txtScore = new Text(14, 34, "Wins: <" + wins + ">   Losses: <" + losses + ">");
		txtScore.setFont(new Font("OCR A Extended", 18));
		txtScore.setFill(Color.WHITE);
		root.getChildren().add(txtScore);

		// add player to the root
		root.getChildren().add(player);

		Scene scene = new Scene(root);

		// starts movement and shooting
		scene.setOnKeyPressed(event-> {
			switch (event.getCode()) {
			case LEFT:
				player.moveLeft  = true;
				break;
			case RIGHT:
				player.moveRight = true;
				break;
			case SPACE:
				if (!player.isDead) {
					//create new bullet
					Bullet newBullet = player.shoot(shipString);
					// display new bullet
					root.getChildren().add(newBullet);
					// add new bullet to the array
					bullets.add(newBullet);
					// ship bullets move up
					bullets.get(bullets.size() - 1).moveUp = true;
				}
				break;
			default:
				break;
			}
		});

		// stops movement
		scene.setOnKeyReleased(event-> {
			switch (event.getCode()) {
			case LEFT:
				player.moveLeft  = false;
				break;
			case RIGHT:
				player.moveRight = false;
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
		for (int row = 0; row < invaders.length; row++) {
			for (int col = 0; col < invaders[row].length; col++) {
				// create new invader
				invaders[row][col] = new Alien(SCREEN_WIDTH / 5 + col * GAP, GAP + 10 + row * GAP, "Invader", invaderImage);
				// display invader
				root.getChildren().add(invaders[row][col]);
			}
		}
	}

	/**
	 * Game Timer
	 * 	Handle method (Main game loop)
	 * 		Movement Control
	 * 		Collision Detection
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
							// if they are not dead
							if (!invaders[row][col].isDead) {
								// create new bullet
								Bullet newBullet = invaders[row][col].shoot(invaderString);
								// display new bullet
								root.getChildren().add(newBullet);
								// add bullet to the array
								bullets.add(newBullet);
								// invader bullets move down
								bullets.get(bullets.size() - 1).moveDown = true;
							}
						}
					}
				}	
				lastUpdate = now;
			}

			/**
			 * Handling Movement
			 */
			// player movement
			if (player.moveLeft) {
				player.moveLeft();
			} else if (player.moveRight) {
				player.moveRight();
			} 

			// bullet movement
			for (int i = 0; i < bullets.size(); i++) {
				if (bullets.get(i).moveUp) {
					bullets.get(i).moveUp();
				} else if (bullets.get(i).moveDown) {
					bullets.get(i).moveDown();
				}
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
			 * Alien Bullet/Ship Collision Detection
			 */
			for (int i = 0; i < bullets.size(); i++) {
				// if the bullet is a invader bullet
				if (bullets.get(i).TYPE.equals(invaderString)) {
					// and the bullet is not dead 
					if (!bullets.get(i).isDead) {
						// and the bullet and ship collide
						if (bullets.get(i).getBoundsInParent().intersects(player.getBoundsInParent())) {
							// player disappears (did not remove them from the root so reset is easier)
							player.isDead = true;
							player.setVisible(false);
							// bullet disappears
							bullets.get(i).isDead = true;
							//bullets.get(i).setVisible(false);
							root.getChildren().remove(bullets.get(i));
						}
					}
				}
			}

			/**
			 * Ship Bullet/Aliens Collision Detection
			 */
			for (int i = 0; i < bullets.size(); i++) {
				for (int row = 0; row < invaders.length; row++) {
					for (int col = 0; col < invaders[row].length; col++) {
						// if the bullet is a ship bullet
						if (bullets.get(i).TYPE.equals(shipString)) {
							// and the bullet and the invader are not dead
							if (!bullets.get(i).isDead && !invaders[row][col].isDead) {
								// and the bullet and alien collide
								if (bullets.get(i).getBoundsInParent().intersects(invaders[row][col].getBoundsInParent())) {
									// alien disappears
									invaders[row][col].isDead = true;
									deadInvaders++;
									root.getChildren().remove(invaders[row][col]);
									// bullet disappears
									bullets.get(i).isDead = true;
									//bullets.get(i).setVisible(false);
									root.getChildren().remove(bullets.get(i));
								}
							}
						}
					}
				}
			}
			
			if (player.isDead) {
				gameOver = true;
				losses ++;
				timer.stop();
				gameOver("Invader Wins");

			}

			if (deadInvaders == (NUM_INVADERS * NUM_INVADERS)) {
				gameOver = true;
				wins++;
				timer.stop();
				gameOver("Ship Wins");
			}
		}
	}

	/**
	 * Button control method
	 */
	static public SpaceGame getInstance() {
		return instance;
	}

	public void hideSecond() {
		secondStage.hide();
	}
	
	public void mainMenu() {
		myStage.setScene(scnMenu);
	}
	
	public void playGame() {
		myStage.setScene(scnMain);
		myStage.show();
	}
	
	public void reset() {
		// reset game
		gameOver = false;
		// reset player
		player.reset();
		player.setVisible(true);
		// reset bullets 
		for (int i = 0; i < bullets.size(); i++) {
			root.getChildren().remove(bullets.get(i));
		}
		bullets.clear();
		// reset invaders
		deadInvaders = 0;
		for (int row = 0; row < invaders.length; row++) {
			for (int col = 0; col < invaders[row].length; col++) {
				root.getChildren().remove(invaders[row][col]);
			}
		}
		runInvaders();
		// set text
		txtScore.setText("Wins: <" + wins + ">   Losses: <" + losses + ">");
		//start timer
		timer.start();
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
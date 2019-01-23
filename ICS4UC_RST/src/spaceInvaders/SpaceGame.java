package spaceInvaders;

import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpaceGame extends Application {
	/**
	 * Initialization
	 */	
	// screen constants
	public static final int    SCREEN_WIDTH  = 500;
	public static final int    SCREEN_HEIGHT = 700;
	
	// time var
	public static final double MILISECONDS   = 0.016;
	public static final int    TIME_PERIOD   = 2;
	private double             time          = 0;
	
	// gameplay constant
	public static final int    NUM_ENEMIES   = 5;
	public static final double PERCENT       = 0.3;

	// boolean variables
	boolean moveLeft;
	boolean moveRight;
	
	// root layout
	private Pane root = new Pane();
	
	// main player
	private Shooter player = new Shooter(250, 650, Shooter.PLAYER_SIZE, Shooter.PLAYER_SIZE, 
			Shooter.PLAYER_STRING, Shooter.PLAYER_COLOUR);
		
	/**
	 * Initializes all the enemies
	 */
	private void runEnemies() {
		
		for (int i = 0; i < NUM_ENEMIES; i++) {
			Shooter s = new Shooter(100 + i*50, 150, Shooter.ENEMY_SIZE , Shooter.ENEMY_SIZE , 
					Shooter.ENEMY_STRING, Shooter.ENEMY_COLOUR);
			
			root.getChildren().add(s);
		}
	}
	
	/**
	 * Initializes the bullets
	 */
	private void shoot(Shooter shooter) {
		Shooter bullet = new Shooter((int) (shooter.getTranslateX() + 20), (int) (shooter.getTranslateY()), 
				Shooter.BULLET_WIDTH, Shooter.BULLET_HEIGHT, shooter.TYPE + Shooter.BULLET_STRING, Shooter.BULLET_COLOUR);
		root.getChildren().add(bullet);
	}
	
	/**
	 * Returns list of shooters
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
		
		// set up the enemies
		runEnemies();

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
		
		// for the different cases, player or enemy can die
		shooters().forEach(shooter -> {
			switch (shooter.TYPE) {
			
			case Shooter.ENEMY_STRING + Shooter.BULLET_STRING:
				// enemy bullets move down
				shooter.moveDown();
				
				// collision detection with bullet and player
				if (shooter.getBoundsInParent(). intersects(player.getBoundsInParent())) {
					// player disappears
					player.isDead = true;
					// bullet disappears
					shooter.isDead = true;
				}
				
				break;
			
			case Shooter.PLAYER_STRING + Shooter.BULLET_STRING:
				// player bullets move up
				shooter.moveUp();
				
				// collision detection with bullet and enemy
				shooters().stream().filter(e-> e.TYPE.equals(Shooter.ENEMY_STRING)).forEach(enemy -> {
					if (shooter.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
						// enemy disappears
						enemy.isDead = true;
						// bullet diappears
						shooter.isDead = true;
					}
				});
				break;
			case Shooter.ENEMY_STRING:
				// if the time period is up
				if (time > TIME_PERIOD) {
					// enemy has a certain percent chance of shooting
					if (Math.random() < PERCENT) {
						shoot(shooter);
					}
				}
				break;
			}
		});
		
		// removes dead enemies or players
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

package pong;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* Main.java
* Main class of the Pong Game.
*/

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application{

	//Game states, values, nodes, and objects
	static Main instance;
	double SCREEN_HEIGHT = 600, SCREEN_WIDTH = 800;
	double BALL_SIZE = 8, MIN_SPEED = 8, MAX_SPEED = 12;
	double PADDLE_WIDTH = 5, PADDLE_HEIGHT = 80, PADDLE_SPEED = 6, PADDLE_DECAY = 100, PADDLE_GAP = 30;
	int MAX_SCORE = 7;
	final static double SCORE_INDENT = 80, SCORE_VERT = 40, SCORE_SIZE = 35, PAUSE_VERT = 120;
	PSettingsControl setControl;
	Text txtP1, txtP2, txtPause;
	GameTimer timer;
	Stage myStage;
	Scene scnMenu, scnGame, scnSettings;
	Group gameGroup;
	Paddle p1, p2;
	Ball b1;
	
	/**
	 * Starts the application.
	 */
	@Override
	public void start(Stage myStage) throws Exception {
		//Globalizes variables
		instance = this;
		this.myStage = myStage;
		
		//Initializes objects
		txtPause = new Text();
		timer = new GameTimer();
		gameGroup = new Group();
		twoPlayerInit();
		
		//Creates scenes
		scnGame = new Scene(gameGroup, SCREEN_WIDTH, SCREEN_HEIGHT);
		scnGame.addEventHandler(KeyEvent.ANY, new PongKeyEvent());
		scnGame.setFill(Color.LIGHTGRAY);
		scnMenu = new Scene(FXMLLoader.load(getClass().getResource("PMenu.fxml")));
		scnSettings = new Scene(FXMLLoader.load(getClass().getResource("PSettings.fxml")));
		
		//Initializes stage
		myStage.setScene(scnMenu);
		myStage.setTitle("Pong");
		myStage.show();
	}
	
	/**
	 * Initializes game object and nodes.
	 */
	public void twoPlayerInit() {
		//Paddle objects
		p1 = new Paddle(PADDLE_SPEED, 1, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_GAP, SCREEN_WIDTH, SCREEN_HEIGHT);
		p2 = new Paddle(PADDLE_SPEED, 2, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_GAP, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Text objects
		txtP1 = new Text(Integer.toString(p1.getScore()));
		txtP1.setFont(new Font(SCORE_SIZE));
		txtP1.setX(SCORE_INDENT);
		txtP1.setY(SCORE_VERT);
		
		txtP2 = new Text(Integer.toString(p2.getScore()));
		txtP2.setFont(new Font(35));
		txtP2.setX(SCREEN_WIDTH - SCORE_INDENT - txtP2.getLayoutBounds().getWidth());
		txtP2.setY(SCORE_VERT);
		
		txtPause = new Text("Press Enter to Start/Stop\nESC for Menu");
		txtPause.setTextAlignment(TextAlignment.CENTER);
		txtPause.setFont(new Font(50));
		txtPause.setFill(Color.RED);
		txtPause.setX(SCREEN_WIDTH / 2 - txtPause.getLayoutBounds().getWidth() / 2);
		txtPause.setY(SCREEN_HEIGHT - 50);
		
		//Ball Object
		b1 = new Ball(MIN_SPEED, MAX_SPEED, BALL_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Adds all objects and nodes to the game group
		gameGroup.getChildren().addAll(p1, p2, b1, txtP1, txtP2, txtPause);
	}
	
	/**
	 * Updates the screen size (for game).
	 */
	private void updateScreenSize() {
		//Gets new screen sizes
		SCREEN_WIDTH = scnGame.getWindow().getWidth();
		SCREEN_HEIGHT = scnGame.getWindow().getHeight();
		
		//Send new size values to all game objects
		p1.updateScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
		p2.updateScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
		b1.updateScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Updates text positions
		txtP2.setX(SCREEN_WIDTH - SCORE_INDENT - txtP2.getLayoutBounds().getWidth());
		txtP2.setY(SCORE_VERT);
		txtPause.setX(SCREEN_WIDTH / 2 - txtPause.getLayoutBounds().getWidth() / 2);
		txtPause.setY(SCREEN_HEIGHT - PAUSE_VERT);
	}
	
	/**
	 * Starts the pong game.
	 */
	public void playGame() {
		//Sets scene to game scene and adds listeners for size
		myStage.setScene(scnGame);
		myStage.widthProperty().addListener(gameListener);
		myStage.heightProperty().addListener(gameListener);
		updateScreenSize();
	}

	/**
	 * Updates game for when a player has won.
	 * @param winner
	 *			The paddle that won the game.
	 */
	public void gameOver(Paddle winner) {
		//Updates text for game over
		updateGfx();
		txtPause.setText("Player " + winner.playerNum + " wins!\nEnter to Restart");
		txtPause.setX(SCREEN_WIDTH / 2 - txtPause.getLayoutBounds().getWidth() / 2);
		
		//Updates timer for game over
		timer.gameOver();
		
		//Resets paddle scores for new game
		p1.resetScore();
		p2.resetScore();
	}
	
	/**
	 * Updates text (Paddle scores).
	 */
	public void updateGfx() {
		txtP1.setText(Integer.toString(p1.getScore()));
		txtP2.setText(Integer.toString(p2.getScore()));
	}
	
	/**
	 * Exits to menu from game (player hit ESC).
	 */
	public void escapeGame() {
		//Pauses the game
		pause();
		
		//Removes game size listeners and sets scene to menu
		myStage.widthProperty().removeListener(gameListener);
		myStage.heightProperty().removeListener(gameListener);
		myStage.setScene(scnMenu);
	}

	/**
	 * Pauses or resumes the game (switches state).
	 */
	public void pause() {
		//If timer is running, stop timer and show pause text
		if(timer.isRunning()) {
			timer.stop();
			txtPause.setOpacity(1);
		}
		//If timer is stopped, start timer and remove pause text
		else {
			timer.start();
			txtPause.setOpacity(0);
		}
	}
	
	/**
	 * Sets game play or pause to a certain state.
	 * @param state
	 *			Boolean stating whether or not the game is paused. (true = paused)
	 */
	public void pause(boolean state) {
		//If true, stop game and show pause text
		if(state) {
			timer.stop();
			txtPause.setOpacity(1);
		}
		//Otherwise start game and remove pause text
		else {
			timer.start();
			txtPause.setOpacity(0);
		}
	}

	/**
	 * Listener for game screen size.
	 */
	ChangeListener<Number> gameListener = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			updateScreenSize();
		}
	};
	
	/**
	 * Timer for game animations.
	 */
	class GameTimer extends AnimationTimer {
		
		//Not really necessary since whole game is on one thread but just wanted to flex this keyword
		private volatile boolean running;
		private volatile boolean gameOver = false;
		
		/**
		 * Starts timer.
		 */
		@Override
		public void start() {
			//Resets game if starting from game over
			if(gameOver) {
				gameOver = false;
				txtPause.setText("Press Enter to Start/Stop\nESC for Menu");
				txtPause.setX(SCREEN_WIDTH / 2 - txtPause.getLayoutBounds().getWidth() / 2);
				txtPause.setOpacity(1);
				b1.reset();
				return;
			}
			super.start();
			running = true;
		}

		/**
		 * Stops the timer.
		 */
		@Override
		public void stop() {
			super.stop();
			running = false;
		}
		
		/**
		 * Stops timer for game over.
		 */
		public void gameOver() {
			pause(true);
			gameOver = true;
		}
		
		/**
		 * Returns running state.
		 * @return
		 *			Boolean indicating if timer is running.
		 */
		public boolean isRunning() {
			return running;
		}
		
		/**
		 * Updates paddle and ball position while timer is running.
		 * @param arg0
		 *			Necessary arg.
		 */
		@Override
		public void handle(long arg0) {
			p1.move();
			p2.move();
			b1.move();
		}
	}
	
	/**
	 * Class that handles user key inputs for game.
	 */
	class PongKeyEvent implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent e) {
			EventType<KeyEvent> type = e.getEventType();
			KeyCode code = e.getCode();
			
			//If key is typed
			if(type == KeyEvent.KEY_PRESSED) {
				if(code == KeyCode.W)
					p1.setUp(true);
				if(code == KeyCode.S)
					p1.setDown(true);
				if(code == KeyCode.UP)
					p2.setUp(true);
				if(code == KeyCode.DOWN)
					p2.setDown(true);
				if(code == KeyCode.ENTER) {
					pause();
				}
				if(code == KeyCode.ESCAPE) {
					escapeGame();
				}
			}
			//If key is released
			if(type == KeyEvent.KEY_RELEASED) {
				if(code == KeyCode.W)
					p1.setUp(false);
				if(code == KeyCode.S)
					p1.setDown(false);
				if(code == KeyCode.UP)
					p2.setUp(false);
				if(code == KeyCode.DOWN)
					p2.setDown(false);
			}
		}
	}
	
	/**
	 * Updates game objects with current settings.
	 */
	public void updateSettings() {
		
		//Settings for paddle 1
		p1.setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
		p1.setSpeed(PADDLE_SPEED);
		p1.setDecel(PADDLE_DECAY);
		
		//Settings for paddle 2
		p2.setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
		p2.setSpeed(PADDLE_SPEED);
		p2.setDecel(PADDLE_DECAY);
		
		//Ball settings
		b1.setSize(BALL_SIZE);
		b1.setMinSpeed(MIN_SPEED);
		b1.setMaxSpeed(MAX_SPEED);
	}
	
	/**
	 * Updates game settings using passed double array.
	 * @param vals
	 *			A double array containing new settings to be used.
	 */
	public void updateSettings(Double[] vals) {
		//Updates settings values one by one out of array
		MIN_SPEED = vals[0];
		MAX_SPEED = vals[1];
		BALL_SIZE = vals[2];
		PADDLE_WIDTH = vals[3];
		PADDLE_HEIGHT = vals[4];
		PADDLE_SPEED = vals[5];
		PADDLE_DECAY = vals[6];
		MAX_SCORE = vals[7].intValue();
		
		//Updates game objects with new settings
		updateSettings();
	}
	
	/**
	 * Returns current game settings in a double array.
	 * @return
	 *			A double array containing game settings.
	 */
	public Double[] getSettings() {
		return new Double[]{MIN_SPEED, MAX_SPEED, BALL_SIZE, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_SPEED, PADDLE_DECAY, MAX_SCORE * 1.0};
	}
	
	/**
	 * Sets the settings menu controller.
	 * @param cont
	 *			The Settings Controller to be used.
	 */
	public void setSettingsController(PSettingsControl cont) {
		setControl = cont;
	}
	
	/**
	 * Returns instance of this.
	 * @return
	 *			Main(this) instance.
	 */
	static public Main getInstance() {
		return instance;
	}
	
	/**
	 * @param args command-line args.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

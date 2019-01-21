package pong;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application{

	static Main instance;
	double SCREEN_HEIGHT = 600, SCREEN_WIDTH = 800, PADDLE_WIDTH = 10, PADDLE_HEIGHT = 70, PADDLE_SPEED = 5, PADDLE_GAP = 30;
	GameTimer timer;
	Stage myStage;
	Scene scnMenu, scnGame, scnSettings;
	Group gameGroup;
	Paddle p1, p2;
	Ball b1;
	ArrayList<Ball> balls= new ArrayList<Ball>();
	
	@Override
	public void start(Stage myStage) throws Exception {
		instance = this;
		this.myStage = myStage;
		
		gameGroup = new Group();
		twoPlayerInit();
		
		scnGame = new Scene(gameGroup, SCREEN_WIDTH, SCREEN_HEIGHT);
		scnGame.addEventHandler(KeyEvent.ANY, new PongKeyEvent());
		scnGame.setFill(Color.LIGHTGRAY);
		
		scnMenu = new Scene(FXMLLoader.load(getClass().getResource("PMenu.fxml")));
//		scnSettings = new Scene(FXMLLoader.load(getClass().getResource("PSettings.fxml")));
		
		timer = new GameTimer();
		
		myStage.setScene(scnMenu);
		myStage.setTitle("Pong");
		myStage.show();
	}
	
	public void twoPlayerInit() {
		p1 = new Paddle(PADDLE_SPEED, 1, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_GAP, SCREEN_WIDTH, SCREEN_HEIGHT);
		p2 = new Paddle(PADDLE_SPEED, 2, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_GAP, SCREEN_WIDTH, SCREEN_HEIGHT);
		b1 = new Ball(10, 10, 15, SCREEN_WIDTH, SCREEN_HEIGHT);
		gameGroup.getChildren().addAll(p1, p2, b1);
	}
	
	private void updateScreenSize() {
		SCREEN_WIDTH = scnGame.getWindow().getWidth();
		SCREEN_HEIGHT = scnGame.getWindow().getHeight();
		p1.updateScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
		p2.updateScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
		b1.updateScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	
	public void playGame() {
		myStage.setScene(scnGame);
		myStage.widthProperty().addListener(e -> updateScreenSize());
		myStage.heightProperty().addListener(e -> updateScreenSize());
	}
	
	class GameTimer extends AnimationTimer {
		
		//Not really necessary since whole game is on one thread but just wanted to flex this keyword
		private volatile boolean running;
		
		@Override
		public void start() {
			super.start();
			running = true;
		}
		
		@Override
		public void stop() {
			super.stop();
			running = false;
		}
		
		public boolean isRunning() {
			return running;
		}
		
		@Override
		public void handle(long arg0) {
			p1.move();
			p2.move();
			b1.move();
		}
	}
	
	class PongKeyEvent implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			EventType<KeyEvent> type = e.getEventType();
			KeyCode code = e.getCode();
			
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
					if(timer.isRunning())
						timer.stop();
					else
						timer.start();
				}
				if(code == KeyCode.ESCAPE) {
					
				}
			}
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
	
	static public Main getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

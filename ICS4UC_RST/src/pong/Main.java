package pong;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application{

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
		this.myStage = myStage;
		
		gameGroup = new Group();
		twoPlayerInit();
		
		scnGame = new Scene(gameGroup, SCREEN_WIDTH, SCREEN_HEIGHT);
		scnGame.addEventHandler(KeyEvent.ANY, new PongKeyEvent());
		scnGame.setFill(Color.LIGHTGRAY);
		myStage.widthProperty().addListener(e -> updateScreenSize());
		myStage.heightProperty().addListener(e -> updateScreenSize());
		
		timer = new GameTimer();
		timer.start();
		
		myStage.setScene(scnGame);
		myStage.setTitle("Pong");
		myStage.show();
	}
	
	public void twoPlayerInit() {
		p1 = new Paddle(PADDLE_SPEED, 1, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_GAP, SCREEN_WIDTH, SCREEN_HEIGHT);
		p2 = new Paddle(PADDLE_SPEED, 2, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_GAP, SCREEN_WIDTH, SCREEN_HEIGHT);
		b1 = new Ball(1, 5, 15, SCREEN_WIDTH, SCREEN_HEIGHT);
		gameGroup.getChildren().addAll(p1, p2, b1);
	}
	
	private void updateScreenSize() {
		SCREEN_WIDTH = scnGame.getWindow().getWidth();
		SCREEN_HEIGHT = scnGame.getWindow().getHeight();
		p1.updateScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
		p2.updateScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
		b1.updateScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	
	class GameTimer extends AnimationTimer {

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
	
	public static void main(String[] args) {
		launch(args);
	}
}

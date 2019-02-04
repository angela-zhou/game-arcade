package menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application{

	private static Menu instance;
	Stage stgMenu;
	
	// instances of games
	asteroids.Game                astGame   = new asteroids.Game();
	pacMan.PacMan                 pacGame   = new pacMan.PacMan();
	ultimateTicTacToe.Main        tttGame   = new ultimateTicTacToe.Main();
	egyptianWar.EgyptianWarGame   warGame   = new egyptianWar.EgyptianWarGame();
	spaceInvaders.SpaceGame       spaceGame = new spaceInvaders.SpaceGame();
	pong.Main					  pongGame  = new pong.Main();
	
	
	@Override
	public void start(Stage stgMenu) throws Exception {
		instance = this;
		this.stgMenu = stgMenu;
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		Scene scnMenu = new Scene(menuLoader.load());
		
		stgMenu.setScene(scnMenu);
		stgMenu.setTitle("Menu");
		stgMenu.show();
	}
	
	public void exit() {
		stgMenu.hide();
	}
	
	/**
	 * Methods to start a new stage with games
	 */
	public void runAsteroids() {
		try {
			astGame.start(new Stage());
		} catch (Exception e) {
			System.out.println("Unable to start game ### Error @ Menu.java - public void runAsteroids()");
		}
	}
	
	public void runPac() {
		try {
			pacGame.start(new Stage());
		} catch (Exception e) {
			System.out.println("Unable to start game ### Error @ Menu.java - public void runPac()");
		}
	}
	
	public void runPong() {
		try {
			pongGame.start(new Stage());
		} catch (Exception e) {
			System.out.println("Unable to start game ### Error @ Menu.java - public void runPong()");
		}
	}
	
	public void runTTT() {
		try {
			tttGame.start(new Stage());
		} catch (Exception e) {
			System.out.println("Unable to start game ### Error @ Menu.java - public void runTTT()");
		}
	}
	
	public void runWar() {
		try {
			warGame.start(new Stage());
		} catch (Exception e) {
			System.out.println("Unable to start game ### Error @ Menu.java - public void runWar()");
		}
	}
	
	public void runSpace() {
		spaceGame = new spaceInvaders.SpaceGame();
		try {
			spaceGame.start(new Stage());
		} catch (Exception e) {
			System.out.println("Unable to start game ### Error @ Menu.java - public void runSpace()");
		}
	}
	
	public static Menu getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

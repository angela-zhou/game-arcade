package menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application{

	private static Menu instance;
	// instances of games
	asteroids.Game                astGame    = new asteroids.Game();
	pacMan.PacMan                 pacGame    = new pacMan.PacMan();
	ultimateTicTacToe.Main        tttGame    = new ultimateTicTacToe.Main();
	egyptianWar.EgyptianWarGame   warGame    = new egyptianWar.EgyptianWarGame();
	//spaceGame = new 
	
	
	@Override
	public void start(Stage stgMenu) throws Exception {
		instance = this;
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		Scene scnMenu = new Scene(menuLoader.load());
		
		stgMenu.setScene(scnMenu);
		stgMenu.setTitle("Menu");
		stgMenu.show();
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
	
//	public void runSpace() {
//		try {
//			spaceGame.start(new Stage());
//		} catch (Exception e) {
//			System.out.println("Unable to start game ### Error @ Menu.java - public void runSpace()");
//		}
//	}
	
	public static Menu getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

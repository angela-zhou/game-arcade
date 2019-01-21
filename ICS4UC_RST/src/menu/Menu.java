package menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application{

	private static Menu instance;
	asteroids.Game astGame = new asteroids.Game();
	pacMan.PacMan pacGame = new pacMan.PacMan();
	
	@Override
	public void start(Stage stgMenu) throws Exception {
		instance = this;
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		Scene scnMenu = new Scene(menuLoader.load());
		
		stgMenu.setScene(scnMenu);
		stgMenu.setTitle("Menu");
		stgMenu.show();
	}
	
	public void runAsteroids() {
		//Looking to start a game? easy peasy
		//Look at the top, use the package name and MAIN CLASS (the one with the launch) to make an instance of the game
		//Call the start method on the instance with a new stage and boom, your game is running
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
			e.printStackTrace();
		}
	}
	
	public static Menu getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package menu;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MenuController {
	
	// need to put everything in the root
	@FXML
	Pane root;
	
	// initialize variables for each button image
	@FXML
	ImageView imgAsteroids, imgTicTacToe, imgPacMan, imgEgyptianWar;

	@FXML
	protected void initialize() {
		//set images in their place
		//imgAsteroids.setImage(new Image(getClass().getResource("/images/Asteroids.png").toString()));
		//imgTicTacToe.setImage(new Image(getClass().getResource("/images/TicTacToe.png").toString()));
		//imgPacMan.setImage(new Image(getClass().getResource("/images/PacMan.png").toString()));
		imgEgyptianWar.setImage(new Image(getClass().getResource("/images/EgyptianWar.png").toString()));
	}
	
	@FXML
	private void runAsteroids() {
		//Looking to run your game? easy peasy
		//Get the instance of the Menu class and create a run method from there
		//(Look at Menu.runAsteroids for an example)
		Menu.getInstance().runAsteroids();
	}
	
}

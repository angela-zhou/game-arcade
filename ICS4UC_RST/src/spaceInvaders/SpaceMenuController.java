package spaceInvaders;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * SpaceMenuController.java 
 */
import javafx.fxml.FXML;

public class SpaceMenuController {
	
	@FXML
	private void play() {
		SpaceGame.getInstance().playGame();
	}
	
}

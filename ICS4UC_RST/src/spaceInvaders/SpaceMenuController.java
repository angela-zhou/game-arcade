package spaceInvaders;

import javafx.fxml.FXML;

public class SpaceMenuController {
	
	@FXML
	private void play() {
		SpaceGame.getInstance().playGame();
	}
	
}

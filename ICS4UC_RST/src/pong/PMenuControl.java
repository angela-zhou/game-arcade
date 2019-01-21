package pong;

import javafx.fxml.FXML;

public class PMenuControl {

	@FXML
	private void exit() {
		Main.getInstance().myStage.hide();
	}
	
	@FXML
	private void settings() {
		Main.getInstance().myStage.setScene(Main.getInstance().scnMenu);
	}
	
	@FXML
	private void play() {
		Main.getInstance().playGame();
	}
}

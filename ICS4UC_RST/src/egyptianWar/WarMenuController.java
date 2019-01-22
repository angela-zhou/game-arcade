package egyptianWar;

import javafx.fxml.FXML;

public class WarMenuController {

	@FXML
	private void play() {
		CardGame.getInstance().playGame();
	}

	@FXML
	private void help() {
		CardGame.getInstance().getHelp();
	}

	@FXML
	private void exit() {
		CardGame.getInstance().exit();
	}
}

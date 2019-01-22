package egyptianWar;

import javafx.fxml.FXML;

public class WarMenuController {

	@FXML
	private void play() {
		Game.getInstance().playGame();
	}

	@FXML
	private void help() {
		Game.getInstance().getHelp();
	}

	@FXML
	private void exit() {
		Game.getInstance().exit();
	}
}

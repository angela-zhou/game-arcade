package egyptianWar;

import javafx.fxml.FXML;

public class WarHelpController {
	@FXML
	private void playGame() {
		Game.getInstance().playGame();
		Game.getInstance().hideSecond();
	}
	
	@FXML
	private void mainMenu() {
		Game.getInstance().hideSecond();
	}
}

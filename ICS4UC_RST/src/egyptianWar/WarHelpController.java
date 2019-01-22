package egyptianWar;

import javafx.fxml.FXML;

public class WarHelpController {
	@FXML
	private void playGame() {
		CardGame.getInstance().playGame();
		CardGame.getInstance().hideSecond();
	}
	
	@FXML
	private void mainMenu() {
		CardGame.getInstance().hideSecond();
	}
}

package ultimateTicTacToe;

import javafx.fxml.FXML;

public class TTTHelpControl {

	@FXML
	private void playGame() {
		Main.getInstance().playGame();
		Main.getInstance().hideSecond();
	}
	
	@FXML
	private void mainMenu() {
		Main.getInstance().hideSecond();
	}
}

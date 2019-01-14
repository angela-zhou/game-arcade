package ultimateTicTacToe;

import javafx.fxml.FXML;

public class TTTGameOverControl {

	@FXML
	private void mainMenu() {
		Main.getInstance().mainMenu();
		Main.getInstance().hideSecond();
	}
	
	@FXML
	private void replay() {
		Main.getInstance().gameSpace.reset();
		Main.getInstance().hideSecond();
	}
}

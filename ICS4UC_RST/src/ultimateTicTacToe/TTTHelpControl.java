package ultimateTicTacToe;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* TTTHelpControl.java
* A controller for the Ultimate Tic-Tac-Toe Game.
*/

import javafx.fxml.FXML;

public class TTTHelpControl {

	/**
	 * Sets the main stage scene to game, hides the help window.
	 */
	@FXML
	private void playGame() {
		Main.getInstance().playGame();
		Main.getInstance().hideSecond();
	}
	
	/**
	 * Hides the help window.
	 */
	@FXML
	private void mainMenu() {
		Main.getInstance().hideSecond();
	}
}

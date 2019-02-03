package ultimateTicTacToe;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* TTTGameOverControl.java
* A controller for the Ultimate Tic-Tac-Toe Game.
*/

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TTTGameOverControl {

	//Text for winner of game
	@FXML
	Text winner;
	
	/**
	 * Returns user to the main menu stage.
	 */
	@FXML
	private void mainMenu() {
		Main.getInstance().mainMenu();
		Main.getInstance().hideSecond();
	}
	
	/**
	 * Resets game and hides game over window.
	 */
	@FXML
	private void replay() {
		Main.getInstance().gameSpace.reset();
		Main.getInstance().hideSecond();
	}
	
	/**
	 * Sets the text for who won the game.
	 * @param winChar
	 *			The char of the player who won.
	 */
	public void setWinnerText(char winChar) {
		//For if winner was 'X'
		if(winChar == 'X') {
			winner.setText("X's Win!");
			winner.setFill(Color.RED);
			winner.setX(0);
		}
		
		//For if winner was 'O'
		if(winChar == 'O') {
			winner.setText("O's Win!");
			winner.setFill(Color.BLUE);
			winner.setX(0);
		}
		
		//For if game was tied
		if(winChar == 'T') {
			winner.setText("Game Tied!");
			winner.setFill(Color.PURPLE);
			winner.setX(-50);
		}
	}
}

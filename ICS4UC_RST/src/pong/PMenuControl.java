package pong;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* PMenuControl.java
* A controller for the Pong Game.
*/

import javafx.fxml.FXML;

public class PMenuControl {
	
	/**
	 * Exits the pong game.
	 */
	@FXML
	private void exit() {
		Main.getInstance().myStage.hide();
	}
	
	/**
	 * Shows the settings scene.
	 */
	@FXML
	private void settings() {
		Main.getInstance().setControl.settingsInit();
		Main.getInstance().myStage.setScene(Main.getInstance().scnSettings);
	}
	
	/**
	 * Plays the pong game.
	 */
	@FXML
	private void play() {
		Main.getInstance().playGame();
	}
}

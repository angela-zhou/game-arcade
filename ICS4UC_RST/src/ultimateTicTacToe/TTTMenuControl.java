package ultimateTicTacToe;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* TTTMenuControl.java
* A controller for the Ultimate Tic-Tac-Toe Game.
*/

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TTTMenuControl {

	//Menu ImageView objects
	@FXML
	ImageView picTic;
	@FXML
	ImageView picHelp;
	@FXML
	ImageView picExit;
	
	/**
	 * Sets the images for the menu ImageView objects.
	 */
	@FXML
	protected void initialize() {
			picTic.setImage(new Image(TTTMenuControl.class.getResource("images/Board_Decal.png").toString()));
			picHelp.setImage(new Image(TTTMenuControl.class.getResource("images/Help_Decal.png").toString()));
			picExit.setImage(new Image(TTTMenuControl.class.getResource("images/Exit_Decal.png").toString()));
	}
	
	/**
	 * Runs the playGame method in main class.
	 */
	@FXML
	private void play() {
		Main.getInstance().playGame();
	}
	
	/**
	 * Opens the help window.
	 */
	@FXML
	private void help() {
		Main.getInstance().getHelp();
	}
	
	/**
	 * Exits the program.
	 */
	@FXML
	private void exit() {
		Main.getInstance().exit();
	}
}

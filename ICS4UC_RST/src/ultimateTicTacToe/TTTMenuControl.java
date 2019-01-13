package ultimateTicTacToe;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TTTMenuControl {

	@FXML
	ImageView picTic;
	@FXML
	ImageView picHelp;
	@FXML
	ImageView picExit;
	
	@FXML
	protected void initialize() {
			picTic.setImage(new Image(TTTMenuControl.class.getResource("images/Board_Decal.png").toString()));
			picHelp.setImage(new Image(TTTMenuControl.class.getResource("images/Help_Decal.png").toString()));
			picExit.setImage(new Image(TTTMenuControl.class.getResource("images/Exit_Decal.png").toString()));
	}
	
	@FXML
	private void play() {
		Main.getInstance().playGame();
	}
	
	@FXML
	private void exit() {
		Main.getInstance().exit();
	}
}

package egyptianWar;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * WarMenuController.java 
 */
import javafx.fxml.FXML;

public class WarMenuController {

	@FXML
	private void play() {
		EgyptianWarGame.getInstance().playGame();
	}

	@FXML
	private void help() {
		EgyptianWarGame.getInstance().getHelp();
	}

	@FXML
	private void exit() {
		EgyptianWarGame.getInstance().exit();
	}
}

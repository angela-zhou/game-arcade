package egyptianWar;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * WarHelpController.java 
 */
import javafx.fxml.FXML;

public class WarHelpController {
	@FXML
	private void playGame() {
		EgyptianWarGame.getInstance().playGame();
		EgyptianWarGame.getInstance().hideSecond();
	}
	
	@FXML
	private void mainMenu() {
		EgyptianWarGame.getInstance().hideSecond();
	}
}

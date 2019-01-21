package menu;

import javafx.fxml.FXML;

public class MenuController {
	
	@FXML
	private void runAsteroids() {
		Menu.getInstance().runAsteroids();
	}
	
	@FXML
	private void runPac() {
		Menu.getInstance().runPac();
	}
	
	@FXML
	private void runTTT() {
		Menu.getInstance().runTTT();
	}
	
}

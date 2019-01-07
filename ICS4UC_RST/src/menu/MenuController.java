package menu;

import javafx.fxml.FXML;

public class MenuController {

	@FXML
	protected void initialize() {
		
	}
	
	@FXML
	private void runAsteroids() {
		//Looking to run your game? easy peasy
		//Get the instance of the Menu class and create a run method from there
		//(Look at Menu.runAsteroids for an example)
		Menu.getInstance().runAsteroids();
	}
	
}

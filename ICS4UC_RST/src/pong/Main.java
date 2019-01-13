package pong;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	Stage myStage;
	Scene scnMenu, scnGame, scnSettings;
	
	@Override
	public void start(Stage myStage) throws Exception {
		this.myStage = myStage;
		
		
		
		myStage.setScene(scnMenu);
		myStage.setTitle("Pong");
		myStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package ultimateTicTacToe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Game extends Application{

	@Override
	public void start(Stage stgMain) throws Exception {
		
		GridPane root = new GridPane();
		
		OuterBoard gameSpace = new OuterBoard(root);
		
		Scene scnMain = new Scene(root);
		
		stgMain.setScene(scnMain);
		stgMain.setTitle("Ultimate Tic-Tac-Toe");
		stgMain.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

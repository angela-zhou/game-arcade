package egyptianWar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CardGame extends Application {
	
	static final int GAP   = 15;
	static final int HEIGHT = 330;
	static final int WIDTH  = 100;

	Deck playingDeck;
	
	ImageView cardsInPlay;
	
	String winner;

	Text txtComputerScore;
	Text txtPlayerScore;

	public WarHand computer;
	public WarHand player; 
	
	Stage myStage;
	
	static CardGame instance;
	
	Scene scnMenu;
	
	@Override
	public void start(Stage myStage) throws Exception {
		
		instance = this;
		this.myStage = myStage;
		
		scnMenu = new Scene(FXMLLoader.load(getClass().getResource("WarMenu.fxml")));
		
	}
	
	public void mainMenu() {
		myStage.setScene(scnMenu);
	}
	
	static public CardGame getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

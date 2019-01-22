package egyptianWar;

import javafx.application.Application;
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
	
	@Override
	public void start(Stage myStage) throws Exception {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

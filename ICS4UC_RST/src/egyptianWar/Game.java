package egyptianWar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {
	
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
	Stage secondStage;
	
	static Game instance;
	
	Scene scnMenu, scnHelp, scnMain;
	
	@Override
	public void start(Stage myStage) throws Exception {
		
		instance = this;
		this.myStage = myStage;
		secondStage = new Stage();
		
		mainGame();
		
		scnMenu = new Scene(FXMLLoader.load(getClass().getResource("WarMenu.fxml")));
		scnHelp = new Scene(FXMLLoader.load(getClass().getResource("WarHelp.fxml")));
		
		myStage.setScene(scnMenu);
		myStage.setTitle("Menu");
		myStage.show();
		
	}
	
	private void mainGame() {
		
		scnMain = new Scene(root);
	}

	static public Game getInstance() {
		return instance;
	}
	
	public void mainMenu() {
		myStage.setScene(scnMenu);
	}
	
	public void exit() {
		myStage.hide();
	}

	public void playGame() {
		myStage.setScene(scnMain);
	}

	public void getHelp() {
		secondStage.setTitle("Game Rules");
		secondStage.setScene(scnHelp);
		secondStage.show();
	}
	
	public void hideSecond() {
		secondStage.hide();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package ultimateTicTacToe;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* Main.java
* Main class of the Ultimate Tic-Tac-Toe Game.
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{

	//Constants for game layout
	final static int GRID_GAP = 18;
	final static int SQUARE_SPACE = 3;
	final static int PADDING_GAP = 10;
	final static int TITLE_FONT = 40;
	final static int LABEL_FONT = 20;
	final static int[] LABEL_INS = {10, 10, 0, 10}; //TOP, RIGHT, BOTTOM, LEFT
	
	//Game and JavaFX objects
	OuterBoard gameSpace;
	TTTGameOverControl gOverControl;
	Stage myStage, secondStage;
	Label lblNext, lblTurn, lblTurnChar;
	Scene scnMain, scnMenu, scnHelp, scnOver;
	static Main instance;
	FXMLLoader loadOver;
	
	/**
	 * Starts the game (object and stage setup)
	 */
	@Override
	public void start(Stage myStage) throws Exception {
		
		//Initializes global variables
		instance = this;
		this.myStage = myStage;
		secondStage = new Stage();
		
		//Initialize game method
		gameInit();
		
		//Loads menu and help scene
		scnMenu = new Scene(FXMLLoader.load(getClass().getResource("TTTMenu.fxml")));
		scnHelp = new Scene(FXMLLoader.load(getClass().getResource("TTTHelp.fxml")));
		
		//Loads game over scene and controller
		loadOver = new FXMLLoader(getClass().getResource("TTTGameOver.fxml"));
		Parent parOver = loadOver.load();
		scnOver = new Scene(parOver);
		gOverControl = loadOver.getController();
		
		//Initial stage setup
		myStage.setScene(scnMenu);
		myStage.setTitle("Ultimate Tic-Tac-Toe");
		myStage.show();
	}
	
	/**
	 * Initializes JavaFX objects for game scene.
	 */
	private void gameInit() {
		//Container objects
		VBox root = new VBox();
		HBox text = new HBox();
		
		//Current turn text
		lblTurn = new Label("Current Turn: ");
		lblTurn.setFont(new Font(LABEL_FONT));
		lblTurnChar = new Label("X");
		lblTurnChar.setFont(new Font(LABEL_FONT));
		lblTurnChar.setTextFill(Color.RED);
		
		//Title text
		Label lblTitle = new Label("Ultimate Tic-Tac-Toe");
		lblTitle.setMaxWidth(Double.MAX_VALUE);
		lblTitle.setAlignment(Pos.TOP_CENTER);
		lblTitle.setFont(new Font("Times New Roman", TITLE_FONT));
		
		//Next play area text
		lblNext = new Label("Next Square: Any          ");
		lblNext.setFont(new Font(LABEL_FONT));
		lblNext.setMaxWidth(Double.MAX_VALUE);
		lblNext.setAlignment(Pos.CENTER_RIGHT);
		
		//Text formatting and adding to container
		HBox.setHgrow(lblTitle, Priority.ALWAYS);
		HBox.setHgrow(lblNext, Priority.ALWAYS);
		text.setPadding(new Insets(LABEL_INS[0], LABEL_INS[1], LABEL_INS[2], LABEL_INS[3]));
		text.getChildren().addAll(lblTurn, lblTurnChar, lblNext);
		
		//Board container setup and formatting
		GridPane board = new GridPane();
		board.setVgap(GRID_GAP);
		board.setHgap(GRID_GAP);
		board.setPadding(new Insets(PADDING_GAP, PADDING_GAP, PADDING_GAP, PADDING_GAP));
		board.setAlignment(Pos.CENTER);
		
		//Adds all objects to root node
		root.getChildren().addAll(lblTitle, text, board);
		root.setStyle("-fx-background-color: #D3D3D3;");
		
		//Initializes main game object and main scene
		gameSpace = new OuterBoard(board, SQUARE_SPACE, this);
		scnMain = new Scene(root);
	}
	
	/**
	 * Exits the program by closing the main stage.
	 */
	public void exit() {
		myStage.hide();
	}
	
	/**
	 * Resets the current game and sets scene to menu.
	 */
	public void mainMenu() {
		myStage.setScene(scnMenu);
		gameSpace.reset();
	}
	
	/**
	 * Sets second window to help scene and shows it.
	 */
	public void getHelp() {
		secondStage.setTitle("Game Rules");
		secondStage.setScene(scnHelp);
		secondStage.show();
	}
	
	/**
	 * Sets main stage scene to game scene.
	 */
	public void playGame() {
		myStage.setScene(scnMain);
	}
	
	/**
	 * Sets secondary stage to game over scene and shows it.
	 */
	public void gameOver() {
		gOverControl.setWinnerText(gameSpace.getWinner());
		secondStage.setTitle("Game Over");
		secondStage.setScene(scnOver);
		secondStage.show();
	}
	
	/**
	 * Sets the text for next play position.
	 * @param txt
	 *			The String containing next play position text.
	 */
	public void setNext(String txt) {
		lblNext.setText(txt);
	}
	
	/**
	 * Sets the text indicating who's turn it is.
	 * @param txt
	 *			The name of the player who's turn it is.
	 * @param textCol
	 *			The current turn player's colour.
	 */
	public void setTurn(String txt, Color textCol) {
		lblTurnChar.setText(txt);
		lblTurnChar.setTextFill(textCol);
	}
	
	/**
	 * Hides the secondary stage (help and game over)
	 */
	public void hideSecond() {
		secondStage.hide();
	}
	
	/**
	 * Returns an instance of the main class.
	 * @return
	 *			This class.
	 */
	static public Main getInstance() {
		return instance;
	}
	
	/**
	 * Launches the application.
	 * @param args command-line args.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

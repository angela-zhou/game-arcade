package ultimateTicTacToe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{

	final static int GRID_GAP = 18;
	final static int SQUARE_SPACE = 3;
	final static int PADDING_GAP = 10;
	final static int TITLE_FONT = 40;
	final static int LABEL_FONT = 20;
	final static int[] LABEL_INS = {10, 10, 0, 10}; //TOP, RIGHT, BOTTOM, LEFT
	
	OuterBoard gameSpace;
	Stage myStage;
	Label lblNext, lblTurn;
	Scene scnMain, scnMenu, scnHelp, scnOver;
	static Main instance;
	
	@Override
	public void start(Stage myStage) throws Exception {
		
		instance = this;
		this.myStage = myStage;
		
		gameInit();
		
		scnMenu = new Scene(FXMLLoader.load(getClass().getResource("TTTMenu.fxml")));
		scnHelp = new Scene(FXMLLoader.load(getClass().getResource("TTTHelp.fxml")));
		scnOver = new Scene(FXMLLoader.load(getClass().getResource("TTTGameOver.fxml")));
		
		myStage.setScene(scnMenu);
		myStage.setTitle("Ultimate Tic-Tac-Toe");
		myStage.show();
	}
	
	private void gameInit() {
		VBox root = new VBox();
		HBox text = new HBox();
		
		lblTurn = new Label("Current Turn: X");
		lblTurn.setFont(new Font(LABEL_FONT));
		
		Label lblTitle = new Label("Ultimate Tic-Tac-Toe");
		lblTitle.setMaxWidth(Double.MAX_VALUE);
		lblTitle.setAlignment(Pos.TOP_CENTER);
		lblTitle.setFont(new Font("Times New Roman", TITLE_FONT));
		
		lblNext = new Label("Next Square: Any          ");
		lblNext.setFont(new Font(LABEL_FONT));
		lblNext.setMaxWidth(Double.MAX_VALUE);
		lblNext.setAlignment(Pos.CENTER_RIGHT);
		
		HBox.setHgrow(lblTitle, Priority.ALWAYS);
		HBox.setHgrow(lblNext, Priority.ALWAYS);
		
		text.setPadding(new Insets(LABEL_INS[0], LABEL_INS[1], LABEL_INS[2], LABEL_INS[3]));
		text.getChildren().addAll(lblTurn, lblNext);
		
		GridPane board = new GridPane();
		board.setVgap(GRID_GAP);
		board.setHgap(GRID_GAP);
		board.setPadding(new Insets(PADDING_GAP, PADDING_GAP, PADDING_GAP, PADDING_GAP));
		board.setAlignment(Pos.CENTER);
		
		root.getChildren().addAll(lblTitle, text, board);
		
		gameSpace = new OuterBoard(board, SQUARE_SPACE, this);
		
		scnMain = new Scene(root);
	}
	
	public void exit() {
		myStage.hide();
	}
	
	public void getHelp() {
		myStage.setScene(scnHelp);
	}
	
	public void playGame() {
		myStage.setScene(scnMain);
	}
	
	public void gameOver() {
		myStage.setScene(scnOver);
	}
	
	public void setNext(String txt) {
		lblNext.setText(txt);
	}
	
	public void setTurn(String txt) {
		lblTurn.setText(txt);
	}
	
	static public Main getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package ultimateTicTacToe;

import javafx.application.Application;
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

public class Game extends Application{

	final static int GRID_GAP = 18;
	final static int SQUARE_SPACE = 3;
	final static int PADDING_GAP = 10;
	final static int TITLE_FONT = 40;
	final static int LABEL_FONT = 20;
	final static int[] LABEL_INS = {10, 10, 0, 10}; //TOP, RIGHT, BOTTOM, LEFT
	
	Label lblNext, lblTurn;
	
	
	@Override
	public void start(Stage stgMain) throws Exception {
		
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
		
		OuterBoard gameSpace = new OuterBoard(board, SQUARE_SPACE, this);
		
		Scene scnMain = new Scene(root);
		
		stgMain.setScene(scnMain);
		stgMain.setTitle("Ultimate Tic-Tac-Toe");
		stgMain.show();
	}
	
	public void setNext(String txt) {
		lblNext.setText(txt);
	}
	
	public void setTurn(String txt) {
		lblTurn.setText(txt);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

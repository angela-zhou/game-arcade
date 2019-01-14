package ultimateTicTacToe;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TTTGameOverControl {

	static public TTTGameOverControl instance;
	@FXML
	Text winner;
	
	TTTGameOverControl() {

	}
	
	@FXML
	private void mainMenu() {
		Main.getInstance().mainMenu();
		Main.getInstance().hideSecond();
	}
	
	@FXML
	private void replay() {
		Main.getInstance().gameSpace.reset();
		Main.getInstance().hideSecond();
	}
	
	public void setWinnerText(char winChar) {
		if(winChar == 'X') {
			winner.setText("X" + winner.getText().substring(1));
			winner.setFill(Color.RED);
		}
		if(winChar == 'O') {
			winner.setText("O" + winner.getText().substring(1));
			winner.setFill(Color.BLUE);
		}
	}
	
	static public TTTGameOverControl getInstance() {
		return instance;
	}
}

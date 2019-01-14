package ultimateTicTacToe;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TTTGameOverControl {

	@FXML
	Text winner;
	
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
}

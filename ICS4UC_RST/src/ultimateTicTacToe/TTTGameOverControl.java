package ultimateTicTacToe;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
			winner.setText("X's Win!");
			winner.setFill(Color.RED);
			winner.setX(0);
		}
		if(winChar == 'O') {
			winner.setText("O's Win!");
			winner.setFill(Color.BLUE);
			winner.setX(0);
		}
		if(winChar == 'T') {
			winner.setText("Game Tied!");
			winner.setFill(Color.PURPLE);
			winner.setX(-50);
		}
	}
}

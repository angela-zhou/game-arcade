package spaceInvaders;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * GameOverController.java 
 */
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameOverController {

	@FXML
	Text winner;

	@FXML
	ImageView alien;

	@FXML
	private void mainMenu() {
		SpaceGame.getInstance().mainMenu();
		SpaceGame.getInstance().hideSecond();
	}

	@FXML
	private void playAgain() {
		SpaceGame.getInstance().reset();
		SpaceGame.getInstance().hideSecond();
	}

	public void setWinnerText(String win) {
		// if player loses
		if (win == "Invader") {
			winner.setText("You Lose :(");
			winner.setFill(Color.RED);
		}
		// if player wins
		if (win == "Ship") {
			winner.setText("You Win :)");
			winner.setFill(Color.LIME);
		}

	}
	
	public void setWinnerImage(String win) {
		// if player loses
		if (win == "Invader") {
			alien.setImage(new Image(getClass().getResource("images/You Lose.png").toString()));
		}
		// if player wins
		if (win == "Ship") {
			alien.setImage(new Image(getClass().getResource("images/You Win.png").toString()));
		}
	}

}


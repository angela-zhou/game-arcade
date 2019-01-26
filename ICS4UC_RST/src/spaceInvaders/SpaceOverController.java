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

public class SpaceOverController {

	@FXML
	Text winner;

	@FXML
	ImageView alien;

	@FXML
	private void playAgain() {
		//SpaceGame.getInstance().reset();
		SpaceGame.getInstance().hideSecond();
		SpaceGame.getInstance().playGame();
	}

	public void setWinnerText(String win) {
		// if player loses
		if (win == "Invader Wins") {
			winner.setText("You Lose!");
			winner.setFill(Color.RED);
		}
		// if player wins
		if (win == "Ship Wins") {
			winner.setText("You Win!");
			winner.setFill(Color.LIME);
		}

	}
	
	public void setWinnerImage(String win) {
		// if player loses
		if (win == "Invader Wins") {
			alien.setImage(new Image(getClass().getResource("images/You Lose.png").toString()));
		}
		// if player wins
		if (win == "Ship Wins") {
			alien.setImage(new Image(getClass().getResource("images/You Win.png").toString()));
		}
	}

}


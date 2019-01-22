package egyptianWar;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * EgyptianWarGame.java 
 */
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EgyptianWarGame extends Application {
	static final int GAP         = 15;
	static final int HEIGHT      = 330;
	static final int WIDTH       = 100;
	static final int LARGE_FONT  = 28;
	static final int MEDIUM_FONT = 18;
	static final int SMALL_FONT  = 14;

	Deck playingDeck;
	public WarHand player1;
	public WarHand player2;

	// show the cards that have been played
	ImageView imgCardInPlay;

	// array list of all the previously played cards
	ArrayList<Card>  playedCards;

	// labels to display score
	Label lblPlayer1;
	Label lblPlayer2;

	Stage myStage;
	Stage secondStage;

	static EgyptianWarGame instance;

	Scene scnMenu, scnHelp, scnMain;

	@Override
	public void start(Stage myStage) throws Exception {

		instance = this;
		this.myStage = myStage;
		secondStage = new Stage();

		// run main game
		mainGame();

		// load FXML files
		scnMenu = new Scene(FXMLLoader.load(getClass().getResource("WarMenu.fxml")));
		scnHelp = new Scene(FXMLLoader.load(getClass().getResource("WarHelp.fxml")));

		// start by running the menu
		myStage.setScene(scnMenu);
		myStage.setTitle("Egyptian War");
		myStage.show();

	}

	/**
	 * Main Game Method
	 */
	private void mainGame() {

		playingDeck = new Deck();
		playedCards = new ArrayList<Card>();

		// may implement feature to allow user to enter their name
		player1 = new WarHand("Player 1");
		player2 = new WarHand("Player 2");

		// root layout
		VBox root = new VBox(GAP);
		root.setPadding(new Insets(GAP, GAP, GAP, GAP));
		root.setAlignment(Pos.CENTER);

		// title label
		Label lblTitle = new Label("Egyptian War");
		lblTitle.setFont(Font.font(LARGE_FONT));
		root.getChildren().add(lblTitle);

		// cards in play image
		imgCardInPlay = new ImageView(new Card().getCardImage());
		root.getChildren().add(imgCardInPlay);

		// display score/num of cards in hand
		lblPlayer1 = new Label(player1.getName() + " has " + player1.getScore() + " cards.");
		lblPlayer1.setFont(Font.font(SMALL_FONT));
		root.getChildren().add(lblPlayer1);
		// play instructions
		Label lblPlay1 = new Label(player1.getName() + " can press the Q key to play their next card.");
		root.getChildren().add(lblPlay1);
		// slap instructions
		Label lblSlap1 = new Label(player1.getName() + " can press the S key to slap.");
		root.getChildren().add(lblSlap1);

		// display score/num of cards in hand
		lblPlayer2 = new Label(player2.getName() + " has " + player2.getScore() + " cards.");
		lblPlayer2.setFont(Font.font(SMALL_FONT));
		root.getChildren().add(lblPlayer2);
		// play instructions
		Label lblPlay2 = new Label(player2.getName() + " can press the P key to play their next card.");
		root.getChildren().add(lblPlay2);
		// slap instructions
		Label lblSlap2 = new Label(player2.getName() + " can press the K key to slap.");
		root.getChildren().add(lblSlap2);

		// main scene
		scnMain = new Scene(root);
		// link with keypress events
		scnMain.setOnKeyPressed(event -> handleKeyPressed(event));
		// divide the cards by 2 on startup
		startGame(playingDeck);
	}

	public void handleKeyPressed(KeyEvent event) {
		KeyCode code = event.getCode();
		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
			// Q key press: player 1 next card
			if (code == KeyCode.Q && player1.canPlay()) {
				playCard(player1, player2);
				updateScore();
				// P key press: player 2 next card
			} else if (code == KeyCode.P && player2.canPlay()) {
				playCard(player2, player1);
				updateScore();
				// S key press: player 1 slap
			} else if (code == KeyCode.S) {
				slap(player1);
				updateScore();
				// K key press: player 2 slap
			} else if (code == KeyCode.K) {
				slap(player2);
				updateScore();
			}
		}
	}

	/**
	 * the events that occur when a card is played
	 */
	private void playCard(WarHand currentPlayer, WarHand opposingPlayer) {
		// make new card from the play
		Card newCard = currentPlayer.playCard();

		// set image
		imgCardInPlay.setImage(newCard.getCardImage());

		// add to array list of played cards
		playedCards.add(newCard);


		// if the player plays an number card and runs out of chances
		if ((checkFaceCard(newCard) == false) && (currentPlayer.getNumChances() == 0)) {
			currentPlayer.setCanPlay(false);
			opposingPlayer.setCanPlay(true);
			
			// opposing player gets all the cards
			opposingPlayer.addAllCards(playedCards);
			
			// reset num of chances
			currentPlayer.setNumChances(-1);
			
			
		// if the player plays a number card and still has chances
		} else if ((checkFaceCard(newCard) == false) && (currentPlayer.getNumChances() > 0)) {
			currentPlayer.setCanPlay(true);
			opposingPlayer.setCanPlay(false);
			
			// subtract a chance
			currentPlayer.setNumChances((currentPlayer.getNumChances() - 1));
			
			
		// if the player plays a face card and still has chances
		} else if (checkFaceCard(newCard) && (currentPlayer.getNumChances() > 0)) {
			currentPlayer.setCanPlay(false);
			opposingPlayer.setCanPlay(true);
			
			// reset num of chances
			currentPlayer.setNumChances(-1);
			
			
		// if the player plays a face card and has no chances
		} else if (checkFaceCard(newCard) && (currentPlayer.getNumChances() == -1)) {
			currentPlayer.setCanPlay(false);
			opposingPlayer.setCanPlay(true);
			
			// chances are set on the opposing player
			setChances(newCard, opposingPlayer);
			
			
		// if the player plays a number card and has no chances
		} else if ((checkFaceCard(newCard) == false) && (currentPlayer.getNumChances() == -1)) {
			currentPlayer.setCanPlay(false);
			opposingPlayer.setCanPlay(true);
		}
	}

	/**
	 * the events that occur when a user slaps
	 */
	private void slap(WarHand player) {
		// may implement this method
		// slapping for sandwiches and doubles only
		// sorting algorithm
	}

	/**
	 * Checks if a card played is a face card
	 */
	private boolean checkFaceCard(Card card) {
		boolean isFaceCard = false;

		// if card is Jack
		if      (card.getIntValue() == 11) isFaceCard = true;
		// if card is Queen
		else if (card.getIntValue() == 12) isFaceCard = true;
		// if card is King
		else if (card.getIntValue() == 13) isFaceCard = true;
		// if card is Ace
		else if (card.getIntValue() == 14) isFaceCard = true;

		return isFaceCard;
	}

	/**
	 * Sets the users chances depending on the card
	 */
	private void setChances(Card card, WarHand opposingPlayer) {

		// if card is Jack
		if      (card.getIntValue() == 11) opposingPlayer.setNumChances(1);
		// if card is Queen
		else if (card.getIntValue() == 12) opposingPlayer.setNumChances(2);
		// if card is King
		else if (card.getIntValue() == 13) opposingPlayer.setNumChances(3);
		// if card is Ace
		else if (card.getIntValue() == 14) opposingPlayer.setNumChances(4);
	}

	/**
	 * Deals half the cards to computer, half to player
	 */
	private void startGame(Deck deck) {
		for (int i = 0; i < 26; i++) {
			player1.addCard(deck.dealNextCard());
			player2.addCard(deck.dealNextCard());
			updateScore();
		}
	}

	/**
	 * Updates the number of cards each player has
	 */
	private void updateScore() {
		lblPlayer1.setText(player1.getName() + " has " + player1.getScore() + " cards.");
		lblPlayer1.setFont(Font.font(SMALL_FONT));
		lblPlayer2.setText(player2.getName() + " has " + player2.getScore() + " cards.");
		lblPlayer2.setFont(Font.font(SMALL_FONT));

		if (player1.hasWon()) {
			// players cannot play anymore
			player1.setCanPlay(false);
			player2.setCanPlay(false);
			// tell user player 1 has won
			lblPlayer1.setText(player1.getName() + " wins!");
			lblPlayer1.setFont(Font.font(SMALL_FONT));
			lblPlayer2.setText(player2.getName() + " loses.");
			lblPlayer2.setFont(Font.font(SMALL_FONT));
		} else if (player2.hasWon()) {
			// players cannot play anymore
			player1.setCanPlay(false);
			player2.setCanPlay(false);
			// tell user player 2 has won
			lblPlayer1.setText(player1.getName() + " loses.");
			lblPlayer1.setFont(Font.font(SMALL_FONT));
			lblPlayer2.setText(player2.getName() + " wins!");
			lblPlayer2.setFont(Font.font(SMALL_FONT));
		}
	}

	/**
	 * Button and Stage control methods
	 */
	static public EgyptianWarGame getInstance() {
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

	/**
	 * Main Method
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

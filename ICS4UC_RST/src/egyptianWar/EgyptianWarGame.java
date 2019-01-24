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
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EgyptianWarGame extends Application {
	// display constants
	static final int GAP         = 15;
	static final int LARGE_FONT  = 28;
	static final int MEDIUM_FONT = 18;
	static final int SMALL_FONT  = 14;
	static final int FIRST_COL   = 0;
	static final int SECOND_COL  = 6;
	static final int THIRD_COL   = 12;

	// state constants
	static final int NO_CHANCES = 0;
	static final int  A_CHANCES = 1;
	static final int  B_CHANCES = 2;

	// state var
	int currentState  = NO_CHANCES;
	int nextState;

	Deck playingDeck;
	public WarHand playerA;
	public WarHand playerB;

	// show the cards that have been played
	ImageView imgCardInPlay;

	// array list of all the previously played cards
	ArrayList<Card> playedCards;

	// labels to display score
	Label lblScoreA;
	Label lblScoreB;
	
	// labels to display who wins each round 
	Label lblStatus;

	// listViews to display instructions and numChances
	ListView<String> lstPlayerA;
	ListView<String> lstPlayerB;

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

		// instantiate the deck and the arrayList of played cards
		playingDeck = new Deck();
		playedCards = new ArrayList<Card>();

		// may implement feature to allow user to enter their name
		playerA = new WarHand("Player A");
		playerB = new WarHand("Player B");
		
		lstPlayerA = new ListView<String>();
		lstPlayerB = new ListView<String>();

		// root layout
		GridPane root = new GridPane();
		root.setHgap(GAP);
		root.setVgap(GAP);
		root.setPadding(new Insets(GAP, GAP, GAP, GAP));
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: #008000;");

		// player A's "hub"
		// score
		lblScoreA = new Label("Score: " + playerA.getScore());
		lblScoreA.setFont(new Font("Candara", SMALL_FONT));
		lblScoreA.setTextFill(Color.GOLD);
		root.add(lblScoreA, FIRST_COL, 2, 1, 1); 
		// listView
		lstPlayerA.getItems().add(playerA.getName() + " can press the Q key to play.");
		lstPlayerA.getItems().add(playerA.getName() + " can press the S key to slap.");
		root.add(lstPlayerA, FIRST_COL, 3, 1, 5);


		// player B's "hub"
		// score
		lblScoreB = new Label("Score: " + playerB.getScore());
		lblScoreB.setFont(new Font("Candara", SMALL_FONT));
		lblScoreB.setTextFill(Color.GOLD);
		root.add(lblScoreB, THIRD_COL, 2, 1, 1); 
		// listView 
		lstPlayerB.getItems().add(playerB.getName() + " can press the P key to play.");
		lstPlayerB.getItems().add(playerB.getName() + " can press the K key to slap.");
		root.add(lstPlayerB, THIRD_COL, 3, 1, 5);

		
		// center 
		// status
		lblStatus = new Label("Egyptian War");
		lblStatus.setFont(new Font("Candara", LARGE_FONT));
		lblStatus.setTextFill(Color.GOLD);
		lblStatus.setAlignment(Pos.CENTER);
		root.add(lblStatus, SECOND_COL, 0, 1, 1); 

		// cards in play image
		imgCardInPlay = new ImageView(new Card().getCardImage());
		root.add(imgCardInPlay, SECOND_COL, 1, 1, 1); 

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
			// Q key press: player A next card
			if (code == KeyCode.Q && playerA.canPlay()) {
				playCard(playerA, playerB);
				updateScore();
				// P key press: player B next card
			} else if (code == KeyCode.P && playerB.canPlay()) {
				playCard(playerB, playerA);
				updateScore();
				// S key press: player A slap
			} else if (code == KeyCode.S) {
				slap(playerA);
				updateScore();
				// K key press: player B slap
			} else if (code == KeyCode.K) {
				slap(playerB);
				updateScore();
			}
		}
	}

	/**
	 * the events that occur when a card is played
	 */
	private void playCard(WarHand currentPlayer, WarHand opposingPlayer) {
		// make new card from the play
		Card newCard;
		if (currentPlayer == this.playerA) {
			newCard = currentPlayer.playCard(lstPlayerA);
		} else {
			newCard = currentPlayer.playCard(lstPlayerB);
		}

		// set image
		imgCardInPlay.setImage(newCard.getCardImage());

		// add to array list of played cards
		playedCards.add(newCard);

		// updates nextState
		runStateMachine(newCard, currentPlayer, opposingPlayer);

		// the next scenario depends on the next state and the state they came from
		switch (nextState) {
		case NO_CHANCES:
			if (currentState == A_CHANCES) {
				// block player A from playing (they lost the round)
				playerA.setCanPlay(false);
				// allow player B to play (they won the round)
				playerB.setCanPlay(true);
				lblStatus.setText(playerB.getName() + " just won the round.");
				// B gets all the cards
				opposingPlayer.addAllCards(playedCards, imgCardInPlay, lstPlayerB);
				// update the score
				updateScore();
			} else if (currentState == B_CHANCES) {
				// block player B from playing (they lost the round)
				playerB.setCanPlay(false);
				// allow player A to play (they won the round)
				playerA.setCanPlay(true);
				lblStatus.setText(playerA.getName() + " just won the round.");
				// A gets all the cards
				opposingPlayer.addAllCards(playedCards, imgCardInPlay, lstPlayerA);
				// update the score
				updateScore();
			} else {
				// block current player from playing (they just placed a card)
				currentPlayer.setCanPlay(false);
				// allow opposing player to play
				opposingPlayer.setCanPlay(true);
			}
			break;
		case A_CHANCES:
			// block player B from playing
			playerB.setCanPlay(false);
			// ensure player A can play
			playerA.setCanPlay(true);
			if (currentState == NO_CHANCES) { 
				// player A now has chances
				setChances(newCard, opposingPlayer);
				// display in list View
				lstPlayerA.getItems().add(opposingPlayer.getName() + " now has " + opposingPlayer.getNumChances() + " chance(s).");
			} else if (currentState == B_CHANCES) {
				// player A now has chances
				setChances(newCard, opposingPlayer);
				// display in list view
				lstPlayerA.getItems().add(opposingPlayer.getName() + " now has " + opposingPlayer.getNumChances() + " chance(s).");
				// reset num chances to 0
				playerB.setNumChances(0);
				// display in list View
				lstPlayerB.getItems().add(playerB.getName() + " now has 0 chances.");
				//lstPlayerA.getItems().add(opposingPlayer.getName() + " now has " + opposingPlayer.getNumChances() + " chances.");
			} 
			break;
		case B_CHANCES:
			// block player A from playing
			playerA.setCanPlay(false);
			// ensure player B can play
			playerB.setCanPlay(true);
			if (currentState == NO_CHANCES) { 
				// player B now has chances
				setChances(newCard, opposingPlayer);
				// display in list View
				lstPlayerB.getItems().add(opposingPlayer.getName() + " now has " + opposingPlayer.getNumChances() + " chance(s).");
			} else if (currentState == A_CHANCES) {
				// player B now has chances
				setChances(newCard, opposingPlayer);
				// display in list View
				lstPlayerB.getItems().add(opposingPlayer.getName() + " now has " + opposingPlayer.getNumChances() + " chance(s).");
				// reset num chances to 0
				playerA.setNumChances(0);
				// display in list View
				lstPlayerA.getItems().add(playerA.getName() + " now has 0 chances.");
				//System.out.println(opposingPlayer.getName() + " now has " + opposingPlayer.getNumChances() + " chances.");

			} 
			break;
		}

		// update the state once changes have been made
		currentState = nextState;
	}

	/**
	 * The state machine only changes the NEXT STATE based on the current state
	 */
	private void runStateMachine(Card newCard, WarHand currentPlayer, WarHand opposingPlayer) {
		switch (currentState) {
		case NO_CHANCES:
			// if it is not a face card
			if (!checkFaceCard(newCard)) {
				// next state stays the same
				nextState = NO_CHANCES;
			} else {
				// if player A plays a face card
				if (this.playerA == currentPlayer) {
					// the next state is B chances
					nextState = B_CHANCES;
					// if player B plays a face card
				} else if (this.playerB == currentPlayer) {
					// the next state is A chances
					nextState = A_CHANCES;
				}
			}
			break;
		case A_CHANCES:
			if (!checkFaceCard(newCard)) {
				// and numChances is greater than 0
				if (playerA.getNumChances() > 0) {
					// next state stays the same
					nextState = A_CHANCES;
					// and numChances is equal to zero
				} else if (playerA.getNumChances() == 0) {
					// next state is no chances
					nextState = NO_CHANCES;
				}
				// if it is a face card
			} else {
				// next state will be B chances
				nextState = B_CHANCES;	
			}
			break;
		case B_CHANCES:
			// if it is not a face card 
			if (!checkFaceCard(newCard)) {
				// and numChances is greater than 0
				if (playerB.getNumChances() > 0) {
					// next state stays the same
					nextState = B_CHANCES;
					// and numChances is equal to zero
				} else if (playerB.getNumChances() == 0) {
					// next state is no chances
					nextState = NO_CHANCES;
				}
				// if it is a face card
			} else {
				// next state will be A chances
				nextState = A_CHANCES;	
			}
			break;
		default:
			break;
		}  // end of switch
	}  // end of method

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
	private void setChances(Card card, WarHand player) {

		// if card is Jack
		if      (card.getIntValue() == 11) player.setNumChances(1);
		// if card is Queen
		else if (card.getIntValue() == 12) player.setNumChances(2);
		// if card is King
		else if (card.getIntValue() == 13) player.setNumChances(3);
		// if card is Ace
		else if (card.getIntValue() == 14) player.setNumChances(4);
	}

	/**
	 * Deals half the cards to computer, half to player
	 */
	private void startGame(Deck deck) {
		for (int i = 0; i < 26; i++) {
			playerA.addCard(deck.dealNextCard());
			playerB.addCard(deck.dealNextCard());
			updateScore();
		}
	}

	/**
	 * Updates the number of cards each player has
	 */
	private void updateScore() {
		lblScoreA.setText(playerA.getName() + " has " + playerA.getScore() + " cards.");
		lblScoreA.setFont(Font.font(SMALL_FONT));
		lblScoreB.setText(playerB.getName() + " has " + playerB.getScore() + " cards.");
		lblScoreB.setFont(Font.font(SMALL_FONT));
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
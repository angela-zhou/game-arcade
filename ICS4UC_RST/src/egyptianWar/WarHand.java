package egyptianWar;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * WarHand.java 
 */
import java.util.ArrayList;

import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class WarHand {

	// array list of cards
	private ArrayList<Card> hand;

	// data fields
	private int     score;
	private String  name;
	private boolean canPlay;
	private int     numChances;
	private boolean isLoser;

	public WarHand(String name) {
		// create hand
		hand = new ArrayList<Card>();
		// set score to zero
		score = 0;
		// set name
		this.name = name;
		// allow them to play
		canPlay = true;
		// set numChances to zero
		setNumChances(0);
	}

	public void addCard(Card card) {
		// add card to hand
		hand.add(card);
		// add 1 to "score" 
		// this is the number of cards per hand
		score += 1;
	}

	public void addAllCards(ArrayList<Card> playedCards, ImageView cardsInPlay, ListView<String> list) {
		// update score
		score += playedCards.size();
		hand.addAll(playedCards);
		playedCards.clear();
		// display in listview
		list.getItems().add(name + " has all the played cards.");
		list.getItems().add(name + " can play their next card");

	}

	public Card playCard() {
		Card nextCard;

		int cardsLeft = hand.size();

		// select a random card from the remaining cards
		int newCardIndex = (int) (cardsLeft * Math.random());

		// find the card
		nextCard = (hand.get(newCardIndex));
		
		if (numChances > 0) {
			// decrement a chance if the player has them
			numChances--;
		}
		
		// remove it from the deck
		hand.remove(newCardIndex);
		// subtract 1 from score
		// one less card in the hand
		score -= 1;

		return nextCard;
	}
	
	public void clearHand() {
		score -= hand.size();
		hand.clear();
	}

	public int getScore() {
		//score = hand.size();
		return score;
	}

	public String getName() {
		return name;
	}

	public boolean canPlay() {
		return canPlay;
	}

	public void setCanPlay(boolean canPlay) {
		this.canPlay = canPlay;
	}

	public int getNumChances() {
		return numChances;
	}

	public void setNumChances(int numChances) {
		this.numChances = numChances;
	}
}



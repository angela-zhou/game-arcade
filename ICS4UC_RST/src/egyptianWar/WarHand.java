package egyptianWar;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * WarHand.java 
 */
import java.util.ArrayList;

import javafx.scene.image.ImageView;

public class WarHand {
	
	// array list of cards
	private ArrayList<Card> hand;
	
	// data fields
	private int     score;
	private String  name;
	private boolean canPlay;
	private int     numChances;
	
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
	
	public void addAllCards(ArrayList<Card> playedCards, ImageView cardsInPlay) {
		hand.addAll(playedCards);
		playedCards.clear();
		cardsInPlay.setImage(new Card().getCardImage());
		
	}
	
	public Card playCard() {
		Card nextCard;

		int cardsLeft = hand.size();

		// select a random card from the remaining cards
		int newCardIndex = (int) (cardsLeft * Math.random());
		
		// find the card
		nextCard = (hand.get(newCardIndex));
		
		// remove it from the deck
		hand.remove(newCardIndex);
		// subtract 1 from score
		// one less card in the hand
		score -= 1;

		return nextCard;
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



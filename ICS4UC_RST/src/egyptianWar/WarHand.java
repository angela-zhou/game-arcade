package egyptianWar;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * WarHand.java 
 */
import java.util.ArrayList;

public class WarHand {
	
	// array list of cards
	private ArrayList<Card> hand;
	
	// data fields
	private int     score;
	private String  name;
	private boolean canPlay;
	private int     numChances;
	private boolean wins;
	
	public WarHand(String name) {
		// create hand
		hand = new ArrayList<Card>();
		// set score to zero
		score = 0;
		// set name
		this.name = name;
		// allow user to play
		canPlay = true;
		// has no chances yet
		numChances = -1;
		// has not won yet
		wins = false;
	}
	
	public void addCard(Card card) {
		// add card to hand
		hand.add(card);
		// add 1 to "score" 
		// this is the number of cards per hand
		score += 1;
		// set wins to true if hand has all the cards
		if (score == 52) {
			wins = true;
		}
	}
	
	public void addAllCards(ArrayList<Card> playedCards) {
		hand.addAll(playedCards);
		playedCards.clear();
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

	public boolean hasWon() {
		return wins;
	}
}



package egyptianWar;

import java.util.ArrayList;

public class WarHand {
	
	private ArrayList<Card> hand;
	private int score;
	String name;
	
	public WarHand(String name) {
		// create hand
		hand = new ArrayList<Card>();
		// set score to zero
		score = 0;
		// set name
		// most likely "computer" or "player"
		this.name = name;
	}
	
	public void addCard(Card card) {
		// add card to hand
		hand.add(card);
		// add 1 to "score" 
		// this is the number of cards per hand
		score += 1;
		// line for debug purposes - will delete
		// first 26 are computer, next 26 are player
		//System.out.println("adding to" + name + " " + card.toString());
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
}



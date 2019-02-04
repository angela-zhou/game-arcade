package egyptianWar;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * Deck.java 
 */
import java.util.ArrayList;
import java.util.Arrays;

public class Deck {

	// 1st char is the name (T = 10, J = 11, Q = 12, K = 13, A = 14)
	// 2nd char is the suit
	final static private String[] CARDS = { 
			"2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "TC", "JC", "QC", "KC", "AC", 
			"2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "TD", "JD", "QD", "KD", "AD", 
			"2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "TH", "JH", "QH", "KH", "AH", 
			"2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "TS", "JS", "QS", "KS", "AS" };

	private ArrayList<String> deck;
	
	private ArrayList<String> discardDeck;

	public Deck() {
		// create ArrayList from the deck
		deck = new ArrayList<String>(Arrays.asList(CARDS));
	}

	/**
	 * gets the next card from the deck
	 * returns null if the deck is empty
	 **/
	public Card dealNextCard() {
		Card dealtCard;

		int cardsLeft = deck.size();

		// select a random card from the remaining cards
		int newCardIndex = (int) (cardsLeft * Math.random());
		
		// get the name of the card and make a new card from it
		dealtCard = new Card(deck.get(newCardIndex));
		
		// remove it from the deck
		deck.remove(newCardIndex);

		return dealtCard;
	}

	/**
	 * discards a card into a separate array
	 **/
	public void discardCard(String card, ArrayList<String> hand) {
		// remove card from hand
		hand.remove(card);
		
		// add the card to the discard pile
		discardDeck.add(card);
	}
	
	/**
	 * combines all the cards to later be dealt
	 * dealing is random; true "shuffling" is done as the cards are dealt
	 */
	public void shuffle() {
		// add all the cards in the discard pile
		deck.addAll(discardDeck);
	}
	
	/**
	 * clears the deck then adds all the cards back in
	 */
	public void reset() {
		deck.clear();
		deck.addAll(Arrays.asList(CARDS));
	}
}

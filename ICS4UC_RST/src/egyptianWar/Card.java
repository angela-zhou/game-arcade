package egyptianWar;

import javafx.scene.image.Image;

public class Card {

    private final char value;
    private final char suit;

    public Card(String name) {
        value = name.charAt(0);  // first char gives the name
        suit  = name.charAt(1);  // second char gives the suit
    }

    public Card() { // default constructor shows back of card
        this("XX");
    }

    public Image getCardImage() {
        return new Image(getClass().getResource("/CARDS/" + value + suit + ".jpg").toString());
    }

    public String toString() {

        String returnValue = "";

        // get the name of the card for display purposes
        if (Character.isDigit(value)) returnValue = Character.toString(value);  // card value 2 - 9
        else if (value == 'T') returnValue = "10"; // card value 10
        else if (value == 'J') returnValue = "Jack";
        else if (value == 'Q') returnValue = "Queen";
        else if (value == 'K') returnValue = "King";
        else if (value == 'A') returnValue = "Ace";
        
        switch (suit) {
            case 'S': returnValue += " of Spades"; break;
            case 'H': returnValue += " of Hearts"; break;
            case 'D': returnValue += " of Diamonds"; break;
            case 'C': returnValue += " of Clubs"; break;
        }

        return returnValue;
    }
    
    public int getIntValue() {

        int intValue = 0;

        // get the value of the card for comparison
        if (Character.isDigit(value)) { // card value 2 - 9
            intValue = Character.getNumericValue(value);
        } else if (value == 'T') { // card value 10
            intValue = 10;
        } else if (value == 'J') {
            intValue = 11;
        } else if (value == 'Q') {
            intValue = 12;
        } else if (value == 'K') {
            intValue = 13;
        } else if (value == 'A') {
            intValue = 14;
        }
        return intValue;
    }
    
    @Override
    public boolean equals(Object obj) {
    	// check if object is a card
    	if (!(obj instanceof Card)) {
    		return false;
    	}
    	
    	// return the boolean value
    	// of the comparison b/w card strings
    	Card temp = (Card)obj;
    	return temp.toString().equals(this.toString());
    	
    }

}
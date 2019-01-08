package ultimateTicTacToe;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Square extends Button{

	public static final char X = 'X';
	public static final char O = 'O';
	public static final char BLANK = ' ';

	private static final Image imgX = new Image(Square.class.getResource("images/X.jpg").toString());
	private static final Image imgO = new Image(Square.class.getResource("images/O.jpg").toString());
	private static final Image imgBLANK = new Image(Square.class.getResource("images/cardback.jpg").toString());
	
	private char value;
	
	public Square() {
		super();
		value = BLANK;
		setGraphic(new ImageView(imgBLANK));
	}
	
	public void playSquare(char val) {
		value = val;
		switch (value) {
		case X:
			setGraphic(new ImageView(imgX));
			break;
		case O:
			setGraphic(new ImageView(imgO));
			break;
		}
	}
	
	public void reset() {
		value = BLANK;
		setGraphic(new ImageView(imgBLANK));
	}
	
	public char getValue() {
		return value;
	}
}

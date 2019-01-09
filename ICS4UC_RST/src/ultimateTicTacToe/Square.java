package ultimateTicTacToe;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Square extends Button{

	private static final Image imgX = new Image(Square.class.getResource("images/X.jpg").toString());
	private static final Image imgO = new Image(Square.class.getResource("images/O.jpg").toString());
	private static final Image imgBLANK = new Image(Square.class.getResource("images/cardback.jpg").toString());
	
	private char value;
	private int superPos;
	
	public Square(int gridPos) {
		super();
		superPos = gridPos;
		value = ' ';
		setGraphic(new ImageView(imgBLANK));
	}
	
	public void playSquare(char val) {
		
		switch (val) {
		case 'X':
			setGraphic(new ImageView(imgX));
			break;
		case 'O':
			setGraphic(new ImageView(imgO));
			break;
		}
	}
	
	public int getPos() {
		return superPos;
	}
	
	public void reset() {
		value = ' ';
		setGraphic(new ImageView(imgBLANK));
	}
	
	public char getValue() {
		return value;
	}
}

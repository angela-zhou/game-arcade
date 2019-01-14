package ultimateTicTacToe;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Square extends Button{

	private static final Image imgX = new Image(Square.class.getResource("images/X.png").toString());
	private static final Image imgO = new Image(Square.class.getResource("images/O.png").toString());
	private static final Image imgBlank = new Image(Square.class.getResource("images/blank.png").toString());
	private static final Image imgBlue = new Image(Square.class.getResource("images/blue.png").toString());
	private static final Image imgRed = new Image(Square.class.getResource("images/red.png").toString());
	
	private char value;
	private int superPos;
	private ImageView curImage;
	
	public Square(int gridPos) {
		super();
		superPos = gridPos;
		value = ' ';
		curImage = new ImageView();
		curImage.setFitHeight(50);
		curImage.setFitWidth(50);
		curImage.setImage(imgBlank);
		setGraphic(curImage);
		this.setPadding(Insets.EMPTY);
	}
	
	public void playSquare(char val) {
		value = val;
		switch (val) {
		case 'X':
			curImage.setImage(imgX);
			break;
		case 'O':
			curImage.setImage(imgO);
			break;
		}
	}
	
	public int getPos() {
		return superPos;
	}
	
	public void setBlue() {
		curImage.setImage(imgBlue);
	}
	
	public void setRed() {
		curImage.setImage(imgRed);
	}
	
	public void setBlank() {
		curImage.setImage(imgBlank);
	}
	
	public void reset() {
		value = ' ';
		curImage.setImage(imgBlank);
	}
	
	public char getValue() {
		return value;
	}
}

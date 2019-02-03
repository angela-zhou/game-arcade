package ultimateTicTacToe;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* Square.java
* An object for the Ultimate Tic-Tac-Toe Game.
*/

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Square extends Button{

	//Image imports for the different square graphics
	private static final Image imgX = new Image(Square.class.getResource("images/X.png").toString());
	private static final Image imgO = new Image(Square.class.getResource("images/O.png").toString());
	private static final Image imgBlank = new Image(Square.class.getResource("images/blank.png").toString());
	private static final Image imgBlue = new Image(Square.class.getResource("images/blue.png").toString());
	private static final Image imgRed = new Image(Square.class.getResource("images/red.png").toString());
	private static final Image imgPurple = new Image(Square.class.getResource("images/purple.png").toString());
	
	//Square states and vals
	private char value;
	private int superPos;
	private ImageView curImage;
	
	/**
	 * Creates a new square.
	 * @param gridPos
	 *			The squares position in the InnerBoard grid.
	 */
	public Square(int gridPos) {
		//Initializes button and default square values
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
	
	/**
	 * Plays a square for the passed player.
	 * @param val
	 *			The char of the player playing this square.
	 */
	public void playSquare(char val) {
		//Globalizes value of player who played this square
		value = val;
		
		//Sets graphic for player 'X' or 'O'
		switch (val) {
		case 'X':
			curImage.setImage(imgX);
			break;
		case 'O':
			curImage.setImage(imgO);
			break;
		}
	}
	
	/**
	 * Gets this square's position in it's InnerBoard object.
	 * @return
	 *			This square's position in it's InnerBoard object.
	 */
	public int getPos() {
		return superPos;
	}
	
	/**
	 * Sets this square graphic to a blue square.
	 */
	public void setBlue() {
		curImage.setImage(imgBlue);
	}
	
	/**
	 * Sets this square graphic to a red square.
	 */
	public void setRed() {
		curImage.setImage(imgRed);
	}
	
	/**
	 * Sets this square graphic to a blank(white) square.
	 */
	public void setBlank() {
		curImage.setImage(imgBlank);
	}
	
	/**
	 * Sets this square graphic to a purple square.
	 */
	public void setPurple() {
		curImage.setImage(imgPurple);
	}
	
	/**
	 * Resets this squares won value and graphic
	 */
	public void reset() {
		value = ' ';
		curImage.setImage(imgBlank);
	}
	
	/**
	 * Returns the value of the player who won this square.
	 * @return
	 *			The char of the player who played this square.
	 */
	public char getValue() {
		return value;
	}
}

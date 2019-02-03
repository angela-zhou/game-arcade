package pong;

/**
* @author Matthew Stoltz
* Date: Jan. 2018
* Course: ICS4U
* PSettingsControl.java
* A controller for the Pong Game.
*/

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PSettingsControl {

	//FXML objects
	private boolean listening;
	@FXML
	TextField bMin;
	@FXML
	TextField bMax;
	@FXML
	TextField bSize;
	@FXML
	TextField pW;
	@FXML
	TextField pH;
	@FXML
	TextField pSpeed;
	@FXML
	TextField pDecay;
	@FXML
	TextField gScore;
	
	/**
	 * Initializes the settings screen (on FXML being loaded).
	 */
	@FXML
	protected void initialize() {
		//Sets this as the settings controller in main
		Main.getInstance().setSettingsController(this);
		
		//Adds listener to each text field and turns listening on
		bMin.textProperty().addListener(e -> textUpdate(bMin));
		bMax.textProperty().addListener(e -> textUpdate(bMax));
		bSize.textProperty().addListener(e -> textUpdate(bSize));
		pW.textProperty().addListener(e -> textUpdate(pW));
		pH.textProperty().addListener(e -> textUpdate(pH));
		pSpeed.textProperty().addListener(e -> textUpdate(pSpeed));
		pDecay.textProperty().addListener(e -> textUpdate(pDecay));
		gScore.textProperty().addListener(e -> textUpdate(gScore));
		listening = true;
	}
	
	/**
	 * Updates text fields when a text change event occurs.
	 * @param source
	 *			The source of the text change event.
	 */
	@FXML
	private void textUpdate(TextField source) {
		//Doesn't do anything if shouldn't be listening
		if(!listening)
			return;
		
		//Doesn't do anything if text field is empty
		if(source.getLength() == 0)
			return;
		
		//Removes new typed character if it isn't a number
		if(!source.getText().matches("[0-9]+")) {
			source.setText(source.getText().substring(0, source.getText().length() - 1));
			return;
		}
		
		//Makes sure number entry is within range
		checkBounds();
		
		//Creates new double array holding new settings
		Double[] newSets = new Double[] {
				Double.parseDouble(bMin.getText()),
				Double.parseDouble(bMax.getText()),
				Double.parseDouble(bSize.getText()),
				Double.parseDouble(pW.getText()),
				Double.parseDouble(pH.getText()),
				Double.parseDouble(pSpeed.getText()),
				Double.parseDouble(pDecay.getText()),
				Double.parseDouble(gScore.getText()),
		};
		//Updates settings in main with double array
		Main.getInstance().updateSettings(newSets);
	}
	
	/**
	 * Updates text fields to be within the accepted value ranges.
	 */
	private void checkBounds() {
		//Ball min speed
		if(Double.parseDouble(bMin.getText()) < 1)
			bMin.setText("1");
		if(Double.parseDouble(bMin.getText()) > 15)
			bMin.setText("15");
		
		//Ball max speed
		if(Double.parseDouble(bMax.getText()) < Double.parseDouble(bMin.getText()))
			bMax.setText(bMin.getText());
		if(Double.parseDouble(bMax.getText()) > 15)
			bMax.setText("15");
		
		//Ball Size
		if(Double.parseDouble(bSize.getText()) < 1)
			bMax.setText("1");
		if(Double.parseDouble(bMax.getText()) > 50)
			bMax.setText("50");
		
		//Paddle Width
		if(Double.parseDouble(pW.getText()) < 1)
			pW.setText("1");
		if(Double.parseDouble(pW.getText()) > 100)
			pW.setText("100");
		
		//Paddle Height
		if(Double.parseDouble(pH.getText()) < 10)
			pH.setText("10");
		if(Double.parseDouble(pH.getText()) > 250)
			pH.setText("250");
		
		//Paddle Speed
		if(Double.parseDouble(pSpeed.getText()) < 1)
			pSpeed.setText("1");
		if(Double.parseDouble(pSpeed.getText()) > 25)
			pSpeed.setText("25");
		
		//Paddle Decay
		if(Double.parseDouble(pDecay.getText()) < 0)
			pDecay.setText("0");
		if(Double.parseDouble(pDecay.getText()) > 100)
			pDecay.setText("100");
		
		//Game Score
		if(Double.parseDouble(gScore.getText()) < 1)
			gScore.setText("1");
		if(Double.parseDouble(gScore.getText()) > 21)
			gScore.setText("21");		
	}
	
	/**
	 * Runs the pong game.
	 */
	@FXML
	private void play() {
		Main.getInstance().playGame();
	}
	
	/**
	 * Initializes settings screen (on call from main class).
	 */
	public void settingsInit() {
		//Gets current settings from main
		Double[] curSets = Main.getInstance().getSettings();
		
		//Stops listening to update settings text fields
		listening = false;
		bMin.setText("" + curSets[0].intValue());
		bMax.setText("" + curSets[1].intValue());
		bSize.setText("" + curSets[2].intValue());
		pW.setText("" + curSets[3].intValue());
		pH.setText("" + curSets[4].intValue());
		pSpeed.setText("" + curSets[5].intValue());
		pDecay.setText("" + curSets[6].intValue());
		gScore.setText("" + curSets[7].intValue());
		
		//Resumes listening
		listening = true;
	}
}

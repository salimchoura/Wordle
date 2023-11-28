package view_controller;

import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.interfaces.MyObservable;
import model.interfaces.MyObserver;
import model.Attempt;
import model.Wordle;

/**
 * This class represents the view for the keyboard in the Wordle game.
 * It extends the VBox class and implements the MyObserver interface.
 */
public class KeyboardView extends VBox implements MyObserver{
	// This class is implemented in the following way:
	// It extends the VBox which consists of three HBoxes
	// which represent one line of keyboard. So, eventually, 
	// we have 3 lines of buttons. 
	
	private HashMap<Character, Button> keys; 

	/**
	 * Constructs a KeyboardView object for the Wordle game.
	 * @param game The Wordle game instance.
	 */
	public KeyboardView(Wordle game) {
		this.setSpacing(8);

		String allbuts = "QWERTYUIOP⌫ASDFGHJKL↵ZXCVBNM";
		char [] arrayOfButs = allbuts.toCharArray();
		keys = new HashMap<>();
		HBox horizontalBox = new HBox();
		for (char letter: arrayOfButs) {
			if (letter == 'Q' || letter == 'A' || letter == 'Z') {
				horizontalBox = new HBox(10);
				horizontalBox.setAlignment(Pos.CENTER);
				this.getChildren().add(horizontalBox);
			}
			if (letter == '⌫') {
			Button temp = new Button(String.valueOf(letter));
			temp.setPrefSize(80, 40);
			horizontalBox.getChildren().add(temp);
			keys.put(letter, temp);
			}			
			else if (letter == '↵') {
			Button temp = new Button(String.valueOf(letter));
			temp.setPrefSize(60, 40);
			horizontalBox.getChildren().add(temp);
			keys.put(letter, temp);
			}
			else {
			Button temp = new Button(String.valueOf(letter));
			temp.setPrefSize(40, 40);
			horizontalBox.getChildren().add(temp);
			keys.put(letter, temp);
			}
			
		}
		takeAction(game);
		loadGame(game);
	}
	
	/**
	 * Loads the state of the game on the keyboard view.
	 * @param game The Wordle game instance.
	 */
	private void loadGame(Wordle game) 
	{
		Attempt[] attempts = game.getAttempts();
		for (int i=0;i<6-game.numberOfAttempts();i++)
		{
			Integer[] positions = attempts[i].getPositions();
			for (int j=0;j<attempts[i].getAttempt().length();j++)
			{
				for (Button button : keys.values())
				{
					if (button.getText().toLowerCase().equals(String.valueOf(attempts[i].getAttempt().charAt(j))))
					{
						if (positions[j]==0)
							button.setStyle("-fx-background-color: rgb(108,169,101); -fx-text-fill: white;");
						if (positions[j]==1)
							button.setStyle("-fx-background-color: rgb(120,124,127); -fx-text-fill: white;");
						if (positions[j]==2)
							button.setStyle("-fx-background-color: rgb(200,182,83); -fx-text-fill: white;");
					}
				}
			}
		}
	}

	/**
	 * Defines the actions to be taken when buttons on the keyboard are pressed.
	 * @param game The Wordle game instance.
	 */
	public void takeAction(Wordle game) {
		for (Button button : keys.values()) {
			button.setOnAction(event -> {
				String str = button.getText();
				if (str.charAt(0) == '⌫') {
					game.addAttempt("backspace");
				}
				else if (str.charAt(0) == '↵') {
					game.addAttempt("enter");
				}
				else {
					game.addAttempt(str);
				}
			});
		}
	}


    /**
     * This method sets styles for buttons.
     * @param command
     */
    public void setStyleForButtons(String command) {
    	// Set CSS layout for Buttons. 
    	for (Button button : keys.values()) {
    		button.setStyle(command);
    	}
    }
    
	@Override
	/**
	 * This method updates the keyboard view.
	 */
	public void update(MyObservable observable) {
		Wordle game = (Wordle) observable;
		String currentAttempt = game.getAttempt().toLowerCase();
		if (game.isLockedIn() && game.exists())
		{
			Integer[] positions = game.getPositions();
			for (int i=0;i<currentAttempt.length();i++)
			{
				for (Button button : keys.values())
				{
					if (button.getText().toLowerCase().equals(String.valueOf(currentAttempt.charAt(i))))
					{
						if (positions[i]==0)
							button.setStyle("-fx-background-color: rgb(108,169,101); -fx-text-fill: white;");
						if (positions[i]==1)
							button.setStyle("-fx-background-color: rgb(120,124,127); -fx-text-fill: white;");
						if (positions[i]==2)
							button.setStyle("-fx-background-color: rgb(200,182,83); -fx-text-fill: white;");
					}
				}
			}
		}				
	}
	
	/**
	 * This methods sets the game mode to dark.
	 */
	public void setDark()
	{
		this.setStyle("-fx-background-color: rgb(55,55,55);");
		for (Button button : keys.values())
		{
			if (button.getStyle().equals(""))
				button.setStyle("-fx-background-color: black; -fx-text-fill:white;");
		}
	}
	
	/**
	 * This method sets the game to light.
	 */
	public void setLight()
	{
		this.setStyle(null);
		for (Button button : keys.values())
		{
			String color = button.getStyle().split(";")[0].split(":")[1].strip();
			if (color.equals("black"))
				button.setStyle(null);
		}
	}
	
}

/**
 * Umidjon Muzrapov
 * Salim Choura
 * Jose Bernardo Montano
 * Ivan Denisovich Akinfiev
 */

package view_controller;

/**
 * Imports needed
 */

import java.util.ArrayList;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Attempt;
import model.Wordle;
import model.interfaces.MyObservable;
import model.interfaces.MyObserver;
import view_controller.KeyboardView;

/**
 * AttemptView extends BordePane and implements MyObserver, it has instance
 * variables that keep track of username, gridPane, funcPane, welcomeLabel,
 * myButtons, currentButtonIndex, and isOver. It displays the user's attempts in
 * the Wordle Game
 */
public class AttemptView extends BorderPane implements MyObserver {

	/**
	 * Instance variables for the AttemptView class
	 */
	private String username;
	private GridPane gridPane;
	private StackPane funcPane = new StackPane();
	private Label welcomeLabel;
	private ArrayList<Button> myButtons;
	private int currentButtonIndex;
	private boolean isOver = false;

	/**
	 * The constructor for AttemptView receives a Wordle game, and initializes some
	 * of the instance variables such as: myButtons, gridPane. It creates 30 buttons
	 * with their respective functionality, finally the constructor calls loadGame
	 * with the Wordle object
	 * 
	 * @param game
	 */
	public AttemptView(Wordle game) {
		myButtons = new ArrayList<>();
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);

		for (int i = 0; i < 30; i++) {
			Button button = new Button("");
			button.setDisable(true);
			button.setPrefSize(50, 50);
			button.setStyle("-fx-backface-visibility: visible; -fx-opacity: 1;");
			myButtons.add(button);
			gridPane.add(myButtons.get(i), i % 5, i / 5);
		}
		this.setTop(funcPane);
		this.setCenter(gridPane);
		loadGame(game);

	}

	/**
	 * shakeCurrentButton method shakes a specific button based on index, it makes
	 * sure that the game is notOver and that the button is not out of range
	 * 
	 * @param index an int value that represents the index of the button to shake
	 */
	private void shakeCurrentButton(int index) {
		if (isOver && currentButtonIndex < 30) {
			return;
		}

		Button button = myButtons.get(index);
		TranslateTransition shakeTransitionOne = new TranslateTransition(Duration.seconds(0.25), button);
		shakeTransitionOne.setByX(10);
		shakeTransitionOne.play();
		shakeTransitionOne.setOnFinished((event) -> {
			TranslateTransition shakeTransitionTwo = new TranslateTransition(Duration.seconds(0.25), button);
			shakeTransitionTwo.setByX(-20);
			shakeTransitionTwo.play();
			shakeTransitionTwo.setOnFinished((eventTwo) -> {
				TranslateTransition shakeTransitionThree = new TranslateTransition(Duration.seconds(0.25), button);
				shakeTransitionThree.setByX(10);
				shakeTransitionThree.play();
			});
		});
	}

	/**
	 * loadGame method load the Wordle game into the GridPane, and retrieves the
	 * attempt by setting the text and style for each corresponding letter and
	 * button
	 * 
	 * @param game The wordle object representing the current game
	 */
	private void loadGame(Wordle game) {
		Attempt[] attempts = game.getAttempts();
		for (int i = 0; i < attempts.length; i++) {
			Integer[] positions = attempts[i].getPositions();
			for (int j = 0; j < attempts[i].size(); j++) {
				myButtons.get(currentButtonIndex).setStyle("-fx-text-fill:white;");
				myButtons.get(currentButtonIndex).setText(attempts[i].getAttempt().substring(j, j + 1).toUpperCase());
				if (positions[j] == 0)
					myButtons.get(currentButtonIndex)
							.setStyle("-fx-text-fill:white;-fx-opacity: 1; -fx-background-color:rgb(108,169,101)");
				if (positions[j] == 1)
					myButtons.get(currentButtonIndex)
							.setStyle("-fx-text-fill:white;-fx-opacity: 1; -fx-background-color:rgb(120,124,127)");
				if (positions[j] == 2)
					myButtons.get(currentButtonIndex)
							.setStyle("-fx-text-fill:white;-fx-opacity: 1; -fx-background-color:rgb(200,182,83)");
				currentButtonIndex++;
			}
		}
	}

	/**
	 * The method flipCurrentButton flips the a button at the current index, which
	 * will display the button with the letter and background color
	 * 
	 * @param index  the index of the button to flip
	 * @param letter the letter to display on the new button
	 * @param color  the background color of the new button
	 */
	private void flipCurrentButton(int index, String letter, String color) {
		if (isOver && currentButtonIndex < 30) {
			return;
		}

		Button button = myButtons.get(index);
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), button);
		rotateTransition.setAxis(Rotate.X_AXIS);
		rotateTransition.setFromAngle(0);
		rotateTransition.setToAngle(180);
		rotateTransition.setOnFinished(event -> {
			Button newButton = new Button(letter);
			newButton.setDisable(true);
			newButton.setStyle("-fx-background-color:" + color + "; -fx-text-fill: white; -fx-opacity: 1;");
			newButton.setPrefSize(50, 50);
			myButtons.set(index, newButton);
			gridPane.add(newButton, index % 5, index / 5);
			rotateTransition.stop();

			if (index >= myButtons.size()) {
				isOver = true;
			}
		});
		rotateTransition.play();
	}

	/**
	 * update method is call whenever the observable object is changed, and it
	 * updates the view based on the state of the Wordle game
	 * 
	 * @param observable the Wordle game being observed
	 */
	@Override
	public void update(MyObservable observable) {

		Wordle game = (Wordle) observable;
		String lastLetter = game.mostRecent();
		currentButtonIndex = game.curButtonIndex();
		if ((currentButtonIndex) < 30)
			myButtons.get(currentButtonIndex).setText(lastLetter);
		if (game.hasWon()) {
			for (int i = currentButtonIndex - 5; i < currentButtonIndex; i++) {
				flipCurrentButton(i, myButtons.get(i).getText(), "rgb(108,169,101)");
				String counter = "";
				for (int j = 0; j < 10000; j++) {
					counter += String.valueOf(j);
				}
			}
		}
		// show an alert congratulating the player
		else if (game.hasLost())
			// show an alert telling the player they lost along with
			// a the correct word they should have guessed
			;
		else if (game.isLockedIn()) {
			if (game.exists()) {
				Integer[] positions = game.getPositions();
				for (int i = currentButtonIndex - 5; i < currentButtonIndex; i++) {
					if (positions[i % 5] == 0)
						flipCurrentButton(i, myButtons.get(i).getText(), "rgb(108,169,101)");
					if (positions[i % 5] == 1)
						flipCurrentButton(i, myButtons.get(i).getText(), "rgb(120,124,127)");
					if (positions[i % 5] == 2)
						flipCurrentButton(i, myButtons.get(i).getText(), "rgb(200,182,83)");

					String counter = "";
					for (int j = 0; j < 10000; j++) {
						counter += String.valueOf(j);
					}
				}
			} else {
				for (int i = currentButtonIndex - 5; i < currentButtonIndex; i++) {
					shakeCurrentButton(i);
				}
			}
		}
	}

	/**
	 * setDark method will change the CSS properties of the components to make it
	 * dark mode
	 */
	public void setDark() {
		this.setStyle("-fx-background-color: rgb(55,55,55);");
		for (int i = 0; i < 30; i++) {
			if (i >= (currentButtonIndex / 5) * 5) {
				myButtons.get(i).setStyle("-fx-background-color: black; -fx-opacity: 1; -fx-text-fill: white;");
			}
		}
	}

	/**
	 * setLight method will change the CSS properties of the components to make it
	 * light mode
	 */
	public void setLight() {
		this.setStyle(null);
		for (int i = 0; i < 30; i++) {
			if (i >= (currentButtonIndex / 5) * 5) {
				myButtons.get(i).setStyle(null);
				myButtons.get(i).setStyle("-fx-opacity: 1;");
			}
		}
	}
}
package model;

import java.io.Serializable;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.interfaces.MyObservable;


/**
 * 
 * @author UBIS
 * This class is a model of the wordle game.
 */
public class Wordle extends MyObservable implements Serializable
{
	private Library library;
	private Integer attemptsLeft;
	private Attempt[] attempts; //array of attempts
	private Integer currentAttemptIndex;
	private String correctWord;
	// array of integers that we're going to use to compare the
	// position of the letter in the attempt to its position
	// in the correct word.
	private Integer[] positions;
	private boolean lockedIn;
	private boolean hasWon;
	private boolean hasLost;
	private String mostRecent;
	private Integer currentButtonIndex;
	
	/**
	 * The constructor creates a library of words of length 5,
	 * generates a word for the user to guess, and initializes
	 * the number of attempts left. It also initializes the 
	 * attempts to empty strings (the reason we do this is 
	 * because we're going to concatenation on the attempts
	 * so they need to be already initialized). The constructor
	 * also initializes the index of the current attempt.
	 */
	public Wordle()
	{
		hasWon = false;
		hasLost = false;
		library = new Library();
		correctWord = library.generateWord();
		attemptsLeft = 6;
		attempts = new Attempt[attemptsLeft];
		for (int i=0; i<attempts.length;i++)
		{
			attempts[i] = new Attempt("",correctWord);
		}
		currentButtonIndex = 0;
		currentAttemptIndex = 0;
	}
	
	/**
	 * This constructor does the same thing as the previous constructor
	 * except for generating a random word. This constructor allows 
	 * us to choose the correct guess and will be used for testing.
	 */
	public Wordle(String word)
	{
		hasWon = false;
		hasLost = false;
		library = new Library();
		correctWord = word; 
		attemptsLeft = 6;
		attempts = new Attempt[attemptsLeft];
		for (int i=0; i<attempts.length;i++)
		{
			attempts[i] = new Attempt("",correctWord);
		}
		currentAttemptIndex = 0;
		currentButtonIndex = 0;
	}
	
	
	
	/**
	 * 
	 * @param keyContent: the content of the key pressed/clicked
	 * This method grows our current attempt.
	 */
	public void addAttempt(String keyContent)
	{

		mostRecent = "";
		// if the button clicked is the back space button, we delete
		// the last letter of our current attempt and notify the observers.
		if (keyContent.equals("backspace") && attempts[currentAttemptIndex].size()!=0)
		{
			lockedIn = false;
			mostRecent = "";
			currentButtonIndex -=1;
			attempts[currentAttemptIndex].deleteLast();
			notifyObservers(this);
		}
		// if the button clicked is the enter button and the attempt has the necessary size,
		// and exists in our library, we lock in the attempt, call the getPositions method
		// to get the array that gives us feedback about the attempt that was entered. We also
		// notify the observers, move on to the next attempt and check for a win/loss. 
		// NOTE: We need to notify the observers before we change the current index, because the
		// views will need the current index to know which line to animate when attempt is locked in.
		// This explains the placement of notify() in this block of code.
		else if (keyContent.equals("enter") && attempts[currentAttemptIndex].size() == 5)
		{ 
			attempts[currentAttemptIndex].setAttempt(attempts[currentAttemptIndex].getAttempt().toLowerCase());
			this.correctWord=correctWord.toLowerCase();
			lockedIn = true;
			if (exists())
			{
				positions = attempts[currentAttemptIndex].getPositions();
				attemptsLeft-=1;
				if (attempts[currentAttemptIndex].getAttempt().equals(correctWord))
				{
					hasWon = true;
				}
				else if (attemptsLeft == 0)
					hasLost = true;
				notifyObservers(this);
				currentAttemptIndex+=1;
				lockedIn = false; 
			}
			else
				notifyObservers(this);
			// after notifying the observer, we move on to the next attempt
			// and go back to a state where lockedIn is false;
		}
		else if (!keyContent.equals("enter") && !keyContent.equals("backspace") 
				&& attempts[currentAttemptIndex].size()!=5)
		{
				mostRecent = keyContent.toUpperCase();
				attempts[currentAttemptIndex].concatenate(keyContent);
				notifyObservers(this);
				currentButtonIndex +=1;
		}
	}
	

	/**
	 * @return the current attempt.
	 * This method is necessary for when we update the keyboard view.
	 */
	public String getAttempt()
	{
		return attempts[currentAttemptIndex].getAttempt();
	}
	
	
	/**
	 * @return feedback received on the attempt.
	 * (feedback is explained better in Attempt class)
	 * This method will be called in the views because it tells us
	 * which tiles to color in green, which tiles to color in yellow,
	 * and which tiles to color in black.
	 */
	public Integer[] getPositions()
	{
		return positions;
	}
	
	/**
	 * @return a boolean indicating if the current attempt is locked in.
	 * In other words, if enter was clicked/pressed.
	 */
	public boolean isLockedIn()
	{
		return lockedIn;
	}
	

	/**
	 * @return a boolean indicating if the player has won.
	 */
	public boolean hasWon()
	{
		return hasWon;
	}
	

	/** 
	 * @return a boolean indicating if the player has lost
	 */
	public boolean hasLost()
	{
		return hasLost;
	}
	
	/**
	 * @return the index where the next letter will be entered.
	 */
	public Integer curButtonIndex()
	{
		return currentButtonIndex;
	}
	
	/**
	 * @return the most recent letter entered.
	 * This method is necessary when updating AttemptView.
	 */
	public String mostRecent()
	{
		return mostRecent;
	}
	
	/**
	 * @return a boolean indicating whether the word exists in our data base.
	 */
	public boolean exists()
	{
		return library.contains(attempts[currentAttemptIndex].getAttempt().toLowerCase());
	}
	
	/**
	 * @return the array containing the attempts.
	 * This method is necessary for persistence (when we load an unfinished game).
	 */
	public Attempt[] getAttempts()
	{
		return attempts;
	}
	
	/**
	 * @return the number of attempts left
	 */
	public Integer numberOfAttempts()
	{
		return attemptsLeft;
	}
	
	/**
	 * @return the correct word.
	 */
	public String getCorrect()
	{
		return correctWord;
	}
}

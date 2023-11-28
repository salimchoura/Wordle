package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author UBIS
 * This class is an attempt class that will constitute an 
 * attempt in the Wordle game.
 */
public class Attempt implements Serializable
{
	private String attempt;
	private Integer size;
	private Integer[] positions;
	private String correctWord;
	

	/**
	 * 
	 * @param letter: this is the first letter of the attempt which
	 * is what the attempt starts as.
	 * @param correctWord: the correct word the player has to guess.
	 * The constructor initializes the instance variables.
	 */
	public Attempt(String letter, String correctWord)
	{
		this.correctWord = correctWord;
		positions = new Integer[5]; 
		this.attempt = letter;
		size = attempt.length();
	}
	

	/**
	 * @param letter: the letter that we concatenate to our attempt
	 * The way we have it, attempt grows with every button press/click
	 * and it grows one letter at a time until it reaches the desired 
	 * length (5 letters)
	 */
	public void concatenate(String letter)
	{
		attempt = attempt + letter;
		size+=1;
	}
	
	/**
	 * This method deleted the last letter of the attempt.
	 * This is for when the user clicks back space.
	 */
	public void deleteLast()
	{
		attempt = attempt.substring(0,attempt.length()-1);
		size-=1;
	}
	
	/**
	 * 
	 * @return the size of the attempt
	 */
	public Integer size()
	{
		return size;
	}
	

	/**
	 * 
	 * @return an array where each slot gives us information about the letter
	 * at that index of the attempt. A 0 means that the letter is correct and
	 * at the right spot, a 1 means that the letter is completely wrong, 
	 * a 2 means that the letter exists in the correct word but it was 
	 * placed in the wrong position.
	 */
	public Integer[] getPositions()
	{
		HashMap<String, Integer> occurrences = new HashMap<>();
		for (int i=0;i<correctWord.length();i++)
		{
			if (occurrences.containsKey(correctWord.substring(i,i+1)))
			{
				Integer occurrence = occurrences.get(correctWord.substring(i,i+1));
				occurrence+=1;
				occurrences.put(correctWord.substring(i,i+1), occurrence);
			}
			else
				occurrences.put(correctWord.substring(i,i+1), 1);
		}
		ArrayList<Integer> rightSpots = new ArrayList<>();
		for (int i=0;i<size;i++)
		{
			// 0 for correctly placed letters
			if (attempt.substring(i,i+1).equals(correctWord.substring(i,i+1)))
			{
				positions[i] = 0;
				Integer occurrence = occurrences.get(attempt.substring(i,i+1));
				occurrence-=1;
				occurrences.put(attempt.substring(i,i+1), occurrence);
				rightSpots.add(i);
			}
		}
		for (int i=0;i<size;i++)
		{
			if (!rightSpots.contains(i))
			{
				// 1 for completely wrong letters
				if (!correctWord.contains(attempt.substring(i,i+1)) || occurrences.get(attempt.substring(i,i+1))==0)
				{
					positions[i] = 1;
				}
				// 2 for partially right letters.
				else if (occurrences.get(attempt.substring(i,i+1))>0)
				{
					positions[i] = 2;
					Integer occurrence = occurrences.get(attempt.substring(i,i+1));
					occurrence-=1;
					occurrences.put(attempt.substring(i,i+1), occurrence);
				}
			}
		}
		return positions;
	}
	

	/**
	 * 
	 * @return the attempt string
	 */
	public String getAttempt()
	{
		return attempt;
	}
	
	/**
	 * 
	 * @param attempt: the current attempt
	 * sets the current attempt to the passed parameter.
	 */
	public void setAttempt(String attempt)
	{
		this.attempt=attempt;
	}
}

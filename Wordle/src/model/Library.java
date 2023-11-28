package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


/**
 * @author UBIS
 * This class is a library of words of length 5 that can
 * also generate a random word of length 5.
 */
public class Library implements Serializable
{
	private String fileName = "words.txt"; 
	// library will have a mapping of the word with itself.
	private HashMap<String,String> library = new HashMap<>();
	// libraryTwo will have a mapping of the index of the 
	// word (the order it was ordered in) and the word.
	// libraryTwo is needed because we can't generate a
	// random word with the variable library.
	private HashMap<Integer,String> libraryTwo = new HashMap<>();
	private Random randomizer = new Random();
	
	
	/**
	 * The constructor makes the library by opening a file
	 * that contains all the words of length 5 and storing them
	 * in a map. The reason we are storing in a map is because
	 * it makes looking for a word more time efficient 
	 * (especially since there's a lot of words)
	 */
	public Library()
	{
		try 
		{
			Scanner scanner = new Scanner(new File(fileName));
			while (scanner.hasNext())
			{
				String line = scanner.nextLine().strip();
				String[] words = line.split(" ");
				for (int i=0; i<words.length; i++)
				{
					String word = words[i];
					library.put(word,word);
					libraryTwo.put(i, word);
				}
 			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @return a random word from the library.
	 */
	public String generateWord()
	{
		Integer randomNumber = randomizer.nextInt(libraryTwo.size());
		return libraryTwo.get(randomNumber);
	}
	

	/** 
	 * @param word: an attempt entered by the player
	 * @return a boolean indicating if the attempt exists in
	 * the library
	 */
	public boolean contains(String word)
	{
		return (library.containsKey(word));
	}
	
}

package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import model.Library;

/**
 * @author UBIS
 * This class is a test class for the Library class.
 */
class LibraryTest 
{

	@Test
	void test() 
	{ 
		Library library = new Library();
		assertTrue(library.contains("hello"));
		assertFalse(library.contains("abcde"));
		System.out.println(library.generateWord());
	}

}

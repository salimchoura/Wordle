package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.Wordle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author UBIS
 * This class is a test class for the Wordle class.
 */
class WordleTest 
{

	@Test
	void test() 
	{
		Wordle game = new Wordle("glide");
		game.addAttempt("g");
		assertEquals("g",game.getAttempt());
		game.addAttempt("h");
		assertEquals("gh",game.getAttempt());
		game.addAttempt("o");
		assertEquals("gho",game.getAttempt());
		game.addAttempt("s");
		assertEquals("ghos",game.getAttempt());
		game.addAttempt("t");
		assertEquals("ghost",game.getAttempt()); 
		game.addAttempt("backspace");
		assertEquals("ghos",game.getAttempt());
		game.addAttempt("t");
		assertEquals("ghost",game.getAttempt());
		game.addAttempt("enter");
		assertFalse(game.hasWon());
		assertFalse(game.hasLost());
		Integer[] positions = game.getPositions();
		assertEquals(positions[0],0);
		assertEquals(positions[1],1);
		assertEquals(positions[2],1);
		assertEquals(positions[3],1);
		assertEquals(positions[4],1);
		
		game.addAttempt("g");
		assertEquals("g",game.getAttempt());
		game.addAttempt("l");
		assertEquals("gl",game.getAttempt());
		game.addAttempt("e");
		assertEquals("gle",game.getAttempt());
		game.addAttempt("a");
		assertEquals("glea",game.getAttempt());
		game.addAttempt("m");
		assertEquals("gleam",game.getAttempt());
		game.addAttempt("enter");
		assertFalse(game.hasWon());
		assertFalse(game.hasLost());
		positions = game.getPositions();
		assertEquals(positions[0],0);
		assertEquals(positions[1],0);
		assertEquals(positions[2],2);
		assertEquals(positions[3],1);
		assertEquals(positions[4],1);
		
		assertEquals("",game.getAttempt());
		game.addAttempt("g");
		assertEquals("g",game.getAttempt());
		game.addAttempt("l");
		assertEquals("gl",game.getAttempt());
		game.addAttempt("i");
		assertEquals("gli",game.getAttempt());
		game.addAttempt("d");
		assertEquals("glid",game.getAttempt());
		game.addAttempt("e");
		assertEquals("glide",game.getAttempt());
		game.addAttempt("enter");
		assertTrue(game.hasWon());
		assertFalse(game.hasLost());
		positions = game.getPositions();
		assertEquals(positions[0],0);
		assertEquals(positions[1],0);
		assertEquals(positions[2],0);
		assertEquals(positions[3],0);
		assertEquals(positions[4],0);
		
		game = new Wordle("chill");
		game.addAttempt("c");
		game.addAttempt("h");
		game.addAttempt("e");
		game.addAttempt("c");
		game.addAttempt("k");
		game.addAttempt("enter");
		
		game.addAttempt("c");
		game.addAttempt("h");
		game.addAttempt("e");
		game.addAttempt("c");
		game.addAttempt("k");
		game.addAttempt("enter");
		
		game.addAttempt("c");
		game.addAttempt("h");
		game.addAttempt("e");
		game.addAttempt("c");
		game.addAttempt("k");
		game.addAttempt("enter");
		
		game.addAttempt("c");
		game.addAttempt("h");
		game.addAttempt("e");
		game.addAttempt("c");
		game.addAttempt("k");
		game.addAttempt("enter");
		
		game.addAttempt("c");
		game.addAttempt("h");
		game.addAttempt("e");
		game.addAttempt("c");
		game.addAttempt("k");
		game.addAttempt("enter");
		game.addAttempt("c");
		assertEquals("C",game.mostRecent());
		assertEquals(26,game.curButtonIndex());
		game.addAttempt("h");
		game.addAttempt("e");
		game.addAttempt("c");
		game.addAttempt("k");
		game.addAttempt("enter");
		
		assertTrue(game.hasLost());
		
		game = new Wordle();
	}

}

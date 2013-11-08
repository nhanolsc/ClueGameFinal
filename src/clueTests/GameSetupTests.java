package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;
import clueGame.Card.CardType;

public class GameSetupTests {
	private static Board testBoard;
	private static ClueGame clueGame;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 24;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_CARDS = 21;
	public static final int WEAPONS = 6;
	public static final int PLAYERS = 6;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testBoard = new Board("ClueLayout.csv", "legend.txt");
		clueGame = new ClueGame("PlayerCards.txt", "WeaponCards.txt", "legend.txt");
		testBoard.loadLegend();
		testBoard.loadBoard();
		testBoard.calcAdjacencies();
		//clueGame.loadConfigFiles();
		//clueGame.deal();
	}

	@Test
	public void testPlayersFromFile() {
		// checks that humanIndex actually points to the human player, then checks color
		// and starting location
		// may change to HumanPlayer eventually
		Player human = clueGame.getPlayers().get(clueGame.humanIndex);
		assertEquals("Miss Scarlet", human.getName());
		assertEquals(Color.RED, human.getColor());
		assertEquals(199, testBoard.calcIndex(human.getRow(), human.getCol()));
		
		// checks some computer players, may change to ComputerPlayer class later
		Player comp = clueGame.getPlayers().get(5);
		assertEquals("Professor Plum", comp.getName());
		assertEquals(Color.magenta, comp.getColor());
		assertEquals(569, testBoard.calcIndex(comp.getRow(), comp.getCol()));
		
		comp = clueGame.getPlayers().get(3);
		assertEquals("Mr Green", comp.getName());
		assertEquals(Color.green, comp.getColor());
		assertEquals(43, testBoard.calcIndex(comp.getRow(), comp.getCol()));
	}
	
	@Test
	public void testCards() {
		ArrayList<Card> testCards = clueGame.getCards();
		// checks total number of cards
		assertEquals(NUM_CARDS, testCards.size());
		
		int wCount, pCount, rCount;
		wCount = pCount = rCount = 0;
		for (int i = 0; i < NUM_CARDS; ++i) {
			if (testCards.get(i).getType() == CardType.PERSON)
				++pCount;
			else if (testCards.get(i).getType() == CardType.WEAPON)
				++wCount;
			else if (testCards.get(i).getType() == CardType.ROOM)
				++rCount;
		}
		// after iterating through the list of cards, checks the number of each type of card
		assertEquals(PLAYERS, pCount);
		assertEquals(WEAPONS, wCount);
		// NUM_ROOMS is 11 because it includes the closet and walkway, should be only 9 in card list
		assertEquals(NUM_ROOMS-2, rCount);
		// checks the list of cards for one of each type
		assertTrue(testCards.contains(new Card("Miss Scarlet", CardType.PERSON)));
		assertTrue(testCards.contains(new Card("Garden", CardType.ROOM)));
		assertTrue(testCards.contains(new Card("Knife", CardType.WEAPON)));


	}

	@Test
	public void testDeal() {
		ArrayList<Player> testPlay = clueGame.getPlayers();
		int cardsDealt = 0;
		
		int min, max;
		min = max = testPlay.get(0).getMyCards().size();
		for (int i=0; i < PLAYERS; ++i) {
			int temp = testPlay.get(i).getMyCards().size();
			if (temp > max)
				max = temp;
			else if (temp < min)
				min = temp;
			
			// checks that the difference between the lowest card count and the 
			// highest card count is never more than 1
			assertTrue(Math.abs(max - min) <= 1);
			cardsDealt += temp;
		}
		
		// Checks that the total number of cards from each player is equal to the total number of cards
		// with 3 subtracted since 3 cards are reserved for the solution
		assertEquals(NUM_CARDS-3, cardsDealt);
		
		// list of cards that have been seen once already
		ArrayList<Card> seenCards = new ArrayList<Card>(NUM_CARDS);
		for (Player player : testPlay) {
			for (Card card : player.getMyCards()) {
				
				// if this card has already been added to the array, the test will fail
				assertFalse(seenCards.contains(card));
				// adds the card to the list of seen cards
				seenCards.add(card);
			}
		}
	}

}

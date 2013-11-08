package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class GameActionTests {
	private static Board testBoard;
	private static ClueGame clueGame;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 24;
	public static final int NUM_ROOMS = 11;
	
	public static Card whiteCard;
	public static Card greenCard;
	public static Card peaCard;
	public static Card plumCard;
	public static Card mustardCard;
	public static Card scarCard;
	
	public static Card knifeCard;
	public static Card pipeCard;
	public static Card ropeCard;
	public static Card wrenchCard;
	public static Card candleCard;
	public static Card gunCard;
	
	public static Card gardenCard;
	public static Card studyCard;
	public static Card libCard;
	public static Card hallCard;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testBoard = new Board("ClueLayout.csv", "legend.txt");
		clueGame = new ClueGame("PlayerCards.txt", "WeaponCards.txt", "legend.txt");
		testBoard.loadLegend();
		testBoard.loadBoard();
		testBoard.calcAdjacencies();
		clueGame.loadConfigFiles();
		//clueGame.deal();
		
		whiteCard = new Card("Mrs White", Card.CardType.PERSON);
		greenCard = new Card("Mr Green", Card.CardType.PERSON);
		peaCard = new Card("Mrs Peacock", Card.CardType.PERSON);
		plumCard = new Card("Professor Plum", Card.CardType.PERSON);
		mustardCard = new Card("Colonel Mustard", Card.CardType.PERSON);
		scarCard = new Card("Miss Scarlet", Card.CardType.PERSON);
		
		knifeCard = new Card("Knife", Card.CardType.WEAPON);
		pipeCard = new Card("Pipe", Card.CardType.WEAPON);
		ropeCard = new Card("Rope", Card.CardType.WEAPON);
		wrenchCard = new Card("Wrench", Card.CardType.WEAPON);
		candleCard = new Card("Candlestick", Card.CardType.WEAPON);
		gunCard = new Card("Revolver", Card.CardType.WEAPON);
		
		gardenCard = new Card("Garden", Card.CardType.ROOM);
		studyCard = new Card("Study", Card.CardType.ROOM);
		libCard = new Card("Library", Card.CardType.ROOM);
		hallCard = new Card("Hallway", Card.CardType.ROOM);
	}

	@Test
	public void testAccusation() {
		// creates a dummy solution so we know the correct values
		clueGame.setAnswer(new Solution("Miss Scarlet", "Knife", "Garden"));
		
		// have a player make a correct accusation
		ComputerPlayer accuse = new ComputerPlayer();
		Solution accusing =accuse.makeAccusation("Miss Scarlet", "Knife", "Garden");
		
		// checks that checkAccusation returns true for the correct accusation
		assertTrue(clueGame.checkAccusation(accusing));
		
		// checks for a false return with the wrong Room
		accusing = new Solution("Miss Scarlet", "Knife", "Kitchen");
		assertFalse(clueGame.checkAccusation(accusing));
		
		// checks for a false return with the wrong Weapon
		accusing = new Solution("Miss Scarlet", "Candlestick", "Garden");
		assertFalse(clueGame.checkAccusation(accusing));
		
		// checks for a false return with the wrong Person
		accusing = new Solution("Professor Plum", "Knife", "Garden");
		assertFalse(clueGame.checkAccusation(accusing));
		
		// checks for false return with all three wrong
		accusing = new Solution("Mrs White", "Rope", "Dining Room");
		assertFalse(clueGame.checkAccusation(accusing));

	}
	
	@Test
	public void testRoomSelect () {
		// calculate targets with 3 steps at an index where the Library is within 2 steps
		int index = testBoard.calcIndex(8, 19);
		int steps = 3;
		// should contain several walkway cells and the Library doorway
		//testBoard.startTargets(index, steps);
		Set<Integer> testTargets = testBoard.getTargets(index, steps);
		ComputerPlayer player = new ComputerPlayer(" ", Color.black, 0, 0);
		// pickLocation should return the room every time it's called
		for (int i = 0; i < 5; i++) {
			BoardCell cell = player.pickLocation(testTargets, testBoard.getCells());
			assertTrue(cell.isRoom());
			// clears lastRoomVisited by giving it the walkway cell initial
			player.setLastRoomVisited('W');
		}
	}
	
	@Test
	public void testRandomTargetSelection() {
		ComputerPlayer player = new ComputerPlayer();
		// location with three targets, none of which are rooms
		int index = testBoard.calcIndex(5, 0);
		//testBoard.startTargets(index, 2);
		int loc_3_0Tot = 0;
		int loc_5_2Tot = 0;
		int loc_7_0Tot = 0;
		// run the test 100 times
		for (int i=0; i < 100; i++) {
			BoardCell picked = player.pickLocation(testBoard.getTargets(index, 2), testBoard.getCells());
			if (picked == testBoard.getCellAt(testBoard.calcIndex(3, 0)))
				loc_3_0Tot++;
			else if (picked == testBoard.getCellAt(testBoard.calcIndex(5, 2)))
				loc_5_2Tot++;
			else if (picked == testBoard.getCellAt(testBoard.calcIndex(7, 0)))
				loc_7_0Tot++;
			else
				fail("Invalid target selected");
		}
		// tests that we have 100 total selections
		assertEquals(100, loc_3_0Tot + loc_5_2Tot + loc_7_0Tot);
		// make sure each target was selected more than once
		assertTrue(loc_3_0Tot > 10);
		assertTrue(loc_5_2Tot > 10);
		assertTrue(loc_7_0Tot > 10);
	}
	
	@Test
	public void testLastVisitedRoom() {
		ComputerPlayer player = new ComputerPlayer();
		// location that is 1 step away from Study
		int index = testBoard.calcIndex(16, 20);
		// it will pick the Study room cell, and by doing so set lastRoomVisited to the Study
		player.pickLocation(testBoard.getTargets(index, 1), testBoard.getCells());
		
		// calls the function again, and test to make sure that the room is not selected every time
		int roomTot, otherTot;
		roomTot = otherTot = 0;
		// run the test 20 times
		for (int i = 0; i < 20; i ++) {
			BoardCell cell = player.pickLocation(testBoard.getTargets(index, 1), testBoard.getCells());
			if (cell.isRoom())
				roomTot++;
			else
				otherTot++;
		}
		assertEquals(20, roomTot + otherTot);
		// ensures a target other than the room is picked more than half the time
		assertTrue(otherTot > 10);
	}

	@Test
	public void testDisproveSuggestion_1player1match() {
		ComputerPlayer player = new ComputerPlayer();
		// "deal" the cards
		player.getMyCards().add(whiteCard);
		player.getMyCards().add(greenCard);
		player.getMyCards().add(knifeCard);
		player.getMyCards().add(pipeCard);
		player.getMyCards().add(gardenCard);
		player.getMyCards().add(studyCard);
		// test right person
		assertEquals(whiteCard, player.disproveSuggestion("Mrs White", "Library", "Candlestick"));
		// test right room
		assertEquals(gardenCard, player.disproveSuggestion("Miss Scarlet", "Garden", "Candlestick"));
		// test right weapon
		assertEquals(knifeCard, player.disproveSuggestion("Miss Scarlet", "Library", "Knife"));
		// test returning nothing/null
		assertEquals(null, player.disproveSuggestion("Miss Scarlet", "Library", "Candlestick"));
	}
	
	@Test
	public void testDisproveSuggestion_1playerMultipleMatch() {
		ComputerPlayer player = new ComputerPlayer();
		// "deal" the cards
		player.getMyCards().add(whiteCard);
		player.getMyCards().add(greenCard);
		player.getMyCards().add(knifeCard);
		player.getMyCards().add(pipeCard);
		player.getMyCards().add(gardenCard);
		player.getMyCards().add(studyCard);
		
		int whiteTot, studyTot, knifeTot;
		whiteTot = studyTot = knifeTot = 0;
		
		// run the test 100 times
		for (int i = 0; i < 100; ++i) {
			Card card = player.disproveSuggestion("Mrs White", "Study", "Knife");
			if (card == whiteCard)
				whiteTot++;
			else if (card == studyCard)
				studyTot++;
			else if (card == knifeCard)
				knifeTot++;
			else
				fail("Invalid target selected");
		}
		
		// tests that we have 100 total selections
		assertEquals(100, whiteTot + studyTot + knifeTot);
		// make sure each target was selected more than once
		assertTrue(whiteTot > 10);
		assertTrue(studyTot > 10);
		assertTrue(knifeTot > 10);
	}
	
	@Test
	public void testDisproveSuggestion_AllPlayersQueried() {
		// gets a full list of players
		ArrayList<Player> testPlayers = new ArrayList<Player>(6);
		for (int i = 0; i < 5; i++)
			testPlayers.add(new ComputerPlayer());
		testPlayers.add(new HumanPlayer());
		
		// "deals" the cards
		testPlayers.get(0).getMyCards().add(hallCard);
		testPlayers.get(0).getMyCards().add(plumCard);
		testPlayers.get(1).getMyCards().add(gardenCard);
		testPlayers.get(1).getMyCards().add(knifeCard);
		testPlayers.get(2).getMyCards().add(libCard);
		testPlayers.get(2).getMyCards().add(wrenchCard);
		testPlayers.get(3).getMyCards().add(peaCard);
		testPlayers.get(3).getMyCards().add(whiteCard);
		testPlayers.get(4).getMyCards().add(ropeCard);
		testPlayers.get(4).getMyCards().add(studyCard);
		testPlayers.get(5).getMyCards().add(pipeCard);
		testPlayers.get(5).getMyCards().add(greenCard);
		clueGame.setPlayers(testPlayers);
		
		// tests that no player has a card, null returned
		// sets the suggesting player in the kitchen, since there is no kitchen card "dealt"
		clueGame.getPlayers().get(0).setStartCol(15);
		clueGame.getPlayers().get(0).setStartRow(19);
		//System.out.println(clueGame.getPlayers().get(0).getStartRow()+" "+clueGame.getPlayers().get(0).getStartCol());
		assertEquals(null, clueGame.handleSuggestion("Miss Scarlet", "CandleStick", clueGame.getPlayers().get(0)));
		
		// tests that the human player returns the pipe card
		assertEquals(pipeCard, clueGame.handleSuggestion("Miss Scarlet", "Pipe", clueGame.getPlayers().get(0)));
		
		// tests that the suggesting player does not disprove its own suggestion, null returned
		assertEquals(null, clueGame.handleSuggestion("Professor Plum", "CandleStick", clueGame.getPlayers().get(0)));
		// same test for human player
		clueGame.getPlayers().get(5).setStartCol(15);
		clueGame.getPlayers().get(5).setStartRow(19);
		assertEquals(null, clueGame.handleSuggestion("Miss Scarlet", "Pipe", clueGame.getPlayers().get(5)));
		
		// tests a suggestion that two players can disprove, making sure that the "first" player disproves it
		// the first player in the list has the plum card, and the last player has the pipe card
		assertEquals(null, clueGame.handleSuggestion("Professor Plum", "Pipe", clueGame.getPlayers().get(0)));

		// tests that the player at the end of the list can disprove
		assertEquals(null, clueGame.handleSuggestion("Mr Green", "Candlestick", clueGame.getPlayers().get(5)));
	}
	
	@Test
	public void testMakeCorrectSuggestion() {
		// creates a computer player in the Observatory (17, 3) and sets up a list of "seen" cards
		// that includes all but Miss Scarlet and the rope
		ComputerPlayer player = new ComputerPlayer();
		int row = 3;
		int col = 17;
		player.setStartRow(row);
		player.setStartCol(col);
		RoomCell cell = (RoomCell) testBoard.getBoardCellAt(row, col);

		player.updateSeen(whiteCard);
		player.updateSeen(peaCard);
		player.updateSeen(greenCard);
		player.updateSeen(mustardCard);
		player.updateSeen(plumCard);
		
		player.updateSeen(pipeCard);
		player.updateSeen(wrenchCard);
		player.updateSeen(gunCard);
		player.updateSeen(knifeCard);
		player.updateSeen(candleCard);
		
		// the suggestion should only be these cards
		Solution sol = player.createSuggestion(testBoard.getRooms().get(cell.getInitial()), clueGame.getCards());
		assertTrue(sol.person.equals("Miss Scarlet"));
		assertTrue(sol.weapon.equals("Rope"));
		assertTrue(sol.room.equals("Observatory"));
	}
	
	@Test
	public void testMakeRandomSuggestion() {
		// creates a computer player in the Dining Room (19, 7) and sets up a list of "seen" cards
		ComputerPlayer player = new ComputerPlayer();
		int row = 19;
		int col = 7;
		player.setStartRow(row);
		player.setStartCol(col);
		player.updateSeen(whiteCard);
		player.updateSeen(peaCard);
		player.updateSeen(greenCard);
		
		player.updateSeen(pipeCard);
		player.updateSeen(wrenchCard);
		player.updateSeen(gunCard);
		int scarTot, mustardTot, plumTot, knifeTot, candleTot, ropeTot;
		scarTot = mustardTot = plumTot = knifeTot = candleTot = ropeTot = 0;
		
		RoomCell cell = (RoomCell) testBoard.getBoardCellAt(row, col);
		for (int i = 0; i < 100; i++) {
			
			Solution sol = player.createSuggestion(testBoard.getRooms().get(cell.getInitial()), clueGame.getCards());
			
			// makes sure the correct room is chosen
			assertTrue(sol.room.equals("Dining Room"));
			if (sol.person.equalsIgnoreCase("Miss Scarlet"))
				scarTot++;
			else if (sol.person.equalsIgnoreCase("Colonel Mustard"))
				mustardTot++;
			else if (sol.person.equals("Professor Plum"))
				plumTot++;
			if (sol.weapon.equals("Knife"))
				knifeTot++;
			else if (sol.weapon.equals("Candlestick"))
				candleTot++;
			else if (sol.weapon.equals("Rope"))
				ropeTot++;
		}
		assertEquals(100, scarTot + mustardTot + plumTot);
		assertEquals(100, knifeTot + candleTot + ropeTot);
		
		// tests that each person/weapon was picked some number of times, to ensure random selection is working
		assertTrue(scarTot > 15);
		assertTrue(mustardTot > 15);
		assertTrue(plumTot > 15);
		
		assertTrue(knifeTot > 15);
		assertTrue(candleTot > 15);
		assertTrue(ropeTot > 15);
	}
}

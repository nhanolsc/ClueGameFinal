package clueTests;

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;
//import clueGame.RoomCell.DoorDirection;

public class InstructorTests {
	// I made this static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	
	@BeforeClass
	public static void setUp() throws Exception {
		board = new Board("InstructorClueLayout.csv", "InstructorClueLegend.txt");
		board.loadLegend();
		board.loadBoard();
	}
	
	@Test
	public void roomsNumber() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals(NUM_ROOMS, rooms.size());	
	}
	
	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		// Ensure we read the correct number of rooms
		assertEquals(NUM_ROOMS, rooms.size());
		// Test retrieving a few from the hash, including the first
		// and last in the file and a few others
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Billiard room", rooms.get('R'));
		assertEquals("Dining room", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction, plus two cells that are not
	// a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		// Test one each RIGHT/LEFT/UP/DOWN
		System.out.println("Here");
		RoomCell room = board.getRoomCellAt(4, 3);
		assertTrue(room.isRoom());
		System.out.println("Whathat");
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		
		System.out.println("Now here");
		room = board.getRoomCellAt(4, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getRoomCellAt(15, 18);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getRoomCellAt(14, 11);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		
		// Test that room pieces that aren't doors know it
		room = board.getRoomCellAt(14, 14);
		assertFalse(room.isDoorway());	
		
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(board.calcIndex(0, 6));
		assertFalse(cell.isDoorway());		

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		Assert.assertEquals(506, totalCells);
		for (int i=0; i<totalCells; i++)
		{
			BoardCell cell = board.getCellAt(i);
			if (cell.isDoorway())
				numDoors++;
		}
		assertEquals(16, numDoors);
	}

	@Test
	public void testCalcIndex() {
		// Test each corner of the board
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(NUM_COLUMNS-1, board.calcIndex(0, NUM_COLUMNS-1));
		assertEquals(483, board.calcIndex(NUM_ROWS-1, 0));
		assertEquals(505, board.calcIndex(NUM_ROWS-1, NUM_COLUMNS-1));
		// Test a couple others
		assertEquals(24, board.calcIndex(1, 1));
		assertEquals(66, board.calcIndex(2, 20));		
	}
	
	// Test a few room cells to ensure the room initial is
	// correct.
	@Test
	public void testRoomInitials() {
		System.out.println("Here");
		assertEquals('C', board.getRoomCellAt(0, 0).getInitial());
		System.out.println("A");
		
		assertEquals('R', board.getRoomCellAt(4, 8).getInitial());
		System.out.println("B");
		assertEquals('B', board.getRoomCellAt(9, 0).getInitial());
		System.out.println("C");
		assertEquals('O', board.getRoomCellAt(21, 22).getInitial());
		System.out.println("D");
		assertEquals('K', board.getRoomCellAt(21, 0).getInitial());
		System.out.println("E");
	}
	
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file names
		Board b = new Board("InstructorClueLayoutBadColumns.csv", "InstructorClueLegend.txt");
		// You may change these calls if needed to match your function names
		// My loadConfigFiles has a try/catch, so I can't call it directly to
		// see test throwing the BadConfigFormatException
		b.loadLegend();
		b.loadBoard();
	}
	
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		Board b = new Board("InstructorClueLayoutBadRoom.csv", "InstructorClueLegend.txt");
		b.loadLegend();
		b.loadBoard();
	}
	
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		Board b = new Board("InstructorClueLayout.csv", "InstructorClueLegendBadFormat.txt");
		b.loadLegend();
		b.loadBoard();
	}
}
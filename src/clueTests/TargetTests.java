package clueTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

import experiment.IntBoard;

public class TargetTests {
	private static Board testBoard;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_COLUMNS = 24;
	public static final int NUM_ROWS = 24;
	
	@Before
	public void init() throws Exception {
		testBoard = new Board("ClueLayout.csv", "legend.txt");
		testBoard.loadLegend();
		testBoard.loadBoard();
		testBoard.calcAdjacencies();
	}
	
	//Test important helper functions
	@Test
	public void testRowColumn() {
		testBoard.calcAdjacencies();
		
		int a = testBoard.calcIndex(15, 7);
		assertEquals(15, testBoard.getRow(a));
		assertEquals(7, testBoard.getCol(a));
		
		int b = testBoard.calcIndex(4, 9);
		assertEquals(4, testBoard.getRow(b));
		assertEquals(9, testBoard.getCol(b));
		
		int c = testBoard.calcIndex(11, 3);
		assertEquals(11, testBoard.getRow(c));
		assertEquals(3, testBoard.getCol(c));
		
		int d = testBoard.calcIndex(21, 14);
		assertEquals(21, testBoard.getRow(d));
		assertEquals(14, testBoard.getCol(d));
	}
	
	//ADJACENCY TESTS FOR.............................................................

	//Red: Locations with only walkways as adjacent locations 
	@Test
	public void testAdjOnlyWalkways() {
		testBoard.calcAdjacencies();
		
		int location = testBoard.calcIndex(1, 12);
		int up = testBoard.calcIndex(0, 12);
		int down = testBoard.calcIndex(2, 12);
		int left = testBoard.calcIndex(1, 11);
		int right = testBoard.calcIndex(1, 13);
		LinkedList<Integer> testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(4, testList.size());
		
		location = testBoard.calcIndex(6, 7);
		up = testBoard.calcIndex(5, 7);
		down = testBoard.calcIndex(7, 7);
		left = testBoard.calcIndex(6, 6);
		right = testBoard.calcIndex(6, 8);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(4, testList.size());
	}
	
	//Orange: Locations that are at each edge of the board
	@Test
	public void testEdges() {
		testBoard.calcAdjacencies();
		
		int location = testBoard.calcIndex(0, 11);
		int down = testBoard.calcIndex(1, 11);
		int left = testBoard.calcIndex(0, 10);
		int right = testBoard.calcIndex(0, 12);
		LinkedList<Integer> testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
		
		location = testBoard.calcIndex(11, 23);
		int up = testBoard.calcIndex(10, 23);
		down = testBoard.calcIndex(12, 23);
		left = testBoard.calcIndex(11, 22);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertEquals(2, testList.size());
		
		location = testBoard.calcIndex(23, 12);
		up = testBoard.calcIndex(22, 12);
		left = testBoard.calcIndex(23, 11);
		right = testBoard.calcIndex(23, 13);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(2, testList.size());
		
		location = testBoard.calcIndex(10, 0);
		up = testBoard.calcIndex(9, 0);
		down = testBoard.calcIndex(11, 0);
		right = testBoard.calcIndex(10, 1);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertEquals(2, testList.size());
	}
	
	//Green: Locations that are beside a room cell that is not a doorway
	@Test
	public void testAdjNextToRoom() {
		testBoard.calcAdjacencies();

		int location = testBoard.calcIndex(4, 6);
		int up = testBoard.calcIndex(3, 6);
		int down = testBoard.calcIndex(5, 6);
		int left = testBoard.calcIndex(4, 5);
		int right = testBoard.calcIndex(4, 7);
		LinkedList<Integer> testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertFalse(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
		
		location = testBoard.calcIndex(6, 15);
		up = testBoard.calcIndex(5, 15);
		down = testBoard.calcIndex(7, 15);
		left = testBoard.calcIndex(6, 14);
		right = testBoard.calcIndex(6, 16);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertFalse(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
	}

	//Blue: Locations that are adjacent to a doorway with needed direction
	@Test
	public void testAdjToDoorway() {
		testBoard.calcAdjacencies();

		int location = testBoard.calcIndex(3, 7);
		int up = testBoard.calcIndex(2, 7);
		int down = testBoard.calcIndex(4, 7);
		int left = testBoard.calcIndex(3, 6);
		int right = testBoard.calcIndex(3, 8);
		LinkedList<Integer> testList = testBoard.getAdjList(location);
		assertFalse(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertFalse(testList.contains(right));
		assertEquals(2, testList.size());
		
		location = testBoard.calcIndex(5, 2);
		up = testBoard.calcIndex(4, 2);
		down = testBoard.calcIndex(6, 2);
		left = testBoard.calcIndex(5, 1);
		right = testBoard.calcIndex(5, 3);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertFalse(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
	}
	
	//Purple: Locations that are doorways
	@Test
	public void testDoorway() {
		testBoard.calcAdjacencies();

		int location = testBoard.calcIndex(2, 7);
		int up = testBoard.calcIndex(1, 7);
		int down = testBoard.calcIndex(3, 7);
		int left = testBoard.calcIndex(2, 6);
		int right = testBoard.calcIndex(2, 8);
		LinkedList<Integer> testList = testBoard.getAdjList(location);
		assertFalse(testList.contains(up));
		assertFalse(testList.contains(down));
		assertTrue(testList.contains(left));
		assertFalse(testList.contains(right));
		assertEquals(1, testList.size());
		
		location = testBoard.calcIndex(4, 2);
		up = testBoard.calcIndex(3, 2);
		down = testBoard.calcIndex(5, 2);
		left = testBoard.calcIndex(4, 1);
		right = testBoard.calcIndex(4, 3);
		testList = testBoard.getAdjList(location);
		assertFalse(testList.contains(up));
		assertTrue(testList.contains(down));
		assertFalse(testList.contains(left));
		assertFalse(testList.contains(right));
		assertEquals(1, testList.size());
	}
	
	//TARGET TESTS FOR.................................................................
	
	//Pink: Targets along walkways at various distances
	@Test
	public void testTargetAlongWalkway_1() {
		int location = testBoard.calcIndex(0, 16);
		Set<Integer> targets1 = testBoard.getTargets(location, 2);

		int a = testBoard.calcIndex(0, 14);
		int b = testBoard.calcIndex(0, 18);
		int c = testBoard.calcIndex(1, 15);
		int d = testBoard.calcIndex(2, 16);

		assertEquals(4, targets1.size());
		assertTrue(targets1.contains(a));
		assertTrue(targets1.contains(b));
		assertTrue(targets1.contains(c));
		assertTrue(targets1.contains(d));
	}
	
	@Test
	public void testTargetAlongWalkway_2() {
		int location = testBoard.calcIndex(10, 7);
		Set<Integer> targets2 = testBoard.getTargets(location, 3);

		int a =  testBoard.calcIndex(7, 7);
		int b =  testBoard.calcIndex(8, 6);
		int c =  testBoard.calcIndex(8, 8);
		int d =  testBoard.calcIndex(9, 7);
		int e =  testBoard.calcIndex(9, 9);
		int f =  testBoard.calcIndex(10, 6);
		int g =  testBoard.calcIndex(10, 8);
		int h =  testBoard.calcIndex(10, 10);
		int i =  testBoard.calcIndex(11, 7);
		int j =  testBoard.calcIndex(11, 9);
		int k =  testBoard.calcIndex(12, 6);
		int l =  testBoard.calcIndex(12, 8);
		int m = testBoard.calcIndex(13, 7);

		assertEquals(8, targets2.size());
		assertTrue(targets2.contains(a));
		assertTrue(targets2.contains(b));
		assertTrue(targets2.contains(c));
		assertTrue(targets2.contains(d));
		assertFalse(targets2.contains(e));
		assertTrue(targets2.contains(f));
		assertFalse(targets2.contains(g));
		assertFalse(targets2.contains(h));
		assertTrue(targets2.contains(i));
		assertFalse(targets2.contains(j));
		assertTrue(targets2.contains(k));
		assertFalse(targets2.contains(l));
		assertTrue(targets2.contains(m));
	}

	@Test
	public void testTargetAlongWalkway_3() {
		int location = testBoard.calcIndex(8, 20);
		Set<Integer> targets3 = testBoard.getTargets(location, 4);

		int a =  testBoard.calcIndex(7, 23);
		int b =  testBoard.calcIndex(8, 16);
		int c =  testBoard.calcIndex(9, 23);
		int d = testBoard.calcIndex(9, 18); 

		assertEquals(4, targets3.size());
		assertTrue(targets3.contains(a));
		assertTrue(targets3.contains(b));
		assertTrue(targets3.contains(c));
		assertTrue(targets3.contains(d));
	}
	
	@Test
	public void testTargetAlongWalkway_4() {
		int location = testBoard.calcIndex(18, 8);
		Set<Integer> targets4 = testBoard.getTargets(location, 5);

		int a =  testBoard.calcIndex(13, 8);
		int b =  testBoard.calcIndex(14, 7);
		int c =  testBoard.calcIndex(14, 9);
		int d =  testBoard.calcIndex(15, 6);
		int e =  testBoard.calcIndex(15, 8);
		int f =  testBoard.calcIndex(15, 10);
		int g =  testBoard.calcIndex(16, 5);
		int h =  testBoard.calcIndex(16, 7);
		int i =  testBoard.calcIndex(16, 9);
		int j =  testBoard.calcIndex(16, 11);
		int k =  testBoard.calcIndex(22, 7);
		int l =  testBoard.calcIndex(22, 9);
		int m = testBoard.calcIndex(23, 8);
		int n = testBoard.calcIndex(19, 7);

		assertEquals(12, targets4.size());
		assertFalse(targets4.contains(a));
		assertTrue(targets4.contains(b));
		assertFalse(targets4.contains(c));
		assertTrue(targets4.contains(d));
		assertTrue(targets4.contains(e));
		assertTrue(targets4.contains(f));
		assertTrue(targets4.contains(g));
		assertTrue(targets4.contains(h));
		assertTrue(targets4.contains(i));
		assertTrue(targets4.contains(j));
		assertTrue(targets4.contains(k));
		assertTrue(targets4.contains(l));
		assertTrue(targets4.contains(m));
		assertTrue(targets4.contains(n));
	}
	
	//White: Targets that allow the user to enter a room
	@Test
	public void testTargetEnterRoom_1() {
		int location = testBoard.calcIndex(3, 15);
		Set<Integer> targets1 = testBoard.getTargets(location, 2);
		
		int a = testBoard.calcIndex(1, 15);
		int b = testBoard.calcIndex(2, 14);
		int c = testBoard.calcIndex(2, 16);
		int d = testBoard.calcIndex(3, 14);
		int e = testBoard.calcIndex(3, 17);
		int f = testBoard.calcIndex(4, 16);
		
		assertEquals(7, targets1.size()); 
		assertTrue(targets1.contains(a));
		assertTrue(targets1.contains(b));
		assertTrue(targets1.contains(c));
		assertTrue(targets1.contains(d)); 
		assertTrue(targets1.contains(e));
		assertTrue(targets1.contains(f));
	}
	
	@Test
	public void testTargetEnterRoom_2() {
		int location = testBoard.calcIndex(8, 18);
		Set<Integer> targets2 = testBoard.getTargets(location, 3);

		int a = testBoard.calcIndex(7, 16);
		int b = testBoard.calcIndex(8, 15);
		int c = testBoard.calcIndex(9, 16);
		int d = testBoard.calcIndex(9, 18);
		int e = testBoard.calcIndex(8, 21);

		assertEquals(5, targets2.size()); 
		assertTrue(targets2.contains(a));
		assertTrue(targets2.contains(b));
		assertTrue(targets2.contains(c));
		assertTrue(targets2.contains(d)); 
		assertTrue(targets2.contains(e));
	}

	//Turquoise: Targets calculated when leaving a room
	@Test
	public void testTargetLeavingRoom_1() {
		int location = testBoard.calcIndex(17, 20);
		Set<Integer> targets = testBoard.getTargets(location, 3);

		int a = testBoard.calcIndex(16, 18);
		int b = testBoard.calcIndex(16, 22);

		assertEquals(2, targets.size());
		assertTrue(targets.contains(a));
		assertTrue(targets.contains(b));
	}

	@Test
	public void testTargetLeavingRoom_2() {
		int location = testBoard.calcIndex(19, 15);
		Set<Integer> targets = testBoard.getTargets(location, 2);

		int a = testBoard.calcIndex(18, 16);
		int b = testBoard.calcIndex(20, 16);
		int c = testBoard.calcIndex(20, 14);
		int d = testBoard.calcIndex(21, 15);

		assertEquals(2, targets.size());
		assertTrue(targets.contains(a));
		assertTrue(targets.contains(b));
		assertFalse(targets.contains(c));
		assertFalse(targets.contains(d));
	}
}

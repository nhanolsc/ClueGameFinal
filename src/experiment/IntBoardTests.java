package experiment;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class IntBoardTests {
	public IntBoard newBoard;
	
	@Before
	public void init() {
		newBoard = new IntBoard();
	}

	@Test
	public void testCalcIndex() {
		assertEquals(newBoard.calcIndex(1,3), 7);
		assertEquals(newBoard.calcIndex(0,0), 0);
	}
	
	@Test
	public void testAdjacency()
	{
		newBoard.calcAdjacencies();
		
		LinkedList<Integer> testList = newBoard.getAdjList(0);
		assertTrue(testList.contains(4));
		assertTrue(testList.contains(1));
		assertEquals(2, testList.size());
		
		testList = newBoard.getAdjList(15);
		assertTrue(testList.contains(11));
		assertTrue(testList.contains(14));
		assertEquals(2, testList.size());
		
		testList = newBoard.getAdjList(7);
		assertTrue(testList.contains(11));
		assertTrue(testList.contains(3));
		assertTrue(testList.contains(6));
		assertEquals(3, testList.size());
		
		testList = newBoard.getAdjList(8);
		assertTrue(testList.contains(12));
		assertTrue(testList.contains(9));
		assertTrue(testList.contains(4));
		assertEquals(3, testList.size());
		
		testList = newBoard.getAdjList(5);
		assertTrue(testList.contains(4));
		assertTrue(testList.contains(9));
		assertTrue(testList.contains(6));
		assertTrue(testList.contains(1));
		assertEquals(4, testList.size());
		
		testList = newBoard.getAdjList(10);
		assertTrue(testList.contains(6));
		assertTrue(testList.contains(9));
		assertTrue(testList.contains(14));
		assertTrue(testList.contains(11));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testTargets9_1() {
		Set<Integer> targets = newBoard.getTargets(9,1);
		assertEquals(4, targets.size());
		assertTrue(targets.contains(5));
		assertTrue(targets.contains(8));
		assertTrue(targets.contains(13));
		assertTrue(targets.contains(10));
	}
		
	@Test
	public void testTargets6_2() {
		System.out.println(newBoard.getTargets(6, 2));
		Set<Integer> targets = newBoard.getTargets(6,2);
		assertEquals(6, targets.size());
		assertTrue(targets.contains(1));
		assertTrue(targets.contains(4));
		assertTrue(targets.contains(9));
		assertTrue(targets.contains(14));
		assertTrue(targets.contains(11));
		assertTrue(targets.contains(3));
	}
		
	@Test
	public void testTargets4_3() {
		Set<Integer> targets = newBoard.getTargets(4,3);
		assertEquals(7, targets.size());
		assertTrue(targets.contains(0));
		assertTrue(targets.contains(5));
		assertTrue(targets.contains(8));
		assertTrue(targets.contains(13));
		assertTrue(targets.contains(10));
		assertTrue(targets.contains(7));
		assertTrue(targets.contains(2));
	}
		
	@Test
	public void testTargets0_4() {
		Set<Integer> targets = newBoard.getTargets(0,4);
		assertEquals(6, targets.size());
		assertTrue(targets.contains(2));
		assertTrue(targets.contains(5));
		assertTrue(targets.contains(8));
		assertTrue(targets.contains(7));
		assertTrue(targets.contains(10));
		assertTrue(targets.contains(13));
	}
		
	@Test
	public void testTargets12_5() {
		Set<Integer> targets = newBoard.getTargets(12,5);
		assertEquals(8, targets.size());
		assertTrue(targets.contains(8));
		assertTrue(targets.contains(13));
		assertTrue(targets.contains(0));
		assertTrue(targets.contains(5));
		assertTrue(targets.contains(10));
		assertTrue(targets.contains(15));
		assertTrue(targets.contains(2));
		assertTrue(targets.contains(7));
	}
		
	@Test
	public void testTargets0_6() {
		Set<Integer> targets = newBoard.getTargets(0,6);
		assertEquals(7, targets.size());
		assertTrue(targets.contains(2));
		assertTrue(targets.contains(5));
		assertTrue(targets.contains(8));
		assertTrue(targets.contains(13));
		assertTrue(targets.contains(10));
		assertTrue(targets.contains(7));
		assertTrue(targets.contains(15));	
	}

}

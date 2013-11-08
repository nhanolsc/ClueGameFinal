package experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	
	private Map<Integer, LinkedList<Integer>> adjMatrix = new HashMap<Integer, LinkedList<Integer>>();
	private Set<Integer> targets = new HashSet<Integer>();
	private boolean[] visited = new boolean[16];

	public IntBoard() {
		
	}
	
	public Map<Integer, LinkedList<Integer>> calcAdjacencies() {
		for (int index = 0; index < 16; index++) {
			LinkedList<Integer> adjList = new LinkedList<Integer>();
			if (index % 4 == 0) {
				if (index + 4 < 16) {
					adjList.add(index + 4);
					//System.out.println("Added " + (index + 4));
				}
				if (index - 4 >= 0) {
					adjList.add(index - 4);
					//System.out.println("Added " + (index - 4));
				}
				if (index + 1 < 16) {
					adjList.add(index + 1);
					//System.out.println("Added " + (index + 1));
				}
			} else if ((index + 1) % 4 == 0) {
				if (index + 4 < 16) {
					adjList.add(index + 4);
					//System.out.println("Added " + (index + 4));
				}
				if (index - 4 >= 0) {
					adjList.add(index - 4);
					//System.out.println("Added " + (index + 4));
				}
				if (index - 1 >= 0) {
					adjList.add(index - 1);
					//System.out.println("Added " + (index - 1));
				}
			} else {
				if (index + 4 < 16) {
					adjList.add(index + 4);
					//System.out.println("Added " + (index + 4));
				}
				if (index - 4 >= 0) {
					adjList.add(index - 4);
					//System.out.println("Added " + (index - 4));
				}
				if (index + 1 < 16) {
					adjList.add(index + 1);
					//System.out.println("Added " + (index + 1));
				}
				if (index - 1 >= 0) {
					adjList.add(index - 1);
					//System.out.println("Added " + (index -1));
				}
			}
			adjMatrix.put(index, adjList);
		}
		return adjMatrix;
	}
	
	public void startTargets(int location, int steps) {
		//populate visited array
		for (int i = 0; i < 15; i++) 
			visited[i] = false;
		//set visited[start location] to true
		visited[location] = true;
		//adjacency matrix calculated
		this.calcAdjacencies();
		//System.out.println(this.calcAdjacencies());
		//calcTargets with location and steps 
		this.calcTargets(location, steps);
	}
	
	public Set<Integer> calcTargets(int thisCell, int numSteps) {
		LinkedList<Integer> adjCells = new LinkedList<Integer>();
		LinkedList<Integer> temp = getAdjList(thisCell);
		for (int value : temp) {
			if (!visited[value])
				adjCells.add(value);
		}
		//System.out.println("AdjList for " + thisCell + ": " + adjCells);
		for (int adjCell : adjCells) {
			visited[adjCell] = true;
			//System.out.println("Num steps: " + numSteps);
			if (numSteps == 1) {
				targets.add(adjCell);
				//System.out.println("Added " + adjCell + " to targets.");
			}
			else
				calcTargets(adjCell, (numSteps-1));
			visited[adjCell] = false;
		}
		//System.out.println("Targets for " + thisCell + " taking " + numSteps + " steps: " + targets);
		return targets;
	}
	
	public Set<Integer> getTargets(int location, int steps) {
		Set<Integer> set = new HashSet<Integer>();
		this.startTargets(location, steps);
		set = calcTargets(location, steps);
		return set;
	}
	
	public LinkedList<Integer> getAdjList(int index) {
		return adjMatrix.get(index);
	}
	
	public int calcIndex(int row, int col) {
		int index = 0;
		index = row*4 + col;
		return index;
	}
}

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public class HumanPlayer extends Player {
	
	public HumanPlayer() {
		super();
	}

	public HumanPlayer(String name, Color color, int row, int col)
	{
		super(name, color, row, col);
	}

	public void makeMove(Set<Integer> targets, ArrayList<BoardCell> cells, Board board) {
		board.setHumanTurn(true);
		board.highlightTargets(targets);
		//board.repaint();
	}
}

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;

import javax.swing.JPanel;

public abstract class Player extends JPanel{
	private String name;
	private Color color;
	protected int row, col;
	private ArrayList<Card> myCards;

	public Player() {
		super();
		name = "";
		color = null;
		row = col = 0;
		// 10 seemed like a good size, probably won't be more than 4 or 5 though
		myCards = new ArrayList<Card>(10);
	}

	public Player(String name, Color color, int startRow, int startCol) {
		super();
		this.name = name;
		this.color = color;
		this.row = startRow;
		this.col = startCol;
		// 10 seemed like a good size, probably won't be more than 4 or 5 though
		myCards = new ArrayList<Card>(10);
	}

	public Card disproveSuggestion(String person, String room, String weapon)
	{
		ArrayList<Card> contradiction = new ArrayList<Card>();
		Random rand = new Random();
		int r = 0;
		for(int i = 0; i < myCards.size(); i++)
		{
			if(myCards.get(i).getName().equalsIgnoreCase(person))
				contradiction.add(myCards.get(i));
			else if(myCards.get(i).getName().equalsIgnoreCase(room))
				contradiction.add(myCards.get(i));
			else if(myCards.get(i).getName().equalsIgnoreCase(weapon))
				contradiction.add(myCards.get(i));
		}
		if (contradiction.size() > 0)
			r = rand.nextInt(contradiction.size());
		else
			contradiction.add(null);
		return contradiction.get(r);
	}

	public Solution makeAccusation(String person, String weapon, String room) {
		return new Solution(person, weapon, room);
	}
	
	public abstract void makeMove(Set<Integer> targets, ArrayList<BoardCell> cells, Board board);

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	// for testing purposes only
	public void setStartRow(int startRow) {
		this.row = startRow;
	}

	public void setStartCol(int startCol) {
		this.col = startCol;
	}
	// end test methods

	public void draw(Graphics g, Board board)
	{
		g.setColor(color);
		g.fillOval(col*30, row*30, 30, 30);
		g.setColor(color.black);
		g.drawOval(col*30, row*30, 30, 30);
	}
}

package clueGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class BoardCell {
	protected int row;
	protected int column;
	public static final int SIZE = 30;
	
	public BoardCell() {
		super();
	}
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public abstract void draw(Graphics g, Board board, boolean highlight);
	
	
	public boolean containsClick(Point point) {
		Rectangle rect = new Rectangle(column*SIZE, row*SIZE, SIZE, SIZE);
		if (rect.contains(point)) {
			return true;
		} else
			return false;
	}
}

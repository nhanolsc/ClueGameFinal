package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	
	public WalkwayCell(int row, int column) {
		super(row, column);
	}

	public boolean isWalkway() {
		return true;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public void draw(Graphics g, Board board, boolean highlight) {
		g.setColor(Color.YELLOW);
		if (highlight)
			g.setColor(Color.green);
		g.fillRect(column*SIZE, row*SIZE, SIZE, SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(column*SIZE, row*SIZE, SIZE, SIZE);
		
	}
	
}

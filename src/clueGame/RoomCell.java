package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};

	private DoorDirection doorDirection;
	private char roomInitial;
	
	public boolean roomName;
	private int doorSize = 4;

	public RoomCell(int row, int column) {
		super(row, column);
	}

	public RoomCell(int row, int column, char roomInitial, DoorDirection doorDirection, boolean roomName) {
		super(row, column);
		this.roomInitial = roomInitial;
		this.doorDirection = doorDirection;
		this.roomName = roomName;
	}

	public boolean isRoom() {
		return true;
	}

	public boolean isDoorway() {
		if (doorDirection != DoorDirection.NONE) {
			return true;
		} else {
			return false;
		}
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return roomInitial;
	}

	public void draw(Graphics g, Board board, boolean highlight) {
		g.setColor(Color.gray);
		if (highlight)
			g.setColor(Color.green);
		g.fillRect(column*SIZE, row*SIZE, SIZE, SIZE);
		if (doorDirection == DoorDirection.DOWN)
		{
			g.setColor(Color.BLUE);
			g.fillRect(column*SIZE, row*SIZE+(SIZE-doorSize), SIZE, doorSize);
		}
		else if (doorDirection == DoorDirection.UP)
		{
			g.setColor(Color.BLUE);
			g.fillRect(column*SIZE, row*SIZE, SIZE, doorSize);
		}     
		else if (doorDirection == DoorDirection.LEFT)
		{
			g.setColor(Color.BLUE);
			g.fillRect(column*SIZE, row*SIZE, doorSize, SIZE);
		}
		else if (doorDirection == DoorDirection.RIGHT)
		{
			g.setColor(Color.BLUE);
			g.fillRect(column*SIZE+(SIZE-doorSize), row*SIZE, doorSize, SIZE);
		}
		if (roomName) {
			g.setColor(Color.WHITE);
			g.drawString(board.getRooms().get(roomInitial).toUpperCase(), column*SIZE-2, row*SIZE);
		}
	}
}

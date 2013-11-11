package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player{
	private char lastRoomVisited;
	private Set<Card> saw;
	private Solution storedSuggest;
	
	public ComputerPlayer() {
		super();
		saw = new HashSet<Card>();
	}
	
	public ComputerPlayer(String name, Color color, int row, int col){
		super(name, color, row, col);
		saw = new HashSet<Card>();
	}
	
	public BoardCell pickLocation(Set<Integer> targets, ArrayList<BoardCell> cells)
	{
		Random rand = new Random();
		int moveCount = targets.size();
		int r = rand.nextInt(moveCount);
		// checks the entire target list for a room cell
		for (int choice : targets) {
			BoardCell cell = cells.get(choice);
			if (cell.isRoom()) {
				if (((RoomCell) cell).getInitial() != lastRoomVisited) {
					lastRoomVisited = ((RoomCell) cell).getInitial();
					return cell;
				}
			}
		}
		// if a room cell is not returned, iterates through the list,
		// once it hits the random number, returns that cell
		int count = 0;
		for (int choice : targets) {
			if (count == r)
				return cells.get(choice);
			++count;
		}
		// just to give each path a return
		return null;
	}
	
	public Solution createSuggestion(String room, ArrayList<Card> cards)
	{
		Solution suggestion = new Solution();
		suggestion.room = room;
		Random rand = new Random();
		ArrayList<String> possiblePeople = new ArrayList<String>();
		ArrayList<String> possibleWeapons = new ArrayList<String>();
		boolean check;
		for (Card choice : cards)
		{
			check = true;
			for (Card seenCard : saw) {
				if (seenCard.getName().equalsIgnoreCase(choice.getName()))
					check = false;
			}
			if (choice.getType() == CardType.PERSON && check) {
				possiblePeople.add(choice.getName());
			}
			else if (choice.getType() == CardType.WEAPON && check) {
				possibleWeapons.add(choice.getName());
			}
		}
		int r = rand.nextInt(possiblePeople.size());
		suggestion.person = possiblePeople.get(r);
		
		r = rand.nextInt(possibleWeapons.size());
		suggestion.weapon = possibleWeapons.get(r);
		
		storedSuggest = suggestion;
		return suggestion;
	}

	public void updateSeen(Card seen)
	{
		saw.add(seen);
	}
	
	public Solution makeMove(Set<Integer> targets, ArrayList<BoardCell> cells, Board board, ArrayList<Card> cards) {
		if (cards.size() - saw.size() == 3) {		// change this
			String person, weapon, room;
			person = weapon = room = "";
			for (Card card : cards) {
				if (!saw.contains(card)) {
					if (card.getType() == CardType.PERSON)
						person = card.getName();
					else if (card.getType() == CardType.WEAPON)
						weapon = card.getName();
					else if (card.getType() == CardType.ROOM)
						room = card.getName();
				}
			}
			return makeAccusation(person, weapon, room);
		}
		else {
			BoardCell move = pickLocation(targets, cells);
			row = move.getRow();
			col = move.getColumn();
			if (board.getRoomCellAt(row, col) != null)
				createSuggestion(board.getRooms().get(board.getRoomCellAt(row, col)),  cards);
			return new Solution("","","");
		}
	}
	
	// for testing purposes only
	
	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}

	public char getLastRoomVisited() {
		return lastRoomVisited;
	}
	
	public void draw(Graphics g, Board board) {
		super.draw(g, board);
	}
}

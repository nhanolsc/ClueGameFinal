package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import clueGame.Card.CardType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DisplayCards extends JPanel{
	private ArrayList<Card> people, rooms, weapons;
	
	public DisplayCards(ArrayList<Card> cards) {
		people = new ArrayList<Card>();
		rooms = new ArrayList<Card>();
		weapons = new ArrayList<Card>();
		JLabel title = new JLabel("My cards");
		readCards(cards);
		setLayout(new GridLayout(4,1));
		add(title);
		add(createPeople());
		add(createRooms());
		add(createWeapons());
	}
	
	public void readCards(ArrayList<Card> cards) {
		for (Card card : cards) {
			if (card.getType() == CardType.PERSON)
				people.add(card);
			else if (card.getType() == CardType.ROOM)
				rooms.add(card);
			else if (card.getType() == CardType.WEAPON)
				weapons.add(card);
		}
	}
	
	public JPanel createWeapons() {
		JPanel weaponDisplay = new JPanel(new GridLayout(0,1));
		JTextArea display;
		for (Card card : weapons) {
			display = new JTextArea(1, 10);
			display.setText(card.getName());
			display.setEditable(false);
			weaponDisplay.add(display);
		}
		weaponDisplay.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		return weaponDisplay;
	}
	
	public JPanel createRooms() {
		JPanel roomDisplay = new JPanel(new GridLayout(0,1));
		JTextArea display;
		for (Card card : rooms) {
			display = new JTextArea(1, 10);
			display.setText(card.getName());
			display.setEditable(false);
			roomDisplay.add(display);
		}
		roomDisplay.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		return roomDisplay;
	}
	
	public JPanel createPeople() {
		JPanel peopleDisplay = new JPanel(new GridLayout(0,1));
		JTextArea display;
		for (Card card : people) {
			display = new JTextArea(1, 10);
			display.setText(card.getName());
			display.setEditable(false);
			peopleDisplay.add(display);
		}
		peopleDisplay.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		return peopleDisplay;
	}
	
}

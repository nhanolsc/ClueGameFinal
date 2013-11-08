package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class Guesses extends JPanel {
	private JComboBox person, room, weapon;
	private ArrayList<Card> cards;

	public Guesses(ArrayList<Card> cards) {
		JPanel arrange = new JPanel(new GridLayout(6,1));
		this.cards = cards;
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		JLabel roomLabel = new JLabel("Room");

		person = new JComboBox();
		room = new JComboBox();
		weapon = new JComboBox();
		for (Card card : cards) {
			if (card.getType() == CardType.PERSON)
				person.addItem(card.getName());
			else if (card.getType() == CardType.ROOM)
				room.addItem(card.getName());
			else if (card.getType() == CardType.WEAPON)
				weapon.addItem(card.getName());

		}
		
		arrange.add(personLabel);
		arrange.add(person);
		arrange.add(roomLabel);
		arrange.add(room);
		arrange.add(weaponLabel);
		arrange.add(weapon);
		add(arrange);
		setBorder(new TitledBorder (new EtchedBorder(), "Guesses"));

	}
}

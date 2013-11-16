package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import clueGame.Card.CardType;


public class MakeaSuggestion extends JDialog{
	private JComboBox person, weapon;
	private ArrayList<Card> cards;
	private JButton submit, cancel;
	private JLabel personLabel, weaponLabel, roomLabel;
	private boolean submitted;
	private Board board;
	private ClueGame game;


	public MakeaSuggestion(ArrayList<Card> cards) {
		setTitle("Make a Suggestion");
		setSize(300,300);
		JPanel arrange = new JPanel(new GridLayout(0,2));
		personLabel = new JLabel("Person");
		weaponLabel = new JLabel("Weapon");
		roomLabel = new JLabel("Room");

		submit= new JButton("Submit");
		cancel= new JButton("Cancel");


		person = new JComboBox();
		weapon = new JComboBox();
		for (Card card : cards) {
			if (card.getType() == CardType.PERSON)
				person.addItem(card.getName());
			else if (card.getType() == CardType.WEAPON)
				weapon.addItem(card.getName());

		}

		arrange.add(personLabel);
		arrange.add(person);
		arrange.add(roomLabel);
		arrange.add(weaponLabel);
		arrange.add(weapon);
		arrange.add(submit);
		arrange.add(cancel);
		add(arrange);


		submit.addActionListener(new SuggestionListener());
		cancel.addActionListener(new SuggestionListener());
	}

	public class SuggestionListener implements ActionListener{
		private MakeaSuggestion mkSuggestion;
		private Card suggested;
		public void actionPerformed(ActionEvent e){
			if(e.getSource()== submit){
				submitted = true;
				String inputPerson= person.getName();
				String inputWeapon= weapon.getName();
				suggested= game.handleSuggestion(inputPerson, inputWeapon, game.getPlayers().get(0));

			}
			else if(e.getSource()== cancel){
				mkSuggestion.setVisible(false);
			}
			mkSuggestion.setVisible(false);
		}
	}



}
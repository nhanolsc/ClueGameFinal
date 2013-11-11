package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import clueGame.Card.CardType;


public class MakeaGuess extends JDialog{
	private JComboBox person, room, weapon;
	private ArrayList<Card> cards;
	private JButton submit, cancel;
	private JLabel personLabel, weaponLabel, roomLabel;
	private boolean submitted;
	private Board board;
	private ClueGame game;
	

	public MakeaGuess(ArrayList<Card> cards) {
		JPanel arrange = new JPanel(new GridLayout(0,2));
		personLabel = new JLabel("Person");
		weaponLabel = new JLabel("Weapon");
		roomLabel = new JLabel("Room");
		
		// I think we'll have to pass in the cards, that's why the null pointer exception was happening
		this.cards = cards;

		submit= new JButton("Submit");
		cancel= new JButton("Cancel");


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
		arrange.add(submit);
		arrange.add(cancel);
		add(arrange);


		submit.addActionListener(new ListenToGuess());
		cancel.addActionListener(new ListenToGuess());
	}

	public class ListenToGuess implements ActionListener{
	private MakeaGuess mkGuess;
		//mkGuess= new MakeaGuess(cards);
		public void actionPerformed(ActionEvent e){
			if(e.getSource()== submit){
				submitted = true;
			}
			else if(e.getSource()== cancel){
				mkGuess.setVisible(false);
				submitted= false;
			}
			if(submitted == true){
				String inputPerson= person.getName();
				String inputWeapon= weapon.getName();
				String inputRoom= room.getName();
				Solution sol= new Solution(inputPerson, inputWeapon, inputRoom);
				game.checkAccusation(sol);
				if(game.checkAccusation(sol)== true){
					JOptionPane.showMessageDialog(game, "Your Accusation is Correct", "YOU ARE THE WINNER", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(game, "Your Accusation is Incorrect", "SORRY", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			mkGuess.setVisible(false);
		}
	}

	

}
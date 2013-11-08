package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

public class DetectiveNotes extends JDialog {
	private ArrayList<Card> people, weapons, rooms;
	public DetectiveNotes(ArrayList<Card> cards){
		setTitle("Detective Notes");
		setSize(500, 700);
		setLayout(new GridLayout(1, 2));
		people = new ArrayList<Card>();
		weapons = new ArrayList<Card>();
		rooms = new ArrayList<Card>();
		for (Card card : cards) {
			if (card.getType() == CardType.PERSON)
				people.add(card);
			else if (card.getType() == CardType.WEAPON)
				weapons.add(card);	
			else if (card.getType() == CardType.ROOM)
				rooms.add(card);

		}
		Guesses guessList = new Guesses(cards);
		Checks playerChecks = new Checks(people, "Player");
		Checks weaponChecks = new Checks(weapons, "Weapon");
		Checks roomChecks = new Checks(rooms, "Room");
		JPanel checks = new JPanel(new GridLayout(3,1));

		checks.add(playerChecks);
		checks.add(weaponChecks);
		checks.add(roomChecks);
		add(checks);

		add(guessList);
		
		
	}

	public static void main(String[] args) {
		//DetectiveNotes gui = new DetectiveNotes();	
		//gui.setVisible(true);
	}
}
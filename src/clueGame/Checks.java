package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Checks extends JPanel {
	private JCheckBox box;
	public Checks(ArrayList<Card> cards, String title) {
		setLayout(new GridLayout(0, 2));
		for(Card card : cards)
		{
			box = new JCheckBox(card.getName());
			add(box);
		}
		setBorder(new TitledBorder (new EtchedBorder(), title));

	}

}

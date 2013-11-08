package clueGame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Guess extends JPanel {
	private String guessing;
	private JTextArea area;

	public Guess() {
		JLabel guess = new JLabel("Guess");
		guessing = " Blank";
		add(guess);
		area = new JTextArea(1, 15);
		updateDisplay();
		area.setEditable(false);
		add(area);
		setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
	}
	
	public void updateDisplay() {
		area.setText("");
	}

}

package clueGame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GuessResult extends JPanel {
	private String response;
	private JTextArea area;

	public GuessResult() {
		JLabel response = new JLabel("Response");
		add(response);
		area = new JTextArea(1, 12);
		updateDisplay();
		area.setEditable(false);
		add(area);
		
		setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
	}
	public void updateDisplay() {
		area.setText("");
	}

}

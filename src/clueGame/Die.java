package clueGame;

import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Die extends JPanel{
	private int result;
	private JTextArea area;
	Random r;

	public Die() {
		r = new Random();
		JLabel title = new JLabel("Roll");
		add(title);
		area = new JTextArea(1, 15);
		//updateDisplay();
		area.setEditable(false);
		add(area);
		
		setBorder(new TitledBorder (new EtchedBorder(), "Die"));
	}
	
	public void roll() {
		result = r.nextInt(6)+1;
		updateDisplay();
	}
	
	public void updateDisplay() {
		area.setText(Integer.toString(result));
	}

	public int getResult() {
		return result;
	}

}

package clueGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.*;

public class ControlGUI extends JPanel {
	private JTextField whoseTurn;

	private ClueGame game;
	private ArrayList<Player> players;
	private ArrayList<Card> cards;
	private Board board;
	private Die die;
	private Guess guess;
	private GuessResult result;
	private MakeaGuess mkGuess;
	// current player index is only -1 at the game start
	private int currentIndex = -1;
	Player currentPlayer;
	private JButton nextPlayer, accuse;
	
	public ControlGUI(ClueGame game) {
		this.game = game;
		board = game.getArea();
		this.players =game.getPlayers();
		die = new Die();
		guess = new Guess();
		result = new GuessResult();
		cards = game.getCards();
		
		mkGuess= new MakeaGuess(cards);
		
		setSize(750, 300);
		createLayout();
	}


	public void createLayout() {
		setLayout(new GridLayout(2,0));
		JPanel turnPanel = new JPanel(new GridLayout(2,1));
		JLabel label = new JLabel("                       Whose turn?");
		whoseTurn = new JTextField(20);
		whoseTurn.setEditable(false);
		turnPanel.add(label);
		turnPanel.add(whoseTurn);

		JPanel topPanel = new JPanel();
		nextPlayer = new JButton("Next Player");
		nextPlayer.addActionListener(new ButtonListener());
		accuse = new JButton("Make an Accusation");

		topPanel.setLayout(new GridLayout(1,3));
		topPanel.add(turnPanel);
		topPanel.add(nextPlayer);
		topPanel.add(accuse);
		add(topPanel);

		JPanel bottomPanel = new JPanel(new GridLayout(0,3));
		bottomPanel.add(die);
		bottomPanel.add(guess);	
		bottomPanel.add(result);	
		add(bottomPanel);
	}
	
	public void startMove() {
		if (currentIndex < players.size()-1)
			currentIndex++;
		else
			currentIndex = 0;
		if (currentIndex == game.humanIndex)
			game.setHumanMustFinish(true);
		currentPlayer = players.get(currentIndex);
		die.roll();
		updateTurn();
		int steps = die.getResult();
		int location = board.calcIndex(currentPlayer.getRow(), currentPlayer.getCol());
		Set<Integer> targets = board.getTargets(location, steps);
		currentPlayer.makeMove(targets, board.getCells(), board, cards);
		board.repaint();
	}

	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()== nextPlayer){
				if (game.isHumanMustFinish() && board.isHumanTurn()){
					JOptionPane.showMessageDialog(game, "You must make your move first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					startMove();
				}
			}
			else if(e.getSource()== accuse){
				if (game.isHumanMustFinish() && board.isHumanTurn()){
					mkGuess.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(game, "It's not your turn", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

		public void updateTurn() {
			whoseTurn.setText(players.get(currentIndex).getName());
		}
	}

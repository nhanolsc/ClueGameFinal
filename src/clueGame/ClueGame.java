package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import clueGame.Card.CardType;

public class ClueGame extends JFrame {
	private Board area;
	private Solution answer;
	private ArrayList<Player> players;
	public int humanIndex = 0;
	private ArrayList<Card> cards;
	private String playerFile;
	private String weaponFile;
	private String roomFile;
	
	private DetectiveNotes notes;
	private ControlGUI control;
	private DisplayCards displayCards;
	
	private boolean humanMustFinish = false;

	public ClueGame(String playerFile, String weaponFile, String roomFile) throws FileNotFoundException, BadConfigFormatException
	{
		this.playerFile = playerFile;
		this.weaponFile = weaponFile;
		this.roomFile = roomFile;
		players = new ArrayList<Player>(6);
		cards = new ArrayList<Card>(21);
		area = new Board("ClueLayout.csv", roomFile, players);
		area.loadLegend();
		area.loadBoard();
		area.calcAdjacencies();
		loadConfigFiles();
		deal();
		area.updatePlayers(players);
		
		control = new ControlGUI(this);
		displayCards = new DisplayCards(players.get(humanIndex).getMyCards());

		setTitle("Clue");
		createLayout();

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

	}

	public void deal()
	{
		Random rand = new Random();
		// array of cards that have been dealt
		boolean[] dealt = new boolean[21];
		int r = rand.nextInt(21);
		// picks some random cards for the solution
		int check = 0;
		String person, weapon, room;
		person = weapon = room = "";
		while(check < 3)
		{
			Card solution = cards.get(r);
			if(solution.getType() == CardType.PERSON && check ==0)
			{
				dealt[r] = true;
				person = cards.get(r).getName();
				check += 1;
			}
			else if(solution.getType() == CardType.WEAPON && check ==1)
			{
				dealt[r] = true;
				weapon = cards.get(r).getName();
				check += 1;
			}
			else if(solution.getType() == CardType.ROOM && check ==2)
			{
				dealt[r] = true;
				room = cards.get(r).getName();
				check += 1;
			}
			r = rand.nextInt(21);
		}
		answer = new Solution(person, weapon, room);

		// deals the rest of the cards randomly
		int playerNum;
		for(int i =0; i < cards.size()-3; i++)
		{
			while(dealt[r])
			{
				r = rand.nextInt(21); 
			}
			dealt[r] = true;
			playerNum = i%6;        
			players.get(playerNum).getMyCards().add(cards.get(r));
		}
	}

	// divided between player, weapon, and room cards
	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException
	{
		loadPlayers();
		loadWeapons();
		loadRooms();
	}

	// loads all the necessary information into the players list, then adds
	// the player names into the cards list
	public void loadPlayers() throws FileNotFoundException {
		FileReader r = new FileReader(playerFile);
		Scanner input = new Scanner(r);
		String[] line;
		String name;
		Color color;
		int row, col;
		while (input.hasNextLine()) {
			line = input.nextLine().split(",");
			name = line[0];
			color = convertColor(line[1]);
			row = Integer.parseInt(line[2]);
			col = Integer.parseInt(line[3]);
			// makes the first player human player
			if (players.size() == 0)
				players.add(new HumanPlayer(name, color, row, col));
			else
				players.add(new ComputerPlayer(name, color, row, col));
			cards.add(new Card(name, CardType.PERSON));        
		}
		input.close();
	}

	// gets the weapon names from file, adds to card list
	public void loadWeapons() throws FileNotFoundException {
		FileReader r = new FileReader(weaponFile);
		Scanner input = new Scanner(r);
		while (input.hasNextLine())
			cards.add(new Card(input.nextLine(), CardType.WEAPON));
		input.close();
	}

	// gets the room names from the legend file, while excluding the
	// walkway and closet
	public void loadRooms() throws FileNotFoundException {
		FileReader r = new FileReader(roomFile);
		Scanner input = new Scanner(r);
		String[] line;
		while (input.hasNextLine()) {
			line = input.nextLine().split(", ");
			if (!line[0].equalsIgnoreCase("X") && !line[0].equalsIgnoreCase("W")) {
				cards.add(new Card(line[1], CardType.ROOM));
			}
		}
		input.close();
	}

	public void selectAnswer()
	{

	}

	public Card handleSuggestion(String person, String weapon, Player accusingPerson)
	{
		RoomCell cell = (RoomCell) area.getBoardCellAt(accusingPerson.getRow(), accusingPerson.getCol());
		for(int i = 0; i < players.size(); i++)
		{
			Player test = players.get(i);
			Map<Character, String> legend = area.getRooms();
			String room;
			char key = cell.getInitial();
			room = legend.get(key);
			Card error = test.disproveSuggestion(person, room, weapon);
			if(error != null)
			{
				for(int j = 0; j < accusingPerson.getMyCards().size(); j++)
				{
					if(error.equals(accusingPerson.getMyCards().get(j)))
						return null;
				}
				return error;
			}
		}
		return null;
		//probably going to need to update seen card set in computer player
	}

	public boolean checkAccusation(Solution sol)
	{
		if (!(sol.person.equalsIgnoreCase(answer.person)))
			return false;
		else if (!(sol.weapon.equalsIgnoreCase(answer.weapon)))
			return false;
		else if (!(sol.room.equalsIgnoreCase(answer.room)))
			return false;
		else
			return true;
	}

	public Solution getAnswer() {
		return answer;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public Board getArea() {
		return area;
	}
	
	public boolean isHumanMustFinish() {
		return humanMustFinish;
	}

	public void setHumanMustFinish(boolean humanMustFinish) {
		this.humanMustFinish = humanMustFinish;
	}

	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			// imported the java.lang.reflect.Field class, hopefully that's right
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null); }
		catch (Exception e) {
			color = null; // Not defined }
		}
		return color;
	}

	// for testing purposes only
	public void setAnswer(Solution answer) {
		this.answer = answer;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	// end test methods

	public void createLayout()
	{
		add(area, BorderLayout.CENTER);
		add(control, BorderLayout.SOUTH);
		add(displayCards, BorderLayout.EAST);
		setSize(860, 890);
	}

	private JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotes());
		menu.add(createFileExitItem());
		return menu;
	}
	private JMenuItem createFileExitItem()
	{
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	private JMenuItem createDetectiveNotes()
	{
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				notes = new DetectiveNotes(cards);
				notes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	public static void main(String[] args) throws FileNotFoundException, BadConfigFormatException
	{
		ClueGame game = new ClueGame("PlayerCards.txt", "WeaponCards.txt", "legend.txt");
		//game.loadConfigFiles();
		//game.pack();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		JOptionPane.showMessageDialog(game, "You are Miss Scarlet, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
	}

}

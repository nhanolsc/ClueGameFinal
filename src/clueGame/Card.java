package clueGame;

public class Card {
	public String name;
	public enum CardType {ROOM, WEAPON, PERSON};
	CardType type;
	
	public Card()
	{
		name = "";
		type = null;
	}

	public Card(String name, CardType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}
	

	@Override
	public boolean equals(Object obj)
	{
		if(this.name.equalsIgnoreCase(( (Card) obj ).name) )
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	
}

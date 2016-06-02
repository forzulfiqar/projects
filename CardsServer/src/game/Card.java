
package game;

public class Card {

	private String suit;
	private String name;
	private int value;

	public Card(String suit, String name, int value) {
		this.suit = suit;
		this.name = name;
		this.value = value;
	}

	public String getSuit() {
		return suit;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
	
	public boolean isSameSuit(Card c)
	{
		boolean same = false;
		
		if (this.getSuit() == c.getSuit())
		{
			same = true;
		}
		
		return same;
	}
	
	public String toString() {
		return "<" + suit + " " + name + ">";
	}

}
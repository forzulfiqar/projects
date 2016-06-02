
package game;

import java.util.ArrayList;

public class Dealer extends GamePlayer {

	private Deck deck;
	private Table table;

	public Dealer() {
		super("Dealer", "", 0, false, "", 0);
		deck = new Deck();
		table = new Table();
	}

	public void shuffleCards() {

		deck.shuffle();
	}

	public Card dealCard() {
		return deck.dealCard();
	}

	public int nextMove() {
		return 0;
	}
	
	public void appendCards(ArrayList<Card> cards)
    {  
        for(Card c: cards)
        {    
        	this.table.addCardToTable(c);
        }        
    }
	public void appendCard(Card card) {
		this.table.addCardToTable(card);
	}

	public void showAllCards() {
		table.showAllCards();
	}
	
	public void showOneCardOnTable(int x) {
		table.showHiddenCardsFirst(x);
	}
	
	public void showTwoCardOnTable(int x, int y) {
		table.showHiddenCardsSecond(x, y);
	}
	
	public int getTableLength() 
	{
		return table.getNumberOnTable();
	}
	
	public int getDeckLength()
	{
		return deck.deckLength();
	}
	
	public void replaceCardInTable(int i, Card c)
    {
    	table.replaceCardInTable(i, c);
    }
	
	public void removeCardInTable(int i)
	{
		table.removeCardInTable(i);	
	}
		
	public void clearCardInTable()
	{
		table.clearCardInTable();
	}
	
	public Card getCardChosen(int x)
	{
		return table.returnCardChosen(x);
	}

}
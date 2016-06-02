
package game;

import java.util.*;

public class Table {
	private ArrayList<Card> table;
	
	public Table()
    {
		table = new ArrayList<Card>();
        //table.clear();               
    }
    
	public Card dealCard()
    {
        return table.remove(0);
    }
	
	public ArrayList<Card> getCardsOnTable() 
	{
		return table;
	}
	
	public int getNumberOnTable()
	{
		return table.size();
	}
    
    public void addCardToTable(Card c)
    {
    	table.add(c);
    }
    
    public void replaceCardInTable(int i, Card c)
    {
    	table.set(i-1, c);
    }
    
    public void removeCardInTable(int i)
    {
    	table.remove(i-1);
    }
    
    public void clearCardInTable()
    {
    	table.clear();
    }
    
    public void showAllCards()
    {
    	int i = 1;
    	for(Card c : table)
    	{
			if(i == 6 || i == 11)
			{
				System.out.println();
			}
			System.out.print("<" + c.getName() + " " + c.getSuit() + ">" + "   ");
			i++;
    	}
    	System.out.println();
    }
    
    public void showHiddenCardsFirst(int x)
    {
    	int i = 1;
    	for(Card c : table)
    	{
			if(i == 6 || i == 11)
			{
				System.out.println();
			}
			if(i == x)
			{
				System.out.print("<" + c.getName() + " " + c.getSuit() + ">" + "   ");
			}
			else
			{
				System.out.print("<Card " + i + ">" + "   ");
			}
			i++;
    	}
    	System.out.println();
    }
    
    public void showHiddenCardsSecond(int x, int y)
    {
    	int i = 1;
    	for(Card c : table)
    	{
			if(i == 6 || i == 11)
			{
				System.out.println();
			}
			if(i == x)
			{
				System.out.print("<" + c.getName() + " " + c.getSuit() + ">" + "   ");
			}
			else if(i == y)
			{
				System.out.print("<" + c.getName() + " " + c.getSuit() + ">" + "   ");
			}
			else
			{
				System.out.print("<Card " + i + ">" + "   ");
			}
			i++;
    	}
    	System.out.println();
    }
    
    public void appendCards(ArrayList<Card> cards)
    {  
        for(Card c: cards)
        {
            this.table.add(c);
        }
    }
    
    public Card returnCardChosen(int x)
    {
    	return table.get(x);
    	/*
    	int i = 1;
    	for(Card c : table)
    	{
			if(i == x)
			{
				return c;
			}
			i++;
    	}
		return null;
		*/    	
    }

}


package game;

import java.util.*;

public class Deck {
	private ArrayList<Card> deckOfCards;
	
	public Deck()
    {
        
		deckOfCards = new ArrayList<Card>();
        //*        
        String [] suits = {"Heart","Diamond","Spade","Club"};
        
        for(int n=0;n<suits.length;n++)
        {
            String suit = suits[n];
            Card card;
            card = new Card(suit,"Ace",1);
            deckOfCards.add(card);
            for(int i=2;i<=10;i++)
            {
                card = new Card(suit,i+"",i);
                deckOfCards.add(card);
            }
        
            card = new Card(suit,"Jack",11);
            deckOfCards.add(card);
         
            card = new Card(suit,"Queen",12);
            deckOfCards.add(card);
      
            card = new Card(suit,"King",13);
            deckOfCards.add(card);
        }
        //*/
        /*
        Card card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13, card14, card15, card16, card17, card18, card19, card20, card21, card22, card23, card24, card25, card26 ;
        card1 = new Card("Spade","Jack of",11);
        card2 = new Card("Spade","Jack of",11);
        card3 = new Card("Spade","Jack of",11);
        card4 = new Card("Spade","Jack of",11);
        card5 = new Card("Spade","Jack of",11);
        card6 = new Card("Spade","Jack of",11);
        card7 = new Card("Spade","Jack of",11);
        card8 = new Card("Spade","Jack of",11);
        card9 = new Card("Spade","Jack of",11);
        card10 = new Card("Spade","Jack of",11);
        card11 = new Card("Spade","Jack of",11);
        card12 = new Card("Spade","Jack of",11);
        card13 = new Card("Spade","Jack of",11);
        card14 = new Card("Heart","Jack of",11);
        card15 = new Card("Heart","Jack of",11);
        card16 = new Card("Heart","Jack of",11);
        card17 = new Card("Heart","Jack of",11);
        card18 = new Card("Heart","Jack of",11);
        card19 = new Card("Heart","Jack of",11);
        card20 = new Card("Heart","Jack of",11);
        card21 = new Card("Heart","Jack of",11);
        card22 = new Card("Heart","Jack of",11);
        card23 = new Card("Heart","Jack of",11);
        card24 = new Card("Heart","Jack of",11);
        card25 = new Card("Heart","Jack of",11);
        card26 = new Card("Heart","Jack of",11);
        
        deckOfCards.add(card1);
        deckOfCards.add(card2);
        deckOfCards.add(card3);
        deckOfCards.add(card4);
        deckOfCards.add(card5);
        deckOfCards.add(card6);
        deckOfCards.add(card7);
        deckOfCards.add(card8);
        deckOfCards.add(card9);
        deckOfCards.add(card10);
        deckOfCards.add(card11);
        deckOfCards.add(card12);
        deckOfCards.add(card13);
        deckOfCards.add(card14);
        deckOfCards.add(card15);
        deckOfCards.add(card16);
        deckOfCards.add(card17);
        deckOfCards.add(card18);
        deckOfCards.add(card19);
        deckOfCards.add(card20);
        deckOfCards.add(card21);
        deckOfCards.add(card22);
        deckOfCards.add(card23);
        deckOfCards.add(card24);
        deckOfCards.add(card25);
        deckOfCards.add(card26);
        
        */
    }
	
	public void showCards()
    {   
       for(Card card: deckOfCards){
    	   System.out.println(card);
       }
    }
    
    public Card dealCard()
    {
        return deckOfCards.remove(0);
    }
    
    public void appendCard(Card c)
    {
    	deckOfCards.add(c);
        
    }   
    
    public int deckLength()
    {
    	return deckOfCards.size();
    }
   
    public void shuffle(){
    	shuffle(deckOfCards);
    }
    
    public void shuffle(ArrayList<Card> cards)
    {
        
        Random r = new Random();
        for(int i=0;i<cards.size()*1000;i++)
        {
            int a = r.nextInt(cards.size());
            int b = r.nextInt(cards.size());
            
            Card cardA = cards.get(a);
            Card cardB = cards.get(b);
            
            cards.set(a, cardB);
            cards.set(b, cardA);
            
        }
        
    }
           
    public ArrayList<Card> getDeckOfCards() {
		return deckOfCards;
	}

	public void setDeckOfCards(ArrayList<Card> deckOfCards) {
		this.deckOfCards = deckOfCards;
	}

	public static void main(String []args)
    {
        Deck deck = new Deck();
        deck.shuffle();
        
    }
}

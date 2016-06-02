
package game;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GamePlayer extends Player implements GamePlayerInterface {
	
	private String lastLogin;
	private ArrayList<Card> cardsOnHand;
	private boolean isHuman;
	private int score;
	private long dateDiff;
	private int difficulty;
	
	public GamePlayer(String loginName, String hashedPassword, int wins, boolean isHuman, String lastLogin, int score) {

		super(loginName, hashedPassword, wins);
		this.isHuman = isHuman;
		this.score = score;
		this.lastLogin = lastLogin;
		cardsOnHand = new ArrayList<Card>();
	}
	
	public GamePlayer(String loginName, String hashedPassword, int wins, String lastLogin) {

		super(loginName, hashedPassword, wins);
		this.isHuman = true;
		cardsOnHand = new ArrayList<Card>();
		
	}
	
	public void setLastLogin(String lastLogin)
	{
		this.lastLogin = lastLogin;
	}
	
	public String getLastLogin()
	{
		return lastLogin;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public void addScore(int score)
	{
		this.score += score;
	}
	
	public int getScore()
	{
		return score;
	}
		
	public void setLastLoginToNow()
	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currDate = new Date();
		String lastLoginString = dateFormat.format(currDate);
		
		this.lastLogin = lastLoginString;
	}
	
	public void setDateDiff()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currDate = new Date();
		Date startDate = null;
		Date endDate = null;
		try {
			Date lastLoginDate = dateFormat.parse(lastLogin);
			startDate = lastLoginDate;
		} catch (ParseException e) {
			System.out.println("Error parsing last login.");
			e.printStackTrace();
		}
		
		endDate = currDate;
		
		long duration  = (endDate.getTime() - startDate.getTime());
		
		this.dateDiff = duration / (1000 * 60 * 60 * 24);
	}
	
	public long getDiffDate()
	{
		setDateDiff();
		return this.dateDiff;
	}
	
	public void addCard(Card card) 
	{
		cardsOnHand.add(card);
	}
	
	public ArrayList<Card> getCardsOnHand() 
	{

		return cardsOnHand;

	}
	
	public int getNumberOfCardsOnHand()
	{
		
		return cardsOnHand.size();
	}

	public void clearCardsOnHand() 
	{
		cardsOnHand.clear();
	}
	
	public int nextMove()
	{
		return  Keyboard.readInt("Choose the card number to flip: ");
    }
	
	public int enterNextChoice()
	{
		return  Keyboard.readInt("Enter choice: ");
    }
	
	public boolean isHuman()
	{
		return isHuman;
	}
	
	public static void main(String []args)
    {
        GamePlayer p = new GamePlayer("tester", "", 0, true, "", 0);
        p.setLastLoginToNow();
    }
	
	public int getDifficulty()
	{
		return this.difficulty;
	}
	
	public void addWins(int i){
		
	}
}

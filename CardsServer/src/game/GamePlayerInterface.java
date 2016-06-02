
package game;

import java.util.ArrayList;

public interface GamePlayerInterface {
	public void addCard(Card card);
	public ArrayList<Card> getCardsOnHand();
	public void clearCardsOnHand();
	public int nextMove();
	public String getLoginName();
	public boolean isHuman();
	public int getNumberOfCardsOnHand();
	public void addScore(int score);
	public void setLastLogin(String lastLogin);
	public String getLastLogin();
	public void setScore(int score);
	public int getScore();
	public void setLastLoginToNow();
	public void setWins(int wins);
	public void addWins(int i);
	public int getWins();
	public int enterNextChoice();
	public int getDifficulty();
	
}

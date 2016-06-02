
package game;

import java.net.Socket;

public class Player extends User {
	private int wins = 0;
	private CardsClientListener cardsClientListenerObj;
	private int playerNumber;
		
	public Player(String loginName, String hashedPassword, int wins){
		super(loginName,hashedPassword);
		this.wins = wins;
	}
	
	public Player(String loginName, String hashedPassword, CardsClientListener cardsClientListenerObj, int playerNumber){
		super(loginName,hashedPassword);
		this.cardsClientListenerObj = cardsClientListenerObj;
		this.playerNumber = playerNumber;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public CardsClientListener getCardsClientListenerObj() {
		return cardsClientListenerObj;
	}

	public void setCardsClientListenerObj(CardsClientListener cardsClientListenerObj) {
		this.cardsClientListenerObj = cardsClientListenerObj;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	
}

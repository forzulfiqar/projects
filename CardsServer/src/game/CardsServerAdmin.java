package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CardsServerAdmin {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter username");

		String userName = scan.nextLine();

		System.out.println("Please enter password");

		String password = scan.nextLine();

		System.out.println("Please enter port number to user for server");

		int portNumber = scan.nextInt();

		System.out.println("portNumber: " + portNumber);

		System.out.println("Please enter number of players");

		int nubmerOfPlayers = scan.nextInt();

		Hashtable<String, Player> players = new Hashtable<String, Player>();
		
		try {
			ServerSocket cardsServerObj = new ServerSocket(portNumber);
			int currentNumberOfPlayers = 0;
			Socket playerSocket = null;
			Player player = null;

			System.out.println("The Cards Game Server is running.");

			while (currentNumberOfPlayers < nubmerOfPlayers) {
				System.out.println("Total players required for the game: " + nubmerOfPlayers );
				System.out.println("Current number of players: " + currentNumberOfPlayers);
				System.out.println("Current players");
				
				Enumeration playersKeys = players.keys();
				while(playersKeys.hasMoreElements()) {
					System.out.println(players.get(playersKeys.nextElement()).getLoginName());
				}
				
				System.out.println("Waiting for all players to join");
												
				currentNumberOfPlayers++;
				playerSocket = cardsServerObj.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
				PrintWriter out = new PrintWriter(playerSocket.getOutputStream(), true);

				CardsClientListener CardsClientListenerObj = new CardsClientListener(in, out);
				//CardsClientListenerObj.start();

				//Read username, password sent by client
				String clientData = in.readLine();

				StringTokenizer st2 = new StringTokenizer(clientData, "~");
				String clientUserName = "" + st2.nextElement();
				String clientPassword = "" + st2.nextElement();
				
				player = new Player(clientUserName, clientPassword, CardsClientListenerObj, currentNumberOfPlayers);
				players.put(currentNumberOfPlayers+"", player);

				//Send playerNumber to client for further communication
				out.println(currentNumberOfPlayers);
				
				System.out.println("Player number " + currentNumberOfPlayers + " joined the game");
				
			}
			
			System.out.println("The game begins");
						
			Dealer dealer = new Dealer();
			dealer.shuffleCards();
			
			int cardsInTable = 15;
			for(int i=0;i<cardsInTable;i++){
				dealer.appendCard(dealer.dealCard());								
		    }
						
			int currentPlayerNumberPlaying = 0;
			Player currentPlayer = null;
			String enterCardNumbersStatement = "inputcardnumbers:Please enter number of first card between 1 and 15";
			//String enterSecondCardStatement = "input2:Please enter number of second card between 1 and 15";
			String cardNumbers = null;
			String firstCardNumber;
			String secondCardNumber;
			int timeToSleep = 3000;
			while(true) {
				currentPlayerNumberPlaying++;
				
				if(currentPlayerNumberPlaying>nubmerOfPlayers) {
					//Send current selection of player to all players
					Enumeration keys =  players.keys();
					Player pl = null;
					StringBuilder messageToSend = new StringBuilder("All players have taken turns. Game is over. Final Score:");
					while(keys.hasMoreElements()) {
						pl = players.get(keys.nextElement());
						messageToSend.append(", Player " + pl.getPlayerNumber() + " Score: " + pl.getWins());
						//pl.getCardsClientListenerObj().getOut().println("over:" + messageToSend);
					}
					
					System.out.println(messageToSend.toString());
					
					keys =  players.keys();
					pl = null;					
					while(keys.hasMoreElements()) {
						pl = players.get(keys.nextElement());						
						pl.getCardsClientListenerObj().getOut().println("over:" + messageToSend.toString());
					}
					
					Thread.sleep(timeToSleep);
					
					System.exit(0);
					
				}
				
				currentPlayer = players.get(currentPlayerNumberPlaying+"");
				Card firstCard = null;
				Card secondCard = null;
				
				String messageToSend = "waitingplayerinput:Waiting for input from player " + currentPlayer.getPlayerNumber();
				Enumeration keys =  players.keys();
				Player pl = null;					
				while(keys.hasMoreElements()) {
					pl = players.get(keys.nextElement());						
					pl.getCardsClientListenerObj().getOut().println(messageToSend);
				}		
				
				while(true) {	
					
					currentPlayer.getCardsClientListenerObj().getOut().println(enterCardNumbersStatement);
					cardNumbers = currentPlayer.getCardsClientListenerObj().getIn().readLine();
					StringTokenizer st2 = new StringTokenizer(cardNumbers, "~");
					firstCardNumber = "" + st2.nextElement();
					secondCardNumber = "" + st2.nextElement();
					
					firstCard = dealer.getCardChosen(Integer.parseInt(firstCardNumber));
					secondCard = dealer.getCardChosen(Integer.parseInt(secondCardNumber));
					//check if match
					if(firstCard.isSameSuit(secondCard)) {						
						currentPlayer.getCardsClientListenerObj().getOut().println("matched:Cards matched, continue please");
						currentPlayer.setWins(currentPlayer.getWins()+1);
						
						if(dealer.getTableLength()>0) {
							dealer.replaceCardInTable(Integer.parseInt(firstCardNumber), dealer.dealCard());
						} else {
							currentPlayer.getCardsClientListenerObj().getOut().println("over:Cards finished in the deck, game over");
							System.exit(0);
						}
						if(dealer.getTableLength()>0) {
							dealer.replaceCardInTable(Integer.parseInt(secondCardNumber), dealer.dealCard());
						} else {
							currentPlayer.getCardsClientListenerObj().getOut().println("over:Cards finished in the deck, game over");
							System.exit(0);
						}
						
						//Send current selection of player to all players
						keys =  players.keys();
						pl = null;
						messageToSend = "Player having username " + currentPlayer.getLoginName() + " , player number " + currentPlayer.getPlayerNumber() + " selected " + firstCard.getName() + " of " + firstCard.getSuit() + " and " +  secondCard.getName() + " of "+ secondCard.getSuit() +" which is a match";
						while(keys.hasMoreElements()) {
							pl = players.get(keys.nextElement());
							pl.getCardsClientListenerObj().getOut().println("matchallplayersmessage:" + messageToSend);
						}
						System.out.println(messageToSend);
						
						continue;
					} else {
						currentPlayer.getCardsClientListenerObj().getOut().println("nomatch:Cards did not match, your turn is over");						
						//Send current selection of player to all players
						keys =  players.keys();
						pl = null;
						messageToSend = "Player having username" + currentPlayer.getLoginName() + " and player number " + currentPlayer.getPlayerNumber() + " selected " + firstCard.getName() + " of " + firstCard.getSuit() + " and " +  secondCard.getName() + " of "+ secondCard.getSuit() +" which is not a match";
						while(keys.hasMoreElements()) {
							pl = players.get(keys.nextElement());
							pl.getCardsClientListenerObj().getOut().println("nomatchallplayersmessage:" + messageToSend);
						}
						System.out.println(messageToSend);
						break;
					}					
				}
			}						
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

	}
}

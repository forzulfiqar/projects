package game;

import java.io.BufferedReader;
import java.io.FileInputStream;
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

		// System.out.println("Please enter username");

		// String userName = scan.nextLine();
		final String ADMIN_USER_NAME = "admin";

		System.out.println("Please enter port number for server");

		int portNumber = Integer.parseInt(scan.nextLine());

		System.out.println("portNumber: " + portNumber);

		System.out.println("Please enter number of players");

		int nubmerOfPlayers = Integer.parseInt(scan.nextLine());

		System.out.println("Please enter password for " + ADMIN_USER_NAME);

		String adminPassword = scan.nextLine();
		String adminCredentialsFile = "resources/admin.dat";
		String adminPasswordInFile = null;

		Hashtable<String, String> credentialsTable = CredentialsReaderHelper
				.readFileForCredentials(adminCredentialsFile);
		if (credentialsTable != null && credentialsTable.get(ADMIN_USER_NAME) != null) {
			adminPasswordInFile = credentialsTable.get(ADMIN_USER_NAME);
		} else {
			System.out.println("admin password does not exist in " + adminCredentialsFile + " file");
		}

		System.out.println("adminPasswordInFile: " + adminPasswordInFile);

		String hashOfAdminPassword = null;
		/*
		 * while (true) { hashOfAdminPassword = Utility.getHash(adminPassword);
		 * System.out.println("Hash of entered password: " +
		 * hashOfAdminPassword);
		 * 
		 * if (!hashOfAdminPassword.equalsIgnoreCase(adminPasswordInFile)) {
		 * System.out .println(ADMIN_USER_NAME +
		 * " password did not match in our records, please re-enter password");
		 * adminPassword = scan.nextLine(); continue; } else { break; } }
		 */

		Hashtable<String, Player> players = new Hashtable<String, Player>();

		try {
			ServerSocket cardsServerObj = new ServerSocket(portNumber);
			int currentNumberOfPlayers = 0;
			Socket playerSocket = null;
			Player player = null;

			System.out.println("The Cards Game Server is running.");

			while (currentNumberOfPlayers < nubmerOfPlayers) {

				System.out.println("Current players");

				Enumeration playersKeys = players.keys();
				while (playersKeys.hasMoreElements()) {
					System.out.println(players.get(playersKeys.nextElement()).getLoginName());
				}

				String messageToSend = "Waiting for all players to join. Current Number of players: " + players.size()
						+ ", Total required: " + nubmerOfPlayers;
				// Message for server
				System.out.println(messageToSend);
				messageToSend = "waitingallplayerstojoin: " + messageToSend;

				Enumeration keys = players.keys();
				Player pl = null;
				while (keys.hasMoreElements()) {
					pl = players.get(keys.nextElement());
					pl.sendMessage(messageToSend);
				}

				playerSocket = cardsServerObj.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
				PrintWriter out = new PrintWriter(playerSocket.getOutputStream(), true);

				CardsClientListener CardsClientListenerObj = new CardsClientListener(in, out);
				// CardsClientListenerObj.start();

				String clientUserName = null;
				String clientPassword = null;
				String playersCredentialsFile = "resources/players.dat";
				Hashtable<String, String> clientCredentialsTable = CredentialsReaderHelper
						.readFileForCredentials(playersCredentialsFile);
				String userPasswordInFile = null;
				String hashOfUserPassword = null;

				clientUserName = in.readLine();
				if (clientCredentialsTable != null && clientCredentialsTable.get(clientUserName) != null) {
					userPasswordInFile = clientCredentialsTable.get(clientUserName);
					out.println("userexists: userName =  " + clientUserName + " exists in " + playersCredentialsFile
							+ " file");
				} else {
					System.out.println(
							"userName = " + clientUserName + " tried to login. This username does not exist in " + playersCredentialsFile + " file");
					out.println("userdoesnotexist: userName =  " + clientUserName + " does not exist in "
							+ playersCredentialsFile + " file");
					continue;
				}

				boolean loginOK = false;
				int passwordAttemts = 0;
				while (passwordAttemts < 3) {
					clientPassword = in.readLine();
					hashOfUserPassword = Utility.getHash(clientPassword);
					System.out.println("Hash of entered user password: " + hashOfUserPassword);

					if (!hashOfUserPassword.equalsIgnoreCase(userPasswordInFile)) {
						passwordAttemts++;
						if (passwordAttemts >= 3) {
							out.println("allpasswordattemptsfailed: ");
						}
						if (passwordAttemts < 3) {
							System.out.println("Password for user " + clientUserName + " did not match in records");
							out.println("passworddoesnotmatch: Password for user " + clientUserName
									+ " did not match in our records, please re-enter password");
							Thread.sleep(1000);
						}

					} else {
						out.println("passwordmatched: " + (currentNumberOfPlayers + 1));
						loginOK = true;
						break;
					}
				}

				if (!loginOK) {
					System.out.println("User " + clientUserName + " did not enter correct password in three attempts");
					continue;
				}

				currentNumberOfPlayers++;
				player = new Player(clientUserName, clientPassword, CardsClientListenerObj, currentNumberOfPlayers);
				players.put(currentNumberOfPlayers + "", player);

				messageToSend = "Player number " + currentNumberOfPlayers + " with username of " + clientUserName
						+ " joined the game";
				// Message for server
				System.out.println(messageToSend);
				messageToSend = "newplayerjoined: " + messageToSend;

				keys = players.keys();
				pl = null;
				while (keys.hasMoreElements()) {
					pl = players.get(keys.nextElement());
					pl.sendMessage(messageToSend);
				}

			}

			System.out.println("The game begins");

			Dealer dealer = new Dealer();
			dealer.shuffleCards();

			int cardsInTable = 15;
			for (int i = 0; i < cardsInTable; i++) {
				dealer.appendCard(dealer.dealCard());
			}

			int currentPlayerNumberPlaying = 0;
			Player currentPlayer = null;
			String enterCardNumbersStatement = "inputcardnumbers:Please enter number of first card between 1 and 15";
			// String enterSecondCardStatement = "input2:Please enter number of
			// second card between 1 and 15";
			String cardNumbers = null;
			String firstCardNumber;
			String secondCardNumber;
			int timeToSleep = 1000;
			while (true) {
				currentPlayerNumberPlaying++;

				if (currentPlayerNumberPlaying > nubmerOfPlayers) {
					// Send current selection of player to all players
					Enumeration keys = players.keys();
					Player pl = null;
					StringBuilder messageToSend = new StringBuilder(
							"All players have taken turns. Game is over. Final Score:");
					while (keys.hasMoreElements()) {
						pl = players.get(keys.nextElement());
						messageToSend.append(", Player " + pl.getPlayerNumber() + " Score: " + pl.getWins());
					}

					System.out.println(messageToSend.toString());

					keys = players.keys();
					pl = null;
					while (keys.hasMoreElements()) {
						pl = players.get(keys.nextElement());
						pl.sendMessage("over:" + messageToSend.toString());
					}

					// Give some time for clients to consume the messages
					Thread.sleep(timeToSleep);

					System.exit(0);

				}

				currentPlayer = players.get(currentPlayerNumberPlaying + "");
				Card firstCard = null;
				Card secondCard = null;

				String messageToSend = "waitingplayerinput:Waiting for input from player "
						+ currentPlayer.getPlayerNumber();

				System.out.println(messageToSend);

				Enumeration keys = players.keys();
				Player pl = null;
				while (keys.hasMoreElements()) {
					pl = players.get(keys.nextElement());
					pl.sendMessage(messageToSend);
				}

				while (true) {
					currentPlayer.sendMessage(enterCardNumbersStatement);
					cardNumbers = currentPlayer.readMessage();
					StringTokenizer st2 = new StringTokenizer(cardNumbers, "~");
					firstCardNumber = "" + st2.nextElement();
					secondCardNumber = "" + st2.nextElement();

					firstCard = dealer.getCardChosen(Integer.parseInt(firstCardNumber));
					secondCard = dealer.getCardChosen(Integer.parseInt(secondCardNumber));
					// check if match
					if (firstCard.isSameSuit(secondCard)) {
						currentPlayer.sendMessage("matched:Cards matched, continue please");
						currentPlayer.setWins(currentPlayer.getWins() + 1);

						if (dealer.getTableLength() > 0) {
							dealer.replaceCardInTable(Integer.parseInt(firstCardNumber), dealer.dealCard());
						} else {
							currentPlayer.sendMessage("over:Cards finished in the deck, game over");
							System.exit(0);
						}
						if (dealer.getTableLength() > 0) {
							dealer.replaceCardInTable(Integer.parseInt(secondCardNumber), dealer.dealCard());
						} else {
							currentPlayer.sendMessage("over:Cards finished in the deck, game over");
							System.exit(0);
						}

						// Send current selection of player to all players
						keys = players.keys();
						pl = null;
						messageToSend = "Player having username " + currentPlayer.getLoginName()
								+ " , and player number = " + currentPlayer.getPlayerNumber() + " selected "
								+ firstCard.getName() + " of " + firstCard.getSuit() + " and " + secondCard.getName()
								+ " of " + secondCard.getSuit() + " which is a match";
						while (keys.hasMoreElements()) {
							pl = players.get(keys.nextElement());
							pl.sendMessage("matchallplayersmessage:" + messageToSend);
						}
						System.out.println(messageToSend);

						continue;
					} else {
						currentPlayer.sendMessage("nomatch:Cards did not match, your turn is over");
						// Send current selection of player to all players
						keys = players.keys();
						pl = null;
						messageToSend = "Player having username " + currentPlayer.getLoginName()
								+ " and player number = " + currentPlayer.getPlayerNumber() + " selected "
								+ firstCard.getName() + " of " + firstCard.getSuit() + " and " + secondCard.getName()
								+ " of " + secondCard.getSuit() + " which is not a match";
						while (keys.hasMoreElements()) {
							pl = players.get(keys.nextElement());
							pl.sendMessage("nomatchallplayersmessage:" + messageToSend);
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

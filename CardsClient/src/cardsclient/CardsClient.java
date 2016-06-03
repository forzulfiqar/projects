package cardsclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Scanner;

public class CardsClient {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter IP address of server");

		String ipAddress = scan.nextLine();

		System.out.println("Please enter port number");

		int portNumber = Integer.parseInt(scan.nextLine());

		System.out.println("Please enter username");

		String userName = scan.nextLine();

		System.out.println("Please enter password");

		String password = scan.nextLine();

		
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			Socket socket = new Socket(ipAddress, portNumber);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			// Send username, password to server
			out.println(userName);
			String serverMessage = null;
			serverMessage = in.readLine();
						
			if (serverMessage.contains("userexists:")) {
				System.out.println(serverMessage.substring(serverMessage.indexOf(':') + 1));
			}
			if (serverMessage.contains("userdoesnotexist:")) {
				System.out.println(serverMessage.substring(serverMessage.indexOf(':') + 1));
				System.exit(0);
			}
			
			//Three password attempts
			while(true) {
				out.println(password);	
				serverMessage = in.readLine();
				
				if (serverMessage.contains("allpasswordattemptsfailed:")) {
					System.out.println("You failed all allowed password attempts");
					System.exit(0);
				}
				
				if (serverMessage.contains("passworddoesnotmatch:")) {
					System.out.println(serverMessage.substring(serverMessage.indexOf(':') + 1));
					password = scan.nextLine();
					continue;
				}				
				
				
				if (serverMessage.contains("passwordmatched:")) {
					System.out.println("You have joined the game successfuly. Your player number is " + serverMessage.substring(serverMessage.indexOf(':') + 1)
							+ ". Please wait for next message");
					break;
				}
				
			}
			
			String response = null;
			String firstCardNumber = null;
			String secondCardNumber = null;
			int timeToSleep = 1000;
			while (true) {
				try {
					response = in.readLine();

					if (response.contains("waitingallplayerstojoin:")) {
						System.out.println(response.substring(response.indexOf(':') + 1));
						continue;
					}

					if (response.contains("newplayerjoined:")) {
						System.out.println(response.substring(response.indexOf(':') + 1));
						continue;
					}

					if (response.contains("waitingplayerinput:")) {
						System.out.println(response.substring(response.indexOf(':') + 1));
						continue;
					}

					if (response.contains("inputcardnumbers:")) {
						System.out.println("Please enter first card number between 1 and 15");
						firstCardNumber = scan.nextLine();
						// Thread.sleep(1000);
						System.out.println("Please enter second card number between 1 and 15");
						secondCardNumber = scan.nextLine();
						// Thread.sleep(1000);
						out.println(firstCardNumber + "~" + secondCardNumber);
						// Thread.sleep(timeToSleep);
						continue;
					}
					if (response.contains("over:")) {
						System.out.println(response.substring(response.indexOf(':') + 1));
						System.exit(0);
					}
					if (response.contains("matched:")) {
						System.out.println(response.substring(response.indexOf(':') + 1));
						continue;
					}
					if (response.contains("nomatch:")) {
						System.out.println(response.substring(response.indexOf(':') + 1));
						continue;
					}
					if (response.contains("matchallplayersmessage:")) {
						System.out.println(response.substring(response.indexOf(':') + 1));
						continue;
					}
					if (response.contains("nomatchallplayersmessage::")) {
						System.out.println(response.substring(response.indexOf(':') + 1));
						continue;
					}

					// Thread.sleep(5000);
				} catch (IOException ex) {
					response = "Error: " + ex;
				}
			}

		} catch (Exception e) {
			System.out.println("Exception client" + e);
		}

	}

}

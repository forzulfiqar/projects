package cardsclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CardsClient {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter username");
		
		String userName =  scan.nextLine();
		
		System.out.println("Please enter password");
		
		String password =  scan.nextLine();
		
		System.out.println("Please enter IP address of server");

	    String ipAddress = scan.nextLine();
	    
	    System.out.println("Please enter port number");

	    int portNumber = scan.nextInt();	    
	    	    
	    BufferedReader in = null;
	    PrintWriter out = null;
	    try {
	    Socket socket = new Socket(ipAddress, portNumber);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        
        //Send username, password to server
        out.println(userName + "~" + password);
                
        //Read playerNumber
        String playerNumberString = in.readLine();        
        System.out.println("Player Number: " + playerNumberString);
        
        String response = null;       
        String firstCardNumber = null;
        String secondCardNumber = null;
        int timeToSleep = 1000;
        while(true) {
        	try {
        		response = in.readLine(); 
        		
        		if(response.contains("waitingplayerinput:")) {
        			System.out.println(response.substring(response.indexOf(':')+1));
        			continue;
        		}
        		
        		
        		if(response.contains("inputcardnumbers:")) {
        			System.out.println("Please enter first card number");
        			firstCardNumber =  scan.nextLine();
        			Thread.sleep(1000);
        			System.out.println("Please enter second card number");
        			secondCardNumber =  scan.nextLine();
        			Thread.sleep(1000);
        			out.println(firstCardNumber+"~"+secondCardNumber); 
        			Thread.sleep(timeToSleep);
        			continue;
        		}        		
        		if(response.contains("over:")) {
        			System.out.println(response.substring(response.indexOf(':')+1));
        			System.exit(0);
        		}
        		if(response.contains("matched:")) {
        			System.out.println(response.substring(response.indexOf(':')+1));
        			continue;
        		}
        		if(response.contains("nomatch:")) {
        			System.out.println(response.substring(response.indexOf(':')+1));
        			continue;
        		}
        		if(response.contains("matchallplayersmessage:")) {
        			System.out.println(response.substring(response.indexOf(':')+1));
        			continue;
        		}
        		if(response.contains("nomatchallplayersmessage::")) {
        			System.out.println(response.substring(response.indexOf(':')+1));
        			continue;
        		}        		
                
                //Thread.sleep(5000);                
            } catch (IOException ex) {
                   response = "Error: " + ex;
            }
        }
                
	    } catch(Exception e) {
	    	System.out.println("Exception client" + e);
	    }

	}

}

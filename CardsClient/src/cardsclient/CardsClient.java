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
		
		String userName =  scan.nextLine();
		
		System.out.println("Please enter password");
		
		String password =  scan.nextLine();
			    	    
	    String playersCredentialsFile = "resources/players.dat";
		String userPasswordInFile = null;
		
		Hashtable<String, String> credentialsTable = CredentialsReaderHelper.readFileForCredentials(playersCredentialsFile);
		if(credentialsTable!=null && credentialsTable.get(userName)!=null) {
			userPasswordInFile = credentialsTable.get(userName);
		} else {
			System.out.println("Password for user " + userName + " does not exist in " + playersCredentialsFile + " file");
		}
		
		System.out.println("userPasswordInFile: " + userPasswordInFile);
		
		String hashOfUserPassword = null;
		
		/*
		while(true) {
			hashOfUserPassword = Utility.getHash(password);
			System.out.println("Hash of entered password: " + hashOfUserPassword);
			
			if(!hashOfUserPassword.equalsIgnoreCase(userPasswordInFile)) {
				System.out.println("Password for user "+userName + " did not match in our records, please re-enter password");
				password = scan.nextLine();
				continue;
			} else {
				break;
			}
		}
		*/
		
	    	    
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
        System.out.println("You have joined the game successfuly. Your player number is " + playerNumberString + ". Please wait for next message");
        
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
        			//Thread.sleep(1000);
        			System.out.println("Please enter second card number");
        			secondCardNumber =  scan.nextLine();
        			//Thread.sleep(1000);
        			out.println(firstCardNumber+"~"+secondCardNumber); 
        			//Thread.sleep(timeToSleep);
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

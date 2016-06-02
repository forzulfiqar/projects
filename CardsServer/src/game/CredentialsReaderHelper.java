package game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class CredentialsReaderHelper {
	
	public static Hashtable<String, String> readFileForCredentials(String filePath) {
		Hashtable<String, String> credentialsTable = new Hashtable<String, String>();
		String userName = null;
		String password = null;
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(
			        new InputStreamReader(new FileInputStream(filePath)));
		    String line;
		    while ((line = br.readLine()) != null) {
		    	userName = "";
				password = "";
				
		    	StringTokenizer st2 = new StringTokenizer(line, "|");
		    	if(st2.hasMoreElements()) {
		    		userName = "" + st2.nextElement();
		    	}
		    	if(st2.hasMoreElements()) {
		    		password = "" + st2.nextElement();
		    	}
		    	
		    	credentialsTable.put(userName, password);
				
		    } 
		}catch(Exception e) {
		    	
		    } finally {
		    	try {
		    br.close();
		    	} catch(IOException ioE) {
		    		
		    	}
		}
		
		return credentialsTable;
	}

}


package game;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import Common.Utility;

abstract public class User {
	
	private String loginName;
	private String hashedPassword;
	
	
	public User(String loginName, String hashedPassword){
		this.loginName = loginName;
		this.hashedPassword = hashedPassword;
	}
	
	public String getLoginName(){
		return loginName;
	}
	
	public String getHashedPassword(){
		return hashedPassword;
	}
	
	public void setHashedPassword(String newHashedPassword){
		this.hashedPassword = newHashedPassword;
	}
	
	public void setLoginName(){
		this.loginName = loginName;
	}
	
	public boolean checkPassword(String password){
		boolean status = false;
		//String hPassword = Utility.getHash(password);
		
		//if(hashedPassword.equals(hPassword)){
			//status = true;
		//}
		
		return status;
	}

}

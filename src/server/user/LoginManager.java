package server.user;

import java.util.Hashtable;

public class LoginManager {

	private Hashtable<String, String> passwordTable = new Hashtable<String, String>();
	
	//TODO: keep track of logins, ip
	
	public boolean login(String username, String hash) {		
		return passwordTable.containsKey(username) && passwordTable.get(username).equals(hash);
	}
	
	public boolean register(String username, String hash) {
		if(passwordTable.containsKey(username))
			return false;
		passwordTable.put(username, hash);
		return true;
	}
	
	//TODO: public boolean changePassword()
	//TODO: logout()
}

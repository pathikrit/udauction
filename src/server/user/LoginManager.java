package server.user;

import java.util.HashMap;

public class LoginManager extends HashMap<String, String> {	

	//TODO: keep track of logins, ip

	private static final long serialVersionUID = -5642763143311195185L;
	
	public boolean login(String username, String hash) {		
		return containsKey(username) && get(username).equals(hash);
	}
	
	public boolean register(String username, String hash) {
		if(username.equalsIgnoreCase("DUMMY"))
			return false;
		if(containsKey(username)) {
			return false;
		} else {
			put(username, hash);		
			return true;
		}
	}
	
	public boolean changePassword(String username, String old_hash, String new_hash) {
		if (containsKey(username) && get(username).equals(old_hash)) {
			put(username, new_hash);		
			return true;
		} else {
			return false;
		}
	}
	
	//TODO: logout()
}

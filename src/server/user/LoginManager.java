package server.user;

import java.util.HashMap;

public class LoginManager extends HashMap<String, String> {	

	//TODO: keep track of logins, ip

	private static final long serialVersionUID = -5642763143311195185L;

	public boolean login(String username, String hash) {		
		return containsKey(username) && get(username).equals(hash);
	}
	
	public boolean register(String username, String hash) {
		if(containsKey(username))
			return false;
		put(username, hash);		
		return true;
	}
	
	//TODO: public boolean changePassword()
	//TODO: logout()
}

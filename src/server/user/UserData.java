package server.user;

import java.util.Hashtable;

public class UserData {
	
	Hashtable<String, Object> data = new Hashtable<String, Object>();
	
	public void setLoggedIn(boolean loggedIn) {
		data.put("LOGGED_IN", loggedIn);
	}
	
	public boolean isLoggedIn() {
		return (Boolean)data.get("LOGGED_IN");
	}	

}

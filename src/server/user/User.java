package server.user;

public class User {	
	
	private UserData data = new UserData();
	
	public User() {
		data.setLoggedIn(false);
	}
	
	public UserData getData() {
		return data;
	}	
}

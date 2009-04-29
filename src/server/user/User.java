package server.user;

import java.util.LinkedHashMap;

import auction.Auction;

public class User extends LinkedHashMap<String, Object> {	
	
	private static final long	serialVersionUID	= -8070729974413538237L;
	private UserData data;
	
	public User() {
		leaveAuction();
		setHelp();
		clearHelpOnce();
		logout();
		clearExit();		
	}
	
	public UserData getData() {
		return data;
	}
	
	public void setData(UserData data) {
		this.data = data;
	}
	
	public void login() {
		put("LOGGED_IN", true);
	}
	
	public void logout() {
		put("LOGGED_IN", false);
	}
	
	public boolean isLoggedIn() {
		return (Boolean)get("LOGGED_IN");
	}
	
	public void setHelpOnce() {
		put("HELP1", true);
	}
	
	public void clearHelpOnce() {
		put("HELP1", false);	
	}
	
	public boolean isHelpOnce() {
		return (Boolean)get("HELP1");
	}
	
	public void setHelp() {
		put("HELP", true);
	}
	
	public void clearHelp() {
		put("HELP", false);	
	}
	
	public boolean isHelp() {
		return (Boolean)get("HELP");
	}
	
	public void joinAuction(Auction auction) {
		put("CURRENT_AUCTION", auction);
	}

	public void leaveAuction() {
		joinAuction(null);
	}

	public boolean isInAuction() {
		return (getCurrentAuction() != null);
	}

	public Auction getCurrentAuction() {
		return (Auction)get("CURRENT_AUCTION");
	}
	
	public void setExit() {
		put("EXIT", true);
	}
	
	public void clearExit() {
		put("EXIT", false);
	}
	
	public boolean isExit() {
		return (Boolean)get("EXIT");
	}	
	
	public boolean equals(User user) {
		return data.equals(user.getData());
	}
	
	public String getStatus() {
		return data.toString() + "\n" + toString();
	}
}

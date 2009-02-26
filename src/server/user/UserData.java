package server.user;

import java.util.HashSet;
import java.util.Hashtable;

import auction.Auction;

public class UserData {
	
	private Hashtable<String, Object> data = new Hashtable<String, Object>();
	
	public UserData() {
		data.put("LOGGED_IN", false);
		HashSet<Auction> auctions = new HashSet<Auction>();
		data.put("CREATED_AUCTIONS", auctions);
	}
	
	public void setUserName(String username) {
		data.put("USERNAME", username);
	}
	
	public String getUserName() {
		return (String)data.get("USERNAME");
	}
	
	public void setLoggedIn(boolean loggedIn) {
		data.put("LOGGED_IN", loggedIn);
	}
	
	public boolean isLoggedIn() {
		return (Boolean)data.get("LOGGED_IN");
	}
	
	public void joinAuction(Auction auction) {
		data.put("CURRENT_AUCTION", auction);
	}
	
	public Auction getCurrentAuction() {
		return (Auction)data.get("CURRENT_AUCTION");
	}
	
	@SuppressWarnings("unchecked")
	public void addAuction(Auction auction) {
		((HashSet<Auction>)data.get("CREATED_AUCTIONS")).add(auction);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteAuction(Auction auction) {
		((HashSet<Auction>)data.get("CREATED_AUCTIONS")).remove(auction);
	}
	
	public boolean equals(UserData data) {
		return getUserName().equals(data.getUserName()); 
	}

}

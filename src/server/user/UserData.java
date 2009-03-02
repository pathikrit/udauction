package server.user;

import java.util.HashSet;
import java.util.HashMap;

import auction.Auction;
import auction.Bidder;

public class UserData { // TODO: make this class extend a HashMap
	
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	public UserData() {
		data.put("LOGGED_IN", false);
		HashSet<Auction> auctions = new HashSet<Auction>();
		HashSet<Bidder> bidders = new HashSet<Bidder>();
		data.put("CREATED_AUCTIONS", auctions);
		data.put("BIDDERS", bidders);
		data.put("EXIT", false);
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
	
	public Auction leaveAuction() {
		Auction auction = getCurrentAuction();
		joinAuction(null);
		return auction;
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
	
	@SuppressWarnings("unchecked")
	public void addBidder(Bidder bidder) {
		((HashSet<Bidder>)data.get("BIDDERS")).add(bidder);
	}
	
	@SuppressWarnings("unchecked")
	public void removeBidder(Bidder bidder) {
		((HashSet<Bidder>)data.get("BIDDERS")).remove(bidder);
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<Bidder> getBidders() {
		return ((HashSet<Bidder>)data.get("BIDDERS"));
	}
	
	public boolean equals(UserData data) {
		return getUserName().equals(data.getUserName()); 
	}
	
	public void setExit(boolean exit) {
		data.put("EXIT", exit);
	}
	
	public boolean isExit() {
		return (Boolean)data.get("EXIT");
	}
}

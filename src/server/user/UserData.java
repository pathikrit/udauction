package server.user;

import java.util.HashSet;
import java.util.HashMap;

import auction.Auction;
import auction.Bidder;

public class UserData extends HashMap<String, Object> {

	private static final long serialVersionUID = 6097804409259693848L;

	public UserData() {
		put("LOGGED_IN", false);
		HashSet<Auction> auctions = new HashSet<Auction>();
		HashSet<Bidder> bidders = new HashSet<Bidder>();
		put("CREATED_AUCTIONS", auctions);
		put("BIDDERS", bidders);
		put("EXIT", false);
	}
	
	public void setUserName(String username) {
		put("USERNAME", username);
	}
	
	public String getUserName() {
		return (String)get("USERNAME");
	}
	
	public void setLoggedIn(boolean loggedIn) {
		put("LOGGED_IN", loggedIn);
	}
	
	public boolean isLoggedIn() {
		return (Boolean)get("LOGGED_IN");
	}
	
	public void joinAuction(Auction auction) {
		put("CURRENT_AUCTION", auction);
	}
	
	public Auction leaveAuction() {
		Auction auction = getCurrentAuction();
		joinAuction(null);
		return auction;
	}
	
	public Auction getCurrentAuction() {
		return (Auction)get("CURRENT_AUCTION");
	}
	
	@SuppressWarnings("unchecked")
	public void addAuction(Auction auction) {
		((HashSet<Auction>)get("CREATED_AUCTIONS")).add(auction);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteAuction(Auction auction) {
		((HashSet<Auction>)get("CREATED_AUCTIONS")).remove(auction);
	}
	
	@SuppressWarnings("unchecked")
	public void addBidder(Bidder bidder) {
		((HashSet<Bidder>)get("BIDDERS")).add(bidder);
	}
	
	@SuppressWarnings("unchecked")
	public void removeBidder(Bidder bidder) {
		((HashSet<Bidder>)get("BIDDERS")).remove(bidder);
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<Bidder> getBidders() {
		return ((HashSet<Bidder>)get("BIDDERS"));
	}
	
	public boolean equals(UserData data) {
		return getUserName().equals(getUserName()); 
	}
	
	public void setExit(boolean exit) {
		put("EXIT", exit);
	}
	
	public boolean isExit() {
		return (Boolean)get("EXIT");
	}
}

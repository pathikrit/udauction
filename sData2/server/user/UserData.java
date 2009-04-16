package server.user;

import java.util.HashSet;
import java.util.HashMap;

import auction.Auction;
import auction.Bidder;

public class UserData extends HashMap<String, Object> {

	private static final long serialVersionUID = 6097804409259693848L;

	//TODO: take all non-shared data outside to user
	
	public UserData() {
		put("LOGGED_IN", false);
		put("HELP", true);
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
	
	public void login() {
		put("LOGGED_IN", true);
	}
	
	public void logout() {
		put("LOGGED_IN", false);
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
	
	public boolean isLoggedIn() {
		return (Boolean)get("LOGGED_IN");
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
	
	public String getUserInfo() {
		return getUserName();
	}
	
	public String getStatus() {
		return toString();
	}
}

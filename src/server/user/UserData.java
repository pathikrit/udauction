package server.user;

import java.util.HashSet;
import java.util.LinkedHashMap;

import auction.Auction;
import auction.Bidder;
import auction.Item;

public class UserData extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 6097804409259693848L;

	//TODO: take all non-shared data outside to user
	
	public UserData() {
		setUserName("");
		HashSet<Auction> auctions = new HashSet<Auction>();
		HashSet<Bidder> bidders = new HashSet<Bidder>();
		HashSet<Item> items = new HashSet<Item>();
		HashSet<Item> won_items = new HashSet<Item>();
		put("CREATED_AUCTIONS", auctions);
		put("BIDS", bidders);
		put("SELL_ITEMS", items);
		put("WON_ITEMS", won_items);
	}
	
	public UserData(String username) {
		this();
		setUserName(username);		
	}
	
	public void setUserName(String username) {
		put("USERNAME", username);
	}
	
	public String getUserName() {
		return (String)get("USERNAME");
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
		((HashSet<Bidder>)get("BIDS")).add(bidder);
	}
	
	@SuppressWarnings("unchecked")
	public void removeBidder(Bidder bidder) {
		((HashSet<Bidder>)get("BIDS")).remove(bidder);
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<Bidder> getBidders() {
		return ((HashSet<Bidder>)get("BIDS"));
	}
	
	@SuppressWarnings("unchecked")
	public void addSellItem(Item item) {
		((HashSet<Item>)get("SELL_ITEMS")).add(item);
	}
	
	@SuppressWarnings("unchecked")
	public void removeSellItem(Item item) {
		((HashSet<Item>)get("SELL_ITEMS")).remove(item);
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<Item> getSellItem() {
		return ((HashSet<Item>)get("SELL_ITEMS"));
	}
	
	@SuppressWarnings("unchecked")
	public void addWonItem(Item item) {
		((HashSet<Item>)get("WON_ITEM")).add(item);
	}
	
	@SuppressWarnings("unchecked")
	public void removeWonItem(Item item) {
		((HashSet<Item>)get("WON_ITEM")).remove(item);
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<Item> getWonItem() {
		return ((HashSet<Item>)get("WON_ITEM"));
	}
	
	public boolean equals(UserData data) {
		return getUserName().equals(data.getUserName());
	}
	
	public String getUserInfo() {
		return getUserName();
	}
}

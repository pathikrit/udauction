package auction;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import server.user.User;

public class Auction {
	
	private String name;
	private User auctionAdmin;
	private static AuctionAlgorithm auctionAlgorithm;		
	
	// TODO: change all public to protected when needed
	
	private LinkedHashSet<Bidder> bidders = new LinkedHashSet<Bidder>();
	private LinkedHashMap<String, Item> items = new LinkedHashMap<String, Item>();
	
	public Auction(String name, User auctionAdmin) {
		this.name = name;
		this.auctionAdmin = auctionAdmin;
	}
	
	public String getAuctionName() {
		return name;
	}
	
	public User getAuctionAdmin() {
		return auctionAdmin;
	}
	
	public boolean canJoin(User user) {
		return true;
	}

	public void setItems(LinkedHashMap<String, Item> items) {
		this.items = items;
	}

	public LinkedHashMap<String, Item> getItems() {
		return items;
	}
}

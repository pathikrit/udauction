package auction;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import server.user.User;

public class Auction {
	
	private String name;
	private User auctionAdmin;
	
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
	
	public boolean addItem(String id) {
		if(items.containsKey(id))
			return false;
		items.put(id, new Item(id));
		return true;
	}
	
	public boolean addBidder(User user, String ids[], int weights[]) {
		Bidder bidder = new Bidder(user, this);
		for(int i = 0; i < ids.length; i++) {
			if (items.containsKey(ids[i])) {
				bidder.addBid(items.get(ids[i]), weights[i]);
			} else {
				return false;
			}
		}
		
		AuctionAlgorithm.matchBidder(bidder);
	
		if(bidder.getMatched().equals(AuctionAlgorithm.DUMMY)) {
			
		} else if (bidders.contains(AuctionAlgorithm.DUMMY.getMatched())) {
			bidders.remove(AuctionAlgorithm.DUMMY.getMatched());			
		} else {
			user.getData().addBidder(bidder);
			bidders.add(bidder);
		}
		return true;
	}
		
	public String toString() {
		return name;
	}
}

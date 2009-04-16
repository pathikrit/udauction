package auction;

import java.util.ArrayList;
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
	
	public boolean addItem(User user, String id) {
		if(items.containsKey(id))
			return false;
		items.put(id, new Item(user, id));
		return true;
	}
	
	public boolean addItem(User user, String id, String deadline, String extended_deadline) {
		if(items.containsKey(id))
			return false;
		try {
			items.put(id, new Item(user, id, Long.parseLong(deadline), Long.parseLong(extended_deadline)));
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}
	
	public boolean deleteItem(User user, String id) {
		return items.containsKey(id) && items.get(id).deleteItem(user);
	}
	
	public String listItems() {
		String ret = "";
		for(Item it: items.values())
			ret = "{" + it + ": " + it.getMatched() + " = " + it.getV() + "}";
		return ret;
	}
	
	public String getItemInfo(String id) {
		if (items.containsKey(id)) {
			return items.get(id).getInfo();
		} else {
			return "Failed to find an item";
		}
	}
	
	public boolean addBidder(User user, String ids[], int weights[]) {
		Bidder bidder = new Bidder(user, this);
		ArrayList<Item> group = new ArrayList<Item>();
		for(int i = 0; i < ids.length; i++) {
			if (items.containsKey(ids[i])) {
				Item it = items.get(ids[i]);
				// TODO: can do group bid checking here
				group.add(it);
				bidder.addBid(it, weights[i]);
			} else {
				return false;
			}
		}
		if(!groupBid(group))
			return false;
		
		AuctionAlgorithm.matchBidder(bidder);		
		if(bidder.getMatched().equals(AuctionAlgorithm.DUMMY)) {
			// do nothing
		} else if (bidders.contains(AuctionAlgorithm.DUMMY.getMatched())) {
			user.getData().addBidder(bidder);
			AuctionAlgorithm.DUMMY.getMatched().getUser().getData().removeBidder(AuctionAlgorithm.DUMMY.getMatched());
			bidders.remove(AuctionAlgorithm.DUMMY.getMatched());			
		} else {
			user.getData().addBidder(bidder);
			bidders.add(bidder);
		}
		return true;		
	}
	
	public boolean groupBid(ArrayList<Item> group) {
		return true; // TODO group bidding
	}
		
	public String getInfo() { // more detailed + description if needed
		return "Auction name: " + name + "\n"
				+ "Created by: " + auctionAdmin + "\n"
				+ "Current number of bidders: " + bidders.size() + "\n"
				+ "Current number of auctioned items: " + items.size();		
	}
	
	public String toString() {
		return name;
	}
}

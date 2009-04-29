package auction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import server.user.UserData;

public class Auction {
	
	private String name;
	private UserData auctionAdmin;
	
	// TODO: change all public to protected when needed
	
	private LinkedHashSet<Bidder> bidders = new LinkedHashSet<Bidder>();
	private LinkedHashMap<String, Item> items = new LinkedHashMap<String, Item>();
	
	public Auction(String name, UserData auctionAdmin) {
		this.name = name;
		this.auctionAdmin = auctionAdmin;
	}
	
	public String getAuctionName() {
		return name;
	}
	
	public UserData getAuctionAdmin() {
		return auctionAdmin;
	}
	
	public boolean canJoin(UserData user) {
		return true;
	}

	public void setItems(LinkedHashMap<String, Item> items) {
		this.items = items;
	}

	public LinkedHashMap<String, Item> getItems() {
		return items;
	}
	
	public boolean addItem(UserData userData, String id) {
		return addItem(userData, id, Long.MAX_VALUE + "", "0");
	}
	
	public boolean addItem(UserData userData, String id, String endTime, String extendTime) {
		if(items.containsKey(id))
			return false;
		try {
			Item item = new Item(userData, id, Long.parseLong(endTime), Long.parseLong(extendTime));
			items.put(id, item);
			userData.addSellItem(item);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public boolean addItem(UserData userData, String [] split) {
		String id = split[1];
		if(items.containsKey(id))
			return false;
		try {			
			long endTime = Long.parseLong(split[2]),
				  extendTime = Long.parseLong(split[3]),
				  startingTime = 0;
			int  startingPrice = 0,
				  reservePrice = 0,
				  buyNowPrice = Integer.MAX_VALUE;
			if (split.length >= 5)
				startingTime = Long.parseLong(split[4]);
			if (split.length >= 6)
				startingPrice = Integer.parseInt(split[5]);
			if (split.length >= 7)
				reservePrice = Integer.parseInt(split[6]);
			if (split.length >= 8)
				reservePrice = Integer.parseInt(split[7]);
			if (split.length > 8)
				return false;
			Item item = new Item(userData, id, endTime, extendTime, startingTime, startingPrice, reservePrice, buyNowPrice);
			items.put(id, item);
			userData.addSellItem(item);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public boolean deleteItem(UserData userData, String id) {
		return items.containsKey(id) && items.get(id).deleteItem(userData);
	}
	
	public String listItems() {
		String ret = "";
		for(Item it: items.values()) {
			if (it.getMatched() != null) {
				ret += "{" + it + ": " + it.getMatched().getUserData().getUserName() + "=" + it.getV() + "}";
			} else {
				ret += "{" + it + ": " + it.getMatched() + "=" + it.getV() + "}";
			}
		}
		return ret;
	}
	
	public String getItemInfo(String id) {
		if (items.containsKey(id)) {
			return items.get(id).getInfo();
		} else {
			return "Failed to find an item";
		}
	}
	
	public boolean addBidder(UserData userData, String ids[], int weights[]) {
		Bidder bidder = new Bidder(userData, this);
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
			userData.addBidder(bidder);
			AuctionAlgorithm.DUMMY.getMatched().getUserData().removeBidder(AuctionAlgorithm.DUMMY.getMatched());
			bidders.remove(AuctionAlgorithm.DUMMY.getMatched());			
		} else {
			userData.addBidder(bidder);
			bidders.add(bidder);
		}
		return true;		
	}
	
	public boolean groupBid(ArrayList<Item> group) {
		return true; // TODO group bidding
	}
		
	public String getInfo() { // more detailed + description if needed
		return "Auction name: " + name + "\n"
				+ "Created by: " + auctionAdmin.getUserName() + "\n"
				+ "Current number of bidders: " + bidders.size() + "\n"
				+ "Current number of auctioned items: " + items.size();		
	}
	
	public String toString() {
		return name;
	}
}

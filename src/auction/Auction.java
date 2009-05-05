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
	private LinkedHashMap<String, Item> soldItems = new LinkedHashMap<String, Item>();
	
	public Auction(String name, UserData auctionAdmin) {
		new SellingThread(this).start();
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
				startingTime = Math.max(Long.parseLong(split[4]), 0);
			if (split.length >= 6)
				startingPrice = Math.max(Integer.parseInt(split[5]), 0);
			if (split.length >= 7)
				reservePrice = Math.max(Integer.parseInt(split[6]), 0);
			if (split.length >= 8)
				buyNowPrice = Math.max(Integer.parseInt(split[7]), 0);
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
	
	protected void addSoldItem(Item item) {
		soldItems.put(item.getId(), item);
	}
	
	protected void deleteItem(String id) {
		items.remove(id);
	}
	
	public boolean deleteItem(UserData userData, String id) {
		if (items.containsKey(id) && items.get(id).deleteItem(userData)) {
			deleteItem(id);
			return true;
		} else {
			return false;
		}		
	}
	
	public String list(LinkedHashMap<String, Item> it) {
		String ret = "";
		for(String i: it.keySet()) {
			ret += it.get(i).getInfo() + "\n";
		}
		return ret.substring(0, Math.max(0, ret.length()-1));
	}
	public String listItems() {
		return list(items);
	}
	
	public String listSoldItems() {
		return list(soldItems);
	}
	
	public String getInfo(LinkedHashMap<String, Item> it, String id) {
		if (it.containsKey(id)) {
			return it.get(id).getInfo();
		} else {
			return "Failed to find an item";
		}
	}
	
	public String getItemInfo(String id) {
		return getInfo(items, id);
	}
	
	public String getSoldItemInfo(String id) {
		return getInfo(soldItems, id);
	}
	
	protected LinkedHashSet<Bidder> getBidders() {
		return bidders;
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
		if(AuctionAlgorithm.equalsDummy(bidder.getMatched())) {
			// do nothing
		} else if (bidders.contains(AuctionAlgorithm.getDummyMatched())) {
			userData.addBidder(bidder);
			bidders.add(bidder);
			AuctionAlgorithm.getDummyMatched().getUserData().removeBidder(AuctionAlgorithm.getDummyMatched());
			bidders.remove(AuctionAlgorithm.getDummyMatched());			
		} else {
			userData.addBidder(bidder);
			bidders.add(bidder);
		}
		return true;		
	}
	
	protected void deleteBidder(Bidder bidder) {
		bidders.remove(bidder);
	}
	
	public boolean groupBid(ArrayList<Item> group) {
		return true; // TODO group bidding
	}
		
	public String getInfo() { // more detailed + description if needed
		return "Auction name: " + name + "\n"
				+ "Created by: " + auctionAdmin.getUserName() + "\n"
				+ "Current number of bidders: " + bidders.size() + "\n"
				+ "Current number of auctioned items: " + items.size() + "\n"
				+ "Sold items: " + soldItems.keySet();
	}
	
	public String toString() {
		return name;
	}
}

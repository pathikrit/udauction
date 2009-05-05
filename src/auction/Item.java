package auction;

import java.util.Date;
import server.user.UserData;

import static lib.Util.INFINITY;

public class Item {
	
	private String id; //TODO: create a ItemData class to manage more item info
	
	@SuppressWarnings("unused")
	private int v = 0, startingPrice, reservePrice, buyNowPrice = Integer.MAX_VALUE, soldPrice = INFINITY;
	private long startingTime, endTime, extendTime;	
	
	private UserData seller;
	private Bidder matched;
	
	// TODO: Can we create two items in an auction with same id? NO!

	public Item(UserData userData, String id) {
		this.seller = userData;
		this.id = id;
		endTime = Long.MAX_VALUE;
		extendTime = endTime;
		startingTime = System.currentTimeMillis();
	}
	
	public Item(UserData userData, String id, long endTime, long extendTime) {
		this(userData, id);
		long time = System.currentTimeMillis();
		this.endTime = time + Math.max(0, endTime) * 1000;
		this.extendTime = this.endTime + Math.max(0, extendTime) * 1000;
		startingTime = time;
	}
	
	public Item(UserData userData, String id, long endTime, long extendTime, long startingTime, int startingPrice, int reservePrice, int buyNowPrice) {
		this(userData, id, endTime, extendTime);
		long time = System.currentTimeMillis();
		this.startingTime = time + Math.max(0, startingTime) * 1000;
		this.startingPrice = startingPrice;
		v = startingPrice;
		this.reservePrice = Math.max(startingPrice, reservePrice);
		if (reservePrice != 0 && reservePrice >= startingPrice) {
			Bidder dummyBidder = new Bidder(userData);
			dummyBidder.addBid(this, reservePrice);
			dummyBidder.setMatched(this);
			matched = dummyBidder;
		}
		this.buyNowPrice = buyNowPrice;
	}
	
	protected String getId() {
		return id;
	}
	
	public void setV(int v) {
		this.v = v;
	}

	public int getV() {
		return v;
	}

	public void setSoldPrice(int soldPrice) {
		this.soldPrice = soldPrice;
	}

	public int getSoldPrice() {
		return soldPrice;
	}
	
	public void setMatched(Bidder matched) {
		this.matched = matched;
	}

	public Bidder getMatched() {
		return matched;
	}
	
	protected long getStartingTime() {
		return startingTime;
	}
	
	protected long getEndingTime() {
		return endTime;
	}
	
	protected long getExtendedTime() {
		return extendTime;
	}
	
	public boolean deleteItem(UserData userData) {
		return seller.equals(userData) && System.currentTimeMillis() < startingTime;
	}
	
	public String getInfo() {
		Date startingDate = new Date(startingTime);
		Date endDate = new Date(endTime);
		Date extendDate = new Date(extendTime);
		String ret = 
			  "Item name: " + id + "\n"
			+ "Price: " + (v==INFINITY ? soldPrice : v) + "\n"
			+ "Starting price: " + startingPrice + "\n"
			+ "Starting time: " + startingDate + "\n"
			+ "End time: " + endDate + "\n"
			+ "Extended time: " + extendDate;
		if (matched != null) {
			ret += "\nCurrent winner: " + matched.getUserData().getUserName();
		}
		return ret;
		
	}

	public void sell() {
		soldPrice = v;
		v = INFINITY;
		matched.getUserData().addWonItem(this);
		matched.getUserData().removeBidder(matched);
		matched.getAuction().deleteBidder(matched);
	}
	
	public String toString() {
		return id;
	}
}

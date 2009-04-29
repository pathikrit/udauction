package auction;

import java.util.Date;
import server.user.UserData;

public class Item {
	
	private String id; //TODO: create a ItemData class to manage more item info
	
	private boolean active = true;
	@SuppressWarnings("unused")
	private int v = 0, startingPrice, reservePrice, buyNowPrice = Integer.MAX_VALUE;
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
		this.endTime = time + endTime;
		this.extendTime = this.endTime + extendTime;
		startingTime = time;
	}
	
	public Item(UserData userData, String id, long endTime, long extendTime, long startingTime, int startingPrice, int reservePrice, int buyNowPrice) {
		this(userData, id, endTime, extendTime);
		long time = System.currentTimeMillis();
		if(startingTime == 0) {
			setActive();
		} else {
			clearActive();
		}
		this.startingTime = time + startingTime;
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
	
	public void setV(int v) {
		this.v = v;
	}

	public int getV() {
		return v;
	}

	public void setMatched(Bidder matched) {
		this.matched = matched;
	}

	public Bidder getMatched() {
		return matched;
	}

	public void setActive() {
		active = true;
	}
	
	public void clearActive() {
		active = false;
	}

	public boolean isActive() {
		return active;
	}
	
	public boolean deleteItem(UserData userData) {
		return seller.equals(userData) && System.currentTimeMillis() < startingTime;
	}
	
	public String getInfo() {
		Date startingDate = new Date(startingTime);
		Date endDate = new Date(endTime);
		Date extendDate = new Date(extendTime);
		String ret = 
			  "Name: " + id + "\n"
			+ "Price: " + v + "\n"
			+ "Starting price: " + startingPrice + "\n"
			+ "Starting time: " + startingDate + "\n"
			+ "End time: " + endDate + "\n"
			+ "Extended time: " + extendDate;
		if (matched != null) {
			ret += "\nCurrent winner: " + matched.getUserData().getUserName();
		}
		return ret;
		
	}

	public String toString() {
		return id;
	}
}

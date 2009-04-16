package auction;

import java.util.Date;
import server.user.UserData;

public class Item {
	
	private String id; //TODO: create a ItemData class to manage more item info
	
	private boolean active = true;
	@SuppressWarnings("unused")
	private int v = 0, startingPrice = 0, reservePrice = 0;
	private long startingTime, endTime, extendTime;	
	
	private UserData seller;
	private Bidder matched;
	
	// TODO: Can we create two items in an auction with same id? NO!
	
	public Item(UserData userData, String id) {
		this(userData, id, Long.MAX_VALUE, 0, 0);		
	}
	
	public Item(UserData userData, String id, long endTime, long extendTime) {
		this(userData, id, endTime, extendTime, 0);		
	}
	
	public Item(UserData userData, String id, long endTime, long extendTime, long startTime) {
		this.seller = userData;
		this.id = id;
		long time = System.currentTimeMillis();
		this.endTime = time + endTime;
		this.extendTime = this.endTime + extendTime;
		this.startingTime = time + startTime;
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

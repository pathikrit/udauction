package auction;

import java.util.Date;
import server.user.User;

public class Item {
	
	private String id; //TODO: create a ItemData class to manage more item info
	
	private boolean active = true;
	@SuppressWarnings("unused")
	private int v = 0, startingPrice = 0, reservePrice = 0;
	private long startingTime, endTime, extendTime;	
	
	private User seller;
	private Bidder matched;
	
	// TODO: comments of time
//	public Item() {
//		this(""); //TODO: is this a bug? generate a random id maybe? WAS FOR DUMMY
//	}
	
	// TODO: Can we create two items in an auction with same id? NO!
	
	public Item(User user, String id) {
		this(user, id, Long.MAX_VALUE, 0, System.currentTimeMillis());		
	}
	
	public Item(User user, String id, long endTime, long extendTime) {
		this(user, id, endTime, extendTime, System.currentTimeMillis());		
	}
	
	public Item(User user, String id, long endTime, long extendTime, long startTime) {
		this.seller = user;
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
	
	public boolean deleteItem(User user) {
		return seller.equals(user) && System.currentTimeMillis() < startingTime;
	}
	
	public String getInfo() {
		Date startingDate = new Date(startingTime);
		Date endDate = new Date(endTime);
		Date extendDate = new Date(extendTime);
		
		return "Name: " + id + "\n"
		+ "Price: " + v + "\n"
		+ "Starting time: " + startingDate + "\n"
		+ "End time: " + endDate + "\n"
		+ "Extended time: " + extendDate + "\n"
		+ "Starting price: " + startingPrice;
	}

	public String toString() {
		return id;
	}
}

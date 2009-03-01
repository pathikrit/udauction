package auction;

public class Item {
	
	private String id; //TODO: create a ItemData class to manage more item info
	
	private boolean active = true;
	private int v = 0, startingPrice = 0, reservePrice = 0;
	private long startingTime, endTime, extendTime;	
	
	private Bidder matched;
	
	// TODO: comments of time
	public Item() {
		this("");
	}
	
	public Item(String id) {
		this(id, Long.MAX_VALUE, 0, System.currentTimeMillis());		
	}
	
	public Item(String id, long endTime, long extendTime) {
		this(id, endTime, extendTime, System.currentTimeMillis());		
	}
	
	public Item(String id, long endTime, long extendTime, long startTime) {
		this.id = id;
		this.endTime = endTime;
		this.extendTime = extendTime;
		this.startingTime = startTime;
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

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public String toString() {
		return id;
	}
}

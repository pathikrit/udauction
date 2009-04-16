package auction;

import java.util.LinkedHashSet;

import server.user.User;
import lib.Pair;

// TODO: Make sure all imports are standardly arranged

public class Bidder {
	
	protected static class Bid extends Pair<Item, Integer> {		
		public Bid(Item item, Integer weight) {
			super(item, weight);
		}
		
		public Item getItem() {
			return getFirst();
		}
		
		public int getWeight() {
			// TODO: make sure all bids are named weights in all files
			return getSecond();
		}		
	}
	
	private Auction auction;
	private LinkedHashSet<Bid> bids = new LinkedHashSet<Bid>();	
	private Item matched; // do not match to dummy!
	private User user;
	
	@SuppressWarnings("unused")
	private int u = 0, shift = 0;
	
	public Bidder(User user, Auction auction) {
		this.user = user;
		this.auction = auction;
	}
	
	public User getUser() {
		return user;
	}
	
	public void addBid(Item item, int weight) {		
		bids.add(new Bid(item, weight));
	}
	
	public LinkedHashSet<Bid> getBids() {
		return bids;
	}

	public void setU(int u) {
		this.u = u;
	}

	public int getU() {
		return u;
	}

	public void setMatched(Item matched) {
		this.matched = matched;
	}

	public Item getMatched() {
		return matched;
	}
	
	public Auction getAuction() {
		return auction;
	}
	
	public String toString() {
		return user.toString();
	}
}

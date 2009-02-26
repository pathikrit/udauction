package auction;

import java.util.Hashtable;

import server.user.User;

public class AuctionManager {

	private Hashtable<String, Auction> auctions = new Hashtable<String, Auction>();
		
	public Auction createAuction(String name, User user) {
		name = name.toLowerCase();
		if (auctions.contains(name)) {
			return null;
		}
		else {
			Auction newAuction = new Auction(name, user);
			auctions.put(name, newAuction);
			return newAuction;
		}
	}
	
	public boolean deleteAuction(String name, User user) {
		name = name.toLowerCase();
		Auction auction = auctions.get(name);
		if (auction == null || !auction.getAuctionAdmin().equals(user)) {
			return false;
		}
		return true;
	}
	
	public Auction getAuction(String name, User user) {
		name = name.toLowerCase();
		Auction auction = auctions.get(name);
		if (auction == null || !auction.isAllowed(user)) // TODO: better naming for isAllowed
			return null;
		else
			return auction;
	}
}

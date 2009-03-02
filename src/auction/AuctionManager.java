package auction;

import java.util.HashMap;

import server.user.User;

public class AuctionManager {

	// TODO: structure the program such that all permission checking of actions is done in one PermissionChecker class.
	
	private HashMap<String, Auction> auctions = new HashMap<String, Auction>();	
		
	public Auction createAuction(String name, User auctionAdmin) {
		name = name.toLowerCase();		
		if(auctions.containsKey(name))
			return null;
		Auction auction = new Auction(name, auctionAdmin); 
		auctions.put(name, auction);
		return auction;
	}
	
	public boolean deleteAuction(String name, User user) {
		name = name.toLowerCase();
		Auction auction = auctions.get(name);		
		return auction != null && auction.getAuctionAdmin().equals(user) && (auctions.remove(name) != null);
	}
	
	public Auction getAuction(String name, User user) {
		name = name.toLowerCase();
		Auction auction = auctions.get(name);
		if(auction == null || !auction.canJoin(user))
			return null;
		return auction;
	}
}

package auction;

import java.util.HashMap;

import server.user.User;

public class AuctionManager {

	// TODO: structure the program such that all permission checking of actions is done in one PermissionChecker class.
	
	private HashMap<String, Auction> auctions = new HashMap<String, Auction>();	
		
	public Auction createAuction(String name, User auctionAdmin) {
		name = name.toLowerCase();		
		return auctions.containsKey(name) ? null : auctions.put(name, new Auction(name, auctionAdmin));		
	}
	
	public boolean deleteAuction(String name, User user) {
		name = name.toLowerCase();		
		return auctions.get(name).getAuctionAdmin().equals(user) && (auctions.remove(name) != null);
	}
	
	public Auction getAuction(String name, User user) {
		name = name.toLowerCase();
		return auctions.get(name).canJoin(user) ? auctions.get(name) : null;
	}
}

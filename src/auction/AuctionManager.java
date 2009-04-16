package auction;

import java.util.HashMap;

import server.user.User;

public class AuctionManager extends HashMap<String, Auction> {

	// TODO: structure the program such that all permission checking of actions is done in one PermissionChecker class.
	
	private static final long	serialVersionUID	= 1024660891804044046L;

	public Auction createAuction(String auctionName, User auctionAdmin) {
		auctionName = auctionName.toLowerCase();		
		if(containsKey(auctionName))
			return null;
		Auction auction = new Auction(auctionName, auctionAdmin); 
		put(auctionName, auction);
		return auction;
	}
	
	public boolean deleteAuction(String auctionName, User user) {
		auctionName = auctionName.toLowerCase();
		Auction auction = get(auctionName);		
		return auction != null && auction.getAuctionAdmin().equals(user) && (remove(auctionName) != null);
	}
	
	public Auction getAuction(String auctionName, User user) {
		auctionName = auctionName.toLowerCase();
		Auction auction = get(auctionName);
		if(auction == null || !auction.canJoin(user))
			return null;
		return auction;
	}
	
	public String listAuctions() {
		return values().toString();
	}
	
	public String getInfo(String auctionName) {
		return get(auctionName) == null ? "Auction does not exist" : get(auctionName).getInfo();
	}
}

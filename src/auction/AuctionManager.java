package auction;

import java.util.Collection;
import java.util.HashMap;

import server.user.UserData;

public class AuctionManager extends HashMap<String, Auction> {

	// TODO: structure the program such that all permission checking of actions is done in one PermissionChecker class.
	
	private static final long serialVersionUID = 1024660891804044046L;

	public AuctionManager() {
		new SanityThread(this).start();
	}
	
	public Auction createAuction(String auctionName, UserData auctionAdmin) {
		auctionName = auctionName.toLowerCase();		
		if(containsKey(auctionName))
			return null;
		Auction auction = new Auction(auctionName, auctionAdmin); 
		put(auctionName, auction);
		return auction;
	}
	
	public boolean deleteAuction(String auctionName, UserData userData) {
		auctionName = auctionName.toLowerCase();
		Auction auction = get(auctionName);		
		return auction != null && auction.getAuctionAdmin().equals(userData) && (remove(auctionName) != null);
	}
	
	public Auction getAuction(String auctionName, UserData userData) {
		auctionName = auctionName.toLowerCase();
		Auction auction = get(auctionName);
		if(auction == null || !auction.canJoin(userData))
			return null;
		return auction;
	}
	
	protected Collection<Auction> getAuctions() {
		return this.values();
	}
	
	public String listAuctions() {
		return values().toString();
	}
	
	public String getInfo(String auctionName) {
		return get(auctionName) == null ? "Auction does not exist" : get(auctionName).getInfo();
	}
}

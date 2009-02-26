package auction;

import server.user.User;

public class Auction {
	
	private String name;
	private User auctionAdmin;
	private static AuctionAlgorithm auctionAlgorithm;
	
	public Auction(String name, User auctionAdmin) {
		this.name = name;
		this.auctionAdmin = auctionAdmin;
	}
	
	public String getAuctionName() {
		return name;
	}
	
	public User getAuctionAdmin() {
		return auctionAdmin;
	}
	
	public boolean isAllowed(User user) {
		return true;
	}
}

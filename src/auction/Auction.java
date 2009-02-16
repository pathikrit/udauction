package auction;

import java.net.ServerSocket;

public class Auction {

	ServerSocket auctionSocket;
	
	public Auction(ServerSocket auctionSocket) {
		this.auctionSocket = auctionSocket;
	}
}

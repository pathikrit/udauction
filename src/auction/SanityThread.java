package auction;

import java.util.Date;
import java.util.LinkedHashSet;

import auction.Bidder.Bid;

public class SanityThread extends Thread{
	
	private AuctionManager auctionManager;
	private static final int SLEEP_DELAY = 10; // in seconds
	
	SanityThread(AuctionManager auctionManager) {
		this.auctionManager = auctionManager;
	}
	
	public void run() {
		while(true) {
			try {
				LinkedHashSet<Bidder> bidders;
				for(Auction auction: auctionManager.getAuctions()) {
					bidders = auction.getBidders();
					for(Bidder bidder: bidders) {
						LinkedHashSet<Bid> bids = bidder.getBids();
						int maxUtility = 0;
						for(Bid bid: bids)
							maxUtility = Math.max(maxUtility, bid.getWeight() - bid.getItem().getV());
						if (maxUtility != bidder.getU()) {
							System.out.println("Failed the sanity check!!!");
							System.out.println("Auction: " + auction);
							System.out.println("User: " + bidder.getUserData());
							System.out.println("Bids: " + bids);
						}
					}
				}
				System.out.println("Performed the sanity check on " + (new Date(System.currentTimeMillis())));
				sleep(SLEEP_DELAY*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

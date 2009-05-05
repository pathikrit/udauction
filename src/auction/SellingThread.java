package auction;

import java.util.HashSet;
import java.util.LinkedHashMap;

public class SellingThread extends Thread{
	
	private Auction auction;
	private LinkedHashMap<String, Item> items;
	
	SellingThread(Auction auction) {
		this.auction = auction;
		items = auction.getItems();
	}
	
	public void run() {
		while(true) {
			try {
				HashSet<Item> temp_items = new HashSet<Item>();		
				for(Item it: items.values()) {
					long time = System.currentTimeMillis();
					if(time >= it.getExtendedTime()) {
						temp_items.add(it);
						auction.addSoldItem(it);
						it.sell();
					}
				}
				for(Item it: temp_items) {
					auction.deleteItem(it.getId());
				}
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

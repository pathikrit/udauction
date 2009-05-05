package auction;

import java.util.HashMap;
import java.util.HashSet;

import server.user.UserData;
import static lib.Util.INFINITY;

public class AuctionAlgorithm {
	
	private static Item DUMMY = new Item(new UserData("DUMMY"), "DUMMY");
	
	// TODO: recursive insertion
	// TODO: change_bid
	
	protected static Bidder getDummyMatched() {
		return DUMMY.getMatched();
	}
	
	protected static boolean equalsDummy(Item it) {
		return DUMMY.equals(it);
	}
	
	public static void matchBidder(Bidder bidder) {
		HashMap<Item, Integer> potentials = new HashMap<Item, Integer>();
		HashMap<Bidder, Item> labelBidders = new HashMap<Bidder, Item>();
		HashMap<Item, Bidder> labelItems = new HashMap<Item, Bidder>();
		HashSet<Bidder> visitedBidders = new HashSet<Bidder>();
		HashSet<Item> visitedItems = new HashSet<Item>();
		
		for(Bidder.Bid bid : bidder.getBids()) {
			bidder.setU(Math.max(bidder.getU(), bid.getWeight() - bid.getItem().getV()));
		}
		
		DUMMY.setV(0);
		DUMMY.setMatched(null);
		labelBidders.put(bidder, DUMMY);
		potentials.put(DUMMY, INFINITY);
		
		while(true) {
			for (Bidder b: labelBidders.keySet())
				if (!visitedBidders.contains(b)) {
					if (potentials.get(DUMMY) > b.getU()) {
						potentials.put(DUMMY, b.getU());
						labelItems.put(DUMMY, b);
					}
					visitedBidders.add(b);
					for (Bidder.Bid bid : b.getBids()) {
						if ((b.getMatched() == null || !b.getMatched().equals(bid.getFirst())) &&							
							(!potentials.containsKey(bid.getFirst()) || (b.getU() + bid.getFirst().getV() - bid.getSecond() < potentials.get(bid.getFirst())))) {
							labelItems.put(bid.getFirst(), b);
							potentials.put(bid.getFirst(), b.getU() + bid.getFirst().getV() - bid.getSecond());
						}
					}
				}
			
			boolean pass = true;
			
			for (Item item: labelItems.keySet())
				if (!visitedItems.contains(item) && potentials.containsKey(item) && potentials.get(item) == 0) {
					visitedItems.add(item);
					if(item.getMatched() != null) {
						labelBidders.put(item.getMatched(), item);
						pass = false;
					}
					else {
						Item right = item;
						if(!labelItems.containsKey(right)) 
							return;
						for (Bidder left = labelItems.get(right); ;left = labelItems.get(right)) {
							if(left.getMatched() == right) {
								left.setMatched(null);
								right.setMatched(null);
								if (!labelBidders.containsKey(left) || labelBidders.get(left).equals(DUMMY)) 
									return;
								right = labelBidders.get(left);
								left.setMatched(right);
								right.setMatched(left);
								if (!labelItems.containsKey(right)) 
									return;
							}
							else {
								left.setMatched(right);
								right.setMatched(left);
								if (!labelBidders.containsKey(left) || labelBidders.get(left).equals(DUMMY)) 
									return;
								right = labelBidders.get(left);
								if (!labelItems.containsKey(right)) 
									return;
							}
						}
					}
				}
			if(pass) {
				int delta = INFINITY;
				for (Integer i: potentials.values())
					if (i > 0)
						delta = Math.min(delta, i);
				if (delta == INFINITY) {
					bidder.setMatched(DUMMY);
					return;
				}
				for (Bidder b: labelBidders.keySet())
					b.setU(b.getU() - delta);
				for (Item i: potentials.keySet()) {
					if (potentials.get(i) == 0)
						i.setV(i.getV() + delta);
					if (potentials.get(i) > 0 && labelItems.containsKey(i))
						potentials.put(i, potentials.get(i)-delta);
				}
			}
		}

	}
	
}
package auction;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import server.user.UserData;

public class AuctionTester {

	private static Auction auction;
	private static LinkedHashMap<String, Item> items;
	private static LinkedHashSet<Bidder> bidders;
	
	private static int w[][];
	private static int DENSITY = 33, S = 100, T = 100, MAX_WEIGHT = 20;
	
	//TODO: make a nicer tester
	
	public static void main(String args[]) {
		auction =  new Auction("Test Auction", new UserData("DUMMY"));
		items = auction.getItems();
		bidders = new LinkedHashSet<Bidder>();
		
		for(int tests = 1; tests >= 0; tests--) {			
			S = 1 + (int)(99*Math.random());
			T = 1 + (int)(99*Math.random());
			generateRandom();
		w = new int[][]{{0, 0, 0, 7, 16, 18, 14, 0, 0, 10},
						{18, 0, 0, 4, 6, 0, 17, 0, 0, 3},
						{4, 10, 0, 0, 0, 4, 18, 3, 0, 0},
						{0, 0, 0, 0, 0, 0, 5, 9, 0, 14},
						{14, 14, 0, 0, 19, 0, 11, 17, 0, 0},
						{0, 18, 15, 8, 0, 0, 5, 3, 0, 8},
						{15, 5, 0, 14, 0, 8, 0, 0, 12, 9},
						{0, 0, 0, 0, 18, 15, 10, 13, 17, 4},
						{19, 1, 0, 17, 0, 15, 15, 0, 14, 0},
						{0, 10, 7, 5, 12, 0, 6, 18, 10, 4}
					   };
		
			int row = w.length, col = w[0].length;		
			for (int i = 0; i < row; i++) {
				Bidder newBidder = new Bidder(new UserData("B"+i), auction);
				for (int j = 0; j < col; j++) {
					if (items.containsKey("I"+j) && w[i][j] != 0)
						newBidder.addBid(items.get("I"+j), w[i][j]);
					else if (w[i][j] != 0){
						Item newItem = new Item(new UserData("B"+i), "I"+j);
						items.put("I"+j, newItem);
						newBidder.addBid(newItem, w[i][j]);
					}
				}
				bidders.add(newBidder);
				AuctionAlgorithm.matchBidder(newBidder);
	
				
				for(Bidder bidder : bidders) {
					System.out.print(bidder + "'s edge-weights are: ");
					for(Bidder.Bid bid : bidder.getBids()) {
						System.out.print(bid + " ");
					}
					System.out.println();
				}
	
				System.out.println("\nMaximum matching is: ");
				for(Bidder bidder : bidders) {
					System.out.println(bidder + " is matched to " + bidder.getMatched() + " with (u,v) = (" + bidder.getU() + ", "+ bidder.getMatched().getV() + ")");
				}
				System.out.println("****************************************************");
			}
			printResult();
			printVCG();
			printMatrix();
			
			//if(tests%10 == 0)
			//	System.out.println("Passed test case " + tests);
		}
	}
	
	private static void generateRandom() {
		w = new int[S][T];
		for(int i = 0; i < S; i++)
			for(int j = 0; j < T; j++)
				w[i][j] = (Math.random()*100 < DENSITY) ? 0 : (int)(MAX_WEIGHT*Math.random());
	}
	
	@SuppressWarnings("unused")
	private static Bidder convert(int w[], int i) {
		Bidder newBidder = new Bidder(new UserData("B"+i), auction);
		for (int j = 0; j < w.length; j++) {
			if (items.containsKey("I"+j) && w[j] != 0)
				newBidder.addBid(items.get("I"+j), w[j]);
			else if (w[j] != 0){
				Item newItem = new Item(new UserData("B"+i),"I"+j);
				items.put("I"+j, newItem);
				newBidder.addBid(newItem, w[j]);
			}
		}
		bidders.add(newBidder);
		AuctionAlgorithm.matchBidder(newBidder);
		return newBidder;
	}
	
	private static void printResult() {
		for (Bidder bidder: bidders) {
			System.out.println("Bidder " + bidder + ((AuctionAlgorithm.equalsDummy(bidder.getMatched()))?
					" is not matched to any item":("(" + bidder.getU() + ") is matched to " + bidder.getMatched() + "[" + bidder.getMatched().getV() + "]")));
		}
	}

	private static void printMatrix() {
		for (int i[]:  w) {
			for (int j: i)
				System.out.printf("%4d ", j);			
			System.out.println();
		}		
	}

	private static void printVCG() {
		System.out.println("The current VCG is " + Arrays.toString(getVCG()));
	}

	private static int[] getVCG() {
		int prices[] = new int[items.size()], i = 0;		
		for (Item item: items.values()) {
			prices[i++] = item.getV();			
		}
		return prices;
	}

}

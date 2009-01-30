package cmd;

import java.util.ArrayDeque;

import udAuction.Bid;
import udAuction.Item;

public class User {
	
	private ArrayDeque<Bid> bids, proposed;
	private String login, password; //TODO: security
	private Item matched; 
	
}

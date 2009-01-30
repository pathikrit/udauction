package cmd;

import java.util.ArrayList;

import udAuction.Bid;
import udAuction.Item;


public class CommandParser {

	final static private String USER_COMMANDS[] = {"help", "bid", "sell", "list", "info", "exit"};
	
	//final static private String ADMIN_COMMANDS[] = {"help", "start", "close"}; //TODO	
	
	public boolean parse(String command) {
		return false;
	}
	
	public void help() {
		
	}
	
	public void bid(ArrayList<Bid> bids) {
		
	}
	
	/*
	 * sell number of identical items
	 */
	public void sell(Item it, int number) {
		
	}
	
	/*
	 * list all items in the auction
	 */
	public void list() {
		
	}
	
	/*
	 * 
	 */
	public void info(Item it) {
		
	}
	
	public void info(User u) {
		
	}
	
	public void exit() {
		
	}
	
}

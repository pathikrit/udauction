package cmd;

import java.util.ArrayList;

import udAuction.Bid;
import udAuction.Database;
import udAuction.Item;
import udAuction.LoginManager;


public class CommandParser {

	final static private String USER_COMMANDS[] = {"help", "bid", "sell", "list", "info", "exit"};
	//final static private String ADMIN_COMMANDS[] = {"help", "start", "close"}; //TODO
	
	LoginManager lm;
	Database db;
	
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
	
	public void exit(User u, String cookie) {
		if(lm.logout(u, cookie)) {
			System.out.println("Logout successfully.");
		}
		else
			System.out.println("Fail to logout.");
	}
}

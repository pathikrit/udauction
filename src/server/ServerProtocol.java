package server;

import java.util.HashMap;

import auction.Auction;
import auction.AuctionManager;

import server.user.LoginManager;
import server.user.User;
import server.user.UserData;

public class ServerProtocol {
	
	private String commands[][] = {
			{"HELP"},
			{"REGISTER", "username", "password", "confirm_password"},
			{"LOGIN", "username", "password"},						
			{"CREATE_AUCTION", "auction_name"},
			{"JOIN_AUCTION", "auction_name"},
			{"LEAVE_AUCTION"},			
			{"ADD_ITEM", "item_name", "..."},
			{"BID", "item_name", "bid_value", "..."}, //"..." means variable number of args
			{"LOGOUT"},
			{"EXIT"}			
			//TODO: list_items, info <name>, list_auctions, etc.
	};
	
	private static LoginManager loginManager = new LoginManager();
	private HashMap<String, UserData> userTable = new HashMap<String, UserData>();
	private static AuctionManager auctionManager = new AuctionManager();
	
	// TODO: nicer returns
	public String processCommand(String line, User user) {
		UserData data = user.getData();
		String split[] = validate(line);
		Auction auction = null;
		
		if(split == null)
			return "BAD COMMAND!";
		
		// TODO: put user's login ip address in userdata & user
		// TODO: every command has its own handler method
		if(split[0].equalsIgnoreCase("LOGIN")) {
			if(loginManager.login(split[1], split[2])) {
				if (userTable.containsKey(split[1]))
					data = userTable.get(split[1]);
				data.setLoggedIn(true);
				return "Login Succesful!";
			} else {
				return "Username/password does not exist";
			}
		} else if(split[0].equalsIgnoreCase("REGISTER")) {
			if(split[2].equals(split[3])) {
				if(loginManager.register(split[1], split[2])) {
					return "Succesfully registered";
				} else {
					return "Username taken";
				}
			} else {
				return "Passwords do not match";
			} 
		} else if(split[0].equalsIgnoreCase("EXIT")) {
			data.setLoggedIn(false);
			data.setExit(true);
			return "Bye Bye!!!";			
		} 
		
		if(data.isLoggedIn()) {
			if(split[0].equalsIgnoreCase("CREATE_AUCTION")) {
				if ((auction = auctionManager.createAuction(split[1], user)) == null) {
					return "The auction name is taken";
				} else {
					data.addAuction(auction);
					return "Succesfully created an auction " + split[1];
				}
			} else if(split[0].equalsIgnoreCase("JOIN_AUCTION")) {
				if ((auction = auctionManager.getAuction(split[1], user)) == null) {
					return "You are not allowed to join this auction";
				} else {
					data.joinAuction(auction); // TODO: modify userdata for current user
					return "Succesfully joined an auction " + split[1];
				}
			} else if(split[0].equalsIgnoreCase("LEAVE_AUCTION")) {
				auction = data.leaveAuction();
				return "You successfully left auction " + auction + "";
				//TODO: what if auction is null
			} else if(split[0].equalsIgnoreCase("ADD_ITEM")) {
				auction = data.getCurrentAuction();
				if(auction == null) {
					return "Failed to add item; join an auction to add an item";					
				} else {
					String msg = "";				
					int c = 0;
					for(int i = 1; i < split.length; i++) {
						if(auction.addItem(split[i])) {
							msg += "You added item " + split[i] + " to auction " + auction;
							c++;
							// TODO: auto-generate item ids
						} else {
							msg += "There is another item with same item name " + split[i] + " in current auction";
						}
						msg += "\n";												
					}
					if(c > 0)
						msg += "Successfully added " + c + " items";
					else
						msg += "Failed to add an item";
					return msg;
				}								
			} else if(split[0].equalsIgnoreCase("BID")) {
				auction = data.getCurrentAuction();
				if(auction == null) {
					return "Failed to bid; join an auction to bid";					
				} else {
					int n = split.length - 1;
					if(n%2 != 0)
						return "BAD COMMAND!";
					n /= 2;
					String ids[] = new String[n];
					int weights[] = new int[n];
					
					for(int i = 0; i < n; i++) {
						ids[i] = split[2*i+1];
						try {
							weights[i] = Integer.parseInt(split[2*i+2]);
						} catch(NumberFormatException nfe) {
							return "BAD COMMAND!";
						}							
					}
					
					if(auction.addBidder(user, ids, weights)) {
						return "Successfully group bid on " + n + " items";
					} else {
						return "Group bid failed";					
					}
						
				}
			} else if(split[0].equalsIgnoreCase("LOGOUT")) {
				data.setLoggedIn(false); //TODO: user.logout() and user.login()
				return "Successfully logged out!";
			} 
				
				//TODO: help else if()
				else
					return "Should not reach here";
			// TODO: in logout, merge userdata for all different logins of same user
			// TODO: OR change userdata dynamically
		} else {
			return "BAD COMMAND!";
		}
	}
	
	/*
	 * splits line if line is a valid command
	 * else, returns null
	 */
	private String[] validate(String line) {
		String split[] = line.split("\\s");
		for(String c[] : commands)
			if(c[0].equalsIgnoreCase(split[0]) && (c[c.length-1].equals("...") || c.length == split.length)) {
				return split;
			}	
		return null;
	}
}

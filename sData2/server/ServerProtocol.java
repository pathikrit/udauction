package server;

import java.util.HashMap;

import auction.Auction;
import auction.AuctionManager;

import server.user.LoginManager;
import server.user.User;
import server.user.UserData;

public class ServerProtocol {
	
	private static String commands[][] = {
		//Before login
		{"HELP_ON"},	// show all appropriate commands on the current level {before login, after login, etc..
		{"HELP_OFF"},
		{"REGISTER", "username", "password", "confirm_password"},
		{"LOGIN", "username", "password"},
		{"EXIT"},
		//After login
		{"CHANGE_PASSWORD", "old_password", "new_password", "confirm_new_password"},
		{"CREATE_AUCTION", "auction_name"},
		{"DELETE_AUCTION", "auction_name"}, // TODO: not implemented
		{"LIST_AUCTIONS"},
		{"INFO_AUCTION", "auction_name"},
		{"JOIN_AUCTION", "auction_name"},
		{"INFO_USER", "username"},
		{"STATUS"},	// TODO: nicer status
		{"LOGOUT"},	// save userData
		//Inside an auction
		{"LEAVE_AUCTION"},
		// TODO: add support for optional: starting time[def:0], starting price[def:0], reserved price[def:0]
		{"ADD_ITEM", "item_name", "deadline", "extended_deadline", "..."},
		{"ADD_ITEMS", "item_name", "..."}, // TODO: not implemented
		{"DELETE_ITEM", "item_name"}, // only if the item is not auctioned yet
		{"LIST_ITEMS"},
		{"INFO_ITEM", "item_name"},
		{"BID", "item_name", "bid_value", "..."}, //"..." means variable number of args
		{"CHANGE_BID", "item_name", "bid_value", "..."}, // TODO: not implemented
		{"INFO_BIDDER", "bidder_name"}, // TODO: not implemented				
	};
	
	private static HashMap<String, UserData> userTable = new HashMap<String, UserData>();	
	private static LoginManager loginManager = new LoginManager();
	private static AuctionManager auctionManager = new AuctionManager();
	
	// TODO: nicer returns
	public static synchronized String processCommand(String line, User user) {
		UserData data = user.getData();
		String split[] = validate(line);
		Auction auction = null;
		
		if(split == null)
			return "BAD COMMAND!";
		
		// TODO: put user's login ip address in userdata & user
		// TODO: every command has its own handler method
		if(split[0].equalsIgnoreCase("HELP_ON")) {
			data.setHelp();
			return "Help is on";
		} else if(split[0].equalsIgnoreCase("HELP_OFF")) {
			data.clearHelp();
			return "Help is off";
		} else if(split[0].equalsIgnoreCase("LOGIN")) {
			if(loginManager.login(split[1], split[2])) {
				if (userTable.containsKey(split[1]))
					data = userTable.get(split[1]);
					user.setData(data);
				if(data.isLoggedIn())
					return "You are already logged in!";
				data.login();				
				return "Login Succesful!";
			} else {
				return "Username/password does not exist";
			}
		} else if(split[0].equalsIgnoreCase("REGISTER")) {
			if(split[2].equals(split[3])) {
				if(loginManager.register(split[1], split[2])) {
					data.setUserName(split[1]);
					return "Succesfully registered";
				} else {
					return "Username taken";
				}
			} else {
				return "Passwords do not match";
			} 
		} else if(split[0].equalsIgnoreCase("EXIT")) {
			data.logout();
			data.setExit(true);
			return "Bye Bye!!!";			
		}
		
		// After login
		if(data.isLoggedIn()) {
			if (split[0].equalsIgnoreCase("CHANGE_PASSWORD")) {
				if (split[2].equals(split[3])) {
					if (loginManager.changePassword(data.getUserName(), split[1], split[2])) {
						return "The password is succesfully updated";
					} else {
						return "Wrong password";
					}					
				} else {
					return "New passwords do not match";
				}				
			} else if(split[0].equalsIgnoreCase("CREATE_AUCTION")) {
				if ((auction = auctionManager.createAuction(split[1], user)) == null) {
					return "The auction name is taken";
				} else {
					data.addAuction(auction);
					return "Succesfully created an auction " + split[1];
				}
			} else if(split[0].equalsIgnoreCase("LIST_AUCTIONS")) {
					return auctionManager.listAuctions();
			} else if(split[0].equalsIgnoreCase("INFO_AUCTION")) {
				return auctionManager.getInfo(split[1]);
			} else if(split[0].equalsIgnoreCase("JOIN_AUCTION")) {
				if ((auction = auctionManager.getAuction(split[1], user)) == null) {
					return "You are not allowed to join this auction";
				} else {
					data.joinAuction(auction); // TODO: modify userdata for current user
					return "Succesfully joined an auction " + split[1];
				}
			} else if(split[0].equalsIgnoreCase("INFO_USER")) {
				return userTable.get(split[1]).getUserInfo();
			} else if(split[0].equalsIgnoreCase("STATUS")) {
				return data.getStatus();
			} else if(split[0].equalsIgnoreCase("LOGOUT")) {
				data.leaveAuction();
				data.logout(); //TODO: user.logout() and user.login()				
				return "Successfully logged out!";
			}
			
			// After join_auction
			if (data.isInAuction()) {
				auction = data.getCurrentAuction();
				if(split[0].equalsIgnoreCase("LEAVE_AUCTION")) {
					data.leaveAuction();
					return "You successfully left auction " + auction;
				} else if(split[0].equalsIgnoreCase("ADD_ITEM")) {
					auction = data.getCurrentAuction();
					if(auction.addItem(user, split[1], split[2], split[3])) {
						return "You added item " + split[1] + " to auction " + auction;
						// TODO: auto-generate item ids
					} else {
						return "There is another item with same item name " + split[1] + " in the current auction";
					}
//				} else if(split[0].equalsIgnoreCase("ADD_ITEMS")) {
//					auction = data.getCurrentAuction();
//					String msg = "";				
//					int c = 0;
//					for(int i = 1; i < split.length; i++) {
//						if(auction.addItem(split[i])) {
//							msg += "You added item " + split[i] + " to auction " + auction;
//							c++;
//							// TODO: auto-generate item ids
//						} else {
//							msg += "There is another item with same item name " + split[i] + " in the current auction";
//						}
//						msg += "\n";												
//					}
//					if(c > 0)
//						msg += "Successfully added " + c + " items";
//					else
//						msg += "Failed to add an item";
//					return msg;
				} else if(split[0].equalsIgnoreCase("DELETE_ITEM")) {
					if (auction.deleteItem(user, split[1])) {
						return "Succesfully deleted an item";
					} else {
						return "Failed to delete an item";
					}
				} else if(split[0].equalsIgnoreCase("LIST_ITEMS")) {
					return auction.listItems();
				} else if(split[0].equalsIgnoreCase("INFO_ITEM")) {
					return auction.getItemInfo(split[1]);
				} else if(split[0].equalsIgnoreCase("BID")) {
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
				} else { // TODO: help else if()
					return "Should not reach here";
				}
			// TODO: in logout, merge userdata for all different logins of same user
			// TODO: OR change userdata dynamically
			} else {
				return "BAD COMMAND!";
			}
		} else {
			return "BAD COMMAND!";
		}
	}
	
	/*
	 * splits line if line is a valid command
	 * else, returns null
	 */
	private static String[] validate(String line) {
		String split[] = line.split("\\s");
		for(String c[] : commands)
			if(c[0].equalsIgnoreCase(split[0]) && ((c[c.length-1].equals("...") && (c.length <= split.length + 1)) || c.length == split.length)) {
				return split;
			}	
		return null;
	}
}

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
		// In this case no optional fields can be included: it_name, dline, exline, it_name, dline, exline, ...
		{"ADD_ITEMS", "item_name", "deadline", "extended_deadline", "..."},
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
			user.setHelp();
			return "Help is on";
		} else if(split[0].equalsIgnoreCase("HELP_OFF")) {
			user.clearHelp();
			return "Help is off";
		} else if(split[0].equalsIgnoreCase("LOGIN")) {
			if(loginManager.login(split[1], split[2])) {
				if (userTable.containsKey(split[1])) {
					data = userTable.get(split[1]);
					user.setData(data);
				} else {
					userTable.put(split[1], data);
				}
				data.setUserName(split[1]);
//				if(split[1].equals(data.getUserName()))
//					return "You are already logged in!";
				user.login();
				return "Login Succesful!";
			} else {
				return "Username/password does not exist";
			}
		} else if(split[0].equalsIgnoreCase("REGISTER")) {
			if(split[2].equals(split[3])) {
				if(loginManager.register(split[1], split[2])) {
					userTable.put(split[1], new UserData(split[1]));
					return "Succesfully registered";
				} else {
					return "Username taken";
				}
			} else {
				return "Passwords do not match";
			} 
		} else if(split[0].equalsIgnoreCase("EXIT")) {
			user.logout();
			user.setData(null);
			user.setExit();
			return "Bye Bye!!!";			
		}
		
		// After login
		if(user.isLoggedIn()) {
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
				if ((auction = auctionManager.createAuction(split[1], data)) == null) {
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
				if ((auction = auctionManager.getAuction(split[1], data)) == null) {
					return "You are not allowed to join this auction";
				} else {
					user.joinAuction(auction); // TODO: modify userdata for current user
					return "Succesfully joined an auction " + split[1];
				}
			} else if(split[0].equalsIgnoreCase("INFO_USER")) {
				if (userTable.containsKey(split[1])) {
					return userTable.get(split[1]).getUserInfo();
				} else {
					return "This user does not exist";
				}				
			} else if(split[0].equalsIgnoreCase("STATUS")) {
				return user.getStatus();
			} else if(split[0].equalsIgnoreCase("LOGOUT")) {
				user.leaveAuction();
				user.setData(new UserData());
				user.logout(); //TODO: user.logout() and user.login()				
				return "Successfully logged out!";
			}
			
			// After join_auction
			if (user.isInAuction()) {
				auction = user.getCurrentAuction();
				if(split[0].equalsIgnoreCase("LEAVE_AUCTION")) {
					user.leaveAuction();
					return "You successfully left auction " + auction;
				} else if(split[0].equalsIgnoreCase("ADD_ITEM")) {
					if(auction.addItem(data, split[1], split[2], split[3])) {
						return "You added item " + split[1] + " to auction the current auction";
						// TODO: auto-generate item ids
					} else {
						return "There is another item with same item name " + split[1] + " in the current auction";
					}
				} else if(split[0].equalsIgnoreCase("ADD_ITEMS")) {
					String msg = "";				
					int c = 0;
					if ((split.length-1) % 3 == 0) {
						for(int i = 1; i < split.length; i+= 3) {
							if(auction.addItem(data, split[i], split[i+1], split[i+2])) {
								msg += "You added an item " + split[i] + " to the current auction";
								c++;
								// TODO: auto-generate item ids
							} else {
								msg += "There is another item with the same item name " + split[i] + " in the current auction";
							}
							msg += "\n";
						}
						if(c > 0) {
							msg += "Successfully added " + c + " items";
						} else {
							msg += "Failed to add an item";
						}
						return msg;
					} else {
						return "BAD COMMAND!";
					}
				} else if(split[0].equalsIgnoreCase("DELETE_ITEM")) {
					if (auction.deleteItem(data, split[1])) {
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
					if(auction.addBidder(data, ids, weights)) {
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

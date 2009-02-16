package server;

import java.util.Hashtable;

import auction.Auction;
import auction.AuctionManager;

import server.user.LoginManager;
import server.user.UserData;

public class ServerProtocol {
	
	private String commands[][] = { 
			{"LOGIN", "username", "password"},	
			{"REGISTER", "username", "password", "confirm_password"},
			{"LOGOUT"},
			{"CREATE_AUCTION"},
			{"HELP"}
	};
	
	private static LoginManager loginManager = new LoginManager();
	private Hashtable<String, UserData> userTable = new Hashtable<String, UserData>();
	private static AuctionManager auctionManager = new AuctionManager();
	
	// TODO: nicer returns
	public String processCommand(String line, UserData data) {		
		String split[] = validate(line);
		if(split == null)
			return "BAD COMMAND!";
		
		if(split[0].equalsIgnoreCase("LOGIN")) {
			if(loginManager.login(split[1], split[2])) {
				if (userTable.containsKey(split[1]))
					data = userTable.get(split[1]);
				data.setLoggedIn(true);
				return "Login Succesful!";
			} else {
				return "Username/password does not exist.....";
			}
		} else if(split[0].equalsIgnoreCase("REGISTER")) {
			if(split[2].equals(split[3])) {
				if(loginManager.register(split[1], split[2])) {
					return "Succesfully registered";
				} else {
					return "Username taken";
				}
			} else {
				return "Passwords do not match ...";
			}			
		} else if(split[0].equalsIgnoreCase("CREATE_AUCTION")) {
			auctionManager.createAuction();			
			return "NOT IMPLEMENTED";			
		} else		
			return "Should not reach here";
	}
	
	/*
	 * splits line if line is a valid command
	 * else, returns null
	 */
	private String[] validate(String line) {
		String split[] = line.split("\\s");
		for(String c[] : commands)
			if(c[0].equalsIgnoreCase(split[0]) && c.length == split.length) {
				return split;
			}	
		return null;
	}
}

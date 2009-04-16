package server;

import java.io.PrintWriter;

import auction.Bidder;

import server.user.UserData;

public class DisplayManager {
	
	private PrintWriter out;
	
	public DisplayManager(PrintWriter out) {
		this.out = out;
	}
	
	//TODO: nicer print outs
	public String display(UserData data) {
		String displayString = "\n";
		if(data.isLoggedIn()) {
			for(Bidder bidder: data.getBidders()) {
				displayString += "You are matched to " + bidder.getMatched() + " in auction " + bidder.getAuction() + " for price " + bidder.getMatched().getV() + "\n";
			}
			if(data.isHelp()) {
				displayString += "Available commands (# - not implemented):\n" +
				   "> change_password <old_password> <new_password> <confirm_new_password>\n" +
				   "> create_auction <auction_name>\n" +
				   "> #delete_auction <auction_name>\n" +
				   "> list_auctions\n" +
				   "> info_auction <auction_name>\n" +
				   "> join_auction <auction_name>\n" +
				   "> info_user <username>\n" +
				   "> status\n";
				if(data.isInAuction()) {
					displayString +=
					"> leave_auction\n" +
					"> add_item <item_name> <deadline> <extended_deadline>\n" +
					"> #add_items <item_name> ..." +
					"> delete_item <item_name>\n" +
					"> list_items\n" +
					"> info_item <item_name\n" +
					"> bid <item_name> <bid_value> ...\n" +
					"> #change_bid <item_name> <bid_value> ...\n" +
					"> info_bidder <username>\n";				
				}
				displayString +=
					"> logout\n"; 
			}
		} else if(data.isHelp()) {
				displayString += "Available commands (# - not implemented):\n" +
					"> login <username> <password>\n" +
					"> register <username> <password> <confirm_password>\n";
		}
		if(data.isHelp()) {
			displayString += 
					"> help_on\n" +
					"> help_off\n" +
					"> exit\n";
		}
		displayString += "bye";
		out.println(displayString);	
		out.flush();
		return displayString;
	}
}

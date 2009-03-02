package server;

import java.io.PrintWriter;

import auction.Bidder;

import server.user.UserData;

public class DisplayManager {
	
	PrintWriter out;
	
	public DisplayManager(PrintWriter out) {
		this.out = out;
	}
	
	public String display(UserData data) {
		String displayString = "";
		if(data.isLoggedIn()) {
			for(Bidder bidder: data.getBidders()) {
				displayString += "You are matched to " + bidder.getMatched() + " in auction " + bidder.getAuction() + " for price " + bidder.getMatched().getV() + "\n";
			}
		} else { //only login code no server protocol
			//TODO: nicer print outs
			displayString += "login <username> <password> OR register <username> <password> <confirm_password>";			
		}
		displayString += "\nbye";
		out.println(displayString);	
		out.flush();
		return displayString;
	}
}

package server;

import java.io.PrintWriter;

import server.user.UserData;

public class DisplayManager {
	
	PrintWriter out;
	
	public DisplayManager(PrintWriter out) {
		this.out = out;
	}
	
	public void display(UserData data) {
		if(data.isLoggedIn()) {
			
		} else {
			//TODO: nicer print outs
			out.println("login<username, password>, register<username, password, password>");
			
			//only login code no server protocol
		}
		out.println("bye");
		out.flush();
	}
}
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Stack;

import manager.Auction;

public class Server {

	//private ports 49152–65535
	public static final int PORT_BEGIN = 49152, PORT_END = 65535, PORT = 51065;
	ServerSocket serverSocket;

	private static Stack<Integer> freePorts = new Stack<Integer>();
	
	static {
		for(int i = PORT_BEGIN; i <= PORT_END; i++)
			if(i != PORT)
				freePorts.push(i);		
	}
	
	public Server() {		
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			 System.err.println("Could not listen on port: " + PORT);
		}		
	}
	
	public void createAuction() {
		for(boolean found = false; !found;) {
			int port = freePorts.pop();
			try {
				ServerSocket auctionSocket = new ServerSocket(port);				
				found = true;
				Auction a = new Auction(auctionSocket);
				
			} catch (IOException e) {
				 System.err.println("Could not listen on port: " + port);
			}
		}		
	}
	
	
	
}

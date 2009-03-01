package server;

import java.io.File;
import java.net.ServerSocket;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.IOException;

//import auction.Auction;

public class Server {	
	
	private ServerSocket serverSocket;
	private int serverPort;
	private static final String CONFIG_FILE = ".server";		
	
	public Server() {
		readConfig(CONFIG_FILE);
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}	
	}
	
	//TODO: Auto discovery of server
	public void listen() {		
		boolean listening = true;
		// TODO another thread for listening to command line for exit command
		try {
			while (listening)			
				new ServerThread(serverSocket.accept()).start();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	public void readConfig(String file) {
		try {
			for(Scanner sc = new Scanner(new File(file)); sc.hasNextLine(); ) {
				String line = sc.nextLine().trim();				
				if(line.startsWith("port"))
					serverPort = Integer.parseInt(line.substring(line.indexOf("=")+1).trim());				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createAuction() {
		// TODO
	}
	
	public static void main(String cmdLine[]) {
		// TODO args[0] can be used to overwrite default config file
		// TODO rename all dot files to .config 
		Server s = new Server();
		s.listen();
	}
	
}

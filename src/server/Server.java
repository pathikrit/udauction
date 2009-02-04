package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;

//import manager.Auction;

public class Server {

	//private ports 49152–65535
	public static final int PORT_BEGIN = 49152, PORT_END = 65535;
	
	private ServerSocket serverSocket;
	private int serverPort;
	private static final String CONFIG_FILE = ".server";
	private static Stack<Integer> freePorts = new Stack<Integer>();	
	
	public Server() {
		readConfig(CONFIG_FILE);
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}		 		
		init();		
	}
	
	public void init() {
		for(int i = PORT_BEGIN; i <= PORT_END; i++)
			if(i != serverPort)
				freePorts.push(i);
	}
	
	public void listen() {
		
        while(true) {
        	Socket clientSocket;
			try {
				clientSocket = serverSocket.accept();
				Scanner sc = new Scanner(clientSocket.getInputStream());
				while(sc.hasNextLine()) {
					String line = sc.nextLine();
					System.out.println(line + " came from " + clientSocket.getInetAddress());					
				}
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }        
        //TODO serverSocket.close();
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
//		for(boolean found = false; !found;) {
//			int port = freePorts.pop();		
//			ServerSocket auctionSocket;
//			try {
//				auctionSocket = new ServerSocket(port);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}				
//			found = true;
//			//Auction a = new Auction(auctionSocket);
//			
//			
//		}		
	}
	
	public static void main(String cmdLine[]) {
		// TODO args[0] can be used to overwrite default config file
		// TODO rename all dot files to .config 
		Server s = new Server();
		s.listen();
	}
	
}

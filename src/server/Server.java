package server;

import java.io.File;
import java.net.ServerSocket;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.IOException;

//import auction.Auction;

public class Server {	
	
	private static ServerSocket serverSocket;
	private static int serverPort;
	private static final String CONFIG_FILE = ".server";		
	
	//TODO: Auto discovery of server
	public static void listen() {		
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
		
	public static void readConfig(String file) {
		try {
			for(Scanner sc = new Scanner(new File(file)); sc.hasNextLine(); ) {
				String line = sc.nextLine().trim();				
				if(line.startsWith("port"))
					serverPort = Integer.parseInt(line.substring(line.indexOf("=")+1).trim());		
				// TODO: make sure all ifs have braces
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void runServer() {
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	}
	
	//TODO: Decrease permissions as much as possible e.g. all methods here should be private
	public static void main(String cmdLine[]) {
		// TODO args[0] can be used to overwrite default config file
		// TODO rename all dot files to .config
		readConfig(CONFIG_FILE);
		runServer();
		listen();
	}
	
}

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
	private static ServerListenerThread serverListenerThread;
	
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

	//TODO: Auto discovery of server
	public static void listen() {		
		serverListenerThread = new ServerListenerThread(serverSocket);
		serverListenerThread.start();
	}
		
	public static void execute() {
		Scanner cmd = new Scanner(System.in);
		String line = "";
		while(!line.equalsIgnoreCase("exit") && cmd.hasNext()) {
			line = cmd.nextLine().trim();
			System.out.println(ServerProtocol.processCommand(line));
		}
	}
	
	//TODO: Decrease permissions as much as possible e.g. all methods here should be private
	public static void main(String cmdLine[]) {
		// TODO args[0] can be used to overwrite default config file
		// TODO rename all dot files to .config
		System.out.println("Server Started");
		readConfig(CONFIG_FILE);
		runServer();
		listen();
		execute();		
		System.exit(0);
	}	
}

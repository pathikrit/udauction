// TODO: How to compile and run from command line
package client;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import java.net.UnknownHostException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Console {

	private static final String CONFIG_FILE = ".client";
	
	private String server;
	private int port;
	protected Socket serverSocket;
	
	public Console() {
		readConfig(CONFIG_FILE);
		connectToServer();
	}
	// TODO lib package for common libraries e.g. parsing input file in Util.java
	// TODO System.exit(-1)
	// TODO JUnit testing
	
	public void execute() {
		Scanner cmd = new Scanner(System.in);
		String c = "";		
		do {		
			try {
				String fromServer;
				for(Scanner in = new Scanner(serverSocket.getInputStream()); !(fromServer = in.nextLine()).equalsIgnoreCase("BYE"); ) { 				
					System.out.println(fromServer);
				}			
				PrintWriter out = new PrintWriter(serverSocket.getOutputStream());
				System.out.println();
				c = cmd.nextLine();
				out.println(c);
				out.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		} while(!c.equalsIgnoreCase("EXIT"));
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connectToServer() {		
		try {
			serverSocket = new Socket(server, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}	
	}
	
	//TODO: merge all readConfigs into util 
	
	public void readConfig(String file) {
		try {
			for(Scanner sc = new Scanner(new File(file)); sc.hasNextLine(); ) {
				String line = sc.nextLine().trim();
				// TODO HashMap<String, String> config
				if(line.startsWith("server"))
					server = line.substring(line.indexOf("=")+1).trim();
				else if(line.startsWith("port"))
					port = Integer.parseInt(line.substring(line.indexOf("=")+1).trim());				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO args[0] can be used to overwrite default config file
		Console c = new Console();
		c.execute();
	}
}

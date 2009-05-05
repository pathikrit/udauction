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

	protected static final String CONFIG_FILE = ".client";
	
	private static String server;
	private static int port;
	protected static Socket serverSocket;	
	
	// TODO lib package for common libraries e.g. parsing input file in Util.java
	// TODO System.exit(-1)
	// TODO JUnit testing
	// TODO Check the static execute later
	// TODO: merge all readConfigs into util
	
	public static void readConfig(String file) {
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
	
	public static void connectToServer() {		
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
	
	public static void execute() {
		Scanner cmd = new Scanner(System.in);
		Scanner in;
		try {
			in = new Scanner(serverSocket.getInputStream());
			String line = "";
			do {
				if (in.hasNext() == false) {
					System.out.println("Server closed");
					break;
				}
				for(; in.hasNext() && !(line = in.nextLine()).equalsIgnoreCase("BYE"); ) { 				
					System.out.println(line);
				}				
				PrintWriter out = new PrintWriter(serverSocket.getOutputStream());
				System.out.println();
				line = cmd.nextLine();
				out.println(line);
				out.flush();
				System.out.println();
			} while(!line.equalsIgnoreCase("EXIT"));
			serverSocket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	
	public static void main(String[] args) {
		// TODO args[0] can be used to overwrite default config file
		readConfig(CONFIG_FILE);
		connectToServer();
		execute();
	}
}

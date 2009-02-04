package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Console {

	private static final String CONFIG_FILE = ".client";
	
	private String server;
	private int port;
	private Socket serverSocket;
	
	public Console() {
		readConfig(CONFIG_FILE);
		connectToServer();		
	}
	
	public void execute() {
		Scanner cmd = new Scanner(System.in);
		for(String c; !(c = cmd.nextLine()).equalsIgnoreCase("exit"); ) {			
			
			try {
				PrintWriter out = new PrintWriter(serverSocket.getOutputStream());
				out.write(c+"\n");
				out.flush();				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}		
		}
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
		// TODO args[0] can be used to overwrite defailt config file
		Console c = new Console();
		c.execute();
	}
}

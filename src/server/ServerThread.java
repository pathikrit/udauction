package server;

import java.net.Socket;
import java.util.Scanner;

import java.io.IOException;
import java.io.PrintWriter;

import server.user.LoginManager;
import server.user.User;


public class ServerThread extends Thread {
	
	private Socket clientSocket;
	
	private static LoginManager loginManager = new LoginManager();
	private static DisplayManager displayManager;
	private User user = new User();

	public ServerThread(Socket clientSocket) {
		System.out.println("Client connected: " + clientSocket);
		this.clientSocket = clientSocket;
	}
	
	// TODO: http://java.sun.com/j2se/1.4.2/docs/guide/security/jaas/tutorials/GeneralAcnOnly.html

	public void run() {	
		try {			
			Scanner sc = new Scanner(clientSocket.getInputStream());
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
			displayManager = new DisplayManager(out);
			while(sc.hasNextLine()) {
				displayManager.display(user.getData());
				String line = sc.nextLine();
				
			}			
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

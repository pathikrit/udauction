package server;

import java.net.Socket;
import java.util.Scanner;

import java.io.IOException;
import java.io.PrintWriter;

import server.user.LoginManager;
import server.user.User;


public class ServerThread extends Thread {
	
	private Socket clientSocket;
	
//	private static LoginManager loginManager = new LoginManager();
	private static DisplayManager displayManager;
	private static ServerProtocol serverProtocol = new ServerProtocol();
	private User user = new User();

	public ServerThread(Socket clientSocket) {
		System.out.println("Client connected: " + clientSocket);
		this.clientSocket = clientSocket;
	}
	
	// TODO: http://java.sun.com/j2se/1.4.2/docs/guide/security/jaas/tutorials/GeneralAcnOnly.html
	// TODO: Change BYE to something better

	public void run() {	
		try {			
			Scanner in = new Scanner(clientSocket.getInputStream());
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			displayManager = new DisplayManager(out);
			
			while(true) {
			
				displayManager.display(user.getData());				
				out.println(serverProtocol.processCommand(in.nextLine(), user.getData()));				
				out.flush();			
			} 			
			//out.close();
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

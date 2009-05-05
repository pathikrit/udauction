package server;

import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import java.io.IOException;
import java.io.PrintWriter;

//import server.user.LoginManager;
import server.user.User;


public class ServerThread extends Thread {
	
	private Socket clientSocket;
	
	private DisplayManager displayManager;
	private User user = new User();

	public ServerThread(Socket clientSocket) {
		System.out.println("Client connected: " + clientSocket);
		this.clientSocket = clientSocket;
	}
	
	protected Socket getClientSocket() {
		return clientSocket;
	}
	
	// TODO: http://java.sun.com/j2se/1.4.2/docs/guide/security/jaas/tutorials/GeneralAcnOnly.html
	// TODO: Change BYE to something better
	
	public void run() {	
		try {			
			Scanner in = new Scanner(clientSocket.getInputStream());
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			displayManager = new DisplayManager(out);
			
			while(!user.isExit()) {
				displayManager.display(user);
				
				// TODO: restructure all outs to DisplayManager
				try {
					out.println(ClientProtocol.processCommand(in.nextLine(), user));
				} catch(NoSuchElementException nsee) {
					System.out.println("Client ended communication");
					ServerListenerThread.disconnectClient(this);
					break;
					// TODO: remove try-catch
				}
				out.flush();			
			} 			
			//out.close();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
			try {				
				clientSocket.close();
				System.out.println("Client disconnected: " + clientSocket);
				ServerListenerThread.disconnectClient(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void cleanUp() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

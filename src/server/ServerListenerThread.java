package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;

public class ServerListenerThread extends Thread {
	
	private ServerSocket serverSocket;
	private ServerThread serverThread;
	private static HashSet<ServerThread> serverThreadSet = new HashSet<ServerThread>();
	private boolean listening = true;
	
	ServerListenerThread(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}	
	
	public void run() {
		try {
			while (listening) {
				serverThread = new ServerThread(serverSocket.accept());
				serverThread.start();
				serverThreadSet.add(serverThread);
			}
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
	
	protected static void disconnectClient(ServerThread serverThread) {
		serverThreadSet.remove(serverThread);
	}
	
	protected static String getClients() {
		String info = "";
		for (ServerThread i: serverThreadSet)
			info += i.getClientSocket().toString() + "\n";
		return info.substring(0, Math.max(info.length()-1, 0));
	}
}
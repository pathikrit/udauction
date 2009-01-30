package misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestServer {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		ServerSocket serverSocket = new ServerSocket(2366);		
		Socket clientSocket = serverSocket.accept();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		System.out.println(in.readLine());
		
		clientSocket.close();
	    serverSocket.close();
	}
}

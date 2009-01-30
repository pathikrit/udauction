package misc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket clientSocket = new Socket("127.0.0.1", 2366);
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

		out.write("hello world");
		out.flush();
		
		clientSocket.close();
		
	}

}

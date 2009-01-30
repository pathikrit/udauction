package misc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket cs = new Socket("127.0.0.1", 2366);
		InputStream in = cs.getInputStream();
		OutputStream out = cs.getOutputStream();
		
		out.write(10);

	}

}

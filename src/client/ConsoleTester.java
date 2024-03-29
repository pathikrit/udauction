package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConsoleTester extends Console {

	public static void main(String[] args) {
		readConfig(CONFIG_FILE);
		connectToServer();
		execute();
	}
	
	public static void execute() {
		Scanner cmd = new Scanner(
					"help\n" +
					"help_off\n" +
					"info_item i1\n" +
					"create_auction a1\n" +
					"add_item i1\n" +
					"login linh test\n" +
					"login linh\n" +
					"items_on\n" + 
					"register linh test test2\n" +
					"items_off\n" +
					"register linh test test\n" +
					"register rick t1 t1\n" +
					"login linh test2\n" +
					"add_item i4 3000 4000\n" +
					"login linh test\n" +						
					"add_item i1\n" +
					"login linh test\n" +
					"join_auction a0\n" +
					"create_auction a1\n" +
					"join_auction a2\n" +
					"change_password test test2 test2\n" +
					"list_auctions\n" +
					"info rick\n" +
					"info_user rick\n" +
					"status\n" +
					"create_auction a2\n" +
					"info_auction a1\n" +
					"join_auction a1\n" +
					"login linh test\n" +
					"add_item i1\n" +
					"help\n" +
					"add_item i1\n" +
					"add_item i2 i3 i1\n" +
					"add_item i2 i3 i1\n" +
					"items_on\n" +
					"add_item i1 1000 2000\n" +
					"bid i1 20\n" +
					"bid i1 i2\n" +
					"bid i1 10 i2\n" +
					"bid i1 1 i2 20 i4 40\n" +
					"bid i1 20 i2 10\n" +
					"items_off\n" + 
					"add_items i1 2 3 i2 5 10 i3 15 0\n" +
					"list_items\n" +
					"info_item a1\n" +
					"info_item i1\n" +
					"info_item i2\n" +
					"info_item i3\n" +
					"info_item i4\n" +
					"status\n" +
					"logout\n" +
					"login linh test\n" +
					"\n" +
					"exit"
				);
		String c = "";		
		do {		
			try {
				String fromServer;
				for(Scanner in = new Scanner(serverSocket.getInputStream()); !(fromServer = in.nextLine()).equalsIgnoreCase("BYE"); ) { 				
					System.out.println("<< " + fromServer);
				}			
				PrintWriter out = new PrintWriter(serverSocket.getOutputStream());
				System.out.println();
				c = cmd.nextLine();
				System.out.println(">> " + c);				
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

}

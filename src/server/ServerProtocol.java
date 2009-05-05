package server;

public class ServerProtocol {
	
	private static String commands[][] = {
		{"PRINT_CLIENTS"},
		{"EXIT"}
	};
	
	public static String processCommand(String line) {
		String split[] = validate(line);
		if(split == null)
			return "BAD COMMAND!";
		//TODO: notify all clients of server closing, and closing them
		else if(split[0].equalsIgnoreCase("PRINT_CLIENTS")) {
			return ServerListenerThread.getClients();
		}
		else if(split[0].equalsIgnoreCase("EXIT")) {			
			return "Server closed";
		} else {
			return "BAD COMMAND!";
		}
	}
	
	/*
	 * splits line if line is a valid command
	 * else, returns null
	 */
	private static String[] validate(String line) {
		String split[] = line.split("\\s");
		for(String c[] : commands)
			if(c[0].equalsIgnoreCase(split[0]) && ((c[c.length-1].equals("...") && (c.length <= split.length + 1)) || c.length == split.length)) {
				return split;
			}	
		return null;
	}
}

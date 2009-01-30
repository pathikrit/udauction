package udAuction;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

import cmd.User;


public class LoginManager {
	
	/*
	 * users is set of all users
	 * online set of currently logged-in users
	 */
	private static HashSet<User> users, activeUsers;
	private static HashSet<String> activeCookies;
	private static Hashtable<User, String> logins;
	private static Hashtable<User, String> cookies;
	private Database db;
	
	public void newUser(User u, String psw) {
		if (!logins.contains(u)) {
			logins.put(u, psw);
			users.add(u);
		}
	}
	
	public String login(User u, String psw) {
		if (logins.get(u).equals(psw) && !users.contains(u)) {
			String cookie = generate();
			cookies.put(u, cookie);
			activeUsers.add(u);
			return cookie;
		}
		return "";
	}
	
	private String generate() {
		String cookie = "";
		do {
		for(int i = 0; i < 16; i++)
			cookie += (char) (33 + (int)(94*Math.random()));
		} while(activeCookies.contains(cookie));
		activeCookies.add(cookie);
		return cookie;
	}
	
	public boolean authenticate(User u, String cookie) {
		return cookies.get(u).equals(cookie);
	}
	
	public boolean logout(User u, String cookie) {
		Scanner sc = new Scanner(System.in);
		if (authenticate(u, cookie)) {
			db.saveInfo(u, cookie);
			cookies.remove(u);			
			activeCookies.remove(cookie);			
			activeUsers.remove(u);
			
			return true;
		}
		else {
			System.out.println("Input your password: ");
			if(logins.get(u).equals(sc.next())) {
				String real_cookie = cookies.get(u);
				db.saveInfo(u, cookie);
				cookies.remove(u);
				activeCookies.remove(real_cookie);				
				activeUsers.remove(u);
				db.saveInfo(u, cookie);
				return true;
			}
		}
		return false;
	}
	
	//TODO secure-login + authentication

}

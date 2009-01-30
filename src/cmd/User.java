package cmd;

import java.util.ArrayDeque;
import java.util.Scanner;

import udAuction.Bid;
import udAuction.Database;
import udAuction.Item;
import udAuction.LoginManager;

public class User {
	
	private ArrayDeque<Bid> bids, proposed;
	private String login, cookie; //TODO: security
	private Item matched;
	private LoginManager lm;
	private Database db;
	
	public User() {
		login = "";
		cookie = "";
	}
	
	private void login() {
		Scanner sc = new Scanner(System.in);
		while(cookie.equals("")) {
			System.out.println("Input your login: ");
			login = sc.next();
			System.out.println("Input your password: ");
			String psw = sc.next();
			cookie = lm.login(this, psw);
		}
		db.getInfo(this, cookie);
	}
	
	public void setLoginManager(LoginManager logM) {
		lm = logM;
	}
	
	public String getLogin() {
		return login;
	}
	
	public boolean equals(User u) {
		return login.equals(u.getLogin());
	}
}

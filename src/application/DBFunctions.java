package application;
import java.sql.*;
import java.util.*;

public class DBFunctions {
	public static int activeUser;
	//Searches for a username/password pair, returns id of user if a match is found
	//Returns -1 for admin account
	//Returns 0 for incorrect login
	
	public int getActiveUser() {
		return activeUser;
	}
	
	public void setActiveUser(int x) {
		activeUser = x;
		System.out.println("Active User Set to " + activeUser);
	}
	
	public int loginSearch(Connection c, String user, String pass) {
		try {
			PreparedStatement ps = c.prepareStatement("SELECT * FROM registration");
			ResultSet rs = ps.executeQuery();
			if(user.equals("admin") && pass.equals("password")) {
				return -1;
			} else {
				while (rs.next()) {
					int setID = rs.getInt("id");
					String uname = rs.getString("username");
					String upass = rs.getString("password");
					System.out.println("Checking Id: " + setID + ", User: " + uname + ", Pass: " + upass);
					if(uname.equals(user) && upass.equals(pass)) {
						return setID;
					}
				}
			}
			return 0;
		} catch(Exception e) {
			return 0;
		}
	}
	
	//Returns a 2d arraylist of strings.  Each index represents one account
	//Order: Int ref, String name, float bal
	public ArrayList<ArrayList<String>> getAccounts(Connection c, int id) {
		ArrayList<ArrayList<String>> acct = new ArrayList<ArrayList<String>>();
		boolean found = false;
		try {
			PreparedStatement ps = c.prepareStatement("SELECT * FROM accounts");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int refNum = rs.getInt("ref");
				int userID = rs.getInt("id");
				String account = rs.getString("name");
				float bal = rs.getFloat("bal");
				
				if(userID == id){
					found = true;
					System.out.println("Account found: " + refNum);
					ArrayList<String> temp = new ArrayList<String>();
					temp.add(Integer.toString(refNum));
					temp.add(account);
					temp.add(Float.toString(bal));
					acct.add(temp);
				}
			}
			if(!found) {
				System.out.println("No accounts found");
			}
			return acct;
		} catch(Exception e) {
			return acct;
		}
	}
	
	public ArrayList<ArrayList<String>> getSubs(Connection c, int id) {
		ArrayList<ArrayList<String>> subs  = new ArrayList<ArrayList<String>>();
		
		return subs;
	}
	
	//Registers a new user
	//Returns "ErrorUserNotAllowed" is the username is already taken 
	//Returns "Success" if the new user is successfully added to the database
	public String register(Connection c, String name, String pass) {
		try {
			if(pass.equals("") || pass.equals(null)) {
				return "ErrorNeedPassword";
			}
			PreparedStatement ps = c.prepareStatement("SELECT * FROM registration");
			ResultSet rs = ps.executeQuery();
			if(name.equals("admin")) {
				return "ErrorUserNotAllowed";
			} else {
				while (rs.next()) {
					String uname = rs.getString("username");
					System.out.println("Checking User: " + uname);
					if(uname.equals(name)) {
						return "ErrorUserNotAllowed";
					}
				}
			}
			System.out.println("Registering New User: " + name);
			Statement s = c.createStatement();
			String sql = "INSERT INTO registration (username, password) VALUES(\"" + name + "\", \"" + pass + "\")";
			s.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println(e);
			return "";
		}
		return "Success";
	}
	
	public String getUser(Connection c, int id) throws Exception{
		try {
			PreparedStatement ps = c.prepareStatement("SELECT * FROM registration");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int userID = rs.getInt("id");
				String uname = rs.getString("username");
				System.out.println("Checking User: " + uname);
				if(userID == id) {
					return uname;
				}
			}
			return "ErrorNoUserFound";
		} catch(Exception e) {
			System.out.println(e);
			return "ErrorNoUserFound";
		}
	}
	
	public int getTotalIncome(Connection c, int userID) {
		int total = 0;
		try {
			PreparedStatement ps = c.prepareStatement("SELECT * FROM income");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int amt = rs.getInt("amt");
				if(userID == id) {
					total += amt;
				}
			}
		} catch(Exception e) {
			System.out.println(e);
			return total;
		}
		return total;
	}
	
	public int getTotalCosts(Connection c, int userID) {
		int total = 0;
		try {
			PreparedStatement ps = c.prepareStatement("SELECT * FROM budgets");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int amt = rs.getInt("amt");
				if(userID == id) {
					total += amt;
				}
			}
			ps = c.prepareStatement("SELECT * FROM bills");
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int amt = rs.getInt("amt");
				if(userID == id) {
					total += amt;
				}
			}
		} catch(Exception e) {
			System.out.println(e);
			return total;
		}
		return total;
	}
	
	public ArrayList<String> getUserCosts(Connection c, int userID) {
		ArrayList<String> info = new ArrayList<String>();
		try {
			PreparedStatement ps = c.prepareStatement("SELECT * FROM costs");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int amt = rs.getInt("amt");
				String date = rs.getString("date");
				String desc = rs.getString("desc");
				if(userID == id) {
					info.add("$" + amt + "\t|\t" + date + "\t|\t" + desc);
				}
			}
		} catch(Exception e) {
			return null;
		}
		return info;
	}
	
	public ArrayList<String> getUserBills(Connection c, int userID) {
		ArrayList<String> info = new ArrayList<String>();
		try {
			PreparedStatement ps = c.prepareStatement("SELECT * FROM bills");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int amt = rs.getInt("amt");
				String name = rs.getString("name");
				if(userID == id) {
					info.add("$" + amt + "\t|\t" + name);
				}
			}
		} catch(Exception e) {
			return null;
		}
		return info;
	}
	
	public ArrayList<String> getUserBudgets(Connection c, int userID) {
		ArrayList<String> info = new ArrayList<String>();
		try {
			PreparedStatement ps = c.prepareStatement("SELECT * FROM budgets");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int amt = rs.getInt("amt");
				String name = rs.getString("name");
				if(userID == id) {
					info.add("$" + amt + "\t|\t" + name);
				}
			}
		} catch(Exception e) {
			return null;
		}
		return info;
	}
	
	public void newCost(Connection c, int userID, String name, int amt, String date) {
		try {
			Statement s = c.createStatement();
			String sql = "INSERT INTO costs(id, amt, date, desc) VALUES(" + userID + ", " + amt + ", \"" + date + "\", \"" + name + "\")";
			s.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void newBill(Connection c, int userID, int amt, String name) {
		try {
			Statement s = c.createStatement();
			String sql = "INSERT INTO bills(id, amt, name) VALUES(" + userID + ", " + amt + ", \"" + name + "\")";
			s.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void newBudget(Connection c, int userID, int amt, String name) {
		try {
			Statement s = c.createStatement();
			String sql = "INSERT INTO budgets(id, amt, name) VALUES(" + userID + ", " + amt + ", \"" + name + "\")";
			s.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public ArrayList<String> getRoster(Connection c) {
		ArrayList<String> users = new ArrayList<String>();
		try {
			PreparedStatement ps = c.prepareStatement("SELECT * FROM registration");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("username");
				if(id != -1) {
					users.add(id + "\t\t|\t\t" + name);
				}
			}
		} catch(Exception e) {
			return null;
		}
		return users;
	}
}



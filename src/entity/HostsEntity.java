package entity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HostsEntity {
	private Logger logMsg = Logger.getGlobal();
	
	private String adminNo, name, date, time;
	private String[] userID = new String[11], userName = new String[11];
	private int sportsType;
	
	public HostsEntity(String adminNo, String name, String date, String time, String[] userID, String[] userName, int sportsType) {
		if (userID.length == this.userID.length) {
			if (userName.length == this.userName.length) {
				this.adminNo = adminNo;
				this.name = name;
				this.date = date;
				this.time = time;
				this.userID = userID;
				this.userName = userName;
				this.sportsType = sportsType;
			}
			else {
				logMsg.log(Level.SEVERE, "String[] userName pointer length doesn't match.", new Exception("Please change the size to " + this.userName.length + "."));
			}
		}
		else {
			logMsg.log(Level.SEVERE, "String[] userID pointer length doesn't match.", new Exception("Please change the size to " + this.userID.length + "."));
		}
	}
	
	public String getAdminNo() {
		return adminNo;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}
	
	public String[] getUserID() {
		return userID;
	}
	
	public String[] getUserName() {
		return userName;
	}
	
	public int getSportsType() {
		return sportsType;
	}
}

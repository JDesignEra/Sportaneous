package entity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HostsEntity {
	private Logger logMsg = Logger.getGlobal();
	
	private String adminNo, name, date, time, sport, facility;
	private String[] userID, userName;
	private int sportType;
	private boolean equipment;
	
	public HostsEntity(String adminNo, String name, String date, String time, String sport, String facility, String[] userID, String[] userName, int sportType, boolean equipment) {
		if (userID.length == userName.length) {
			this.adminNo = adminNo;
			this.name = name;
			this.date = date;
			this.time = time;
			this.sport = sport;
			this.userID = userID;
			this.userName = userName;
			this.sportType = sportType;
			this.equipment = equipment;
		}
		else {
			logMsg.log(Level.SEVERE, "String[] userID length does not match with userName length.", new Exception("Please change the size of of userID[] or userName[]."));
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
	
	public String getSport() {
		return sport;
	}
	
	public String getFacility() {
		return facility;
	}
	
	public String[] getUserID() {
		return userID;
	}
	
	public String[] getUserName() {
		return userName;
	}
	
	public int getSportType() {
		return sportType;
	}
	
	public boolean getEquipment() {
		return equipment;
	}
}

package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class HostsEntity implements Serializable {
	private static final long serialVersionUID = -821229972284685153L;

	private String adminNo, name, facility;
	private LocalTime time;
	private LocalDate date;
	private String[] userID = new String[11], userName = new String[11];
	private int sportsType;

	private ArrayList<String> playersRecruited;

	public HostsEntity(String adminNo, String name, LocalDate date, LocalTime time, int sportsType, ArrayList<String> playersRecruited, String facility) {

		if (userID.length == this.userID.length) {
			if (userName.length == this.userName.length) {
				this.adminNo = adminNo;
				this.name = name;
				this.date = date;
				this.time = time;
				this.sportsType = sportsType;
				this.playersRecruited = playersRecruited;
				this.facility = facility;
			}
		}
	}

	public String getAdminNo() {
		return adminNo;
	}

	public String getName() {
		return name;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getTime() {
		return time;
	}

	public int getSportsType() {
		return sportsType;
	}

	public ArrayList<String> getPlayersRecruited() {
		return playersRecruited;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

}
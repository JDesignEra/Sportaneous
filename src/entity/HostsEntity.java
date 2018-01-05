package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class HostsEntity implements Serializable {

	private static final long serialVersionUID = -821229972284685153L;
	private String adminNo, name, time;
	private LocalDate date;
	private String[] userID = new String[11], userName = new String[11];
	private int sportsType;
	private ArrayList<String> playersRecruited;

	public HostsEntity(String adminNo, String name, LocalDate date, String time, int sportsType, ArrayList<String> playersRecruited) {
		if (userID.length == this.userID.length) {
			if (userName.length == this.userName.length) {
				this.adminNo = adminNo;
				this.name = name;
				this.date = date;
				this.time = time;
				this.sportsType = sportsType;
				this.playersRecruited = playersRecruited;
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

	public String getTime() {
		return time;
	}

	public int getSportsType() {
		return sportsType;
	}

	public ArrayList<String> getPlayersRecruited() {
		return playersRecruited;
	}

}
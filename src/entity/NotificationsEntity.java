package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NotificationsEntity implements Serializable {
	private String adminNo, name, sports, location, date, time;
	private int status;

	public NotificationsEntity(String adminNo, String name, String sports, String location, String date, String time, int status) {
		this.adminNo = adminNo;
		this.name = name;
		this.sports = sports;
		this.location = location;
		this.date = date;
		this.time = time;
		this.status = status;
	}

	public String getAdminNo() {
		return adminNo;
	}

	public String getName() {
		return name;
	}

	public String getSports() {
		return sports;
	}

	public String getLocation() {
		return location;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public int getStatus() {
		return status;
	}

	public void setName(String name) {
		this.name = name;
	}
}

package entity;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class NotificationsEntity implements Serializable {
	private String adminNo, name, sports, location;
	private LocalDateTime dateTime;
	private int status;

	public NotificationsEntity(String adminNo, String name, String sports, String location, LocalDateTime datetime, int status) {
		this.setAdminNo(adminNo);
		this.setName(name);
		this.setSports(sports);
		this.setLocation(location);
		this.setDateTime(datetime);
		this.setStatus(status);
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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setAdminNo(String adminNo) {
		this.adminNo = adminNo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSports(String sports) {
		this.sports = sports;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

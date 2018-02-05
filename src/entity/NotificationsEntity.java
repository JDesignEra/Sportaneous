package entity;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class NotificationsEntity implements Serializable {
	private String adminNo, hostName, hostAd, sports, location;
	private LocalDateTime dateTime;
	private int status;

	public NotificationsEntity(String userAdminNo, String userName, String hostName, String hostAd, String sports, String location, LocalDateTime datetime, int status) {
		this.adminNo = userAdminNo;
		this.userName = userName;
		this.hostName = hostName;
		this.hostAd = hostAd;
		this.sports = sports;
		this.location = location;
		this.dateTime = datetime;
		this.status = status;
	}

	public String getAdminNo() {
		return adminNo;
	}

	public String getHostName() {
		return hostName;
	}
	
	public String getHostAd() {
		return hostAd;
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

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public void setHostAd(String hostAd) {
		this.hostAd = hostAd;
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

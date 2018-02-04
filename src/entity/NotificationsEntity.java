package entity;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class NotificationsEntity implements Serializable {
	private String adminNo, hostName, hostAd, sports, location, userName;
	private LocalDateTime dateTime;
	private int status;

	public NotificationsEntity(String userAdminNo, String userName, String hostName, String hostAd, String sports, String location, LocalDateTime datetime, int status) {
		this.setAdminNo(userAdminNo);
		this.setHostName(hostName);
		this.setSports(sports);
		this.setLocation(location);
		this.setDateTime(datetime);
		this.setStatus(status);
		this.setHostAd(hostAd);
		this.setUserName(userName);
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

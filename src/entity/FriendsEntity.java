package entity;

import java.io.Serializable;

public class FriendsEntity implements Serializable {
	private static final long serialVersionUID = 3790262896588918586L;
	private String userID;
	private String friendID;
	private int status;

	public FriendsEntity(String userID, String friendID, int status) {
		this.setUserID(userID);
		this.friendID = friendID;
		this.status = status;
	}

	public String getFriendsID() {
		return friendID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}

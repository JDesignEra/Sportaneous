package entity;

import java.io.Serializable;

public class FriendsEntity implements Serializable {
	private String userID1, userID2, userName1, userName2;
	private int status;
	
	public FriendsEntity(String userID1, String userID2, String userName1, String userName2, int status) {
		this.userID1 = userID1;
		this.userID2 = userID2;
		this.userName1 = userName1;
		this.userName2 = userName2;
		this.status = status;
	}
	
	public String getUserID1() {
		return userID1;
	}
	
	public String getUserID2() {
		return userID2;
	}
	
	public String getUserName1() {
		return userName1;
	}
	
	public String getUserName2() {
		return userName2;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}
	
	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
}

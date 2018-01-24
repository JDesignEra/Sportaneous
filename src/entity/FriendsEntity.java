package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FriendsEntity implements Serializable {
	private String userAdminNo, friendAdminNo;
	private int status;

	public FriendsEntity(String userAdminNo, String friendAdminNo, int status) {
		this.setUserAdminNo(userAdminNo);
		this.setFriendAdminNo(friendAdminNo);
		this.status = status;
	}

	public String getUserAdminNo() {
		return userAdminNo;
	}

	public String getFriendAdminNo() {
		return friendAdminNo;
	}

	public int getStatus() {
		return status;
	}

	public void setUserAdminNo(String userAdminNo) {
		this.userAdminNo = userAdminNo;
	}

	public void setFriendAdminNo(String friendAdminNo) {
		this.friendAdminNo = friendAdminNo;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

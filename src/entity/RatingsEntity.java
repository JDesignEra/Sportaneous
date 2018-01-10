package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RatingsEntity implements Serializable {
	private static final long serialVersionUID = -280347867690637374L;
	private String status, adminNo, userName;
	private int noRate, uid;
	private double userRating;

	public RatingsEntity(int uid, String status, String adminNo, String userName, int noRate, double userRating) {
       this.uid = uid;                            // to identify the sets of group
       this.status = status;                      // to know if they completed the rating 
       this.adminNo = adminNo;
       this.userName = userName;
       this.noRate = noRate;  
       this.userRating = userRating ;
	}

	public int getUid(){
		return uid;
	}
	public String getStatus() {
		return status;
	}

	public String getUserID() {
		return adminNo;
	}

	public String getUserName() {
		return userName;
	}

	public int getNoRate() {
		return noRate;
	}

	public double getUserRating() {
		return userRating;
	}
    public void setUid(int uid){
    	this.uid = uid;   	
    }
	public void setStatus(String status) {
		this.status = status;
	}

	public void setadminNo(String adminNo) {
		 this.adminNo = adminNo;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setNoRate(int noRate) {
		this.noRate = noRate;
	}

	public void setUserRating(double userRating) {
		this.userRating = userRating;
	}
	
}

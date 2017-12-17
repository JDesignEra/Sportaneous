package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RatingsEntity implements Serializable {
	private final Logger logMsg = Logger.getGlobal();
	
	private String status;
	private String[] userID = new String[12], userName = new String[12];
	private int[] noRate = new int[12];
	private BigDecimal[] userRating = new BigDecimal[12];
	
	public RatingsEntity(String status, String[] userID, String[] userName, int[] noRate, BigDecimal[] userRating) {
		if (userID.length == this.userID.length) {
			if (userName.length == this.userName.length) {
				if (noRate.length == this.noRate.length) {
					if (userRating.length == this.userRating.length) {
						this.status = status;
						this.userID = userID;
						this.userName = userName;
						this.noRate = noRate;
						this.userRating = userRating;
					}
					else {
						logMsg.log(Level.SEVERE, "BigDecimal[] userRating pointer length doesn't match.", new Exception("Please change the size to " + this.userRating.length + "."));
					}
				}
				else {
					logMsg.log(Level.SEVERE, "int[] noRate pointer length doesn't match.", new Exception("Please change the size to " + this.noRate.length + "."));
				}
			}
			else {
				logMsg.log(Level.SEVERE, "String[] userName pointer length doesn't match.", new Exception("Please change the size to " + this.userName.length + "."));
			}
		}
		else {
			logMsg.log(Level.SEVERE, "String[] userID pointer length doesn't match.", new Exception("Please change the size to " + this.userID.length + "."));
		}
	}
	
	public String getStatus() {
		return status;
	}
	
	public String[] getUserID() {
		return userID;
	}
	
	public String[] getUserName() {
		return userName;
	}
	
	public int[] getNoRate() {
		return noRate;
	}
	
	public BigDecimal[] getUserRating() {
		return userRating;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setUserID(String[] userID) {
		if (userID.length == this.userID.length) {
			this.userID = userID;
		}
		else {
			logMsg.log(Level.SEVERE, "String[] userID pointer length doesn't match.", new Exception("Please change the size to " + this.userID.length + "."));
		}
	}
	
	public void setUserName(String[] userName) {
		if (userName.length == this.userName.length) {
			this.userName = userName;
		}
		else {
			logMsg.log(Level.SEVERE, "String[] userName pointer length doesn't match.", new Exception("Please change the size to " + this.userName.length + "."));
		}
	}
	
	public void setNoRate(int[] noRate) {
		if (noRate.length == this.noRate.length) {
			this.noRate = noRate;
		}
		else {
			logMsg.log(Level.SEVERE, "int[] noRate pointer length doesn't match.", new Exception("Please change the size to " + this.noRate.length + "."));
		}
	}
	
	public void setUserRating(BigDecimal[] userRating) {
		if (userRating.length == this.userRating.length) {
			this.userRating = userRating;
		}
		else {
			logMsg.log(Level.SEVERE, "BigDecimal[] userRating pointer length doesn't match.", new Exception("Please change the size to " + this.userRating.length + "."));
		}
	}
}

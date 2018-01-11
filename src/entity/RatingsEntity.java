package entity;

import java.io.Serializable;

public class RatingsEntity implements Serializable {
	private static final long serialVersionUID = -280347867690637374L;
	private String adminNo;
	private boolean status;
	private int[] rating;

	public RatingsEntity(String adminNo, int[] rating, boolean status) {
		if (rating.length != 5) {
			throw new IllegalArgumentException("rating argument must be a size of 5.");
		}

		this.status = status; // to know if they completed the rating
		this.adminNo = adminNo;
		this.rating = rating;
	}

	public boolean getStatus() {
		return status;
	}

	public String getAdminNo() {
		return adminNo;
	}

	public int[] getRating() {
		return rating;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setAdminNo(String adminNo) {
		this.adminNo = adminNo;
	}

	public void setRating(int[] rating) {
		if (rating.length != 5) {
			throw new IllegalArgumentException("rating argument must be the length of 5.");
		}

		this.rating = rating;
	}
}

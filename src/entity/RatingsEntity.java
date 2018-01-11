package entity;

import java.io.Serializable;

public class RatingsEntity implements Serializable {
	private static final long serialVersionUID = -280347867690637374L;
	private String matchID;
	private String[] adminNums, comments;
	private int[] ratings;
	private boolean[] attendances;
	private int noRated; // Check if all players rated

	public RatingsEntity(String matchID, String[] adminNums, String[] comments, int[] ratings, boolean[] attendances, int noRated) {
		if (comments.length != adminNums.length && ratings.length != adminNums.length && attendances.length != adminNums.length) {
			throw new IllegalArgumentException("comments, adminNums, ratings and attendances argument size must be the same size of adminNums");
		}

		this.setMatchID(matchID);
		this.adminNums = adminNums;
		this.setComments(comments);
		this.ratings = ratings;
		this.attendances = attendances;
		this.noRated = noRated;
	}

	public String getMatchID() {
		return matchID;
	}

	public String[] getAdminNums() {
		return adminNums;
	}

	public String[] getComments() {
		return comments;
	}

	public int[] getRatings() {
		return ratings;
	}

	public boolean[] getAttendances() {
		return attendances;
	}

	public int getNoRated() {
		return noRated;
	}

	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}

	public void setAdminNums(String[] adminNums) {
		this.adminNums = adminNums;
	}

	public void setComments(String[] comments) {
		if (comments.length != adminNums.length) {
			throw new IllegalArgumentException("comments argument size must be the same size of adminNums");
		}

		this.comments = comments;
	}

	public void setRatings(int[] ratings) {
		if (ratings.length != adminNums.length) {
			throw new IllegalArgumentException("adminNums argument size must be the same size of adminNums");
		}

		this.ratings = ratings;
	}

	public void setAttendances(boolean[] attendances) {
		if (attendances.length != adminNums.length) {
			throw new IllegalArgumentException("attendances argument size must be the same size of adminNums");
		}

		this.attendances = attendances;
	}

	public void setStatus(int noRated) {
		this.noRated = noRated;
	}

	public int incrementAndGetNoRate() {
		return ++this.noRated;
	}
}

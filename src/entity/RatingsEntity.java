package entity;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class RatingsEntity implements Serializable {
	private String hostAdminNo, sport;
	private LocalDateTime dateTime;
	private String[] adminNums, comments;
	private int[] ratings;
	private int[] attendanceCount;
	private int matchID, noRated; // Check if all players rated

	public RatingsEntity(	int matchID, String hostAdminNo, String sport, LocalDateTime dateTime, String[] adminNums, String[] comments, int[] ratings, int[] attendanceCount,
							int noRated) {
		if (comments.length != adminNums.length && ratings.length != adminNums.length && attendanceCount.length != adminNums.length) {
			throw new IllegalArgumentException("comments, adminNums, ratings and attendances argument size must be the same size of adminNums");
		}

		this.matchID = matchID;
		this.hostAdminNo = hostAdminNo;
		this.sport = sport;
		this.dateTime = dateTime;
		this.adminNums = adminNums;
		this.comments = comments;
		this.ratings = ratings;
		this.attendanceCount = attendanceCount;
		this.noRated = noRated;
	}

	public int getMatchID() {
		return matchID;
	}

	public String getHostAdminNo() {
		return hostAdminNo;
	}

	public String getSport() {
		return sport;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
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

	public int[] getAttendanceCount() {
		return attendanceCount;
	}

	public int getNoRated() {
		return noRated;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

	public void setHostAdminNo(String hostAdminNo) {
		this.hostAdminNo = hostAdminNo;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
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

	public void setAttendanceCount(int[] attendanceCount) {
		if (attendanceCount.length != adminNums.length) {
			throw new IllegalArgumentException("attendances argument size must be the same size of adminNums");
		}

		this.attendanceCount = attendanceCount;
	}
	
	public void increaseAttendanceCount(int index) {
		this.attendanceCount[index] += 1;
	}

	public void setNoRated(int noRated) {
		this.noRated = noRated;
	}

	public int incrementAndGetNoRate() {
		noRated += 1;
		return noRated;
	}
}

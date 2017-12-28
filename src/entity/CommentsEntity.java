package entity;

import java.io.Serializable;

public class CommentsEntity implements Serializable {
	private String adminNo, name, comment;
	private double rating;

	public CommentsEntity(String adminNo, String name, String comment, double rating) {
		this.adminNo = adminNo;
		this.name = name;
		this.comment = comment;
		this.rating = rating;
	}

	public String getAdminNo() {
		return adminNo;
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public double getRating() {
		return rating;
	}
}

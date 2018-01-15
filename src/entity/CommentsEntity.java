package entity;

import java.io.Serializable;

public class CommentsEntity implements Serializable {
	private static final long serialVersionUID = 8118192445132088506L;

	private String adminNo, name, comment;
	private int rating;

	public CommentsEntity(String adminNo, String name, String comment, int rating) {
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

	public int getRating() {
		return rating;
	}
}

package entity;

import java.io.Serializable;

public class AccountsEntity implements Serializable {
	private static final long serialVersionUID = 3819033989766829058L;
	private String adminNo, email, password, name, favSport, interestedSports, intro, matchID;
	private double height, weight;
	private double[] rating;
	private int matchPlayed, totalMatch;
	private boolean heightVisibility, weightVisibility;

	public AccountsEntity(	String adminNo, String email, String password, String name, String favSport, String interestedSports, String intro, String matchID, double height,
							double weight, boolean heightVisibility, boolean weightVisibility, double[] rating,int matchPlayed, int totalMatched) {
		if (rating.length != 5) {
			throw new IllegalArgumentException("rating argument has to be the length of 5.");
		}
		
		this.adminNo = adminNo;
		this.email = email;
		this.password = password;
		this.name = name;
		this.favSport = favSport;
		this.interestedSports = interestedSports;
		this.intro = intro;
		this.matchID = matchID;
		this.height = height;
		this.weight = weight;
		this.heightVisibility = heightVisibility;
		this.weightVisibility = weightVisibility;
		this.rating = rating;
		this.matchPlayed = matchPlayed;
		this.totalMatch = totalMatched;
	}

	public String getAdminNo() {
		return adminNo;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getFavSport() {
		return favSport;
	}

	public String getInterestedSports() {
		return interestedSports;
	}

	public String getIntro() {
		return intro;
	}

	public String getMatchID() {
		return matchID;
	}

	public double getHeight() {
		return height;
	}

	public double getWeight() {
		return weight;
	}

	public boolean getHeightVisibility() {
		return heightVisibility;
	}

	public boolean getWeightVisibility() {
		return weightVisibility;
	}

	public double[] getRating() {
		return rating;
	}

	public int getMatchPlayed() {
		return matchPlayed;
	}

	public int getTotalMatch() {
		return totalMatch;
	}

	public void setAdminNo(String adminNo) {
		this.adminNo = adminNo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFavSport(String favSport) {
		this.favSport = favSport;
	}

	public void setInterestedSports(String interestedSports) {
		this.interestedSports = interestedSports;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setHeightVisibility(boolean heightVisibility) {
		this.heightVisibility = heightVisibility;
	}

	public void setWeightVisibility(boolean weightVisibility) {
		this.weightVisibility = weightVisibility;
	}

	public void setRating(double[] rating) {
		if (rating.length != 5) {
			throw new IllegalArgumentException("rating argument has to be the length of 5");
		}
		
		this.rating = rating;
	}

	public void setMatchPlayed(int matchPlayed) {
		this.matchPlayed = matchPlayed;
	}

	public void setTotalMatch(int totalMatch) {
		this.totalMatch = totalMatch;
	}

}

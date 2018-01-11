package entity;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RatingsEntity implements Serializable {
	private static final long serialVersionUID = -280347867690637374L;
	private String status, adminNo, userName;
	private int UID;
	private int[] rating = new int [5];
	private int [] noPlayer = new int [5];  // Number of player rated for each number of stars
	private int [] noStars = new int [5];
	private int totalStars = 0 ;
	 
	public RatingsEntity(int UID, String status, String adminNo, String userName, int [] rating, int [] noPlayer, int [] noStars, int totalStars) {
       this.UID = UID;                            // to identify the sets of group
       this.status = status;                      // to know if they completed the rating 
       this.adminNo = adminNo;
       this.userName = userName;
       this.rating = rating;  
       this.noPlayer = noPlayer;
       this.noStars = noStars;
       this.totalStars = totalStars;
	}

	public int getUID(){
		return UID;
	}
	public String getStatus() {
		return status;
	}

	public String getAdminNo() {
		return adminNo;
	}

	public String getUserName() {
		return userName;
	}

	public int[] getRating() {
		return rating;
	}
	
	public int[] getNoPlayer(){
		return noPlayer;
	}
	
	public int[] getnoStars(){
		return noStars;
	}
	
	public int getToatlStar(){
		return totalStars;
	}
	
    public void setUid(int uid){
    	this.UID = UID;   	
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
	
	public void setNoPlayer(int [] noPlayer){
		this.noPlayer = noPlayer;
	}
	
	public void setNoStars(int[] noStars){
		this.noStars = noStars;
	}
	
	public void setTotalStars(int totalStars){
		this.totalStars = totalStars;
	}

	public void setRating(int[] rating) {
		this.rating = rating;
	}
	
	public int calculateRating(){
		rating[0] = 1;
		rating[1] = 2;
		rating[2] = 3;
		rating[3] = 4;
		rating[4] = 5;
		
		for(int i=0 ; i<5 ;i++){	
		noStars[i] = rating[i]* noPlayer[i];
		}
		
		for(int i=0; i<5; i++){
			 totalStars += noStars[i];
		}
		return totalStars;
		
	}
	
}

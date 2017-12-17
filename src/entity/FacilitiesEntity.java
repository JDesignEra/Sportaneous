package entity;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacilitiesEntity implements Serializable {
	private final Logger logMsg = Logger.getGlobal();
	
	// badmintonCourt, basketballCourt, cageField, indoorBasketballCourt, soccerField, squashCourt, tennisCourt
	private int[] facilities = new int[7];
	
	/**
	 * @param facilities
	 * badmintonCourt, basketballCourt, cageField, indoorBasketballCourt, soccerField, squashCourt, tennisCourt
	 */
	public FacilitiesEntity(int[] facilities) {
		if (facilities.length == this.facilities.length) {
			this.facilities = facilities;
		}
		else {
			logMsg.log(Level.SEVERE, "int[] facilities pointer length doesn't match.", new Exception("Please change the size to " + this.facilities.length + "."));
		}
	}
	
	public int[] getFacilities() {
		return facilities;
	}
	
	public int getBadmintonCourt() {
		return facilities[0];
	}
	
	public int getBasketballCourt() {
		return facilities[1];
	}
	
	public int getCageField() {
		return facilities[2];
	}
	
	public int getIndoorBasketballCourt() {
		return facilities[3];
	}
	
	public int getSoccerField() {
		return facilities[4];
	}
	
	public int getSquashCourt() {
		return facilities[5];
	}
	
	public int getTennisCourt() {
		return facilities[6];
	}
	
	public void setBadmintonCourt(int badmintonCourt) {
		this.facilities[0] = badmintonCourt;
	}
	
	public void setBasketballCourt(int basketballCourt) {
		this.facilities[1] = basketballCourt;
	}
	
	public void setCageField(int cageField) {
		this.facilities[2] = cageField;
	}
	
	public void setIndoorBasketballCourt(int indoorBasketballCourt) {
		this.facilities[3] = indoorBasketballCourt;
	}
	
	public void setSoccerField(int soccerField) {
		this.facilities[4] = soccerField;
	}
	
	public void setSquashCourt(int squashCourt) {
		this.facilities[5] = squashCourt;
	}
	
	public void setTennisCourt(int tennisCourt) {
		this.facilities[6] = tennisCourt;
	}
}

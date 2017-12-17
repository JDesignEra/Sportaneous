package entity;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EquipmentsEntity implements Serializable {
	private final Logger logMsg = Logger.getGlobal();
	
	// badminton, basketball, frisbee, soccer, squash, tennis
	private int[] equipments = new int[6];
	
	
	/**
	 * @param equipments
	 * badminton, basketball, frisbee, soccer, squash, tennis
	 */
	public EquipmentsEntity(int[] equipments) {
		if (equipments.length == this.equipments.length) {
			this.equipments = equipments;
		}
		else {
			logMsg.log(Level.SEVERE, "int[] equipments pointer length doesn't match.", new Exception("Please change the size to " + this.equipments.length + "."));
		}
	}
	
	public int[] getEquipments() {
		return equipments;
	}
	
	public int getBadminton() {
		return equipments[0];
	}
	
	public int getBasketball() {
		return equipments[1];
	}
	
	public int getFrisbee() {
		return equipments[2];
	}
	
	public int getSoccer() {
		return equipments[3];
	}
	
	public int getSquash() {
		return equipments[4];
	}
	
	public int getTennis() {
		return equipments[5];
	}
	
	public void setBadminton(int badminton) {
		this.equipments[0] = badminton;
	}
	
	public void setBasketball(int basketball) {
		this.equipments[1] = basketball;
	}
	
	public void setFrisbee(int frisbee) {
		this.equipments[2] = frisbee;
	}
	
	public void setSoccer(int soccer) {
		this.equipments[3] = soccer;
	}
	
	public void setSquash(int squash) {
		this.equipments[4] = squash;
	}
	
	public void setTennis(int tennis) {
		this.equipments[5] = tennis;
	}
}

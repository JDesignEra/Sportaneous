package entity;

import java.io.Serializable;

public class EquipmentsEntity implements Serializable {
	private String sport;
	private int equipmentQty;
	
	public EquipmentsEntity(String sports, int equipmentQty) {
		this.sport = sports;
		this.equipmentQty = equipmentQty;
	}
	
	public String getSports() {
		return sport;
	}
	
	public int getEquipmentQty() {
		return equipmentQty;
	}
	
	public void setSports(String equipmentName) {
		this.sport = equipmentName;
	}
	
	public void setEquipmentQty(int equipmentQty) {
		this.equipmentQty = equipmentQty;
	}
}

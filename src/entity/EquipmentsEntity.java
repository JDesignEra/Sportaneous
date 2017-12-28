package entity;

import java.io.Serializable;

public class EquipmentsEntity implements Serializable {
	private static final long serialVersionUID = 7674994437516139215L;
	private String sport;
	private int equipmentQty;
	
	public EquipmentsEntity(String sports, int equipmentQty) {
		this.sport = sports;
		this.equipmentQty = equipmentQty;
	}
	
	public String getSport() {
		return sport;
	}
	
	public int getEquipmentQty() {
		return equipmentQty;
	}
	
	public void setSport(String equipmentName) {
		this.sport = equipmentName;
	}
	
	public void setEquipmentQty(int equipmentQty) {
		this.equipmentQty = equipmentQty;
	}
}

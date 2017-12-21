package entity;

public class EquipmentsEntity {
	private String sports;
	private int equipmentQty;
	
	public EquipmentsEntity(String sports, int equipmentQty) {
		this.sports = sports;
		this.equipmentQty = equipmentQty;
	}
	
	public String getSports() {
		return sports;
	}
	
	public int getEquipmentQty() {
		return equipmentQty;
	}
	
	public void setSports(String equipmentName) {
		this.sports = equipmentName;
	}
	
	public void setEquipmentQty(int equipmentQty) {
		this.equipmentQty = equipmentQty;
	}
}

package entity;

import java.io.Serializable;

public class FacilitiesEntity implements Serializable {
	private String facilityName;
	private int facilityQty;
	
	public FacilitiesEntity(String facilityName, int facilityQty) {
		this.facilityName = facilityName;
		this.facilityQty = facilityQty;
	}
	
	public String getFacilityName() {
		return facilityName;
	}
	
	public int getFacilityQty() {
		return facilityQty;
	}
}

package dataAccess;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.EquipmentsEntity;

public class EquipmentDA {
	
	private static DB db;
	private static ConcurrentMap<String, EquipmentsEntity> equipments;
	
	public static void initDA() {
		db = DBMaker
				.newFileDB(new File("tmp/equipments.db"))
				.closeOnJvmShutdown()
				.make();
		
		equipments = db.getTreeMap("equipments");
		
		
		if (equipments.keySet().isEmpty()) {
			equipments.put("badminton", new EquipmentsEntity("Badminton", 100));
			equipments.put("basketball", new EquipmentsEntity("Basketball", 100));
			equipments.put("frisbee", new EquipmentsEntity("Frisbee", 100));
			equipments.put("soccer", new EquipmentsEntity("Soccer", 100));
			equipments.put("squash", new EquipmentsEntity("Squash", 100));
			equipments.put("tennis", new EquipmentsEntity("Tennis", 100));
		}
			
		db.commit();

	}
	
	public static int rentEquipment(String sport) {
		int equipmentsQty = equipments.get(sport).getEquipmentQty();
		
		if (equipments.get(sport).getEquipmentQty() <= 0) {
			return 0; // fail
		}
		else {
			equipments.replace(sport, new EquipmentsEntity(sport, --equipmentsQty));
		}
		
		db.commit();
		
		return equipmentsQty;
	}
	
	public static int returnEquipment(String sport) {
		
		int equipmentQty = equipments.get(sport).getEquipmentQty();
	
		equipments.replace(sport, new EquipmentsEntity(sport, ++equipmentQty));
		
		db.commit();
		
		return equipmentQty;
	}
	
	
	public static Object[][] getAllData() {
		Object[][] data = new Object[equipments.size()][2];
			
		int i = 0;
		
		for (EquipmentsEntity equipmentsEntity : equipments.values()) {
			data[i][0] = equipmentsEntity.getSport();
			data[i][1] = equipmentsEntity.getEquipmentQty();
			
			i++;
		}
		
		return data;
	}
	
	public static void addEquipment(String sport, int no) {
		
		if (no > 0) {
			
			equipments.replace(sport, new EquipmentsEntity(sport, equipments.get(sport).getEquipmentQty()+no));
			
			db.commit();
		}

	}
	
	public static void removeEquipment(String sport, int no) {
		
		if (no > 0) {
			if (equipments.get(sport).getEquipmentQty() - no >= 0) {
				
				equipments.replace(sport, new EquipmentsEntity(sport, equipments.get(sport).getEquipmentQty()-no));
				
				db.commit();
			}
		}
		
	}
	
	public static void main(String[] args) {
		initDA();
		
		for (int i = 0; i < getAllData().length; i++) {
			for (Object j : getAllData()[i]) {
				System.out.println(j);
			}
		}
	}
}

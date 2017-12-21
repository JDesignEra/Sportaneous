package dataAccess;

import java.io.File;
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
		
		/*
		equipments.put("Badminton", new EquipmentsEntity("Badminton", 10));
		equipments.put("Basketball", new EquipmentsEntity("Basketball", 10));
		equipments.put("Frisbee", new EquipmentsEntity("Frisbee", 10));
		equipments.put("Soccer", new EquipmentsEntity("Soccer", 10));
		equipments.put("Squash", new EquipmentsEntity("Squash", 10));
		equipments.put("Tennis", new EquipmentsEntity("Tennis", 10));
		*/
		db.commit();
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
		
		equipments.replace(sport, new EquipmentsEntity(sport, equipmentQty));
		
		db.commit();
		
		return equipmentQty;
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

package dataAccess;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.EquipmentsEntity;

public class EquipmentDA {
	
	private static DB db;
	private static DB db1;
	private static ConcurrentMap<String, EquipmentsEntity> equipments;
	private static ConcurrentMap<String, Integer> stock;
	
	public static void initDA() {
		db = DBMaker
				.newFileDB(new File("tmp/equipments.db"))
				.closeOnJvmShutdown()
				.make();
		
		db1 = DBMaker
				.newFileDB(new File("tmp/equipmentStock.db"))
				.closeOnJvmShutdown()
				.make();
		
		equipments = db.getTreeMap("equipments");
		
		stock = db1.getTreeMap("tmp/equipmentStock.db");
		
		
		if (equipments.keySet().isEmpty()) {
			equipments.put("badminton", new EquipmentsEntity("Badminton", 100));
			equipments.put("basketball", new EquipmentsEntity("Basketball", 100));
			equipments.put("frisbee", new EquipmentsEntity("Frisbee", 100));
			equipments.put("soccer", new EquipmentsEntity("Soccer", 100));
			equipments.put("squash", new EquipmentsEntity("Squash", 100));
			equipments.put("tennis", new EquipmentsEntity("Tennis", 100));
		}
		
		if (stock.keySet().isEmpty()) {
			stock.put("badminton", 100);
			stock.put("basketball", 100);
			stock.put("frisbee", 100);
			stock.put("soccer", 100);
			stock.put("squash", 100);
			stock.put("tennis", 100);
		}
		

		
		db.commit();
		db1.commit();
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
		
		if (equipmentQty+1 <= stock.get(sport)) {
			equipments.replace(sport, new EquipmentsEntity(sport, ++equipmentQty));
		} 
		
		db.commit();
		
		return equipmentQty;
	}
	
	public static int checkStock(String sport) {
		
		return stock.get(sport) - equipments.get(sport).getEquipmentQty();
		
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
	
	public static void addStock(String sport, int no) {
		
		if (no > 0) {
			int newno = stock.get(sport) + no;
			stock.replace(sport, newno);
			int newno1 = equipments.get(sport).getEquipmentQty() + no;
			equipments.replace(sport, new EquipmentsEntity(sport, newno1));
			
			db.commit();
			db1.commit();
		}
		
		System.out.print("Total stock for " + sport + " after adding: " + stock.get(sport));
	}
	
	public static void removeStock(String sport, int no) {
		
		if (no > 0) {
			if (equipments.get(sport).getEquipmentQty() - no >= 0) {
				int newno = equipments.get(sport).getEquipmentQty() - no;
				equipments.replace(sport, new EquipmentsEntity(sport, newno));
				stock.replace(sport, stock.get(sport)-no);
				
				db.commit();
				db1.commit();
			}
		}
		
		System.out.println("Total stock for " + sport + " after removing: " + stock.get(sport));
		
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

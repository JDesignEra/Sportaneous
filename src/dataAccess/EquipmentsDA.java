package dataAccess;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.EquipmentsEntity;

public class EquipmentsDA {
	private static DB db;
	private static DB db2;
	private static ConcurrentMap<String, EquipmentsEntity> equipments;
	private static ConcurrentMap<String, HashMap<String, Integer>> loanbook; //admin, HMAP<sportsName, Integer>

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/equipments.db")).closeOnJvmShutdown().make();
		db2 = DBMaker.newFileDB(new File("tmp/equipmentsLoanBook.db")).closeOnJvmShutdown().make();
		
		equipments = db.getTreeMap("equipments");
		loanbook = db2.getTreeMap("equipmentsLoanBook.db");

	}
	
	public static boolean equipmentIsAvailable(String sport) {
		
		if (equipments.get(sport).getEquipmentQty() > 0) {
			return true;
		}
		
		return false;
	}

	public static int rentEquipment(String adminNo, String sport) {
		String admin = adminNo.toLowerCase();
		int equipmentsQty = equipments.get(sport).getEquipmentQty();
		
		if (!loanbook.containsKey(admin)) {
			if (equipmentIsAvailable(sport)) {
				HashMap<String, Integer> record = new HashMap<String, Integer>();
				record.put(sport, 1);
				loanbook.putIfAbsent(admin, record);
				equipments.replace(sport, new EquipmentsEntity(sport, --equipmentsQty));
				
			} else {
				return 0;
			}
		} else {
			int totalBorrowed = 0;
			try {
				for (int i : loanbook.get(admin).values()) {
					totalBorrowed += i;
				}
			} catch (Exception e){
				
			}
			
			if (equipmentIsAvailable(sport)) {
				
				if (totalBorrowed == 0) {
					HashMap<String, Integer> record = loanbook.get(admin);
					record.put(sport, 1);
					loanbook.replace(admin, record);
					equipments.replace(sport, new EquipmentsEntity(sport, --equipmentsQty));
					
				} else {
					return 0;
				}
			}
		}
		
//		if (equipmentIsAvailable(sport) == false) {
//			return 0; // fail
//		}
//		else {
//			equipments.replace(sport, new EquipmentsEntity(sport, --equipmentsQty));
//		}
//
		db2.commit();
		db.commit();

		return 2;
	}
	
	public static void checkStudentRecord(String adminNo) {
		HashMap<String, Integer> toDisplay = loanbook.get(adminNo.toLowerCase());
		
		for (String x : toDisplay.keySet()) {
			System.out.println(x + ": \t " + loanbook.get(adminNo.toLowerCase()).get(x));
		}
	}

	public static int returnEquipment(String adminNo, String sport) {
		int equipmentQty = equipments.get(sport).getEquipmentQty();
		
		String admin = adminNo.toLowerCase();
		if (loanbook.containsKey(admin)) {
			if (equipmentIsAvailable(sport)) {
				loanbook.putIfAbsent(admin, null);
				equipments.replace(sport, new EquipmentsEntity(sport, ++equipmentQty));
			} else {
				return 0;
			}
		}

		db.commit();

		return 2;
	}

	public static Object[][] getAllData() {
		Object[][] data = null;

		if (!equipments.isEmpty()) {
			data = new Object[equipments.size()][2];

			int i = 0;

			for (EquipmentsEntity equipmentsEntity : equipments.values()) {
				data[i][0] = equipmentsEntity.getSport();
				data[i][1] = equipmentsEntity.getEquipmentQty();

				i++;
			}
		}

		return data;
	}

	public static void addEquipment(String sport, int no) {
		if (no > 0) {
			equipments.replace(sport, new EquipmentsEntity(sport, equipments.get(sport).getEquipmentQty() + no));

			db.commit();
		}
	}

	public static void removeEquipment(String sport, int no) {
		if (no > 0) {
			if (equipments.get(sport).getEquipmentQty() - no >= 0) {
				equipments.replace(sport, new EquipmentsEntity(sport, equipments.get(sport).getEquipmentQty() - no));

				db.commit();
			}
		}
	}

	
	public static void main(String[] args) { initDA();
		initDA();
		
//		equipments.put("Badminton", new EquipmentsEntity("Badminton", 10));
//		equipments.put("Basketball", new EquipmentsEntity("Basketball", 10));
//		equipments.put("Frisbee", new EquipmentsEntity("Frisbee", 10));
//		equipments.put("Soccer", new EquipmentsEntity("Soccer", 10));
//		equipments.put("Squash", new EquipmentsEntity("Squash", 10));
//		equipments.put("Tennis", new EquipmentsEntity("Tennis", 10));
//		db.commit();
	
		rentEquipment("170196W", "Badminton");
		checkStudentRecord("170196W");
	
		System.out.println();
	
		for (int i = 0; i < getAllData().length; i++) {
			for (Object j : getAllData()[i]) {
				System.out.println(j);
			}
		}
	
	}
	 
}

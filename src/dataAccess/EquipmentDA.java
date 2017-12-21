package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
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
		
		equipments.put("Badminton", new EquipmentsEntity("Badminton", 10));
		equipments.put("Basketball", new EquipmentsEntity("Basketball", 10));
		equipments.put("Frisbee", new EquipmentsEntity("Frisbee", 10));
		equipments.put("Soccer", new EquipmentsEntity("Soccer", 10));
		equipments.put("Squash", new EquipmentsEntity("Squash", 10));
		equipments.put("Tennis", new EquipmentsEntity("Tennis", 10));
		db.commit();
	}
	
	public static Object[][] getAllData() {
		Object[][] data = new Object[equipments.size()][2];
			
		int i = 0;
		
		for (EquipmentsEntity equipmentsEntity : equipments.values()) {
			data[i][0] = equipmentsEntity.getSports();
			data[i][1] = equipmentsEntity.getEquipmentQty();
		}
		
		return data;
	}
	
	public static void rentEquipment(String sports) {
		int qty = equipments.get(sports).getEquipmentQty();
	}
	
	public static void borrowEquipment(String n, int x) { // n = name of equipment, x = no of sets of equipment 
		
		int[] dup = new int[6];
		int combinedNoOfEq = 0;
		int index = 0;
		
		for (int i = 0; i < typesOfEq.length; i++) { //clone existing array
			dup[i] = equipments.get("current").getEquipments()[i];
		}
		
		
		for (int i = 0; i < typesOfEq.length; i++) { //update cloned array with newest value
			if (typesOfEq[i].equals(n)) {
				dup[i] = equipments.get("current").getEquipments()[i] - x;
			}
		}
		
		equipments.replace("current", new EquipmentsEntity(dup)); //replaces old array with cloned array that has the updated values
		
		db.commit(); //save changes
	}
	
	public static void returnEquipment(String n, int tobereturned) { // n = name of equipment, x = no of sets of equipment 
		
		int[] dup = new int[6];
		int combinedNoOfEq = 0;
		int index = 0;
		
		for (int i = 0; i < typesOfEq.length; i++) { //clone existing array
			dup[i] = equipments.get("current").getEquipments()[i];
		}
		
		
		for (int i = 0; i < typesOfEq.length; i++) { //update cloned array with newest value
			if (typesOfEq[i].equals(n)) {
				combinedNoOfEq = equipments.get("current").getEquipments()[i] + tobereturned;
				index = i;
				
			}
		}
		
		if (combinedNoOfEq <= totalStock[index] ) { //ensures inventory + return sets don't exceed the total stock 
			dup[index] = combinedNoOfEq;
		}
		
		equipments.replace("current", new EquipmentsEntity(dup)); //replaces old array with cloned array that has the updated values
		
		db.commit(); //save changes
	}
	
	public static void setTotalStock(int a, int b, int c, int d, int e, int f) { //*NOT COMPLETED YET*
		
		int[] stock = new int[] {a,b,c,d,e,f};

		boolean allPositiveNo = true;
		
		for (int i = 0; i < 6; i++) {
			if (stock[i] < 0) {
				allPositiveNo = false;
			}
		}
		
		if (allPositiveNo == true) {
			totalStock = stock;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(equipments.get("Basletball").getEquipmentQty(););
	}
}

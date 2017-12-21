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
	private static String[] typesOfEq = new String[] {"badminton", "basketball", "frisbee", "soccer", "squash", "tennis"};
	private static int[] totalStock = {0, 0, 0, 0, 0, 0};
	
	public static void initDA() {
		db = DBMaker
				.newFileDB(new File("tmp/equipments.db"))
				.closeOnJvmShutdown()
				.make();
		
		equipments = db.getTreeMap("equipments");
		
		db.commit();
	}
	
	public static Object[][] getAllData() {
		// 6 rows, 2 columns
		Object[][] rowData = new Object[6][2];
			
		
		for (int i = 0; i < 6; i++) {
			rowData[i][0] = typesOfEq[i];
			rowData[i][1] = equipments.get("current").getEquipments()[i];
		}
		
		return rowData;
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
		int[] newCurrent = new int[6];
		boolean allPositiveNo = true;
		
		for (int i = 0; i < 6; i++) {
			if (stock[i] < 0) {
				allPositiveNo = false;
			}
		}
		
		
			
		
		
	}
}

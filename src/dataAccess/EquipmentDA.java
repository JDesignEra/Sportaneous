package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.EquipmentsEntity;

public class EquipmentDA {
	
	private static DB db;
	private static ConcurrentMap<Integer, EquipmentsEntity> equipments;
	
	
	private static String[] typesOfEq = new String[] {"badminton", "basketball", "frisbee", "soccer", "squash", "tennis"};
	
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
			
		for (EquipmentsEntity ee : equipments.values()) {
			for (int i = 0; i < 6; i++) {
				rowData[i][0] = typesOfEq[i];
				rowData[i][1] = ee.getEquipments()[i];
			}
		}
		
		return rowData;
	}
	
	public static void borrowEquipment(String n, int x) { // n = name of equipment, x = no of sets of equipment 
		for (int i = 0; i < typesOfEq.length; i++) {
			if (typesOfEq[i].equals(n)) {
				equipments.get(1).getEquipments()[i]--;
			}
		}
	}
}

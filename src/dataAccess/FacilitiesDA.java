package dataAccess;

import java.io.File;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.FacilitiesEntity;
import sun.util.logging.resources.logging;

public class FacilitiesDA {
	private static DB db;
	private static ConcurrentMap<String, FacilitiesEntity> facilities;
	
	public static void initDA() {
		db = DBMaker
				.newFileDB(new File("tmp/facilities.db"))
				.closeOnJvmShutdown()
				.make();
		
		facilities = db.getTreeMap("facilities");
		
		/*
		facilities.put("Badminton Court", new FacilitiesEntity("Badminton Court", 4));
		facilities.put("Cage Field", new FacilitiesEntity("Cage Field", 2));
		facilities.put("Indoor Basketball Court", new FacilitiesEntity("Indoor Basketball Court", 2));
		facilities.put("Outdoor Basketball Court", new FacilitiesEntity("Outdoor Basketball Court", 4));
		facilities.put("Soccer Field", new FacilitiesEntity("Soccer Field", 2));
		facilities.put("Squash Court", new FacilitiesEntity("Squash Court", 4));
		facilities.put("Tennis Court", new FacilitiesEntity("Tennis Court", 4));
		*/
		db.commit();
	}
	
	public static Object[][] getAllData() {
		Object[][] data = new Object[facilities.size()][2];
		
		int i = 0;
		
		for (FacilitiesEntity facilitiesEntity : facilities.values()) {
			data[i][0] = facilitiesEntity.getFacilityName();
			data[i][1] = facilitiesEntity.getFacilityQty();
			
			i++;
		}
		
		return data;
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

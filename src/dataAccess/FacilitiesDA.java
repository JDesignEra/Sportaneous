package dataAccess;

import java.io.File;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.FacilitiesEntity;

public class FacilitiesDA {
	private static DB db;
	private static ConcurrentMap<String, FacilitiesEntity> facilities;
	private static FacilitiesEntity session;

	public static void initDa() {
		db = DBMaker.newFileDB(new File("tmp/facilities.db")).closeOnJvmShutdown().make();

		facilities = db.getTreeMap("facilties");

		db.commit();
	}

	public static Object[][] getAllData() {
		Object[][] rowData = new Object[facilities.size()][1];
		int i = 0;
		for (FacilitiesEntity facilitiesEntity : facilities.values()) {
			rowData[i][0] = facilitiesEntity.getFacilities();
			i++;
		}
		return rowData;
	}
}

package dataAccess;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.HostsEntity;

public class HostsDA {

	private static DB db;
	private static DB db1;
	private static ConcurrentMap<String, HostsEntity> hosts;
	private static HashMap<String, HostsEntity> searchResults;
	private static ConcurrentMap<String, String> profpics;
	private static HostsEntity session;
	private static String[] sports = new String[] {"Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis"};
	
	public static void initDA() {
		
		
		db = DBMaker
				.newFileDB(new File("tmp/hosts.db"))
				.closeOnJvmShutdown()
				.make();
		
		hosts = db.getTreeMap("hosts");
		
		
		db.commit();
		
		/**[FOR TESTING ONLY] --START-- [FOR TESTING ONLY]**/
		
		db1 = DBMaker
				.newFileDB(new File("tmp/profilepictures.db"))
				.closeOnJvmShutdown()
				.make();
		
		profpics = db1.getTreeMap("hosts");
		
//		profpics.put("170146W", "application/assets/img/profile_pics/camila_cabello.jpg");
//		profpics.put("170285X", "application/assets/img/profile_pics/annalise_keating.jpg");
//		profpics.put("170374Y", "application/assets/img/profile_pics/mark_zuckerberg.jpg");
//		profpics.put("170463Z", "application/assets/img/profile_pics/tim_cook.jpg");
//		profpics.put("170552A", "application/assets/img/profile_pics/bill_gates.jpg");
		
		db1.commit();
		
		/**  --END--  **/
	}

	public static int hostGame(String adminNo, String name, LocalDate date, String time, int sportsType) {		
		if (adminNo.isEmpty() || name.isEmpty() || date==null || time.isEmpty()) {
			return 0; // Fields required
		}
		
		
		if (hosts.putIfAbsent(adminNo, new HostsEntity(adminNo, name, date, time, sportsType)) != null) {
			return 1; 
		}	
		
		db.commit();
		return 2; // Success
	}
	
	public static void searchGame(String adminORname, String date, String time, String sportsType) {
		initDA();
//		String sport = sports[sportsType];
		if (adminORname.isEmpty() && date.isEmpty() && time.isEmpty() && sportsType.isEmpty()) {
			for (String x: hosts.keySet()) {
				searchResults.put(x, hosts.get(x));
			}
		}
		
		if (adminORname.isEmpty() && date.isEmpty() && !time.isEmpty() && sportsType.isEmpty()) {
			
		}
		
		if (adminORname.isEmpty() && date.isEmpty() && time.isEmpty() && !sportsType.isEmpty()) {
			for (HostsEntity x : hosts.values()) {
				if (sports[x.getSportsType()].equals(sportsType)) {
					searchResults.put(x.getAdminNo(), hosts.get(x.getAdminNo()));
				}
			}
		}
		
		if (!adminORname.isEmpty() && date.isEmpty() && time.isEmpty() && sportsType.isEmpty()) {
			
			for (String x : hosts.keySet()) {
				if (x.toLowerCase().equals(adminORname.toLowerCase())) {
					System.out.println("Admin no!");
					searchResults.put(x, hosts.get(x));
				}
			}

			for (HostsEntity x : hosts.values()) {
				if (x.getName().toLowerCase().equals(adminORname.toLowerCase())) {
					System.out.println("FOUND NAME");
					searchResults.put(x.getAdminNo(), x);
				}
			}
		}
		
		


	}
	
	public static HashMap<String, HostsEntity> getSearchResults() {
		return searchResults;
	}
	
	public static int addFriends(String adminNo, String name, String date, String time, String[] userID, String[] userName, int sportsType) {
		HostsEntity hostsEntity;
		
		if ((hostsEntity = hosts.get(adminNo)) == null) {
			return 0; // Does not exist
		}
		
		db.commit();
		
		if (session != null && session.getAdminNo().equals(adminNo)) {
			session = (HostsEntity) hosts;
		}
		
		return 1; // Success
	}
	
	//need to create a method to remove friend
	public static Object[][] getAllData() {

		Object[][] rowData = new Object[hosts.size()][5];
		
		int i = 0;
		for (HostsEntity hostsEntity : hosts.values()) {
			rowData[i][0] = hostsEntity.getAdminNo();
			rowData[i][1] = hostsEntity.getName();
			rowData[i][2] = hostsEntity.getDate();
			rowData[i][3] = hostsEntity.getTime();
			rowData[i][4] = hostsEntity.getSportsType();
			i++;
		}
		
		return rowData;
	}
	
	public static HashMap<String, HostsEntity> returnHostsList() {
		initDA();
		HashMap<String, HostsEntity> list = new HashMap<String, HostsEntity>();
		
		for (String adminNo : hosts.keySet()) {
			list.put(adminNo, hosts.get(adminNo));
		}
		
		return list;
	}
	
	public static String getProfilePictureURL(String adminNo) {
		initDA();
		return profpics.get(adminNo);
	}
	
	public static void initializeSearchResults() {
		searchResults = new HashMap<String, HostsEntity>();
	}
	
	public static void main(String args[]) {
		initDA();
		
		for (int i = 0; i < getAllData().length; i++) {
			for (Object j : getAllData()[i]) {
				System.out.println(j.toString());
			}
		}
	}
	
}



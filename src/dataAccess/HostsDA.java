package dataAccess;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.HostsEntity;

public class HostsDA {

	private static DB db;
	private static ConcurrentMap<Integer, HostsEntity> hosts;
	private static ArrayList<HostsEntity> searchResults;
	private static String[] sports = new String[] { "Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis" };
	public static HashMap<String, Integer> gameSize = new HashMap<String, Integer>();
	private static Atomic.Integer key;
	

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/hosts.db")).closeOnJvmShutdown().make();
		hosts = db.getTreeMap("hosts");
		db.commit();
		
		key = db.getAtomicInteger("host_key");
	}

	public static int hostGame(String adminNo, String name, LocalDate date, LocalTime time, int sportsType, ArrayList<String> playersRecruited) {
		int keyz = key.get();
		ArrayList<String> duplicatedGame = new ArrayList<String>();
		
		if (adminNo.isEmpty() || name.isEmpty() || date == null || time==null) {
			return 0; // Fields required
		}
		
		for (HostsEntity x : hosts.values()) {
			if (x.getAdminNo().toLowerCase().equals(adminNo.toLowerCase())  &&  x.getDate().equals(date)  && x.getTime().equals(time)) {
				duplicatedGame.add(x.getName());
			}
		}
		
		if (duplicatedGame.size() == 0) {
			keyz = key.incrementAndGet();
			System.out.println("Key Value now: " + keyz);
		} else {
			System.out.println("Key Value now: " + keyz);
		}
		
		if (hosts.putIfAbsent(keyz, new HostsEntity(adminNo, name, date, time, sportsType, playersRecruited)) != null) {
			return 1;
		}

		db.commit();
		return 2; // Success
	}

	public static int searchGame(String adminORname, LocalDate date, LocalTime time, String sportsType) {
		initDA();
		ArrayList<HostsEntity> s1 = new ArrayList<HostsEntity>();
		ArrayList<HostsEntity> s2 = new ArrayList<HostsEntity>();
		ArrayList<HostsEntity> s3 = new ArrayList<HostsEntity>();
		ArrayList<HostsEntity> s4 = new ArrayList<HostsEntity>();
		
		if (!adminORname.isEmpty()) {
			for (HostsEntity x : hosts.values()) {
				if (x.getAdminNo().toLowerCase().equals(adminORname.toLowerCase()) || x.getName().toLowerCase().equals(adminORname.toLowerCase())) {
					System.out.println("Found name");
					s1.add(x);
				}
			}
			System.out.println("S1: " + s1.size());
			
		} else {
			for (HostsEntity x : hosts.values()) {
				s1.add(x);
			}
			
		}
		
		if (date != null) {
			for (HostsEntity x : s1) {
				if (x.getDate().equals(date)) {
					s2.add(x);
				}
			}
			System.out.println("S2: " + s2.size());
		} else {
			for (HostsEntity x : s1) {
				s2.add(x);
			}
		}
		
		if (time != null) {
			for (HostsEntity x : s2) {
				if (x.getTime().equals(time)) {
					s3.add(x);
				}
			}
			System.out.println("S3: " + s3.size());
		} else {
			for (HostsEntity x : s2) {
				s3.add(x);
			}
			System.out.println("S3: " + s3.size());
		}
		
		if (!sportsType.isEmpty()) {
			for (HostsEntity x : s3) {
				if (sports[x.getSportsType()].equals(sportsType)) {
					s4.add(x);
				}
			}
			System.out.println("S4: " + s4.size());
		} else {
			for (HostsEntity x : s3) {
				s4.add(x);
			}
		}
		
		searchResults = s4;
		
//		for (HostsEntity x : searchResults) {
//			if (x.getAdminNo().equals(AccountsDA.getAdminNo())) {
//				searchResults.remove(x);
//			}
//		}

		if (searchResults.isEmpty()) {
			return 0;
		}
		return 1;
	}

	public static ArrayList<HostsEntity> getSearchResults() {
		return searchResults;
	}

	public static void addFriends(String hostAd, LocalDate date, LocalTime time, String tobeaddedAd) {
		initDA();
		
		ArrayList<String> list = null;
		int key = 0;

		for (int x : hosts.keySet()) {
			if (hosts.get(x).getAdminNo().toLowerCase().equals(hostAd.toLowerCase())  &&  hosts.get(x).getDate().equals(date)  &&  hosts.get(x).getTime().equals(time)) {
				key = x;
			}
		}
		
		System.out.println("Index: " + key);
		
		if (key != 0) {
			System.out.println("Found at: " + key);
			if (hosts.get(key).getPlayersRecruited() == null) {
				list = new ArrayList<String>();
				list.add(tobeaddedAd.toLowerCase());
				System.out.println("Halfway through");
				HostsEntity tobereplaced = new HostsEntity(hostAd, hosts.get(key).getName(), hosts.get(key).getDate(), hosts.get(key).getTime(),
						hosts.get(key).getSportsType(), list);
				hosts.replace(key, tobereplaced);
				db.commit();
				System.out.println("Player successfully added");
			} else {
				list = hosts.get(key).getPlayersRecruited();
				
				if (!list.contains(tobeaddedAd.toLowerCase())) {

					System.out.println("Condition 2 fulfilled.");
					list.add(tobeaddedAd);
					HostsEntity tobereplaced = new HostsEntity(hostAd, hosts.get(key).getName(), hosts.get(key).getDate(), hosts.get(key).getTime(), hosts.get(key).getSportsType(),
							list);
					hosts.replace(key, tobereplaced);
					db.commit();
				}
			}
		}
	}

	// need to create a method to remove friend
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

	public static ArrayList<HostsEntity> returnHostsList() {
		initDA();
		ArrayList<HostsEntity> list = new ArrayList<HostsEntity>();

		for (HostsEntity game : hosts.values()) {
			list.add(game);
		}

		return list;
	}

	public static ConcurrentMap<Integer, HostsEntity> getHostDB() {
		return hosts;
	}

	public static void initializeSearchResults() {
		searchResults = new ArrayList<HostsEntity>();
	}
	
	public static int getGameSize(int sportType) {
		gameSize.put(sports[0], 4);
		gameSize.put(sports[1], 10);
		gameSize.put(sports[2], 7);
		gameSize.put(sports[3], 22);
		gameSize.put(sports[4], 4);
		gameSize.put(sports[5], 4);
		
		return gameSize.get(sports[sportType]);
	}

	public static void main(String args[]) {
		
		initDA();
//		for (int i : hosts.keySet()) {
//			System.out.println(i);
//		}

//		System.out.println(HostsDA.hostGame("170146W", "Camila Cabello", LocalDate.of(2018, 1, 2), LocalTime.of(17, 00), 1, null)); 
//		System.out.println(HostsDA.hostGame("170285X", "Annalise Keating", LocalDate.of(2018, 1, 4), LocalTime.of(19, 00), 2, null));
//		System.out.println(HostsDA.hostGame("170374Y", "Mark Zuckerberg", LocalDate.of(2018, 1, 8), LocalTime.of(11, 00), 4, null));
//		System.out.println(HostsDA.hostGame("170463Z", "Tim Cook", LocalDate.of(2018, 1, 16), LocalTime.of(14, 00), 3, null));
//		System.out.println(HostsDA.hostGame("170552A", "Bill Gates", LocalDate.of(2018, 2, 1), LocalTime.of(19, 00), 5, null));
//		System.out.println(HostsDA.hostGame("170957E", "Sheldon Cooper", LocalDate.of(2018, 2, 1), LocalTime.of(17, 00), 0, null));
//		System.out.println(HostsDA.hostGame("170707E", "Howard Wolowitz", LocalDate.of(2018, 2, 3), LocalTime.of(16, 00), 2, null));	
//		System.out.println(HostsDA.hostGame("170146W", "Camila Cabello", LocalDate.of(2018, 1, 2), LocalTime.of(18, 00), 3, null));
		
//		initializeSearchResults();
		System.out.println(searchGame("", null, null, "Frisbee"));
		for (HostsEntity x : searchResults) {
			System.out.println("Search results: " + x.getName() + " " + x.getDate() + " " + x.getTime());
		}
//		
		
//
//		for (int i = 0; i < getAllData().length; i++) {
//			for (Object j : getAllData()[i]) {
//				System.out.println(j.toString());
//			}
//		}
		
		
	}

	public static ArrayList<String> getFriends(String whosegameisclicked, LocalDate date, LocalTime time) {
		ArrayList<String> tobereturned = new ArrayList<String>();
		for (HostsEntity x : hosts.values()) {
			if (x.getAdminNo().toLowerCase().equals(whosegameisclicked.toLowerCase()) && x.getTime().equals(time) && x.getDate().equals(date)) {
				tobereturned = x.getPlayersRecruited();
			}
		}
		return tobereturned;
	}
}

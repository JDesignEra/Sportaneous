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
	public final static String[] sports = new String[] { "Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis" };
	public static HashMap<String, Integer> gameSize = new HashMap<String, Integer>();
	private static Atomic.Integer key;
	

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/hosts.db")).closeOnJvmShutdown().make();
		hosts = db.getTreeMap("hosts");
		db.commit();
		
		key = db.getAtomicInteger("host_key");
	}

	public static int hostGame(String adminNo, String name, LocalDate date, LocalTime time, int sportsType, ArrayList<String> playersRecruited, String facility) {
		int keyz = key.get();
		ArrayList<String> duplicatedGame = new ArrayList<String>();
		
		if (adminNo.isEmpty() || name.isEmpty() || date == null || time==null || facility.isEmpty()) {
			return 0; // Fields required
		}
		
		for (HostsEntity x : hosts.values()) {
			if (x.getAdminNo().toLowerCase().equals(adminNo.toLowerCase())  &&  x.getDate().equals(date)  && x.getTime().equals(time)) {
				duplicatedGame.add(x.getName());
			}
			if (x.getDate().equals(date)  && x.getTime().equals(time) && x.getFacility().equals(facility)) {
				duplicatedGame.add(x.getName());
			}
		}
		
		if (duplicatedGame.size() == 0) {
			keyz = key.incrementAndGet();
			System.out.println("Key Value now: " + keyz);
		} else {
			System.out.println("Key Value now: " + keyz);
		}
		
		if (hosts.putIfAbsent(keyz, new HostsEntity(adminNo, name, date, time, sportsType, playersRecruited, facility)) != null) {
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
				if (x.getAdminNo().toLowerCase().equals(adminORname.toLowerCase()) || x.getName().toLowerCase().contains(adminORname.toLowerCase())) {
					System.out.println("Found name");
					s1.add(x);
				}
			}
			
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

		} else {
			for (HostsEntity x : s2) {
				s3.add(x);
			}

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

	public static int addFriends(String hostAd, LocalDate date, LocalTime time, String tobeaddedAd) {
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
				System.out.println(hostAd + " is the first player to be added.");
				list = new ArrayList<String>();
				list.add(tobeaddedAd.toLowerCase());
				HostsEntity tobereplaced = new HostsEntity(hostAd, hosts.get(key).getName(), hosts.get(key).getDate(), hosts.get(key).getTime(),
						hosts.get(key).getSportsType(), list, hosts.get(key).getFacility());
				hosts.replace(key, tobereplaced);
				db.commit();
				System.out.println("Player successfully added");
				return list.size();
			} else {
				list = hosts.get(key).getPlayersRecruited();
				
				if (!list.contains(tobeaddedAd.toLowerCase()) && list.size() < getGameSize(hosts.get(key).getSportsType())) {

					System.out.println(tobeaddedAd + " not found in the list; allowed to be added (" + getGameSize(hosts.get(key).getSportsType()) + ")");
					list.add(tobeaddedAd.toLowerCase());
					HostsEntity tobereplaced = new HostsEntity(hostAd, hosts.get(key).getName(), hosts.get(key).getDate(), hosts.get(key).getTime(), hosts.get(key).getSportsType(),
							list, hosts.get(key).getFacility());
					hosts.replace(key, tobereplaced);
					db.commit();
					return list.size();
				}
			}
		}
		return -99;
	}

	public static int removeFriend(String hostAd, LocalDate date, LocalTime time, String tobeRemoved) {
		initDA();
		AccountsDA.initDA();

		ArrayList<String> list = null;
		int key = 0;

		for (int x : hosts.keySet()) {
			if (hosts.get(x).getAdminNo().toLowerCase().equals(hostAd.toLowerCase()) && hosts.get(x).getDate().equals(date) && hosts.get(x).getTime().equals(time)) {
				key = x;
			}
		}	
		
		if (key != 0) {
			System.out.println("Host found at: " + key);
			if (hosts.get(key).getPlayersRecruited() != null) {
				
				list = hosts.get(key).getPlayersRecruited();

				if (list.contains(tobeRemoved.toLowerCase())) {
					int nameIndex = -1;
					
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).equals(tobeRemoved.toLowerCase())) {
							nameIndex = i;
						}
					}
					list.remove(nameIndex);
					HostsEntity tobereplaced = new HostsEntity(hostAd, hosts.get(key).getName(), hosts.get(key).getDate(), hosts.get(key).getTime(), hosts.get(key).getSportsType(),
							list, hosts.get(key).getFacility());
					hosts.replace(key, tobereplaced);
					db.commit();
					return list.size();
				}
			}
		}
		
		return -99;
	}
	
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
		hosts.clear();
		System.out.println(HostsDA.hostGame("170146W", "Camila Cabello", LocalDate.of(2018, 3, 2), LocalTime.of(17, 00), 1, null, "Indoor Basketball Court")); 
		System.out.println(HostsDA.hostGame("170285X", "Annalise Keating", LocalDate.of(2018, 3, 4), LocalTime.of(19, 00), 2, null, "Hockey Pitch"));
		System.out.println(HostsDA.hostGame("170374Y", "Mark Zuckerberg", LocalDate.of(2018, 3, 8), LocalTime.of(11, 00), 4, null, "Squash Court"));
		System.out.println(HostsDA.hostGame("170463Z", "Tim Cook", LocalDate.of(2018, 3, 16), LocalTime.of(14, 00), 3, null, "Soccer court"));
		System.out.println(HostsDA.hostGame("170552A", "Bill Gates", LocalDate.of(2018, 3, 1), LocalTime.of(19, 00), 5, null, "Tennis Court"));
		System.out.println(HostsDA.hostGame("170957E", "Sheldon Cooper", LocalDate.of(2018, 3, 1), LocalTime.of(17, 00), 0, null, "Badminton Court"));
		System.out.println(HostsDA.hostGame("170707E", "Howard Wolowitz", LocalDate.of(2018, 3, 3), LocalTime.of(16, 00), 2, null, "Hockey Pitch"));	
		System.out.println(HostsDA.hostGame("170146W", "Camila Cabello", LocalDate.of(2018, 3, 2), LocalTime.of(18, 00), 3, null, "Soccer Court"));
		
		
//		ArrayList<String> ppl = new ArrayList<String>();
//		ppl.add("170196w");
//		System.out.println(HostsDA.hostGame("170146W", "Camila Cabello", LocalDate.of(2018, 9, 2), LocalTime.of(19, 00), 3, ppl, "Soccer Court"));
//		System.out.println(HostsDA.getFriends("170146W", LocalDate.of(2018, 9, 2), LocalTime.of(19, 00)));
//		System.out.println(HostsDA.removeFriend("170146W", LocalDate.of(2018, 9, 2), LocalTime.of(19, 00), "170196W"));
		
//		for (int i = 0; i < getAllData().length; i++) {
//			for (Object j : getAllData()[i]) {
//				System.out.println(j.toString());
//			}
//		}
		
		
	}

	public static ArrayList<String> getFriends(String whosegameisclicked, LocalDate date, LocalTime time) {
		initDA();
		ArrayList<String> tobereturned = new ArrayList<String>();
		for (HostsEntity x : hosts.values()) {
			if (x.getAdminNo().toLowerCase().equals(whosegameisclicked.toLowerCase()) && x.getTime().equals(time) && x.getDate().equals(date)) {
				tobereturned = x.getPlayersRecruited();
			}
		}
		return tobereturned;
	}
}

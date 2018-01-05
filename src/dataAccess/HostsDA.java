package dataAccess;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.HostsEntity;

public class HostsDA {

	private static DB db;
	private static ConcurrentMap<String, HostsEntity> hosts;
	private static HashMap<String, HostsEntity> searchResults;
	private static HostsEntity session;
	private static String[] sports = new String[] { "Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis" };

	public static void initDA() {

		db = DBMaker.newFileDB(new File("tmp/hosts.db")).closeOnJvmShutdown().make();

		hosts = db.getTreeMap("hosts");

		db.commit();
<<<<<<< HEAD
		
		
	}

	public static int hostGame(String adminNo, String name, LocalDate date, String time, int sportsType, ArrayList<String> playersRecruited) {		
		if (adminNo.isEmpty() || name.isEmpty() || date==null || time.isEmpty()) {
			return 0; // Fields required
		}
		
		
		if (hosts.putIfAbsent(adminNo, new HostsEntity(adminNo, name, date, time, sportsType, playersRecruited)) != null) {
			return 1; 
		}	
		
=======

		/** --END-- **/
	}

	public static int hostGame(String adminNo, String name, LocalDate date, String time, int sportsType) {
		if (adminNo.isEmpty() || name.isEmpty() || date == null || time.isEmpty()) {
			return 0; // Fields required
		}

		if (hosts.putIfAbsent(adminNo, new HostsEntity(adminNo, name, date, time, sportsType)) != null) {
			return 1;
		}

>>>>>>> d2a895f846d895bb786007d08afe28cdf7f086f2
		db.commit();
		return 2; // Success
	}

	public static void searchGame(String adminORname, String date, String time, String sportsType) {
		
		initDA();
<<<<<<< HEAD

=======
		// String sport = sports[sportsType];
>>>>>>> d2a895f846d895bb786007d08afe28cdf7f086f2
		if (adminORname.isEmpty() && date.isEmpty() && time.isEmpty() && sportsType.isEmpty()) {
			for (String x : hosts.keySet()) {
				searchResults.put(x, hosts.get(x));
			}
		}
<<<<<<< HEAD
		
=======

		if (adminORname.isEmpty() && date.isEmpty() && !time.isEmpty() && sportsType.isEmpty()) {

		}

>>>>>>> d2a895f846d895bb786007d08afe28cdf7f086f2
		if (adminORname.isEmpty() && date.isEmpty() && time.isEmpty() && !sportsType.isEmpty()) {
			for (HostsEntity x : hosts.values()) {
				if (sports[x.getSportsType()].equals(sportsType)) {
					searchResults.put(x.getAdminNo(), x);
				}
			}
		}
		
		if (adminORname.isEmpty() && date.isEmpty() && !time.isEmpty() && sportsType.isEmpty()) {
			System.out.println(searchResults.size());
			for (HostsEntity x : hosts.values()) {
				if (time.equals(x.getTime())) {
					searchResults.put(x.getAdminNo(), x);
				} 
			}
		}
		
		if (adminORname.isEmpty() && !date.isEmpty() && time.isEmpty() && sportsType.isEmpty()) {
			LocalDate ld = LocalDate.of(Integer.parseInt(date.substring(4)), Integer.parseInt(date.substring(2, 4)), Integer.parseInt(date.substring(0,2)));
			for (HostsEntity x: hosts.values()) {
				if (x.getDate().equals(ld)) {
					searchResults.put(x.getAdminNo(), x);
				}
			}
			
		}

		if (!adminORname.isEmpty() && date.isEmpty() && time.isEmpty() && sportsType.isEmpty()) {

			for (String x : hosts.keySet()) {
				if (x.toLowerCase().equals(adminORname.toLowerCase())) {
					searchResults.put(x, hosts.get(x));
				}
			}

			for (HostsEntity x : hosts.values()) {
				if (x.getName().toLowerCase().contains(adminORname.toLowerCase())) {
					searchResults.put(x.getAdminNo(), x);
				}
			}
		}
		
		if (!adminORname.isEmpty() && date.isEmpty() && time.isEmpty() && !sportsType.isEmpty()) {
			for (String x : hosts.keySet()) {
				if (x.toLowerCase().equals(adminORname.toLowerCase()) && sports[hosts.get(x).getSportsType()].equals(sportsType)) {
					searchResults.put(x, hosts.get(x));
				}
				
				if (hosts.get(x).getName().toLowerCase().contains(adminORname.toLowerCase()) && sports[hosts.get(x).getSportsType()].equals(sportsType)) {
					searchResults.put(x, hosts.get(x));
				}
				
			}
		}
		
		if (adminORname.isEmpty() && !date.isEmpty() && time.isEmpty() && !sportsType.isEmpty()) {
			LocalDate ld = LocalDate.of(Integer.parseInt(date.substring(4)), Integer.parseInt(date.substring(2, 4)), Integer.parseInt(date.substring(0,2)));
			for (HostsEntity x: hosts.values()) {
				if (x.getDate().equals(ld) && sports[x.getSportsType()].equals(sportsType)) {
					searchResults.put(x.getAdminNo(), x);
				}
			}
		}
<<<<<<< HEAD
		
		if (adminORname.isEmpty() && date.isEmpty() && !time.isEmpty() && !sportsType.isEmpty()) {
			for (HostsEntity x: hosts.values()) {
				if (x.getTime().equals(time) && sports[x.getSportsType()].equals(sportsType)) {
					searchResults.put(x.getAdminNo(), x);
				}
			}
		}
		
		if (!adminORname.isEmpty() && !date.isEmpty() && time.isEmpty() && sportsType.isEmpty()) {
			
			LocalDate ld = LocalDate.of(Integer.parseInt(date.substring(4)), Integer.parseInt(date.substring(2, 4)), Integer.parseInt(date.substring(0,2)));
			
			for (String x : hosts.keySet()) {
				if (x.toLowerCase().equals(adminORname.toLowerCase()) && hosts.get(x).getDate().equals(ld)) {
					searchResults.put(x, hosts.get(x));
				}
				
				if (hosts.get(x).getName().toLowerCase().contains(adminORname.toLowerCase()) && hosts.get(x).getDate().equals(ld)) {
					searchResults.put(x, hosts.get(x));
				}
				
			}
		}
		
		if (!adminORname.isEmpty() && date.isEmpty() && !time.isEmpty() && sportsType.isEmpty()) {
			for (String x : hosts.keySet()) {
				if (x.toLowerCase().equals(adminORname.toLowerCase()) && hosts.get(x).getTime().equals(time)) {
					searchResults.put(x, hosts.get(x));
				}
				
				if (hosts.get(x).getName().toLowerCase().contains(adminORname.toLowerCase()) && hosts.get(x).getTime().equals(time)) {
					searchResults.put(x, hosts.get(x));
				}
			}
		}
		
		if (adminORname.isEmpty() && !date.isEmpty() && !time.isEmpty() && sportsType.isEmpty()) {
			LocalDate ld = LocalDate.of(Integer.parseInt(date.substring(4)), Integer.parseInt(date.substring(2, 4)), Integer.parseInt(date.substring(0,2)));
			for (HostsEntity x: hosts.values()) {
				if (x.getDate().equals(ld) && x.getTime().equals(time)) {
					searchResults.put(x.getAdminNo(), x);
				}
			}
		}
		
		if (!adminORname.isEmpty() && !date.isEmpty() && time.isEmpty() && !sportsType.isEmpty()) {
			LocalDate ld = LocalDate.of(Integer.parseInt(date.substring(4)), Integer.parseInt(date.substring(2, 4)), Integer.parseInt(date.substring(0,2)));
			
			for (String x : hosts.keySet()) {
				if (x.toLowerCase().equals(adminORname.toLowerCase()) && hosts.get(x).getDate().equals(ld) && sports[hosts.get(x).getSportsType()].equals(sportsType)) {
					searchResults.put(x, hosts.get(x));
				}
				
				if (hosts.get(x).getName().toLowerCase().contains(adminORname) && hosts.get(x).getDate().equals(ld) && sports[hosts.get(x).getSportsType()].equals(sportsType)) {
					searchResults.put(x, hosts.get(x));
				}
			}
		}
		
		if (!adminORname.isEmpty() && date.isEmpty() && !time.isEmpty() && !sportsType.isEmpty()) {
			for (String x : hosts.keySet()) {
				if (x.toLowerCase().equals(adminORname.toLowerCase()) && hosts.get(x).getTime().equals(time) && sports[hosts.get(x).getSportsType()].equals(sportsType)) {
					searchResults.put(x, hosts.get(x));
				} 
				
				if (hosts.get(x).getName().toLowerCase().contains(adminORname) && hosts.get(x).getTime().equals(time) && sports[hosts.get(x).getSportsType()].equals(sportsType)) {
					searchResults.put(x, hosts.get(x));
				}
			}
		}
		
		if (adminORname.isEmpty() && !date.isEmpty() && !time.isEmpty() && !sportsType.isEmpty()) {
			LocalDate ld = LocalDate.of(Integer.parseInt(date.substring(4)), Integer.parseInt(date.substring(2, 4)), Integer.parseInt(date.substring(0,2)));
			for (HostsEntity x : hosts.values()) {
				if (x.getDate().equals(ld) && x.getTime().equals(time) && sports[x.getSportsType()].equals(sportsType)) {
					searchResults.put(x.getAdminNo(),x);
				}
			}
		}
		
		if (!adminORname.isEmpty() && !date.isEmpty() && !time.isEmpty() && sportsType.isEmpty()) {
			LocalDate ld = LocalDate.of(Integer.parseInt(date.substring(4)), Integer.parseInt(date.substring(2, 4)), Integer.parseInt(date.substring(0,2)));
			for (String x : hosts.keySet()) {
				System.out.println("HELLO");
				if (x.toLowerCase().equals(adminORname.toLowerCase()) && hosts.get(x).getDate().equals(ld) && hosts.get(x).getTime().equals(time)) {
					searchResults.put(x,hosts.get(x));
				}
				if (hosts.get(x).getName().toLowerCase().contains(adminORname.toLowerCase()) && hosts.get(x).getDate().equals(ld) && hosts.get(x).getTime().equals(time)) {
					searchResults.put(x,hosts.get(x));
				}
			}
		}
=======
>>>>>>> d2a895f846d895bb786007d08afe28cdf7f086f2

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

	public static HashMap<String, HostsEntity> returnHostsList() {
		initDA();
		HashMap<String, HostsEntity> list = new HashMap<String, HostsEntity>();

		for (String adminNo : hosts.keySet()) {
			list.put(adminNo, hosts.get(adminNo));
		}

		return list;
	}

	public static void initializeSearchResults() {
		searchResults = new HashMap<String, HostsEntity>();
	}

	public static void main(String args[]) {
		initDA();
<<<<<<< HEAD
		
//		for (int i = 0; i < getAllData().length; i++) {
//			for (Object j : getAllData()[i]) {
//				System.out.println(j.toString());
//			}
//		}
		initializeSearchResults();
		searchGame("", "02012018", "", "");
		
		for (HostsEntity x: searchResults.values()) {
			System.out.println(x.getName());
=======

		for (int i = 0; i < getAllData().length; i++) {
			for (Object j : getAllData()[i]) {
				System.out.println(j.toString());
			}
>>>>>>> d2a895f846d895bb786007d08afe28cdf7f086f2
		}
	}

}

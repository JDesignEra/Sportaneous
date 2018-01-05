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
	private static String[] sports = new String[] { "Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis" };

	public static void initDA() {

		db = DBMaker.newFileDB(new File("tmp/hosts.db")).closeOnJvmShutdown().make();

		hosts = db.getTreeMap("hosts");

		db.commit();

	}

	public static int hostGame(String adminNo, String name, LocalDate date, String time, int sportsType, ArrayList<String> playersRecruited) {
		if (adminNo.isEmpty() || name.isEmpty() || date == null || time.isEmpty()) {
			return 0; // Fields required
		}

		if (hosts.putIfAbsent(adminNo, new HostsEntity(adminNo, name, date, time, sportsType, playersRecruited)) != null) {
			return 1;
		}

		db.commit();
		return 2; // Success
	}

	public static void searchGame(String adminORname, String date, String time, String sportsType) {
		initDA();

		if (adminORname.isEmpty() && date.isEmpty() && time.isEmpty() && sportsType.isEmpty()) {
			for (String x : hosts.keySet()) {
				searchResults.put(x, hosts.get(x));
			}
		}

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
	}
}
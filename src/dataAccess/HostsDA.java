package dataAccess;

import java.io.File;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.HostsEntity;

public class HostsDA {

	private static DB db;
	private static ConcurrentMap<String, HostsEntity> hosts;
	private static HostsEntity session;
	
	public static void initDA() {
		db = DBMaker
				.newFileDB(new File("tmp/hosts.db"))
				.closeOnJvmShutdown()
				.make();
		
		hosts = db.getTreeMap("hosts");
		
		
		db.commit();
		
		
	}
	public static int hostGame(String adminNo, String name, String date, String time, String[] userID, String[] userName, int sportsType) {
		if (adminNo.isEmpty() || name.isEmpty() || date.isEmpty() || time.isEmpty()/*userID.isEmpty() || sportsType.isEmpty())*/) {
			return 0; // Fields required
		}
		
		/*if (hosts.putIfAbsent(adminNo, new AccountsEntity(adminNo, name, date, time,userID,userName,sportsType "", "", "", "", 0, 0, false, false, new BigDecimal(0), 0, 0, 0)) != null) {
			return 1; 
		}*/
		
		db.commit();
		return 2; // Success
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
		Object[][] rowData = new Object[hosts.size()][16];
		
		int i = 0;
		for (HostsEntity hostsEntity : hosts.values()) {
			rowData[i][0] = hostsEntity.getAdminNo();
			rowData[i][1] = hostsEntity.getName();
			rowData[i][2] = hostsEntity.getDate();
			rowData[i][3] = hostsEntity.getTime();
			rowData[i][4] = hostsEntity.getUserID();
			rowData[i][5] = hostsEntity.getUserName();
			rowData[i][6] = hostsEntity.getSportsType();
			i++;
		}
		
		return rowData;
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



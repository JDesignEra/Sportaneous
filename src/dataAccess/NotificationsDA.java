package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.NotificationsEntity;

public class NotificationsDA {
	private static DB db;
	private static ConcurrentMap<String, List<NotificationsEntity>> notifications;
	private static String sessionID = AccountsDA.getSession().getAdminNo();
	private static List<NotificationsEntity> notificationsList;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/notifications.db")).closeOnJvmShutdown().make();

		notifications = db.getTreeMap("notifications");
		/*
		 * List<NotificationsEntity> temp = new ArrayList<>(); temp.add(new
		 * NotificationsEntity("175150r", "Wilson", "basketball", "basketball Court",
		 * "10/1/18", "15:00", 1)); notifications.put("175150r", temp);
		 */

		db.commit();
	}

	public static Object[][] getAllData() {
		Object[][] rowData = new Object[notifications.size()][7];
		notificationsList = (notifications.get(sessionID) != null ? notifications.get(sessionID) : new ArrayList<>());

		int i = 0;
		for (NotificationsEntity notificationsEntity : notificationsList) {
			rowData[i][0] = notificationsEntity.getAdminNo();
			rowData[i][1] = notificationsEntity.getName();
			rowData[i][2] = notificationsEntity.getSports();
			rowData[i][3] = notificationsEntity.getLocation();
			rowData[i][4] = notificationsEntity.getDate();
			rowData[i][5] = notificationsEntity.getTime();
			rowData[i][6] = notificationsEntity.getStatus();
			i++;
		}
		return rowData;
	}

	public static Object[][] getAccountNotifications(String adminNo) {
		return notifications.get(adminNo).toArray(new Object[notifications.get(adminNo).size()][]);
	}

	public static int checkStatus(String notiID) {
		notificationsList = (notifications.get(sessionID) != null ? notifications.get(sessionID) : new ArrayList<>());
		
		for (NotificationsEntity notificationsEntity : notificationsList) {
			if (notificationsEntity.getAdminNo().equals(notiID)) {
				return notificationsEntity.getStatus();// 0 = Invitation,
														// 1 = Joined,
														// 2 = Rating,
														// 3 = Friend Request
			}
		}
		return 4; // no Notifications
	}

	public static void addNotifications(String sports, String location, String date, String time, int status) {
		String sessionName = AccountsDA.getName();

		notificationsList = (notifications.get(sessionID) != null ? notifications.get(sessionID) : new ArrayList<>());
		notificationsList.add(new NotificationsEntity(sessionID, sessionName, sports, location, date, time, status));
		notifications.put(sessionID, notificationsList);

		db.commit();
	}

	public static void deleteNotificaions(String adminNo) {
		int i = 0;
		notificationsList = (notifications.get(sessionID) != null ? notifications.get(sessionID) : new ArrayList<>());
		for (NotificationsEntity notiEntity : notificationsList) {
			if (notiEntity.getAdminNo().equals(adminNo)) {
				notificationsList.remove(i);
				notifications.put(sessionID, notificationsList);
				break;
			}
			i++;
		}
		db.commit();
	}

	/*
	 * public static void main(String[] args) { initDA();
	 * 
	 * for (int i = 0; i < getAllData().length; i++) { for (Object j :
	 * getAllData()[i]) { System.out.println(j); } } }
	 */

}

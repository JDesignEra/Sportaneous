package dataAccess;

import java.io.File;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.NotificationsEntity;

public class NotificationsDA {
	private static DB db;
	private static ConcurrentMap<String, NotificationsEntity> notifications;
	private static NotificationsEntity session;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/notifications.db")).closeOnJvmShutdown().make();

		notifications = db.getTreeMap("notifications");

		/*
		 * notifications.put("175150r", new NotificationsEntity("175150r", "Wilson",
		 * "basketball", "basketball Court", "10/1/18", "15:00", 1));
		 */
		db.commit();
	}

	public static Object[][] getAllData() {
		Object[][] rowData = new Object[notifications.size()][7];

		int i = 0;
		for (NotificationsEntity notificationsEntity : notifications.values()) {
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

	public static int deleteNotificaions(int id) {
		if (notifications.remove(id) == null) {
			return 0; // fail
		}
		db.commit();
		return 1; // success
	}

	public static NotificationsEntity getSession() {
		return session;
	}

	public static String getAdminNo() {
		return session.getAdminNo();
	}

	public static String getName() {
		return session.getName();
	}

	public static String getSports() {
		return session.getSports();
	}

	public static String getLocation() {
		return session.getLocation();
	}

	public static String getDate() {
		return session.getDate();
	}

	public static String getTime() {
		return session.getTime();
	}

	public static int getStatus() {
		return session.getStatus();
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

package dataAccess;

import java.io.File;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.NotificationsEntity;

public class NotificationsDA {
	private static DB db;
	private static ConcurrentMap<String, NotificationsEntity> notifications;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/notifications.db")).closeOnJvmShutdown().make();

		notifications = db.getTreeMap("notifications");

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
}

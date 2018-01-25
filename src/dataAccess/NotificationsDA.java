package dataAccess;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.NotificationsEntity;

public class NotificationsDA {
	private static DB db;
	private static ConcurrentMap<String, List<NotificationsEntity>> notifications;
	private static String sessionID = AccountsDA.getAdminNo();
	private static List<NotificationsEntity> notificationsList;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/notifications.db")).closeOnJvmShutdown().make();

		notifications = db.getTreeMap("notifications");
		/*
		List<NotificationsEntity> temp = new ArrayList<>();
		temp.add(new NotificationsEntity("admin", "Wilson", "basketball", "Basketball Court", LocalDateTime.of(2018, 2, 12, 15, 00), 0));
		temp.add(new NotificationsEntity("admin", "john", "basketball", "Basketball Court", LocalDateTime.of(2018, 3, 22, 17, 00), 1));
		notifications.put("admin", temp);
		 */
		db.commit();
	}

	public static List<NotificationsEntity> getNotifications() {
		return notifications.get(sessionID) != null ? notifications.get(sessionID) : new ArrayList<>();
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

	public static void addNotifications(String sports, String location, LocalDateTime dateTime, int status) {
		String sessionName = AccountsDA.getName();

		notificationsList = (notifications.get(sessionID) != null ? notifications.get(sessionID) : new ArrayList<>());
		notificationsList.add(new NotificationsEntity(sessionID, sessionName, sports, location, dateTime, status));
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
}

package dataAccess;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.AccountsEntity;
import entity.NotificationsEntity;

public class NotificationsDA {
	private static DB db;
	private static ConcurrentMap<String, List<NotificationsEntity>> notifications;
	private static List<NotificationsEntity> notificationsList;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/notifications.db")).closeOnJvmShutdown().make();
		notifications = db.getTreeMap("notifications");
//		List<NotificationsEntity> temp = new ArrayList<>();
//		temp.add(new NotificationsEntity("4321a", "David Beckham", "basketball", "Basketball Court", LocalDateTime.of(2018, 2, 12, 15, 00), 0));
//		temp.add(new NotificationsEntity("1234a", "Jimmy Butler", "basketball", "Basketball Court", LocalDateTime.of(2018, 3, 14, 16, 00), 1));
//		notifications.put("admin", temp);
		db.commit();
	}

	public static List<NotificationsEntity> getNotifications() {
		return notifications.get(AccountsDA.getAdminNo()) != null ? notifications.get(AccountsDA.getAdminNo()) : new ArrayList<>();
	}

	public static int checkStatus(String notiID) {
		String sessionID = AccountsDA.getAdminNo();
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

	public static void addNotifications(String userAdminNo, String sports, String location, LocalDateTime dateTime, int status) {
		String sessionName = AccountsDA.getName();
		String sessionAd = AccountsDA.getAdminNo();

		notificationsList = (notifications.get(userAdminNo) != null ? notifications.get(userAdminNo) : new ArrayList<>());
		notificationsList.add(new NotificationsEntity(userAdminNo, sessionName, sessionAd, sports, location, dateTime, status));
		notifications.put(userAdminNo, notificationsList);

		db.commit();
	}

	public static void deleteNotificaions(String adminNo) {
		String sessionID = AccountsDA.getAdminNo();
		int i = 0;

		notificationsList = (notifications.get(sessionID) != null ? notifications.get(sessionID) : new ArrayList<>());
		for (NotificationsEntity notificationsEntity : notificationsList) {
			if (notificationsEntity.getAdminNo().equals(adminNo)) {
				notificationsList.remove(i);
				notifications.put(sessionID, notificationsList);
				db.commit();
				break;
			}
			i++;
		}
	}
}

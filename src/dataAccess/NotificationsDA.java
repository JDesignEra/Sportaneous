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

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/notifications.db")).closeOnJvmShutdown().make();
		notifications = db.getTreeMap("notifications");
		
		db.commit();
	}

	public static List<NotificationsEntity> getNotifications() {
		return notifications.get(AccountsDA.getAdminNo()) != null ? notifications.get(AccountsDA.getAdminNo()) : new ArrayList<>();
	}

	public static int checkStatus(String notiID) {
		String sessionID = AccountsDA.getAdminNo();
		List<NotificationsEntity> notificationsList = (notifications.get(sessionID) != null ? notifications.get(sessionID) : new ArrayList<>());

		for (NotificationsEntity notificationsEntity : notificationsList) {
			if (notificationsEntity.getAdminNo().equals(notiID)) {
				return notificationsEntity.getStatus();// 0 = Invitation, 1 = Joined, 2 = Rating, 3 = Friend Request
			}
		}
		return 4; // no Notifications
	}

	/**
	 * For Friend Request
	 * 
	 * @param userAdminNo
	 * @param status
	 */
	public static void addNotifications(String userAdminNo, int status) {
		NotificationsDA.addNotifications(userAdminNo, null, null, null, 3);
	}

	public static void addNotifications(String userAdminNo, String sports, String location, LocalDateTime dateTime, int status) {
		String sessionName = AccountsDA.getName();
		String sessionAd = AccountsDA.getAdminNo();
		String userName = AccountsDA.getAccData(userAdminNo).getName();

		List<NotificationsEntity> notificationsList = (notifications.get(userAdminNo) != null ? notifications.get(userAdminNo) : new ArrayList<>());
		notificationsList.add(new NotificationsEntity(userAdminNo, userName, sessionName, sessionAd, sports, location, dateTime, status));
		notifications.put(userAdminNo, notificationsList);

		db.commit();
	}

	public static void deleteNotificaions(int index) {
		String sessionID = AccountsDA.getAdminNo();

		List<NotificationsEntity> notificationsList = (notifications.get(sessionID) != null ? notifications.get(sessionID) : new ArrayList<>());
		notificationsList.remove(index);
		notifications.put(sessionID, notificationsList);
		
		db.commit();
	}
}

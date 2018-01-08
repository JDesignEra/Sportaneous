package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.FriendsEntity;

public class FriendsDA {
	private static DB db;
	private static ConcurrentMap<String, List<FriendsEntity>> friends;
	private static String sessionID = AccountsDA.getAdminNo();
	private static List<FriendsEntity> sessionFriendsList = (friends.get(sessionID) != null ? friends.get(sessionID) : new ArrayList<>());

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/friends.db")).closeOnJvmShutdown().make();
		friends = db.getTreeMap("friends");

		db.commit();
	}

	public static Object[][] getFriends() {
		Object[][] data = new Object[sessionFriendsList.size()][16];
		int i = 0;

		for (FriendsEntity friendsEntity : sessionFriendsList) {
			data[i] = AccountsDA.getAccData(friendsEntity.getFriendsID());
		}

		return data;
	}

	public static int checkStatus(String friendsID) {
		for (FriendsEntity friendsEntity : sessionFriendsList) {
			if (friendsEntity.getFriendsID().equals(friendsID)) {
				return friendsEntity.getStatus(); // 0 = Pending, 1 = Friend
			}
		}

		return 3; // Not friend
	}

	public static void addFriend(String friendsID) {
		// Update Friend's friends database data
		List<FriendsEntity> friendFriendsList = (friends.get(friendsID) != null ? friends.get(friendsID) : new ArrayList<>());
		friendFriendsList.add(new FriendsEntity(friendsID, sessionID, 0));
		friends.put(friendsID, friendFriendsList);

		// Updated Session's friends database data
		sessionFriendsList.add(new FriendsEntity(sessionID, friendsID, 0));
		friends.put(sessionID, sessionFriendsList);

		db.commit();
	}

	public static void removeFriend(String friendsID) {
		List<FriendsEntity> friendFriendsList = (friends.get(friendsID) != null ? friends.get(friendsID) : new ArrayList<>());

		// Updated Friend's friends database data
		for (int j = 0; j < friendFriendsList.size(); j++) {
			if (friendFriendsList.get(j).getFriendsID().equals(sessionID)) {

				friendFriendsList.remove(j);
				friends.put(friendsID, friendFriendsList);
				break;
			}
		}

		// Update Session's Friends Database data
		for (int i = 0; i < sessionFriendsList.size(); i++) {
			if (sessionFriendsList.get(i).getFriendsID().equals(friendsID)) {
				sessionFriendsList.remove(i);
				friends.put(sessionID, sessionFriendsList);

				break;
			}
		}
	}
}
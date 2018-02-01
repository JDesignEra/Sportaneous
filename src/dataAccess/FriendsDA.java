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
	private static List<FriendsEntity> friendsList;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/friends.db")).closeOnJvmShutdown().make();
		friends = db.getTreeMap("friends");

//		List<FriendsEntity> temp = new ArrayList<>();
//		temp.add(new FriendsEntity("admin", "1234a", 1));
//		temp.add(new FriendsEntity("admin", "4321a", 1));
//		temp.add(new FriendsEntity("admin", "1234b", 0));
//		friends.put("admin", temp);
		db.commit();
	}

	public static List<FriendsEntity> getFriends() {
		return friends.get(AccountsDA.getAdminNo()) != null ? friends.get(AccountsDA.getAdminNo()) : new ArrayList<>();
	}

	public static void addFriend(String friendAdminNo) {
		String sessionID = AccountsDA.getAdminNo();
		// Session's Friend's Friend List
		friendsList = (friends.get(friendAdminNo) != null ? friends.get(friendAdminNo) : new ArrayList<>());
		friendsList.add(new FriendsEntity(friendAdminNo, sessionID, 0));
		friends.put(friendAdminNo, friendsList);

		// Session's Friend List
		friendsList = (friends.get(sessionID) != null ? friends.get(sessionID) : new ArrayList<>());
		friendsList.add(new FriendsEntity(sessionID, friendAdminNo, 0));
		friends.put(sessionID, friendsList);

		db.commit();
	}

	public static void removeFriend(String friendAdminNo) {
		String sessionID = AccountsDA.getAdminNo();
		int i = 0;

		// Session's Friend's Friend List
		friendsList = (friends.get(friendAdminNo) != null ? friends.get(friendAdminNo) : new ArrayList<>());
		for (FriendsEntity friendsEntity : friendsList) {
			if (friendsEntity.getFriendAdminNo().equals(sessionID)) {
				i = 0;
				friendsList.remove(i);
				
				friends.put(friendAdminNo, friendsList);
				db.commit();

				break;
			}

			i++;
		}

		// Session's Friend List
		friendsList = (friends.get(sessionID) != null ? friends.get(sessionID) : new ArrayList<>());
		for (FriendsEntity friendsEntity : friendsList) {
			if (friendsEntity.getFriendAdminNo().equals(friendAdminNo)) {
				friendsList.remove(i);
				
				friends.put(sessionID, friendsList);
				db.commit();

				break;
			}

			i++;
		}
	}

	public static void acceptRequest(String friendAdminNo) {
		String sessionID = AccountsDA.getAdminNo();
		int i = 0;

		// Session's Friend's Friend List
		friendsList = (friends.get(friendAdminNo) != null ? friends.get(friendAdminNo) : new ArrayList<>());
		for (FriendsEntity friendsEntity : friendsList) {
			if (friendsEntity.getFriendAdminNo().equals(sessionID)) {
				i = 0;

				friendsEntity.setStatus(1);
				friendsList.set(i, friendsEntity);
				friends.put(friendAdminNo, friendsList);
				db.commit();

				break;
			}

			i++;
		}

		// Session's Friend List
		friendsList = (friends.get(sessionID) != null ? friends.get(sessionID) : new ArrayList<>());
		for (FriendsEntity friendsEntity : friendsList) {
			if (friendsEntity.getFriendAdminNo().equals(friendAdminNo)) {
				friendsEntity.setStatus(1);
				friendsList.set(i, friendsEntity);
				
				friends.put(sessionID, friendsList);
				db.commit();

				break;
			}

			i++;
		}
	}

	public static int checkStatus(String friendAdminNo) {
		String sessionID = AccountsDA.getAdminNo();
		friendsList = (friends.get(sessionID) != null ? friends.get(sessionID) : new ArrayList<>());

		for (FriendsEntity friendsEntity : friendsList) {
			if (friendsEntity.getFriendAdminNo().equals(friendAdminNo)) {
				return friendsEntity.getStatus(); // 0 = Pending, 1 = Friend
			}
		}

		return 2; // Not Friends
	}
}
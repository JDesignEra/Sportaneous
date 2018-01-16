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
	private static List<FriendsEntity> friendsList;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/friends.db")).closeOnJvmShutdown().make();
		friends = db.getTreeMap("friends");

		// List<FriendsEntity> temp = new ArrayList<>();
		// temp.add(new FriendsEntity("admin", "1234a", 1));
		// temp.add(new FriendsEntity("admin", "4321a", 1));
		// temp.add(new FriendsEntity("admin", "1234b", 0));
		// friends.put("admin", temp);
		db.commit();
	}

	/**
	 * [0] = AdminNo, [1] = Name, [2] = Height<br>
	 * [3] = Weight, [4] = Height Visibility, [5] = Weight Visibility<br>
	 * [6] = Rating, [7] = Match Played, [8] = Total Match
	 * 
	 * @return Object[][]
	 */
	public static Object[][] getFriends() {
		friendsList = (friends.get(sessionID) != null ? friends.get(sessionID) : new ArrayList<>());
		List<Object[]> dataList = new ArrayList<>();

		for (FriendsEntity friendsEntity : friendsList) {
			Object[] accData = AccountsDA.getAccData(friendsEntity.getFriendAdminNo());
			Object[] data = new Object[9];

			if (friendsEntity.getStatus() == 1) {
				data[0] = accData[0]; // AdminNo
				data[1] = accData[3]; // Name
				data[2] = accData[7]; // Height
				data[3] = accData[8]; // Weight
				data[4] = accData[9]; // Height Visibility
				data[5] = accData[10]; // Weight Visibility
				data[6] = accData[11]; // Rating
				data[7] = accData[12]; // Match Attended
				data[8] = accData[13]; // Total Match Joined

				dataList.add(data);
			}
		}

		return dataList.toArray(new Object[dataList.size()][]);
	}

	public static void addFriend(String friendAdminNo) {
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
		int i = 0;

		// Session's Friend's Friend List
		friendsList = (friends.get(friendAdminNo) != null ? friends.get(friendAdminNo) : new ArrayList<>());
		for (FriendsEntity friendsEntity : friendsList) {
			if (friendsEntity.getFriendAdminNo().equals(sessionID)) {
				i = 0;
				friendsList.remove(i);
				friends.put(friendAdminNo, friendsList);

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

				break;
			}

			i++;
		}

		db.commit();
	}

	public static void acceptRequest(String friendAdminNo) {
		int i = 0;

		// Session's Friend's Friend List
		friendsList = (friends.get(friendAdminNo) != null ? friends.get(friendAdminNo) : new ArrayList<>());
		for (FriendsEntity friendsEntity : friendsList) {
			if (friendsEntity.getFriendAdminNo().equals(sessionID)) {
				i = 0;

				friendsEntity.setStatus(1);
				friendsList.set(i, friendsEntity);
				friends.put(friendAdminNo, friendsList);

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

				break;
			}

			i++;
		}

		db.commit();
	}

	public static int checkStatus(String friendAdminNo) {
		friendsList = (friends.get(sessionID) != null ? friends.get(sessionID) : new ArrayList<>());

		for (FriendsEntity friendsEntity : friendsList) {
			if (friendsEntity.getFriendAdminNo().equals(friendAdminNo)) {
				return friendsEntity.getStatus(); // 0 = Pending, 1 = Friend
			}
		}

		return 2; // Not Friends
	}
}
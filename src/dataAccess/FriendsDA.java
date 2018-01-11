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
	private static List<FriendsEntity> sessionFriendsList;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/friends.db")).closeOnJvmShutdown().make();

		friends = db.getTreeMap("friends");

		// List<FriendsEntity> temp = new ArrayList<>();
		// temp.add(new FriendsEntity("admin", "1", 1));
		// temp.add(new FriendsEntity("admin", "2", 1));
		// temp.add(new FriendsEntity("admin", "3", 1));
		// temp.add(new FriendsEntity("admin", "4", 0));
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
		sessionFriendsList = (friends.get(sessionID) != null ? friends.get(sessionID) : new ArrayList<>());
		List<Object[]> dataList = new ArrayList<>();

		for (FriendsEntity friendsEntity : sessionFriendsList) {
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
				data[7] = accData[13]; // Match Played
				data[8] = accData[14]; // Total Match

				dataList.add(data);
			}
		}

		return dataList.toArray(new Object[dataList.size()][]);
	}

	public static int checkStatus(String friendsID) {
		for (FriendsEntity friendsEntity : sessionFriendsList) {
			if (friendsEntity.getFriendAdminNo().equals(friendsID)) {
				return friendsEntity.getStatus(); // 0 = Pending, 1 = Friend
			}
		}

		return 3; // Not Friends
	}
}
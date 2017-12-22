package dataAccess;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.FriendsEntity;

public class FriendsDA {
	private static DB db;
	private static Atomic.Integer keyIndex;
	private static ConcurrentMap<Integer, FriendsEntity> friends;

	public static void initDA() {
		db = DBMaker
				.newFileDB(new File("tmp/friends.db"))
				.closeOnJvmShutdown()
				.make();

		keyIndex = db.getAtomicInteger("friends_keyIndex");
		friends = db.getTreeMap("friends");

		//friends.put(keyIndex.getAndIncrement(), new FriendsEntity("admin", "admin1", "admin", "admin1", 0));
		db.commit();

	}
	
	public static List<FriendsEntity> getSessionFriends() {
		String sessionID = AccountsDA.getAdminNo();
		List<FriendsEntity> friendsList = new ArrayList<>();
		
		for (FriendsEntity friendsEntity : friends.values()) {
            String[] userID = new String[] {friendsEntity.getUserID1(), friendsEntity.getUserID2()};
            
            if (userID[0] == sessionID || userID[1] == sessionID) {
                friendsList.add(friendsEntity);
            }
        }
		
		return friendsList;
	}

	public static int addFriend(String friendID, String friendName) {
		String sessionID = AccountsDA.getAdminNo();
		
		for (FriendsEntity friendsEntity : friends.values()) {
			String[] userID = new String[] {friendsEntity.getUserID1(), friendsEntity.getUserID2()};
			
			if (userID[0] == sessionID && userID[1] == friendID || userID[1] == sessionID && userID[0] == friendID) {
				return 0; // Already friends
			}
		}
		
		if (friends.putIfAbsent(keyIndex.getAndIncrement(), new FriendsEntity(sessionID, friendID, AccountsDA.getName(), friendName, 1)) != null) {
			return 1; // fail
		}

		db.commit();
		return 2; // Success
	}
	
	public static int removeFriend(String friendID) {
		String sessionID = AccountsDA.getAdminNo();
		int i = 0;
		
		for (FriendsEntity friendsENtity : friends.values()) {
			String[] userID = new String[] {friendsENtity.getUserID1(), friendsENtity.getUserID2()};
			
			if (userID[0] == sessionID && userID[1] == friendID || userID[1] == sessionID && userID[0] == friendID) {
				friends.remove(i);
				keyIndex.decrementAndGet();
				
				return 1; // Success
			}
			
			i++;
		}
		
		return 0; // fail
	}

	public static void main(String args[]) {
		initDA();
		
		/*
		for (FriendsEntity fe : getSessionFriends("admin")) {
            System.out.println(fe.getUserID1());
            System.out.println(fe.getUserID2());
            System.out.println(fe.getUserName1());
            System.out.println(fe.getUserName2());
            System.out.println(fe.getStatus());
        }
        */
		
		/*
		for (int i = 0; i < getAllData().length; i++) {
			int x = 0;
			for (Object j : getAllData()[i]) {
				System.out.println(j.toString());
			}
		}
		*/
	}
}
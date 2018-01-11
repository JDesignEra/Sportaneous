package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.FriendsEntity;

public class FriendsDA {
	private static DB db;//declare map db
	private static ConcurrentMap<String, List<FriendsEntity>> friends;//store data base in the concurrentMap//Your storing a list of friend entity to the data base itself
	private static String sessionID = AccountsDA.getAdminNo();
	private static List<FriendsEntity> sessionFriendsList;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/friends.db")).closeOnJvmShutdown().make();

		friends = db.getTreeMap("friends");//assigning the friends data base to the concurrent map

		List<FriendsEntity> temp = new ArrayList<>();//users can store mulitple lists //it is an array list
		temp.add(new FriendsEntity("admin", "1", 1));//when you store admin 1 and admin 2 both pf the the values will be vise versa.both of the different accounts.
		temp.add(new FriendsEntity("admin", "2", 1));
		temp.add(new FriendsEntity("admin", "3", 1));
		temp.add(new FriendsEntity("admin", "4", 0));
		friends.put("admin", temp);//if the key value-(unique identifier for each account cannot have dulicates) (admin)

		db.commit();//commit the changes to the data base
	}

	/**
	 * [0] = AdminNo, [1] = Name, [2] = Height<br>
	 * [3] = Weight, [4] = Height Visibility, [5] = Weight Visibility<br>
	 * [6] = Rating, [7] = Match Played, [8] = Total Match
	 * 
	 * @return Object[][]
	 */
	
	//sessionFreindsList this is the current login user friend list.
	public static Object[][] getFriends() {//eg it is like int or String// you are storing the friends entity into the object
		sessionFriendsList = (friends.get(sessionID) != null ? friends.get(sessionID) : new ArrayList<>());//sessionFriendsList is a listFrined enity
		List<Object[]> dataList = new ArrayList<>();//sessionID is the session admin number getting the accoutsDA. 1)if it is not null it will store the friends entity list of the specific admin number.
		//?=if, :=else if.
		
		for (FriendsEntity friendsEntity : sessionFriendsList) {//for each friend entity in sessionFriendsList it will loop until it hits the last friends enity,so we store accepted friends into datalist(arraylist)
			Object[] accData = AccountsDA.getAccData(friendsEntity.getFriendAdminNo());//this is to get the account infomation based the friends admin number
			Object[] data = new Object[9];// to store required accounts information

			if (friendsEntity.getStatus() == 1) {//we do a check to see if it is a friend by 1, so it is 1 means it is a friend
				data[0] = accData[0]; // AdminNo
				data[1] = accData[3]; // Name
				data[2] = accData[7]; // Height
				data[3] = accData[8]; // Weight
				data[4] = accData[9]; // Height Visibility
				data[5] = accData[10]; // Weight Visibility
				data[6] = accData[11]; // Rating
				data[7] = accData[13]; // Match Played
				data[8] = accData[14]; // Total Match

				dataList.add(data);//this part gets added to the empty data list(line 45)
			}
		}

		return dataList.toArray(new Object[dataList.size()][]);//return it as a two dimintional array. row is for the friends entity and the column is the infomation of the friend entity from the datalist array
	}

	public static int checkStatus(String friendsID) {// check staus of specific admin number, friendsID is  friends admin number
		for (FriendsEntity friendsEntity : sessionFriendsList) {// keep looping until the current admin number equals to the parameter() adminnumber(friendsID)(line70)
			if (friendsEntity.getFriendAdminNo().equals(friendsID)) {//so if the friends entity equals admin number(friendsID) from the sessionFriendsList
				return friendsEntity.getStatus(); // 0 = Pending(displayed in notification), 1 = Friend
			}
		}

		return 3; // Not Friends(Not inside the data base)
	}
}
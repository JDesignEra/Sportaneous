package dataAccess;



import java.io.File;

import java.math.BigDecimal;

import java.util.concurrent.ConcurrentMap;



import org.mapdb.DB;

import org.mapdb.DBMaker;



import entity.AccountsEntity;

import entity.FriendsEntity;



public class FriendsDA {



	private static DB db;

	private static ConcurrentMap<Integer, FriendsEntity> friends;

	private static FriendsEntity session;



	public static void initDA() {

		db = DBMaker

				.newFileDB(new File("tmp/accounts.db"))

				.closeOnJvmShutdown()

				.make();



		friends = db.getTreeMap("friends");



		db.commit();

	}



	public static Object[][] getAllData() {

		Object[][] rowData = new Object[friends.size()][5];



		int i = 0;

		for (FriendsEntity  friendsEntity : friends.values()) {

			rowData[i][0] = friendsEntity.getUserID1();

			rowData[i][1] = friendsEntity.getUserID2();

			rowData[i][2] = friendsEntity.getUserName1();

			rowData[i][3] = friendsEntity.getUserName2();

			rowData[i][4] = friendsEntity.getStatus();



			i++;

		}



		return rowData;

	}



	public static int addFriend(String userID1, String userID2, String userName1, String userName2 , int status) {

		if (userID1.isEmpty() || userID2.isEmpty() || userName1.isEmpty() || userName2.isEmpty()) {

			return 0; // Fields required

		}



		if (friends.putIfAbsent(friends.size() + 1, new FriendsEntity(userID1, userID2, userName1, userName2, 0)) != null) {

			return 1; // Fail

		}



		db.commit();

		return 2; // Success

	}

	public static int removeAccount(String username) {



		for (int i = 0; i < getAllData().length; i++) {



			if (session.getUserName1().equals(username)){

				return 0; //delete session

			}

			else if (session.getUserName1().equals(username)){

				return 0;

			}

			if (friends.remove(username) == null){

				return 1; //fail

			}



		}

		db.commit();

		return 2; //success



	}





	public static void main(String args[]) {

		initDA();



		for (int i = 0; i < getAllData().length; i++) {

			int x = 0;

			for (Object j : getAllData()[i]) {

				System.out.println(j.toString());

			}



		}

	}

}
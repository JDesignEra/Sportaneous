package dataAccess;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.RatingsEntity;

public class RatingsDA {
	private static DB db;
	private static ConcurrentMap<String, List<RatingsEntity>> ratings;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/ratings.db")).closeOnJvmShutdown().make();
		ratings = db.getTreeMap("ratings");

//		List<RatingsEntity> temp = new ArrayList<>();
//		temp.add(new RatingsEntity(1, "admin", "Badminton", LocalDateTime.now(), new String[] { "1234a", "1234b", "4321a" }, new String[] { "", "", "" }, new int[] { 1, 1, 1 },
//				new int[] { 1, 0, 1 }, 3));
//		temp.add(new RatingsEntity(2, "admin", "BasketBall", LocalDateTime.now(), new String[] { "1234a", "4321a" }, new String[] { "", "" }, new int[] { 1, 1 },
//				new int[] { 1, 0 }, 0));
//		ratings.put("admin", temp);
//
//		temp = new ArrayList<>();
//		temp.add(new RatingsEntity(1, "admin", "BasketBall", LocalDateTime.now(), new String[] { "admin", "4321a" }, new String[] { "", "" }, new int[] { 1, 1 },
//				new int[] { 1, 0 }, 1));
//		temp.add(new RatingsEntity(2, "admin", "BasketBall", LocalDateTime.now(), new String[] { "admin", "4321a" }, new String[] { "", "" }, new int[] { 1, 1 },
//				new int[] { 1, 0 }, 0));
//		ratings.put("1234a", temp);
//
//		temp = new ArrayList<>();
//		temp.add(new RatingsEntity(1, "admin", "BasketBall", LocalDateTime.now(), new String[] { "admin", "1234a" }, new String[] { "", "" }, new int[] { 1, 1 },
//				new int[] { 1, 0 }, 1));
//		temp.add(new RatingsEntity(2, "admin", "BasketBall", LocalDateTime.now(), new String[] { "admin", "1234a" }, new String[] { "", "" }, new int[] { 1, 1 },
//				new int[] { 1, 0 }, 0));
//		ratings.put("4321a", temp);
		db.commit();
	}

	public static List<RatingsEntity> getRatings() {
		return ratings.get(AccountsDA.getAdminNo()) != null ? ratings.get(AccountsDA.getAdminNo()) : new ArrayList<>();
	}

	public static void addRatings(int matchID, String hostAdminNo, String sport, LocalDateTime dateTime, String[] adminNums) {
		for (String adminNo : adminNums) {
			List<RatingsEntity> ratingList = (ratings.get(adminNo) != null ? ratings.get(adminNo) : new ArrayList<>());
			String[] adminGrp = new String[adminNums.length - 1];
			int i = 0;

			for (String s : adminNums) {
				if (s != adminNo) {
					adminGrp[i] = s;
				}

				NotificationsDA.addNotifications(adminNo, sport, null, dateTime, 2);

				i++;
			}

			ratingList.add(new RatingsEntity(matchID, hostAdminNo, sport, dateTime, adminGrp, new String[adminGrp.length], new int[adminGrp.length], new int[adminGrp.length], 0));

			ratings.put(adminNo, ratingList);
			db.commit();
		}
	}

	public static void updateRatings(int matchID, String[] playerComments, int[] playerRatings, boolean[] playerAttendances) {
		String sessionID = AccountsDA.getAdminNo();
		List<RatingsEntity> tempList = new ArrayList<>();
		String[] adminNums = null;
		int noRated = 0;

		// Session
		List<RatingsEntity> ratingsList = (ratings.get(sessionID) != null ? ratings.get(sessionID) : new ArrayList<>());

		for (int i = 0; i < ratingsList.size(); i++) {
			if (ratingsList.get(i).getMatchID() == matchID) {
				RatingsEntity ratingsEntity = ratingsList.get(i);

				noRated = ratingsEntity.incrementAndGetNoRate();

				ratingsEntity.setComments(playerComments);
				ratingsEntity.setRatings(playerRatings);

				for (int j = 0; j < playerAttendances.length; j++) {
					if (playerAttendances[j]) {
						ratingsEntity.increaseAttendanceCount(j);
					}
				}

				// Last to submit or not
				if (noRated > ratingsEntity.getAdminNums().length) {
					adminNums = new String[ratingsEntity.getAdminNums().length + 1];
					adminNums[0] = sessionID;
					System.arraycopy(ratingsEntity.getAdminNums(), 0, adminNums, 1, ratingsEntity.getAdminNums().length);

					tempList.add(ratingsEntity);

					ratingsList.remove(i);
					ratings.put(sessionID, ratingsList);
					db.commit();

					break;
				}
				else {
					ratingsList.set(i, ratingsEntity);
					ratings.put(sessionID, ratingsList);
					db.commit();

					break;
				}
			}
		}

		// Others
		for (int i = 0; i < tempList.get(0).getAdminNums().length; i++) {
			String currAdminNo = tempList.get(0).getAdminNums()[i];
			ratingsList = (ratings.get(currAdminNo) != null ? ratings.get(currAdminNo) : new ArrayList<>());

			for (int j = 0; j < ratingsList.size(); j++) {
				if (ratingsList.get(j).getMatchID() == matchID) {
					RatingsEntity ratingsEntity = ratingsList.get(j);

					ratingsEntity.setNoRated(noRated);

					for (int k = 0; k < ratingsEntity.getAttendanceCount().length; k++) {
						if (k != 0 && k + 1 != i && playerAttendances[k]) {
							ratingsEntity.increaseAttendanceCount(k);
						}
						else if (k == 0 && k + 1 != i && playerAttendances[k]) {
							ratingsEntity.increaseAttendanceCount(k);
						}
					}

					// Last to submit or not
					if (noRated > ratingsEntity.getAdminNums().length) {
						tempList.add(ratingsEntity);

						ratingsList.remove(j);
						ratings.put(currAdminNo, ratingsList);
						db.commit();
					}
					else {
						ratingsList.set(j, ratingsEntity);
						ratings.put(currAdminNo, ratingsList);
						db.commit();
					}
				}
			}
		}

		// Commit to account
		if (adminNums != null && noRated > tempList.get(0).getAdminNums().length) {
			int j = 0;

			for (RatingsEntity ratingsEntity : tempList) {
				for (int i = 0; i < ratingsEntity.getAdminNums().length; i++) {
					if (ratingsEntity.getAttendanceCount()[i] >= (ratingsEntity.getAdminNums().length) / 2) {
						String adminNo = ratingsEntity.getAdminNums()[i];

						AccountsDA.updateAccRating(adminNo, ratingsEntity.getRatings()[i]);
						CommentsDA.addComment(adminNums[j], adminNo, ratingsEntity.getComments()[i], ratingsEntity.getRatings()[i]);
						
						if (j == 0 || (j == 1 && i == 0)) {
							AccountsDA.incrementAccMatch(adminNo, true);
						}
					}
					else {
						if (j == 0 || (j == 1 && i == 0)) {
							AccountsDA.incrementAccMatch(ratingsEntity.getAdminNums()[i], false);
						}
					}
				}

				j++;
			}
		}
	}
}

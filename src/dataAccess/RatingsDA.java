package dataAccess;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
//		temp.add(new RatingsEntity(1, "admin", "BasketBall", LocalDateTime.now(), new String[] { "1234a", "4321a" }, new String[] { "", "" }, new int[] { 1, 1 },
//				new int[] { 1, 0 }, 2));
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

				i++;
			}

			ratingList.add(new RatingsEntity(matchID, hostAdminNo, sport, dateTime, adminGrp, new String[adminGrp.length], new int[adminGrp.length], new int[adminGrp.length], 0));

			ratings.put(adminNo, ratingList);
			db.commit();
		}
	}

	public static void updateRatings(int matchID, String[] playerComments, int[] playerRatings, boolean[] playerAttendances) {
		String sessionID = AccountsDA.getAdminNo();
		List<RatingsEntity> ratingsList = (ratings.get(sessionID) != null ? ratings.get(sessionID) : new ArrayList<>());

		List<RatingsEntity> tempList = new ArrayList<>();
		String[] adminNums = null;
		int noRated = 0;

		int i = 0;

		i = 0;
		// Session
		for (RatingsEntity ratingsEntity : ratingsList) {
			if (ratingsEntity.getMatchID() == matchID) {
				noRated = ratingsEntity.incrementAndGetNoRate();

				adminNums = new String[ratingsEntity.getAdminNums().length + 1];
				adminNums[0] = sessionID;

				for (int j = 0; j < ratingsEntity.getAdminNums().length; j++) {
					adminNums[j + 1] = ratingsEntity.getAdminNums()[j];

					if (playerAttendances[j]) {
						ratingsEntity.increaseAttendanceCount(j);
					}
				}

				// Last to submit
				if (noRated > ratingsEntity.getAdminNums().length) {
					tempList.add(ratingsEntity);
					ratingsList.remove(i);

					ratings.put(sessionID, ratingsList);
					db.commit();
				}
				else {
					ratingsList.set(i, ratingsEntity);

					ratings.put(sessionID, ratingsList);
					db.commit();
				}
			}

			i++;
		}

		// Others
		for (int j = 1; j < adminNums.length; j++) {
			ratingsList = (ratings.get(adminNums[j]) != null ? ratings.get(adminNums[j]) : new ArrayList<>());
			i = 0;

			playerAttendances = Arrays.copyOf(playerAttendances, playerAttendances.length + 1);

			for (RatingsEntity ratingsEntity : ratingsList) {
				if (ratingsEntity.getMatchID() == matchID) {
					ratingsEntity.setNoRated(noRated);

					// Updated Attendance Count
					for (int k = 1; k < ratingsEntity.getAdminNums().length - 1; k++) {
						if (k != j && playerAttendances[k - 1]) {
							ratingsEntity.increaseAttendanceCount(k);
						}
					}

					if (noRated > ratingsEntity.getAdminNums().length) {
						tempList.add(ratingsEntity);
						ratingsList.remove(i);

						ratings.put(adminNums[j], ratingsList);
						db.commit();
					}
					else {
						ratingsList.set(i, ratingsEntity);

						ratings.put(adminNums[j], ratingsList);
						db.commit();
					}
				}

				i++;
			}
		}

		// Commit to account
		if (noRated >= adminNums.length) {
			i = 0;

			for (RatingsEntity ratingsEntity : tempList) {
				for (int j = 0; j < ratingsEntity.getAdminNums().length; j++) {
					if (ratingsEntity.getAttendanceCount()[j] >= (adminNums.length - 1) / 2) {
						AccountsDA.updateAccRating(ratingsEntity.getAdminNums()[j], ratingsEntity.getRatings()[j]);
						AccountsDA.incrementAccMatch(ratingsEntity.getAdminNums()[j], true);
						CommentsDA.addComment(adminNums[i], ratingsEntity.getAdminNums()[j], ratingsEntity.getComments()[j], ratingsEntity.getRatings()[j]);
					}
					else {
						AccountsDA.incrementAccMatch(ratingsEntity.getAdminNums()[j], false);
					}
				}

				i++;
			}
		}
	}
}

package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.RatingsEntity;

public class RatingsDA {
	private static DB db;
	private static ConcurrentMap<String, List<RatingsEntity>> ratings;
	private static String sessionID = AccountsDA.getAdminNo();

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/ratings.db")).closeOnJvmShutdown().make();
		ratings = db.getTreeMap("ratings");

		db.commit();
	}

	public static void addRating(String matchID, String[] adminNums) {
		for (String adminNo : adminNums) {
			List<RatingsEntity> ratingList = (ratings.get(adminNo) != null ? ratings.get(adminNo) : new ArrayList<>());
			String[] adminGrp = new String[adminNums.length - 1];
			int i = 0;

			for (String s : adminNums) {
				if (s != adminNo) {
					adminGrp[i] = s;
				}
			}

			ratingList.add(new RatingsEntity(matchID, adminGrp, new String[adminGrp.length], new int[adminGrp.length], new boolean[adminGrp.length], 0));

			ratings.put(adminNo, ratingList);
			db.commit();
		}
	}

	public static void updateRating(String matchID, String[] comments, int[] rating, boolean[] attendances) {
		List<RatingsEntity> ratingList = (ratings.get(sessionID) != null ? ratings.get(sessionID) : new ArrayList<>());
		String[] adminGrp = null;
		int noRated = 0;
		int i = 0;

		// Updated Session Account
		for (RatingsEntity ratingsEntity : ratingList) {
			if (ratingsEntity.getMatchID().equals(matchID)) {
				adminGrp = ratingsEntity.getAdminNums();

				ratingsEntity.setComments(comments);
				ratingsEntity.setRatings(rating);
				noRated = ratingsEntity.incrementAndGetNoRate();

				if (noRated == adminGrp.length) {
					ratingList.remove(i);
					ratings.put(sessionID, ratingList);
				}
				else {
					ratingList.set(i, ratingsEntity);
					ratings.put(sessionID, ratingList);
					break;
				}
			}

			i++;
		}

		// Updated Other Accounts
		for (String adminNo : adminGrp) {
			ratingList = (ratings.get(adminNo) != null ? ratings.get(adminNo) : new ArrayList<>());
			i = 0;

			for (RatingsEntity ratingsEntity : ratingList) {
				if (ratingsEntity.getMatchID().equals(matchID)) {
					if (ratingsEntity.getNoRated() == noRated) {
						ratingsEntity.incrementAndGetNoRate();
						
						ratingList.set(i, ratingsEntity);
						ratings.put(adminNo, ratingList);
					}
					else {
						ratingList.remove(i);
						ratings.put(adminNo, ratingList);
					}
				}
				
				i++;
			}
		}
	}
}

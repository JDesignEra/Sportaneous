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
	private static String sessionID = AccountsDA.getAdminNo();

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/ratings.db")).closeOnJvmShutdown().make();
		ratings = db.getTreeMap("ratings");

		List<RatingsEntity> temp = new ArrayList<>();
		temp.add(new RatingsEntity(1, "admin", "BasketBall", LocalDateTime.now(), new String[] {"1234a", "4321a"} , new String[] { "", "" }, new int[] { 0, 0 }, new boolean[] { true, true }, 0));
		temp.add(new RatingsEntity(1, "admin", "BasketBall", LocalDateTime.now(), new String[] {"1234a", "4321a"} , new String[] { "", "" }, new int[] { 0, 0 }, new boolean[] { true, true }, 0));
		temp.add(new RatingsEntity(1, "admin", "BasketBall", LocalDateTime.now(), new String[] {"1234a", "4321a"} , new String[] { "", "" }, new int[] { 0, 0 }, new boolean[] { true, true }, 0));
		ratings.put("admin", temp);
		db.commit();
	}

	public static List<RatingsEntity> getRatings() {
		return ratings.get(sessionID) != null ? ratings.get(sessionID) : new ArrayList<>();
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
			}

			ratingList.add(
					new RatingsEntity(matchID, hostAdminNo, sport, dateTime, adminGrp, new String[adminGrp.length], new int[adminGrp.length], new boolean[adminGrp.length], 0));

			ratings.put(adminNo, ratingList);
			db.commit();
		}
	}

	public static void updateRatings(int matchID, String[] comments, int[] rating, boolean[] attendances) {
		List<RatingsEntity> ratingList = (ratings.get(sessionID) != null ? ratings.get(sessionID) : new ArrayList<>());
		String[] adminGrp = null;
		int noRated = 0;
		int i = 0;

		// Update Session Account
		for (RatingsEntity ratingsEntity : ratingList) {
			if (ratingsEntity.getMatchID() == matchID) {
				adminGrp = ratingsEntity.getAdminNums(); // Store adminNumber there's not current sessionID
				noRated = ratingsEntity.incrementAndGetNoRate(); // Store No. of players rated

				ratingsEntity.setComments(comments);
				ratingsEntity.setRatings(rating);

				if (noRated == adminGrp.length) { // If is last player to submit ratings, remove it.
					ratingList.remove(i);
					ratings.put(sessionID, ratingList);

					db.commit();
					// TODO update comment's database and account's database
				}
				else { // Else just replace it.
					ratingList.set(i, ratingsEntity);
					ratings.put(sessionID, ratingList);
					break;
				}
			}

			i++;
		}

		// Update Other Accounts
		for (String adminNo : adminGrp) {
			ratingList = (ratings.get(adminNo) != null ? ratings.get(adminNo) : new ArrayList<>());
			i = 0;

			for (RatingsEntity ratingsEntity : ratingList) {
				if (ratingsEntity.getMatchID() == matchID) {
					if (ratingsEntity.getNoRated() == noRated) { // If is last player to submit ratings, remove it.
						ratingsEntity.incrementAndGetNoRate();

						ratingList.set(i, ratingsEntity);
						ratings.put(adminNo, ratingList);
						db.commit();
					}
					else { // Else just update No. of players rated
						ratingList.remove(i);
						ratings.put(adminNo, ratingList);
					}
				}

				i++;
			}
		}
	}
}

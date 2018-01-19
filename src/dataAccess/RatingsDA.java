package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.AccountsEntity;
import entity.RatingsEntity;

public class RatingsDA {
	private static DB db;
	private static ConcurrentMap<String, List<RatingsEntity>> ratings;
	private static String sessionID = AccountsDA.getAdminNo();

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/ratings.db")).closeOnJvmShutdown().make();
		ratings = db.getTreeMap("ratings");

		List<RatingsEntity> temp = new ArrayList<>();
		temp.add(new RatingsEntity("1", new String[] { "1", "2" }, new String[] { "1",  "2" }, new int[]{3, 0}, new boolean[] { true, true}, 1));
		temp.add(new RatingsEntity("2", new String[] { "3", "4" }, new String[] { "1",  "2" }, new int[]{3, 0}, new boolean[] { true, true}, 1));
		ratings.put("admin", temp);
		db.commit();
	}
	
	public static List<Object[][]> getSesssionRatingData() {
		List<RatingsEntity> ratingList = (ratings.get("admin") != null ? ratings.get("admin") : new ArrayList<>());
		List<Object[][]> data = new ArrayList<>();
		
		for (RatingsEntity ratingsEntity : ratingList) {
		}
		
		return data;
	}
	
	/*
	public static Object[][] getAllAccData() {
		List<RatingsEntity> ratingList = (ratings.get(sessionID) != null ? ratings.get(sessionID) : new ArrayList<>());
		Object[][] data = new Object[ratings.size()][15];
		int i = 0;

		for (RatingsEntity ratingsEntity : ratingList) {
			data[i][0] = (ratingsEntity).getMatchID();
			data[i][1] = (ratingsEntity).getAdminNums();
			data[i][2] = (ratingsEntity).getComments();
			data[i][3] = (ratingsEntity).getRatings();
			data[i][4] = (ratingsEntity).getAttendances();
			data[i][5] = (ratingsEntity).getNoRated();
		
			i++;
		}

		return data;
	}
	*/

	public static void addRatings(String matchID, String[] adminNums) {
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

	
	public static void updateRatings(String matchID, String[] comments, int[] rating, boolean[] attendances) {
		List<RatingsEntity> ratingList = (ratings.get(sessionID) != null ? ratings.get(sessionID) : new ArrayList<>());
		String[] adminGrp = null;
		int noRated = 0;
		int i = 0;

		// Update Session Account
		for (RatingsEntity ratingsEntity : ratingList) {
			if (ratingsEntity.getMatchID().equals(matchID)) {
				adminGrp = ratingsEntity.getAdminNums();	// Store adminNumber there's not current sessionID
				noRated = ratingsEntity.incrementAndGetNoRate();	// Store No. of players rated

				ratingsEntity.setComments(comments);
				ratingsEntity.setRatings(rating);

				if (noRated == adminGrp.length) {	// If is last player to submit ratings, remove it.
					ratingList.remove(i);
					ratings.put(sessionID, ratingList);
					
					db.commit();
					// TODO update comment's database and account's database
				}
				else {	// Else just replace it.
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
				if (ratingsEntity.getMatchID().equals(matchID)) {
					if (ratingsEntity.getNoRated() == noRated) {	// If is last player to submit ratings, remove it.
						ratingsEntity.incrementAndGetNoRate();
						
						ratingList.set(i, ratingsEntity);
						ratings.put(adminNo, ratingList);
						db.commit();
					}
					else {	// Else just update No. of players rated
						ratingList.remove(i);
						ratings.put(adminNo, ratingList);
					}
				}
				
				i++;
			}
		}
	}
	
	public static void main(String[] args) {
		initDA();
		
//		for (int i = 0; i < getSesssionRatingData().length; i++) {
//			for (Object s : getSesssionRatingData()[i]) {
//				System.out.println(s.toString());
//			}
//		}
	}
}

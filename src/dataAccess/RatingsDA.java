package dataAccess;

import java.io.File;
import java.io.IOException;
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

	public static void newRating(String hostAdminNo, String... adminNo) throws IOException {
		List<RatingsEntity> ratingList = new ArrayList<>();
		ratingList.add(new RatingsEntity(hostAdminNo, new int[] { 0, 0, 0, 0, 0 }, false));

		for (String s : adminNo) {
			ratingList.add(new RatingsEntity(s, new int[] { 0, 0, 0, 0, 0 }, false));
		}

		if ((ratings.putIfAbsent(hostAdminNo, ratingList)) == null) {
			throw new IOException("");
		}
	}

	public static void edit(String comment, int rating) {
		CommentsDA.addComment(sessionID, comment, rating);
	}

	public static void main(String[] args) {
		initDA();

	}

}

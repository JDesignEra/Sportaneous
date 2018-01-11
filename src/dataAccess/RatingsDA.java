package dataAccess;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.RatingsEntity;

public class RatingsDA {
	private static DB db;
	private static ConcurrentMap<String, List<RatingsEntity>> ratings;
	private static Atomic.Integer key;
	private static String sessionID = AccountsDA.getAdminNo();

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/ratings.db")).closeOnJvmShutdown().make();

		key = db.getAtomicInteger("friends_key");
		ratings = db.getTreeMap("ratings");

		db.commit();
	}

	public static Object[][] getAllData() {
		Object[][] rowData = new Object[ratings.size()][7];

		int i = 0;
		for (List<RatingsEntity> ratingsEntity : ratings.values()) {
			rowData[i][0] = ((RatingsEntity) ratingsEntity).getUID();
			rowData[i][1] = ((RatingsEntity) ratingsEntity).getStatus();
			rowData[i][2] = ((RatingsEntity) ratingsEntity).getAdminNo();
			rowData[i][3] = ((RatingsEntity) ratingsEntity).getUserName();
			rowData[i][4] = ((RatingsEntity) ratingsEntity).getRating();
			i++;
		}
		return rowData;
	}

	public static void add(){

	}

	public static void edit(String comment, double rating){
		CommentsDA.addComment(sessionID, comment, rating);
	}

	public static void main(String[] args) {
		initDA();

	}

}

package dataAccess;

import java.io.File;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.NotificationsEntity;
import entity.RatingsEntity;

public class RatingsDA {
	private static DB db;
	private static ConcurrentMap<String, RatingsEntity> ratings;
	private static RatingsEntity session;

	
	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/ratings.db")).closeOnJvmShutdown().make();

		ratings = db.getTreeMap("ratings");

		db.commit();
	}
	public static void main(String[] args) {
		initDA();

	}

}

package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.CommentsEntity;

public class CommentsDA {
	private static DB db;
	private static ConcurrentMap<String, List<CommentsEntity>> comments;

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/comments.db")).closeOnJvmShutdown().make();
		comments = db.getTreeMap("comments");

//		List<CommentsEntity> ceList = new ArrayList<>();
//		ceList.add(new CommentsEntity("1234a", "Jimmy Butler", "Great Player!", 4));
//		ceList.add(new CommentsEntity("4321a", "David Beckham", "Awesome team player!", 5));
//		ceList.add(new CommentsEntity("1234b", "Roger Federer", "Friendly player with pretty good skill", 3));
//		ceList.add(new CommentsEntity("4321b", "Lee Chong Wei", "Test", 4));
//		comments.put("admin", ceList);
		db.commit();
	}

	public static List<CommentsEntity> getComments(String adminNo) {
		return comments.get(adminNo) != null ? comments.get(adminNo) : new ArrayList<>();
	}

	/**
	 * @param adminNo
	 *            - Account's administrator number
	 * @param comment
	 *            - Comments to be added to the associated administrator number
	 * @param rating
	 *            - Rating given to be added to the associated administrator number
	 */
	public static void addComment(String adminNo, String comment, int rating) {
		String sessionAdminNo = AccountsDA.getAdminNo();
		String sessionName = AccountsDA.getName();
		List<CommentsEntity> commentsList;

		commentsList = comments.get(adminNo) != null ? comments.get(adminNo) : new ArrayList<>();

		commentsList.add(new CommentsEntity(sessionAdminNo, sessionName, comment, rating));
		comments.put(adminNo, commentsList);
	}
}

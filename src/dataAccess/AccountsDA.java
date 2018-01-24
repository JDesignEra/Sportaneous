package dataAccess;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.AccountsEntity;
import entity.FriendsEntity;

import modules.SendMail;

public class AccountsDA {

	private static DB db;
	private static ConcurrentMap<String, AccountsEntity> accounts;
	private static AccountsEntity session;
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@nyp.edu.sg$|"
			+ "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-\\\\+]+\\.nyp.edu.sg$";

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/accounts.db")).closeOnJvmShutdown().make();
		accounts = db.getTreeMap("accounts");

		// accounts.put("admin", new AccountsEntity("admin", "admin@nyp.edu.sg",
		// "password", "Administrator", "", "", "", 0, 0, false, false, new int[] { 0,
		// 0, 0, 0, 5 }, 0, 0, 0));
		// accounts.put("1234a", new AccountsEntity("1234a", "1234f@mymail.nyp.edu.sg",
		// "password", "Jimmy Butler", "Basketball", "Basketball,Squash,Tennis", "", 0,
		// 0, false, false,
		// new int[] { 1, 0, 3, 0, 0 }, 0, 0, 0));
		// accounts.put("4321a", new AccountsEntity("4321a", "4321a@mymail.nyp.edu.sg",
		// "password", "David Beckham", "Soccer", "Soccer,Squash,Basketball", "", 0, 0,
		// false, false,
		// new int[] { 1, 0, 0, 0, 1 }, 0, 0, 0));
		// accounts.put("1234b", new AccountsEntity("1234b", "1234b@mymail.nyp.edu.sg",
		// "password", "Roger Federer", "Tennis", "Basketball,Squash,Tennis", "", 0, 0,
		// false, false,
		// new int[] { 1, 0, 0, 2, 0 }, 0, 0, 0));
		// accounts.put("4321b", new AccountsEntity("4321b", "4321b@mymailnyp.edu.sg",
		// "password", "Lee Chong Wei", "Badminton", "Badminton,Squash,Tennis", "", 0,
		// 0, false, false,
		// new int[] { 1, 0, 1, 0, 0 }, 0, 0, 0));
		db.commit();
	}

	public static List<AccountsEntity> getAllData() {
		return new ArrayList<>(accounts.values());
	}

	public static List<AccountsEntity> getFriendsAcc() {
		List<AccountsEntity> accList = new ArrayList<>();

		for (FriendsEntity friendsEntity : FriendsDA.getFriends()) {
			if (friendsEntity.getStatus() == 1) {
				accList.add(accounts.get(friendsEntity.getFriendAdminNo()));
			}
		}

		return accList;
	}

	public static AccountsEntity getAccData(String adminNo) {
		return accounts.get(adminNo.toLowerCase());
	}

	public static void updateAccRating(String adminNo, int... ratings) {
		AccountsEntity accountsEntity = accounts.get(adminNo.toLowerCase());
		accountsEntity.setRating(ratings);

		accounts.replace(adminNo, accountsEntity);
	}

	public static void updateAccMatch(String adminNo, int matchPlayed, int totalMatch) {
		AccountsEntity accountEntity = accounts.get(adminNo.toLowerCase());
		accountEntity.setMatchPlayed(matchPlayed);
		accountEntity.setTotalMatch(totalMatch);

		accounts.remove(adminNo, accountEntity);
	}

	public static int login(String adminNo, String password) {
		AccountsEntity accountsEntity;
		adminNo = adminNo.toLowerCase();

		if (adminNo.isEmpty() || password.isEmpty()) {
			return 1; // Required field
		}

		if ((accountsEntity = accounts.get(adminNo)) == null || !accountsEntity.getPassword().equals(password)) {
			return 2; // Invalid Credentials
		}

		session = accountsEntity;
		return 0; // Success
	}

	public static int addAccount(String name, String adminNo, String email, String password) {
		adminNo = adminNo.toLowerCase();
		email = email.toLowerCase();

		if (adminNo.isEmpty() || email.isEmpty() || password.isEmpty() || name.isEmpty()) {
			return 1; // Fields required
		}

		if (!email.matches(EMAIL_REGEX)) {
			return 2; // Validation Fail.
		}

		for (AccountsEntity accountsEntity : accounts.values()) {
			if (accountsEntity.getEmail().equals(email)) {
				return 3; // Registered Email
			}
		}

		if (accounts.putIfAbsent(adminNo, new AccountsEntity(adminNo, email, password, name, "", "", "", 0, 0, false, false, new int[] { 0, 0, 0, 0, 0 }, 0, 0, 0)) != null) {
			return 4; // Registered Admin Number
		}

		String body = "You have registered an account with Sportaneous successfully.<br /><br />" + "Your account details are as follow:<br />" + "<strong>Admin Number: </strong>"
				+ adminNo + "<br />" + "<strong>Password: </strong>" + password;

		new SendMail().send(email, "[Sportaneous] Account Registered Successfully", body);

		db.commit();
		return 0; // Success
	}

	public static int editAccount(	String email, String password, String name, String favSport, String interestedSports, String intro, double height, double weight,
									boolean heightVisibility, boolean weightVisibility) {
		AccountsEntity accountsEntity;
		email = email.toLowerCase();

		if ((accountsEntity = accounts.get(session.getAdminNo())) == null) {
			return 1; // Does not exist
		}

		if (!email.matches(EMAIL_REGEX)) {
			return 2; // Validation Fail
		}

		if (password.isEmpty()) {
			session.getPassword();
		}
		else {
			accountsEntity.setPassword(password);
		}

		accountsEntity.setAdminNo(session.getAdminNo());
		accountsEntity.setEmail(!email.isEmpty() ? email : session.getEmail());
		accountsEntity.setName(!name.isEmpty() ? name : session.getName());
		accountsEntity.setFavSport(favSport);
		accountsEntity.setInterestedSports(interestedSports);
		accountsEntity.setIntro(intro);
		accountsEntity.setHeight(height);
		accountsEntity.setWeight(weight);
		accountsEntity.setHeightVisibility(heightVisibility);
		accountsEntity.setWeightVisibility(weightVisibility);
		accountsEntity.setRating(session.getRating());
		accountsEntity.setMatchPlayed(session.getMatchPlayed());
		accountsEntity.setTotalMatch(session.getTotalMatch());
		accountsEntity.setMatchID(session.getMatchID());

		accounts.replace(session.getAdminNo(), accountsEntity);

		if (session.getAdminNo().equals(session.getAdminNo())) {
			session = accountsEntity;
		}

		db.commit();
		return 0; // Success
	}

	public static int passwordReset(String email) {
		AccountsEntity accountsEntity = null;
		email = email.toLowerCase();
		String newPass = randomPassword();

		if (email.isEmpty()) {
			return 1; // Required fields
		}

		for (AccountsEntity entity : accounts.values()) {
			if (email.equals(entity.getEmail())) {
				accountsEntity = entity;
				break;
			}
		}

		if (accountsEntity == null) {
			return 2; // Invalid email
		}

		accountsEntity.setPassword(newPass);

		if (accounts.replace(accountsEntity.getAdminNo(), accountsEntity) == null) {
			return 3; // fail
		}

		String body = "You have recently reset you account's password.<br /><br />" + "Your account's new password details are as follow:<br />"
				+ "<strong style='font-size: 18px'>" + newPass + "</strong>";

		new SendMail().send(email, "[Sportaneous] Account Password Reset", body);

		db.commit();
		return 0;
	}

	public static void logout() {
		session = null;
	}

	private static String randomPassword() {
		final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "!@#$%^&*_=+-";
		SecureRandom random = new SecureRandom();
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < 16; i++) {
			int index = random.nextInt(CHARS.length());
			result.append(CHARS.charAt(index));
		}

		return result.toString();
	}

	public static AccountsEntity getSession() {
		return session;
	}

	public static String getAdminNo() {
		return session.getAdminNo();
	}

	public static String getEmail() {
		return session.getEmail();
	}

	public static String getPassword() {
		return session.getPassword();
	}

	public static String getName() {
		return session.getName();
	}

	public static String getFavSport() {
		return session.getFavSport();
	}

	public static String getInterestedSports() {
		return session.getInterestedSports();
	}

	public static String getIntro() {
		return session.getIntro();
	}

	public static int getMatchID() {
		return session.getMatchID();
	}

	public static double getHeight() {
		return session.getHeight();
	}

	public static double getWeight() {
		return session.getWeight();
	}

	public static boolean getHeightVisibility() {
		return session.getHeightVisibility();
	}

	public static boolean getWeightVisibility() {
		return session.getWeightVisibility();
	}

	public static double getRating() {
		return session.getCalRating();
	}

	public static double getCalRating() {
		return session.getCalRating();
	}

	public static int getMatchPlayed() {
		return session.getMatchPlayed();
	}

	public static int getTotalMatch() {
		return session.getTotalMatch();
	}
}

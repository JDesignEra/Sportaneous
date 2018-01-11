package dataAccess;

import java.io.File;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.AccountsEntity;

import modules.SendMail;

public class AccountsDA {

	private static DB db;
	private static ConcurrentMap<String, AccountsEntity> accounts;
	private static AccountsEntity session;
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@nyp.edu.sg$|^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-\\\\+]+\\.nyp.edu.sg$";

	private AccountsDA() {
		throw new IllegalStateException("AccountsDA class");
	}

	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/accounts.db")).closeOnJvmShutdown().make();
		accounts = db.getTreeMap("accounts");

		// accounts.put("admin", new AccountsEntity("admin", "admin@nyp.edu.sg",
		// "password", "Administrator", "", "", "", "", 0, 0, false, false, new int[] {
		// 0, 0, 0, 0, 5 }, 0, 0));
		// accounts.put("1", new AccountsEntity("1", "admin@nyp.edu.sg", "password",
		// "1", "Basketball", "Basketball,Squash,Tennis", "", "", 0, 0, false, false,
		// new int[] { 1, 0, 3, 0, 0 }, 0, 0));
		// accounts.put("2", new AccountsEntity("2", "admin@nyp.edu.sg", "password",
		// "2", "Basketball", "Basketball,Squash,Tennis", "", "", 0, 0, false, false,
		// new int[] { 1, 0, 0, 0, 1 }, 0, 0));
		// accounts.put("3", new AccountsEntity("3", "admin@nyp.edu.sg", "password",
		// "3", "Basketball", "Basketball,Squash,Tennis", "", "", 0, 0, false, false,
		// new int[] { 1, 0, 0, 2, 0 }, 0, 0));
		// accounts.put("4", new AccountsEntity("4", "admin@nyp.edu.sg", "password",
		// "4", "Basketball", "Basketball,Squash,Tennis", "", "", 0, 0, false, false,
		// new int[] { 1, 0, 1, 0, 0 }, 0, 0));
		db.commit();
	}

	public static Object[][] getAllData() {
		Object[][] data = new Object[accounts.size()][16];
		int i = 0;

		for (AccountsEntity accountsEntity : accounts.values()) {
			data[i][0] = accountsEntity.getAdminNo();
			data[i][1] = accountsEntity.getEmail();
			data[i][2] = accountsEntity.getPassword();
			data[i][3] = accountsEntity.getName();
			data[i][5] = accountsEntity.getFavSport();
			data[i][6] = accountsEntity.getInterestedSports();
			data[i][7] = accountsEntity.getIntro();
			data[i][8] = accountsEntity.getHeight();
			data[i][9] = accountsEntity.getWeight();
			data[i][10] = accountsEntity.getHeightVisibility();
			data[i][11] = accountsEntity.getWeightVisibility();
			data[i][12] = calRating(accountsEntity.getRating());
			data[i][14] = accountsEntity.getMatchPlayed();
			data[i][15] = accountsEntity.getTotalMatch();
			i++;
		}

		return data;
	}

	/**
	 * [0] = AdminNo, [1] = Email, [2] = Password, [3] = Name<br>
	 * [4] = Favorite Sport, [5] = Interested Sport, [6] = Intro<br>
	 * [7] = Height, [8] = Weight, [9] = Height Visibility<br>
	 * [10] = Weight Visibility, [11] = Rating, [12] = Number Of Players Rated<br>
	 * [13] = Total Matched Joined, [14] = Total Matched Attended
	 * 
	 * @param adminNo
	 * @return Object[]
	 */
	public static Object[] getAccData(String adminNo) {
		AccountsEntity accountsEntity = accounts.get(adminNo.toLowerCase());
		Object[] data = new Object[15];

		data[0] = accountsEntity.getAdminNo();
		data[1] = accountsEntity.getEmail();
		data[2] = accountsEntity.getPassword();
		data[3] = accountsEntity.getName();
		data[4] = accountsEntity.getFavSport();
		data[5] = accountsEntity.getInterestedSports();
		data[6] = accountsEntity.getIntro();
		data[7] = accountsEntity.getHeight();
		data[8] = accountsEntity.getWeight();
		data[9] = accountsEntity.getHeightVisibility();
		data[10] = accountsEntity.getWeightVisibility();
		data[11] = calRating(accountsEntity.getRating());
		data[13] = accountsEntity.getMatchPlayed();
		data[14] = accountsEntity.getTotalMatch();

		return data;
	}

	public static void updateAccRating(String adminNo, int... ratings) {
		AccountsEntity accountsEntity = accounts.get(adminNo.toLowerCase());
		accountsEntity.setRating(ratings);

		accounts.replace(adminNo, accountsEntity);
	}

	public static int login(String adminNo, String password) {
		AccountsEntity accountsEntity = null;
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

		if (accounts.putIfAbsent(adminNo, new AccountsEntity(adminNo, email, password, name, "", "", "", "", 0, 0, false, false, new int[] { 0, 0, 0, 0, 0 }, 0, 0)) != null) {
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
		accountsEntity.setMatchID(session.getMatchID());
		accountsEntity.setHeight(height);
		accountsEntity.setWeight(weight);
		accountsEntity.setHeightVisibility(heightVisibility);
		accountsEntity.setWeightVisibility(weightVisibility);
		accountsEntity.setRating(session.getRating());
		accountsEntity.setMatchPlayed(session.getMatchPlayed());
		accountsEntity.setTotalMatch(session.getTotalMatch());

		accounts.replace(session.getAdminNo(), accountsEntity);

		if (session != null && session.getAdminNo().equals(session.getAdminNo())) {
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

	public static String getMatchID() {
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
		return calRating(session.getRating());
	}

	public static int getMatchPlayed() {
		return session.getMatchPlayed();
	}

	public static int getTotalMatch() {
		return session.getTotalMatch();
	}

	private static double calRating(int[] rating) {
		if (rating.length != 5) {
			throw new IllegalArgumentException("rating must be the length of 5");
		}

		int total = 0;
		for (int d : rating) {
			total += d;
		}

		return total / (rating[0] + ((double) rating[1] / 2) + ((double) rating[2] / 3) + ((double) rating[3] / 4) + ((double) rating[4] / 5));
	}
}

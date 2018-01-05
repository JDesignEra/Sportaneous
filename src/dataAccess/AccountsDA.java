package dataAccess;

import java.io.File;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.AccountsEntity;

import modules.PasswordGenerator;
import modules.SendMail;

import application.Main;

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

		accounts.put("admin",
				new AccountsEntity("admin", "admin@nyp.edu.sg", "password", "Administrator", "Basketball", "Basketball,Squash,Tennis", "", "", 0, 0, false, false, 3.5, 0, 0, 0));
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
			data[i][12] = accountsEntity.getRating();
			data[i][13] = accountsEntity.getNoRate();
			data[i][14] = accountsEntity.getMatchPlayed();
			data[i][15] = accountsEntity.getTotalMatch();
			i++;
		}

		return data;
	}

	public static int login(String adminNo, String password) {
		AccountsEntity accountsEntity = null;
		adminNo = adminNo.toLowerCase();

		if (adminNo.isEmpty() || password.isEmpty()) {
			return 1; // Required field
		}

		if ((accountsEntity = accounts.get(adminNo)) == null) {
			return 2; // User does not exist
		}

		if (!accountsEntity.getPassword().equals(password)) {
			return 3; // Invalid password
		}

		session = accountsEntity;
		
		//-for testing only-//
		Main.currentUserAdminNo = session.getAdminNo();
		System.out.println(Main.currentUserAdminNo);
		//-------end-------//
		
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

		if (accounts.putIfAbsent(adminNo, new AccountsEntity(adminNo, email, password, name, "", "", "", "", 0, 0, false, false, 0, 0, 0, 0)) != null) {
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
		accountsEntity.setNoRate(session.getNoRate());
		accountsEntity.setMatchPlayed(session.getMatchPlayed());
		accountsEntity.setTotalMatch(session.getTotalMatch());

		accounts.replace(session.getAdminNo(), accountsEntity);

		if (session != null && session.getAdminNo().equals(session.getAdminNo())) {
			session = accountsEntity;
		}

		db.commit();
		return 0; // Success
	}

	public static int passwordReset(String adminNo, String email) {
		AccountsEntity accountsEntity;
		adminNo = adminNo.toLowerCase();
		email = email.toLowerCase();
		String newPass = new PasswordGenerator().newPassword();

		if (adminNo.isEmpty() || email.isEmpty()) {
			return 1; // Required fields
		}

		if (accounts.get(adminNo) == null) {
			return 2; // Invalid adminNo
		}

		if (!accounts.get(adminNo).getEmail().equals(email)) {
			return 3; // Invalid email
		}

		accountsEntity = accounts.get(adminNo);
		accountsEntity.setPassword(newPass);

		if (accounts.replace(adminNo, accountsEntity) == null) {
			return 4; // fail
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
		return session.getHeight();
	}

	public static boolean getHeightVisibility() {
		return session.getHeightVisibility();
	}

	public static boolean getWeightVisibility() {
		return session.getWeightVisibility();
	}

	public static double getRating() {
		return session.getRating();
	}

	public static int getNoRate() {
		return session.getNoRate();
	}

	public static int getMatchPlayed() {
		return session.getMatchPlayed();
	}

	public static int getTotalMatch() {
		return session.getTotalMatch();
	}
}

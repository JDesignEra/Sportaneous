package dataAccess;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.AccountsEntity;

public class AccountsDA {
	
	private static DB db;
	private static ConcurrentMap<String, AccountsEntity> accounts;
	private static AccountsEntity session;
	
	public static void initDA() {
		db = DBMaker
				.newFileDB(new File("tmp/accounts.db"))
				.closeOnJvmShutdown()
				.make();
		
		accounts = db.getTreeMap("saccounts");
		
		//accounts.put("admin", new AccountsEntity("admin", "admin@nype.edu.sg", "password", "Administrator", "", "", "", "", 0, 0, false, false, new BigDecimal(0), 0, 0, 0));
		db.commit();
	}
	
	public String getName() {
		return session.getName();
	}
	
	public String getPhoto() {
		return session.getPhoto();
	}
	
	public String getFavSport() {
		return session.getFavSport();
	}
	
	public String getInterestedSports() {
		return session.getInterestedSports();
	}
	
	public String getIntro() {
		return session.getIntro();
	}
	
	public double getHeight() {
		return session.getHeight();
	}
	
	public double getWeight() {
		return session.getHeight();
	}
	
	public boolean getHeightVisibility() {
		return session.getHeightVisibility();
	}
	
	public BigDecimal getRating() {
		return session.getRating();
	}
	
	public int getNoRate() {
		return session.getNoRate();
	}
	
	public int getMatchPlayed() {
		return session.getMatchPlayed();
	}
	
	public int getTotalMatch() {
		return session.getTotalMatch();
	}
	
	public static int login(String adminNo, String password) {
		AccountsEntity accountsEntity = null;
		
		if (adminNo.isEmpty() || password.isEmpty()) {
			return 0; // Success
		}
		
		if ((accountsEntity = accounts.get(adminNo)) == null) {
			return 1; // user does not exist
		}
		
		session = accountsEntity;
		return 3; // Success
	}
	
	public static int addAccount(String adminNo, String email, String password, String name) {
		if (adminNo.isEmpty() || email.isEmpty() || password.isEmpty() || name.isEmpty()) {
			return 0; // Fields required
		}
		
		if (accounts.putIfAbsent(adminNo, new AccountsEntity(adminNo, email, password, name, "", "", "", "", 0, 0, false, false, new BigDecimal(0), 0, 0, 0)) != null) {
			return 1; // Fail
		}
		
		db.commit();
		return 2; // Success
	}
	
	public static int editAccount(String adminNo, String password, String name, String photo, String favSport, String interestedSports, String intro, double height, double weight, boolean heightVisibility, boolean weightVisibility) {
		AccountsEntity accountsEntity;
		
		if ((accountsEntity = accounts.get(adminNo)) == null) {
			return 0; // Does not exist
		}
		
		accountsEntity.setPassword(password);
		accountsEntity.setName(name);
		accountsEntity.setPhoto(photo);
		accountsEntity.setFavSport(favSport);
		accountsEntity.setInterestedSports(interestedSports);
		accountsEntity.setIntro(intro);
		accountsEntity.setHeight(height);
		accountsEntity.setWeight(weight);
		accountsEntity.setHeightVisibility(heightVisibility);
		accountsEntity.setWeightVisibility(weightVisibility);
		
		accounts.replace(adminNo, accountsEntity);
		db.commit();
		
		if (session != null && session.getAdminNo().equals(adminNo)) {
			session = accountsEntity;
		}
		
		return 1; // Success
	}
	
	public static void logout() {
		session = null;
	}
	
	public static Object[][] getAllData() {
		Object[][] rowData = new Object[accounts.size()][16];
		
		int i = 0;
		for (AccountsEntity accountsEntity : accounts.values()) {
			rowData[i][0] = accountsEntity.getAdminNo();
			rowData[i][1] = accountsEntity.getEmail();
			rowData[i][2] = accountsEntity.getPassword();
			rowData[i][3] = accountsEntity.getName();
			rowData[i][4] = accountsEntity.getPhoto();
			rowData[i][5] = accountsEntity.getFavSport();
			rowData[i][6] = accountsEntity.getInterestedSports();
			rowData[i][7] = accountsEntity.getIntro();
			rowData[i][8] = accountsEntity.getHeight();
			rowData[i][9] = accountsEntity.getWeight();
			rowData[i][10] = accountsEntity.getHeightVisibility();
			rowData[i][11] = accountsEntity.getWeightVisibility();
			rowData[i][12] = accountsEntity.getRating();
			rowData[i][13] = accountsEntity.getNoRate();
			rowData[i][14] = accountsEntity.getMatchPlayed();
			rowData[i][15] = accountsEntity.getTotalMatch();
			i++;
		}
		
		return rowData;
	}
	
	public static void main(String args[]) {
		initDA();
		
		for (int i = 0; i < getAllData().length; i++) {
			for (Object j : getAllData()[i]) {
				System.out.println(j.toString());
			}
		}
	}
}

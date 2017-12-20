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
		
		accounts = db.getTreeMap("accounts");
		
		//accounts.put("admin", new AccountsEntity("admin", "admin@nype.edu.sg", "password", "Administrator", "", "", "", 0, 0, false, false, new BigDecimal(0), 0, 0, 0));
		db.commit();
	}
	
	public static Object[][] getAllData() {
		Object[][] rowData = new Object[accounts.size()][15];
		
		int i = 0;
		for (AccountsEntity accountsEntity : accounts.values()) {
			rowData[i][0] = accountsEntity.getAdminNo();
			rowData[i][1] = accountsEntity.getEmail();
			rowData[i][2] = accountsEntity.getPassword();
			rowData[i][3] = accountsEntity.getName();
			rowData[i][4] = accountsEntity.getFavSport();
			rowData[i][5] = accountsEntity.getInterestedSports();
			rowData[i][6] = accountsEntity.getIntro();
			rowData[i][7] = accountsEntity.getHeight();
			rowData[i][8] = accountsEntity.getWeight();
			rowData[i][9] = accountsEntity.getHeightVisibility();
			rowData[i][10] = accountsEntity.getWeightVisibility();
			rowData[i][11] = accountsEntity.getRating();
			rowData[i][12] = accountsEntity.getNoRate();
			rowData[i][13] = accountsEntity.getMatchPlayed();
			rowData[i][14] = accountsEntity.getTotalMatch();
			i++;
		}
		
		return rowData;
	}
	
	public static int addAccount(String adminNo, String email, String password, String name) {
		if (adminNo.isEmpty() || email.isEmpty() || password.isEmpty() || name.isEmpty()) {
			return 0; // Fields required
		}
		
		if (accounts.putIfAbsent(adminNo, new AccountsEntity(adminNo, email, password, name, "", "", "", 0, 0, false, false, new BigDecimal(0), 0, 0, 0)) != null) {
			return 1; // Fail
		}
		
		db.commit();
		return 2; // Success
	}
	
	public static int editAccount(String adminNo, String password, String name, String favSport, String interestedSports, String intro, double height, double weight, boolean heightVisibility, boolean weightVisibility) {
		AccountsEntity accountsEntity;
		
		if ((accountsEntity = accounts.get(adminNo)) == null) {
			return 0; // Does not exist
		}
		
		accountsEntity.setPassword(password);
		accountsEntity.setName(name);
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
	
	public static void main(String args[]) {
		initDA();
		
		for (int i = 0; i < getAllData().length; i++) {
			int x = 0;
			for (Object j : getAllData()[i]) {
				System.out.println(j.toString());
			}
		}
	}
}

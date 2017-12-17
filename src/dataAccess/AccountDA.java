package dataAccess;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.AccountsEntity;

public class AccountDA {
	
	private static DB db;
	private static ConcurrentMap<String, AccountsEntity> accounts;
	private static AccountsEntity session;
	
	public static void initDA() {
		db = DBMaker
				.newFileDB(new File("tmp/accounts.db"))
				.closeOnJvmShutdown()
				.make();
		
		accounts = db.getTreeMap("accounts");
		
		//account.put("admin", new AccountsEntity("admin", "admin@nyp.edu.sg", "password", "Administrator", "Basketball", "Badminton,Frisbee,Soccer,Squash,Tennis", null, 0, 0, new BigDecimal(0), 0, 0, 0));
		db.commit();
	}
	
	public static Object[][] getAllData() {
		Object[][] rowData = new Object[accounts.size()][13];
		
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
			rowData[i][9] = accountsEntity.getRating();
			rowData[i][10] = accountsEntity.getNoRate();
			rowData[i][11] = accountsEntity.getMatchPlayed();
			rowData[i][12] = accountsEntity.getTotalMatch();
			i++;
		}
		
		return rowData;
	}
	
	public static int addAccount(String adminNo, String email, String name, String password) {
		if (adminNo.isEmpty() || password.isEmpty() || name.isEmpty() || password.isEmpty()) {
			return 0; // Fields required
		}
		
		if (accounts.putIfAbsent(adminNo, new AccountsEntity(adminNo, email, password, name, null, null, null, 0, 0, null, 0, 0, 0)) != null) {
			return 1; // Fail
		}
		
		db.commit();
		return 2; // Success
	}
	
	public static int editAccount(String adminNo, String password, String name, String favSport, String interestedSports, String intro, double height, double weight) {
		AccountsEntity accountsEntity;
		
		if (password.isEmpty()) {
			return 0; // Field required
		}
		
		if ((accountsEntity = accounts.get(adminNo)) == null) {
			return 1; // Does not exist
		}
		
		accountsEntity.setPassword(password);
		accountsEntity.setName(name);
		accountsEntity.setFavSport(favSport);
		accountsEntity.setInterestedSports(interestedSports);
		accountsEntity.setIntro(intro);
		accountsEntity.setHeight(height);
		accountsEntity.setWeight(weight);
		
		accounts.replace(adminNo, accountsEntity);
		db.commit();
		
		if (session != null && session.getAdminNo().equals(adminNo)) {
			session = accountsEntity;
		}
		
		return 2; // Success
	}
	
	public static void main(String args[]) {
		
	}
}

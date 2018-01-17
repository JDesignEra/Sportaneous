package application;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;

import javafx.scene.layout.RowConstraints;

import entity.RatingsEntity;

import dataAccess.AccountsDA;
import dataAccess.RatingsDA;

public class RatingsBlankController {
	@FXML
	private RowConstraints ratingContent;
//	private Object[][] ratings = RatingsDA.getAllData();
//	private static String sessionID = AccountsDA.getAdminNo();
//
//	public Object[][] getAllData() {
//		List<RatingsEntity> ratingList = (ratings.get(sessionID) != null ? ratings.get(sessionID) : new ArrayList<>());
//		
//		Object[][] data = new Object[ratings.size()][15];
//		int i = 0;
//
//		for (RatingsEntity ratingsEntity : ratingList) {
//			data[i][0] = (ratingsEntity).getMatchID();
//			data[i][1] = (ratingsEntity).getAdminNums();
//			data[i][2] = (ratingsEntity).getComments();
//			data[i][3] = (ratingsEntity).getRatings();
//			data[i][4] = (ratingsEntity).getAttendances();
//			data[i][5] = (ratingsEntity).getNoRated();
//		
//			i++;
//		}
//
//		return data;
//	}
}

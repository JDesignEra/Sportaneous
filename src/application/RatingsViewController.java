package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import dataAccess.AccountsDA;
import dataAccess.RatingsDA;

public class RatingsViewController implements Initializable {
	@FXML
	private RowConstraints ratingContent;

	@FXML GridPane ratingsGridPane;
	private static Object[][] ratings;
	private static int ratingIndex;

	@FXML

	private final URL ratingsURL = getClass().getResource("/application/modules/RatingsProfile.fxml");
	@Override
	public void initialize(URL location, ResourceBundle resources){
		RatingsDA.initDA();
        if (RatingsDA.getSessionRatingData(AccountsDA.getAdminNo()).length > 0) {
		ratings = RatingsDA.getSessionRatingData();
		int colCount = 0;
		int rowCount = 0;

		ratingsGridPane.getChildren().remove(0);
		ratingsGridPane.alignmentProperty().set(Pos.TOP_CENTER);
		ratingsGridPane.setManaged(true);

		for (ratingIndex = 0; ratingIndex < ratings.length; ratingIndex++) {
			try {
				if (colCount < 2) {
					ratingsGridPane.add(FXMLLoader.load(ratingsURL), colCount++, rowCount);
				}
				else {
					colCount = 0;
					ratingsGridPane.add(FXMLLoader.load(ratingsURL), colCount++, ++rowCount);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}


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

package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;

import javafx.scene.control.ScrollPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import dataAccess.AccountsDA;
import dataAccess.RatingsDA;

public class RateBackgroundController {
	@FXML
	private ScrollPane backgroundID;
	@FXML
	private JFXButton CloseID;

	@FXML GridPane ratingsGridPane;
	private static Object[][] ratings;
	private static int ratingIndex;

	private final URL ratingsURL = getClass().getResource("/application/modules/RatePlayer.fxml");
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
	// Event Listener on JFXButton[#CloseID].onAction
	@FXML
	public void close(ActionEvent event) {
		Main.getRoot().setRight(null);
	}

}

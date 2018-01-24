package application;

import java.net.URL;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import entity.RatingsEntity;

import dataAccess.RatingsDA;

import javafx.scene.layout.GridPane;

public class RatingsViewController {
	@FXML private VBox ratingContentVBox;
	@FXML private GridPane emptyRatingGridPane;

	private List<RatingsEntity> ratings;
	private int ratingIndex;

	private URL ratingsCardViewURL = getClass().getResource("/application/modules/RatingsCardView.fxml");

	@FXML
	private void initialize() {
		ratings = RatingsDA.getRatings();
		
		if (!ratings.isEmpty()) {
			ratingContentVBox.getChildren().clear();
			ratingContentVBox.setAlignment(Pos.TOP_CENTER);

			for (ratingIndex = 0; ratingIndex < ratings.size(); ratingIndex++) {
				try {
					ratingContentVBox.getChildren().add(FXMLLoader.load(ratingsCardViewURL));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getRatingIndex() {
		return ratingIndex;
	}
}

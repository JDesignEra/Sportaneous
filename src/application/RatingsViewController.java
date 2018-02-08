package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import entity.RatingsEntity;

import dataAccess.RatingsDA;

import application.modules.RatingsCardViewController;

public class RatingsViewController {
	@FXML private VBox ratingContentVBox;
	@FXML private GridPane emptyRatingGridPane;

	private final URL ratingsCardViewURL = getClass().getResource("/application/modules/RatingsCardView.fxml");
	
	@FXML
	private void initialize() {
		List<RatingsEntity> ratings = RatingsDA.getRatings();
		
		if (!ratings.isEmpty()) {
			ratingContentVBox.getChildren().clear();
			ratingContentVBox.setAlignment(Pos.TOP_CENTER);

			for (int ratingIndex = 0; ratingIndex < ratings.size(); ratingIndex++) {
				FXMLLoader loader = new FXMLLoader(ratingsCardViewURL);
				
				try {
					ratingContentVBox.getChildren().add(loader.load());
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
				RatingsCardViewController ratingsCardViewController = loader.getController();
				ratingsCardViewController.setRatings(ratings, ratingIndex);
			}
		}
	}
}
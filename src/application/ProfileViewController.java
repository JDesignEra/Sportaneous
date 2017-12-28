package application;

import javafx.fxml.FXML;

import javafx.scene.text.Text;

import java.math.BigDecimal;

import com.jfoenix.controls.JFXButton;

import dataAccess.AccountsDA;
import javafx.event.ActionEvent;

import javafx.scene.text.TextFlow;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public class ProfileViewController {
	@FXML private Text nameTxt;
	@FXML private Text heightWeightTxt;
	@FXML private Text ratingTxt;
	@FXML private Text matchNoTxt;
	@FXML private Text introTxt;
	@FXML private Label favSportChip;
	@FXML private Label intSportChip;
	@FXML private Circle dpCircle;
	@FXML private TextFlow favSportTxtFlow;
	@FXML private TextFlow intSportsTxtFlow;
	@FXML private JFXButton editBtn;
	@FXML private JFXButton prevComBtn;
	@FXML private JFXButton nxtComBtn;
	@FXML private GridPane profileGridPane;

	@FXML
	public void initialize() {
		String name = AccountsDA.getName();
		String favSport = AccountsDA.getFavSport();
		String intSports = AccountsDA.getInterestedSports();
		String intro = AccountsDA.getIntro();
		String photo = AccountsDA.getPhoto();
		int matchPlayed = AccountsDA.getMatchPlayed();
		int totalMatch = AccountsDA.getTotalMatch();
		double height = AccountsDA.getHeight();
		double weight = AccountsDA.getWeight();
		BigDecimal rating = AccountsDA.getRating();

		nameTxt.setText(name);
		matchNoTxt.setText(Integer.toString(matchPlayed) + " / " + Integer.toString(totalMatch));

		// Profile Photo
		Image img = new Image("/application/assets/uploads/default.png");
		ImageView imgView = new ImageView(img);
		Circle clip = new Circle(100, 100, 100);

		if (!photo.isEmpty()) {
			img = new Image("/application/assets/uploads/" + photo);
			imgView = new ImageView(img);

			// Crop
			if (img.getHeight() >= 200 || img.getWidth() >= 200) {
				int w = (int) img.getWidth();
				int h = (int) img.getHeight();

				if (img.getHeight() > img.getWidth()) {
					PixelReader pr = img.getPixelReader();
					WritableImage newImage = new WritableImage(pr, 0, (h - w) / 2, w, w);

					imgView.setImage(newImage);
				} else {
					PixelReader pr = img.getPixelReader();
					WritableImage newImage = new WritableImage(pr, (w - h) / 2, 0, h, h);

					imgView.setImage(newImage);
				}
			}
		}

		imgView.setFitWidth(200);
		imgView.setFitHeight(200);
		imgView.setClip(clip);

		profileGridPane.add(imgView, 0, 0);

		// Height & Weight
		if (AccountsDA.getHeightVisibility() || AccountsDA.getWeightVisibility()) {
			if (AccountsDA.getHeightVisibility() && AccountsDA.getWeightVisibility()) {
				heightWeightTxt.setText(Double.toString(height) + " m | " + Double.toString(weight) + " kg");
			} else if (AccountsDA.getHeightVisibility()) {
				heightWeightTxt.setText(Double.toString(height) + " m");
			} else if (AccountsDA.getWeightVisibility()) {
				heightWeightTxt.setText(Double.toString(weight) + " kg");
			}
		} else {
			heightWeightTxt.setVisible(false);
			heightWeightTxt.setManaged(false);
		}

		// Ratings
		if (rating.compareTo(BigDecimal.ZERO) > 0) {
			StringBuilder ratingStars = new StringBuilder();

			for (int i = 0; i < 5; i++) {
				if (i < rating.toBigInteger().intValue()) {
					ratingStars.append("\uf005 ");
				} else if (rating.subtract(BigDecimal.valueOf(i)).compareTo(new BigDecimal(0.5)) >= 0) {
					ratingStars.append("\uf123 ");
				} else {
					ratingStars.append((i < 4 ? "\uf006 " : "\uf006"));
				}
			}

			ratingTxt.setText(ratingStars.toString());
		}

		// Introduction
		if (!intro.isEmpty()) {
			introTxt.setText(intro);
		}

		// Favorite Sport
		if (!favSport.isEmpty()) {
			favSportChip.setText(favSport);
		}

		// Interested Sports
		if (!intSports.isEmpty()) {
			String[] intSport = intSports.split(",");

			intSportChip.setVisible(false);
			intSportChip.setManaged(false);

			for (String i : intSport) {
				Label lbl = new Label();
				lbl.setText(i);
				lbl.getStyleClass().add("sportChip");

				intSportsTxtFlow.getChildren().add(lbl);
			}
		}
	}

	// Event Listener on JFXButton[#editBtn].onAction
	@FXML
	public void editBtnOnAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on JFXButton[#prevComBtn].onAction
	@FXML
	public void prevComBtn(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on JFXButton[#nxtComBtn].onAction
	@FXML
	public void nxtComBtnOnAction(ActionEvent event) {
		// TODO Autogenerated
	}
}
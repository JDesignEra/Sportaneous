package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;

import com.jfoenix.controls.JFXButton;

import application.assets.modules.CommentsViewController;
import dataAccess.AccountsDA;
import dataAccess.CommentsDA;
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
	@FXML private GridPane commContentGridPane;
	@FXML private GridPane commentGridPane;

	private String adminNo = AccountsDA.getAdminNo();
	private String name = AccountsDA.getName();
	private String favSport = AccountsDA.getFavSport();
	private String intSports = AccountsDA.getInterestedSports();
	private String intro = AccountsDA.getIntro();
	private int matchPlayed = AccountsDA.getMatchPlayed();
	private int totalMatch = AccountsDA.getTotalMatch();
	private double height = AccountsDA.getHeight();
	private double weight = AccountsDA.getWeight();
	private double rating = AccountsDA.getRating();

	private Image img = new Image("/application/assets/uploads/default.png");
	private ImageView imgView = new ImageView(img);
	private Circle clip = new Circle(100, 100, 100);

	@FXML
	public void initialize() {
		CommentsDA.initDA();

		if (CommentsDA.getComments(adminNo).length > 1) {
			nxtComBtn.getStyleClass().remove("inactive");
			nxtComBtn.getStyleClass().add("danger");
		}

		nameTxt.setText(name);
		matchNoTxt.setText(Integer.toString(matchPlayed) + " / " + Integer.toString(totalMatch));

		// Profile Photo
		try {
			img = new Image("/application/assets/uploads/" + adminNo + ".png");
			imgView = new ImageView(img);

			// Crop
			if (img.getHeight() >= 200 || img.getWidth() >= 200) {
				int w = (int) img.getWidth();
				int h = (int) img.getHeight();

				if (img.getHeight() > img.getWidth()) {
					PixelReader pr = img.getPixelReader();
					WritableImage newImage = new WritableImage(pr, 0, (h - w) / 2, w, w);

					imgView.setImage(newImage);
				}
				else {
					PixelReader pr = img.getPixelReader();
					WritableImage newImage = new WritableImage(pr, (w - h) / 2, 0, h, h);

					imgView.setImage(newImage);
				}
			}
		}
		catch (Exception e) {
			e.getStackTrace();
		}

		imgView.setFitWidth(200);
		imgView.setFitHeight(200);
		imgView.setClip(clip);

		profileGridPane.add(imgView, 0, 0);

		// Height & Weight
		if (AccountsDA.getHeightVisibility() || AccountsDA.getWeightVisibility()) {
			if (AccountsDA.getHeightVisibility() && AccountsDA.getWeightVisibility()) {
				heightWeightTxt.setText(Double.toString(height) + " m | " + Double.toString(weight) + " kg");
			}
			else if (AccountsDA.getHeightVisibility()) {
				heightWeightTxt.setText(Double.toString(height) + " m");
			}
			else if (AccountsDA.getWeightVisibility()) {
				heightWeightTxt.setText(Double.toString(weight) + " kg");
			}
		}
		else {
			heightWeightTxt.setVisible(false);
			heightWeightTxt.setManaged(false);
		}

		// Ratings
		if (rating > 0) {
			StringBuilder ratingStars = new StringBuilder();

			for (int i = 0; i < 5; i++) {
				if (i < (int) rating) {
					ratingStars.append("\uf005 ");
				}
				else if ((rating - i) >= 0.5) {
					ratingStars.append("\uf123 ");
				}
				else {
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

		// Comments
		if (CommentsDA.getComments(adminNo) != null) {
			try {
				commentGridPane.getChildren().remove(commentGridPane.lookup(".commContentGridPane"));
				commContentGridPane = new GridPane();

				commContentGridPane = FXMLLoader.load(getClass().getResource("/application/assets/modules/CommentsView.fxml"));
				commContentGridPane.getStyleClass().add("commContentGridPane");

				commentGridPane.add(commContentGridPane, 0, 1);
				GridPane.setColumnSpan(commContentGridPane, GridPane.REMAINING);
			}
			catch (Exception e) {
				e.getStackTrace();
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
	public void prevComBtnOnAction(ActionEvent event) {
		if (CommentsDA.getComments(adminNo) != null) {
			if (CommentsViewController.getIndex() > 0) {
				try {
					CommentsViewController.setIndex(CommentsViewController.getIndex() - 1);

					commentGridPane.getChildren().remove(commentGridPane.lookup(".commContentGridPane"));
					commContentGridPane = new GridPane();

					commContentGridPane = FXMLLoader.load(getClass().getResource("/application/assets/modules/CommentsView.fxml"));
					commContentGridPane.getStyleClass().add("commContentGridPane");

					commentGridPane.add(commContentGridPane, 0, 1);
					GridPane.setColumnSpan(commContentGridPane, GridPane.REMAINING);

					nxtComBtn.getStyleClass().remove("inactive");
					nxtComBtn.getStyleClass().add("danger");

					if (CommentsViewController.getIndex() <= 0) {
						prevComBtn.getStyleClass().remove("danger");
						prevComBtn.getStyleClass().add("inactive");
					}
					else {
						prevComBtn.getStyleClass().remove("inactive");
						prevComBtn.getStyleClass().add("danger");
					}
				}
				catch (Exception e) {
					e.getStackTrace();
				}
			}
		}
	}

	// Event Listener on JFXButton[#nxtComBtn].onAction
	@FXML
	public void nxtComBtnOnAction(ActionEvent event) {
		if (CommentsDA.getComments(adminNo) != null) {
			if (CommentsViewController.getIndex() < CommentsDA.getComments(adminNo).length - 1) {
				try {
					CommentsViewController.setIndex(CommentsViewController.getIndex() + 1);

					commentGridPane.getChildren().remove(commentGridPane.lookup(".commContentGridPane"));
					commContentGridPane = new GridPane();

					commContentGridPane = FXMLLoader.load(getClass().getResource("/application/assets/modules/CommentsView.fxml"));
					commContentGridPane.getStyleClass().add("commContentGridPane");

					commentGridPane.add(commContentGridPane, 0, 1);
					GridPane.setColumnSpan(commContentGridPane, GridPane.REMAINING);

					prevComBtn.getStyleClass().remove("inactive");
					prevComBtn.getStyleClass().add("danger");

					if (CommentsViewController.getIndex() == CommentsDA.getComments(adminNo).length - 1) {
						nxtComBtn.getStyleClass().remove("danger");
						nxtComBtn.getStyleClass().add("inactive");
					}
					else {
						nxtComBtn.getStyleClass().remove("inactive");
						nxtComBtn.getStyleClass().add("danger");
					}
				}
				catch (Exception e) {
					e.getStackTrace();
				}
			}
		}
	}
}

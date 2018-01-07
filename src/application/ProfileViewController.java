package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import dataAccess.AccountsDA;
import dataAccess.CommentsDA;

import modules.TransitionAnimation;

import application.modules.CommentsViewController;

public class ProfileViewController implements Initializable {
	@FXML private Text nameTxt, heightWeightTxt, ratingTxt, matchNoTxt, introTxt;
	@FXML private Label favSportChip, intSportChip;
	@FXML private Circle dpCircle;
	@FXML private TextFlow favSportTxtFlow, intSportsTxtFlow;
	@FXML private JFXButton editBtn, prevComBtn, nxtComBtn;
	@FXML private GridPane profileGridPane, commentGridPane;
	@FXML private VBox commContentVBox;

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
	private GridPane commContentGridPane;

	private final URL commentsViewURL = getClass().getResource("/application/modules/CommentsView.fxml");
	private final URL editProfileViewURL = getClass().getResource("/application/EditProfileView.fxml");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CommentsDA.initDA();

		if (CommentsDA.getComments(adminNo).length > 1) {
			nxtComBtn.getStyleClass().remove("inactive");
			nxtComBtn.getStyleClass().add("danger");
		}

		nameTxt.setText(name);
		matchNoTxt.setText(Integer.toString(matchPlayed) + " / " + Integer.toString(totalMatch));

		// Profile Photo
		if (getClass().getResource("/application/assets/uploads/" + adminNo + ".png") != null) {
			img = new Image(getClass().getResource("/application/assets/uploads/" + adminNo + ".png").toExternalForm());
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
			commentGridPane.getChildren().remove(commentGridPane.lookup(".commContent"));
			commContentGridPane = new GridPane();

			try {
				commContentGridPane = FXMLLoader.load(commentsViewURL);
			}
			catch (Exception e) {
				e.getStackTrace();
			}

			commContentGridPane.getStyleClass().add("commContent");

			commentGridPane.add(commContentGridPane, 0, 1);
			GridPane.setColumnSpan(commContentGridPane, GridPane.REMAINING);
		}
	}

	// Event Listener on JFXButton[#editBtn].onAction
	@FXML
	public void editBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(editProfileViewURL));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Event Listener on JFXButton[#prevComBtn].onAction
	@FXML
	public void prevComBtnOnAction(ActionEvent event) {
		if (CommentsDA.getComments(adminNo) != null && CommentsViewController.getIndex() > 0) {
			new Timeline(new KeyFrame(Duration.ZERO, fadeOutEv -> {
				new TransitionAnimation().fadeOut(250, commContentGridPane, 1.0);
			}), new KeyFrame(Duration.millis(300), actionEv -> {
				CommentsViewController.setIndex(CommentsViewController.getIndex() - 1);
				commentGridPane.getChildren().remove(commentGridPane.lookup(".commContent"));
				commContentGridPane = new GridPane();

				try {
					commContentGridPane = FXMLLoader.load(commentsViewURL);
				}
				catch (Exception e) {
					e.getStackTrace();
				}

				commContentGridPane.getStyleClass().add("commContent");

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

				new TransitionAnimation().fromRightFadeIn(500, commContentGridPane, commentGridPane.getPadding().getLeft() * 2);
			})).play();
		}
	}

	// Event Listener on JFXButton[#nxtComBtn].onAction
	@FXML
	public void nxtComBtnOnAction(ActionEvent event) {
		if (CommentsDA.getComments(adminNo) != null && CommentsViewController.getIndex() < CommentsDA.getComments(adminNo).length - 1) {
			new Timeline(new KeyFrame(Duration.ZERO, fadeOutEv -> {
				new TransitionAnimation().fadeOut(250, commContentGridPane, 1.0);
			}), new KeyFrame(Duration.millis(300), actionEv -> {
				CommentsViewController.setIndex(CommentsViewController.getIndex() + 1);
				commentGridPane.getChildren().remove(commentGridPane.lookup(".commContent"));
				commContentGridPane = new GridPane();

				try {
					commContentGridPane = FXMLLoader.load(commentsViewURL);
				}
				catch (Exception e) {
					e.getStackTrace();
				}

				commContentGridPane.getStyleClass().add("commContent");

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

				new TransitionAnimation().fromLeftFadeIn(500, commContentGridPane, commentGridPane.getPadding().getRight() * 2);
			})).play();
		}
	}

}

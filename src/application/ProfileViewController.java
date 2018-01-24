package application;

import java.net.URL;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import entity.AccountsEntity;

import dataAccess.AccountsDA;
import dataAccess.CommentsDA;
import dataAccess.FriendsDA;

import modules.Misc;
import modules.TransitionAnimation;

import application.modules.CommentsViewController;

public class ProfileViewController {
	@FXML private Text nameTxt, heightWeightTxt, ratingTxt, matchNoTxt, introTxt;
	@FXML private Label favSportChip, intSportChip;
	@FXML private Circle dpCircle;
	@FXML private TextFlow favSportTxtFlow, intSportsTxtFlow;
	@FXML private JFXButton actionBtn, prevComBtn, nxtComBtn, bckBtn;
	@FXML private GridPane profileGridPane, commentGridPane, commContentGridPane;
	@FXML private StackPane stackPaneRoot;
	@FXML private FlowPane buttonsFlowPane;

	private static String adminNo = AccountsDA.getAdminNo();
	private static String name = AccountsDA.getName();
	private static String favSport = AccountsDA.getFavSport();
	private static String intSports = AccountsDA.getInterestedSports();
	private static String intro = AccountsDA.getIntro();
	private static int matchPlayed = AccountsDA.getMatchPlayed();
	private static int totalMatch = AccountsDA.getTotalMatch();
	private static double height = AccountsDA.getHeight();
	private static double weight = AccountsDA.getWeight();
	private static double rating = AccountsDA.getCalRating();
	private static boolean heightVisibility = AccountsDA.getHeightVisibility();
	private static boolean weightVisbility = AccountsDA.getWeightVisibility();

	private static int friendStatus = 3;
	private static String backURL;

	private static final URL profileViewURL = ProfileViewController.class.getResource("/application/ProfileView.fxml");
	private final URL commentsViewURL = getClass().getResource("/application/modules/CommentsView.fxml");
	private final URL editProfileViewURL = getClass().getResource("/application/EditProfileView.fxml");

	@FXML
	private void initialize() {
		profileGridPane.add(new Misc().cropCirclePhoto(adminNo, 100), 0, 0);
		nameTxt.setText(name);
		matchNoTxt.setText(Integer.toString(matchPlayed) + " / " + Integer.toString(totalMatch));
		ratingTxt.setText(new Misc().getRatingShapes(rating));

		// Height & Weight
		if (heightVisibility || weightVisbility) {
			if (heightVisibility && weightVisbility) {
				heightWeightTxt.setText(Double.toString(height) + " m | " + Double.toString(weight) + " kg");
			}
			else if (heightVisibility) {
				heightWeightTxt.setText(Double.toString(height) + " m");
			}
			else if (weightVisbility) {
				heightWeightTxt.setText(Double.toString(weight) + " kg");
			}
		}
		else {
			heightWeightTxt.setVisible(false);
			heightWeightTxt.setManaged(false);
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
		if (CommentsDA.getComments(adminNo).size() > 1) {
			nxtComBtn.setDisable(false);
		}

		if (!CommentsDA.getComments(adminNo).isEmpty()) {
			commentGridPane.getChildren().remove(commContentGridPane);

			commContentGridPane = new GridPane();
			CommentsViewController.setIndex(0);

			try {
				commContentGridPane = FXMLLoader.load(commentsViewURL);
			}
			catch (Exception e) {
				e.getStackTrace();
			}

			commentGridPane.add(commContentGridPane, 0, 1);
			GridPane.setColumnSpan(commContentGridPane, GridPane.REMAINING);
		}

		// Top button
		switch (friendStatus) {
			case 0:
				actionBtn.getStyleClass().remove("success");
				actionBtn.getStyleClass().add("danger");
				actionBtn.setText("Cancel Friend Request");
				bckBtn.setVisible(true);
				break;

			case 1:
				actionBtn.getStyleClass().remove("success");
				actionBtn.getStyleClass().add("danger");
				actionBtn.setText("Remove Friend");
				bckBtn.setVisible(true);
				break;

			case 2:
				actionBtn.setText("Add Friend");
				bckBtn.setVisible(true);
				break;
		}
	}

	// Event Listener on JFXButton[#actionBtn].onAction
	@FXML
	public void actionBtnOnAction(ActionEvent event) {
		switch (friendStatus) {
			case 0:
			case 1:
				JFXDialogLayout content = new JFXDialogLayout();
				JFXDialog dialog = new JFXDialog(stackPaneRoot, content, JFXDialog.DialogTransition.CENTER);

				if (friendStatus == 0) {
					content.setHeading(new Text("Cancel Friend Request"));
					content.setBody(new Text("Are you sure you want to cancel your friend request?\n" + "You will be redirected to your previous page if you select YES."));
				}
				else {
					content.setHeading(new Text("Remove Friend"));
					content.setBody(new Text("Are you sure you want to remove this person as your friend?\n" + "You will be redirected to your previous page if you select YES."));
				}

				// Dialog Yes Button
				JFXButton dialogYesBtn = new JFXButton("YES");
				dialogYesBtn.getStyleClass().add("success");
				dialogYesBtn.setCursor(Cursor.HAND);
				dialogYesBtn.setOnAction(yesEV -> {
					dialog.close();

					FriendsDA.removeFriend(adminNo);

					try {
						Main.getRoot().setCenter(FXMLLoader.load(getClass().getResource(backURL)));
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				});
				content.setActions(dialogYesBtn);

				// Dialog No Button
				JFXButton dialogNoBtn = new JFXButton("NO");
				dialogNoBtn.getStyleClass().add("danger");
				dialogNoBtn.setCursor(Cursor.HAND);
				dialogNoBtn.setOnAction(yesEV -> dialog.close());
				content.getActions().add(dialogNoBtn);

				dialog.show();
				break;

			case 2:
				actionBtn.getStyleClass().remove("success");
				actionBtn.getStyleClass().add("danger");
				actionBtn.setText("Cancel Friend Request");

				FriendsDA.addFriend(adminNo);
				break;

			default:
				try {
					Main.getRoot().setCenter(FXMLLoader.load(editProfileViewURL));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				break;
		}
	}

	// Event Listener on JFXButton[#bckBtn].onAction
	@FXML
	public void bckBtnOnAction(ActionEvent event) {
		if (backURL.contains("ProfileView.fxml")) {
			viewSessionProfile();
		}
		else {
			try {
				Main.getRoot().setCenter(FXMLLoader.load(getClass().getResource(backURL)));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Event Listener on JFXButton[#prevComBtn].onAction
	@FXML
	public void prevComBtnOnAction(ActionEvent event) {
		if (CommentsViewController.getIndex() > 0) {
			new Timeline(new KeyFrame(Duration.ZERO, fadeOutEv -> {
				new TransitionAnimation().fadeOut(250, commContentGridPane, 1.0);
			}), new KeyFrame(Duration.millis(300), actionEv -> {
				CommentsViewController.setIndex(CommentsViewController.getIndex() - 1);

				commContentGridPane = new GridPane();
				commContentGridPane.getStyleClass().add("commContent");

				try {
					commContentGridPane = FXMLLoader.load(commentsViewURL);
				}
				catch (Exception e) {
					e.getStackTrace();
				}

				commentGridPane.add(commContentGridPane, 0, 1);
				GridPane.setColumnSpan(commContentGridPane, GridPane.REMAINING);

				nxtComBtn.setDisable(false);

				if (CommentsViewController.getIndex() <= 0) {
					prevComBtn.setDisable(true);
				}
				else {
					prevComBtn.setDisable(false);
				}

				new TransitionAnimation().fromRightFadeIn(500, commContentGridPane, commentGridPane.getPadding().getLeft() * 2);
			})).play();
		}
	}

	// Event Listener on JFXButton[#nxtComBtn].onAction
	@FXML
	public void nxtComBtnOnAction(ActionEvent event) {
		if (CommentsViewController.getIndex() < CommentsDA.getComments(adminNo).size() - 1) {
			new Timeline(new KeyFrame(Duration.ZERO, fadeOutEv -> new TransitionAnimation().fadeOut(250, commContentGridPane, 1.0)),
					new KeyFrame(Duration.millis(300), actionEv -> {
						CommentsViewController.setIndex(CommentsViewController.getIndex() + 1);

						commContentGridPane = new GridPane();
						commContentGridPane.getStyleClass().add("commContent");

						try {
							commContentGridPane = FXMLLoader.load(commentsViewURL);
						}
						catch (Exception e) {
							e.getStackTrace();
						}

						commentGridPane.add(commContentGridPane, 0, 1);
						GridPane.setColumnSpan(commContentGridPane, GridPane.REMAINING);

						prevComBtn.setDisable(false);

						if (CommentsViewController.getIndex() == CommentsDA.getComments(adminNo).size() - 1) {
							nxtComBtn.setDisable(true);
						}
						else {
							nxtComBtn.setDisable(false);
						}

						new TransitionAnimation().fromLeftFadeIn(500, commContentGridPane, commentGridPane.getPadding().getRight() * 2);
					})).play();
		}
	}

	public static void viewSessionProfile() {
		backURL = "";
		friendStatus = 3;
		adminNo = AccountsDA.getAdminNo();
		name = AccountsDA.getName();
		favSport = AccountsDA.getFavSport();
		intSports = AccountsDA.getInterestedSports();
		intro = AccountsDA.getIntro();
		matchPlayed = AccountsDA.getMatchPlayed();
		totalMatch = AccountsDA.getTotalMatch();
		height = AccountsDA.getHeight();
		weight = AccountsDA.getWeight();
		rating = AccountsDA.getCalRating();
		heightVisibility = AccountsDA.getHeightVisibility();
		weightVisbility = AccountsDA.getWeightVisibility();

		try {
			Main.getRoot().setCenter(FXMLLoader.load(profileViewURL));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for view friend's / other's profile
	 * 
	 * @param adminNo
	 *            - Account's administrator number
	 * @param returnLocation
	 *            - URL location to return to
	 */
	public static void viewProfile(String adminNo, String returnLocation) {
		backURL = returnLocation;
		friendStatus = FriendsDA.checkStatus(adminNo);
		AccountsEntity account = AccountsDA.getAccData(adminNo);

		ProfileViewController.adminNo = account.getAdminNo();
		name = account.getName();
		favSport = account.getFavSport();
		intSports = account.getInterestedSports();
		intro = (account.getIntro().isEmpty() ? "It appears that this user does not need any introduction..." : account.getIntro());
		height = account.getHeight();
		weight = account.getWeight();
		heightVisibility = account.getHeightVisibility();
		weightVisbility = account.getWeightVisibility();
		rating = account.getCalRating();
		matchPlayed = account.getMatchPlayed();
		totalMatch = account.getTotalMatch();

		try {
			Main.getRoot().setCenter(FXMLLoader.load(profileViewURL));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

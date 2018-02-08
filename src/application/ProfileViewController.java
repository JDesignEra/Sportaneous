package application;

import java.io.IOException;
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
import javafx.scene.image.ImageView;
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

import modules.TransitionAnimation;
import modules.Utils;

import application.modules.CommentsViewController;

public class ProfileViewController {
	@FXML private Text nameTxt, heightWeightTxt, ratingTxt, matchNoTxt, introTxt;
	@FXML private Label favSportChip, intSportChip;
	@FXML private Circle dpCircle;
	@FXML private TextFlow favSportTxtFlow, intSportsTxtFlow;
	@FXML private JFXButton actionBtn, prevComBtn, nxtComBtn, bckBtn;
	@FXML private GridPane profileGridPane, commentGridPane, emptyCommContentGridPane;
	@FXML private StackPane stackPaneRoot;
	@FXML private FlowPane buttonsFlowPane;

	private GridPane commContentGridPane;

	private String adminNo = AccountsDA.getAdminNo();
	private String name = AccountsDA.getName();
	private String favSport = AccountsDA.getFavSport();
	private String intSports = AccountsDA.getInterestedSports();
	private String intro = AccountsDA.getIntro();
	private int matchPlayed = AccountsDA.getMatchPlayed();
	private int totalMatch = AccountsDA.getTotalMatch();
	private double height = AccountsDA.getHeight();
	private double weight = AccountsDA.getWeight();
	private double rating = AccountsDA.getCalRating();
	private boolean heightVisibility = AccountsDA.getHeightVisibility();
	private boolean weightVisbility = AccountsDA.getWeightVisibility();

	private int commentIndex = 0;
	private int friendStatus = 3;
	private String backURL;

	private final URL profileViewURL = getClass().getResource("/application/ProfileView.fxml");
	private final URL commentsViewURL = getClass().getResource("/application/modules/CommentsView.fxml");
	private final URL editProfileViewURL = getClass().getResource("/application/EditProfileView.fxml");

	private CommentsViewController commentsViewController;

	@FXML
	private void initialize() {
		if (profileGridPane.getChildren().get(profileGridPane.getChildren().size() - 1) instanceof ImageView) {
			profileGridPane.getChildren().remove(profileGridPane.getChildren().size() - 1);
		}

		profileGridPane.add(Utils.cropCirclePhoto(adminNo, 100), 0, 0);
		nameTxt.setText(name);
		matchNoTxt.setText(Integer.toString(matchPlayed) + " / " + Integer.toString(totalMatch));
		ratingTxt.setText(Utils.getRatingShapes(rating));

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
		else {
			nxtComBtn.setDisable(true);
		}

		if (!CommentsDA.getComments(adminNo).isEmpty()) {
			commentGridPane.getChildren().remove(emptyCommContentGridPane);
			commentGridPane.getChildren().remove(commContentGridPane);

			FXMLLoader loader = new FXMLLoader(commentsViewURL);

			try {
				commContentGridPane = loader.load();
				commContentGridPane.getStyleClass().add("commentContent");
			}
			catch (IOException e) {
				e.getStackTrace();
			}

			commentsViewController = loader.getController();
			commentsViewController.setComments(adminNo, commentIndex);

			commentGridPane.add(commContentGridPane, 0, 1);
			GridPane.setColumnSpan(commContentGridPane, GridPane.REMAINING);
		}
		else if (!commentGridPane.getChildren().contains(emptyCommContentGridPane)) {
			commentGridPane.getChildren().remove(commContentGridPane);

			commentGridPane.add(emptyCommContentGridPane, 0, 1);
			GridPane.setColumnSpan(emptyCommContentGridPane, GridPane.REMAINING);
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
	private void actionBtnOnAction(ActionEvent event) {
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
					friendStatus = 2;

					try {
						Main.getRoot().setCenter(new FXMLLoader(getClass().getResource(backURL)).load());
					}
					catch (IOException e) {
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
				friendStatus = 0;
				break;

			default:
				try {
					Main.getRoot().setCenter(new FXMLLoader(editProfileViewURL).load());
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
	}

	// Event Listener on JFXButton[#bckBtn].onAction
	@FXML
	private void bckBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(getClass().getResource(backURL)));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on JFXButton[#prevComBtn].onAction
	@FXML
	private void prevComBtnOnAction(ActionEvent event) {
		if (commentIndex > 0) {
			new Timeline(new KeyFrame(Duration.ZERO, fadeOutEv -> {
				TransitionAnimation.fadeOut(250, commContentGridPane, 1.0);
			}), new KeyFrame(Duration.millis(300), actionEv -> {
				commentsViewController.setComments(adminNo, --commentIndex);

				nxtComBtn.setDisable(false);

				if (commentIndex <= 0) {
					prevComBtn.setDisable(true);
				}
				else {
					prevComBtn.setDisable(false);
				}

				TransitionAnimation.fromRightFadeIn(500, commContentGridPane, commentGridPane.getPadding().getLeft() * 2);
			})).play();
		}
	}

	// Event Listener on JFXButton[#nxtComBtn].onAction
	@FXML
	private void nxtComBtnOnAction(ActionEvent event) {
		if (commentIndex < CommentsDA.getComments(adminNo).size() - 1) {
			new Timeline(new KeyFrame(Duration.ZERO, fadeOutEv -> TransitionAnimation.fadeOut(250, commContentGridPane, 1.0)),
					new KeyFrame(Duration.millis(300), actionEv -> {
						commentsViewController.setComments(adminNo, ++commentIndex);

						prevComBtn.setDisable(false);

						if (commentIndex == CommentsDA.getComments(adminNo).size() - 1) {
							nxtComBtn.setDisable(true);
						}
						else {
							nxtComBtn.setDisable(false);
						}

						TransitionAnimation.fromLeftFadeIn(500, commContentGridPane, commentGridPane.getPadding().getRight() * 2);
					})).play();
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
	public void viewProfile(String adminNo, String returnLocation) {
		FXMLLoader loader = new FXMLLoader(profileViewURL);

		try {
			Main.getRoot().setCenter(loader.load());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		ProfileViewController profileViewController = loader.getController();

		profileViewController.backURL = returnLocation;
		profileViewController.friendStatus = FriendsDA.checkStatus(adminNo);
		AccountsEntity account = AccountsDA.getAccData(adminNo);

		profileViewController.adminNo = account.getAdminNo();
		profileViewController.name = account.getName();
		profileViewController.favSport = account.getFavSport();
		profileViewController.intSports = account.getInterestedSports();
		profileViewController.intro = (account.getIntro().isEmpty() ? "It appears that this user does not need any introduction..." : account.getIntro());
		profileViewController.matchPlayed = account.getMatchPlayed();
		profileViewController.totalMatch = account.getTotalMatch();
		profileViewController.height = account.getHeight();
		profileViewController.weight = account.getWeight();
		profileViewController.heightVisibility = account.getHeightVisibility();
		profileViewController.weightVisbility = account.getWeightVisibility();
		profileViewController.rating = account.getCalRating();
		profileViewController.matchPlayed = account.getMatchPlayed();
		profileViewController.totalMatch = account.getTotalMatch();

		profileViewController.initialize();
	}

	public String getAdminNo() {
		return adminNo;
	}
}

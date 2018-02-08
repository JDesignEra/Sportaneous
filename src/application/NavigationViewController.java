package application;

import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXButton;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import dataAccess.AccountsDA;
import dataAccess.NotificationsDA;
import dataAccess.RatingsDA;

public class NavigationViewController {
	@FXML private JFXButton navProfileBtn, navPlayBtn, navFriendsBtn, navRatingsBtn, navNotiBtn, navLogoutBtn;
	@FXML private GridPane rootPane;

	private final URL lrfViewURL = getClass().getResource("/application/LRFView.fxml");
	private final URL profileViewURL = getClass().getResource("/application/ProfileView.fxml");
	private final URL findGameViewURL = getClass().getResource("/application/FindAGame_View.fxml");
	private final URL friendsViewURL = getClass().getResource("/application/FriendsView.fxml");
	private final URL notificationViewURL = getClass().getResource("/application/NotificationView.fxml");
	private final URL ratingsViewURL = getClass().getResource("/application/RatingsView.fxml");

	@FXML
	private void initialize() {
		Platform.runLater(() -> {
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), rootPane);
			translateTransition.setFromY(rootPane.getHeight());
			translateTransition.setToY(0);
			translateTransition.play();
		});
		
		notificationSizeCheck();
		ratingSizeCheck();
	}

	// Event Listener on JFXButton[#navProfileBtn].onAction
	@FXML
	private void navProfileBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(profileViewURL));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		for (Node node : rootPane.getChildren()) {
			node.getStyleClass().remove("active");
		}

		navProfileBtn.getStyleClass().add("active");
		notificationSizeCheck();
	}

	// Event Listener on JFXButton[#navPlayBtn].onAction
	@FXML
	private void navPlayBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(findGameViewURL));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		for (Node node : rootPane.getChildren()) {
			node.getStyleClass().remove("active");
		}

		navPlayBtn.getStyleClass().add("active");
		notificationSizeCheck();
	}

	// Event Listener on JFXButton[#navFriendsBtn].onAction
	@FXML
	private void navFriendsBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(friendsViewURL));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		for (Node node : rootPane.getChildren()) {
			node.getStyleClass().remove("active");
		}

		navFriendsBtn.getStyleClass().add("active");
		notificationSizeCheck();
	}

	// Event Listener on JFXButton[#navRatingsBtn].onAction
	@FXML
	private void navRatingsBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(ratingsViewURL));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		for (Node node : rootPane.getChildren()) {
			node.getStyleClass().remove("active");
		}

		navRatingsBtn.getStyleClass().add("active");
		ratingSizeCheck();
	}

	// Event Listener on JFXButton[#navNotiBtn].onAction
	@FXML
	private void navNotiBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(notificationViewURL));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		for (Node node : rootPane.getChildren()) {
			node.getStyleClass().remove("active");
		}

		navNotiBtn.getStyleClass().add("active");
		notificationSizeCheck();
	}

	// Event Listener on JFXButton[#navLogoutBtn].onAction
	@FXML
	private void navLogoutBtnOnAction(ActionEvent event) {
		AccountsDA.logout();

		if (AccountsDA.getSession() == null) {
			try {
				Main.getRoot().setCenter(FXMLLoader.load(lrfViewURL));
				Main.getRoot().setBottom(null);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void notificationSizeCheck() {
		navNotiBtn.getStyleClass().remove("badge");
		
		if (!NotificationsDA.getNotifications().isEmpty()) {
			navNotiBtn.getStyleClass().add("badge");
		}
	}
	
	private void ratingSizeCheck() {
		navNotiBtn.getStyleClass().remove("badge");
		
		if (!RatingsDA.getRatings().isEmpty()) {
			navNotiBtn.getStyleClass().add("badge");
		}
	}
}

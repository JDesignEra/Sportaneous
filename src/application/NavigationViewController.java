package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import dataAccess.AccountsDA;

public class NavigationViewController implements Initializable {
	@FXML private JFXButton navProfileBtn, navPlayBtn, navFriendsBtn, navRatingsBtn, navNotiBtn, navLogoutBtn;
	@FXML private GridPane rootPane;

	private final URL lrfViewURL = getClass().getResource("/application/LRFView.fxml");
	private final URL findGameViewURL = getClass().getResource("/application/FindAGame_View.fxml");
	private final URL friendsViewURL = getClass().getResource("/application/FriendsView.fxml");
	private final URL notificationViewURL = getClass().getResource("/application/NotificationView.fxml");
	private final URL ratingsViewURL = getClass().getResource("/application/RatingsView.fxml");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), rootPane);
			translateTransition.setFromY(rootPane.getHeight());
			translateTransition.setToY(0);
			translateTransition.play();
		});
	}

	// Event Listener on JFXButton[#navProfileBtn].onAction
	@FXML
	public void navProfileBtnOnAction(ActionEvent event) {
		ProfileViewController.viewSessionProfile();

		for (Node node : rootPane.getChildren()) {
			node.getStyleClass().remove("active");
		}

		navProfileBtn.getStyleClass().add("active");
	}

	// Event Listener on JFXButton[#navPlayBtn].onAction
	@FXML
	public void navPlayBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(findGameViewURL));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		for (Node node : rootPane.getChildren()) {
			node.getStyleClass().remove("active");
		}

		navPlayBtn.getStyleClass().add("active");
	}

	// Event Listener on JFXButton[#navFriendsBtn].onAction
	@FXML
	public void navFriendsBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(friendsViewURL));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		for (Node node : rootPane.getChildren()) {
			node.getStyleClass().remove("active");
		}

		navFriendsBtn.getStyleClass().add("active");
	}

	// Event Listener on JFXButton[#navRatingsBtn].onAction
	@FXML
	public void navRatingsBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(ratingsViewURL));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		for (Node node : rootPane.getChildren()) {
			node.getStyleClass().remove("active");
		}

		navRatingsBtn.getStyleClass().add("active");
	}

	// Event Listener on JFXButton[#navNotiBtn].onAction
	@FXML
	public void navNotiBtnOnAction(ActionEvent event) {
		try {
			Main.getRoot().setCenter(FXMLLoader.load(notificationViewURL));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		for (Node node : rootPane.getChildren()) {
			node.getStyleClass().remove("active");
		}

		navNotiBtn.getStyleClass().add("active");
	}

	// Event Listener on JFXButton[#navLogoutBtn].onAction
	@FXML
	public void navLogoutBtnOnAction(ActionEvent event) {
		AccountsDA.logout();

		if (AccountsDA.getSession() == null) {
			try {
				Main.getRoot().setCenter(FXMLLoader.load(lrfViewURL));
				Main.getRoot().setBottom(null);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

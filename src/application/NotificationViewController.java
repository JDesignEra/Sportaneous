package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import entity.NotificationsEntity;

import dataAccess.NotificationsDA;

import application.modules.NotificationCardViewController;

public class NotificationViewController {
	@FXML private GridPane emptyNotifications, rootGridPane;
	@FXML private VBox notificationsVbox;

	private final URL notificationsURL = getClass().getResource("/application/modules/NotificationCardView.fxml");

	@FXML
	private void initialize() {
		List<NotificationsEntity> notifications = NotificationsDA.getNotifications();

		if (!notifications.isEmpty()) {
			notificationsVbox.getChildren().clear();
			notificationsVbox.alignmentProperty().set(Pos.TOP_CENTER);

			for (int notiIndex = 0; notiIndex < notifications.size(); notiIndex++) {
				FXMLLoader loader = new FXMLLoader(notificationsURL);
				
				try {
					notificationsVbox.getChildren().add(loader.load());
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
				NotificationCardViewController notificationCardViewController = loader.getController();
				notificationCardViewController.setNotification(notifications, notiIndex);
			}
		}
	}
}

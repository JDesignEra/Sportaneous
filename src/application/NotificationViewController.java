package application;

import java.net.URL;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import entity.NotificationsEntity;

import dataAccess.NotificationsDA;

public class NotificationViewController {
	@FXML private GridPane emptyNotifications, rootGridPane;
	@FXML private VBox notificationsVbox;

	private final URL notificationsURL = getClass().getResource("/application/Notification.fxml");
	private static List<NotificationsEntity> notification;
	private static int notiIndex;

	@FXML
	private void initialize() {
		notification = NotificationsDA.getNotifications();

		if (!notification.isEmpty()) {
			notificationsVbox.getChildren().clear();
			notificationsVbox.alignmentProperty().set(Pos.TOP_CENTER);

			for (notiIndex = 0; notiIndex < notification.size(); notiIndex++) {	
					try {
						notificationsVbox.getChildren().add(FXMLLoader.load(notificationsURL));
					}
					catch (Exception e) {
						e.printStackTrace();
					}		
			}
		}
	}

	public static int getNotiIndex() {
		return notiIndex;
	}

	public static List<NotificationsEntity> getNotification() {
		return notification;
	}
}

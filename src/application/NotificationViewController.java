package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;

import javafx.scene.layout.GridPane;

import entity.NotificationsEntity;

import dataAccess.NotificationsDA;

public class NotificationViewController {
	@FXML private FlowPane notificationsFlowPane;
	@FXML private GridPane emptyNotifications;

	private final URL notificationsURL = getClass().getResource("/application/Notification.fxml");
	private static List<NotificationsEntity> notification;
	private static int notiIndex;

	@FXML
	private void initialize() {
		if (!NotificationsDA.getNotifications().isEmpty()) {
			notificationsFlowPane.getChildren().clear();
			notificationsFlowPane.alignmentProperty().set(Pos.TOP_CENTER);
			showNotifications();
		}
	}

	private void showNotifications() {

		notification = NotificationsDA.getNotifications();

		notificationsFlowPane.getChildren().clear();

		if (notification.isEmpty()) {
			notificationsFlowPane.alignmentProperty().set(Pos.CENTER);
			notificationsFlowPane.getChildren().add(emptyNotifications);
		}
		else {
			for (notiIndex = 0; notiIndex < notification.size(); notiIndex++) {
				try {
					notificationsFlowPane.getChildren().add(FXMLLoader.load(notificationsURL));
				}
				catch (IOException e) {
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

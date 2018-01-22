package application;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;

import javafx.scene.layout.GridPane;

import dataAccess.NotificationsDA;

public class NotificationViewController {
	@FXML private FlowPane notificationsFlowPane;
	@FXML private GridPane emptyNotifications;

	private final URL notificationsURL = getClass().getResource("/application/modules/Notification.fxml");
	private static Object[][] notifications;
	private static int notiIndex;

	@FXML
	private void initialize() {
		if (NotificationsDA.getAllData().length > 0) {
			notificationsFlowPane.getChildren().clear();
			notificationsFlowPane.alignmentProperty().set(Pos.TOP_CENTER);
			showNotifications();
		}
	}

	private void showNotifications() {
		notifications = new Object[NotificationsDA.getAllData().length][7];
		Object[][] data = NotificationsDA.getAllData();

		notificationsFlowPane.getChildren().clear();

		for (notiIndex = 0; notiIndex < data.length; notiIndex++) {
			notifications[notiIndex][0] = data[notiIndex][0];	//adminNo
			notifications[notiIndex][1] = data[notiIndex][1];	//name
			notifications[notiIndex][2] = data[notiIndex][2];	//sports
			notifications[notiIndex][3] = data[notiIndex][3];	//location
			notifications[notiIndex][4] = data[notiIndex][4];	//date
			notifications[notiIndex][5] = data[notiIndex][5];	//time
			notifications[notiIndex][6] = data[notiIndex][6];	//status
			try {
				notificationsFlowPane.getChildren().add(FXMLLoader.load(notificationsURL));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getNotiIndex() {
		return notiIndex;
	}

	public static Object[][] getNotifications() {
		return notifications;
	}
}

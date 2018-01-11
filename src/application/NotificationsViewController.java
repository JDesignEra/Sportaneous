package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import dataAccess.NotificationsDA;

public class NotificationsViewController implements Initializable {
	@FXML private AnchorPane notiAnchorPane;
	@FXML private GridPane notiGridPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		NotificationsDA.initDA();

		if (NotificationsDA.getAccountNotifications(NotificationsDA.getAdminNo()).length > 0) {
			Object[][] notifications = NotificationsDA.getAccountNotifications(NotificationsDA.getAdminNo());

			notiGridPane.getChildren().remove(notiAnchorPane);

		}
	}
}

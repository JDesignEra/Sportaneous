package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import dataAccess.AccountsDA;
import dataAccess.NotificationsDA;

public class NotificationsViewController implements Initializable {
	@FXML private GridPane notiGridPane, NoNotiGridPane;

	private final URL notificationsURL = getClass().getResource("/application/modules/Notification.fxml");
	private static Object[][] notifications;
	private static int notiIndex;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		NotificationsDA.initDA();
		
		if (NotificationsDA.getAccountNotifications(AccountsDA.getAdminNo()).length > 0) {
			
			//notifications = NotificationsDA.getAccountNotifications(AccountsDA.getAccData(adminNo));
			
			int rowCount = 0;
			int colCount = 0;

			notiGridPane.getChildren().remove(0);
			notiGridPane.alignmentProperty().set(Pos.TOP_CENTER);
			notiGridPane.setManaged(true);

			for (notiIndex = 0; notiIndex < notifications.length; notiIndex++) {
				try {
					if (colCount < 2) {
						notiGridPane.add(FXMLLoader.load(notificationsURL), colCount++, rowCount++);
					}
					else {
						colCount = 0;
						notiGridPane.add(FXMLLoader.load(notificationsURL), colCount++, ++rowCount);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

package application.modules;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import entity.NotificationsEntity;

import dataAccess.AccountsDA;
import dataAccess.FriendsDA;
import dataAccess.HostsDA;
import dataAccess.NotificationsDA;

import modules.Utils;

import application.Main;

public class NotificationCardViewController {
	@FXML private Text iconTxt, titleTxt, contentTxt, nameTxt;
	@FXML private GridPane profileGridPane;
	@FXML private HBox actionBtnHBox;
	@FXML private JFXButton dismissBtn;

	private String hostName, hostAdminNo, sport, venue;
	private LocalDateTime dateTime;
	private int status, index;

	private final URL notificationViewURL = getClass().getResource("/application/NotificationView.fxml");

	@FXML
	public void initalize() {
		nameTxt.setText(hostName);
		profileGridPane.add(Utils.cropCirclePhoto(hostAdminNo, 75), 0, 0);

		switch (status) {
			case 0:
				titleTxt.setText("INVITATION");
				iconTxt.setText("\uf1e3 ");
				contentTxt.setText(hostName + " has invited you to a game of " + sport + " at the " + venue + " on " + dateTime.format(DateTimeFormatter.ofPattern("EEEE")) + ", "
						+ dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " at " + dateTime.format(DateTimeFormatter.ofPattern("h:m a")));
				
				dismissBtn.setText("DECLINE");
				dismissBtn.setPrefWidth(1600 / 2);
				dismissBtn.getStyleClass().add("right");
				
				addAcceptBtn("ACCEPT");
				break;

			case 1:
				titleTxt.setText("INVITATION DECLINED");
				iconTxt.setText("\uf08b ");
				contentTxt.setText(hostName + " has declined your game of " + sport + " at the " + venue + " on " + dateTime.format(DateTimeFormatter.ofPattern("EEEE")) + ", "
						+ dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " at " + dateTime.format(DateTimeFormatter.ofPattern("h:m a")));
				break;

			case 2:
				titleTxt.setText("RATING");
				iconTxt.setText("\uf005 ");
				contentTxt.setText("You have a new pending rating.");
				break;

			case 3:
				titleTxt.setText("FRIEND REQUEST");
				iconTxt.setText("\uf234 ");
				contentTxt.setText(hostName + " has send you a friend request");
				
				dismissBtn.setText("REJECT");
				dismissBtn.setPrefWidth(1600 / 2);
				dismissBtn.getStyleClass().add("right");
				
				addAcceptBtn("ACCEPT");
				break;
		}
	}

	// Event Listener on JFXButton[#dimissBtn].onAction
	@FXML
	public void dismissBtnOnAction(ActionEvent event) {
		if (status == 3) {
			FriendsDA.removeFriend(hostAdminNo);
		}
		else if (status == 0) {
			NotificationsDA.addNotifications(hostAdminNo, sport, venue, dateTime, 1);
			System.out.println(hostAdminNo);
			HostsDA.removeFriend(hostAdminNo, dateTime.toLocalDate(), dateTime.toLocalTime(), AccountsDA.getAdminNo().toLowerCase());
		} 

		NotificationsDA.deleteNotificaions(index);

		try {
			Main.getRoot().setCenter(FXMLLoader.load(notificationViewURL));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addAcceptBtn(String text) {
		JFXButton acceptBtn = new JFXButton(text);
		acceptBtn.getStyleClass().addAll("success", "left");
		acceptBtn.setPrefWidth(1600 / 2);

		EventHandler<ActionEvent> noBtnAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (status == 3) {
					FriendsDA.acceptRequest(hostAdminNo);
				}
				
				NotificationsDA.deleteNotificaions(index);
				
				try {
					Main.getRoot().setCenter(new FXMLLoader(notificationViewURL).load());
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		acceptBtn.setOnAction(noBtnAction);
		actionBtnHBox.getChildren().add(0, acceptBtn);
	}

	public void setNotification(List<NotificationsEntity> notifications, int index) {
		this.index = index;
		hostName = notifications.get(index).getHostName();
		hostAdminNo = notifications.get(index).getHostAd();
		sport = notifications.get(index).getSports();
		venue = notifications.get(index).getLocation();
		dateTime = notifications.get(index).getDateTime();
		status = notifications.get(index).getStatus();

		initalize();
	}
}

package application;

import java.time.LocalDateTime;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import entity.NotificationsEntity;

import dataAccess.AccountsDA;
import dataAccess.FriendsDA;
import dataAccess.HostsDA;
import dataAccess.NotificationsDA;

import modules.Utils;

public class NotificationController {
	@FXML private GridPane notiGridPane;
	@FXML private Label lblStatus;
	@FXML private Text lblNotification;
	@FXML private GridPane profileGridPane;
	@FXML private FlowPane btnActionFlowPane;
	@FXML private Label lblName;
	@FXML private Text txtIcon;
	@FXML private JFXButton yesButton;

	private int i = NotificationViewController.getNotiIndex();
	private List<NotificationsEntity> notifications = NotificationViewController.getNotification();

	private String userAdminNo = notifications.get(i).getAdminNo();
	private String hostName = notifications.get(i).getHostName();
	private String hostAd = notifications.get(i).getHostAd();
	private String sports = notifications.get(i).getSports();
	private String venue = notifications.get(i).getLocation();
	private LocalDateTime dateTime = notifications.get(i).getDateTime();
	private int status = notifications.get(i).getStatus();

	@FXML
	private void initialize() {
		
		lblName.setText(hostName);

		switch (status) {
			case 0:
				lblStatus.setText("Invitation");
				txtIcon.setText("");
				lblNotification.setText(hostName + " has invited you to a game of " + sports + " \nat the " + venue + " on " + dateTime);
				addNoBtn("Decline", 370);
				break;
//			case 1:
//				lblStatus.setText("Joined");
//				txtIcon.setText("");
//				lblNotification.setText(name + " has joined your game of " + sports + " \nat the " + venue + " on " + dateTime);
//				btnActionFlowPane.getChildren().remove(yesButton);
//				addNoBtn("Close", 740);
//				break;
			case 1:
				lblStatus.setText("Invitation declined...");
				txtIcon.setText("");
				lblNotification.setText(hostName + " has declined your game of " + sports + " \nat the " + venue + " on " + dateTime);
				btnActionFlowPane.getChildren().remove(yesButton);
				addNoBtn("Close", 740);
				break;
			case 2:
				lblStatus.setText("Rating");
				txtIcon.setText("");
				lblNotification.setText("You have pending ratings");
				btnActionFlowPane.getChildren().remove(yesButton);
				addNoBtn("Close", 740);
				break;
			case 3:
				lblStatus.setText("Friend Request");
				txtIcon.setText("");
				lblNotification.setText(hostName + " has sent you a friend request");
				yesButton.setText("Accept");
				addNoBtn("Decline", 370);
				break;
		}
		profileGridPane.add(Utils.cropCirclePhoto(hostAd, 100), 0, 0);
	}

	// Event Listener on JFXButton.onAction
	@FXML
	public void yesBtnOnAction(ActionEvent event) {
		if (status == 3) {
			FriendsDA.acceptRequest(hostAd);
		}
		
		NotificationsDA.deleteNotificaions(i);
		
		notiGridPane.setVisible(false);
		notiGridPane.setManaged(false);
	}

	public void addNoBtn(String text, int width) {
		JFXButton noBtn = new JFXButton(text);
		noBtn.getStyleClass().add("danger");
		noBtn.setMinWidth(Control.USE_PREF_SIZE);
		noBtn.setPrefWidth(width);
		noBtn.setMaxWidth(Control.USE_PREF_SIZE);
		btnActionFlowPane.getChildren().add(noBtn);
		
		EventHandler noBtnAction = new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				
				if (status == 0) {
					System.out.println("userAdminNo: " + userAdminNo);
					System.out.println(HostsDA.removeFriend(hostAd, dateTime.toLocalDate(), dateTime.toLocalTime(), AccountsDA.getAdminNo().toLowerCase()));
					NotificationsDA.deleteNotificaions(i);
					NotificationsDA.addNotifications(hostAd, sports, venue, dateTime, 1);
				}
				
				NotificationsDA.deleteNotificaions(i);
				notiGridPane.setVisible(false);
				notiGridPane.setManaged(false);
			}
		};

		noBtn.setOnAction(noBtnAction);
	}
}

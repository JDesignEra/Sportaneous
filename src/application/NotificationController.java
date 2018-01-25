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
import dataAccess.NotificationsDA;

import modules.Misc;

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

	private String adminNo = notifications.get(i).getAdminNo();
	private String name = notifications.get(i).getName();
	private String sports = notifications.get(i).getSports();
	private String venue = notifications.get(i).getLocation();
	private LocalDateTime dateTime = notifications.get(i).getDateTime();
	private int status = notifications.get(i).getStatus();

	private String fNameToUpper = name.replace(name.charAt(0), name.toUpperCase().charAt(0));

	@FXML
	private void initialize() {
		lblName.setText(fNameToUpper);

		switch (status) {
			case 0:
				lblStatus.setText("Invitation");
				txtIcon.setText("");
				lblNotification.setText(fNameToUpper + " has invited you to a game of " + sports + " \nat the " + venue + " on " + dateTime);
				addNoBtn("Decline", 370);
				break;
			case 1:
				lblStatus.setText("Joined");
				txtIcon.setText("");
				lblNotification.setText(fNameToUpper + " has joined your game of " + sports + " \nat the " + venue + " on " + dateTime);
				btnActionFlowPane.getChildren().remove(yesButton);
				addNoBtn("Close", 740);
				break;
			case 2:
				lblStatus.setText("");
				lblStatus.setText("Rating");
				lblNotification.setText("You have pending ratings");
				btnActionFlowPane.getChildren().remove(yesButton);
				addNoBtn("Close", 740);

				break;
			case 3:
				lblStatus.setText("");
				lblStatus.setText("Friend Request");
				lblNotification.setText(adminNo + ", " + fNameToUpper + " has sent you a friend request");
				yesButton.setText("Accept");
				addNoBtn("Decline", 370);
				break;
		}
		profileGridPane.add(new Misc().cropCirclePhoto(adminNo, 100), 0, 0);
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
				NotificationsDA.deleteNotificaions(AccountsDA.getAdminNo());
				// new Snackbar().success(notiGridPane, "Notifications cleared");

				notiGridPane.setVisible(false);
				notiGridPane.setManaged(false);
			}
		};

		noBtn.setOnAction(noBtnAction);
	}

	// Event Listener on JFXButton.onAction
	@FXML
	public void yesBtnOnAction(ActionEvent event) {
		NotificationsDA.addNotifications(sports, venue, dateTime, status);
		notiGridPane.setVisible(false);
		notiGridPane.setManaged(false);
	}
}

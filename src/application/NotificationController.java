package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.scene.text.Text;

import javafx.scene.control.Label;

import javafx.scene.shape.Circle;

import dataAccess.NotificationsDA;

public class NotificationController {
	@FXML private Text lblNotification;
	@FXML private Circle circlePicture;
	@FXML private Label lblName;
	@FXML private Label lblStatus;
	
	private String adminNo = NotificationsDA.getAdminNo();
	private String name = NotificationsDA.getName();
	private String sports = NotificationsDA.getSports();
	private String location = NotificationsDA.getLocation();
	private String date = NotificationsDA.getDate();
	private String time = NotificationsDA.getTime();
	private int status = NotificationsDA.getStatus();

	public void initialize(URL location, ResourceBundle resources) {
		NotificationsDA.initDA();
		
	}
}

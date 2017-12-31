package application;

import javafx.fxml.FXML;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;

import com.jfoenix.controls.JFXButton;

import dataAccess.HostsDA;
import entity.HostsEntity;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.shape.Circle;

public class FindAGame_hostedGameController {
	@FXML
	private Label eventDay;
	@FXML
	private Label eventDate;
	@FXML
	private Label eventTime;
	@FXML
	private Label sportsType;
	@FXML
	private JFXButton joinBtn;
	@FXML
	private Circle hostDP;
	@FXML
	private Label hostName;

	//Database//
	private ConcurrentMap<String, HostsEntity> hosts;
	private String[] sports = new String[] {"Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis"};
		
	public void initialize() {
			
		hosts = HostsDA.returnHostsList();

		ArrayList<String> list = new ArrayList<String>();

		for (String x : hosts.keySet()) {
			list.add(x);
		}

		String adminNo = list.get(FindAGameApp.index);
		String name = hosts.get(adminNo).getName();
		String sportsType = sports[hosts.get(adminNo).getSportsType()];
		LocalDate ld = hosts.get(adminNo).getDate();

		setHostName(name);
		setEventDay(ld);
		setEventDate(ld);
		setSportsType(sportsType);

		if (FindAGameApp.index == hosts.size()) {
			FindAGameApp.index = 0;
		}
		else {
			FindAGameApp.index++;
		}
			
		}

	// Event Listener on JFXButton[#joinBtn].onAction
	@FXML
	void handleJoin(ActionEvent event) {

	}

	void setEventDay(LocalDate ld) {

		java.time.DayOfWeek dayOfWeek = ld.getDayOfWeek();

		eventDay.setText(dayOfWeek.name());
	}

	void setEventDate(LocalDate ld) {
		eventDate.setText(ld.getDayOfMonth() + "/" + ld.getMonthValue() + "/" + ld.getYear());
	}

	void setEventTime(String t) {
		eventTime.setText(t);
	}

	void setHostName(String n) {
		hostName.setText(n);
	}

	void setSportsType(String s) {
		sportsType.setText(s.toUpperCase());
	}
	
}

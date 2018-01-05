package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import entity.HostsEntity;

import dataAccess.HostsDA;

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
	private HashMap<String, HostsEntity> searchR;
	private String[] sports = new String[] {"Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis"};
		
	public void initialize() {
	
		if (HostsDA.getSearchResults().isEmpty()) {
			searchR = HostsDA.returnHostsList();
		} else {
			searchR = HostsDA.getSearchResults();
		}

		ArrayList<String> list = new ArrayList<String>();

		for (String x : searchR.keySet()) {
			list.add(x);
		}

		String adminNo = list.get(FindAGameApp.index);
		String name = searchR.get(adminNo).getName();
		String sportsType = sports[searchR.get(adminNo).getSportsType()];
		LocalDate ld = searchR.get(adminNo).getDate();
		String time = searchR.get(adminNo).getTime();

		setHostName(name);
		setEventDay(ld);
		setEventDate(ld);
		setSportsType(sportsType);
		setEventTime(time);
		setDP(adminNo);

		if (FindAGameApp.index == searchR.size()) {
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
		
		int hr = 0;
		String tobeSet = "";
		if (t.length() == 4) {
			hr = Integer.parseInt(t.substring(0, 2));
			if (hr < 12) {
				tobeSet = hr + ":" + t.substring(2) + " AM";
			} else if (hr > 12){
				hr-=12;
				tobeSet = hr + ":" + t.substring(2) + " PM";
			} else {
				tobeSet = hr + ":" + t.substring(2) + " PM";
			}
		}
		
		eventTime.setText(tobeSet);
		
	}

	void setHostName(String n) {
		hostName.setText(n);
	}

	void setSportsType(String s) {
		sportsType.setText(s.toUpperCase());
	}
	
	void setDP(String adminNo) {

		Image img = new Image("/application/assets/uploads/" + adminNo + ".jpg");
		ImagePattern iv = new ImagePattern(img);
		
		hostDP.setFill(iv);
		
	}
	
}

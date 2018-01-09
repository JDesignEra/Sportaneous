package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


import com.jfoenix.controls.JFXButton;

import dataAccess.HostsDA;
import entity.HostsEntity;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FindAGame_hostedGameController {
	@FXML private Label eventDay;
	@FXML private Label eventDate;
	@FXML private Label eventTime;
	@FXML private Label sportsType;
	@FXML private JFXButton joinBtn;
	@FXML private Label noOfplayers;
	@FXML private Circle hostDP;
	@FXML private Label hostName;
	@FXML private Circle circularMeter;

	//Database//
	private HashMap<String, HostsEntity> searchR;
	private String[] sports = new String[] {"Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis"};
	private String adminNo = "";
		
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
		
		adminNo = list.get(FindAGameController.HostAGame_index);
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
		setNoOfPlayersLabel(adminNo);
		
		if (searchR.get(adminNo).getPlayersRecruited() != null && searchR.get(adminNo).getPlayersRecruited().contains(Main.currentUserAdminNo.toUpperCase())) {
			joinBtn.setDisable(true);
			joinBtn.setStyle("-fx-background-color: grey;");
			System.out.println(adminNo + " ALREADY INSIDE");
		}

		if (FindAGameController.HostAGame_index == searchR.size()-1) {
			FindAGameController.HostAGame_index = 0;
		} else {
			FindAGameController.HostAGame_index++;
		}
			
	}
	
	// Event Listener on JFXButton[#joinBtn].onAction
	@FXML
	void handleJoin(ActionEvent event) {
		System.out.println(Main.currentUserAdminNo.toUpperCase());
		System.out.println(adminNo.toUpperCase());
		
		HostsDA.addFriends(adminNo.toUpperCase(), Main.currentUserAdminNo.toUpperCase());
		
		setNoOfPlayersLabel(adminNo);
		
		joinBtn.setStyle("-fx-background-color: grey;");
	}
	
	@FXML
    void handleNoOfPlayersRecruited(MouseEvent event) throws IOException {
	    FindAGameController.whosegameisclicked = adminNo;
//	    System.out.println(FindAGameController.whosegameisclicked);
//	    System.out.println(HostsDA.getFriends(FindAGameController.whosegameisclicked).size());
		try {
			Main.getRoot().setRight(FXMLLoader.load(getClass().getResource("/application/FindAGame_ViewPlayer.fxml")));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
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
	
	void setNoOfPlayersLabel(String adminNo) {
		int total = checkNoOfPlayersNeeded(searchR.get(adminNo).getSportsType());
		int current;
		if (HostsDA.getHostDB().get(adminNo).getPlayersRecruited() == null) {
			current = 0;
		} 
		else {
			current = HostsDA.getHostDB().get(adminNo).getPlayersRecruited().size();
		}
		noOfplayers.setText(current + " / " + total);
	}
	
	int checkNoOfPlayersNeeded(int a) {
		if (sports[a].equals("Basketball")) {
			return 10;
		}
		if (sports[a].equals("Badminton")) {
			return 4;
		}
		if (sports[a].equals("Frisbee")) {
			return 7;
		}
		if (sports[a].equals("Soccer")) {
			return 22;
		}
		if (sports[a].equals("Tennis")) {
			return 4;
		}
		if (sports[a].equals("Squash")) {
			return 4;
		}
		
		return 0;
	}
	
}

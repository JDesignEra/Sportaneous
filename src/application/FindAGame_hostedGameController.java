package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import com.jfoenix.controls.JFXButton;

import dataAccess.HostsDA;
import modules.Utils;

import entity.HostsEntity;

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


	private ArrayList<HostsEntity> searchR;
	private String[] sports = new String[] {"Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis"};
	private HostsEntity toBeDisplayed_HostedGame;
	private String name, admin, typeOfSports;
	private int typeOfSportsIndex;
	private LocalDate date;
	private LocalTime time;
	private ArrayList<String> listOfRecruitedPlayers;
	private int joinedFriendsListSize = -100;
		
	public void initialize() {
		
		if (HostsDA.getSearchResults().isEmpty()) {
			searchR = HostsDA.returnHostsList();
		} else {
			searchR = HostsDA.getSearchResults();
		}
		
		toBeDisplayed_HostedGame = searchR.get(FindAGame_ViewController.HostAGame_index++);
		name = this.toBeDisplayed_HostedGame.getName();
		admin = this.toBeDisplayed_HostedGame.getAdminNo();
		typeOfSportsIndex = this.toBeDisplayed_HostedGame.getSportsType();
		typeOfSports = sports[typeOfSportsIndex];
		listOfRecruitedPlayers = this.toBeDisplayed_HostedGame.getPlayersRecruited();
		date = this.toBeDisplayed_HostedGame.getDate();
		time = this.toBeDisplayed_HostedGame.getTime();
		
		setHostName();
		setEventDay(date);
		setEventDate(date);
		setSportsType();
		setEventTime(time);
		setDP();
		setNoOfPlayersLabel();

		if (listOfRecruitedPlayers != null && listOfRecruitedPlayers.contains(Main.currentUserAdminNo)) {
			joinBtn.setDisable(true);
			joinBtn.setStyle("-fx-background-color: grey;");
			System.out.println("User has already joined the game that " + name + " (" + admin + ") hosted.");
		}
		
		if (listOfRecruitedPlayers != null && listOfRecruitedPlayers.size() == HostsDA.getGameSize(typeOfSportsIndex)) {
			joinBtn.setDisable(true);
			joinBtn.setStyle("-fx-background-color: grey;");
			System.out.println(name + "'s game is full-");
		}

		if (FindAGame_ViewController.HostAGame_index == searchR.size()) {
			FindAGame_ViewController.HostAGame_index = 0;
		} 
		
		if (admin.toLowerCase().equals(Main.currentUserAdminNo.toLowerCase())) {
			joinBtn.setStyle("-fx-background-color: grey;");
			joinBtn.setDisable(true);
		}
		
	}
	
	// Event Listener on JFXButton[#joinBtn].onAction
	@FXML
	void handleJoin(ActionEvent event) {
		System.out.println(Main.currentUserAdminNo.toUpperCase() + " is trying to join " + admin + "'s game.");
		
		this.joinedFriendsListSize = HostsDA.addFriends(admin, date, time, Main.currentUserAdminNo);
		joinBtn.setStyle("-fx-background-color: grey;");
		
		setNoOfPlayersLabel();

	}
	
	@FXML
    void handleNoOfPlayersRecruited(MouseEvent event) throws IOException {
		FindAGame_ViewController.clickedgame = toBeDisplayed_HostedGame;
		System.out.println("(hostedGameController) Whose game is clicked: " + FindAGame_ViewController.clickedgame.getAdminNo());
	    FindAGame_ViewController.whosegameisclicked = admin;
		try {
			if (listOfRecruitedPlayers != null || this.joinedFriendsListSize != -100) {
				Main.getRoot().setRight(FXMLLoader.load(getClass().getResource("/application/FindAGame_ViewPlayer.fxml")));
			} 
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

	void setEventTime(LocalTime time) {
		
		String tobeSet = time.format(DateTimeFormatter.ofPattern("hh:mm a"));
		
		eventTime.setText(tobeSet);
		
	}

	void setHostName() {
		hostName.setText(name);
	}

	void setSportsType() {
		sportsType.setText(typeOfSports.toUpperCase());
	}
	
	void setDP() {
		
		try {
			ImagePattern ip = new ImagePattern(Utils.cropCirclePhoto(admin, hostDP.getRadius()).getImage());
			hostDP.setFill(ip);
		}
		catch (Exception e2) {
			System.out.println("(FindAGame_ViewPlayerDetailsController) ERROR: UNABLE TO FIND " + admin + "'s PROFILE PICTURE. default.png IS USED INSTEAD.");
			hostDP.setFill(new ImagePattern(new Image("/application/assets/uploads/default.png")));
		}
	}
	
	void setNoOfPlayersLabel() {
		HostsDA.initDA();
		int total = HostsDA.getGameSize(this.toBeDisplayed_HostedGame.getSportsType());
		if (this.joinedFriendsListSize == -100) {
			if (this.toBeDisplayed_HostedGame.getPlayersRecruited() == null) {
				noOfplayers.setText(0 + " / " + total);
			} else {
				noOfplayers.setText(this.toBeDisplayed_HostedGame.getPlayersRecruited().size() + " / " + total);
			}
		} else {
			noOfplayers.setText(this.joinedFriendsListSize + " / " + total);
		}
//		noOfplayers.setText(current + " / " + total);
//		String s = this.toBeDisplayed_HostedGame.getPlayersRecruited().size() + " / " + total;
//		StringProperty sp = new SimpleStringProperty(s);
//		noOfplayers.textProperty().bind(sp);
	}
	

	
}

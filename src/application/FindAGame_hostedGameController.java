package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import com.jfoenix.controls.JFXButton;

import dataAccess.HostsDA;

import modules.Misc;

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
			System.out.println("User has already joined the game that " + name + "(" + admin + ") hosted.");
		}
		
		if (listOfRecruitedPlayers != null && listOfRecruitedPlayers.size() == HostsDA.getGameSize(typeOfSportsIndex)) {
			joinBtn.setDisable(true);
			joinBtn.setStyle("-fx-background-color: grey;");
			System.out.println(name + "'s game is full-");
		}

		if (FindAGame_ViewController.HostAGame_index == searchR.size()) {
			FindAGame_ViewController.HostAGame_index = 0;
		} 
	}
	
	// Event Listener on JFXButton[#joinBtn].onAction
	@FXML
	void handleJoin(ActionEvent event) {
		System.out.println(Main.currentUserAdminNo.toUpperCase());
		System.out.println(admin);
		HostsDA.addFriends(admin, date, time, Main.currentUserAdminNo.toUpperCase());
		setNoOfPlayersLabel();
		
		joinBtn.setStyle("-fx-background-color: grey;");
	}
	
	@FXML
    void handleNoOfPlayersRecruited(MouseEvent event) throws IOException {
		FindAGame_ViewController.clickedgame = toBeDisplayed_HostedGame;
		System.out.println("(hostedGameController) Whose game is clicked: " + FindAGame_ViewController.clickedgame.getAdminNo());
	    FindAGame_ViewController.whosegameisclicked = admin;
		try {
			if (listOfRecruitedPlayers != null) {
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
			ImagePattern ip = new ImagePattern(new Misc().cropCirclePhoto(admin, hostDP.getRadius()).getImage());
			hostDP.setFill(ip);
		}
		catch (Exception e2) {
			System.out.println("(FindAGame_ViewPlayerDetailsController) ERROR: UNABLE TO FIND " + admin + "'s PROFILE PICTURE. default.png IS USED INSTEAD.");
			hostDP.setFill(new ImagePattern(new Image("/application/assets/uploads/default.png")));
		}
	}
	
	void setNoOfPlayersLabel() {
		int total = HostsDA.getGameSize(this.toBeDisplayed_HostedGame.getSportsType());
		int current;
		if (this.toBeDisplayed_HostedGame.getPlayersRecruited() == null) {
			current = 0;
		} 
		else {
			current = this.toBeDisplayed_HostedGame.getPlayersRecruited().size();
		}
		noOfplayers.setText(current + " / " + total);
	}
	

	
}

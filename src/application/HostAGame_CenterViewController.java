package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import com.jfoenix.controls.JFXTimePicker;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import dataAccess.AccountsDA;
import dataAccess.FacilitiesDA;
import dataAccess.HostsDA;

import modules.Snackbar;

import com.jfoenix.controls.JFXComboBox;

import com.jfoenix.controls.JFXDatePicker;

import com.jfoenix.controls.JFXToggleButton;

public class HostAGame_CenterViewController {
    @FXML
    private BorderPane borderPane;
	@FXML
	private JFXDatePicker datePicker;
	@FXML
	private JFXTimePicker timePicker;
	@FXML
	private JFXComboBox<String> sportDropD;
	@FXML
	private JFXComboBox<String> FacilityDropD;
	@FXML
	private JFXToggleButton equipmentToggle;
	@FXML
	private JFXButton hostBtn;
	@FXML
	private JFXButton cancelBtn;
	@FXML
	private Label lbTemp;
	
	private final URL FriendsView = getClass().getResource("/application/HostAGame_FriendsView.fxml");
	private final URL AddedFriendsView = getClass().getResource("/application/HostAGame_AddedFriendsView.fxml");
	private final URL findGameViewURL = getClass().getResource("/application/FindAGame_View.fxml");
	private String sports;
	
	public void initialize() {
		
		try {
			borderPane.setLeft(FXMLLoader.load(FriendsView));
			borderPane.setRight(FXMLLoader.load(AddedFriendsView));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new Snackbar().warning(borderPane, "NOTE: Minutes will be ignored.");
		sportDropD.setItems(FindAGame_ViewController.options);
		
	}
	// Event Listener on JFXButton[#hostBtn].onAction
	@FXML
	private void handleHost(ActionEvent event) {
		
		LocalDate date = datePicker.getValue();
		LocalTime time = timePicker.getValue();
		LocalTime formattedTime = time.of(time.getHour(), 0);
		String adminNo = AccountsDA.getAdminNo();
		String name = AccountsDA.getName();
		sports = sportDropD.getValue();
		String facility = FacilityDropD.getValue();
		
		LocalDate currentD = LocalDate.now();
		LocalTime currentT = LocalTime.now();
		LocalDateTime currentDT = LocalDateTime.of(currentD, currentT);
		
		int sportIndex = -1;
		
		for (int i = 0; i < HostsDA.sports.length; i++) {
			if (sports != null && sports.equals(HostsDA.sports[i])) {
				sportIndex = i;
			}
		}
		
		System.out.println("-----HostAGame_CenterViewController-----");
		System.out.println("Selected Date: " + date);
		System.out.println("Selected Time: " + time);
		System.out.println("Formatted Time: " + formattedTime);
		System.out.println("Selected Sport's Index: " + sportIndex);
		System.out.println("-----HostAGame_CenterViewController-----");
		
		if (date != null && time != null && sportIndex != -1 && !facility.isEmpty()) {
			
			LocalDateTime requestedDT = LocalDateTime.of(date, formattedTime);
			
			if (requestedDT.isAfter(currentDT)) {
				
				System.out.println(requestedDT.isAfter(currentDT));
				
				if (FacilitiesDA.facilityIsAvailable(LocalDateTime.of(date, time), sports)) {
					
					HostsDA.hostGame(adminNo, name, date, formattedTime, sportIndex, new ArrayList<String>(), facility);
					FacilitiesDA.bookFacility(LocalDateTime.of(date, formattedTime), facility);
					new Snackbar().success(borderPane, "Success!");
					
				} else if (FacilitiesDA.facilityIsAvailable(LocalDateTime.of(date, time), sports) == false) {
					
					new Snackbar().danger(borderPane, "Facility is fully booked at this date and time!");
					
				}
				
			} else {
				new Snackbar().danger(borderPane, "Invalid date and time!");
			}
			
		} else {
			new Snackbar().danger(borderPane, "Please fill in all fields!");
		}
		
	}
	// Event Listener on JFXButton[#cancelBtn].onAction
	@FXML
	private void handleCancel(ActionEvent event) {
		borderPane.setLeft(null);
		borderPane.setRight(null);
		try {
			borderPane.setCenter(FXMLLoader.load(findGameViewURL));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @FXML
    void handleSport(MouseEvent event) {
    	FacilityDropD.setItems(null);
    }

    @FXML
    void handleFac(MouseEvent event) {
    	sports = sportDropD.getValue();
    	System.out.println("handleSport clicked");
    	System.out.println("Sport: " + sports);
    	ObservableList<String> options = FXCollections.observableArrayList();
    	
    	if (!sports.isEmpty()) {
			if (!sports.equals("Frisbee")) {
				for (String x : FacilitiesDA.facil) {
					if (x.toLowerCase().contains(sports.toLowerCase())) {
						options.add(x);
					}
				}
			} else {
				options.add("Hockey Pitch");
			}
    	}
    	if (sports.isEmpty()) {
    		options.removeAll();
    	}
    	System.out.println(options.size());
    	FacilityDropD.setItems(options);
    }
    
	@FXML
    void handleTimePicker(MouseEvent event) {
		new Snackbar().warning(borderPane, "NOTE: Minutes will be ignored.");
    }
}

package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import com.jfoenix.controls.JFXTimePicker;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import entity.AccountsEntity;

import dataAccess.AccountsDA;
import dataAccess.EquipmentsDA;
import dataAccess.FacilitiesDA;
import dataAccess.HostsDA;
import dataAccess.NotificationsDA;

import modules.Snackbar;

import com.jfoenix.controls.JFXComboBox;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

public class HostAGame_CenterViewController {

	@FXML private BorderPane borderPane;
	@FXML private JFXDatePicker datePicker;
	@FXML private JFXTimePicker timePicker;
	@FXML private JFXComboBox<String> sportDropD;
	@FXML private JFXComboBox<String> FacilityDropD;
	@FXML private JFXToggleButton equipmentToggle;
	@FXML private JFXButton hostBtn;
	@FXML private JFXButton cancelBtn;
	@FXML private Label lbTemp;
	@FXML private ScrollPane scrollPane;
	@FXML private AnchorPane FV_displayAnchor;
	@FXML private GridPane FV_infoDisplayField;
    @FXML private JFXTextField FV_searchTF;
    @FXML private Label FV_searchBtn;
	
	private final URL FriendsView = getClass().getResource("/application/HostAGame_FriendsView.fxml");
	private final URL AddedFriendsView = getClass().getResource("/application/HostAGame_AddedFriendsView.fxml");
	private final URL findGameViewURL = getClass().getResource("/application/FindAGame_View.fxml");
	private String sports;
	private String facility;
	private LocalDateTime requestedDT;
	private boolean canRentEq = false;
	static int friendIndex = 0;
	static int addedFriendIndex = 0;
	static List<AccountsEntity> friendsDisplayList;
	static ArrayList<AccountsEntity> addedFriends;
	static boolean displayResults = false;
	static ArrayList<AccountsEntity> searchResults = new ArrayList<AccountsEntity>();
	static HostAGame_AddedFriendsViewController afvc;
	static HostAGame_FriendsViewController fvc;
	
	
	public void initialize() throws IOException {
		
		friendsDisplayList = AccountsDA.getAllData();
		addedFriends = new ArrayList<AccountsEntity>();
		displayResults = false;
		searchResults.clear();
		
    	int toberemoved = -1;
    	for (int i = 0; i < friendsDisplayList.size(); i++) {
    		if (friendsDisplayList.get(i).getAdminNo().toLowerCase().equals(AccountsDA.getSession().getAdminNo().toLowerCase())) {
    			toberemoved = i;
    		}
    	}
    	
    	if (toberemoved != -1) {
    		friendsDisplayList.remove(toberemoved);
    	}
		
		try {
			borderPane.setLeft(FXMLLoader.load(FriendsView));
			borderPane.setRight(FXMLLoader.load(AddedFriendsView));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Snackbar.warning(borderPane, "NOTE: Minutes will be ignored.");
		sportDropD.setItems(FindAGame_ViewController.options);
		AccountsDA.initDA();
		
	}
	
	// Event Listener on JFXButton[#hostBtn].onAction
	@FXML
	private void handleHost(ActionEvent event) {
		
		LocalDate date = datePicker.getValue();
		LocalTime time = timePicker.getValue();
		String adminNo = AccountsDA.getAdminNo();
		String name = AccountsDA.getName();
		sports = sportDropD.getValue();
		facility = FacilityDropD.getValue();
		
		LocalDate currentD = LocalDate.now();
		LocalTime currentT = LocalTime.now();
		LocalDateTime currentDT = LocalDateTime.of(currentD, currentT);
		
		int sportIndex = -1;
		
		for (int i = 0; i < HostsDA.sports.length; i++) {
			if (sports != null && sports.equals(HostsDA.sports[i])) {
				sportIndex = i;
			}
		}
		
		if (date != null && time != null && sportIndex != -1 && facility!=null) {
			
			LocalTime formattedTime = LocalTime.of(time.getHour(), 0);
			System.out.println("-----HostAGame_CenterViewController-----");
			System.out.println("Selected Date: " + date);
			System.out.println("Selected Time: " + time);
			System.out.println("Formatted Time: " + formattedTime);
			System.out.println("Selected Sport's Index: " + sportIndex);
			System.out.println("-----HostAGame_CenterViewController-----");
			
			requestedDT = LocalDateTime.of(date, formattedTime);
			
			if (requestedDT.isAfter(currentDT)) {
				
				System.out.println(requestedDT.isAfter(currentDT));
				
				if (FacilitiesDA.facilityIsAvailable(requestedDT, facility)) {
					
					int status = HostsDA.hostGame(adminNo, name, date, formattedTime, sportIndex, new ArrayList<String>(), facility);
					
					if (status == 2) {
						
						FacilitiesDA.bookFacility(requestedDT, facility);
						
						if (canRentEq) {
							EquipmentsDA.rentEquipment(adminNo, sports);
						}
						
						if (!addedFriends.isEmpty()) {
							sendInvitations(AccountsDA.getAdminNo());
						}

						Snackbar.success(borderPane, "Success!");
						
						try {
							borderPane.setCenter(FXMLLoader.load(findGameViewURL));
							borderPane.setLeft(null);
							borderPane.setRight(null);
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						
						Snackbar.danger(borderPane, "Booking is unsuccessful! Please check that date and time are valid.");
						
					}
					
				} else if (FacilitiesDA.facilityIsAvailable(LocalDateTime.of(date, time), sports) == false) {
					
					Snackbar.danger(borderPane, "Facility is fully booked at this date and time!");
					
				}
				
			} else {
				Snackbar.danger(borderPane, "Past dates cannot be selected! Please select future/present date and time.");
			}
			
		} else {
			Snackbar.danger(borderPane, "Please fill in all fields!");
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
    private void handleSport(MouseEvent event) {
    	FacilityDropD.setItems(null);
    	equipmentToggle.setSelected(false);
    	canRentEq = false;
    }

    @FXML
    private void handleFac(MouseEvent event) {
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
    private void handleTimePicker(MouseEvent event) {
		Snackbar.warning(borderPane, "NOTE: Minutes will be ignored.");
    }
	
    @FXML
    private void handleEquipmentToggle(ActionEvent event) {
    	try { 
			if (equipmentToggle.isSelected() && !sportDropD.getValue().isEmpty()) {
				if (EquipmentsDA.equipmentIsAvailable(sportDropD.getValue())) {
					canRentEq = true;
					Snackbar.success(borderPane, "Equipment is available!");
				}
				else {
					equipmentToggle.setSelected(false);
					Snackbar.danger(borderPane, "Equipment is not available!");
				}
			}
			else if (equipmentToggle.isSelected() && sportDropD.getValue().isEmpty()) {
				Snackbar.danger(borderPane, "Please select a sport!");
				equipmentToggle.setSelected(false);
			}
    	} catch (Exception e) {
    		Snackbar.danger(borderPane, "Please select a sport!");
    		equipmentToggle.setSelected(false);
    	}
    }
    
    private void sendInvitations(String hostAd) {
    	if (!addedFriends.isEmpty()) {
    		for (AccountsEntity x : addedFriends) {
    			NotificationsDA.addNotifications(x.getAdminNo().toLowerCase(), sports, facility, requestedDT, 0);
    			HostsDA.addFriends(hostAd, requestedDT.toLocalDate(), requestedDT.toLocalTime(), x.getAdminNo().toLowerCase());
    			System.out.println("Sent a notification to " + x.getName());
    		}
    	}
    }

}

package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import com.jfoenix.controls.JFXTimePicker;

import java.net.URL;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

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
	private JFXComboBox sportDropD;
	@FXML
	private JFXComboBox FacilityDropD;
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
	
	public void initialize() {
		try {
			borderPane.setLeft(FXMLLoader.load(FriendsView));
			borderPane.setRight(FXMLLoader.load(AddedFriendsView));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Event Listener on JFXButton[#hostBtn].onAction
	@FXML
	public void handleHost(ActionEvent event) {
		
	}
	// Event Listener on JFXButton[#cancelBtn].onAction
	@FXML
	public void handleCancel(ActionEvent event) {
		borderPane.setLeft(null);
		borderPane.setRight(null);
		try {
			borderPane.setCenter(FXMLLoader.load(findGameViewURL));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

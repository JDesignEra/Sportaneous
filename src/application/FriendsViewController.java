package application;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import dataAccess.FriendsDA;

public class FriendsViewController implements Initializable {
	@FXML JFXTextField nameAdminNo, emailTxtField;
	@FXML JFXToggleButton srcTypeToggleBtn;
	@FXML GridPane rootGridPane, friendsGridPane, emptyFriendsGridPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FriendsDA.initDA();
	}
	
	// Event Listener on JFXToggleButton[#srcTypeToggleBtn].onAction
	public void srcToggleBtnOnAction(ActionEvent event) {
		// TODO
	}
	
	// Event Listener on JFXButton[#srcBtn].onAction
	public void srcBtnOnAction(ActionEvent event) {
		// TODO
	}
}

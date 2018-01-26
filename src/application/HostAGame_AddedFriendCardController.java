package application;

import javafx.fxml.FXML;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.shape.Circle;

public class HostAGame_AddedFriendCardController {
	@FXML
	private JFXButton addBtn;
	@FXML
	private Label lbMatchesPlayed;
	@FXML
	private Label lbPlayerRating;
	@FXML
	private Label lbStats;
	@FXML
	private Label lbName;
	@FXML
	private Circle playerDP;

	// Event Listener on JFXButton[#addBtn].onAction
	
	@FXML
	private void handleRemoveBtn(ActionEvent event) {
		
	}
}

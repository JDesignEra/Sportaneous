package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import dataAccess.HostsDA;

public class RatingsProfileController {
	@FXML
	private Label eventDay;
	@FXML
	private Label eventDate;
	@FXML
	private Label eventTime;
	@FXML
	private Label sportsType;
	@FXML
	private JFXButton RatePlaterBtn;
	@FXML
	private Label noOfplayers;
	@FXML
	private Circle hostDP;
	@FXML
	private Label hostName;
	@FXML 
	private ScrollPane scrollPane;
    @FXML 
    private AnchorPane displayAnchor;
	@FXML
	private GridPane infoDisplayField;
	@FXML 
	private int i = -1;

	// Event Listener on JFXButton[#RatePlaterBtn].onAction
	@FXML
	
	public void initialize() throws IOException {
		
		HostsDA.initializeSearchResults();
		for (int a = 0; a < HostsDA.returnHostsList().size(); a++) {
			infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_space.fxml")), 1, ++i);
			infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/RatingsProfile.fxml")), 1, ++i);
		}

		displayAnchor.setMinHeight(HostsDA.returnHostsList().size() * 228);

	}
	public void handleRatePlayer(ActionEvent event) {
	     
	}
}

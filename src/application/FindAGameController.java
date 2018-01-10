package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import dataAccess.HostsDA;

public class FindAGameController {

	@FXML private GridPane topPanel;

	@FXML private JFXButton hostBtn;

	@FXML private JFXButton searchBtn;

	@FXML private JFXTextField nameTF;

	@FXML private JFXTextField dateTF;

	@FXML private JFXTextField timeTF;

	@FXML private JFXComboBox<String> sportMenu;

	@FXML private ScrollPane scrollPane;

	@FXML private AnchorPane displayAnchor;

	@FXML private GridPane infoDisplayField;

	@FXML private static String error = "";

	@FXML private int i = 0;
	
	public static int HostAGame_index = 0;
	public static int VPD_index = 0;

	static String whosegameisclicked;
	@FXML
	public void initialize() throws IOException {
		
		whosegameisclicked = Main.currentUserAdminNo;
		HostsDA.initializeSearchResults();
		displayAnchor.setMaxHeight(1043);
		ObservableList<String> options = FXCollections.observableArrayList("", "Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis");
		sportMenu.setEditable(true);
		sportMenu.getEditor().setEditable(false);

		sportMenu.setItems(options);
		sportMenu.setPromptText("Select a sport");
		scrollPane.setFitToWidth(true);

		for (int a = 0; a < HostsDA.returnHostsList().size(); a++) {
			infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_hostedGame.fxml")), 1, i++);
		}

		displayAnchor.setMinHeight(HostsDA.returnHostsList().size() * 228);

	}

	@FXML
	public void handleHostAGame(ActionEvent event) throws IOException {
		
	}

	@FXML
	public void handleSearch(ActionEvent event) throws IOException {

		HostsDA.getSearchResults().clear();

		String adminORname = nameTF.getText();
		String date = dateTF.getText();
		String time = timeTF.getText();
		String sportsType = sportMenu.getValue();

		if (nameTF.getText().isEmpty()) {
			adminORname = "";
		}

		if (dateTF.getText().isEmpty()) {
			date = "";
		}

		if (timeTF.getText().isEmpty()) {
			time = "";
		}

		if (sportMenu.getValue() == null) {
			sportsType = "";
		}

		HostsDA.searchGame(adminORname, date, time, sportsType);

		if (HostsDA.getSearchResults().size() > 0) {
			infoDisplayField.getChildren().clear();
			FindAGameApp.index = 0;
		}

		i = -1;

		for (int a = 0; a < HostsDA.getSearchResults().size(); a++) {
			infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_space.fxml")), 1, ++i);
			infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_hostedGame.fxml")), 1, ++i);
		}

		if (HostsDA.getSearchResults().size() > 0) {
			displayAnchor.setMinHeight(HostsDA.getSearchResults().size() * 228);
			displayAnchor.setMaxHeight(HostsDA.getSearchResults().size() * 228);
		}
		else {
			displayAnchor.setMinHeight(1043);
			displayAnchor.setMaxHeight(1043);
		}

	}

	@FXML
	public void handleSportMenu(ActionEvent event) throws IOException {

	}

}

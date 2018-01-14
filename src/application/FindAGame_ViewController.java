package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import entity.HostsEntity;

import dataAccess.AccountsDA;
import dataAccess.HostsDA;

public class FindAGame_ViewController {

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXTextField nameTF;

    @FXML
    private JFXComboBox<String> sportCombo;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton hostBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane displayAnchor;
    
    @FXML
    private GridPane infoDisplayField;

    @FXML
    private AnchorPane whoopsPane;

	private int i = 0;
	private ObservableList<String> options = FXCollections.observableArrayList("", "Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis");
	public static int HostAGame_index = 0;
	public static int VPD_index = 0;
	static String whosegameisclicked;
	static HostsEntity clickedgame;
	
	@FXML
	public void initialize() throws IOException {
		
		Main.currentUserAdminNo = AccountsDA.getAdminNo();
		System.out.println("(FindAGameController) USER THAT IS USING THE APP NOW: " + Main.currentUserAdminNo);
		
		HostsDA.initializeSearchResults();
		sportCombo.setEditable(true);
		sportCombo.getEditor().setEditable(false);
		sportCombo.setItems(options);
		sportCombo.setPromptText("Select a sport");
		scrollPane.setFitToWidth(true);
		
		if (0 < HostsDA.returnHostsList().size() && HostsDA.returnHostsList().size() <= 3) {
			scrollPane.setFitToHeight(true);
		}

		for (int a = 0; a < HostsDA.returnHostsList().size(); a++) {
			System.out.println("GridPane infoDisplayField (1, " + i + ")");
			infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_hostedGame.fxml")), 1, i++);
		}

		displayAnchor.setMinHeight(HostsDA.returnHostsList().size() * 228);
	}
	
    @FXML
    void handleHostBtn(ActionEvent event) {

    }

    @FXML
    void handleSearch(ActionEvent event) throws IOException {
    	System.out.println(datePicker.getValue());
    	
    	HostsDA.getSearchResults().clear();

		String adminORname = nameTF.getText();
		LocalDate date = datePicker.getValue();
		LocalTime time = timePicker.getValue();
		String sportsType = sportCombo.getValue();

		if (nameTF.getText().isEmpty()) {
			adminORname = "";
		}

		if (date == null) {
			date = null;
		}

		if (time == null) {
			time = null;
		}

		if (sportCombo.getValue() == null) {
			sportsType = "";
		}

		HostsDA.searchGame(adminORname, date, time, sportsType);

		if (HostsDA.getSearchResults().size() > 0) {
			infoDisplayField.getChildren().clear();
		}
		
		if (HostsDA.getSearchResults().size() > 0 && HostsDA.getSearchResults().size() <= 3) {
			scrollPane.setFitToHeight(true);
		}

		i = 0;

		for (int a = 0; a < HostsDA.getSearchResults().size(); a++) {
			infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_hostedGame.fxml")), 1, i++);
		}

		if (HostsDA.getSearchResults().size() > 0) {
			displayAnchor.setMinHeight(HostsDA.getSearchResults().size() * 228);
			displayAnchor.setMaxHeight(HostsDA.getSearchResults().size() * 228);
		} else {
//			displayAnchor.setMinHeight(1043);
//			displayAnchor.setMaxHeight(1043);
		}
		
		timePicker.setValue(null);
		datePicker.setValue(null);
		nameTF.clear();
		sportCombo.setValue(null);
		
    }
    
}


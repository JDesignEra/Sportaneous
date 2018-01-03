package application;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import dataAccess.HostsDA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FindAGameController {

    @FXML
    private GridPane topPanel;

    @FXML
    private JFXButton hostBtn;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXTextField nameTF;

    @FXML
    private JFXTextField dateTF;

    @FXML
    private JFXTextField timeTF;

    @FXML
    private JFXComboBox<String> sportMenu;
    
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane displayAnchor;

    @FXML
    private GridPane infoDisplayField;
    
    private FindAGameApp app;
    
    private static String error = "";
    
    private int i = -1;
    
    public void initialize() throws IOException {
    	
    	HostsDA.initializeSearchResults();
    	displayAnchor.setMaxHeight(1043);
    	ObservableList<String> options = FXCollections.observableArrayList("", "Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis");
    	sportMenu.setEditable(true);
    	sportMenu.getEditor().setEditable(false);
    		    
    	sportMenu.setItems(options);
    	sportMenu.setPromptText("Select a sport");
    	scrollPane.setFitToWidth(true);
    	
//    	infoDisplayField.setGridLinesVisible(true);
    	
    	for (int a = 0; a < HostsDA.returnHostsList().size(); a++) {
    		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_space.fxml")),  1, ++i);
    		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_hostedGame.fxml")),  1, ++i);
    	}
    	
    	displayAnchor.setMinHeight(HostsDA.returnHostsList().size()*228);
    	
    }
    
    @FXML
    public void handleHostAGame(ActionEvent event) throws IOException {
    	error = "I am tired!";
    	showError();
    }

    @FXML
    public void handleSearch(ActionEvent event) throws IOException {
    	
    	HostsDA.getSearchResults().clear();
    	
    	String adminORname = nameTF.getText();
    	String date = dateTF.getText();
    	String time = timeTF.getText();
    	String sportsType = sportMenu.getValue();
    	System.out.println(sportsType);
    	
//    	System.out.println(nameTF.getText().isEmpty());
//    	System.out.println(dateTF.getText().isEmpty());
//    	System.out.println(timeTF.getText().isEmpty());
//    	System.out.println(sportMenu.getValue()==null);
    	
    	HostsDA.searchGame(adminORname, date, time, sportsType);
    	
    	if (HostsDA.getSearchResults().size() > 0) {
    		infoDisplayField.getChildren().clear();
    		FindAGameApp.index = 0;
    	}
    	
    	i = -1;
    	
    	for (int a = 0; a < HostsDA.getSearchResults().size(); a++) {
    		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_space.fxml")),  1, ++i);
    		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_hostedGame.fxml")),  1, ++i);
    	}
    	
    	if (HostsDA.getSearchResults().size() == 0) {
    		displayAnchor.setMinHeight(HostsDA.getSearchResults().size()*228);
    	}
    	
    }

    @FXML
    public void handleSportMenu(ActionEvent event) throws IOException {
    
    }
    
    public void showError() throws IOException {
    	final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        AnchorPane root = FXMLLoader.load(getClass().getResource("/application/FindAGame_ErrorPopUp.fxml"));
        Scene dialogScene = new Scene(root, 400, 150);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
    public static String getErrorMsg() {
    	return error;
    }
    public void setMainApp(FindAGameApp app) {
    	this.app = app;
    }
    
    
    

}

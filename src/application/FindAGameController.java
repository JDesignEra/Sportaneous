package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import dataAccess.HostsDA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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

    private int i = -1;
    
    public void initialize() throws IOException {
    	
    	displayAnchor.setMaxHeight(1043);
    	sportMenu.setEditable(true);
    	ObservableList<String> options = FXCollections.observableArrayList("Badminton", "Basketball", "Frisbee", "Soccer", "Squash", "Tennis");
    		    
    	sportMenu.setItems(options);
    	sportMenu.setPromptText("Select a sport");
    	scrollPane.setFitToWidth(true);
    	
    	infoDisplayField.setGridLinesVisible(true);
    	
    	for (int a = 0; a < HostsDA.returnHostsList().size(); a++) {
    		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_space.fxml")),  1, ++i);
    		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_hostedGame.fxml")),  1, ++i);
    	}
    	
    	displayAnchor.setMinHeight(HostsDA.returnHostsList().size()*228);
    	System.out.println(displayAnchor.getHeight());

    }
    
    @FXML
    public void handleHostAGame(ActionEvent event) {

    }

    @FXML
    public void handleSearch(ActionEvent event) throws IOException {
    	
//    	String nameORadmin = this.nameTF.getText();
//    	String sport = this.sportMenu.getValue();
//    	String date = this.dateTF.getText();
//    	String time = this.timeTF.getText();
    	
    	
//    	nameTF.setText("Hello!");
//    	AnchorPane ap = new AnchorPane();
    	
//		
//    	infoDisplayField.setGridLinesVisible(true);
//    	
//    	infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/Ratings_element2.fxml")),  1, ++i);
//    	infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/Ratings_element.fxml")),  1, ++i);
//    	
//    	System.out.println(infoDisplayField.getHeight());
//    	
//    	displayAnchor.setMinHeight(infoDisplayField.getHeight());
//    	displayAnchor.setPrefHeight(infoDisplayField.getHeight());
//    	displayAnchor.setMaxHeight(infoDisplayField.getHeight());
//    	
//    	System.out.println(FindAGameApp.index);
    }

    @FXML
    public void handleSportMenu(ActionEvent event) {
    	
    }
    
    public void setMainApp(FindAGameApp app) {
    	this.app = app;
    }
    
    

}

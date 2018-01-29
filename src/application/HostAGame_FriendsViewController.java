package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import entity.AccountsEntity;

import dataAccess.AccountsDA;

public class HostAGame_FriendsViewController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane displayAnchor;

    @FXML
    private GridPane infoDisplayField;

    @FXML
    private JFXTextField searchTF;

    @FXML
    private Label searchBtn;
    
    private final URL friendCardURL = getClass().getResource("/application/HostAGame_FriendCard.fxml");
    
    public void initialize() throws IOException {
    	infoDisplayField.getChildren().clear();
    	
    	HostAGame_CenterViewController.fvc = this;
    	AccountsDA.initDA();
    	
    	if (HostAGame_CenterViewController.displayResults) {
    		displayAnchor.setPrefHeight((infoDisplayField.getPrefHeight() + infoDisplayField.getVgap())*HostAGame_CenterViewController.searchResults.size());
    		for (int i = 0; i < HostAGame_CenterViewController.searchResults.size(); i++) {
				infoDisplayField.add(FXMLLoader.load(friendCardURL), 0, i);
			}
    	} else {
    		displayAnchor.setPrefHeight((infoDisplayField.getPrefHeight() + infoDisplayField.getVgap())*HostAGame_CenterViewController.friendsDisplayList.size());
			for (int i = 0; i < HostAGame_CenterViewController.friendsDisplayList.size(); i++) {
				infoDisplayField.add(FXMLLoader.load(friendCardURL), 0, i);
			}
    	}

    }

    @FXML
    private void handleSearch(MouseEvent event) throws IOException {
    	
    	HostAGame_CenterViewController.searchResults.clear();
    	String searched = searchTF.getText();
    	searchTF.clear();
    	List<AccountsEntity> todisplay = HostAGame_CenterViewController.friendsDisplayList;
    	
    	for (AccountsEntity x : todisplay) {
    		if (x.getAdminNo().toLowerCase().equals(searched.toLowerCase()) || x.getName().toLowerCase().contains(searched.toLowerCase())) {
    			HostAGame_CenterViewController.searchResults.add(x);
    		}
    	}
    	
    	if (!HostAGame_CenterViewController.searchResults.isEmpty()) {
    		HostAGame_CenterViewController.displayResults = true;
    	} 
    	
    	initialize();
    }
    
    @FXML
    private void handleSearchTF(ActionEvent event) {
    	searchTF.clear();
    }
    
}

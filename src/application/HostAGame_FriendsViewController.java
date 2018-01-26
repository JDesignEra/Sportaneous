package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXTextField;
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
    	AccountsDA.initDA();
    	HostAGame_CenterViewController.friendsDisplayList = AccountsDA.getAllData();
    	
    	System.out.println(HostAGame_CenterViewController.friendsDisplayList.size());
    	displayAnchor.setMaxHeight((infoDisplayField.getPrefHeight() + infoDisplayField.getVgap())*AccountsDA.getAllData().size() + infoDisplayField.getVgap());
    	System.out.println(displayAnchor.getMaxHeight());
    	
    	for (int i = 0; i < HostAGame_CenterViewController.friendsDisplayList.size(); i++) {
    		infoDisplayField.add(FXMLLoader.load(friendCardURL), 0, i);
    	}
    	
    }

    @FXML
    private void handleSearch(MouseEvent event) throws IOException {
    	String searched = searchTF.getText();
    	List<AccountsEntity> list = AccountsDA.getAllData();
    	ArrayList<AccountsEntity> results = new ArrayList<AccountsEntity>();
    	
    	if (!searched.isEmpty()) {
    		for (AccountsEntity x : list) {
    			if (x.getAdminNo().toLowerCase().equals(searched.toLowerCase()) || x.getName().toLowerCase().contains(searched.toLowerCase())) {
    				results.add(x);
    			}
    		}
    	}
    	
		if (!results.isEmpty()) {
			infoDisplayField.getChildren().clear();
			HostAGame_CenterViewController.friendsDisplayList = results;
			for (int i = 0; i < results.size(); i++) {
				infoDisplayField.add(FXMLLoader.load(friendCardURL), 0, i);
			}
		} else {
			initialize();
		}
    	
    }
    
    

}

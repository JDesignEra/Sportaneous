package application;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import dataAccess.AccountsDA;

public class HostAGame_AddedFriendsViewController {

    @FXML private ScrollPane scrollPane;
    @FXML private AnchorPane displayAnchor;
    @FXML private GridPane infoDisplayField;
    private final URL addedFriendCardURL = new Object().getClass().getResource("/application/HostAGame_AddedFriendCard.fxml");

    public void initialize() throws IOException {
    	infoDisplayField.getChildren().clear();
    	HostAGame_CenterViewController.afvc = this;
    	
    	int size = HostAGame_CenterViewController.addedFriends.size();
    	displayAnchor.setPrefHeight((infoDisplayField.getPrefHeight() + infoDisplayField.getVgap())*size);
    	
    	for (int i = 0; i < HostAGame_CenterViewController.addedFriends.size(); i++) {
    		infoDisplayField.add(FXMLLoader.load(addedFriendCardURL), 0, i);
    	}
    }
}

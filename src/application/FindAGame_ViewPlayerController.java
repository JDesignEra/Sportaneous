package application;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;

import entity.HostsEntity;

import dataAccess.HostsDA;

public class FindAGame_ViewPlayerController {
	
	@FXML private AnchorPane anchorDisplay;
	
    @FXML private ScrollPane scrollPane;

    @FXML private JFXButton closeBtn;
    
    @FXML private GridPane infoDisplayField;
    
    private int a = 0;
    
    private HostsEntity clickedGame; 
    
    public void initialize() throws IOException {
    	
    	clickedGame = FindAGame_ViewController.clickedgame;
    	scrollPane.setFitToWidth(true);
    	
    	try {
    		ArrayList<String> listOfRecruitedPlayers = HostsDA.getFriends(clickedGame.getAdminNo(), clickedGame.getDate(), clickedGame.getTime());
    		System.out.println("Error check: " + listOfRecruitedPlayers);
    		if (listOfRecruitedPlayers.size() > 3) {
    			anchorDisplay.setMinHeight(20+(250+20)*listOfRecruitedPlayers.size());
    			infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_ViewPlayerDetails.fxml")), 0, a++);
    		} else {
    			scrollPane.setFitToHeight(true);	
    		}
    		for (int i = 0; i < listOfRecruitedPlayers.size(); i++) {
    			infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_ViewPlayerDetails.fxml")), 0, a++);
    		}
    		
    	} catch (Exception e) {
    		System.out.println("Error! Please check ViewPlayerDetails. Either that or listOfRecruitedPlayers is not found.");
    	}
    	
    }
    
    @FXML void handleCloseBtn(ActionEvent event) {
    	Main.getRoot().setRight(null);
    }

}

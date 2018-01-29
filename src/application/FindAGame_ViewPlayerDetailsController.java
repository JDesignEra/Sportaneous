package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import entity.AccountsEntity;
import entity.HostsEntity;

import dataAccess.AccountsDA;
import dataAccess.HostsDA;

import modules.Utils;


public class FindAGame_ViewPlayerDetailsController {
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
	@FXML
	private GridPane basePane;
	
	private String target;
	
	private HostsEntity gameClicked;
	
	private ArrayList<String> peopleToDisplay;
	
	private String adminNo = "";
	
	private Utils m;
	
	private AccountsEntity retrievedAcc = null;
	
	public void initialize() {
		
		AccountsDA.initDA();
		HostsDA.initDA();
		
		m = new Utils();
		gameClicked = FindAGame_ViewController.clickedgame;
		target = gameClicked.getName() + " (" + gameClicked.getAdminNo() + ") ";
		peopleToDisplay = gameClicked.getPlayersRecruited();
		
		
		System.out.println("(FindAGame_ViewPlayerDetailsController) Whose game has been clicked: " + target + " / Players recruited: " + peopleToDisplay.size());
		//display details of the people who joined the game the target hosted
		if (FindAGame_ViewController.VPD_index < this.peopleToDisplay.size()) {
			adminNo = this.peopleToDisplay.get(FindAGame_ViewController.VPD_index++);
			System.out.println("(FindAGame_ViewPlayerDetailsController) Recruited player being displayed now: " + adminNo);
			retrievedAcc = AccountsDA.getAccData(adminNo);
		}
		
		setName();
		setDP();
		setHeightWeight();
		setRating();
		setMatchesPlayed();
		
		if (FindAGame_ViewController.VPD_index == this.peopleToDisplay.size()) {
			FindAGame_ViewController.VPD_index = 0;
		}
		
		System.out.println("(ViewPlayerDetails) " + gameClicked.getName() + " (" + gameClicked.getAdminNo() + ")'s recruited player ("+ "VPD index: " + FindAGame_ViewController.VPD_index + ") is being displayed now.");
		
	}
	
	private void setName() {
		lbName.setText(retrievedAcc.getName());
	}
	
	private void setDP() {
		
		try {
			ImagePattern ip = new ImagePattern(m.cropCirclePhoto(adminNo, playerDP.getRadius()).getImage());
			playerDP.setFill(ip);
		}
		catch (Exception e) {
			System.out.println("(FindAGame_ViewPlayerDetailsController) ERROR: UNABLE TO FIND " + adminNo + "'s PROFILE PICTURE. default.png IS USED INSTEAD.");
			playerDP.setFill(new ImagePattern(new Image("/application/assets/uploads/default.png")));
		}
		
	}

	private void setHeightWeight() {
		double height = 0;
		double weight = 0;
		lbStats.setText("");

		try {
			if (retrievedAcc.getHeightVisibility()) {
				height = retrievedAcc.getHeight();
			}

			if (retrievedAcc.getWeightVisibility()) {
				weight = retrievedAcc.getWeight();
			}
		} catch (Exception e) {
			System.out.println("(FindAGame_ViewPlayerDetailsController) ERROR: UNABLE TO READ HEIGHT & WEIGHT");
		}
		
		if (height == 0.0 && weight != 0.0) {
			lbStats.setText(weight + " kg");
		} 
		
		if (height != 0.0 && weight == 0.0) {
			lbStats.setText(height + " m");
		}
		
		if (height != 0.0 && weight != 0.0) {
			lbStats.setText(weight + " kg" + " | " + height + " m");
		}
		
		if (height == 0.0 && weight == 0.0) {
			String line = "";
			try {
				for (int i = 0; i < retrievedAcc.getName().length(); i++) {
					line += "-";
				}
				lbStats.setText(line);
			} catch (Exception e) {
				System.out.println("(FindAGame_ViewPlayerDetailsController): " + adminNo + "'s name not found");
			}
			
		}
	}
	
	public void setRating() {
		lbPlayerRating.setText(m.getRatingShapes(retrievedAcc.getCalRating()));
	}
	
	public void setMatchesPlayed() {
		int total = retrievedAcc.getTotalMatch();
		int played = retrievedAcc.getMatchPlayed();
		
		lbMatchesPlayed.setText(played + " / " + total);
	}
}

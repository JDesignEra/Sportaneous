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

import entity.HostsEntity;

import dataAccess.AccountsDA;
import dataAccess.HostsDA;

import modules.Misc;

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
	
	private Misc m;
	
	public void initialize() {
		
		AccountsDA.initDA();
		HostsDA.initDA();
		
		m = new Misc();
		gameClicked = FindAGame_ViewController.clickedgame;
		target = gameClicked.getName() + " (" + gameClicked.getAdminNo() + ") ";
		peopleToDisplay = gameClicked.getPlayersRecruited();
		
		System.out.println("(FindAGame_ViewPlayerDetailsController) Whose game has been clicked: " + target + " / Players recruited: " + peopleToDisplay.size());
		//display details of the people who joined the game the target hosted
		if (FindAGame_ViewController.VPD_index < this.peopleToDisplay.size()) {
			adminNo = this.peopleToDisplay.get(FindAGame_ViewController.VPD_index++);
			System.out.println("(FindAGame_ViewPlayerDetailsController) Recruited player being displayed now: " + adminNo);
		}
		
		setName();
		setDP();
		setHeightWeight();
		setRating();
		setMatchesPlayed();
		
		if (FindAGame_ViewController.VPD_index == this.peopleToDisplay.size()) {
			FindAGame_ViewController.VPD_index = 0;
		}
		
		System.out.println("VPD index: " + FindAGame_ViewController.VPD_index);
		
	}
	
	private void setName() {
		Object name = AccountsDA.getAccData(adminNo.toLowerCase())[3];
		lbName.setText(name.toString());
	}
	
	private void setDP() {
		
		try {
			ImagePattern ip = new ImagePattern(new Misc().cropCirclePhoto(adminNo, playerDP.getRadius()).getImage());
			playerDP.setFill(ip);
		}
		catch (Exception e2) {
			System.out.println("(FindAGame_ViewPlayerDetailsController) ERROR: UNABLE TO FIND " + adminNo + "'s PROFILE PICTURE. default.png IS USED INSTEAD.");
			playerDP.setFill(new ImagePattern(new Image("/application/assets/uploads/default.png")));
		}
		
	}

	private void setHeightWeight() {
		String height = "0";
		String weight = "0";
		lbStats.setText("");

		try {
			if ((boolean) AccountsDA.getAccData(adminNo.toLowerCase())[9]) {
				height = AccountsDA.getAccData(adminNo.toLowerCase())[7].toString();
			}

			if ((boolean) AccountsDA.getAccData(adminNo.toLowerCase())[10]) {
				weight = AccountsDA.getAccData(adminNo.toLowerCase())[8].toString();
			}
		} catch (Exception e) {
			System.out.println("(FindAGame_ViewPlayerDetailsController) ERROR: UNABLE TO READ HEIGHT & WEIGHT");
		}
		
		if (Double.parseDouble(height) == 0.0 && Double.parseDouble(weight) != 0.0) {
			lbStats.setText(weight + " kg");
		} 
		
		if (Double.parseDouble(height) != 0.0 && Double.parseDouble(weight) == 0.0) {
			lbStats.setText(height + " m");
		}
		
		if (Double.parseDouble(height) != 0.0 && Double.parseDouble(weight) != 0.0) {
			lbStats.setText(weight + " kg" + " | " + height + " m");
		}
		
		if (Double.parseDouble(height) == 0.0 && Double.parseDouble(weight) == 0.0) {
			String line = "";
			try {
				for (int i = 0; i < AccountsDA.getAccData(adminNo.toLowerCase())[3].toString().length(); i++) {
					line += "-";
				}
				lbStats.setText(line);
			} catch (Exception e) {
				System.out.println("(FindAGame_ViewPlayerDetailsController): " + adminNo + "'s name not found");
			}
			
		}
	}
	
	public void setRating() {
		String rating = AccountsDA.getAccData(adminNo.toLowerCase())[11].toString();
		lbPlayerRating.setText(m.getRatingShapes(Double.valueOf(rating).doubleValue()));
	}
	
	public void setMatchesPlayed() {
		String total = AccountsDA.getAccData(adminNo.toLowerCase())[14].toString();
		String played = AccountsDA.getAccData(adminNo.toLowerCase())[13].toString();
		
		lbMatchesPlayed.setText(played + " / " + total);
	}
}

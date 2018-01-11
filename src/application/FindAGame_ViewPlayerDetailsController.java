package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import dataAccess.AccountsDA;
import dataAccess.HostsDA;

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
	
	private String target;
	
	private ArrayList<String> peopleToDisplay;
	
	private String adminNo = "";
	
	public void initialize() {
		
		AccountsDA.initDA();
		HostsDA.initDA();
		target = FindAGameController.whosegameisclicked;
		peopleToDisplay = HostsDA.getHostDB().get(target).getPlayersRecruited();
		
		System.out.println("(FindAGame_ViewPlayerDetailsController) Whose game has been clicked: " + target + " / Players recruited: " + peopleToDisplay.size());
		//display details of the people who joined the game the target hosted
		if (FindAGameController.VPD_index < this.peopleToDisplay.size()) {
			adminNo = this.peopleToDisplay.get(FindAGameController.VPD_index++);
			System.out.println("(FindAGame_ViewPlayerDetailsController) Recruited player being displayed now: " + adminNo);
		}
		
		setName();
		setDP();
		setHeightWeight();
		
		if (FindAGameController.VPD_index == this.peopleToDisplay.size()) {
			FindAGameController.VPD_index = 0;
		} 
	}
	
	private void setName() {
		Object name = AccountsDA.getAccData(adminNo.toLowerCase())[3];
		lbName.setText(name.toString());
	}
	
	private void setDP() {
		Image img;
		try {
			img = new Image("/application/assets/uploads/" + adminNo.toLowerCase() + ".png");
		} catch (Exception e) {
			System.out.println("(FindAGame_ViewPlayerDetailsController) ERROR: UNABLE TO FIND " + adminNo + "'s PROFILE PICTURE. default.png IS USED INSTEAD.");
			img = new Image("/application/assets/uploads/default.png");
		}
		ImagePattern ip = new ImagePattern(img);
		playerDP.setFill(ip);
	}
	
	private void setRating() {
		
		lbPlayerRating.setText("");
	}
	
	private void setHeightWeight() {
		String height = "";
		String weight = "";
		lbStats.setText("");

		try {
			if ((boolean) AccountsDA.getAccData(adminNo.toLowerCase())[9]) {
				height = AccountsDA.getAccData(adminNo.toLowerCase())[7].toString() + "m";
			}

			if ((boolean) AccountsDA.getAccData(adminNo.toLowerCase())[10]) {
				weight = AccountsDA.getAccData(adminNo.toLowerCase())[8].toString() + "kg";
			}
		
		} catch (Exception e) {
			System.out.println("(FindAGame_ViewPlayerDetailsController) ERROR: UNABLE TO READ HEIGHT & WEIGHT");
		}
		
		lbStats.setText(height + " | " + weight);
	}
}

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
		
		System.out.println("target: " + target + " /Players recruited: " + peopleToDisplay.size());
		//display details of the people who joined the game the target hosted
		if (FindAGameController.VPD_index < this.peopleToDisplay.size()) {
			adminNo = this.peopleToDisplay.get(FindAGameController.VPD_index);
			FindAGameController.VPD_index++;
			System.out.println(adminNo.toLowerCase());
		}
		
		setName();
		setDP();
	}
	
	void setName() {
		Object name = AccountsDA.getAccData(adminNo.toLowerCase())[3];
		lbName.setText(name.toString());
	}
	
	void setDP() {
		System.out.println(adminNo.toLowerCase());
		Image img;
		try {
			img = new Image("/application/assets/uploads/" + adminNo.toLowerCase() + ".png");
		} catch (Exception e) {
			System.out.println(adminNo + "'s profile picture not found; default.png is used instead-");
			img = new Image("/application/assets/uploads/default.png");
		}
		ImagePattern ip = new ImagePattern(img);
		playerDP.setFill(ip);
	}
	
	void setRating(int no) {
		String stars = "";
		if (no <= 5) {
			for (int i = 0; i < no; i++) {
				stars += "";
			}
		}
		lbPlayerRating.setText("");
	}
}

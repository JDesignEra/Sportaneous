package application;

import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import entity.AccountsEntity;

import modules.Utils;

public class HostAGame_AddedFriendCardController {
	@FXML
	private JFXButton addBtn;
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

	// Event Listener on JFXButton[#addBtn].onAction
	private List<AccountsEntity> retrievedList;
	private AccountsEntity retrievedAcc;
	
	private String adminNo = "";
	
	private Utils misc;

	// Event Listener on JFXButton[#addBtn].onAction
	public void initialize() {
		misc = new Utils();
		
		retrievedList = HostAGame_CenterViewController.addedFriends;
		
		if (!retrievedList.isEmpty()) {
			retrievedAcc = retrievedList.get(HostAGame_CenterViewController.addedFriendIndex);
			adminNo = retrievedAcc.getAdminNo().toLowerCase();

			setName();
			setDP();
			setHeightWeight();
			setRating();
			setMatchesPlayed();

			if (HostAGame_CenterViewController.addedFriendIndex == retrievedList.size() - 1) {
				HostAGame_CenterViewController.addedFriendIndex = 0;
			}
			else {
				HostAGame_CenterViewController.addedFriendIndex++;
			}
		}
		
	}
	
	@FXML
	private void handleRemoveBtn(ActionEvent event) throws IOException {
		
		if (!HostAGame_CenterViewController.friendsDisplayList.contains(retrievedAcc)) {
			HostAGame_CenterViewController.friendsDisplayList.add(retrievedAcc);
		}
		
		
		int toberemoved = -1;
		for (int i = 0; i < HostAGame_CenterViewController.addedFriends.size(); i++) {
			if (HostAGame_CenterViewController.addedFriends.get(i).getAdminNo().toLowerCase().equals(retrievedAcc.getAdminNo().toLowerCase())) {
				toberemoved = i;
			}
		}
		
		if (toberemoved != -1) {
			HostAGame_CenterViewController.addedFriends.remove(toberemoved);
		}
		
		HostAGame_CenterViewController.displayResults = false;
		HostAGame_CenterViewController.afvc.initialize();
		HostAGame_CenterViewController.fvc.initialize();
	}
	
	@FXML
	public void handleAdd(ActionEvent event) throws IOException {
		HostAGame_CenterViewController.addedFriends.add(retrievedAcc);
		System.out.println(HostAGame_CenterViewController.addedFriends.size());
		HostAGame_CenterViewController.afvc.initialize();
	}
	
	private void setName() {
		lbName.setText(retrievedAcc.getName());
	}
	
	private void setDP() {
		
		try {
			ImagePattern ip = new ImagePattern(misc.cropCirclePhoto(adminNo, playerDP.getRadius()).getImage());
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
		lbPlayerRating.setText(misc.getRatingShapes(retrievedAcc.getCalRating()));
	}
	
	public void setMatchesPlayed() {
		int total = retrievedAcc.getTotalMatch();
		int played = retrievedAcc.getMatchPlayed();
		
		lbMatchesPlayed.setText(played + " / " + total);
	}
}


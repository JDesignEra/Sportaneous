package application.modules;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import entity.AccountsEntity;

import modules.Utils;

import application.ProfileViewController;

public class FriendCardViewController {
	@FXML private Text nameTxt, heightWeightTxt, ratingTxt, matchNoTxt;
	@FXML private GridPane cardContent;

	private int i = 0;

	private String adminNo;
	private String name;
	private double height;
	private double weight;
	private boolean heightVisibility;
	private boolean weightVisbility;
	private double rating;
	private int matchPlayed;
	private int totalMatch;

	@FXML
	private void initialize() {
		if (adminNo != null) {
			nameTxt.setText(name);
			ratingTxt.setText(Utils.getRatingShapes(rating));
			matchNoTxt.setText(matchPlayed + " / " + totalMatch);

			// Height & Weight
			if (heightVisibility || weightVisbility) {
				if (heightVisibility && weightVisbility) {
					heightWeightTxt.setText(height + " m | " + weight + " kg");
				}
				else if (heightVisibility) {
					heightWeightTxt.setText(height + " m");
				}
				else {
					heightWeightTxt.setText(weight + " kg");
				}
			}
			else {
				heightWeightTxt.setVisible(false);
				heightWeightTxt.setManaged(false);
			}

			cardContent.add(Utils.cropCirclePhoto(adminNo, 100), 0, 0);
		}
	}

	@FXML
	public void cardContentOnMouseClick(MouseEvent event) {
		new ProfileViewController().viewProfile(adminNo, "/application/FriendsView.fxml");
	}
	
	public void setAccounts(List<AccountsEntity> accounts, int index) {
		i = index;
		
		adminNo = accounts.get(i).getAdminNo();
		name = accounts.get(i).getName();
		height = accounts.get(i).getHeight();
		weight = accounts.get(i).getWeight();
		heightVisibility = accounts.get(i).getHeightVisibility();
		weightVisbility = accounts.get(i).getWeightVisibility();
		rating = accounts.get(i).getCalRating();
		matchPlayed = accounts.get(i).getMatchPlayed();
		totalMatch = accounts.get(i).getTotalMatch();
		
		initialize();
	}
}

package application.modules;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import entity.AccountsEntity;

import modules.Misc;

import application.FriendsViewController;
import application.ProfileViewController;

public class FriendCardViewController {
	@FXML private Text nameTxt, heightWeightTxt, ratingTxt, matchNoTxt;
	@FXML private GridPane cardContent;

	private int i = FriendsViewController.getAccIndex();
	private List<AccountsEntity> accounts = FriendsViewController.getAccounts();

	private String adminNo = accounts.get(i).getAdminNo();
	private String name = accounts.get(i).getName();
	private double height = accounts.get(i).getHeight();
	private double weight = accounts.get(i).getWeight();
	private boolean heightVisibility = accounts.get(i).getHeightVisibility();
	private boolean weightVisbility = accounts.get(i).getWeightVisibility();
	private double rating = accounts.get(i).getCalRating();
	private int matchPlayed = accounts.get(i).getMatchPlayed();
	private int totalMatch = accounts.get(i).getTotalMatch();

	@FXML
	private void initialize() {
		nameTxt.setText(name);
		ratingTxt.setText(new Misc().getRatingShapes(rating));
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

		cardContent.add(new Misc().cropCirclePhoto(adminNo, 100), 0, 0);
	}

	@FXML
	public void cardContentOnMouseClick(MouseEvent event) {
		ProfileViewController.viewProfile(adminNo, "/application/FriendsView.fxml");
	}
}

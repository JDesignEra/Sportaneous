package application.modules;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import modules.Misc;

import application.FriendsViewController;
import application.ProfileViewController;

public class FriendCardViewController implements Initializable {
	@FXML private Text nameTxt, heightWeightTxt, ratingTxt, matchNoTxt;
	@FXML private GridPane cardContent;

	private int i = FriendsViewController.getAccIndex();
	private Object[][] accounts = FriendsViewController.getAccounts();

	private String adminNo = (String) accounts[i][0];
	private String name = (String) accounts[i][2];
	private double height = (double) accounts[i][3];
	private double weight = (double) accounts[i][4];
	private boolean heightVisibility = (boolean) accounts[i][5];
	private boolean weightVisbility = (boolean) accounts[i][6];
	private double rating = (double) accounts[i][7];
	private int matchPlayed = (int) accounts[i][8];
	private int totalMatch = (int) accounts[i][9];

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
			else if (weightVisbility) {
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

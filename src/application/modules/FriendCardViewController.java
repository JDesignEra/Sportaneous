package application.modules;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import modules.Misc;

import application.FriendsViewController;
import application.ProfileViewController;

public class FriendCardViewController implements Initializable {
	@FXML private Text nameTxt, heightWeightTxt, ratingTxt, matchNoTxt;
	@FXML private Pane cardPane;
	@FXML private GridPane cardContent;

	private int i = FriendsViewController.getFriendIndex();
	private Object[][] friends = FriendsViewController.getFriends();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String adminNo = (String) friends[i][0];
		String name = (String) friends[i][1];
		double height = (double) friends[i][2];
		double weight = (double) friends[i][3];
		boolean heightVisibility = (boolean) friends[i][4];
		boolean weightVisbility = (boolean) friends[i][5];
		double rating = (double) friends[i][6];
		int matchPlayed = (int) friends[i][7];
		int totalMatch = (int) friends[i][8];

		cardPane.setId(adminNo); // Set AdminNumbeer to cardPane
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
		ProfileViewController.viewProfile(cardPane.getId(), "/application/FriendsView.fxml");
	}
}

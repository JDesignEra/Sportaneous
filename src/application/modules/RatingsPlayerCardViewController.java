package application.modules;

import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import entity.AccountsEntity;
import entity.RatingsEntity;

import dataAccess.AccountsDA;
import dataAccess.RatingsDA;

import modules.Misc;

public class RatingsPlayerCardViewController {
	@FXML private Text nameTxt;
	@FXML private Text heightWeightTxt;
	@FXML private Text ratingStarsTxt;
	@FXML private Text matchesTxt;
	@FXML private TextArea commentsTxtArea;
	@FXML private ToggleButton ratingStars1Btn;
	@FXML private ToggleButton ratingStars2Btn;
	@FXML private ToggleButton ratingStars3Btn;
	@FXML private ToggleButton ratingStars4Btn;
	@FXML private ToggleButton ratingStars5Btn;
	@FXML private HBox ratingStarsBtnHBox;
	@FXML private JFXToggleButton attendToggleBtn;

	private static int ratingIndex, playerIndex;
	private RatingsEntity rating = RatingsDA.getRatings().get(ratingIndex);
	private AccountsEntity account = AccountsDA.getAccData(rating.getAdminNums()[playerIndex]);

	@FXML
	private void initialize() {
		nameTxt.setText(account.getName());
		ratingStarsTxt.setText(new Misc().getRatingShapes(account.getCalRating()));
		matchesTxt.setText(Integer.toString(account.getMatchPlayed()) + " / " + Integer.toString(account.getTotalMatch()));

		if (account.getHeightVisibility() || account.getWeightVisibility()) {
			if (account.getHeightVisibility() && account.getWeightVisibility()) {
				heightWeightTxt.setText(Double.toString(account.getHeight()) + " m | " + Double.toString(account.getWeight()) + " kg");
			}
			else if (account.getHeightVisibility()) {
				heightWeightTxt.setText(Double.toString(account.getHeight()) + " kg");
			}
			else {
				heightWeightTxt.setText(Double.toString(account.getWeight()));
			}
		}
		else {
			heightWeightTxt.setVisible(false);
			heightWeightTxt.setManaged(false);
		}
	}

	// Event Listener on ToggleButton[#ratingStars1Btn].onAction
	@FXML
	private void ratingStarsBtnOnAction(ActionEvent event) {
		if (event.getSource() instanceof ToggleButton) {
			ToggleButton toggleBtn = (ToggleButton) event.getSource();
			int ratingStarsIndex = Integer.parseInt(toggleBtn.getId());

			for (int i = 0; i < ratingStarsBtnHBox.getChildren().size(); i++) {
				if (ratingStarsBtnHBox.getChildren().get(i) instanceof ToggleButton) {
					toggleBtn = (ToggleButton) ratingStarsBtnHBox.getChildren().get(i);

					if (i <= ratingStarsIndex) {
						toggleBtn.setSelected(true);
						toggleBtn.setText("\uf005");
					}
					else {
						toggleBtn.setSelected(false);
						toggleBtn.setText("\uf006");
					}
				}
			}
		}
	}

	public static void setRatingIndex(int index) {
		ratingIndex = index;
	}

	public static void setPlayerIndex(int playerIndex) {
		RatingsPlayerCardViewController.playerIndex = playerIndex;
	}
}

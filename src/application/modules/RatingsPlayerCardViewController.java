package application.modules;

import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import entity.AccountsEntity;

import dataAccess.AccountsDA;

import modules.Utils;

public class RatingsPlayerCardViewController {
	@FXML private Text nameTxt;
	@FXML private Text heightWeightTxt;
	@FXML private Text ratingStarsTxt;
	@FXML private Text matchesTxt;
	@FXML private TextArea commentsTxtArea;
	@FXML private HBox ratingStarsBtnHBox;
	@FXML private JFXToggleButton attendToggleBtn;
	@FXML private GridPane rootGridPane;

	private AccountsEntity account;

	@FXML
	private void initialize() {
		if (account != null) {
			rootGridPane.add(Utils.cropCirclePhoto(account.getAdminNo(), 100), 0, 0);
			nameTxt.setText(account.getName());
			ratingStarsTxt.setText(Utils.getRatingShapes(account.getCalRating()));
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

	public void setPlayerRatings(String adminNo) {
		account = AccountsDA.getAccData(adminNo);
		initialize();
	}
}

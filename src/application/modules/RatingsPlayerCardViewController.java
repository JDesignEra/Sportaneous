package application.modules;

import javafx.fxml.FXML;

import javafx.scene.text.Text;

import javafx.scene.control.ToggleGroup;

import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

import javafx.scene.control.ToggleButton;

import com.jfoenix.controls.JFXToggleButton;

public class RatingsPlayerCardViewController {
	@FXML private Text nameTxt;
	@FXML private Text heightWeightTxt;
	@FXML private Text ratingStarsTxt;
	@FXML private Text matchesTxt;
	@FXML private TextArea commentsTxtArea;
	@FXML private ToggleButton ratingStars1Btn;
	@FXML private ToggleGroup ratingStarsBtnGrp;
	@FXML private ToggleButton ratingStars2Btn;
	@FXML private ToggleButton ratingStars3Btn;
	@FXML private ToggleButton ratingStars4Btn;
	@FXML private ToggleButton ratingStars5Btn;
	@FXML private JFXToggleButton attendToggleBtn;

	// Event Listener on ToggleButton[#ratingStars1Btn].onAction
	@FXML
	public void ratingStarsBtnOnAction(ActionEvent event) {
		// TODO Autogenerated
	}
}

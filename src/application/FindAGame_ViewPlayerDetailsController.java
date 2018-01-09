package application;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.shape.Circle;

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
	
	void setName(String name) {
		lbName.setText(name);
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

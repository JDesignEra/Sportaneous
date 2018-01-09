package application.modules;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import dataAccess.AccountsDA;
import dataAccess.CommentsDA;

import modules.Misc;

public class CommentsViewController {
	@FXML private Circle dpCricle;
	@FXML private Text nameTxt;
	@FXML private Text commentTxt;
	@FXML private Text ratingTxt;
	@FXML private GridPane commContentGridPane;

	private static int index = 0;

	private Object[][] comments = CommentsDA.getComments(AccountsDA.getAdminNo());
	private String adminNo = comments[index][0].toString();
	private String name = comments[index][1].toString();
	private String comment = comments[index][2].toString();
	private double rating = (double) comments[index][3];

	@FXML
	public void initialize() {
		if (comments.length > 0 && index < comment.length() - 1) {
			commContentGridPane.add(new Misc().cropCirclePhoto(adminNo, 75), 0, 0);
			nameTxt.setText(name);
			commentTxt.setText(comment);

			// Ratings
			if (rating > 0) {
				StringBuilder ratingStars = new StringBuilder();

				for (int i = 0; i < 5; i++) {
					if (i < (int) rating) {
						ratingStars.append("\uf005 ");
					}
					else if ((rating - i) >= 0.5) {
						ratingStars.append("\uf123 ");
					}
					else {
						ratingStars.append((i < 4 ? "\uf006 " : "\uf006"));
					}
				}

				ratingTxt.setText(ratingStars.toString());
			}
		}
	}

	public static void setIndex(int index) {
		CommentsViewController.index = index;
	}

	public static int getIndex() {
		return index;
	}
}

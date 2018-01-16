package application.modules;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import dataAccess.AccountsDA;
import dataAccess.CommentsDA;

import modules.Misc;

import application.ProfileViewController;

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
	private int rating = (int) comments[index][3];

	@FXML
	public void initialize() {
		commContentGridPane.setId(adminNo);
		commContentGridPane.add(new Misc().cropCirclePhoto(adminNo, 75), 0, 0);
		nameTxt.setText(name);
		commentTxt.setText(comment);
		ratingTxt.setText(new Misc().getRatingShapes(rating));
	}

	@FXML
	public void commContentGridPaneOnMouseClick(MouseEvent event) {
		ProfileViewController.viewProfile(commContentGridPane.getId(), "/application/ProfileView.fxml");
	}

	public static void setIndex(int index) {
		CommentsViewController.index = index;
	}

	public static int getIndex() {
		return index;
	}
}

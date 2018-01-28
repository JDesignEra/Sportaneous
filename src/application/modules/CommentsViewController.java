package application.modules;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import entity.CommentsEntity;

import dataAccess.CommentsDA;

import modules.Utils;

import application.ProfileViewController;

public class CommentsViewController {
	@FXML private Circle dpCricle;
	@FXML private Text nameTxt;
	@FXML private Text commentTxt;
	@FXML private Text ratingTxt;
	@FXML private GridPane commContentGridPane;

	private static int index = 0;

	private List<CommentsEntity> comments = CommentsDA.getComments(ProfileViewController.getAdminNo());
	private String adminNo = comments.get(index).getAdminNo();
	private String name = comments.get(index).getName();
	private String comment = comments.get(index).getComment();
	private int rating = comments.get(index).getRating();

	@FXML
	private void initialize() {
		commContentGridPane.add(Utils.cropCirclePhoto(adminNo, 75), 0, 0);
		nameTxt.setText(name);
		commentTxt.setText(comment);
		ratingTxt.setText(Utils.getRatingShapes(rating));
	}

	@FXML
	private void commContentGridPaneOnMouseClick(MouseEvent event) {
		ProfileViewController.viewProfile(adminNo, "/application/ProfileView.fxml");
	}

	public static void setIndex(int index) {
		CommentsViewController.index = index;
	}

	public static int getIndex() {
		return index;
	}
}

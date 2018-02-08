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

	private String adminNo;
	private String name;
	private String comment;
	private int rating;

	@FXML
	private void initialize() {
		commContentGridPane.getChildren().remove(commContentGridPane.getChildren().size() - 1);
		commContentGridPane.add(Utils.cropCirclePhoto(adminNo, 75), 0, 0);
		nameTxt.setText(name);
		commentTxt.setText(comment);
		ratingTxt.setText(Utils.getRatingShapes(rating));
	}

	@FXML
	private void commContentGridPaneOnMouseClick(MouseEvent event) {
		 new ProfileViewController().viewProfile(adminNo, "/application/ProfileView.fxml");
	}

	public String getAdminNo() {
		return adminNo;
	}

	public void setComments(String adminNo, int index) {
		List<CommentsEntity> comments = CommentsDA.getComments(adminNo);
		name = comments.get(index).getName();
		comment = comments.get(index).getComment();
		rating = comments.get(index).getRating();
		this.adminNo = comments.get(index).getAdminNo();

		initialize();
	}
}

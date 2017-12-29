package application.assets.modules;

import java.io.File;

import dataAccess.AccountsDA;
import dataAccess.CommentsDA;
import javafx.fxml.FXML;

import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

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

	private Image img = new Image("/application/assets/uploads/default.png");
	private ImageView imgView = new ImageView(img);
	private Circle clip = new Circle(75, 75, 75);

	@FXML
	public void initialize() {
		if (comments.length > 0 && index < comment.length() - 1) {
			nameTxt.setText(name);
			commentTxt.setText(comment);

			commContentGridPane.getChildren().remove(commContentGridPane.lookup(".dpImgView"));

			// Profile Photo
			if (new File("/application/assets/uploads/" + adminNo + ".png").exists()) {
				img = new Image("/application/assets/uploads/" + adminNo + ".png");
				imgView = new ImageView(img);

				// Crop
				if (img.getHeight() >= 150 || img.getWidth() >= 150) {
					int w = (int) img.getWidth();
					int h = (int) img.getHeight();

					PixelReader pr;
					WritableImage newImg;

					if (img.getHeight() > img.getWidth()) {
						pr = img.getPixelReader();
						newImg = new WritableImage(pr, 0, (h - w) / 2, w, w);

						imgView.setImage(newImg);
					}
					else {
						pr = img.getPixelReader();
						newImg = new WritableImage(pr, (w - h) / 2, 0, h, h);

						imgView.setImage(newImg);
					}
				}
			}

			imgView.setFitWidth(150);
			imgView.setFitHeight(150);
			imgView.setClip(clip);
			imgView.getStyleClass().add("dpImgView");
			commContentGridPane.add(imgView, 0, 0);

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

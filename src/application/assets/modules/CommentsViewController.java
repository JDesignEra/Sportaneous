package application.assets.modules;

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
	@FXML private GridPane commentGridPane;

	private Object[][] comments = CommentsDA.getComments(AccountsDA.getAdminNo());
	private int index = 0;
	
	private String adminNo = comments[index][0].toString();
	private String name = comments[index][1].toString();
	private String comment = comments[index][2].toString();
	private double rating = Double.valueOf(comments[index][3].toString());

	private Image img;
	private ImageView imgView;
	private Circle clip = new Circle(75, 75, 75);

	@FXML
	public void initialize() {
		populateComments();
	}

	// For Next Comment Button
	public boolean nextComment() {
		if (index < comments.length) {
			index++;
			populateComments();
		}
		
		if (index == comments.length) {
			return false;
		}
		
		return true;
	}

	// For Previous Comment Button
	public boolean prevComment() {
		if (index > 0) {
			index--;
			populateComments();
		}

		if (index == 0) {
			return false;
		}
		
		return true;
	}
	
	private void populateComments() {
		if (comments.length > 0 && index < comments.length) {
			nameTxt.setText(name);
			commentTxt.setText(comment);
			
			// Profile Photo
			try {
				img = new Image("/application/assets/uploads/" + adminNo + ".png");
				imgView = new ImageView(img);

				// Crop
				if (img.getHeight() >= 150 || img.getWidth() >= 150) {
					int w = (int) img.getWidth();
					int h = (int) img.getHeight();

					if (img.getHeight() > img.getWidth()) {
						PixelReader pr = img.getPixelReader();
						WritableImage newImage = new WritableImage(pr, 0, (h - w) / 2, w, w);

						imgView.setImage(newImage);
					}
					else {
						PixelReader pr = img.getPixelReader();
						WritableImage newImage = new WritableImage(pr, (w - h) / 2, 0, h, h);

						imgView.setImage(newImage);
					}
				}
			}
			catch (IllegalArgumentException e) {}

			imgView.setFitWidth(150);
			imgView.setFitHeight(150);
			imgView.setClip(clip);
			
			commentGridPane.add(imgView, 0, 0);
			
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
}

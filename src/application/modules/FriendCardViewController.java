package application.modules;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import dataAccess.FriendsDA;

import modules.Misc;

import application.FriendsViewController;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class FriendCardViewController implements Initializable {
	@FXML private Text nameTxt, heightWeightTxt, ratingTxt, matchNoTxt;
	@FXML private Pane cardPane;
	@FXML private GridPane cardContent;

	private int i = FriendsViewController.getFriendIndex();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Object[][] friends = FriendsDA.getFriends();
		
		cardPane.setId(friends[i][0].toString()); // Set AdminNumbeer to cardPane
		nameTxt.setText(friends[i][1].toString());
		ratingTxt.setText(new Misc().getRatingShapes(Double.parseDouble(friends[i][11].toString())));
		matchNoTxt.setText(friends[i][13] + " / " + friends[i][14]);
		
		// Height & Weight
		if (friends[i][9].equals(true) || friends[i][10].equals(true)) {
			String height = friends[i][7].toString();
			String weight = friends[i][8].toString();
			
			if (friends[i][9].equals(true) && friends[i][10].equals(true)) {
				heightWeightTxt.setText(height + " m | " + weight + " kg");
			}
			else if (friends[i][9].equals(true)) {
				heightWeightTxt.setText(height + " m");
			}
			else if (friends[i][10].equals(true)) {
				heightWeightTxt.setText(weight + " kg");
			}
		}
		else {
			heightWeightTxt.setVisible(false);
			heightWeightTxt.setManaged(false);
		}
		
		// Profile Photo
		Image img = new Image("/application/assets/uploads/default.png");
		ImageView imgView = new ImageView(img);
		Circle clip = new Circle(100, 100, 100);
		
		if (getClass().getResource("/application/assets/uploads/" + friends[i][0] + ".png") != null) {
			img = new Image(getClass().getResource("/application/assets/uploads/" + friends[i][0] + ".png").toExternalForm());
			imgView = new ImageView(img);

			// Crop
			if (img.getHeight() >= 200 || img.getWidth() >= 200) {
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

		imgView.setFitWidth(200);
		imgView.setFitHeight(200);
		imgView.setClip(clip);
	}
}

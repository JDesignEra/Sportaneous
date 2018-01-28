package modules;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Circle;

public final class Utils {
	public static String getRatingShapes(double rating) {
		StringBuilder ratingStars = new StringBuilder();
		
		if (rating > 0) {
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
		}
		else {
			ratingStars.append("\uf006 \uf006 \uf006 \uf006 \uf006");
		}
		
		return ratingStars.toString();
	}
	
	public static ImageView cropCirclePhoto(String adminNo, double radius) {
		Image img = new Image("/application/assets/uploads/default.png");
		ImageView imgView = new ImageView(img);
		Circle clip = new Circle(radius, radius, radius);
		
		if (Utils.class.getResource("/application/assets/uploads/" + adminNo + ".png") != null) {
			img = new Image(Utils.class.getResource("/application/assets/uploads/" + adminNo + ".png").toExternalForm());
			imgView = new ImageView(img);

			// Crop
			if (img.getHeight() >= radius * 2 || img.getWidth() >= radius * 2) {
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

		imgView.setFitWidth(radius * 2);
		imgView.setFitHeight(radius * 2);
		imgView.setClip(clip);
		
		return imgView;
	}
}

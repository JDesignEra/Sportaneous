package application;

import java.net.URL;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;

public class Main extends Application {
	private static BorderPane root;
	private static URL loc;

	@Override
	public void start(Stage primaryStage) {
		try {
			root = new BorderPane();
			loc = getClass().getResource("/application/LRFView.fxml");
			//loc = getClass().getResource("/application/EditProfileView.fxml");
			root.setCenter(FXMLLoader.load(loc));

			JFXDecorator decorator = new JFXDecorator(primaryStage, root);
			decorator.setCustomMaximize(true);
			decorator.setGraphic(new ImageView(new Image("/application/assets/img/Sportaneous_small.png")));
			decorator.setText("Sportaneous");

			Scene scene = new Scene(decorator, 1600, 900);
			scene.getStylesheets().add(getClass().getResource("/application/assets/css/application.css").toExternalForm());
			
			primaryStage.getIcons().add(new Image("/application/assets/img/Sportaneous_small_alt.png"));
			primaryStage.setTitle("Sportaneous");
			primaryStage.setScene(scene);
			primaryStage.show();

			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			double stageHeight = primaryStage.getHeight();
			double stageWidth = primaryStage.getWidth();

			primaryStage.setX(screenBounds.getMaxX() > stageWidth ? (screenBounds.getMaxX() - stageWidth) / 2 : 0);
			primaryStage.setY(screenBounds.getMaxY() > stageHeight ? (screenBounds.getMaxY() - stageHeight) / 2 : 0);
			primaryStage.setMinWidth(1600);
			primaryStage.setMinHeight(900);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static BorderPane getRoot() {
		return root;
	}

	public static URL getLoc() {
		return loc;
	}

	public static void setLoc(URL loc) {
		Main.loc = loc;
	}
}

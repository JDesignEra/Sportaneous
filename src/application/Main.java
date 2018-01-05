package application;

import java.net.URL;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	private static BorderPane root;
	private static URL loc;

	private final URL logoURL = getClass().getResource("/application/assets/img/Sportaneous_small.png");
	private final URL logoAltURL = getClass().getResource("/application/assets/img/Sportaneous_small_alt.png");
	private final URL stylesheetURL = getClass().getResource("/application/assets/css/application.css");
	
	//-for testing only-//
		public static String currentUserAdminNo = "";
	//-------end-------//

	@Override
	public void start(Stage primaryStage) {
		root = new BorderPane();
		loc = getClass().getResource("/application/LRFView.fxml");

		try {
			root.setCenter(FXMLLoader.load(loc));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		JFXDecorator decorator = new JFXDecorator(primaryStage, root);
		decorator.setCustomMaximize(true);
		decorator.setGraphic(new ImageView(new Image(logoURL.toExternalForm())));
		decorator.setText("Sportaneous");

		Scene scene = new Scene(decorator, 1600, 900);
		scene.getStylesheets().add(stylesheetURL.toExternalForm());

		primaryStage.getIcons().add(new Image(logoAltURL.toExternalForm()));
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

	public static void main(String[] args) {
		launch(args);
	}

	public static BorderPane getRoot() {
		return root;
	}

	public static URL getLoc() {
		return loc;
	}
}

package application;
	
import java.net.URL;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private static BorderPane root;
	private static URL loc;
	private static String title = "Sportaneous";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = new BorderPane();
			loc = getClass().getResource("/application/LRFView.fxml");
			root.setCenter(FXMLLoader.load(loc));
			Scene scene = new Scene(root, 1280, 720);
			primaryStage.getIcons().add(new Image("/application/assets/img/Sportaneous_alt.png"));
			primaryStage.setTitle(title);
			scene.getStylesheets().add(getClass().getResource("/application/assets/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
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
	
	public static URL setLoc(URL loc) {
		Main.loc = loc;
		
		return Main.loc;
	}
	
	public static String getTitle() {
		return title;
	}
	
	public static void setTitle(String title) {
		Main.title = title;
	}
}

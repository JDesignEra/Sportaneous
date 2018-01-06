package application.modules;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXProgressBar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

import dataAccess.AccountsDA;

import modules.Snackbar;

import application.Main;

public class UploadBodyViewController {
	@FXML private Text recText;
	@FXML private GridPane rootGridPane;

	private String adminNo = AccountsDA.getAdminNo();
	private boolean success = false;
	private File file;

	private static final String IMAGE_REGEX = "(.*/)*.+\\.(png|jpg|jpeg|PNG|JPG)$";
	private final URL profileViewURL = getClass().getResource("/application/ProfileView.fxml");

	// Event Listener on GridPane.onDragDropped
	@FXML
	public void uploadGridPaneDragDrop(DragEvent event) throws IOException {
		Dragboard dragBoard = event.getDragboard();

		if (dragBoard.hasFiles() && dragBoard.getFiles().size() == 1) {
			file = dragBoard.getFiles().get(0);

			transferFile(file);
		}

		event.setDropCompleted(success);
		event.consume();
	}

	// Event Listener on GridPane.onDragOver
	@FXML
	public void uploadGridPaneDragOver(DragEvent event) {
		Dragboard dragBoard = event.getDragboard();

		rootGridPane.getChildren().remove(rootGridPane.lookup(".para.danger"));

		if (!dragBoard.hasFiles() || dragBoard.getFiles().size() > 1) {
			Text msg = new Text();
			msg.setText("You can only upload one image.");
			msg.getStyleClass().addAll("para", "danger");

			rootGridPane.add(msg, 0, 2);
			event.consume();
		}
		else if (!dragBoard.getFiles().get(0).getName().matches(IMAGE_REGEX)) {
			Text msg = new Text();
			msg.setText("You can only upload either a .png or .jpg image.");
			msg.getStyleClass().addAll("para", "danger");

			rootGridPane.add(msg, 0, 2);
			event.consume();
		}
		else if (dragBoard.getFiles().get(0).length() > 1048576) {
			Text msg = new Text();
			msg.setText("You can only upload a maximum of 1 mb image.");
			msg.getStyleClass().addAll("para", "danger");

			event.consume();
		}
		else {
			event.acceptTransferModes(TransferMode.COPY);
		}
	}

	// Event Listener on Hyperlink.onAction
	@FXML
	public void browseOnAction(ActionEvent event) throws IOException {
		rootGridPane.getChildren().remove(rootGridPane.lookup(".para.danger"));

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Upload Image");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));

		file = fileChooser.showOpenDialog(new Stage());

		if (file != null && file.length() > 1048576) {
			Text msg = new Text();
			msg.setText("You can only upload a maximum of 1 mb image.");
			msg.getStyleClass().addAll("para", "danger");

			rootGridPane.add(msg, 0, 2);
		}
		else if (file != null) {
			transferFile(file);
		}
	}

	private void transferFile(File file) throws IOException {
		BufferedImage bufferImg;

		JFXProgressBar progressBar = new JFXProgressBar();
		progressBar.setProgress(-1.0f);

		rootGridPane.add(progressBar, 0, 2);

		bufferImg = ImageIO.read(file);
		ImageIO.write(bufferImg, "png", new File("src/application/assets/uploads/" + adminNo + ".png"));
		success = ImageIO.write(bufferImg, "png", new File("bin/application/assets/uploads/" + adminNo + ".png"));

		if (rootGridPane.getParent().getParent() instanceof VBox) {
			new Snackbar().successSpinner((VBox) rootGridPane.getParent().getParent(), "Your photo has been uploaded successfully. You will be redirected to your profile page...",
					4000);

			Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3500), ev -> {
				try {
					Main.getRoot().setCenter(FXMLLoader.load(profileViewURL));
					;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}));
			timeline.play();
		}
	}
}

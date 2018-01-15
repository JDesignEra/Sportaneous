package modules;

import java.net.URL;

import com.jfoenix.controls.JFXSnackbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Snackbar {
	private final URL spinnerWhite = getClass().getResource("/application/assets/img/spinner_white.gif");

	public void info(Pane root, String msg) {
		this.info(root, msg, null, null);
	}

	public void success(Pane root, String msg) {
		this.success(root, msg, null, null);
	}

	public void warning(Pane root, String msg) {
		this.warning(root, msg, null, null);
	}

	public void danger(Pane root, String msg) {
		this.danger(root, msg, null, null);
	}

	public void info(Pane root, String msg, String actionText, EventHandler<ActionEvent> actionHandler) {
		JFXSnackbar sb = new JFXSnackbar(root);

		if (actionText != null && !actionText.isEmpty() && actionHandler != null) {
			sb.getStyleClass().addAll("action", "info");
			sb.show(msg, actionText, 3000, actionHandler);
		}
		else {
			sb.getStyleClass().add("info");
			sb.show(msg, 3000);
		}
	}

	public void success(Pane root, String msg, String actionText, EventHandler<ActionEvent> actionHandler) {
		JFXSnackbar sb = new JFXSnackbar(root);

		if (actionText != null && !actionText.isEmpty() && actionHandler != null) {
			sb.getStyleClass().addAll("action", "success");
			sb.show(msg, actionText, 3000, actionHandler);
		}
		else {
			sb.getStyleClass().add("success");
			sb.show(msg, 3000);
		}
	}

	public void warning(Pane root, String msg, String actionText, EventHandler<ActionEvent> actionHandler) {
		JFXSnackbar sb = new JFXSnackbar(root);

		if (actionText != null && !actionText.isEmpty() && actionHandler != null) {
			sb.getStyleClass().addAll("action", "warning");
			sb.show(msg, actionText, 3000, actionHandler);
		}
		else {
			sb.getStyleClass().add("warning");
			sb.show(msg, 3000);
		}
	}

	public void danger(Pane root, String msg, String actionText, EventHandler<ActionEvent> actionHandler) {
		JFXSnackbar sb = new JFXSnackbar(root);

		if (actionText != null && !actionText.isEmpty() && actionHandler != null) {
			sb.getStyleClass().addAll("action", "danger");
			sb.show(msg, actionText, 3000, actionHandler);
		}
		else {
			sb.getStyleClass().add("danger");
			sb.show(msg, 3000);
		}
	}

	public void infoSpinner(Pane root, String msg, long timeOut) {
		JFXSnackbar sb = new JFXSnackbar(root);
		sb.getStyleClass().add("info");

		Parent parent = sb.lookup(".jfx-snackbar-toast").getParent().getParent();

		if (parent instanceof BorderPane) {
			BorderPane rootPane = (BorderPane) parent;
			StackPane stackPane = new StackPane();

			ImageView imgView = new ImageView(spinnerWhite.toExternalForm());
			imgView.setFitHeight(rootPane.getHeight() - 10);
			imgView.setFitWidth(rootPane.getHeight() - 10);
			imgView.preserveRatioProperty().set(true);

			stackPane.getChildren().add(imgView);
			StackPane.setAlignment(imgView, Pos.CENTER);
			stackPane.setPadding(new Insets(5, 20, 0, 5));

			rootPane.setRight(stackPane);
		}

		sb.show(msg, timeOut);
	}

	public void successSpinner(Pane root, String msg, long timeOut) {
		JFXSnackbar sb = new JFXSnackbar(root);
		sb.getStyleClass().add("success");

		Parent parent = sb.lookup(".jfx-snackbar-toast").getParent().getParent();

		if (parent instanceof BorderPane) {
			BorderPane rootPane = (BorderPane) parent;
			StackPane stackPane = new StackPane();

			ImageView imgView = new ImageView(spinnerWhite.toExternalForm());
			imgView.setFitHeight(rootPane.getHeight() - 10);
			imgView.setFitWidth(rootPane.getHeight() - 10);
			imgView.preserveRatioProperty().set(true);

			stackPane.getChildren().add(imgView);
			StackPane.setAlignment(imgView, Pos.CENTER);
			stackPane.setPadding(new Insets(5, 20, 0, 5));

			rootPane.setRight(stackPane);
		}

		sb.show(msg, timeOut);
	}

	public void warningSpinner(Pane root, String msg, long timeOut) {
		JFXSnackbar sb = new JFXSnackbar(root);
		sb.getStyleClass().add("warning");

		Parent parent = sb.lookup(".jfx-snackbar-toast").getParent().getParent();

		if (parent instanceof BorderPane) {
			BorderPane rootPane = (BorderPane) parent;
			StackPane stackPane = new StackPane();

			ImageView imgView = new ImageView(spinnerWhite.toExternalForm());
			imgView.setFitHeight(rootPane.getHeight() - 10);
			imgView.setFitWidth(rootPane.getHeight() - 10);
			imgView.preserveRatioProperty().set(true);

			stackPane.getChildren().add(imgView);
			StackPane.setAlignment(imgView, Pos.CENTER);
			stackPane.setPadding(new Insets(5, 20, 0, 5));

			rootPane.setRight(stackPane);
		}

		sb.show(msg, timeOut);
	}

	public void dangerSpinner(Pane root, String msg, long timeOut) {
		JFXSnackbar sb = new JFXSnackbar(root);
		sb.getStyleClass().add("danger");

		Parent parent = sb.lookup(".jfx-snackbar-toast").getParent().getParent();

		if (parent instanceof BorderPane) {
			BorderPane rootPane = (BorderPane) parent;
			StackPane stackPane = new StackPane();

			ImageView imgView = new ImageView(spinnerWhite.toExternalForm());
			imgView.setFitHeight(rootPane.getHeight() - 10);
			imgView.setFitWidth(rootPane.getHeight() - 10);
			imgView.preserveRatioProperty().set(true);

			stackPane.getChildren().add(imgView);
			StackPane.setAlignment(imgView, Pos.CENTER);
			stackPane.setPadding(new Insets(5, 20, 0, 5));

			rootPane.setRight(stackPane);
		}

		sb.show(msg, timeOut);
	}
}

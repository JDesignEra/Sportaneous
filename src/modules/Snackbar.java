package modules;

import com.jfoenix.controls.JFXSnackbar;

import javafx.scene.layout.Pane;

public class Snackbar {
	public static void danger(Pane root, String msg) {
		JFXSnackbar sb = new JFXSnackbar(root);
		sb.setPrefWidth(root.getWidth());
		sb.getStyleClass().add("danger");
		sb.show(msg, 3000);
	}
	
	public static void success(Pane root, String msg) {
		JFXSnackbar sb = new JFXSnackbar(root);
		sb.setPrefWidth(root.getWidth());
		sb.getStyleClass().add("success");
		sb.show(msg, 3000);
	}
}

package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import dataAccess.AccountsDA;
import dataAccess.CommentsDA;
import dataAccess.EquipmentsDA;
import dataAccess.FacilitiesDA;
import dataAccess.FriendsDA;
import dataAccess.HostsDA;
import dataAccess.NotificationsDA;
import dataAccess.RatingsDA;

import modules.Snackbar;

public class LRFViewController {
	@FXML private GridPane rootPane;
	@FXML private ImageView logoImageView;
	@FXML private TitledPane loginTitledPane;
	@FXML private TextField loginAdminNoTF;
	@FXML private PasswordField loginPassTF;
	@FXML private JFXButton loginBtn;
	@FXML private TitledPane regTitledPane;
	@FXML private TextField regNameTF;
	@FXML private TextField regAdminNoTF;
	@FXML private TextField regEmailTF;
	@FXML private PasswordField regPassTF;
	@FXML private JFXButton regBtn;
	@FXML private TitledPane fpassTitledPane;
	@FXML private TextField fpassEmailTF;
	@FXML private JFXButton fpassBtn;

	private final URL logoURL = getClass().getResource("/application/assets/img/Sportaneous.gif");
	private final URL navigationViewURL = getClass().getResource("/application/NavigationView.fxml");

	@FXML
	private void initialize() {
		AccountsDA.initDA();

		// Re-animate Sportaneous.gif
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), ae -> logoImageView.setImage(new Image(logoURL.toExternalForm()))));
		timeline.play();
	}

	// Event Listener on PasswordField[#loginPassTF] &
	// [#loginAdminNoTF].onKeyPressed
	@FXML
	public void loginTxtFieldOnKeyPress(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			login();
		}
	}

	// Event Listener on JFXButton[#loginBtn].onAction
	@FXML
	public void loginBtnOnAction(ActionEvent event) {
		login();
	}

	// Event Listener on TextField[#regNameTF], [#regAdminNoTF], [#regEmailTF],
	// [#regPassTF].onKeyPressed
	@FXML
	public void regTxtFieldOnKeyPress(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			register();
		}
	}

	// Event Listener on JFXButton[#regBtn].onAction
	@FXML
	public void regBtnOnAction(ActionEvent event) {
		register();
	}

	// Event Listener on TextField[#fpassAdminNoTF], [#fpassEmailTF].onKeyPressed
	@FXML
	public void fpassOnKeyPress(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			resetPass();
		}
	}

	// Event Listener on JFXButton[#fpassBtn].onAction
	@FXML
	public void fpassBtnOnAction(ActionEvent event) {
		resetPass();
	}

	// Event Listener on TitledPane[#loginTitledPane].onMouseClicked
	@FXML
	public void loginTitledPaneOnClick(MouseEvent event) {
		if (fpassTitledPane.isExpanded()) {
			fpassTitledPane.getStyleClass().add("inactive");
		}
		else {
			fpassTitledPane.getStyleClass().remove("inactive");
		}

		logoImageView.setImage(new Image(logoURL.toExternalForm()));
	}

	// Event Listener on TitledPane[#regTitledPane].onMouseClicked
	@FXML
	public void regTitledPaneOnClick(MouseEvent event) {
		if (fpassTitledPane.isExpanded()) {
			fpassTitledPane.getStyleClass().add("inactive");
		}
		else {
			fpassTitledPane.getStyleClass().remove("inactive");
		}

		logoImageView.setImage(new Image(logoURL.toExternalForm()));
	}

	// Event Listener on TitledPane[#fpassTitledPane].onMouseClicked
	@FXML
	public void fpassTitledPaneOnClick(MouseEvent event) {
		if (fpassTitledPane.isExpanded()) {
			fpassTitledPane.getStyleClass().add("inactive");
		}
		else {
			fpassTitledPane.getStyleClass().remove("inactive");
		}

		logoImageView.setImage(new Image(logoURL.toExternalForm()));
	}

	private void login() {
		String adminNo = loginAdminNoTF.getText();
		String pass = loginPassTF.getText();
		resetFieldStyle();

		switch (AccountsDA.login(adminNo, pass)) {
			case 0:
				CommentsDA.initDA();
				EquipmentsDA.initDA();
				FacilitiesDA.initDA();
				FriendsDA.initDA();
				HostsDA.initDA();
				NotificationsDA.initDA();
				RatingsDA.initDA();

				try {
					ProfileViewController.viewSessionProfile();
					Main.getRoot().setBottom(FXMLLoader.load(navigationViewURL));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 1:
				if (adminNo.isEmpty() && pass.isEmpty()) {
					new Snackbar().danger(rootPane, "Admin Number & Password is required.");
					loginAdminNoTF.getStyleClass().add("danger");
					loginPassTF.getStyleClass().add("danger");
				}
				else if (adminNo.isEmpty()) {
					new Snackbar().danger(rootPane, "Admin Number is required.");
					loginAdminNoTF.getStyleClass().add("danger");
					loginPassTF.getStyleClass().removeAll("danger");
				}
				else if (pass.isEmpty()) {
					new Snackbar().danger(rootPane, "Password is required.");
					loginPassTF.getStyleClass().add("danger");
					loginAdminNoTF.getStyleClass().removeAll("danger");
				}
				break;

			case 2:
				new Snackbar().danger(rootPane, "Admin Number or Password is wrong.");
				break;
		}
	}

	private void register() {
		String name = regNameTF.getText();
		String adminNo = regAdminNoTF.getText();
		String email = regEmailTF.getText();
		String pass = regPassTF.getText();

		resetFieldStyle();

		switch (AccountsDA.addAccount(name, adminNo, email, pass)) {
			case 0:
				new Snackbar().success(rootPane, "Successfully registered an account.");
				break;

			case 1:
				List<String> emptyMsg = new ArrayList<>();
				StringBuilder msg = new StringBuilder();

				if (name.isEmpty()) {
					regNameTF.getStyleClass().add("danger");
					emptyMsg.add("Name");
				}

				if (email.isEmpty()) {
					regEmailTF.getStyleClass().add("danger");
					emptyMsg.add("Email");
				}

				if (adminNo.isEmpty()) {
					regAdminNoTF.getStyleClass().add("danger");
					emptyMsg.add("Admin Number");
				}

				if (pass.isEmpty()) {
					regPassTF.getStyleClass().add("danger");
					emptyMsg.add("Password");
				}

				// Build empty field name message.
				for (int i = 0; i < emptyMsg.size(); i++) {
					msg.append(emptyMsg.get(i));

					if (i < emptyMsg.size() - 1) {
						msg.append(", ");
					}
				}

				new Snackbar().danger(rootPane, msg.toString() + " is required.");
				break;

			case 2:
				regEmailTF.getStyleClass().add("danger");
				new Snackbar().danger(rootPane, "Only NYP Email address are allowed.");
				break;

			case 3:
			case 4:
				regEmailTF.getStyleClass().add("danger");

				new Snackbar().danger(rootPane, "This Email or Admin Number is registered with us.", "Forgot Password?", ev -> {
					fpassTitledPane.setExpanded(true);
				});
				break;

			default:
				break;
		}
	}

	private void resetPass() {
		String email = fpassEmailTF.getText();

		resetFieldStyle();

		switch (AccountsDA.passwordReset(email)) {
			case 0:
				new Snackbar().success(rootPane, "Successfully reseted password, please check your email.");
				break;

			case 1:
				fpassEmailTF.getStyleClass().add("danger");
				new Snackbar().danger(rootPane, "Email is required");
				break;

			case 2:
				new Snackbar().danger(rootPane, "Email is not registered with us.", "Register?", ev -> regTitledPane.setExpanded(true));
				break;

			case 3:
				new Snackbar().danger(rootPane, "Whoops! Something went wrong, please try again.");
				break;
		}
	}

	private void resetFieldStyle() {
		loginAdminNoTF.getStyleClass().remove("danger");
		loginPassTF.getStyleClass().remove("danger");
		regNameTF.getStyleClass().remove("danger");
		regEmailTF.getStyleClass().remove("danger");
		regAdminNoTF.getStyleClass().remove("danger");
		regPassTF.getStyleClass().remove("danger");
		fpassEmailTF.getStyleClass().remove("danger");
	}
}

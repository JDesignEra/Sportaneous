package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import dataAccess.AccountsDA;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.TitledPane;

import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class LRFViewController {
	@FXML private TitledPane loginTitledPane;
	@FXML private TextField loginAdminNoTF;
	@FXML private PasswordField loginPassTF;
	@FXML private JFXButton loginBtn;
	@FXML private TitledPane regTitledPane;
	@FXML private TextField regNameTF;
	@FXML private TextField regAdminNoTF;
	@FXML private TextField regEmailTF;
	@FXML private PasswordField regPassTF;
	@FXML private TitledPane fpassTitledPane;
	@FXML private TextField fpassAdminNoTF;
	@FXML private TextField fpassEmailTF;
	@FXML private JFXButton fpassBtn;
	@FXML GridPane rootPane;

	@FXML
	public void initialize() {
		AccountsDA.initDA();
	}
	
	// Event Listener on JFXButton[#loginBtn].onAction
	@FXML
	public void loginBtnOnAction(ActionEvent event) {
		JFXSnackbar sb;
		
		switch (AccountsDA.login(loginAdminNoTF.getText(), loginPassTF.getText())) {
			case 0:
				try {
					Main.setLoc(getClass().getResource("/application/ProfileView.fxml"));
					Main.getRoot().setCenter(FXMLLoader.load(Main.getLoc()));
					Main.getRoot().setBottom(FXMLLoader.load(getClass().getResource("/application/NavigationView.fxml")));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case 1:
				if (loginAdminNoTF.getText().isEmpty() && loginPassTF.getText().isEmpty()) {
					sb = new JFXSnackbar(rootPane);
					sb.getStyleClass().add("danger");
					//sb.enqueue(new SnackbarEvent("Admin Number & Password field is empty.", null, 3000, false, null));
					sb.show("Admin Number & Password field is empty.", 3000);
					loginAdminNoTF.getStyleClass().add("danger");
					loginPassTF.getStyleClass().add("danger");
				}
				else if (loginAdminNoTF.getText().isEmpty()) {
					sb = new JFXSnackbar(rootPane);
					sb.getStyleClass().add("danger");
					sb.show("Admin Number field is empty.", 3000);
					loginAdminNoTF.getStyleClass().add("danger");
					loginPassTF.getStyleClass().removeAll("danger");
				}
				else if (loginPassTF.getText().isEmpty()) {
					sb = new JFXSnackbar(rootPane);
					sb.getStyleClass().add("danger");
					sb.show("Password field is empty.", 3000);
					loginPassTF.getStyleClass().add("danger");
					loginAdminNoTF.getStyleClass().removeAll("danger");
				}
				else {
					loginAdminNoTF.getStyleClass().removeAll("danger");
					loginPassTF.getStyleClass().removeAll("danger");
				}
				break;
				
			case 2:
				sb = new JFXSnackbar(rootPane);
				sb.getStyleClass().add("danger");
				sb.show("Account does not exist.", 3000);
				break;
				
			case 3:
				sb = new JFXSnackbar(rootPane);
				sb.getStyleClass().add("danger");
				sb.show("Invalid password.", 3000);
				break;
	
			default:
				break;
		}
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
	}
}

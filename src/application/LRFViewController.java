package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import application.assets.modules.Snackbar;
import dataAccess.AccountsDA;
import javafx.scene.control.TextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.GridPane;
import javafx.util.Duration;

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
	@FXML private TextField fpassAdminNoTF;
	@FXML private TextField fpassEmailTF;
	@FXML private JFXButton fpassBtn;
	
	@FXML
	public void initialize() {
		AccountsDA.initDA();
		
		//Re-Animate Sportaneous.gif
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), ae -> logoImageView.setImage(new Image(this.getClass().getResource("/application/assets/img/Sportaneous.gif").toExternalForm()))));
		timeline.play();
	}
	
	// Event Listener on PasswordField[#loginPassTF] & [#loginAdminNoTF].onKeyPressed
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
	
	// Event Listener on TextField[#regNameTF], [#regAdminNoTF], [#regEmailTF], [#regPassTF].onKeyPressed
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
	
	private void login() {
		String adminNo = loginAdminNoTF.getText();
		String pass = loginPassTF.getText();
		
		loginAdminNoTF.getStyleClass().removeAll("danger");
		loginPassTF.getStyleClass().removeAll("danger");
		
		switch (AccountsDA.login(adminNo, pass)) {
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
				if (adminNo.isEmpty() && pass.isEmpty()) {
					Snackbar.danger(rootPane, "Admin Number & Password field is empty.");
					loginAdminNoTF.getStyleClass().add("danger");
					loginPassTF.getStyleClass().add("danger");
				}
				else if (adminNo.isEmpty()) {
					Snackbar.danger(rootPane, "Admin Number field is empty.");
					loginAdminNoTF.getStyleClass().add("danger");
					loginPassTF.getStyleClass().removeAll("danger");
				}
				else if (pass.isEmpty()) {
					Snackbar.danger(rootPane, "Password field is empty.");
					loginPassTF.getStyleClass().add("danger");
					loginAdminNoTF.getStyleClass().removeAll("danger");
				}
				break;
				
			case 2:
				Snackbar.danger(rootPane, "This account does not exist, please register an account instead.");
				break;
				
			case 3:
				Snackbar.danger(rootPane, "The login credential you have entered is invalid.");
				break;
	
			default:
				break;
		}
	}
	
	private void register() {
		String name = regNameTF.getText();
		String adminNo = regAdminNoTF.getText();
		String email = regEmailTF.getText();
		String pass = regPassTF.getText();
		
		regNameTF.getStyleClass().removeAll("danger");
		regAdminNoTF.getStyleClass().removeAll("danger");
		regEmailTF.getStyleClass().removeAll("danger");
		regPassTF.getStyleClass().removeAll("danger");
		
		switch(AccountsDA.addAccount(name, adminNo, email, pass)) {
			case 0:
				Snackbar.success(rootPane, "You have registered an account with us successfully.");
				break;
				
			case 1:
				List<String> emptyMsg = new ArrayList<>();
				StringBuilder msg = new StringBuilder();
				
				if (name.isEmpty()) {
					regNameTF.getStyleClass().add("danger");
					emptyMsg.add("Name");
				}
				
				if(email.isEmpty()) {
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
				
				Snackbar.danger(rootPane, msg.toString() + " field is empty.");
				break;
				
			case 2:
				Snackbar.danger(rootPane, "Only NYP Email address are allowed to register an account with us.");
				break;
				
			case 3:
				Snackbar.danger(rootPane, "This Email is registered with us.");
				break;
				
			case 4:
				Snackbar.danger(rootPane, "This Admin Number is registered with us.");
				break;
			
			default:
				break;
		}
	}
	
	private void resetPass() {
		String adminNo = fpassAdminNoTF.getText();
		String email = fpassEmailTF.getText();
		
		loginAdminNoTF.getStyleClass().removeAll("danger");
		loginPassTF.getStyleClass().removeAll("danger");
		
		fpassAdminNoTF.getStyleClass().removeAll("danger");
		fpassEmailTF.getStyleClass().removeAll("danger");
		
		switch (AccountsDA.passwordReset(adminNo, email)) {
		case 0:
			Snackbar.success(rootPane, "Your password had been reset succssfully, please check your email and login with your new password.");
			break;
			
		case 1:
			if (adminNo.isEmpty() && email.isEmpty()) {
				fpassAdminNoTF.getStyleClass().add("danger");
				fpassEmailTF.getStyleClass().add("danger");
				Snackbar.danger(rootPane, "Admin Number and Email field is empty.");
			}
			else if (adminNo.isEmpty()) {
				fpassAdminNoTF.getStyleClass().add("danger");
				Snackbar.danger(rootPane, "Admin Number field is empty.");
			}
			else if (email.isEmpty()) {
				fpassEmailTF.getStyleClass().add("danger");
				Snackbar.danger(rootPane, "Email field is empty");
			}
			break;
			
		case 2:
			Snackbar.danger(rootPane, "This Admin Number is not registered with us.");
			break;
			
		case 3:
			Snackbar.danger(rootPane, "This Email is not registered with us.");
			break;
			
		case 4:
			Snackbar.danger(rootPane, "Whoops! Something went wrong, please try again.");
			break;

		default:
			break;
		}
	}
}

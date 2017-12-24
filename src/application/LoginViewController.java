package application;

import javafx.fxml.FXML;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.TextField;

import javafx.scene.control.TitledPane;

import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;

public class LoginViewController {
	@FXML
	private TitledPane loginTitledPane;
	@FXML
	private TextField loginAdminNoTF;
	@FXML
	private PasswordField loginPassTF;
	@FXML
	private JFXButton loginBtn;
	@FXML
	private TitledPane regTitledPane;
	@FXML
	private TextField regNameTF;
	@FXML
	private TextField regAdminNoTF;
	@FXML
	private TextField regEmailTF;
	@FXML
	private PasswordField regPassTF;
	@FXML
	private TitledPane fpassTitledPane;
	@FXML
	private TextField fpassAdminNoTF;
	@FXML
	private TextField fpassEmailTF;
	@FXML
	private JFXButton fpassBtn;

	// Event Listener on TitledPane[#fpassTitledPane].onMouseClicked
	@FXML
	public void fpassTitledPaneOnClick(MouseEvent event) {
		if (fpassTitledPane.isExpanded()) {
			fpassTitledPane.getStyleClass().add("inactive");
		}
		else {
			fpassTitledPane.getStyleClass().removeAll("inactive");
			System.out.println(fpassTitledPane.getStyle());
		}
	}
	
	// Event Listener on TitledPane[#regTitlePane].onMouseClicked
	@FXML
	public void regTitledPaneOnClick(MouseEvent event) {
		if (regTitledPane.isExpanded()) {
			fpassTitledPane.getStyleClass().removeAll("inactive");
		}
		else {
			fpassTitledPane.getStyleClass().add("inactive");
		}
	}
	
	// Event Listener on TitledPane[#loginTitlePane].onMouseClicked
	@FXML
	public void loginTitledPaneOnClick(MouseEvent event) {
		if (loginTitledPane.isExpanded()) {
			fpassTitledPane.getStyleClass().removeAll("inactive");
		}
		else {
			fpassTitledPane.getStyleClass().add("inactive");
		}
	}
}

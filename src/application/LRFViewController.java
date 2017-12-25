package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.TitledPane;

import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;

public class LRFViewController {
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

	// Event Listener on JFXButton[#loginBtn].onAction
	@FXML
	public void loginBtnOnAction(ActionEvent event) {
		try {
			Main.setLoc(getClass().getResource("/application/ProfileView.fxml"));
			Main.getRoot().setCenter(FXMLLoader.load(Main.getLoc()));
			Main.getRoot().setBottom(FXMLLoader.load(getClass().getResource("/application/NavigationView.fxml")));
		}
		catch (Exception e) {
			e.printStackTrace();
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

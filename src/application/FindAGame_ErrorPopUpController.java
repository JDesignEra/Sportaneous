package application;

import javafx.fxml.FXML;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FindAGame_ErrorPopUpController {
	@FXML
	private JFXButton OKbtn;
	@FXML
	private Label errorMsg;

	// Event Listener on JFXButton[#OKbtn].onAction
	@FXML
	public void initialize() {
		errorMsg.setText(FindAGameController.getErrorMsg());
	}
	@FXML
	public void handleOK(ActionEvent event) {
		Stage stage = (Stage) OKbtn.getScene().getWindow();
	    stage.close();
	}
}

package application;

import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import dataAccess.AccountsDA;
import dataAccess.FriendsDA;

public class FriendsViewController {
	@FXML JFXTextField nameAdminNoTxtField, emailTxtField;
	@FXML JFXToggleButton srcTypeToggleBtn;
	@FXML GridPane emptyFriendsContent;
	@FXML FlowPane friendsFlowPane;

	private static int accIndex;
	private static Object[][] accounts;

	private final URL friendCardURL = getClass().getResource("/application/modules/FriendCardView.fxml");

	@FXML
	private void initialize() {
		if (FriendsDA.getFriends().length > 0) {
			friendsFlowPane.getChildren().clear();
			friendsFlowPane.alignmentProperty().set(Pos.TOP_CENTER);
			showFriends();
		}
	}

	// Event Listener on JFXToggleButton[#srcTypeToggleBtn].onAction
	@FXML
	public void srcToggleBtnOnAction(ActionEvent event) {
		if (srcTypeToggleBtn.isSelected()) {
			srcTypeToggleBtn.setText("ALL PLAYERS");
			showAll();
		}
		else {
			srcTypeToggleBtn.setText("FRIENDS");
			showFriends();
		}
	}

	// Event Listener on JFXButton[#srcBtn].onAction
	@FXML
	public void srcBtnOnAction(ActionEvent event) {
		int i = 0;

		for (Node node : friendsFlowPane.getChildren()) {
			if ((((String) accounts[i][0]).toLowerCase().contains(nameAdminNoTxtField.getText().toLowerCase())
					|| ((String) accounts[i][2]).toLowerCase().contains(nameAdminNoTxtField.getText().toLowerCase()))
					&& ((String) accounts[i][1]).toLowerCase().contains(emailTxtField.getText().toLowerCase())) {
				node.setVisible(true);
				node.setManaged(true);
			}
			else {
				node.setVisible(false);
				node.setManaged(false);
			}
			i++;
		}
	}

	private void showFriends() {
		accounts = FriendsDA.getFriends();

		friendsFlowPane.getChildren().clear();
		nameAdminNoTxtField.clear();
		emailTxtField.clear();

		if (accounts.length > 0) {
			for (accIndex = 0; accIndex < accounts.length; accIndex++) {
				try {
					friendsFlowPane.getChildren().add(FXMLLoader.load(friendCardURL));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			friendsFlowPane.alignmentProperty().set(Pos.CENTER);
			friendsFlowPane.getChildren().add(emptyFriendsContent);
		}
	}

	private void showAll() {
		accounts = new Object[AccountsDA.getAllData().length][10];
		Object[][] data = AccountsDA.getAllData();

		friendsFlowPane.getChildren().clear();
		nameAdminNoTxtField.clear();
		emailTxtField.clear();

		for (accIndex = 0; accIndex < data.length; accIndex++) {
			accounts[accIndex][0] = data[accIndex][0]; // AdminNo
			accounts[accIndex][1] = data[accIndex][1]; // Email
			accounts[accIndex][2] = data[accIndex][3]; // Name
			accounts[accIndex][3] = data[accIndex][7]; // Height
			accounts[accIndex][4] = data[accIndex][8]; // Weight
			accounts[accIndex][5] = data[accIndex][9]; // Height Visibility
			accounts[accIndex][6] = data[accIndex][10]; // Weight Visibility
			accounts[accIndex][7] = data[accIndex][11]; // Rating
			accounts[accIndex][8] = data[accIndex][12]; // Match Attended
			accounts[accIndex][9] = data[accIndex][13]; // Total Match Joined

			try {
				friendsFlowPane.getChildren().add(FXMLLoader.load(friendCardURL));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getAccIndex() {
		return accIndex;
	}

	public static Object[][] getAccounts() {
		return accounts;
	}
}
